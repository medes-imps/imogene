package org.imogene.studio.contrib.ui.navigator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.graphics.Image;
import org.imogene.studio.contrib.ImogeneStudioPlugin;


public abstract class AbstractShadow implements IShadow {
	
	public static final String NATURE_PREFIX = "org.imogene.nature.model."; //$NON-NLS-1$
	
	protected String type;
	
	protected String label=new String("Label not set"); //$NON-NLS-1$
	
	protected IProject parent;
	
	private Image icon ;
	
	public AbstractShadow(IProject parent, String type){
		this.parent = parent;
		this.type = type;
		icon = ImogeneStudioPlugin.getImageDescriptor("icons/Problem.gif").createImage(); //$NON-NLS-1$
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public IProject getParent(){
		return parent;
	}
	
	public void setParent(IProject parent){
		this.parent = parent;
	}
	
	public Image getIcon() {
		return icon;
	}

	public void setIcon(Image img) {
		this.icon = img;
	}

	protected List<Object> getProjects(String natureFilter) {
		ArrayList<Object> generateds = new ArrayList<Object>();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject[] projects = workspace.getRoot().getProjects();

		try {
			int i = 0;
			IResource[] members = parent.members();
			while (i < members.length
					&& !members[i].getName().endsWith("imog")) { //$NON-NLS-1$
				i++;
			}

			String modelName = (members[i].getName().split("\\."))[0]; //$NON-NLS-1$
			for (int p = 0; p < projects.length; p++) {
				IProject project = projects[p];
				if (project.isAccessible()
						&& project.hasNature(NATURE_PREFIX + modelName)
						&& project.hasNature(natureFilter)) {
					generateds.add(project);
				}
			}
		} catch (CoreException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		if(generateds.size()>0)
			return generateds;
		generateds.add(new EmptyShadow(parent));
		return generateds;
	}
		
}
