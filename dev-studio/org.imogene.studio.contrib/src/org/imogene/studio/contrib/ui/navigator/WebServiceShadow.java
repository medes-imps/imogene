package org.imogene.studio.contrib.ui.navigator;

import org.eclipse.core.resources.IProject;
import org.imogene.studio.contrib.ImogeneStudioPlugin;


public class WebServiceShadow extends AbstractShadow {

	public static final String TYPE = "ws"; //$NON-NLS-1$
	
	public static final String NATURE="org.imogene.nature.gen.ws"; //$NON-NLS-1$
	
	public WebServiceShadow(IProject parent){
		super(parent, TYPE);
		setLabel("WebService"); //$NON-NLS-1$
		setIcon(ImogeneStudioPlugin.getImageDescriptor(
				"icons/serverSynchro.png").createImage()); //$NON-NLS-1$
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
