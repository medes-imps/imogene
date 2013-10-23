package org.imogene.android.contrib;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.imogene.studio.contrib.ImogeneModelNature;
import org.imogene.studio.contrib.interfaces.GenerationManager;
import org.imogene.studio.contrib.interfaces.PostGenerationTask;

public class AndroidIconCopyTask implements PostGenerationTask {

	@Override
	public void onPostGeneration(GenerationManager manager) throws CoreException {
		ImogeneModelNature mmn = (ImogeneModelNature) manager.getSelectedProject().getNature(ImogeneModelNature.ID);
		IFolder destinationFolder = manager.getGeneratedProject().getFolder("res/drawable");
		for (IResource res : mmn.getIconsFolder().members()) {
			IResource iResource = destinationFolder.findMember(res.getName().toLowerCase());
			if (iResource != null) {
				iResource.delete(true, null);
			}
			res.copy(destinationFolder.getFullPath().append(res.getName().toLowerCase()), true, null);
		}
	}

}
