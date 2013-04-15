package org.imogene.studio.contrib.interfaces;

import org.eclipse.core.runtime.CoreException;

public interface IconCopyTask {
	
	/**
	 * Copies icons from model project to generated project By default copies
	 * them from folder "icons" to folder "icons"
	 */
	public void copyIcons(GenerationManager manager) throws CoreException; 
}
