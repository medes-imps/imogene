package org.imogene.web.server.scheduling;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import org.apache.log4j.Logger;
import org.imogene.lib.sync.client.Synchronizer;
import org.imogene.lib.sync.client.params.SyncParams;
import org.imogene.web.server.handler.GenericHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.TaskScheduler;

public class SynchronizationScheduler implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger LOG = Logger.getLogger(SynchronizationScheduler.class);

	@Autowired
	private TaskScheduler scheduler;

	@Autowired
	private Synchronizer synchronizer;

	@Autowired
	private GenericHandler handler;

	private ScheduledFuture<?> scheduledFuture;

	public void schedule() {
		SyncParams params = handler.find(SyncParams.class, SyncParams.ID);
		if (params == null || params.getUrl() == null || params.getLogin() == null || params.getPassword() == null
				|| params.getTerminal() == null) {
			LOG.info("Synchronization parameters not intialized");
			return;
		}

		cancel();

		long delay = params.getPeriod() != null ? params.getPeriod() : 15000;
		LOG.info("Scheduling synchronization: delay(ms): " + delay);
		scheduledFuture = scheduler.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				LOG.info("Synchronizing: " + new Date());
				synchronizer.synchronize();

			}
		}, new Date(System.currentTimeMillis() + delay), delay);
	}

	public void cancel() {
		if (scheduledFuture != null) {
			LOG.info("Cancel scheduled synchronization");
			scheduledFuture.cancel(false);
			scheduledFuture = null;
		}
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		ApplicationContext parent = event.getApplicationContext().getParent();
		if (parent == null) {
			LOG.info("ContextRefreshEvent: Root Context");
			return;
		}
		schedule();
	}

}
