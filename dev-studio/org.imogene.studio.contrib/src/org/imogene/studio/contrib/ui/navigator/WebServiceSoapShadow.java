package org.imogene.studio.contrib.ui.navigator;

import org.eclipse.core.resources.IProject;
import org.imogene.studio.contrib.ImogeneStudioPlugin;


public class WebServiceSoapShadow extends AbstractShadow {

	public static final String TYPE = "ws_soap"; //$NON-NLS-1$
	
	public static final String NATURE="org.imogene.nature.gen.ws.soap"; //$NON-NLS-1$
	
	public WebServiceSoapShadow(IProject parent){
		super(parent, TYPE);
		setLabel("WebServiceSoap"); //$NON-NLS-1$
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
