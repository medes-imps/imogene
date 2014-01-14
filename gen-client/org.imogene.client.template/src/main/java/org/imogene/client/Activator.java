package org.imogene.client;

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
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.imogene.client.config.MyPropertyPlaceholderConfigurer;
import org.imogene.client.core.derby.DerbyServer;
import org.imogene.client.core.init.Initializer;
import org.imogene.client.core.jetty.JettyServer;
import org.imogene.client.jobs.AuthJob;
import org.imogene.client.jobs.NTPOffsetJob;
import org.imogene.client.jobs.SyncJob;
import org.imogene.client.jobs.Synchronizer;
import org.imogene.client.ssl.EasySSLProtocolSocketFactory;
import org.imogene.client.ui.AuthenticationDialog;
import org.imogene.client.ui.ISyncConstants;
import org.osgi.framework.BundleContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Activator extends AbstractUIPlugin implements IPropertyChangeListener {

	private static final Logger logger = Logger.getLogger(Activator.class.getName());

	private static final String PLATFORM_TOKEN = "%PLATFORM%"; //$NON-NLS-1$
	private static final String JDBC_URL_PARAM = "jdbc.url"; //$NON-NLS-1$
	private static final String SYNC_DIR_PARAM = "sync.file.directory"; //$NON-NLS-1$

	public static final String PLUGIN_ID = "org.imogene.client"; //$NON-NLS-1$

	private static Activator plugin;

	private final Initializer initializer = new Initializer();
	private final JettyServer jettyServer = new JettyServer();
	private final DerbyServer derbyServer = new DerbyServer();

	private Synchronizer synchronizer;
	private SyncJob syncJob;
	private NTPOffsetJob ntpOffsetJob;

	/**
	 * Returns the shared instance.
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in relative path
	 * 
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		logger.info("Initialize properties");
		initializer.init();

		logger.info("Starting Derby network control server");
		derbyServer.start();

		logger.info("Starting Jetty server");
		jettyServer.start();

		logger.info("Starting Synchronization"); //$NON-NLS-1$

		DOMConfigurator.configure(getClass().getResource("/log4j.xml")); //$NON-NLS-1$

		Protocol lEasyHttps = new Protocol("https", new EasySSLProtocolSocketFactory(), 443); //$NON-NLS-1$
		Protocol.registerProtocol("https", lEasyHttps); //$NON-NLS-1$

		MyPropertyPlaceholderConfigurer.getInstance().setProperties(getSynchronizerProperties());
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"application-context.xml", "dao-context.xml", //$NON-NLS-1$ //$NON-NLS-2$
				"converter-context.xml"); //$NON-NLS-1$

		synchronizer = (Synchronizer) ctx.getBean("synchronizer"); //$NON-NLS-1$

		IPreferenceStore prefs = getPreferenceStore();
		prefs.addPropertyChangeListener(this);
		long period = prefs.getLong(ISyncConstants.SYNC_PERIOD);
		boolean auto = prefs.getBoolean(ISyncConstants.SYNC_AUTO);
		boolean logged = prefs.contains(ISyncConstants.SYNC_LOGIN);
		long rate = prefs.getLong(ISyncConstants.NTP_RATE);
		String host = prefs.getString(ISyncConstants.NTP_HOST);
		ensureSyncJob(period * ISyncConstants.MINUTE_IN_MILLIS, auto && logged);
		ensureNTPOffsetJob(host, rate * ISyncConstants.MINUTE_IN_MILLIS);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		if (!syncJob.cancel()) {
			syncJob.join();
		}
		syncJob = null;

		if (!ntpOffsetJob.cancel()) {
			ntpOffsetJob.join();
		}
		ntpOffsetJob = null;

		jettyServer.stop();
		derbyServer.stop();

		plugin = null;
		super.stop(context);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (ISyncConstants.SYNC_AUTO.equals(event.getProperty())) {
			syncJob.setEnabled((Boolean) event.getNewValue());
		} else if (ISyncConstants.SYNC_PERIOD.equals(event.getProperty())) {
			long period = Long.parseLong((String) event.getNewValue());
			syncJob.setPeriod(period * ISyncConstants.MINUTE_IN_MILLIS);
		} else if (ISyncConstants.NTP_HOST.equals(event.getProperty())) {
			ntpOffsetJob.setHost((String) event.getNewValue());
		} else if (ISyncConstants.NTP_RATE.equals(event.getProperty())) {
			long rate = Long.parseLong((String) event.getNewValue());
			ntpOffsetJob.setRate(rate * ISyncConstants.MINUTE_IN_MILLIS);
		} else if (ISyncConstants.NTP_OFFSET.equals(event.getProperty())) {
			// IPreferenceStore preferences = SyncActivator.getDefault().getPreferenceStore();
			// long offset = preferences.getLong(ISyncConstants.NTP_OFFSET);
			// SyncActivator.getDefault().setOffset(offset);
		}
	}

	/**
	 * Start the synchronization for the given period.
	 */
	public void start() {
		ensureAuthenticated(new Runnable() {
			@Override
			public void run() {
				getPreferenceStore().setValue(ISyncConstants.SYNC_AUTO, true);
			}
		});
	}

	/**
	 * Stop the synchronization.
	 */
	public void stop() {
		ensureAuthenticated(new Runnable() {
			@Override
			public void run() {
				getPreferenceStore().setValue(ISyncConstants.SYNC_AUTO, false);
			}
		});
	}

	/**
	 * Perform a one shot synchronization. If the synchronization is on loop mode it will be reschedule at the end of
	 * the process for the given period.
	 */
	public void synchronize() {
		ensureAuthenticated(new Runnable() {
			@Override
			public void run() {
				syncJob.synchronize();
			}
		});
	}

	/**
	 * Ensure the synchronization user has been authenticated before performing the given action.
	 * 
	 * @param runnable The action to perform if the user is authenticated or after the authentication succeed
	 */
	public void ensureAuthenticated(final Runnable runnable) {
		final IPreferenceStore prefs = getPreferenceStore();
		if (!prefs.contains(ISyncConstants.SYNC_LOGIN)) {
			authenticate(runnable);
		} else {
			runnable.run();
		}
	}

	/**
	 * Authenticate a user and launch the given action once the authentication is successful.
	 * 
	 * @param runnable The action to perform after the authentication succeed
	 */
	public void authenticate(final Runnable runnable) {
		final IPreferenceStore prefs = getPreferenceStore();
		AuthenticationDialog dialog = new AuthenticationDialog(Display.getDefault().getActiveShell());
		dialog.create();
		if (dialog.open() == Window.OK) {
			final String url = prefs.getString(ISyncConstants.SYNC_URL);
			final String login = dialog.getLogin();
			final String password = dialog.getPassword();
			AuthJob authJob = new AuthJob();
			authJob.setUser(true);
			authJob.setSynchronizer(synchronizer);
			authJob.setUrl(url);
			authJob.setLogin(login);
			authJob.setPassword(password);
			authJob.addJobChangeListener(new JobChangeAdapter() {
				@Override
				public void done(IJobChangeEvent event) {
					if (event.getResult().getSeverity() == Status.OK) {
						prefs.setValue(ISyncConstants.SYNC_LOGIN, login);
						prefs.setValue(ISyncConstants.SYNC_PASSWORD, password);
						if (runnable != null) {
							runnable.run();
						}
					} else {
						authenticate(runnable);
					}
				}
			});
			authJob.schedule();
		}
	}

	private void ensureSyncJob(long period, boolean enabled) {
		syncJob = new SyncJob();
		syncJob.setPeriod(period);
		syncJob.setEnabled(enabled);
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

	private void ensureNTPOffsetJob(String host, long rate) {
		ntpOffsetJob = new NTPOffsetJob();
		ntpOffsetJob.setHost(host);
		ntpOffsetJob.setRate(rate);
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