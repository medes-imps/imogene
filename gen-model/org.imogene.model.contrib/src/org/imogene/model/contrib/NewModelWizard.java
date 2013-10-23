package org.imogene.model.contrib;

import java.io.IOException;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.imogene.studio.contrib.ui.generation.GenerationWizard;


/**
 * Wizard used to create a new Imogene model project
 * @author Medes-IMPS 
 */
public class NewModelWizard extends GenerationWizard implements IWorkbenchWizard {

	public NewModelWizard(){		
		setWindowTitle(Messages.NewModelWizard_0);
		try{
		setArchive(FileLocator
				.openStream(Activator.getDefault().getBundle(), new Path(
						"template-site/template.zip"), false)); //$NON-NLS-1$
		setDefinition(FileLocator.openStream(Activator.getDefault()
				.getBundle(), new Path("template-site/templates.xml"), //$NON-NLS-1$
				false));
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		setWorkflow(null);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {		
		
	}
	
	
}
