package org.imogene.sync.client.jobs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

public interface Synchronizer {

	/**
	 * Execute a synchronization process. Returns the result of the synchronization.
	 * 
	 * @param monitor the monitor to be used for reporting progress and responding to cancelation. The monitor is never
	 *            <code>null</code>
	 * @return resulting status of the run. The result must not be <code>null</code>
	 */
	public IStatus synchronize(IProgressMonitor monitor);

	/**
	 * Authenticate a user given the server address, the login and the password
	 * 
	 * @param monitor the monitor to be used for reporting progress and responding to cancelation. The monitor is never
	 *            <code>null</code>
	 * @param url The synchronization server address
	 * @param login The user login
	 * @param password The user password
	 * @return resulting status of the run. The result must not be <code>null</code>
	 */
	public IStatus authenticate(IProgressMonitor monitor, String url, String login, String password);

}
