package org.imogene.library;

import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class LibraryPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.imogene.library";

	// The shared instance
	private static LibraryPlugin plugin;
	
	/**
	 * The constructor
	 */
	public LibraryPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static LibraryPlugin getDefault() {
		return plugin;
	}
	
	public List<LibraryDesc> getAvailableLibraries(){
		List<LibraryDesc> libs = new ArrayList<LibraryDesc>();
		Enumeration<URL> entries = getBundle().findEntries("libs", "*.xml", false);
		while(entries.hasMoreElements()){
			URL e = entries.nextElement();
			libs.add(Tools.getLibraryDesc(e));
		}
		return libs;
	}

}
