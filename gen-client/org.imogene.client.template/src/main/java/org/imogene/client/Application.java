package org.imogene.client;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import org.eclipse.core.runtime.Platform;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {

	private static final Logger logger = Logger.getLogger(Application.class.getName());

	private static final String INSTANCE_PATH = "/opt/imogene.d/data"; //$NON-NLS-1$

	@Override
	public Object start(IApplicationContext context) throws Exception {
		Display display = PlatformUI.createDisplay();
		try {
			Object instanceLocationCheck = checkInstanceLocation();
			if (instanceLocationCheck != null) {
				return instanceLocationCheck;
			}
			int returnCode = PlatformUI.createAndRunWorkbench(display, new ApplicationWorkbenchAdvisor());
			if (returnCode == PlatformUI.RETURN_RESTART) {
				return IApplication.EXIT_RESTART;
			} else {
				return IApplication.EXIT_OK;
			}
		} finally {
			if (display != null) {
				display.dispose();
			}
			Location instanceLoc = Platform.getInstanceLocation();
			if (instanceLoc != null) {
				instanceLoc.release();
			}
		}
	}

	private Object checkInstanceLocation() {
		File workspace = new File(INSTANCE_PATH);
		if (!workspace.canRead() || !workspace.canWrite()) {
			logger.info("No pre-installed workspace"); //$NON-NLS-1$
			return null;
		}
		if (workspace != null && workspace.exists()) {
			Location instanceLoc = Platform.getInstanceLocation();
			if (!instanceLoc.isSet()) {
				try {
					// Don't use File.toURL() since it adds a leading slash that Platform does not
					// handle properly. See bug 54081 for more details.
					String path = workspace.getAbsolutePath().replace(File.separatorChar, '/');
					URL url = new URL("file", null, path); //$NON-NLS-1$
					instanceLoc.set(url, true);
					logger.info("Resuing pre-installed workspace"); //$NON-NLS-1$
					return null;
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		logger.info("Unable to reuse pre-installed workspace"); //$NON-NLS-1$
		return null;
	}

	@Override
	public void stop() {
		if (!PlatformUI.isWorkbenchRunning())
			return;
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final Display display = workbench.getDisplay();
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				if (!display.isDisposed())
					workbench.close();
			}
		});
	}
}
