package org.imogene.studio.contrib.ui.navigator;

import java.util.Comparator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;

public class IResourceTypeComparator implements Comparator<IResource>{

	@Override
	public int compare(IResource o1, IResource o2) {		
		if(o1 instanceof IFolder && o2 instanceof IFile){						
			return 1;
		}
		if(o1 instanceof IFile && o2 instanceof IFolder){						
			return -1;
		}		
		return 0;
	}
	
}
