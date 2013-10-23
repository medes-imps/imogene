package org.imogene.lib.sync.client;

/**
 * Interface that describes a synchronization manager to handle synchronization
 * process. This was created to get around transactions issues due to Spring.
 * <blockquote> In proxy mode (which is the default), only external method calls
 * coming in through the proxy are intercepted. This means that self-invocation,
 * in effect, a method within the target object calling another method of the
 * target object, will not lead to an actual transaction at runtime even if the
 * invoked method is marked with {@code @Transactional}. </blockquote>
 * 
 * @author MEDES-IMPS
 * 
 */
public interface SynchronizationManager {

	/**
	 * Add a synchronization listener.
	 * 
	 * @param listener The listener to callback during the synchronization.
	 */
	public void addSyncListener(SyncListener listener);

	/**
	 * Remove a registered listener.
	 * 
	 * @param listener The listener to remove.
	 */
	public void removeListener(SyncListener listener);

	/**
	 * Start the synchronization process
	 */
	public void start();

	/**
	 * Stop the synchronization process
	 */
	public void stop();

	/**
	 * Get the status of the synchronizer
	 * 
	 * @return true if the synchronizer is running, false otherwise.
	 */
	public boolean isRunning();

	/**
	 * Indicates whether the loop mode is enabled or not.
	 * 
	 * @return true if enabled.
	 */
	public boolean loopMode();

}
