package org.imogene.rcp.container.dashboard;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class DashboardPerspective implements IPerspectiveFactory {

	public static final String ID = "org.imogene.rcp.container.dashboard.DashboardPerspective"; //$NON-NLS-1$

	@Override
	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
		layout.setFixed(true);
	}

}
