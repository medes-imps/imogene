package org.imogene.admin.contrib;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.imogene.studio.contrib.ui.generation.GenerationWizard;
import org.imogene.web.contrib.SpecificWizardPage;
import org.imogene.web.contrib.task.ClasspathCopyTask;
import org.imogene.web.contrib.task.WebIconCopyTask;

public class WebGenerationAction implements IObjectActionDelegate {

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
			SpecificWizardPage specificWizardPage = new SpecificWizardPage();
			specificWizardPage.setTitle("Admin application properties");
			specificWizardPage.setDescription("Specific properties for the admin generation process.");

			GenerationWizard wizard = new GenerationWizard();
			wizard.addPropertiesPage(specificWizardPage);
			wizard.setWindowTitle("Generation of an Admin project");
			wizard.setSelectedProject(mSelectedProject);
			wizard.setArchive(FileLocator.openStream(org.imogene.web.contrib.Activator.getDefault().getBundle(),
					new Path("template-site/template.zip"), false));
			wizard.setDefinition(FileLocator.openStream(Activator.getDefault().getBundle(), new Path(
					"template-site/templates.xml"), false));
			wizard.setWorkflow("workflow/generatorAdmin.mwe");
			wizard.addPostGenerationTask(new WebIconCopyTask());
			wizard.addPostGenerationTask(new ClasspathCopyTask());
			// wizard.addPostGenerationTask(new AddSuperDevModeLaunchConfigurationTask());
			fillTheList(wizard);
			WizardDialog dialog = new WizardDialog(Display.getCurrent().getActiveShell(), wizard);
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

	/**
	 * Fill the workflow list by reading the dedicated extension point.
	 */
	private void fillTheList(GenerationWizard wizard) {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = registry.getExtensionPoint("org.imogene.admin.contrib.specificworkflow");
		if (extensionPoint != null) {
			IExtension[] extensions = extensionPoint.getExtensions();
			for (IExtension extension : extensions) {
				for (IConfigurationElement element : extension.getConfigurationElements()) {
					wizard.addSpecificWorkflow(element.getAttribute("name"), element.getAttribute("path"));
				}
			}
		}
	}

}
