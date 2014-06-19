package org.imogene.web.contrib.task;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.JavaRuntime;
import org.imogene.studio.contrib.interfaces.GenerationManager;
import org.imogene.studio.contrib.interfaces.PostGenerationTask;

import com.google.gwt.eclipse.core.runtime.GWTRuntime;

/**
 * This task creates the launch configuration for the SuperDevMode GWT debug mode for the generated project
 * 
 * @author MEDES-IMPS
 * 
 */
public class AddSuperDevModeLaunchConfigurationTask implements PostGenerationTask {

	private static final String PROGRAM_ARGS = "-noprecompile";
	private static final String MAIN_CLASS = "com.google.gwt.dev.codeserver.CodeServer";

	@Override
	public void onPostGeneration(GenerationManager manager) throws CoreException {
		String projectName = manager.getGeneratedProject().getName();
		String modelName = manager.getProperties().get("modelName");
		boolean embedded = Boolean.parseBoolean(manager.getProperties().get("embedded"));

		ILaunchManager mgr = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType type = mgr
				.getLaunchConfigurationType(IJavaLaunchConfigurationConstants.ID_JAVA_APPLICATION);
		ILaunchConfigurationWorkingCopy wc = type.newInstance(null, projectName);
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, projectName);
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, MAIN_CLASS);
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, buildArgs(modelName));

		// Add the gwt-codeserver.jar to the classpath of the launch configuration
		IJavaProject project = JavaCore.create(manager.getGeneratedProject());
		GWTRuntime runtime = GWTRuntime.findSdkFor(project);
		IPath codeServerPath = runtime.getInstallationPath().append("gwt-codeserver.jar");
		IRuntimeClasspathEntry codeServerEnry = JavaRuntime.newArchiveRuntimeClasspathEntry(codeServerPath);
		codeServerEnry.setClasspathProperty(IRuntimeClasspathEntry.USER_CLASSES);

		// Add the default system JRE container classpath entry
		IPath systemLibsPath = new Path(JavaRuntime.JRE_CONTAINER);
		IRuntimeClasspathEntry systemLibsEntry = JavaRuntime.newRuntimeContainerClasspathEntry(systemLibsPath,
				IRuntimeClasspathEntry.STANDARD_CLASSES);

		// Add the project to the classpath entries list
		IRuntimeClasspathEntry projectEntry = JavaRuntime.newProjectRuntimeClasspathEntry(project);
		projectEntry.setClasspathProperty(IRuntimeClasspathEntry.USER_CLASSES);

		// Add the src/main/java folder to the classpath entries list
		IPath javaFolderPath = project.getPath().append("src").append("main").append("java");
		IRuntimeClasspathEntry javaEntry = JavaRuntime.newArchiveRuntimeClasspathEntry(javaFolderPath);
		javaEntry.setClasspathProperty(IRuntimeClasspathEntry.USER_CLASSES);

		// Add the src/main/embedded folder to the classpath entries list in case we are generating an embedded web
		// application
		IRuntimeClasspathEntry embeddedEntry = null;
		if (embedded) {
			IPath embeddedFolderPath = project.getPath().append("src").append("main").append("embedded");
			embeddedEntry = JavaRuntime.newArchiveRuntimeClasspathEntry(embeddedFolderPath);
			embeddedEntry.setClasspathProperty(IRuntimeClasspathEntry.USER_CLASSES);
		}

		// Add the src/main/resources folder to the classpath entries list
		IPath resourcesFolderPath = project.getPath().append("src").append("main").append("resources");
		IRuntimeClasspathEntry resourcesEntry = JavaRuntime.newArchiveRuntimeClasspathEntry(resourcesFolderPath);
		resourcesEntry.setClasspathProperty(IRuntimeClasspathEntry.USER_CLASSES);

		List<String> classpath = new ArrayList<String>();
		classpath.add(systemLibsEntry.getMemento());
		classpath.add(projectEntry.getMemento());
		classpath.add(codeServerEnry.getMemento());
		classpath.add(javaEntry.getMemento());
		if (embedded) {
			classpath.add(embeddedEntry.getMemento());
		}
		classpath.add(resourcesEntry.getMemento());

		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_DEFAULT_CLASSPATH, false);
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_CLASSPATH, classpath);

		wc.doSave();
	}

	private static String buildArgs(String modelName) {
		StringBuilder builder = new StringBuilder(PROGRAM_ARGS);
		builder.append(" org.imogene.");
		builder.append(modelName.toLowerCase());
		builder.append(".Imog");
		builder.append(modelName.toUpperCase().charAt(0));
		builder.append(modelName.substring(1));
		return builder.toString();
	}

}
