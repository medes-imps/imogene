package org.imogene.web.server.sync;

import org.imogene.lib.sync.client.SyncHandler;
import org.imogene.lib.sync.history.SyncHistory;
import org.springframework.beans.factory.annotation.Autowired;

public class SyncStatusHandler {

	@Autowired
	private SyncHandler syncHandler;

	@Autowired
	private SynchronizationScheduler synchronizationScheduler;

	public SyncStatus getSyncStatus() {
		SyncHistory history = syncHandler.loadLastSyncHistory();

		SyncStatus status = new SyncStatus();
		if (history != null) {
			status.setLastSyncTime(history.getTime());
		}

		switch (synchronizationScheduler.getStatus()) {
		case SynchronizationScheduler.STATUS_CANCELLED:
		case SynchronizationScheduler.STATUS_WAITING:
			if (history == null) {
				status.setCurrentStatus(SyncStatus.STATUS_UNKNOWN);
				break;
			}
			switch (history.getStatus()) {
			case SyncHistory.STATUS_OK:
				status.setCurrentStatus(SyncStatus.STATUS_OK);
				break;
			case SyncHistory.STATUS_ERROR:
				status.setCurrentStatus(SyncStatus.STATUS_ERROR);
				break;
			}
			break;
		case SynchronizationScheduler.STATUS_ELAPSED:
			status.setCurrentStatus(SyncStatus.STATUS_RUNNING);
			break;
		}
		return status;
	}

	public void resetSyncHistory() {
		syncHandler.resetSyncHistory();
	}

}
