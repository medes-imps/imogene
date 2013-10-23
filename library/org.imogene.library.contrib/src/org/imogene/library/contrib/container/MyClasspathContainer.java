package org.imogene.library.contrib.container;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.JavaCore;
import org.imogene.library.contrib.Activator;

public class MyClasspathContainer implements IClasspathContainer {
	
	private List<IClasspathEntry> entries = new Vector<IClasspathEntry>();
		
	public MyClasspathContainer(){			
		Enumeration<URL> jars = Activator.getDefault().getBundle().findEntries("libs", "*.jar", false);				
		while(jars.hasMoreElements()){
			URL url = jars.nextElement();	
			try{
				IPath path = new Path(FileLocator.toFileURL(url).getPath());
				entries.add(JavaCore.newLibraryEntry(path, null, new Path("/")));
			}catch(IOException ioe){
				ioe.printStackTrace();				
			}
		}		
	}

	@Override
	public IClasspathEntry[] getClasspathEntries() {		
		return entries.toArray(new IClasspathEntry[0]);
	}

	@Override
	public String getDescription() {		
		return "Imogene library";
	}

	@Override
	public int getKind() {		
		return IClasspathContainer.K_APPLICATION;
	}

	@Override
	public IPath getPath() {		
		return null;
	}
	
}
