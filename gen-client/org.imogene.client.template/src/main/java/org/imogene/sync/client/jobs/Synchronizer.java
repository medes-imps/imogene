package org.imogene.sync.client.jobs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.imogene.lib.sync.client.parameter.SyncParameter;

public interface Synchronizer {

	/**
	 * Define the time offset between the machine and an NTP server.
	 * 
	 * @param offset Time offset in milliseconds
	 */
	public void setOffset(long offset);

	/**
	 * Define the synchronization URL and the terminal identifier.
	 * 
	 * @param url The synchronization URL
	 * @param terminal The terminal identifier
	 */
	public void setParameters(String url, String terminal);

	/**
	 * Retrieve the synchronization parameters.
	 * 
	 * @return The synchronization parameters
	 */
	public SyncParameter getParameters();

	/**
	 * Execute a synchronization process. Returns the result of the synchronization.
	 * 
	 * @param monitor the monitor to be used for reporting progress and responding to cancelation. The monitor is never
	 *            <code>null</code>
	 * @return resulting status of the run. The result must not be <code>null</code>
	 */
	public IStatus synchronize(IProgressMonitor monitor);

}
