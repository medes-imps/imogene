package org.imogene.web.server.sync;

import java.util.Date;

public class SyncStatus {

	public static final int STATUS_OK = 0;
	public static final int STATUS_ERROR = 1;
	public static final int STATUS_RUNNING = 2;
	public static final int STATUS_UNKNOWN = 3;

	private Date lastSyncTime;

	private int currentStatus = STATUS_UNKNOWN;

	public void setLastSyncTime(Date lastSyncTime) {
		this.lastSyncTime = lastSyncTime;
	}

	public Date getLastSyncTime() {
		return lastSyncTime;
	}

	public void setCurrentStatus(int currentStatus) {
		this.currentStatus = currentStatus;
	}

	public int getCurrentStatus() {
		return currentStatus;
	}

}
