package org.imogene.tools.maven.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.imogene.tools.maven.Activator;


public class LogUtils {

	public static void logError(String message, Throwable e){
		Status status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, message, e);
		Activator.getDefault().getLog().log(status);
	}
	
	public static void logError(String message){
		Status status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, message);
		Activator.getDefault().getLog().log(status);
	}
	
	public static void logInfo(String message){
		Status status = new Status(IStatus.INFO, Activator.PLUGIN_ID, message);
		Activator.getDefault().getLog().log(status);
	}
	
	public static void logWarning(String message){
		Status status = new Status(IStatus.WARNING, Activator.PLUGIN_ID, message);
		Activator.getDefault().getLog().log(status);
	}
}
