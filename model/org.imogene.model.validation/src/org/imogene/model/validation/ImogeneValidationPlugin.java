/**
 * 
 */

package org.imogene.model.validation;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class ImogeneValidationPlugin extends AbstractUIPlugin {

	//The shared instance.
	private static ImogeneValidationPlugin plugin;

	/**
	 * The constructor.
	 */
	public ImogeneValidationPlugin() {
		super();
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 */
	public static ImogeneValidationPlugin getDefault() {
		return plugin;
	}
}
