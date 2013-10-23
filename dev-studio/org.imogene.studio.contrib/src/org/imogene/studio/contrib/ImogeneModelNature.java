package org.imogene.studio.contrib;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

public class ImogeneModelNature implements IProjectNature {
	
	public final static String ID = "org.imogene.studio.modelnature"; //$NON-NLS-1$
	
	public final static String FILE_EXT = "IMOG"; //$NON-NLS-1$

	IProject _project;

	public void configure() throws CoreException {
	}

	public void deconfigure() throws CoreException {
	}

	public IProject getProject() {
		return _project;
	}

	public void setProject(IProject project) {
		_project = project;
	}

	/**
	 * 
	 * @return true if that project contains a imogene Model file
	 */
	public boolean containsModelFile() {
		return getModelFile() != null;
	}

	public IFolder getIconsFolder() {
		try {
			/*
			 * return
			 * getProject().getFolder(getProject().getPersistentProperty((new
			 * QualifiedName("", "ICON_PATH")))) ;
			 */
			return getProject().getFolder("icons"); //$NON-NLS-1$
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Retrieves the (first) imogene model file contained in the project having
	 * that nature
	 * 
	 * @return the <code>IFile</code> corresponding to the imogene model ; null
	 *         if no model file found
	 */
	public IFile getModelFile() {
		IResource[] members;
		try {
			members = getProject().members();
			IFile res = null;
			boolean found = false;
			for (int i = 0; i < members.length && !found; i++) {
				IResource resource = members[i];
				if (resource instanceof IFile)
					if (((IFile) resource).getFileExtension().toUpperCase()
							.equals(FILE_EXT)) {
						res = (IFile) resource;
						found = true;
					}
			}
			return res;
		} catch (CoreException e) {
			return null;
		}
	}

	/**
	 * @return The model name without extension (ex: <cod>myModel</code> for a
	 *         model whose full path is <code>/home/toto/myModel.imog</code>
	 */
	public String getModelName() {
		return containsModelFile() ? getModelFile().getName().split("\\.")[0] //$NON-NLS-1$
				: ""; //$NON-NLS-1$
	}

	/**
	 * This method copy workflow and metamodel files into the project (as hidden
	 * files)
	 * 
	 */
	public void createWorkflowAndMetaModelFile() {

	}
}
