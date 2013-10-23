package org.imogene.studio.contrib.interfaces;

import org.eclipse.core.runtime.CoreException;

public interface PostGenerationTask {
	/**
	 * perform some further work after the generation has been done
	 */
	public void onPostGeneration(GenerationManager manager) throws CoreException;
}
