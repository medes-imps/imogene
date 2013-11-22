package org.imogene.sync.client.jobs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.imogene.sync.client.i18n.Messages;

public class SyncJob extends PeriodicalJob {

	private Synchronizer synchronizer;

	private long period;
	private boolean running;

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
	 * Update the synchronization parameters.
	 * <p>
	 * This will try to run the synchronization process if needed.
	 * </p>
	 * 
	 * @param start whether the synchronization must be started or not
	 * @param period period of synchronization in milliseconds
	 */
	public void setParameters(boolean start, long period) {
		this.period = period;
		this.running = start;
		if (start) {
			schedule(false, period);
		} else {
			cancel();
		}
	}

	/**
	 * Perform a one shot synchronization. If the synchronization is on loop mode it will be reschedule at the end of the process
	 * for the given period.
	 */
	public void synchronize() {
		schedule(true, 0L);
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		IStatus status = synchronizer.synchronize(monitor);
		if (running && !monitor.isCanceled()) {
			schedule(false, period);
		}
		return status;
	}

}
