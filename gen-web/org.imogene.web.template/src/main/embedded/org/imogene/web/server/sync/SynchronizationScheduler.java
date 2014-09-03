package org.imogene.web.server.sync;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.imogene.encryption.EncryptionManager;
import org.imogene.lib.sync.client.Synchronizer;
import org.imogene.lib.sync.client.params.SyncParams;
import org.imogene.web.server.handler.GenericHandler;
import org.imogene.web.server.startup.StartupManager.StartupJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.transaction.annotation.Transactional;

public class SynchronizationScheduler implements StartupJob, SynchronizationHelper {

	private static final Logger LOG = Logger.getLogger(SynchronizationScheduler.class);

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

	@Transactional
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
	@Transactional
	public void run() {
		schedule();
	}

}