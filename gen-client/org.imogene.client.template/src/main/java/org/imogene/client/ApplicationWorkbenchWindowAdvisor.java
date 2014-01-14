package org.imogene.client;

import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.imogene.client.i18n.Messages;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	@Override
	public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
		return new ApplicationActionBarAdvisor(configurer);
	}

	@Override
	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		// configurer.setInitialSize(new Point(400, 300));
		configurer.setShowCoolBar(true);
		// XXX Set the status line and progress indicator so that update
		// information can be shown there
		configurer.setShowStatusLine(true);
		configurer.setShowProgressIndicator(true);
		configurer.setTitle(Messages.application_title);

		startBundle("org.imogene.rcp.initializer");
		startBundle("org.imogene.rcp.derby");
		startBundle("org.imogene.rcp.jetty");
		startBundle("org.imogene.sync.client.<client>"); //TODO Replace with the correct plugin
	}

	private void startBundle(String name) {
		final Bundle bundle = Platform.getBundle(name);
		if (bundle != null && (bundle.getState() & Bundle.ACTIVE) == 0) {
			try {
				bundle.start(Bundle.START_TRANSIENT);
			} catch (BundleException e) {
				e.printStackTrace();
			}
		}
	}

}
