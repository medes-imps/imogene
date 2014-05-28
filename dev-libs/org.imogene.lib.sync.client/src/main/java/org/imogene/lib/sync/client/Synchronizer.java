package org.imogene.lib.sync.client;

/**
 * Basic interface to synchronize from a client with a server or authenticate a user.
 * 
 * @author Medes-IMPS
 * 
 */
public interface Synchronizer {

	/**
	 * Result code on authentication success
	 */
	public static final int AUTH_SUCCESS = 0;

	/**
	 * Result code on authentication failure
	 */
	public static final int AUTH_FAILURE = -1;

	/**
	 * Result code on synchronization success
	 */
	public static final int SYNC_SUCCESS = 0;

	/**
	 * Result code on synchronization failure
	 */
	public static final int SYNC_FAILURE = -1;

	/**
	 * Execute a synchronization process. Returns the result of the synchronization.
	 * 
	 * @return if success returns {@link Synchronizer#SYNC_SUCCESS}, {@link Synchronizer#SYNC_FAILURE} otherwise
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
	 * @return if success returns {@link Synchronizer#SYNC_SUCCESS}, {@link Synchronizer#SYNC_FAILURE} otherwise
	 */
	public int synchronize(String url, String login, String password, String terminal, Long offset);

	/**
	 * Authenticate a user given the server address, the login and the password
	 * 
	 * @param url The synchronization server address
	 * @param login The user login
	 * @param password The user password
	 * @return if success returns {@link Synchronizer#AUTH_SUCCESS}, {@link Synchronizer#AUTH_FAILURE} otherwise
	 */
	public int authenticate(String url, String login, String password);

}
