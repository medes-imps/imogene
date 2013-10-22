package org.imogene.lib.sync.client;


/**
 * Interface that describes a synchronizer.
 * 
 * @author MEDES-IMPS
 * 
 */
public interface OptimizedSynchronizer {

	/**
	 * Add a synchronization status listener
	 * 
	 * @param listener the listener to add
	 */
	public void addSyncListener(SyncListener listener);

	/**
	 * Remove a registered listener
	 * 
	 * @param listener the listener to remove
	 */
	public void removeListener(SyncListener listener);

	/**
	 * Launch the synchronization process
	 */
	public void synchronize();

}