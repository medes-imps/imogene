package org.imogene.lib.sync.client;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Interface that describes a synchronization server client.
 * 
 * @author MEDES-IMPS
 */
public interface SyncClient {

	/* misc */
	public static String ERROR_PREFIX = "-error-";

	/* parameters constants */
	public static String LOGIN_PARAM = "login";

	public static String PASSWD_PARAM = "password";

	public static String TERMINALID_PARAM = "terminal";

	public static String TYPE_PARAM = "type";

	public static String CMD_PARAM = "cmd";

	public static String SESSION_PARAM = "session";

	/* command constants */

	public static String CMD_INIT = "init";

	public static String CMD_CLOSE = "ackservmodif";

	public static String CMD_SENDMODIF = "clmodif";

	public static String CMD_SERVERMODIF = "reqservmodif";

	public static String CDM_DIRECTSEND = "directsend";

	/**
	 * Initialize the session
	 * 
	 * @param login the user login
	 * @param passwd the user password
	 * @param terminalId the terminal id
	 * @param type the synchronization type
	 * @return the session id
	 */
	public String initSession(String login, String passwd, String terminalId, String type);

	/**
	 * Send the client modifications to the server
	 * 
	 * @param sessionId the session id;
	 * @param data the modified entity, serialized
	 * @return the number of entity processed by the server
	 */
	public int sendClientModification(String sessionId, InputStream in);

	/**
	 * Get server modifications
	 * 
	 * @param sessionId the session id
	 * @return the stream received from the server.
	 */
	public void requestServerModifications(String sessionId, OutputStream out);

	/**
	 * Close an opened session
	 * 
	 * @param sessionId the session id
	 * @return true if the command success
	 */
	public boolean closeSession(String sessionId);

	/**
	 * Method that permits to send data outside a global synchronization
	 * process.
	 * 
	 * @param sessionId the sessionId;
	 * @param data the data to send
	 * @return number of modifications applied
	 */
	public int directSend(String sessionId, InputStream out);

}
