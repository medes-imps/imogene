package org.imogene.rcp.container;

import java.util.logging.Logger;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.imogene.rcp.container.bugreport.BugReportPreferencePage;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {
	
	private static final Logger logger = Logger.getLogger(Activator.class.getName());

	// The plug-in ID
	public static final String PLUGIN_ID = "org.imogene.rcp.container"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	// XXX Shared instance of bundle context
	public static BundleContext bundleContext;
	
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		logger.info("Starting container"); //$NON-NLS-1$
		plugin = this;
		bundleContext = context;
		BugReportPreferencePage.initPreferences();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
}
