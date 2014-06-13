package org.imogene.web.contrib.builder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

import com.google.gwt.eclipse.core.runtime.GWTRuntime;

/**
 * This builder is in charge of copying the gwt-servlet.jar to the project library folder given the user version of the
 * GWT SDK.
 * 
 * @author MEDES-IMPS
 * 
 */
public class GWTJarBuilder extends IncrementalProjectBuilder {

	private static String LIBRARY_PATH = "src/main/webapp/WEB-INF/lib/";

	@Override
	protected IProject[] build(int kind, @SuppressWarnings("rawtypes") Map args, IProgressMonitor monitor)
			throws CoreException {
		if (kind == FULL_BUILD) {
			IProject project = getProject();
			IJavaProject javaProject = JavaCore.create(project);

			GWTRuntime runtime = GWTRuntime.findSdkFor(javaProject);
			for (File f : Arrays.asList(runtime.getWebAppClasspathFiles(project))) {
				IFolder libraryFolder = project.getFolder(LIBRARY_PATH);
				if (!libraryFolder.exists()) {
					libraryFolder.create(true, true, monitor);
				}
				IFile libraryFile = libraryFolder.getFile(f.getName());
				if (libraryFile.exists()) {
					libraryFile.delete(true, monitor);
				}
				try {
					libraryFile.create(new FileInputStream(f), true, monitor);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			project.refreshLocal(IResource.DEPTH_INFINITE, null);
		}
		return null;
	}

	@SuppressWarnings("unused")
	private boolean shouldCopy(File sdk, File lib) {
		if (lib.exists())
			return false;
		if (sdk.length() == lib.length())
			return false;
		return true;
	}
}
