package org.imogene.library.contrib.container;

import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.imogene.library.contrib.Activator;

public class ClientClasspathContainer extends AbstractClasspathContainer {

	private List<IClasspathEntry> entries = new Vector<IClasspathEntry>();

	private static String DESCRIPTION = "Imogene synchronizer client library";
	
	private static final String PLUGIN_LIB_FOLDER = "lib";


	public ClientClasspathContainer(IJavaProject project) {	
		IFolder libFolder = project.getProject().getFolder(new Path(PLUGIN_LIB_FOLDER));
		try {
			for (IResource r : libFolder.members()) {
				if (r instanceof IFile && r.getName().endsWith(".jar")) {
					
					entries.add(JavaCore.newLibraryEntry(r.getFullPath(), null, new Path("/"), true));
				}
			}
		} catch (CoreException e) {
			Activator
					.getDefault()
					.getLog()
					.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e
							.getLocalizedMessage(), e));
			e.printStackTrace();
		}
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
