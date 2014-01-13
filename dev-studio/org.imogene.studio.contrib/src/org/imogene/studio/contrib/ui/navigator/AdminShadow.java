package org.imogene.studio.contrib.ui.navigator;

import org.eclipse.core.resources.IProject;
import org.imogene.studio.contrib.ImogeneStudioPlugin;


public class AdminShadow extends AbstractShadow {

	public static final String TYPE = "admin"; //$NON-NLS-1$
	
	public static final String NATURE="org.imogene.nature.gen.admin"; //$NON-NLS-1$
	
	public AdminShadow(IProject parent){
		super(parent, TYPE);
		setLabel("Admin"); //$NON-NLS-1$
		setIcon(ImogeneStudioPlugin.getImageDescriptor(
				"icons/admin.png").createImage()); //$NON-NLS-1$
	}
	
	@Override
	public Object[] getChildren() {
		return getProjects(NATURE).toArray();
	}

	@Override
	public boolean hasChildren() {
		return true;
	}
	

}
