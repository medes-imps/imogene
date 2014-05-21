package org.imogene.client;

import java.util.logging.Logger;

import org.apache.commons.httpclient.protocol.Protocol;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.imogene.client.core.init.Initializer;
import org.imogene.client.core.jetty.JettyServer;
import org.imogene.client.ssl.EasySSLProtocolSocketFactory;
import org.osgi.framework.BundleContext;

public class Activator extends AbstractUIPlugin {

	private static final Logger logger = Logger.getLogger(Activator.class.getName());

	public static final String PLUGIN_ID = "org.imogene.client.%modelName%%"; //$NON-NLS-1$

	private static Activator plugin;

	private final Initializer initializer = new Initializer();
	private final JettyServer jettyServer = new JettyServer();

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

		logger.info("Starting Jetty server");
		jettyServer.start();

		logger.info("Starting Synchronization"); //$NON-NLS-1$

		Protocol lEasyHttps = new Protocol("https", new EasySSLProtocolSocketFactory(), 443); //$NON-NLS-1$
		Protocol.registerProtocol("https", lEasyHttps); //$NON-NLS-1$
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		jettyServer.stop();
		plugin = null;
		super.stop(context);
	}

}