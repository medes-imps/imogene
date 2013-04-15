package org.imogene.studio.contrib.action;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.mwe.core.monitor.ProgressMonitor;

public class ImogeneProgressMonitor implements ProgressMonitor {
	
	private IProgressMonitor mMonitor = null;
	
	public ImogeneProgressMonitor(IProgressMonitor monitor) {
		mMonitor = monitor;
	}

	@Override
	public void beginTask(String name, int totalWork) {
		mMonitor.beginTask(name, totalWork);
	}

	@Override
	public void done() {
		mMonitor.done();
	}

	@Override
	public void internalWorked(double work) {
		mMonitor.internalWorked(work);
	}

	@Override
	public boolean isCanceled() {
		return mMonitor.isCanceled();
	}

	@Override
	public void setCanceled(boolean value) {
		mMonitor.setCanceled(value);
	}

	@Override
	public void setTaskName(String name) {
		mMonitor.setTaskName(name);
	}

	@Override
	public void subTask(String name) {
		mMonitor.subTask(name);
	}

	@Override
	public void worked(int work) {
		mMonitor.worked(work);
	}

	@Override
	public void finished(Object element, Object context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postTask(Object element, Object context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preTask(Object element, Object context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void started(Object element, Object context) {
		// TODO Auto-generated method stub
		
	}

}
