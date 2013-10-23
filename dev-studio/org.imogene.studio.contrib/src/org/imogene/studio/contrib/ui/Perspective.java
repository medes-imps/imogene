package org.imogene.studio.contrib.ui;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IPerspectiveFactory#createInitialLayout(org.eclipse.ui.IPageLayout)
	 */
	public void createInitialLayout(IPageLayout layout) {		
		layout.setEditorAreaVisible(true);		

		/*IFolderLayout topLeft = layout.createFolder("topLeft",
				IPageLayout.LEFT, 0.25f, editorArea);
		topLeft.addView("org.imogene.studio.contrib.navigator");	*/
		/*
		topLeft.addView(viewId) */
	}
	
}
