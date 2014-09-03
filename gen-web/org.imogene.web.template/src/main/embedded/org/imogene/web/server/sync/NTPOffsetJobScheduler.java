package org.imogene.web.server.sync;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import org.apache.log4j.Logger;
import org.imogene.lib.sync.client.NTPOffsetTask;
import org.imogene.web.server.startup.StartupManager.StartupJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;

public class NTPOffsetJobScheduler implements StartupJob {

	private static final Logger LOG = Logger.getLogger(NTPOffsetJobScheduler.class);

	@Autowired
	private TaskScheduler scheduler;

	@Autowired
	private NTPOffsetTask ntpOffsetTask;

	private ScheduledFuture<?> scheduledFuture;

	private int delay = 10000;

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public void schedule() {
		LOG.info("Scheduling NTP offset task: delay(ms): " + delay);
		scheduledFuture = scheduler.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				LOG.info("Update offset from NTP server: " + new Date());
				ntpOffsetTask.update();
			}
		}, new Date(System.currentTimeMillis() + delay), delay);
	}

	public void cancel() {
		if (scheduledFuture != null) {
			LOG.info("Cancel scheduled NTP offset task");
			scheduledFuture.cancel(false);
			scheduledFuture = null;
		}
	}

	@Override
	public void run() {
		schedule();
	}

}