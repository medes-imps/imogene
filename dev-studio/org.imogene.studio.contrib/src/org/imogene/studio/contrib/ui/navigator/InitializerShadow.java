package org.imogene.studio.contrib.ui.navigator;

import org.eclipse.core.resources.IProject;
import org.imogene.studio.contrib.ImogeneStudioPlugin;


public class InitializerShadow extends AbstractShadow {

	public static final String TYPE = "init"; //$NON-NLS-1$
	
	public static final String NATURE="org.imogene.nature.gen.init"; //$NON-NLS-1$
	
	public InitializerShadow(IProject parent){
		super(parent, TYPE);
		setLabel("Initializer"); //$NON-NLS-1$
		setIcon(ImogeneStudioPlugin.getImageDescriptor(
			"icons/init.png").createImage()); //$NON-NLS-1$
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
