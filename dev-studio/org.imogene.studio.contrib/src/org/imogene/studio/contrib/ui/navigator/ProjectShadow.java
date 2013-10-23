package org.imogene.studio.contrib.ui.navigator;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

/**
 * Default shadow project that presents all the generated projects for this
 * element.
 * 
 * @author Medes-IMPS
 */
public class ProjectShadow {

	public String label;
	public IProject parent;

	public ProjectShadow(String label, IProject parent) {
		this.label = label;
		this.parent = parent;
	}

	public Object[] getChildren() {

		if (label.equals("Generated Projects")) { //$NON-NLS-1$
			ProjectShadow[] pp = new ProjectShadow[6];
			pp[0] = new ProjectShadow("Init", this.parent); //$NON-NLS-1$
			pp[1] = new ProjectShadow("Web", this.parent); //$NON-NLS-1$
			pp[2] = new ProjectShadow("Synchro", this.parent); //$NON-NLS-1$
			pp[3] = new ProjectShadow("RCP", this.parent); //$NON-NLS-1$
			pp[4] = new ProjectShadow("Android", this.parent); //$NON-NLS-1$
			pp[5] = new ProjectShadow("Admin", this.parent); //$NON-NLS-1$
			return pp;
		}

		else if (label.equals("Model")) { //$NON-NLS-1$
			try {
				return parent.members();
			} catch (CoreException e) {	
				System.err.println(e.getMessage());
				e.printStackTrace();
			}

		} else {
			ArrayList<IProject> l = getGeneratedProjects();
			if (!l.isEmpty()) {
				IProject[] tab = (IProject[]) l.toArray(new IProject[l.size()]);				
				return tab;
			} else {

				ProjectShadow[] pp = new ProjectShadow[1];
				pp[0] = new ProjectShadow("No generated project", null); //$NON-NLS-1$
				return pp;
			}
		}
		return null;
	}

	public boolean hasChildren() {
		return label.equals("Generated Projects") || label.equals("Model") //$NON-NLS-1$ //$NON-NLS-2$
				|| label.equals("Web") || label.equals("Synchro") //$NON-NLS-1$ //$NON-NLS-2$
				|| label.equals("RCP") || label.equals("Android") //$NON-NLS-1$ //$NON-NLS-2$
				|| label.equals("Init") || label.equals("Admin"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Get the generated project for ... TO BE COMPLETED ...
	 * 
	 * @return all the generated projects.
	 */
	private ArrayList<IProject> getGeneratedProjects() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject[] f = workspace.getRoot().getProjects();

		try {
			int i = 0;
			IResource[] members = parent.members();
			while (i < members.length
					&& !members[i].getName().endsWith("imog")) { //$NON-NLS-1$
				i++;
			}

			String modelName = (members[i].getName().split("\\."))[0];			 //$NON-NLS-1$

			ArrayList<IProject> web = new ArrayList<IProject>();
			ArrayList<IProject> sync = new ArrayList<IProject>();
			ArrayList<IProject> rcp = new ArrayList<IProject>();
			ArrayList<IProject> andro = new ArrayList<IProject>();
			ArrayList<IProject> init = new ArrayList<IProject>();
			ArrayList<IProject> admin = new ArrayList<IProject>();

			for (int i1 = 0; i1 < f.length; i1++) {
				if (f[i1].isAccessible()
						&& f[i1].hasNature("org.imogene.nature.model." //$NON-NLS-1$
								+ modelName)) {

					if (label.equals("Web")) { //$NON-NLS-1$
						if (f[i1].hasNature("org.imogene.nature.gen.gwt")) { //$NON-NLS-1$
							web.add(f[i1]);
						}
					}

					else if (label.equals("Synchro")) { //$NON-NLS-1$
						if (f[i1].hasNature("org.imogene.nature.gen.sync")) { //$NON-NLS-1$
							sync.add(f[i1]);
						}
					}

					else if (label.equals("RCP")) { //$NON-NLS-1$
						if (f[i1].hasNature("org.imogene.nature.gen.rcp")) { //$NON-NLS-1$
							rcp.add(f[i1]);
						}
					}

					else if (label.equals("Android")) { //$NON-NLS-1$
						if (f[i1]
								.hasNature("org.imogene.nature.gen.android")) { //$NON-NLS-1$
							andro.add(f[i1]);
						}
					}

					else if (label.equals("Init")) { //$NON-NLS-1$
						if (f[i1].hasNature("org.imogene.nature.gen.init")) { //$NON-NLS-1$
							init.add(f[i1]);
						}
					}

					else if (label.equals("Admin")) { //$NON-NLS-1$
						if (f[i1].hasNature("org.imogene.nature.gen.admin")) { //$NON-NLS-1$
							admin.add(f[i1]);
						}
					}

				}
			}

			if (label.equals("Web")) { //$NON-NLS-1$
				return web;
			} else if (label.equals("Synchro")) { //$NON-NLS-1$
				return sync;
			} else if (label.equals("RCP")) { //$NON-NLS-1$
				return rcp;
			} else if (label.equals("Android")) { //$NON-NLS-1$
				return andro;
			} else if (label.equals("Init")) { //$NON-NLS-1$
				return init;
			} else if (label.equals("Admin")) { //$NON-NLS-1$
				return admin;
			}

		} catch (CoreException e) {		
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

		return null;

	}

}
