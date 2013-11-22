package org.imogene.lib.push.connections;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

public class ConnectionThread extends Thread {

	private static final Logger logger = Logger.getLogger(ConnectionThread.class.getName());

	private static final String MSG_AUTH = "AUTH";
	private static final String MSG_PING = "PING";
	private static final String MSG_PONG = "PONG";
	private static final String MSG_ACK = "ACK";
	private static final String MSG_PUSH = "PUSH";
	private static final String MSG_QUIT = "QUIT";

	private static final long PING_INTERVAL = 10 * 60 * 1000;
	private static final long PING_TIMEOUT = 2 * 60 * 1000;

	private static final long PUSH_TIMEOUT = 1 * 60 * 1000;
	private static final long PUSH_INTERVAL = 1 * 60 * 1000;

	private final ConnectionController mConnectionController;

	private final Socket mSocket;
	private final Timer mTimer;

	private TimerTask mPingTask = new TimerTask() {
		@Override
		public void run() {
			try {
				ping();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

	private TimerTask mCleanerTask = new TimerTask() {
		@Override
		public void run() {
			mTimer.cancel();
		}
	};

	private Map<String, PushTimeoutTask> mPushTimeoutTasks = new ConcurrentHashMap<String, ConnectionThread.PushTimeoutTask>();
	private TimeoutTask mTimeoutTask;

	private String mConnectionId;
	private String mLogin;
	private String mTerminal;
	private boolean mAuthenticated = false;

	public ConnectionThread(Socket socket, ConnectionController connectionController) {
		mSocket = socket;
		mConnectionController = connectionController;
		mTimer = new Timer();
	}

	public String getConnectionId() {
		return mConnectionId;
	}

	public void setConnectionId(String id) {
		mConnectionId = id;
	}

	public String getLogin() {
		return mLogin;
	}

	public String getTerminal() {
		return mTerminal;
	}

	public void run() {
		try {
			startPinging();

			InputStream is = mSocket.getInputStream();

			byte[] bytes = new byte[1024];
			int length = 0;
			while ((length = is.read(bytes)) != -1) {
				String received = new String(bytes, 0, length);
				logger.debug("thread " + getId() + " received " + received);
				if (mAuthenticated) {
					process(received);
				} else {
					authenticate(received);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			mConnectionController.unregisterThread(this);
			mTimeoutTask = null;
			mPushTimeoutTasks.clear();
			mTimer.schedule(mCleanerTask, 0);
		}
	};

	private synchronized void ping() throws IOException {
		mSocket.getOutputStream().write(MSG_PING.getBytes());
		schedulePingTimeout();
		logger.debug("PING sent from thread: " + Thread.currentThread().getId());
	}

	private synchronized void pong() throws IOException {
		mSocket.getOutputStream().write(MSG_PONG.getBytes());
		logger.debug("PONG sent");
	}

	private synchronized void acknowledge() throws IOException {
		mSocket.getOutputStream().write(MSG_ACK.getBytes());
		logger.debug("ACKNOLEDGEMENT sent");
	}

	private synchronized void push(String id, String message) throws IOException {
		String push = MSG_PUSH + ";" + id + ";" + message;
		mSocket.getOutputStream().write(push.getBytes());
		logger.debug("PUSH message sent");
	}

	public synchronized void pushAndScheduleTimeout(String id, String message) throws IOException {
		push(id, message);

		schedulePushTimeout(id, message);
	}

	public synchronized void abort() {
		mAuthenticated = false;

		try {
			mSocket.shutdownInput();
		} catch (IOException e) {
		}

		try {
			mSocket.shutdownOutput();
		} catch (IOException e) {
		}

		try {
			mSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		logger.debug("Abort - Socket closed");
	}

	private void authenticate(String message) {
		if (message.startsWith(MSG_AUTH)) {
			logger.debug("Authentication...");
			String[] parts = message.split(";");
			if (parts.length >= 4) {
				String terminal = parts[1];
				String login = parts[2];
				String password = parts[3];
				if (mConnectionController.validate(login, password)) {
					mAuthenticated = true;
					mLogin = login;
					mTerminal = terminal;
					mConnectionController.registerThread(this);
					logger.debug("Login succesfull from " + mTerminal + " with user " + mLogin);
				} else {
					logger.debug("Login failed from " + mTerminal + " with user " + mLogin);
					abort();
				}
			}
		} else {
			logger.debug("User must be authenticated before using the tunnel");
			abort();
		}
	}

	private void process(String message) throws IOException {
		if (message.startsWith(MSG_QUIT)) {
			logger.debug("Abort connection");
			abort();
		} else if (message.startsWith(MSG_PING)) {
			logger.debug("Ping received from " + mTerminal + " with user " + mLogin);
			pong();
			mConnectionController.pingReceived(this);
		} else if (message.startsWith(MSG_PONG)) {
			logger.debug("Pong received from " + mTerminal + " with user " + mLogin);
			unschedulePingTimeout();
			mConnectionController.pongReceived(this);
		} else if (message.startsWith(MSG_ACK)) {
			String id = message.split(";")[1];
			mConnectionController.messagePushed(id);
			unschedulePushTimeout(id);
			logger.debug("Acknoledgement received from " + mTerminal + " with user " + mLogin + " for message " + id);
		} else {
			logger.debug("Message malformed received from " + mTerminal + " with user " + mLogin);
		}
	}

	private void startPinging() {
		logger.debug("Start pinging");
		mTimer.schedule(mPingTask, PING_INTERVAL, PING_INTERVAL);
	}

	private void schedulePingTimeout() {
		logger.debug("Schedule ping timeout");
		mTimer.schedule(mTimeoutTask = new TimeoutTask(), PING_TIMEOUT);
	}

	private void unschedulePingTimeout() {
		logger.debug("Unschedule ping timeout");
		if (mTimeoutTask != null) {
			mTimeoutTask.cancel();
			mTimeoutTask = null;
			mTimer.purge();
		}
	}

	private void schedulePushTimeout(String id, String message) {
		logger.debug("Schedule push timeout for [id, message] = [" + id + ", " + message + "]");
		PushTimeoutTask task = new PushTimeoutTask(id, message);
		mTimer.schedule(task, PUSH_TIMEOUT, PUSH_INTERVAL);
		mPushTimeoutTasks.put(id, task);
	}

	private void unschedulePushTimeout(String id) {
		logger.debug("Unschedule push timeout for message " + id);
		PushTimeoutTask task = mPushTimeoutTasks.remove(id);
		if (task != null) {
			task.cancel();
			mTimer.purge();
		}
	}

	private class TimeoutTask extends TimerTask {
		@Override
		public void run() {
			abort();
		}
	}

	private class PushTimeoutTask extends TimerTask {

		private final String mId;
		private final String mMessage;

		public PushTimeoutTask(String id, String message) {
			mId = id;
			mMessage = message;
		}

		@Override
		public void run() {
			mConnectionController.messageTimedout(ConnectionThread.this, mId);
			try {
				push(mId, mMessage);
			} catch (IOException e) {
			}
		}
	}

}
