package org.imogene.android.sync;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.UUID;

import org.apache.http.auth.AuthenticationException;

public interface OptimizedSyncClient {

	/* headers */
	public static final String HEADER_NAME = "medoo-sync";
	public static final String HEADER_VALUE = "synchro";

	/* response messages */
	public static final String RESPONSE_OK = "OK";

	/* parameters constants */
	public static final String PARAM_CMD = "cmd";
	public static final String PARAM_TERMINAL = "terminal";
	public static final String PARAM_LENGTH = "len";
	public static final String PARAM_SESSION = "session";
	public static final String PARAM_DEBUG = "debug";
	public static final String PARAM_SEARCH = "searchedid";

	/* command constants */
	public static final String CMD_AUTH = "auth";
	public static final String CMD_INIT = "init";
	public static final String CMD_RESUME_SEND_INIT = "initresumesend";
	public static final String CMD_RESUME_RECEIVE_INIT = "initresumereceive";
	public static final String CMD_RESUME_SEND = "resumesend";
	public static final String CMD_RESUME_RECEIVE = "resumereceive";
	public static final String CMD_CLOSE = "ackservmodif";
	public static final String CMD_SENDMODIF = "clmodif";
	public static final String CMD_SERVERMODIF = "reqservmodif";
	public static final String CMD_DIRECTSEND = "directsend";
	public static final String CMD_SEARCH = "searchEntity";

	/**
	 * Check authentication for the given login and password
	 * 
	 * @return {@code true} if authentication succeeded {@code false} otherwise
	 * @throws SynchronizationException
	 * @throws AuthenticationException
	 */
	public boolean authentication() throws SynchronizationException, AuthenticationException;

	/**
	 * Initialize the session
	 * 
	 * @param terminalId the terminal id
	 * @return the session id
	 * @throws AuthenticationException
	 * @throws UnrecoverableKeyException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public String initSession(String terminalId) throws SynchronizationException, AuthenticationException;

	/**
	 * Resume a session that terminated with anomaly
	 * 
	 * @param terminalId the terminal id
	 * @param sessionId the session id
	 * @return number of bytes already received
	 * @throws AuthenticationException
	 */
	public long resumeSend(String terminalId, UUID sessionId) throws SynchronizationException, AuthenticationException;

	/**
	 * Resume a session that terminated with anomaly
	 * 
	 * @param terminalId the terminal id
	 * @param sessionId the session id
	 * @param bytesReceived number of bytes already received
	 * @throws AuthenticationException
	 */
	public void resumeReceive(String terminalId, UUID sessionId, long bytesReceived) throws SynchronizationException,
			AuthenticationException;

	/**
	 * Resume the sent of the client modifications to the server
	 * 
	 * @param sessionId the session id;
	 * @param data the modified entity, serialized
	 * @throws AuthenticationException
	 */
	public void resumeRequestModification(UUID sessionId, OutputStream out, long bytesReceived)
			throws SynchronizationException, AuthenticationException;

	/**
	 * Send the client modifications to the server
	 * 
	 * @param sessionId the session id;
	 * @param fis the modified entity, serialized
	 * @return the number of entity processed by the server
	 * @throws AuthenticationException
	 */
	public int sendClientModification(UUID sessionId, FileInputStream fis) throws SynchronizationException,
			AuthenticationException;

	/**
	 * Resume the sent of the client modifications to the server
	 * 
	 * @param sessionId the session id;
	 * @param fis the modified entity, serialized
	 * @return the number of entity processed by the server
	 * @throws AuthenticationException
	 */
	public int resumeSendModification(UUID sessionId, FileInputStream fis) throws SynchronizationException,
			AuthenticationException;

	/**
	 * Get server modifications
	 * 
	 * @param sessionId the session id
	 * @param out the stream received from from the server
	 * @return true if the command succeed.
	 * @throws AuthenticationException
	 */
	public boolean requestServerModifications(UUID sessionId, OutputStream out) throws SynchronizationException,
			AuthenticationException;

	/**
	 * Close an opened session
	 * 
	 * @param sessionId the session id
	 * @param debug if the debug mode is activated or not
	 * @throws AuthenticationException
	 * @throws SynchronizationException
	 */
	public void closeSession(UUID sessionId, boolean debug) throws SynchronizationException, AuthenticationException;

	/**
	 * Method that permits to send data outside a global synchronization process.
	 * 
	 * @param sessionId the sessionId;
	 * @param fis the data to send
	 * @return number of modifications applied
	 * @throws AuthenticationException
	 * @throws SynchronizationException
	 */
	public int directSend(UUID sessionId, FileInputStream fis) throws SynchronizationException, AuthenticationException;

	/**
	 * Method that permit to search an entity into a distant database
	 * 
	 * @param searcheId id to search
	 * @param os the stream received from the server
	 * @throws SynchronizationException
	 * @throws AuthenticationException
	 */
	public void searchEntity(String searcheId, OutputStream os) throws SynchronizationException,
			AuthenticationException;
}
