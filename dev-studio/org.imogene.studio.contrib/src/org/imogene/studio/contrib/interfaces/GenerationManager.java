package org.imogene.studio.contrib.interfaces;

import java.util.Map;

import org.eclipse.core.resources.IProject;

public interface GenerationManager {

	public IProject getSelectedProject();

	public IProject getGeneratedProject();

	public Map<String, String> getProperties();

}
