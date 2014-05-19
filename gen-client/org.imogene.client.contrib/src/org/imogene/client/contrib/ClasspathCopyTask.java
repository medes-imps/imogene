package org.imogene.client.contrib;

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
import org.imogene.studio.contrib.ui.navigator.ClientShadow;

public class ClasspathCopyTask implements PostGenerationTask {

	private static String LIBRARY_PATH = "lib";

	@Override
	public void onPostGeneration(GenerationManager manager) throws CoreException {
		List<ExportedEntry> entries = ExportManager.getClasspath(ClientShadow.NATURE);
		IFolder iDestination = manager.getGeneratedProject().getFolder(LIBRARY_PATH);
		if (!iDestination.exists()) {
			iDestination.create(true, true, null);
		}
		for (ExportedEntry e : entries) {
			try {
				IFile iFile = iDestination.getFile(e.getFileName());
				if (!iFile.exists())
					iFile.create(e.openStream(), true, null);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		IProject p = manager.getGeneratedProject();
		p.refreshLocal(IResource.DEPTH_INFINITE, null);
	}

}
