package org.imogene.studio.contrib.ui.navigator;

import org.eclipse.core.resources.IProject;
import org.imogene.studio.contrib.ImogeneStudioPlugin;

public class ClientShadow extends AbstractShadow {

	public static final String TYPE = "client";

	public static final String NATURE = "org.imogene.nature.gen.client";

	public ClientShadow(IProject parent) {
		super(parent, TYPE);
		setLabel("Client");
		setIcon(ImogeneStudioPlugin.getImageDescriptor("icons/serverSynchro.png").createImage());
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
