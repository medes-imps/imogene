package org.imogene.lib.sync.session;

public interface SyncSessionDao {

	/**
	 * Save a sync session item
	 * 
	 * @param session the session item to save
	 */
	public void saveOrUpdate(SyncSession session);

	/**
	 * Load the specified session
	 * 
	 * @param sessionId the sessionId;
	 */
	public SyncSession load(String id);

	/**
	 * Delete a sync session
	 * 
	 * @param session the session to delete
	 */
	public void delete(SyncSession session);

	/**
	 * Delete all terminated session
	 */
	public void clearTerminated();

	/**
	 * Check if the session is valid
	 * 
	 * @param sessionId the sesionId;
	 * @return true if the session is valid
	 */
	public boolean isValid(String id);

}
