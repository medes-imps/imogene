package org.imogene.studio.contrib.ui.navigator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.imogene.studio.contrib.ImogeneModelNature;
import org.imogene.studio.contrib.ImogeneStudioPlugin;



/**
 * Label provider for the customized navigator
 * @author Medes-IMPS 
 */
public class ImogeneLabelProvider implements ILabelProvider {
	
	/* default workbench label provider */	
	private WorkbenchLabelProvider labelProvider = new WorkbenchLabelProvider();

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
	 */
	public Image getImage(Object element) {
		/* a project */
		if(element instanceof IProject && ((IProject)element).exists()){
			IProject project = (IProject)element;			
			try {
				if (project
						.hasNature(ImogeneModelNature.ID))
					return ImogeneStudioPlugin.getImageDescriptor(
							"icons/model_16.gif").createImage(); //$NON-NLS-1$
			} catch (CoreException ce) {
				ce.printStackTrace();
			}
			return labelProvider.getImage(element);
		}
		/* default icon for other resources */
		if (element instanceof IResource){			
			return labelProvider.getImage(element);			
		}
		/* specific icons for shadow generated project group */
		if (element instanceof IShadow) {
			IShadow p = (IShadow) element;
			return p.getIcon();
		}
		/* default */
		return labelProvider.getImage(element);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 */
	public String getText(Object element) {
		
		if (element instanceof IShadow) {
			IShadow p = (IShadow) element;
			return p.getLabel();
		}
		
		if (element instanceof IResource) {
			IResource p = (IResource) element;
			return p.getName();
		}

		return labelProvider.getText(element);
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
	}
	
}
