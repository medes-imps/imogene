package org.imogene.studio.contrib.ui.navigator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;
import org.imogene.studio.contrib.ImogeneModelNature;


/**
 * Provide the content of the customized navigator
 * @author Medes-IMPS
 */
public class ImogeneTreeContentProvider extends ArrayContentProvider implements
		ITreeContentProvider, IResourceChangeListener {
	
	private Map<String, Object> _wrapperCache = new HashMap<String, Object>();
	
	private Viewer _viewer;

	public ImogeneTreeContentProvider(){
		IWorkspace ws = ResourcesPlugin.getWorkspace();
		ws.addResourceChangeListener(this, IResourceChangeEvent.POST_CHANGE);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	public Object[] getChildren(Object parentElement) {		
		
		/* parent is the Workspace root */
		if (parentElement instanceof IWorkspaceRoot) {
			ArrayList<IProject> models = new ArrayList<IProject>();
			IWorkspaceRoot p = (IWorkspaceRoot) parentElement;		
			IProject[] projects = p.getProjects();
			for (IProject prj : projects) {
				try {
					if (prj.isAccessible() && prj
							.hasNature(ImogeneModelNature.ID)) {
						models.add(prj);
					}
				} catch (CoreException e) {	
					System.err.println(e.getMessage());
					e.printStackTrace();
				}
			}			
			return models.toArray();
		}
		
		/* if the parent is a Generated group */
		else if (parentElement instanceof IShadow) {
			return ((IShadow)parentElement).getChildren();
		}
		
		/* if the parent is a project */
		else if (parentElement instanceof IProject) {
			
			IProject p = (IProject) parentElement;
			try {
								
				if (p.isAccessible()) {				
					/* RCP project, we display the product files and the properties file */
					if (p.hasNature(RcpShadow.NATURE)) {
						List<IResource> products = new Vector<IResource>();						
						products.addAll(recursivFindMembers(p, ".*\\.product")); //$NON-NLS-1$
						products.addAll(recursivFindMembers((IContainer)p.findMember("src"), ".*\\.properties")); //$NON-NLS-1$ //$NON-NLS-2$
						return products.toArray();
					}
					/* Web project, we display the 'application.properties' files */
					else if(p.hasNature(WebShadow.NATURE)||p.hasNature(SynchroShadow.NATURE)||p.hasNature(WebServiceShadow.NATURE)||p.hasNature(AdminShadow.NATURE)||p.hasNature(NotifierShadow.NATURE)){
						List<IResource> toDisplay = recursivFindMembers((IContainer)p.findMember("src"), ".*\\.properties"); //$NON-NLS-1$ //$NON-NLS-2$
						toDisplay.addAll(recursivFindMembers((IContainer)p.findMember("src"), "log4j.xml"));									 //$NON-NLS-1$ //$NON-NLS-2$
						return toDisplay.toArray();										
					}
					/* Android project, we display the 'values*' folders */
					else if(p.hasNature(AndroidShadow.NATURE)){
						Object[] t = recursivFindMembers((IContainer)p.findMember("res"), "values.*").toArray(); //$NON-NLS-1$ //$NON-NLS-2$
						return t;
					}	
					/* Initializer project, we display the properties files */
					else if (p.hasNature(InitializerShadow.NATURE)) {
						List<IResource> files = new Vector<IResource>();		
						//System.out.println("Looking for properties files");
						files.addAll(recursivFindMembers((IContainer)p.findMember("src"), ".*\\.properties")); //$NON-NLS-1$ //$NON-NLS-2$
						//System.out.println(files.size()+" files found");
						return files.toArray();
					}
					/* other IProject, here, could only by a Medoo model project */
					else {						
						IShadow[] res = new IShadow[2];
						
						IShadow genShadow=(IShadow)_wrapperCache.get(p.getName()+"-genShadow");
						if(genShadow==null){
							genShadow = new GeneratedShadow(p);
							_wrapperCache.put(p.getName()+"-genShadow",genShadow);
						}
						
						IShadow modelShadow=(IShadow)_wrapperCache.get(p.getName()+"-modelShadow");
						if(modelShadow==null){
							modelShadow = new ModelShadow(p);
							_wrapperCache.put(p.getName()+"-modelShadow", modelShadow);
						}
						
						res[0] = genShadow;
						res[1] = modelShadow;
						return res;
					}				
				}
			} catch (CoreException ce) {
				System.err.println(ce.getMessage());
				ce.printStackTrace();
			}
		}

		/* if parent element is an IFolder */
		else if (parentElement instanceof IFolder) {

			IFolder f = (IFolder) parentElement;
			try {
				IResource[] mbrs = f.members();
				Arrays.sort(mbrs, new IResourceTypeComparator());
				return mbrs;
			} catch (CoreException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 */
	public Object getParent(Object element) {
		return null;
	}
	

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	public boolean hasChildren(Object element) {

		/* shadow elements */
		if (element instanceof IShadow) {
			return ((IShadow)element).hasChildren();
		}
		
		/* IProject elements */
		if (element instanceof IProject) {			
			IProject p = (IProject) element;
			if (p.isAccessible()) {
				try {
					boolean result = p.hasNature(ImogeneModelNature.ID)
						|| p.hasNature(RcpShadow.NATURE)
						|| p.hasNature(WebShadow.NATURE)
						|| p.hasNature(SynchroShadow.NATURE)
						|| p.hasNature(AdminShadow.NATURE)
						|| p.hasNature(NotifierShadow.NATURE)
						|| p.hasNature(AndroidShadow.NATURE)
						|| p.hasNature(RcpShadow.NATURE)
						|| p.hasNature(WebServiceShadow.NATURE)
						|| p.hasNature(WebServiceSoapShadow.NATURE)
						|| p.hasNature(InitializerShadow.NATURE);					
					return result;
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
		
		/* workspace root and folders */
		return (element instanceof IWorkspaceRoot)
				|| (element instanceof IFolder);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ArrayContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object inputElement) {		
		return getChildren(inputElement);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ArrayContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(final Viewer viewer, Object oldInput, final Object newInput) {
		_viewer = viewer;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
	 */
	@Override
	public void resourceChanged(final IResourceChangeEvent event) {
		
		Display.getDefault().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				 TreeViewer viewer = (TreeViewer) _viewer;
			     //TreePath[] treePaths = viewer.getExpandedTreePaths();	
			     Object[] objs = viewer.getExpandedElements();
			     viewer.refresh();
			     //viewer.setExpandedTreePaths(treePaths);
			     viewer.setExpandedElements(objs);
			}
		});
	}
	
	
	private List<IResource> recursivFindMembers(IContainer r, String name) throws CoreException {
		List<IResource> objects = new Vector<IResource>();
		for(IResource m : r.members()){
			if(m.getName().matches(name)) objects.add(m);
			if(m instanceof IContainer){
				objects.addAll(recursivFindMembers((IContainer)m, name));
			}					
		}
		return objects;
	}
	
}
