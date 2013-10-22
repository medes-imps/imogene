package org.imogene.lib.sync.history;

import java.util.List;

public interface SyncHistoryDao {

	/**
	 * Load all history records
	 * 
	 * @return the list of histories
	 */
	public List<SyncHistory> load();

	/**
	 * Load all history records for a given terminal
	 * 
	 * @param terminalId the terminal id
	 * @return the list of histories
	 */
	public List<SyncHistory> load(String terminalId);

	/**
	 * Load the last successful history record
	 * 
	 * @return the last successful history
	 */
	public SyncHistory loadLastOk();

	/**
	 * Load the last successful history record for the specified terminal.
	 * 
	 * @param terminalId the terminal id
	 * @return the last successful history
	 */
	public SyncHistory loadLastOk(String terminalId);

	/**
	 * Load the last unsuccessful history record.
	 * 
	 * @return the last unsuccessful history
	 */
	public SyncHistory loadLastError();

	/**
	 * Save a synchronization history item
	 * 
	 * @param history the history item to save
	 */
	public void saveOrUpdate(SyncHistory history);

	/**
	 * For tests: Delete all stored histories
	 */
	public void delete();

	/**
	 * Delete old Histories
	 */
	public void deleteOld();

	/**
	 * Delete old Histories for the specified terminal
	 * 
	 * @param terminalId the terminal id
	 */
	public void deleteOld(String terminalId);

}
