package org.imogene.tools.maven;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.imogene.tools.maven.ui.UpdateLibraryWizard;

public class UpdateLibrayAction implements IObjectActionDelegate {
	
	private IProject project;
	
	@Override
	public void run(IAction arg0) {
		UpdateLibraryWizard wizard = new UpdateLibraryWizard(project);
		WizardDialog dialog = new WizardDialog(Display.getCurrent().getActiveShell(), wizard);
		dialog.open();
	}

	@Override
	public void selectionChanged(IAction action, ISelection s) {
		 	IStructuredSelection selection = (IStructuredSelection) s;
	        project = (IProject) (selection.getFirstElement()); 	       
	}

	@Override
	public void setActivePart(IAction arg0, IWorkbenchPart arg1) {
		
	}	
}
