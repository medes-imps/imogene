package org.imogene.library.contrib.container;

import java.util.List;
import java.util.Vector;

import org.eclipse.jdt.core.IClasspathEntry;

public class NotifierClasspathContainer extends AbstractClasspathContainer {

	private List<IClasspathEntry> entries  = new Vector<IClasspathEntry>();
	
	private static String DESCRIPTION = "Imogene notifier library";
	
	public NotifierClasspathContainer(){		
		addNotifier();		
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
