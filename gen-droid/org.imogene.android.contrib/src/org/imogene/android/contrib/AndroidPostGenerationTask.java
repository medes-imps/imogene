package org.imogene.android.contrib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.imogene.model.core.Project;
import org.imogene.studio.contrib.ImogeneModelNature;
import org.imogene.studio.contrib.interfaces.GenerationManager;
import org.imogene.studio.contrib.interfaces.PostGenerationTask;

public class AndroidPostGenerationTask implements PostGenerationTask {

	private static final String MATCH = "org.imogene.android.template.R";

	@Override
	public void onPostGeneration(GenerationManager manager) throws CoreException {
		deleteUselessStuff(manager);
		replaceImportResources(manager);
	}

	private void deleteUselessStuff(GenerationManager manager) throws CoreException {
		IPath path = new Path("gen/fr/medes/medoo/android/theprojectname");
		manager.getGeneratedProject().getFolder(path).delete(true, true, null);
		path = new Path("res/drawable/small");
		manager.getGeneratedProject().getFolder(path).delete(true, true, null);
		path = new Path("res/values/imog__togen.xml");
		manager.getGeneratedProject().getFile(path).delete(true, null);
	}

	private void replaceImportResources(GenerationManager manager) throws CoreException {
		ImogeneModelNature mmn = (ImogeneModelNature) manager.getSelectedProject().getNature(ImogeneModelNature.ID);

		IFolder[] sourceFolders = new IFolder[] { manager.getGeneratedProject().getFolder("src") };

		String replace = "org.imogene.android." + getProjectName(mmn).toLowerCase() + ".R";
		for (IFolder folder : sourceFolders) {
			process(folder.getLocation().toFile(), MATCH, replace);
		}
	}

	private void process(File file, String regex, String replace) {
		if (file.isDirectory()) {
			for (File f : file.listFiles())
				process(f, regex, replace);
			return;
		}

		try {
			replace(file, regex, replace);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void replace(File file, String regex, String replacement) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = "", oldtext = "";
		while ((line = reader.readLine()) != null) {
			oldtext += line + "\r\n";
		}
		reader.close();
		// replace a word in a file
		if (oldtext.contains("'")) {
			System.out.println("File " + file.getAbsolutePath() + " contains a quote");
		}
		String newtext = oldtext.replaceAll(regex, replacement);

		FileWriter writer = new FileWriter(file);
		writer.write(newtext);
		writer.close();
	}

	public String getProjectName(ImogeneModelNature imn) {
		String fullPath = imn.getModelFile().getFullPath().toPortableString();
		URI resourceURI = URI.createPlatformResourceURI(fullPath, true);
		Resource r = new ResourceSetImpl().getResource(resourceURI, true);
		Project model = (Project) r.getContents().get(0);
		return model.getName();
	}

}
