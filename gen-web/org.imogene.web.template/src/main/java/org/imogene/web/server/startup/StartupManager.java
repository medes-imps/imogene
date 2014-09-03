package org.imogene.web.server.startup;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Convenient manager to launch Startup jobs as synchronization or database initialization
 * 
 * @author MEDES-IMPS
 * 
 */
public class StartupManager implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger LOG = Logger.getLogger(StartupManager.class);

	/**
	 * Interface to implement to start a job when application context is fully started.
	 * 
	 * @author MEDES-IMPS
	 * 
	 */
	public static interface StartupJob extends Runnable {
	}

	private List<StartupJob> jobs;

	public void onApplicationEvent(ContextRefreshedEvent event) {
		ApplicationContext parent = event.getApplicationContext().getParent();
		if (parent == null) {
			LOG.info("ContextRefreshEvent: Root Context");
			return;
		}
		LOG.info("ContextRefreshEvent: Application Context");
		for (StartupJob job : jobs) {
			try {
				job.run();
			} catch (Exception e) {
				LOG.error("Error running startup job: " + job.getClass().getName(), e);
			}
		}
	}

	public void setJobs(List<StartupJob> jobs) {
		this.jobs = jobs;
	}

}