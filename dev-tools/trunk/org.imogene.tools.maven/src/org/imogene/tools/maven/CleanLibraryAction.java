package org.imogene.tools.maven;

import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.imogene.tools.maven.internal.LibraryUtils;

public class CleanLibraryAction implements IObjectActionDelegate {
	
	private IProject project;

	@Override
	public void run(IAction action) {		
		IFolder libs = project.getFolder(LibraryUtils.LIBS_FOLDER);		
		if(libs.exists()){
			IFolder jars = libs.getFolder(LibraryUtils.JARS_FOLDER);
			if(jars.exists()){
				try {
					Set<String> jarsNames = LibraryUtils.getAllLibraries(libs);
					LibraryUtils.cleaUnusedJar(jars, jarsNames);
				} catch (CoreException e) {
					e.printStackTrace();
				}				
			}
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection s) {		
		IStructuredSelection selection = (IStructuredSelection) s;
        project = (IProject) (selection.getFirstElement()); 
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {				
	}

	
}
