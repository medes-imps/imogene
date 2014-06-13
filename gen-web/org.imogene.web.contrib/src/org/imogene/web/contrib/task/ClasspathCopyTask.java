package org.imogene.web.contrib.task;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.imogene.library.contrib.export.ExportManager;
import org.imogene.library.contrib.export.ExportedEntry;
import org.imogene.studio.contrib.interfaces.GenerationManager;
import org.imogene.studio.contrib.interfaces.PostGenerationTask;
import org.imogene.studio.contrib.ui.navigator.WebEmbeddedShadow;
import org.imogene.studio.contrib.ui.navigator.WebShadow;

/**
 * This task copies the libraries needed by the generated web application from the imogene studio to the project library
 * folder.
 * 
 * @author MEDES-IMPS
 * 
 */
public class ClasspathCopyTask implements PostGenerationTask {

	private static final String LIBRARY_PATH = "src/main/webapp/WEB-INF/lib";

	private static final String[] EXCLUDES = new String[] { "gwt-voices", "gwt-servlet", "gwt-user", "gwt-dev" };

	@Override
	public void onPostGeneration(GenerationManager manager) throws CoreException {
		boolean embedded = Boolean.parseBoolean(manager.getProperties().get("embedded"));
		List<ExportedEntry> entries = ExportManager
				.getClasspath(embedded ? WebEmbeddedShadow.NATURE : WebShadow.NATURE);
		IFolder iDestination = manager.getGeneratedProject().getFolder(LIBRARY_PATH);
		if (!iDestination.exists()) {
			iDestination.create(true, true, null);
		}
		for (ExportedEntry e : entries) {
			if (!canInclude(e.getFileName())) {
				continue;
			}
			try {
				IFile iFile = iDestination.getFile(e.getFileName());
				if (!iFile.exists()) {
					iFile.create(e.openStream(), true, null);
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		IProject p = manager.getGeneratedProject();
		p.refreshLocal(IResource.DEPTH_INFINITE, null);
	}

	private boolean canInclude(String fileName) {
		for (String exclude : EXCLUDES) {
			if (fileName.startsWith(exclude)) {
				return false;
			}
		}
		return true;
	}

}
