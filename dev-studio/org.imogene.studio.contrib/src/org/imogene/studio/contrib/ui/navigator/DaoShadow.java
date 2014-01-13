package org.imogene.studio.contrib.ui.navigator;

import org.eclipse.core.resources.IProject;
import org.imogene.studio.contrib.ImogeneStudioPlugin;

public class DaoShadow extends AbstractShadow {
	
	public static final String TYPE = "dao"; //$NON-NLS-1$
	
	public static final String NATURE="org.imogene.nature.gen.dao"; //$NON-NLS-1$
	
	public DaoShadow(IProject parent){
		super(parent, TYPE);
		setLabel("Dao"); //$NON-NLS-1$
		setIcon(ImogeneStudioPlugin.getImageDescriptor("icons/dao_24.png") //$NON-NLS-1$
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
