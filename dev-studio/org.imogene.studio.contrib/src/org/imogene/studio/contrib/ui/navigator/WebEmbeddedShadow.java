package org.imogene.studio.contrib.ui.navigator;

import org.eclipse.core.resources.IProject;
import org.imogene.studio.contrib.ImogeneStudioPlugin;

public class WebEmbeddedShadow extends AbstractShadow {

	public static final String TYPE = "web-embedded";

	public static final String NATURE = "org.imogene.nature.gen.web.embedded";

	public WebEmbeddedShadow(IProject parent) {
		super(parent, TYPE);
		setLabel("Web Embedded");
		setIcon(ImogeneStudioPlugin.getImageDescriptor("icons/gwtLogo.png").createImage());
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
