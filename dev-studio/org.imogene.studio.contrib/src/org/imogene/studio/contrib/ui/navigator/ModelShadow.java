package org.imogene.studio.contrib.ui.navigator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.imogene.studio.contrib.ImogeneStudioPlugin;


public class ModelShadow extends AbstractShadow {

	public static final String TYPE="model"; //$NON-NLS-1$
	
	public ModelShadow(IProject parent){
		super(parent, TYPE);
		setLabel("Model"); //$NON-NLS-1$
		setIcon(ImogeneStudioPlugin.getImageDescriptor("icons/Diagram.gif") //$NON-NLS-1$
				.createImage());
	}
	
	@Override
	public Object[] getChildren() {
		try {
			return parent.members();
		} catch (CoreException e) {	
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean hasChildren() {
		return true;
	}

}
