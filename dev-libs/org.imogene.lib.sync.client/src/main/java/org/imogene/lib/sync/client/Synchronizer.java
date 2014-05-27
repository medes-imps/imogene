package org.imogene.lib.sync.client;

/**
 * Basic interface to synchronize from a client with a server or authenticate a user.
 * 
 * @author Medes-IMPS
 * 
 */
public interface Synchronizer {

	/**
	 * Execute a synchronization process. Returns the result of the synchronization.
	 * 
	 * @return if success return 0, -1 otherwise
	 */
	public int synchronize();

	/**
	 * Execute a synchronization process. Returns the result of the synchronization.
	 * 
	 * @param url The synchronization server address
	 * @param login The user login
	 * @param password The user password
	 * @param terminal The terminal identifier
	 * @param offset The time offset
	 * @return if success return 0, -1 otherwise
	 */
	public int synchronize(String url, String login, String password, String terminal, Long offset);

	/**
	 * Authenticate a user given the server address, the login and the password
	 * 
	 * @param url The synchronization server address
	 * @param login The user login
	 * @param password The user password
	 * @return if success return 0, -1 otherwise
	 */
	public int authenticate(String url, String login, String password);

}
