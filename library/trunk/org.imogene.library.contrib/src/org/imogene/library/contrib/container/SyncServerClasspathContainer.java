package org.imogene.library.contrib.container;

import java.util.List;
import java.util.Vector;

import org.eclipse.jdt.core.IClasspathEntry;

public class SyncServerClasspathContainer extends AbstractClasspathContainer {

	private List<IClasspathEntry> entries  = new Vector<IClasspathEntry>();
	
	private static String DESCRIPTION = "Imogene synchronizer library";
	
	public SyncServerClasspathContainer(){		
		//System.out.println(DESCRIPTION);
		addSynchro();
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
