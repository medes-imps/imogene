package org.imogene.android.sync;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.UUID;

public interface OptimizedSyncClient {
	
	/* headers */
	public static final String HEADER_NAME = "medoo-sync";
	
	public static final String HEADER_VALUE = "synchro";
	
	/* misc */
	public static final String ERROR_PREFIX = "-error-";
	
	/* parameters constants */
	public static final String LOGIN_PARAM="login";
	
	public static final String PASSWD_PARAM="password";
	
	public static final String TERMINALID_PARAM="terminal";
	
	public static final String TYPE_PARAM="type";
	
	public static final String CMD_PARAM="cmd";
	
	public static final String LENGTH_PARAM="len";
	
	public static final String SESSION_PARAM="session";	
	
	public static final String DEBUG_PARAM="debug";
	
	public static final String SEARCH_PARAM="searchedid";
	
	/* command constants */
	
	public static final String CMD_AUTH="auth";
	
	public static final String CMD_INIT="init";
	
	public static final String CMD_RESUME_SEND_INIT="initresumesend";
	
	public static final String CMD_RESUME_RECEIVE_INIT="initresumereceive";
	
	public static final String CMD_RESUME_SEND="resumesend";
	
	public static final String CMD_RESUME_RECEIVE="resumereceive";
	
	public static final String CMD_CLOSE="ackservmodif";
	
	public static final String CMD_SENDMODIF="clmodif";
	
	public static final String CMD_SERVERMODIF="reqservmodif";
	
	public static final String CMD_DIRECTSEND="directsend";
	
	public static final String CMD_SEARCH = "searchEntity";
	
	public String authentication(String login, String passwd, String terminalId) throws SynchronizationException;
	
	/**
	 * Initialize the session
	 * @param login the user login
	 * @param passwd the user password
	 * @param terminalId the terminal id
	 * @param type the synchronization type
	 * @return the session id
	 * @throws UnrecoverableKeyException
	 * @throws KeyStoreException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 */
	public String initSession(String login, String passwd, String terminalId, String type) throws SynchronizationException;
	
	/**
	 * Resume a session that terminated with anomaly
	 * @param login the user login
	 * @param passwd the user password
	 * @param terminalId the terminal id
	 * @param type the synchronization type
	 * @param sessionId the session id
	 * @return nb of bytes already received or Error
	 */
	public String resumeSend(String login, String passwd, String terminalId, String type, UUID sessionId) throws SynchronizationException;
	
	/**
	 * Resume a session that terminated with anomaly
	 * @param login the user login
	 * @param passwd the user password
	 * @param terminalId the terminal id
	 * @param type the synchronization type
	 * @param sessionId the session id
	 * @param bytesReceived number of bytes already received
	 * @return Acknowledge or Error
	 */
	public String resumeReceive(String login, String passwd, String terminalId, String type, UUID sessionId, long bytesReceived) throws SynchronizationException;
	
	/**
	 * Resume the sent of the client modifications to the server
	 * @param sessionId the session id;
	 * @param data the modified entity, serialized
	 * @return the number of entity processed by the server
	 */
	public int resumeRequestModification(UUID sessionId, OutputStream out, long bytesReceived) throws SynchronizationException;
	
	/**
	 * Send the client modifications to the server
	 * @param sessionId the session id;
	 * @param fis the modified entity, serialized
	 * @return the number of entity processed by the server
	 */
	public int sendClientModification(UUID sessionId, FileInputStream fis) throws SynchronizationException;
	
	/**
	 * Resume the sent of the client modifications to the server
	 * @param sessionId the session id;
	 * @param fis the modified entity, serialized
	 * @return the number of entity processed by the server
	 */
	public int resumeSendModification(UUID sessionId, FileInputStream fis) throws SynchronizationException;
	
	/**
	 * Get server modifications 
	 * @param sessionId the session id
	 * @param out the stream received from from the server
	 * @return true if the command succeed.
	 */
	public boolean requestServerModifications(UUID sessionId, OutputStream out) throws SynchronizationException;
		
	/**
	 * Close an opened session
	 * @param sessionId the session id
	 * @param debug if the debug mode is activated or not
	 * @return true if the command success
	 * @throws UnrecoverableKeyException 
	 * @throws KeyStoreException 
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException 
	 */
	public boolean closeSession(UUID sessionId, boolean debug) throws SynchronizationException;
	
	/**
	 * Method that permits to send data 
	 * outside a global synchronization process.
	 * @param sessionId the sessionId;
	 * @param fis the data to send
	 * @return number of modifications applied
	 */
	public int directSend(UUID sessionId, FileInputStream fis) throws SynchronizationException;
	
	/**
	 * Method that permit to search an entity into a distant database
	 * @param login current user login
	 * @param password current user password
	 * @param searcheId id to search
	 * @param os the stream received from from the server
	 * @return true if the command succeed.
	 * @throws SynchronizationException
	 */
	public boolean searchEntity(String login, String password, String searcheId, OutputStream os) throws SynchronizationException;
}
