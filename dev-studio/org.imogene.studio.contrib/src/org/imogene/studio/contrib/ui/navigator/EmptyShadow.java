package org.imogene.studio.contrib.ui.navigator;

import org.eclipse.core.resources.IProject;
import org.imogene.studio.contrib.ImogeneStudioPlugin;


public class EmptyShadow extends AbstractShadow {

	public static final String TYPE = "empty"; //$NON-NLS-1$
	
	public EmptyShadow(IProject parent){
		super(parent, TYPE);
		setLabel("No generated projects"); //$NON-NLS-1$
		setIcon(ImogeneStudioPlugin.getImageDescriptor(
				"icons/Green pin.gif").createImage()); //$NON-NLS-1$
	}
	
	@Override
	public Object[] getChildren() {
		return null;
	}

	@Override
	public boolean hasChildren() {
		return false;
	}
	

}
