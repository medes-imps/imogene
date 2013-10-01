package org.imogene.rcp.container;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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

	private static final String DEFAULT_PATH = "/var/extbcam.d"; //$NON-NLS-1$

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
			display.dispose();
		}
	}

	private Object checkInstanceLocation() {
		File file = new File(DEFAULT_PATH);
		if (file.exists()) {
			Location loc = Platform.getInstanceLocation();
			if (loc != null && !loc.isSet()) {
				String path = file.getAbsolutePath().replace(File.separatorChar, '/');
				try {
					URL url = new URL("file", null, path); //$NON-NLS-1$
					loc.set(url, true);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
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
