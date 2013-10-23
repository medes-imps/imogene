package org.imogene.lib.sync.client.parameter;

/**
 * This interface describe the DAO that permits to access the application
 * parameters.
 * 
 * @author medes @ medoo.fr
 */
public interface SyncParameterDao {

	/**
	 * load the actual parameters
	 * 
	 * @return the synchronization parameters
	 */
	public SyncParameter load();

	/**
	 * Store the new synchronization parameters
	 * 
	 * @param parameters the new synchronization parameters
	 */
	public void store(SyncParameter parameters);
}
