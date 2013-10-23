package org.imogene.studio.contrib.action;

import java.io.IOException;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.imogene.model.core.CardEntity;
import org.imogene.model.core.Project;

public class AddColorsAction implements IObjectActionDelegate {
	
    // Store the current model resource selection
    protected IStructuredSelection _selection;

    // Store the currently selected project
    protected IFile _selectedFile;
	
    @Override
	public void setActivePart(IAction action, IWorkbenchPart workbench) {
	}

	@Override
	public void run(IAction action) {
        String fullPath = _selectedFile.getFullPath().toPortableString();
        URI resourceURI = URI.createPlatformResourceURI(fullPath, true);
        Resource r = loadModel(resourceURI);
        
        Project model = (Project) r.getContents().get(0);
        Iterator<CardEntity> it = model.getEntities().iterator();
        while (it.hasNext()) {
        	int color = (int) (Math.random() * 0xffffff);
        	String sColor = String.format("%6x", color);
        	sColor = sColor.replace(' ', '0');
        	it.next().setColor(sColor);
        }
        
        try {
            r.save(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
        _selection = (IStructuredSelection) selection;
        _selectedFile = (IFile) (_selection.getFirstElement());  
	}
	
    public static Resource loadModel(final URI resUri) {
        return new ResourceSetImpl().getResource(resUri, true);
    }
    
}
