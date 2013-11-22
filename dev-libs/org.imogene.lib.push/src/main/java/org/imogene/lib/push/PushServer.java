package org.imogene.lib.push;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.imogene.lib.push.connections.ConnectionController;
import org.imogene.lib.push.connections.ConnectionThread;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class PushServer extends Thread implements InitializingBean, DisposableBean {

	private static final Logger logger = Logger.getLogger(PushServer.class);

	private static final int PORT = 7000;

	private ConnectionController connectionController;

	private ServerSocket mServer;
	private boolean mAbort = false;

	public void setConnectionController(ConnectionController connectionController) {
		this.connectionController = connectionController;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (!isAlive()) {
			start();
			logger.debug("PushServer thread started");
		}
	}

	@Override
	public void run() {
		try {
			mServer = new ServerSocket(PORT);
		} catch (IOException e) {
			return;
		}

		for (;;) {
			try {
				Socket socket = mServer.accept();
				ConnectionThread thread = new ConnectionThread(socket, connectionController);
				thread.start();
			} catch (IOException e) {

			} finally {
				if (mAbort) {
					break;
				}
			}
		}
	}

	public void abort() {
		mAbort = true;
		connectionController.close();
		try {
			mServer.close();
		} catch (IOException e) {
		}
	}

	@Override
	public void destroy() {
		abort();
		try {
			join();
		} catch (InterruptedException e) {

		}
	}

}
