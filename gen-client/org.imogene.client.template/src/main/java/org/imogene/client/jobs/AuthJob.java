package org.imogene.client.jobs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;
import org.imogene.client.i18n.Messages;

/**
 * Authentication Job
 * 
 * @author Medes-IMPS
 * 
 */
public class AuthJob extends Job {

	private Synchronizer synchronizer;

	private String url;
	private String login;
	private String password;

	public AuthJob() {
		super(Messages.auth_title);
	}

	/**
	 * Setter for the synchronizer.
	 * 
	 * @param synchronizer The synchronizer
	 */
	public void setSynchronizer(Synchronizer synchronizer) {
		this.synchronizer = synchronizer;
	}

	/**
	 * The server address for authentication.
	 * 
	 * @param url The url for the server
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * The login of the user to authenticate.
	 * 
	 * @param login The user login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * The password of the user to authenticate.
	 * 
	 * @param password The user password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		return synchronizer.authenticate(monitor, url, login, password);
	}

}
