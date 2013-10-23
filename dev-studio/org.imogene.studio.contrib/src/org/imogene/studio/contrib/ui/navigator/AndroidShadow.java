package org.imogene.studio.contrib.ui.navigator;

import org.eclipse.core.resources.IProject;
import org.imogene.studio.contrib.ImogeneStudioPlugin;


public class AndroidShadow extends AbstractShadow {

	public static final String TYPE = "android"; //$NON-NLS-1$
	
	public static final String NATURE="org.imogene.nature.gen.android"; //$NON-NLS-1$
	
	public AndroidShadow(IProject parent){
		super(parent, TYPE);
		setLabel("Android"); //$NON-NLS-1$
		setIcon(ImogeneStudioPlugin.getImageDescriptor(
			"icons/android_16.png").createImage()); //$NON-NLS-1$
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
