package org.imogene.sync.client.jobs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.imogene.sync.client.i18n.Messages;

public class SyncJob extends PeriodicalJob {

	private Synchronizer synchronizer;

	private long period;
	private boolean enabled;

	public SyncJob() {
		super(Messages.sync_title);
	}

	/**
	 * Setter for the synchronizer.
	 * 
	 * @param synchronizer The synchronizer
	 */
	public void setSynchronizer(Synchronizer synchronizer) {
		this.synchronizer = synchronizer;
	}

	/**
	 * Change the synchronization job status.
	 * 
	 * @param enabled {@code true} to enable the synchronization, {@code false} otherwise
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		updateStatus();
	}

	/**
	 * Set the synchronization period in milliseconds.
	 * 
	 * @param period The synchronization period in milliseconds
	 */
	public void setPeriod(long period) {
		this.period = period;
		updateStatus();
	}

	private void updateStatus() {
		if (enabled) {
			schedule(false, period);
		} else {
			cancel();
		}
	}

	/**
	 * Perform a one shot synchronization. If the synchronization is on loop mode it will be reschedule at the end of
	 * the process for the given period.
	 */
	public void synchronize() {
		schedule(true, 0L);
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		IStatus status = synchronizer.synchronize(monitor);
		if (enabled && !monitor.isCanceled()) {
			schedule(false, period);
		}
		return status;
	}

}
