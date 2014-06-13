package org.imogene.web.contrib.task;

import java.io.File;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.imogene.studio.contrib.ImogeneModelNature;
import org.imogene.studio.contrib.interfaces.GenerationManager;
import org.imogene.studio.contrib.interfaces.PostGenerationTask;

/**
 * Copy the images defined in the project model to the generated project.
 * 
 * @author Medes-IMPS
 */
public class WebIconCopyTask implements PostGenerationTask {

	@Override
	public void onPostGeneration(GenerationManager manager) throws CoreException {
		/* create the IFolder that represents the images destination */
		ImogeneModelNature mmn = (ImogeneModelNature) manager.getSelectedProject().getNature(ImogeneModelNature.ID);
		String imageFolder = "src/main/resources/org/imogene/" + mmn.getModelName().toLowerCase() + "/public/images";
		IFolder destinationFolder = manager.getGeneratedProject().getFolder(imageFolder);

		/* create the project if it doesn't exist */
		File dire = new File(destinationFolder.getLocation().toOSString());
		if (!dire.exists()) {
			dire.mkdirs();
			/* refresh to project to be notified about the directory creation */
			manager.getGeneratedProject().refreshLocal(IResource.DEPTH_INFINITE, null);
		}

		/* copy the images */
		for (IResource res : mmn.getIconsFolder().members()) {
			res.copy(destinationFolder.getFullPath().append(res.getName()), true, null);
		}
	}

}
