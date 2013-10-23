package org.imogene.studio.contrib.ui.navigator;

import org.eclipse.core.resources.IProject;
import org.imogene.studio.contrib.ImogeneStudioPlugin;


public class RcpShadow extends AbstractShadow {

	public static final String TYPE = "rcp"; //$NON-NLS-1$
	
	public static final String NATURE="org.imogene.nature.gen.rcp"; //$NON-NLS-1$
	
	public RcpShadow(IProject parent){
		super(parent, TYPE);
		setLabel("Desktop(RCP)"); //$NON-NLS-1$
		setIcon(ImogeneStudioPlugin.getImageDescriptor(
			"icons/eclipse.png").createImage()); //$NON-NLS-1$
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
