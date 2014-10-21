package org.imogene.lib.sync.client;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.httpclient.auth.AuthenticationException;

/**
 * Interface that describes an optimized synchronization server client. This client should be able to recover from an
 * unexpectedly interrupted synchronization and resume the sending or the receiving respectively at the last sent or
 * received byte.
 * 
 * @author MEDES-IMPS
 */
public interface OptimizedSyncClient {

	/* response messages */
	public static final String RESPONSE_OK = "OK";

	/* headers */
	public static final String HEADER_NAME = "medoo-sync";
	public static final String HEADER_VALUE = "synchro";

	/* parameters constants */
	public static final String PARAM_CMD = "cmd";
	public static final String PARAM_TERMINALID = "terminal";
	public static final String PARAM_LENGTH = "len";
	public static final String PARAM_SESSION = "session";

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
	public static final String CDM_DIRECTSEND = "directsend";

	/**
	 * Set the url of the synchronization server.
	 * 
	 * @param url The url of the server.
	 */
	public void setUrl(String url);

	/**
	 * Authenticate a user
	 * 
	 * @return {@code true} if the user has been correctly authenticated, {@code false} otherwise.
	 * @throws SynchronizationException
	 */
	public boolean authenticate() throws SynchronizationException;

	/**
	 * Initialize the session
	 * 
	 * @return the session id
	 * @throws AuthenticationException
	 */
	public String initSession() throws SynchronizationException, AuthenticationException;

	/**
	 * Resume a session that terminated with anomaly
	 * 
	 * @param sessionId the session id
	 * @return nb of bytes already received or Error
	 * @throws AuthenticationException
	 */
	public long resumeSend(String sessionId) throws SynchronizationException, AuthenticationException;

	/**
	 * Resume a session that terminated with anomaly
	 * 
	 * @param sessionId the session id
	 * @param bytesReceived number of bytes already received
	 * @return Acknowledge or Error
	 * @throws AuthenticationException
	 */
	public void resumeReceive(String sessionId, long bytesReceived) throws SynchronizationException,
			AuthenticationException;

	/**
	 * Resume the sent of the client modifications to the server
	 * 
	 * @param sessionId the session id;
	 * @param data the modified entity, serialized
	 * @return the number of entity processed by the server
	 * @throws AuthenticationException
	 */
	public int resumeRequestModification(String sessionId, OutputStream out, long bytesReceived)
			throws SynchronizationException, AuthenticationException;

	/**
	 * Send the client modifications to the server
	 * 
	 * @param sessionId the session id;
	 * @param data the modified entity, serialized
	 * @return the number of entity processed by the server
	 * @throws AuthenticationException
	 */
	public int sendClientModification(String sessionId, InputStream in) throws SynchronizationException,
			AuthenticationException;

	/**
	 * Resume the sent of the client modifications to the server
	 * 
	 * @param sessionId the session id;
	 * @param data the modified entity, serialized
	 * @return the number of entity processed by the server
	 * @throws AuthenticationException
	 */
	public int resumeSendModification(String sessionId, InputStream in) throws SynchronizationException,
			AuthenticationException;

	/**
	 * Get server modifications
	 * 
	 * @param sessionId the session id
	 * @return the stream received from the server.
	 * @throws AuthenticationException
	 */
	public void requestServerModifications(String sessionId, OutputStream out) throws SynchronizationException,
			AuthenticationException;

	/**
	 * Close an opened session
	 * 
	 * @param sessionId the session id
	 * @throws AuthenticationException
	 */
	public void closeSession(String sessionId) throws SynchronizationException, AuthenticationException;

	/**
	 * Method that permits to send data outside a global synchronization process.
	 * 
	 * @param sessionId the sessionId;
	 * @param data the data to send
	 * @return number of modifications applied
	 */
	public int directSend(String sessionId, InputStream out) throws Exception;

}
