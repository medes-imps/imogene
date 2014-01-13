package org.imogene.studio.contrib.ui.navigator;

import org.eclipse.core.resources.IProject;
import org.imogene.studio.contrib.ImogeneStudioPlugin;


public class WebShadow extends AbstractShadow {

	public static final String TYPE = "web"; //$NON-NLS-1$
	
	public static final String NATURE="org.imogene.nature.gen.gwt"; //$NON-NLS-1$
	
	public WebShadow(IProject parent){
		super(parent, TYPE);
		setLabel("Web"); //$NON-NLS-1$
		setIcon(ImogeneStudioPlugin.getImageDescriptor("icons/gwtLogo.png") //$NON-NLS-1$
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
