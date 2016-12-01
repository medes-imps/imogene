package org.imogene.lib.sync.client;

import java.io.InputStream;
import java.io.OutputStream;

import org.imogene.lib.sync.client.params.SyncParams;
import org.imogene.lib.sync.history.SyncHistory;
import org.imogene.lib.sync.serializer.ImogSerializationException;

public interface SyncHandler {

	/**
	 * Get the entities to be sent to the server for synchronization
	 * 
	 * @param os The stream to write the serialized data to.
	 * @param sessionId The session identifier.
	 * @param login The login of the user synchronizing.
	 * @return The number of entities serialized.
	 */
	public int getDataToSynchronize(OutputStream os, String sessionId, String login) throws SynchronizationException;

	/**
	 * Apply the incoming server modification locally.
	 * 
	 * @param data incoming bytes
	 * @return The number of processed entities.
	 * @throws ImogSerializationException
	 */
	public int applyIncomingModifications(InputStream is) throws ImogSerializationException;

	/**
	 * Load the last unsuccessful history record.
	 * 
	 * @return the last unsuccessful history
	 */
	public SyncHistory loadLastError();

	/**
	 * Update the given history in the database.
	 * 
	 * @param history The history to update.
	 */
	public void updateHistory(SyncHistory history);

	/**
	 * Remove the given history from the database.
	 * 
	 * @param history The history to remove.
	 */
	public void deleteHistory(SyncHistory history);

	/**
	 * Reset the synchronization history by removing last error history from the database.
	 */
	public void resetSyncHistory();

	/**
	 * Load the stored synchronization parameters
	 * 
	 * @return The synchronization parameters.
	 */
	public SyncParams loadParams();

	/**
	 * Load the last sync history registered.
	 * 
	 * @return The last history.
	 */
	public SyncHistory loadLastSyncHistory();

}
