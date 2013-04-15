package org.imogene.studio.contrib.ui.navigator;

import org.eclipse.core.resources.IProject;
import org.imogene.studio.contrib.ImogeneStudioPlugin;


public class NotifierShadow extends AbstractShadow {

	public static final String TYPE = "notif"; //$NON-NLS-1$
	
	public static final String NATURE="org.imogene.nature.gen.notif"; //$NON-NLS-1$
	
	public NotifierShadow(IProject parent){
		super(parent, TYPE);
		setLabel("Notifier"); //$NON-NLS-1$
		setIcon(ImogeneStudioPlugin.getImageDescriptor("icons/bell.png") //$NON-NLS-1$
				.createImage());
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
