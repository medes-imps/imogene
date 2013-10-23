package org.imogene.dao.contrib;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.imogene.studio.contrib.ui.generation.GenerationWizard;

public class DaoGenerationAction implements IObjectActionDelegate {

	// Store the current model resource selection
	protected IStructuredSelection mSelection;

	// Store the currently selected project
	protected IProject mSelectedProject;

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run(IAction action) {
		try {
			GenerationWizard wizard = new GenerationWizard();
			wizard.setWindowTitle("Generation of a Web project");
			wizard.setSelectedProject(mSelectedProject);
			wizard.setArchive(FileLocator
					.openStream(Activator.getDefault().getBundle(), new Path(
							"template-site/template.zip"), false));
			wizard.setDefinition(FileLocator.openStream(Activator.getDefault()
					.getBundle(), new Path("template-site/templates.xml"),
					false));
			wizard.setWorkflow("workflow/generatorDao.mwe");
			WizardDialog dialog = new WizardDialog(Display.getCurrent()
					.getActiveShell(), wizard);
			dialog.open();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		mSelection = (IStructuredSelection) selection;
		mSelectedProject = (IProject) (mSelection).getFirstElement();
	}

}
