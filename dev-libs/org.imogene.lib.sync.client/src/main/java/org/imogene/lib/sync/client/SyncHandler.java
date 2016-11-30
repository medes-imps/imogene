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

	public SyncHistory loadLastError();

	public void updateHistory(SyncHistory history);

	public SyncParams loadParams();

}
