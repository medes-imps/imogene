package org.imogene.library.contrib.container;

import java.util.List;
import java.util.Vector;

import org.eclipse.jdt.core.IClasspathEntry;

public class AllClasspathContainer extends AbstractClasspathContainer {

	private List<IClasspathEntry> entries  = new Vector<IClasspathEntry>();
	
	private static String DESCRIPTION = "Imogene global library";
	
	public AllClasspathContainer(){		
		addAll();
	}
	
	@Override
	protected List<IClasspathEntry> getEntries() {		
		return entries;
	}

	@Override
	public String getDescription() {
		return DESCRIPTION;
	}

}
