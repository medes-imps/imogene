package org.imogene.client.dashboard;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class DashboardPerspective implements IPerspectiveFactory {

	public static final String ID = "org.imogene.client.dashboard.DashboardPerspective"; //$NON-NLS-1$

	@Override
	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
		layout.setFixed(true);
	}

}
