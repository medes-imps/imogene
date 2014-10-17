package org.imogene.lib.sync.server;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.sync.serializer.ImogSerializationException;

/**
 * This interface describes a synchronization server.
 * 
 * @author MEDES-IMPS
 */
public interface OptimizedSyncServer {

	/**
	 * Search a specific entity and serialized it and its child from first level
	 * 
	 * @param entityId the entityId
	 * @param out destination stream
	 * @throws ImogSerializationException
	 */
	public void searchEntity(String entityId, OutputStream out) throws ImogSerializationException;

	/**
	 * Initialize the session between the client and the server
	 * 
	 * @param termId the terminal id
	 * @return the session id
	 */
	public String initSession(String termId);

	/**
	 * Resume a 'send' task of a previous session.
	 * 
	 * @param termiId the terminal id
	 * @param type the session type
	 * @param user the user
	 * @param sessionId the session id
	 * @return The number of bytes previously received or an error code
	 */
	public String initResumeSendSession(String termiId, String type, ImogActor user, String sessionId);

	/**
	 * Resume a 'receive' task of a previous session.
	 * 
	 * @param termiId the terminal id
	 * @param type the session type
	 * @param user the user
	 * @param sessionId the session id
	 * @param bytesReceived number of bytes previously received
	 * @return Acknowledge or an error code
	 */
	public String initResumeRequestSession(String termiId, String type, ImogActor user, String sessionId,
			long bytesReceived);

	/**
	 * Apply modification sent by the client.
	 * 
	 * @param sessionId the current synchronization session id.
	 * @param data Data to synchronize serialized stream.
	 * @return The number of documents added or -1 if an error occurred.
	 */
	public int applyClientModifications(String sessionId, InputStream data) throws ImogSerializationException;

	/**
	 * Resume a sent operation and apply modification sent by the client.
	 * 
	 * @param sessionId the current synchronization session id.
	 * @param data Data to synchronize serialized stream.
	 * @return The number of documents added or -1 if an error occurred.
	 */
	public int resumeApplyClientModifications(String sessionId, InputStream data) throws ImogSerializationException;

	/**
	 * Retrieve the current modification that have been to send to the client.
	 * 
	 * @param sessionId The id of the synchronization session.
	 * @param out The output stream where to serialize the data
	 */
	public void getServerModifications(String sessionId, OutputStream out) throws ImogSerializationException;

	/**
	 * Retrieve the current modification that have been to send to the client.
	 * 
	 * @param sessionId The id of the synchronization session.
	 * @param out The output stream where to serialize the data
	 * @param bytesToSkip number of bytes to skip because already sent and received
	 */
	public void resumeGetServerModifications(String sessionId, OutputStream out, long bytesToSkip)
			throws ImogSerializationException;

	/**
	 * Close the synchronization session.
	 * 
	 * @param sessionId the session to close id
	 * @param status the session status sent by the client
	 * @return A result code.
	 */
	public int closeSession(String sessionId, int status);

	/**
	 * check the validity of a session
	 * 
	 * @param sessionId the session to check id
	 * @return True if it is a valid session, false otherwise
	 */
	public boolean checkSession(String sessionId);

	/**
	 * Get the directory where temp file are stored.
	 * 
	 * @return the directory
	 */
	public File getFileDirectory();
}
