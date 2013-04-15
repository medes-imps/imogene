package org.imogene.sync.client;

import org.imogene.lib.sync.client.parameter.SyncParameter;


public interface ClientInitializer {
	
	public void initDatase();
	
	public void initDefaultUser();

	/**
	 * create the sync parameters
	 */
	public void initSyncParameters();
	
	public SyncParameter getCurrentParameters();
	
	public void updateSyncLoop(boolean loop);
	
	public void updateSyncUrl(String url);
	
	public void updateSyncPeriod(int period);
	
	public void updateSyncParameters(String url, boolean loop, int period);
		
}
