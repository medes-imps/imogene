package org.imogene.web.server.sync;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.imogene.encryption.EncryptionManager;
import org.imogene.lib.sync.client.Synchronizer;
import org.imogene.lib.sync.client.params.SyncParams;
import org.imogene.web.server.handler.GenericHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.transaction.annotation.Transactional;

public class SynchronizationSchedulerImpl implements SynchronizationScheduler {

	private static final Logger LOG = Logger.getLogger(SynchronizationSchedulerImpl.class);

	@Autowired
	private TaskScheduler scheduler;

	@Autowired
	private EncryptionManager encryptionManager;

	@Autowired
	private Synchronizer synchronizer;

	@Autowired
	private GenericHandler handler;

	private ScheduledFuture<?> scheduledFuture;

	@Override
	@Transactional
	public void init(String url, String login, String password) {
		// Try to authenticate
		if (synchronizer.authenticate(url, login, password) != Synchronizer.AUTH_SUCCESS) {
			return;
		}
		// Run a synchronization just after
		String terminal = UUID.randomUUID().toString();
		if (synchronizer.synchronize(url, login, password, terminal, null) != Synchronizer.SYNC_SUCCESS) {
			return;
		}
		// If succeed register sync parameters
		SyncParams params = handler.find(SyncParams.class, SyncParams.ID);
		if (params == null) {
			params = new SyncParams();
		}
		params.setLogin(login);
		params.setUrl(url);
		params.setPassword(new String(Base64.encodeBase64(encryptionManager.encrypt(password.getBytes()))));
		params.setTerminal(terminal);
		handler.save(params);
		schedule();
	}

	@Override
	public void cancel() {
		if (scheduledFuture != null) {
			LOG.info("Cancel scheduled synchronization");
			scheduledFuture.cancel(false);
			scheduledFuture = null;
		}
	}

	@Override
	public int getStatus() {
		if (scheduledFuture != null) {
			long delay = scheduledFuture.getDelay(TimeUnit.MILLISECONDS);
			if (delay <= 0) {
				return STATUS_ELAPSED;
			} else {
				return STATUS_WAITING;
			}
		}
		return STATUS_CANCELLED;
	}

	@Override
	@Transactional
	public void run() {
		schedule();
	}
	
	private void schedule() {
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


}