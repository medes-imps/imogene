package org.imogene.library.contrib.container;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.ClasspathContainerInitializer;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.imogene.studio.contrib.ui.navigator.AdminShadow;
import org.imogene.studio.contrib.ui.navigator.DaoShadow;
import org.imogene.studio.contrib.ui.navigator.InitializerShadow;
import org.imogene.studio.contrib.ui.navigator.NotifierShadow;
import org.imogene.studio.contrib.ui.navigator.ClientShadow;
import org.imogene.studio.contrib.ui.navigator.SynchroShadow;
import org.imogene.studio.contrib.ui.navigator.WebEmbeddedShadow;
import org.imogene.studio.contrib.ui.navigator.WebServiceShadow;
import org.imogene.studio.contrib.ui.navigator.WebServiceSoapShadow;
import org.imogene.studio.contrib.ui.navigator.WebShadow;

public class ClassContainerInitializer extends ClasspathContainerInitializer {

	public final static String LIBRARY_ID = "org.imogene.studio.library.IMOGENE_LIB";

	@Override
	public void initialize(IPath containerPath, IJavaProject project) throws CoreException {

		IProject p = project.getProject();
		AbstractClasspathContainer con = null;

		if (p.hasNature(NotifierShadow.NATURE)) {
			con = new NotifierClasspathContainer();
		} else if (p.hasNature(SynchroShadow.NATURE)) {
			con = new SyncServerClasspathContainer();
		} else if (p.hasNature(ClientShadow.NATURE)) {
			con = new ClientClasspathContainer(project);
		} else if (p.hasNature(WebShadow.NATURE)) {
			con = new WebClasspathContainer();
		} else if (p.hasNature(WebEmbeddedShadow.NATURE)) {
			con = new WebEmbeddedClasspathContainer();
		} else if (p.hasNature(AdminShadow.NATURE)) {
			con = new WebAdminClasspathContainer();
		} else if (p.hasNature(InitializerShadow.NATURE)) {
			con = new InitializerClasspathContainer();
		} else if (p.hasNature(WebServiceShadow.NATURE)) {
			con = new WebServiceClasspathContainer();
		} else if (p.hasNature(WebServiceSoapShadow.NATURE)) {
			con = new WebServiceSoapClasspathContainer();
		} else if (p.hasNature(DaoShadow.NATURE)) {
			con = new DaoClasspathContainer();
		}
		if (con == null)
			con = new AllClasspathContainer();

		if (con != null) {
			JavaCore.setClasspathContainer(containerPath, new IJavaProject[] { project },
					new IClasspathContainer[] { con }, null);
		}

	}

	@SuppressWarnings("unused")
	private void displayInfoAboutProject(IProject p) {
		if (p != null) {
			System.out.println("----- LIB project info -----");
			System.out.println("Name: " + p.getName());
			try {
				for (String id : p.getDescription().getNatureIds()) {
					System.out.println("Nature: " + id);
				}
			} catch (CoreException ce) {
				ce.printStackTrace();
			}
		} else {
			System.out.println("Project is null");
		}
	}

}
