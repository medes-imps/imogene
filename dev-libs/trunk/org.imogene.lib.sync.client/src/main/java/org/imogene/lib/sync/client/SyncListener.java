package org.imogene.lib.sync.client;

/**
 * Interface to implements to monitor the synchronization process.
 * 
 * @author MEDES-IMPS
 * 
 */
public interface SyncListener {

	/**
	 * Notifies when a session has been created.
	 * 
	 * @param id The identifies of the session.
	 */
	public void initSession(String id);

	/**
	 * Notifies when an error occurs during synchronization process.
	 * 
	 * @param code The code of the error.
	 * @param ex The thrown exception.
	 */
	public void syncError(int code, SynchronizationException ex);

	/**
	 * Notifies when data are ready to be sent to the server.
	 * 
	 * @param bytesToSend The number of bytes to be sent.
	 */
	public void sending(int bytesToSend);

	/**
	 * Notifies when the synchronization recover from an interrupted
	 * synchronization during the sending.
	 * 
	 * @param bytesToSend The number of bytes that still need to be sent.
	 * @param allBytes The total number of bytes to be sent.
	 */
	public void resumeSend(int bytesToSend, int allBytes);

	/**
	 * Notifies when receiving the data from the server.
	 * 
	 * @param bytesToReceive The number of bytes expected.
	 */
	public void receiving(int bytesToReceive);

	/**
	 * Notifies when the synchronization recover from an interrupted
	 * synchronization during the receiving.
	 * 
	 * @param bytesToReceive The number of bytes still expected.
	 * @param allBytes The total number of bytes expected.
	 */
	public void resumeReceive(int bytesToReceive, int allBytes);

	/**
	 * Notifies when the synchronization is over.
	 */
	public void finish();
}
