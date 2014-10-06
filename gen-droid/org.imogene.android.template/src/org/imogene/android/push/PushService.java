package org.imogene.android.push;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.imogene.android.Constants;
import org.imogene.android.preference.Preferences;
import org.imogene.android.sync.SyncManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import fr.medes.android.util.ssl.TrustAllSSLSocketFactory;

/**
 * 
 * @author julien
 * @see <a href="https://code.google.com/p/android-random/">https://code.google.com/p/android-random/</a>
 */
public class PushService extends Service {

	private static final String TAG = PushService.class.getName();

	private static final String ACTION_START = "org.imogene.android.action.START";
	private static final String ACTION_STOP = "org.imogene.android.action.STOP";
	private static final String ACTION_PING = "org.imogene.android.action.HEARTBEAT";
	private static final String ACTION_RECONNECT = "org.imogene.android.action.RECONNECT";
	private static final String ACTION_PING_TIMEOUT = "org.imogene.android.action.PING_TIMEOUT";

	private static final String MSG_AUTH = "AUTH";
	private static final String MSG_PING = "PING";
	private static final String MSG_PONG = "PONG";
	private static final String MSG_ACK = "ACK";
	private static final String MSG_PUSH = "PUSH";
	private static final String MSG_QUIT = "QUIT";

	private static final String CMD_SYNC = "SYNC";

	private static final long INITIAL_RETRY_INTERVAL = 10 * 1000;
	private static final long MAXIMUM_RETRY_INTERVAL = 30 * 60 * 1000;

	private static final long PING_INTERVAL = 5 * 60 * 1000;
	private static final long PING_TIMEOUT = 1 * 60 * 1000;

	private Preferences mPrefs;

	private boolean mStarted;
	private ConnectionThread mConnection;

	private ConnectivityManager mConnMan;

	private String mHost;
	private int mPort;
	private boolean mSsl;
	private String mTerminal;
	private String mLogin;
	private String mPassword;

	private final BroadcastReceiver mConnectionReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			NetworkInfo info = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);

			boolean hasConnectivity = (info != null && info.isConnected()) ? true : false;

			if (Constants.DEBUG) {
				Log.i(TAG, "Connecting changed: connected=" + hasConnectivity);
			}

			if (hasConnectivity) {
				PushService.actionReconnect(context);
			}
		}
	};

	public static void actionStart(Context context) {
		Intent intent = new Intent(context, PushService.class);
		intent.setAction(ACTION_START);
		context.startService(intent);
	}

	public static void actionStop(Context context) {
		Intent intent = new Intent(context, PushService.class);
		intent.setAction(ACTION_STOP);
		context.startService(intent);
	}

	public static void actionPing(Context context) {
		Intent intent = new Intent(context, PushService.class);
		intent.setAction(ACTION_PING);
		context.startService(intent);
	}

	public static void actionReconnect(Context context) {
		Intent intent = new Intent(context, PushService.class);
		intent.setAction(ACTION_RECONNECT);
		context.startService(intent);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mConnMan = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		mPrefs = Preferences.getPreferences(this);

		mHost = mPrefs.getPushHost();
		mPort = mPrefs.getPushPort();
		mSsl = mPrefs.isPushSslEnabled();
		mTerminal = mPrefs.getSyncTerminal();
		mLogin = mPrefs.getSyncLogin();
		mPassword = mPrefs.getSyncPassword();

		/*
		 * If our process was reaped by the system for any reason we need to restore our state with merely a call to
		 * onCreate. We record the last "started" value and restore it here if necessary.
		 */
		handleCrashedService();
	}

	@Override
	public void onDestroy() {
		if (Constants.DEBUG) {
			Log.i(TAG, "Service destroyed (started=" + mStarted + ")");
		}

		if (mStarted == true) {
			stop();
		}
	}

	private void handleCrashedService() {
		if (wasStarted() == true) {
			/*
			 * We probably didn't get a chance to clean up gracefully, so do it now.
			 */
			stopPinging();

			/* Formally start and attempt connection. */
			start();
		}
	}

	private boolean wasStarted() {
		return mPrefs.isPushStarted();
	}

	private void setStarted(boolean started) {
		mPrefs.setPushStarted(started);
		mStarted = started;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		final String action = intent.getAction();
		if (ACTION_START.equals(action)) {
			start();
		} else if (ACTION_STOP.equals(action)) {
			stop();
			stopSelf();
		} else if (ACTION_PING.equals(action)) {
			ping();
		} else if (ACTION_RECONNECT.equals(action)) {
			reconnectIfNecessary();
		} else if (ACTION_PING_TIMEOUT.equals(action)) {
			pingTimeout();
		}
		// Since we are pinging periodically the connection is restarted in the onCreate method.
		return START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private void startPinging() {
		if (Constants.DEBUG) {
			Log.i(TAG, "Start pinging");
		}

		Intent i = new Intent(this, PushService.class);
		i.setAction(ACTION_PING);

		PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
		AlarmManager alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + PING_INTERVAL, PING_INTERVAL, pi);
	}

	private void stopPinging() {
		if (Constants.DEBUG) {
			Log.i(TAG, "Stop pinging");
		}

		Intent i = new Intent(this, PushService.class);
		i.setAction(ACTION_PING);

		PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
		AlarmManager alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmMgr.cancel(pi);
	}

	private void scheduleReconnect(long startTime) {
		long interval = mPrefs.getPushRetryInterval(INITIAL_RETRY_INTERVAL);

		long now = System.currentTimeMillis();
		long elapsed = now - startTime;
		if (elapsed < interval) {
			interval = Math.min(interval * 2, MAXIMUM_RETRY_INTERVAL);
		} else {
			interval = INITIAL_RETRY_INTERVAL;
		}

		if (Constants.DEBUG) {
			Log.i(TAG, "Rescheduling connection in " + interval + "ms.");
		}

		mPrefs.setPushRetryInterval(interval);

		Intent i = new Intent(this, PushService.class);
		i.setAction(ACTION_RECONNECT);

		PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
		AlarmManager alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmMgr.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + interval, pi);
	}

	private void resetReconnectInterval() {
		mPrefs.setPushRetryInterval(INITIAL_RETRY_INTERVAL);
	}

	private void unscheduleReconnect() {
		if (Constants.DEBUG) {
			Log.i(TAG, "Stop trying to reconnect");
		}

		Intent i = new Intent(this, PushService.class);
		i.setAction(ACTION_RECONNECT);

		PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
		AlarmManager alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmMgr.cancel(pi);
	}

	private void schedulePingTimeout() {
		if (Constants.DEBUG) {
			Log.i(TAG, "Scheduling ping timeout in " + PING_TIMEOUT + "ms.");
		}

		Intent i = new Intent(this, PushService.class);
		i.setAction(ACTION_PING_TIMEOUT);

		PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
		AlarmManager alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmMgr.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + PING_TIMEOUT, pi);
	}

	private void unschedulePingTimeout() {
		if (Constants.DEBUG) {
			Log.i(TAG, "Unscheduling ping timeout");
		}

		Intent i = new Intent(this, PushService.class);
		i.setAction(ACTION_PING_TIMEOUT);

		PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
		AlarmManager alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmMgr.cancel(pi);
	}

	private synchronized void start() {
		if (mStarted == true) {
			if (Constants.DEBUG) {
				Log.i(TAG, "Attempt to start connection that is already active");
			}
			return;
		}

		setStarted(true);

		registerReceiver(mConnectionReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

		if (Constants.DEBUG) {
			Log.i(TAG, "Connecting...");
		}

		mConnection = new ConnectionThread();
		mConnection.start();
	}

	private synchronized void stop() {
		if (mStarted == false) {
			if (Constants.DEBUG) {
				Log.i(TAG, "Nothing to do, connection do not exist");
			}
			return;
		}

		setStarted(false);

		unregisterReceiver(mConnectionReceiver);
		unscheduleReconnect();

		if (mConnection != null) {
			try {
				mConnection.quit();
			} catch (IOException e) {
				e.printStackTrace();
			}
			mConnection.abort();
			mConnection = null;
		}

	}

	private synchronized void ping() {
		if (mStarted == true && mConnection != null) {
			try {
				mConnection.ping();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private synchronized void reconnectIfNecessary() {
		if (mStarted == true && mConnection == null) {
			if (Constants.DEBUG) {
				Log.i(TAG, "Reconnecting...");
			}

			mConnection = new ConnectionThread();
			mConnection.start();
		}
	}

	private synchronized void pingTimeout() {
		if (mConnection != null) {
			mConnection.abort();
			mConnection = null;
		}
		reconnectIfNecessary();
	}

	private class ConnectionThread extends Thread {

		private Socket mSocket;

		private volatile boolean mAbort = false;

		public ConnectionThread() {
			if (mSsl) {
				try {
					TrustAllSSLSocketFactory factory = new TrustAllSSLSocketFactory();
					mSocket = factory.createSocket();
				} catch (Exception e) {
					Log.e(TAG, "Problem constructiong SSLSocket", e);
				}
			} else {
				mSocket = new Socket();
			}
		}

		private boolean isNetworkAvailable() {
			NetworkInfo info = mConnMan.getActiveNetworkInfo();
			if (info == null) {
				return false;
			}

			return info.isConnected();
		}

		@Override
		public void run() {
			Socket s = mSocket;

			long startTime = System.currentTimeMillis();

			try {
				s.connect(new InetSocketAddress(mHost, mPort), 20000);

				/*
				 * This is a special case for our demonstration. The keep-alive is sent from the client side but since
				 * I'm testing it with just netcat, no response is sent from the server. This means that we might never
				 * actually read any data even though our connection is still alive. Most instances of a persistent TCP
				 * connection would have some sort of application-layer acknowledgement from the server and so should
				 * set a read timeout of KEEP_ALIVE_INTERVAL plus an arbitrary timeout such as 2 minutes.
				 */
				// s.setSoTimeout((int)KEEP_ALIVE_INTERVAL + 120000);

				if (Constants.DEBUG) {
					Log.i(TAG, "Connection established to " + s.getInetAddress() + ":" + mPort);
				}

				/*
				 * Note that T-Mobile appears to implement an opportunistic connect algorithm where the connect call may
				 * succeed even when the remote peer would reject the connection. Shortly after an attempt to send data
				 * an exception will occur indicating the connection was reset.
				 */
				// out.write("Hello, world.\n".getBytes());

				authenticate();

				resetReconnectInterval();

				startPinging();

				InputStream in = s.getInputStream();

				byte[] b = new byte[1024];
				int length = 0;
				while ((length = in.read(b)) != -1) {
					String message = new String(b, 0, length);
					if (Constants.DEBUG) {
						Log.i(TAG, "**** Receiving: " + message);
					}
					process(message);
				}
				if (Constants.DEBUG) {
					Log.i(TAG, "Has finished reading");
				}

				if (mAbort == false) {
					if (Constants.DEBUG) {
						Log.i(TAG, "Server closed connection unexpectedly.");
					}
				}
			} catch (IOException e) {
				Log.e(TAG, "Unexpected I/O error", e);
			} finally {
				stopPinging();
				unschedulePingTimeout();

				if (mAbort == true) {
					if (Constants.DEBUG) {
						Log.i(TAG, "Connection aborted, shutting down.");
					}
				} else {
					try {
						s.close();
					} catch (IOException e) {
					}

					synchronized (PushService.this) {
						mConnection = null;
					}

					/*
					 * If our local interface is still up then the connection failure must have been something
					 * intermittent. Try our connection again later (the wait grows with each successive failure).
					 * Otherwise we will try to reconnect when the local interface comes back.
					 */
					if (isNetworkAvailable()) {
						scheduleReconnect(startTime);
					}
				}
			}
		}

		private void process(String message) throws IOException {
			if (message.startsWith(MSG_PONG)) {
				unschedulePingTimeout();
				if (Constants.DEBUG) {
					Log.i(TAG, "Pong received unschedule timeout");
				}
			} else if (message.startsWith(MSG_PING)) {
				if (Constants.DEBUG) {
					Log.i(TAG, "Ping received answer pong");
				}
				pong();
			} else if (message.startsWith(MSG_PUSH)) {
				String[] parts = message.split(";");
				if (parts.length == 3) {
					String id = parts[1];
					String cmd = parts[2];
					if (Constants.DEBUG) {
						Log.i(TAG, "Command received: " + cmd);
					}
					acknowledge(id);
					if (cmd.equals(CMD_SYNC)) {
						mHandler.sendEmptyMessage(MESSAGE_SYNC);
					}
				} else {
					if (Constants.DEBUG) {
						Log.i(TAG, "Push message malformed");
					}
				}
			}
		}

		public synchronized void write(byte[] bytes) throws IOException {
			mSocket.getOutputStream().write(bytes);
		}

		public synchronized void write(String message) throws IOException {
			write(message.getBytes());
		}

		public synchronized void authenticate() throws IOException {
			write(MSG_AUTH + ";" + mTerminal + ";" + mLogin + ";" + mPassword);
		}

		public synchronized void pong() throws IOException {
			write(MSG_PONG);
			if (Constants.DEBUG) {
				Log.i(TAG, "PONG sent.");
			}
		}

		public synchronized void acknowledge(String id) throws IOException {
			write(MSG_ACK + ";" + id);

			if (Constants.DEBUG) {
				Log.i(TAG, "ACKNOLEDGEMENT sent for message " + id);
			}
		}

		public synchronized void ping() throws IOException {
			write(MSG_PING);
			schedulePingTimeout();

			if (Constants.DEBUG) {
				Log.i(TAG, "PING sent.");
			}
		}

		public synchronized void quit() throws IOException {
			write(MSG_QUIT);

			if (Constants.DEBUG) {
				Log.i(TAG, "QUIT sent.");
			}
		}

		public synchronized void abort() {
			if (Constants.DEBUG) {
				Log.i(TAG, "Connection aborting.");
			}

			mAbort = true;

			try {
				mSocket.shutdownOutput();
			} catch (Exception e) {
			}

			try {
				mSocket.shutdownInput();
			} catch (Exception e) {
			}

			try {
				mSocket.close();
			} catch (Exception e) {
			}

			while (true) {
				try {
					join();
					break;
				} catch (InterruptedException e) {
				}
			}
		}
	}

	private static final int MESSAGE_SYNC = 1;

	private final Handler mHandler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			if (msg.what == MESSAGE_SYNC) {
				SyncManager.startManualSync(PushService.this);
			}
			return false;
		}
	});

}
