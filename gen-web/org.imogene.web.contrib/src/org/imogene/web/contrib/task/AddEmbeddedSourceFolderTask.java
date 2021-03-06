package org.imogene.web.contrib.task;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.imogene.studio.contrib.interfaces.GenerationManager;
import org.imogene.studio.contrib.interfaces.PostGenerationTask;

/**
 * Post generation task that adds the embedded source folder to the classpath in case we are generating an embedded web
 * application.
 * 
 * @author MEDES-IMPS
 * 
 */
public class AddEmbeddedSourceFolderTask implements PostGenerationTask {

	@Override
	public void onPostGeneration(GenerationManager manager) throws CoreException {
		boolean embedded = Boolean.parseBoolean(manager.getProperties().get("embedded"));
		if (embedded) {
			IProject project = manager.getGeneratedProject();
			IJavaProject javaProject = JavaCore.create(project);

			IFolder javaFolder = project.getFolder("src/embedded/java");
			IFolder resourcesFolder = project.getFolder("src/embedded/resources");

			IPackageFragmentRoot javaRoot = javaProject.getPackageFragmentRoot(javaFolder);
			IPackageFragmentRoot resourcesRoot = javaProject.getPackageFragmentRoot(resourcesFolder);
			IClasspathEntry[] oldEntries = javaProject.getRawClasspath();
			IClasspathEntry[] newEntries = new IClasspathEntry[oldEntries.length + 2];
			System.arraycopy(oldEntries, 0, newEntries, 0, oldEntries.length);
			newEntries[oldEntries.length] = JavaCore.newSourceEntry(javaRoot.getPath());
			newEntries[oldEntries.length + 1] = JavaCore.newSourceEntry(resourcesRoot.getPath());
			javaProject.setRawClasspath(newEntries, null);
		}
	}

}
