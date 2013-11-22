package org.imogene.sync.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.log4j.xml.DOMConfigurator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.imogene.lib.sync.client.parameter.SyncParameter;
import org.imogene.sync.client.config.MyPropertyPlaceholderConfigurer;
import org.imogene.sync.client.init.ClientInitializer;
import org.imogene.sync.client.jobs.NTPOffsetJob;
import org.imogene.sync.client.jobs.SyncJob;
import org.imogene.sync.client.jobs.Synchronizer;
import org.imogene.sync.client.ssl.EasySSLProtocolSocketFactory;
import org.imogene.sync.client.ui.ISyncConstants;
import org.osgi.framework.BundleContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SyncActivator extends AbstractUIPlugin {

	private static final Logger logger = Logger.getLogger(SyncActivator.class.getName());

	private static final String PLATFORM_TOKEN = "%PLATFORM%"; //$NON-NLS-1$
	private static final String JDBC_URL_PARAM = "jdbc.url"; //$NON-NLS-1$
	private static final String SYNC_DIR_PARAM = "sync.file.directory"; //$NON-NLS-1$

	public static final String PLUGIN_ID = "org.imogene.sync.client.extbcam"; //$NON-NLS-1$

	private static SyncActivator plugin;

	private Synchronizer synchronizer;
	private SyncJob syncJob;
	private NTPOffsetJob ntpJob;

	/**
	 * Returns the shared instance.
	 * 
	 * @return the shared instance
	 */
	public static SyncActivator getDefault() {
		return plugin;
	}

	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		logger.info("Starting Synchronization"); //$NON-NLS-1$
		plugin = this;

		DOMConfigurator.configure(getClass().getResource("/log4j.xml")); //$NON-NLS-1$

		Protocol lEasyHttps = new Protocol("https", new EasySSLProtocolSocketFactory(), 443); //$NON-NLS-1$
		Protocol.registerProtocol("https", lEasyHttps); //$NON-NLS-1$

		MyPropertyPlaceholderConfigurer.getInstance().setProperties(getSynchronizerProperties());
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("application-context.xml", "dao-context.xml", //$NON-NLS-1$ //$NON-NLS-2$
				"converter-context.xml"); //$NON-NLS-1$

		synchronizer = (Synchronizer) ctx.getBean("synchronizer"); //$NON-NLS-1$

		ClientInitializer init = (ClientInitializer) ctx.getBean("clientInitializer");
		init.initDatase();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		if (syncJob != null) {
			if (!syncJob.cancel()) {
				syncJob.join();
			}
			syncJob = null;
		}

		if (ntpJob != null) {
			if (!ntpJob.cancel()) {
				ntpJob.join();
			}
			ntpJob = null;
		}

		plugin = null;
		super.stop(context);
	}

	/**
	 * Define the time offset between the machine and an NTP server.
	 * 
	 * @param offset Time offset in milliseconds
	 */
	public void setOffset(long offset) {
		synchronizer.setOffset(offset);
	}

	/**
	 * Update the synchronization parameters.
	 * <p>
	 * This will try to run the synchronization process if needed.
	 * </p>
	 * 
	 * @param url URL of the synchronization server
	 * @param loop whether the synchronization must be repeated or not
	 * @param period period of synchronization in milliseconds
	 */
	public void setSyncParameters(String url, String terminal, boolean start, long period) {
		ensureSyncJob();
		synchronizer.setParameters(url, terminal);
		syncJob.setParameters(start, period);
	}

	/**
	 * Retrieves the synchronization parameters.
	 * 
	 * @return The synchronization parameters
	 */
	public SyncParameter getSyncParameters() {
		return synchronizer.getParameters();
	}

	/**
	 * Update the NTP parameters.
	 * <p>
	 * This will try to run the NTP client process if needed.
	 * </p>
	 * 
	 * @param host URL of the NTP host
	 * @param rate frequency of the updates
	 */
	public void setNTPParameters(String host, long rate) {
		if (ntpJob == null) {
			ntpJob = new NTPOffsetJob();
		}
		ntpJob.setParameters(host, rate);
	}

	/**
	 * Start the synchronization for the given period.
	 */
	public void start() {
		getPreferenceStore().setValue(ISyncConstants.SYNC_AUTO, true);
	}

	/**
	 * Stop the synchronization.
	 */
	public void stop() {
		getPreferenceStore().setValue(ISyncConstants.SYNC_AUTO, false);
	}

	/**
	 * Perform a one shot synchronization. If the synchronization is on loop mode it will be reschedule at the end of the process
	 * for the given period.
	 */
	public void synchronize() {
		ensureSyncJob();
		syncJob.synchronize();
	}

	private void ensureSyncJob() {
		if (syncJob == null) {
			syncJob = new SyncJob();
			syncJob.setSynchronizer(synchronizer);
			syncJob.addJobChangeListener(new JobChangeAdapter() {
				@Override
				public void done(IJobChangeEvent event) {
					if (event.getResult().getSeverity() == Status.OK) {
						getPreferenceStore().setValue(ISyncConstants.SYNC_LAST, System.currentTimeMillis());
					}
				}
			});
		}
	}

	/**
	 * Create properties from the eclipse preferences.
	 * 
	 * @return The synchronization client parameters
	 * @throws IOException
	 */
	private Properties getSynchronizerProperties() throws IOException {
		Properties p = new Properties();
		InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties"); //$NON-NLS-1$
		p.load(is);
		setWorkspaceLocation(p, JDBC_URL_PARAM);
		setWorkspaceLocation(p, SYNC_DIR_PARAM);
		return p;
	}

	private void setWorkspaceLocation(Properties p, String key) {
		String newValue = p.getProperty(key).replace(PLATFORM_TOKEN, Platform.getLocation().toOSString());
		p.setProperty(key, newValue);
	}

}