package org.imogene.studio.contrib.ui.generation;

import java.util.Map;

import org.eclipse.jface.wizard.IWizardPage;

public interface GenerationWizardPage extends IWizardPage {

	public Map<String, String> getProperties();
	
}
