package org.imogene.sync.client.jobs;

import org.eclipse.core.runtime.jobs.Job;

public abstract class PeriodicalJob extends Job {

	public PeriodicalJob(String name) {
		super(name);
	}

	/**
	 * Schedule or reschedule a job for a given delay and user. This may stop a scheduled job before rescheduling it due to the
	 * fact that we cannot change the user-initiated parameter on a scheduled or running job.
	 * 
	 * @param user <code>true</code> if this job is a user-initiated job, and <code>false</code> otherwise.
	 * @param delay a time delay in milliseconds before the job should run
	 */
	protected void schedule(final boolean user, final long delay) {
		new Thread() {
			@Override
			public void run() {
				if (!cancel()) {
					try {
						PeriodicalJob.this.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				setUser(user);
				schedule(delay);
			};
		}.start();
	}

}
