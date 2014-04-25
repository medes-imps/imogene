package org.imogene.studio.contrib.ui.generation;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.wizard.Wizard;
import org.imogene.model.core.Project;
import org.imogene.oaw.generator.common.OawGeneratorMedooCommon;
import org.imogene.studio.contrib.ImogeneModelNature;
import org.imogene.studio.contrib.action.GenerateJob;
import org.imogene.studio.contrib.interfaces.PostGenerationTask;
import org.imogene.studio.contrib.properties.GenerationPropertiesPage;
import org.imogene.studio.contrib.properties.GenerationTestPropertiesPage;

public class GenerationWizard extends Wizard {

	private List<PostGenerationTask> mPostGenerationTasks = null;

	private IProject selectedProject;

	private String workflowPath;

	private String projectName;

	private InputStream archive;

	private InputStream definition;

	private CommonGenerationWizardPage commonPage;

	private List<GenerationWizardPage> pages = new ArrayList<GenerationWizardPage>();

	public GenerationWizard() {
		super();
		commonPage = new CommonGenerationWizardPage(Messages.GenerationWizard_0);
		addPage(commonPage);
	}

	@Override
	public boolean performFinish() {

		/* workflow path */
		String workflow = commonPage.getSpecificWorkflow();
		if (workflow == null)
			workflow = workflowPath;

		/* project name */
		projectName = commonPage.getProjectName();

		/* set properties */
		HashMap<String, String> properties = new HashMap<String, String>();
		properties.putAll(commonPage.getProperties());
		for (GenerationWizardPage page : pages) {
			properties.putAll(page.getProperties());
		}
		if (selectedProject != null) {
			try {
				properties.putAll(getWorkflowProperties(getGeneratedPath(), projectName));
				properties.putAll(getCustomizableProperties());
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

		boolean uncompress = true;
		if (project.exists()) {
			MessageDialogWithToggle dialog = MessageDialogWithToggle.openYesNoCancelQuestion(getShell(),
					Messages.GenerationWizard_1, Messages.GenerationWizard_2, Messages.GenerationWizard_3, true, null, null);
			switch (dialog.getReturnCode()) {
			case IDialogConstants.YES_ID:
				try {
					project.delete(true, true, null);
				} catch (CoreException e) {
					e.printStackTrace();
				}
				break;
			case IDialogConstants.CANCEL_ID:
				return true;
			}
			uncompress = dialog.getToggleState();
			// boolean delete = MessageDialog.openQuestion(getShell(), Messages.GenerationWizard_1, Messages.GenerationWizard_2);
			// if (delete) {
			// try {
			// project.delete(true, true, null);
			// } catch (CoreException e) {
			// e.printStackTrace();
			// }
			// }
		}

		GenerateJob job = new GenerateJob(selectedProject, projectName, archive, definition, properties, workflow, uncompress);
		job.setPostGenerationTasks(mPostGenerationTasks);
		job.setUser(true);
		job.schedule();

		return true;
	}

	/**
	 * Specify the workflow to use for the generation
	 * 
	 * @param path
	 *            the workflow path
	 */
	public void setWorkflow(String path) {
		workflowPath = path;
	}

	/**
	 * Add a specific workflow to the workflow choices.
	 * 
	 * @param label
	 *            the workflow label
	 * @param path
	 *            the workflow path.
	 */
	public void addSpecificWorkflow(String label, String path) {
		commonPage.addSpecificWorkflow(label, path);
	}

	/**
	 * Specify the template archive to use
	 * 
	 * @param path
	 *            the archive path
	 */
	public void setArchive(InputStream path) {
		archive = path;
	}

	/**
	 * Specify the template definition file to use
	 * 
	 * @param path
	 *            the definition file
	 */
	public void setDefinition(InputStream path) {
		definition = path;
	}

	/**
	 * add a specific properties wizard page
	 * 
	 * @param page
	 *            the page to add
	 */
	public void addPropertiesPage(GenerationWizardPage page) {
		pages.add(page);
		addPage(page);
	}

	/**
	 * Specify the selected project
	 * 
	 * @param project
	 */
	public void setSelectedProject(IProject project) {
		selectedProject = project;
	}

	/**
	 * Add a task to be performed once the generation achieves
	 * 
	 * @param task
	 */
	public void addPostGenerationTask(PostGenerationTask task) {
		if (mPostGenerationTasks == null) {
			mPostGenerationTasks = new Vector<PostGenerationTask>();
		}
		mPostGenerationTasks.add(task);
	}

	@Override
	public boolean canFinish() {
		boolean yesWeCan = true;
		yesWeCan = yesWeCan && commonPage.isPageComplete();
		for (GenerationWizardPage page : pages) {
			yesWeCan = yesWeCan && page.isPageComplete();
		}
		return yesWeCan;
	}

	/**
	 * Creates and returns a map of properties that a workflow needs to be run
	 * 
	 * @param homeGenPath
	 *            the path in which we want the workflow to put files (it generates a <code>src-gen</code> key with that value in
	 *            the returned properties map
	 * @return a map of workflow properties
	 * @throws CoreException
	 */
	private Map<String, String> getWorkflowProperties(String homeGenPath, String genProjectName) throws CoreException {
		Map<String, String> properties = new HashMap<String, String>();
		ImogeneModelNature mmn = (ImogeneModelNature) selectedProject.getNature(ImogeneModelNature.ID);

		properties.put("modelFullPath", mmn.getModelFile().getLocation().toOSString()); //$NON-NLS-1$
		String modelName = mmn.getModelFile().getName().split("\\.")[0]; //$NON-NLS-1$
		properties.put("modelName", modelName); //$NON-NLS-1$
		properties.put("modelName.lowercase", modelName.toLowerCase()); //$NON-NLS-1$
		// NB : we can specify path relative to the OawGeneratorPlugin's
		// bundle since its ClassLoader will be used as context ClassLoader
		// during generation
		properties.put("projectName", genProjectName); //$NON-NLS-1$
		properties.put("realProjectName", getInsideProjectName(mmn.getModelFile())); //$NON-NLS-1$
		properties.put("medanyMetaModel", OawGeneratorMedooCommon.MEDANY_METAMODEL_FILE_PATH); //$NON-NLS-1$
		properties.put("home-gen", homeGenPath); //$NON-NLS-1$
		properties.put("gen-project-name", genProjectName); //$NON-NLS-1$

		// Operating System name for library selection in templates
		String osName = ((String) System.getProperties().get("os.name")) //$NON-NLS-1$
				.toLowerCase();
		if (osName.contains("linux")) //$NON-NLS-1$
			properties.put("os-name", "linux"); //$NON-NLS-1$ //$NON-NLS-2$
		else if (osName.contains("windows")) //$NON-NLS-1$
			properties.put("os-name", "windows"); //$NON-NLS-1$ //$NON-NLS-2$
		else if (osName.contains("mac")) //$NON-NLS-1$
			properties.put("os-name", "mac"); //$NON-NLS-1$ //$NON-NLS-2$
		else
			properties.put("os-name", ""); //$NON-NLS-1$ //$NON-NLS-2$

		return properties;
	}

	/**
	 * get the inside real project name
	 * 
	 * @return the project name from the model
	 */
	public String getInsideProjectName(IFile modelFile) {
		return ((Project) new ResourceSetImpl()
				.getResource(URI.createPlatformResourceURI(modelFile.getFullPath().toString(), true), true).getContents().get(0))
				.getName().toLowerCase();
	}

	/**
	 * get the generated project path
	 * 
	 * @return path to the generated project
	 */
	public String getGeneratedPath() {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		if (project.exists()) {
			// If project exists
			return project.getLocation().toOSString();
		}
		return ResourcesPlugin.getWorkspace().getRoot().getLocation().append(projectName).toOSString();
	}

	/***************************************************************************
	 * Returns the properties that are configurable through the properties of the medany model project
	 * 
	 * @throws CoreException
	 **************************************************************************/
	private Map<String, String> getCustomizableProperties() throws CoreException {
		String value;
		Map<String, String> properties = new HashMap<String, String>();

		value = selectedProject.getPersistentProperty(new QualifiedName("", //$NON-NLS-1$
				GenerationPropertiesPage.ADMINPASSWORD_PROPERTY));
		properties.put(GenerationPropertiesPage.ADMINPASSWORD_PROPERTY, (value != null) ? value
				: GenerationPropertiesPage.ADMINPASSWORD_DEFAULT);

		value = selectedProject.getPersistentProperty(new QualifiedName("", //$NON-NLS-1$
				GenerationPropertiesPage.BINARIESPATH_PROPERTY));
		properties.put(GenerationPropertiesPage.BINARIESPATH_PROPERTY, (value != null) ? value
				: GenerationPropertiesPage.BINARIESPATH_DEFAULT);

		value = selectedProject.getPersistentProperty(new QualifiedName("", //$NON-NLS-1$
				GenerationPropertiesPage.VIDEOCONVERTER_PROPERTY));
		properties.put(GenerationPropertiesPage.VIDEOCONVERTER_PROPERTY, (value != null) ? value
				: GenerationPropertiesPage.VIDEOCONVERTER_DEFAULT);

		value = selectedProject.getPersistentProperty(new QualifiedName("", //$NON-NLS-1$
				GenerationPropertiesPage.AUDIOCONVERTER_PROPERTY));
		properties.put(GenerationPropertiesPage.AUDIOCONVERTER_PROPERTY, (value != null) ? value
				: GenerationPropertiesPage.VIDEOCONVERTER_DEFAULT);

		value = selectedProject.getPersistentProperty(new QualifiedName("", //$NON-NLS-1$
				GenerationPropertiesPage.PROJECTVERSION_PROPERTY));
		properties.put(GenerationPropertiesPage.PROJECTVERSION_PROPERTY, (value != null) ? value
				: GenerationPropertiesPage.PROJECTVERSION_DEFAULT);

		value = selectedProject.getPersistentProperty(new QualifiedName("", //$NON-NLS-1$
				GenerationPropertiesPage.JDBCDRIVER_PROPERTY));
		properties.put(GenerationPropertiesPage.JDBCDRIVER_PROPERTY, (value != null) ? value
				: GenerationPropertiesPage.JDBCDRIVER_DEFAULT);

		value = selectedProject.getPersistentProperty(new QualifiedName("", //$NON-NLS-1$
				GenerationPropertiesPage.JDBCPATH_PROPERTY));
		properties.put(GenerationPropertiesPage.JDBCPATH_PROPERTY, (value != null) ? value
				: GenerationPropertiesPage.JDBCPATH_DEFAULT);

		value = selectedProject.getPersistentProperty(new QualifiedName("", //$NON-NLS-1$
				GenerationPropertiesPage.JDBCLOGIN_PROPERTY));
		properties.put(GenerationPropertiesPage.JDBCLOGIN_PROPERTY, (value != null) ? value
				: GenerationPropertiesPage.JDBCLOGIN_DEFAULT);

		value = selectedProject.getPersistentProperty(new QualifiedName("", //$NON-NLS-1$
				GenerationPropertiesPage.JDBCPASSWORD_PROPERTY));
		properties.put(GenerationPropertiesPage.JDBCPASSWORD_PROPERTY, (value != null) ? value
				: GenerationPropertiesPage.JDBCPASSWORD_DEFAULT);

		value = selectedProject.getPersistentProperty(new QualifiedName("", //$NON-NLS-1$
				GenerationPropertiesPage.HIBERNATEDIALECT_PROPERTY));
		properties.put(GenerationPropertiesPage.HIBERNATEDIALECT_PROPERTY, (value != null) ? value
				: GenerationPropertiesPage.HIBERNATEDIALECT_DEFAULT);

		value = selectedProject.getPersistentProperty(new QualifiedName("", //$NON-NLS-1$
				GenerationPropertiesPage.BINARIESPATH_PROPERTY));
		properties.put(GenerationPropertiesPage.BINARIESPATH_PROPERTY, (value != null) ? value
				: GenerationPropertiesPage.BINARIESPATH_DEFAULT);

		value = selectedProject.getPersistentProperty(new QualifiedName("", //$NON-NLS-1$
				GenerationPropertiesPage.SMTP_PROPERTY));
		properties.put(GenerationPropertiesPage.SMTP_PROPERTY, (value != null) ? value : GenerationPropertiesPage.SMTP_DEFAULT);

		value = selectedProject.getPersistentProperty(new QualifiedName("", //$NON-NLS-1$
				GenerationPropertiesPage.SMTPLOGIN_PROPERTY));
		properties.put(GenerationPropertiesPage.SMTPLOGIN_PROPERTY, (value != null) ? value
				: GenerationPropertiesPage.SMTPLOGIN_DEFAULT);

		value = selectedProject.getPersistentProperty(new QualifiedName("", //$NON-NLS-1$
				GenerationPropertiesPage.SMTPPASSWD_PROPERTY));
		properties.put(GenerationPropertiesPage.SMTPPASSWD_PROPERTY, (value != null) ? value
				: GenerationPropertiesPage.SMTPPASSWD_DEFAULT);

		value = selectedProject.getPersistentProperty(new QualifiedName("", //$NON-NLS-1$
				GenerationPropertiesPage.WEBSITE_PROPERTY));
		properties.put(GenerationPropertiesPage.WEBSITE_PROPERTY, (value != null) ? value
				: GenerationPropertiesPage.WEBSITE_DEFAULT);

		value = selectedProject.getPersistentProperty(new QualifiedName("", //$NON-NLS-1$
				GenerationPropertiesPage.SYNCHROSERVER_PROPERTY));
		properties.put(GenerationPropertiesPage.SYNCHROSERVER_PROPERTY, (value != null) ? value
				: GenerationPropertiesPage.SYNCHROSERVER_DEFAULT);

		value = selectedProject.getPersistentProperty(new QualifiedName("", //$NON-NLS-1$
				GenerationPropertiesPage.SMSSERVER_PROPERTY));
		properties.put(GenerationPropertiesPage.SMSSERVER_PROPERTY, (value != null) ? value
				: GenerationPropertiesPage.SMSSERVER_DEFAULT);
		value = selectedProject.getPersistentProperty(new QualifiedName("", //$NON-NLS-1$
				GenerationPropertiesPage.WEBSERVICE_PROPERTY));
		properties.put(GenerationPropertiesPage.WEBSERVICE_PROPERTY, (value != null) ? value
				: GenerationPropertiesPage.WEBSERVICE_DEFAULT);

		// Get test properties values

		value = selectedProject.getPersistentProperty(new QualifiedName("", //$NON-NLS-1$
				GenerationTestPropertiesPage.JDBCDRIVER_PROPERTY));
		properties.put(GenerationTestPropertiesPage.JDBCDRIVER_PROPERTY, (value != null) ? value
				: GenerationTestPropertiesPage.JDBCDRIVER_DEFAULT);

		value = selectedProject.getPersistentProperty(new QualifiedName("", //$NON-NLS-1$
				GenerationTestPropertiesPage.JDBCPATH_PROPERTY));
		properties.put(GenerationTestPropertiesPage.JDBCPATH_PROPERTY, (value != null) ? value
				: GenerationTestPropertiesPage.JDBCPATH_DEFAULT);

		value = selectedProject.getPersistentProperty(new QualifiedName("", //$NON-NLS-1$
				GenerationTestPropertiesPage.JDBCLOGIN_PROPERTY));
		properties.put(GenerationTestPropertiesPage.JDBCLOGIN_PROPERTY, (value != null) ? value
				: GenerationTestPropertiesPage.JDBCLOGIN_DEFAULT);

		value = selectedProject.getPersistentProperty(new QualifiedName("", //$NON-NLS-1$
				GenerationTestPropertiesPage.JDBCPASSWORD_PROPERTY));
		properties.put(GenerationTestPropertiesPage.JDBCPASSWORD_PROPERTY, (value != null) ? value
				: GenerationTestPropertiesPage.JDBCPASSWORD_DEFAULT);

		value = selectedProject.getPersistentProperty(new QualifiedName("", //$NON-NLS-1$
				GenerationTestPropertiesPage.HIBERNATEDIALECT_PROPERTY));
		properties.put(GenerationTestPropertiesPage.HIBERNATEDIALECT_PROPERTY, (value != null) ? value
				: GenerationTestPropertiesPage.HIBERNATEDIALECT_DEFAULT);

		value = selectedProject.getPersistentProperty(new QualifiedName("", //$NON-NLS-1$
				GenerationTestPropertiesPage.ICONSPATH_PROPERTY));
		properties.put(GenerationTestPropertiesPage.ICONSPATH_PROPERTY, (value != null) ? value
				: GenerationTestPropertiesPage.ICONSPATH_DEFAULT);

		value = selectedProject.getPersistentProperty(new QualifiedName("", //$NON-NLS-1$
				GenerationTestPropertiesPage.SMTP_PROPERTY));
		properties.put(GenerationTestPropertiesPage.SMTP_PROPERTY, (value != null) ? value
				: GenerationTestPropertiesPage.SMTP_DEFAULT);

		value = selectedProject.getPersistentProperty(new QualifiedName("", //$NON-NLS-1$
				GenerationTestPropertiesPage.SMTPLOGIN_PROPERTY));
		properties.put(GenerationTestPropertiesPage.SMTPLOGIN_PROPERTY, (value != null) ? value
				: GenerationTestPropertiesPage.SMTPLOGIN_DEFAULT);

		value = selectedProject.getPersistentProperty(new QualifiedName("", //$NON-NLS-1$
				GenerationTestPropertiesPage.SMTPPASSWD_PROPERTY));
		properties.put(GenerationTestPropertiesPage.SMTPPASSWD_PROPERTY, (value != null) ? value
				: GenerationTestPropertiesPage.SMTPPASSWD_DEFAULT);

		value = selectedProject.getPersistentProperty(new QualifiedName("", //$NON-NLS-1$
				GenerationTestPropertiesPage.WEBSITE_PROPERTY));
		properties.put(GenerationTestPropertiesPage.WEBSITE_PROPERTY, (value != null) ? value
				: GenerationTestPropertiesPage.WEBSITE_DEFAULT);

		value = selectedProject.getPersistentProperty(new QualifiedName("", //$NON-NLS-1$
				GenerationTestPropertiesPage.SYNCHROSERVER_PROPERTY));
		properties.put(GenerationTestPropertiesPage.SYNCHROSERVER_PROPERTY, (value != null) ? value
				: GenerationTestPropertiesPage.SYNCHROSERVER_DEFAULT);

		value = selectedProject.getPersistentProperty(new QualifiedName("", //$NON-NLS-1$
				GenerationTestPropertiesPage.SMSSERVER_PROPERTY));
		properties.put(GenerationTestPropertiesPage.SMSSERVER_PROPERTY, (value != null) ? value
				: GenerationTestPropertiesPage.SMSSERVER_DEFAULT);

		return properties;
	}
}
