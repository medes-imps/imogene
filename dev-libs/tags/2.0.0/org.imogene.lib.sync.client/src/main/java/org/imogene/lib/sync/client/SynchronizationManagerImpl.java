package org.imogene.lib.sync.client;

import org.apache.log4j.Logger;
import org.imogene.lib.sync.client.parameter.SyncParameter;
import org.imogene.lib.sync.client.parameter.SyncParameterDao;

public class SynchronizationManagerImpl implements SynchronizationManager {

	private static final Logger logger = Logger.getLogger(SynchronizationManagerImpl.class.getName());

	// Injected with Spring
	private SyncParameterDao parameterDao;
	private OptimizedSynchronizer synchronizer;

	// NOT injected with Spring
	private boolean run = true;
	private boolean running = false;
	private SyncParameter parameter;

	/**
	 * Setter for bean injection.
	 * 
	 * @param synchronizer The {@link OptimizedSynchronizer} implementation for
	 *            the application.
	 */
	public void setSynchronizer(OptimizedSynchronizer synchronizer) {
		this.synchronizer = synchronizer;
	}

	/**
	 * Setter for bean injection.
	 * 
	 * @param parameterDao The {@link SyncParameterDao} implementation of the
	 *            application.
	 */
	public void setParameterDao(SyncParameterDao parameterDao) {
		this.parameterDao = parameterDao;
	}

	@Override
	public void addSyncListener(SyncListener listener) {
		synchronizer.addSyncListener(listener);
	}

	@Override
	public void removeListener(SyncListener listener) {
		synchronizer.removeListener(listener);
	}

	@Override
	public boolean isRunning() {
		return running;
	}

	@Override
	public boolean loopMode() {
		return true;
	}

	@Override
	public void start() {
		parameter = parameterDao.load();
		if (parameter == null) {
			throw new RuntimeException("Synchronization parameters not initialized, thread not started");
		}

		logger.debug("Starting the client, run=" + run);
		running = true;
		run = parameter.isLoop();
		while (true) {
			try {
				synchronizer.synchronize();
				if (run) {
					Thread.sleep(parameter.getPeriod());
				} else {
					break;
				}
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
			}
		}
		running = false;
	}

	@Override
	public void stop() {
		run = false;
	}

}
