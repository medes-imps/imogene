package org.imogene.web.server.sync;

import org.imogene.web.server.startup.StartupManager.StartupJob;

public interface SynchronizationScheduler extends StartupJob {

	public static final int STATUS_CANCELLED = 1;
	public static final int STATUS_ELAPSED = 2;
	public static final int STATUS_WAITING = 3;

	void init(String url, String login, String password);

	int getStatus();

	void cancel();

}