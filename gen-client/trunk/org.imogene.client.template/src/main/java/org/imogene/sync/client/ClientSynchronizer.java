package org.imogene.sync.client;

import java.io.File;
import java.util.Properties;

import org.imogene.lib.sync.client.SyncListener;
import org.imogene.lib.sync.client.SynchronizationManager;
import org.imogene.lib.sync.client.parameter.SyncParameter;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;

/**
 * The activator class that controls the plug-in life cycle
 * 
 * @author MEDES-IMPS
 */
public class ClientSynchronizer {

	private SynchronizationManager manager;
	private ClientInitializer ci;
	private SynchroUtils su;
	private Thread syncThread;

	public ClientSynchronizer(String configurationPath) {
		this(new FileSystemResource(configurationPath));
	}

	public ClientSynchronizer(File configuration) {
		this(new FileSystemResource(configuration));
	}

	public ClientSynchronizer(FileSystemResource configuration) {
		injectConfiguration(configuration);
		initSynchronizer();
	}

	public ClientSynchronizer(Properties configuration) {
		injectConfiguration(configuration);
		initSynchronizer();
	}

	private void injectConfiguration(FileSystemResource properties) {
		MyPropertyPlaceholderConfigurer.getInstance().setLocation(properties);
	}

	private void injectConfiguration(Properties properties) {
		MyPropertyPlaceholderConfigurer.getInstance().setProperties(properties);
	}

	private void initSynchronizer() {
		// open/read the application context file
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"application-context.xml", "dao-context.xml",
				"converter-context.xml");

		ci = (ClientInitializer) ctx.getBean("clientInitializer");

		/* initialize */
		ci.initDatase();
		ci.initSyncParameters();
		ci.initDefaultUser();
		
		su = (SynchroUtils) ctx.getBean("synchroUtils");

		manager = (SynchronizationManager) ctx.getBean("synchronizationManager");
	}

	public void synchronize() {
		if (!isRunning()) {
			syncThread = new Thread() {
				public void run() {
					manager.start();
				};
			};
			syncThread.start();
		}
	}

	public void stopSynchronization() {
		manager.stop();
	}

	public boolean isRunning() {
		return syncThread != null && syncThread.isAlive();
	}

	public void addSyncListener(SyncListener listener) {
		manager.addSyncListener(listener);
	}

	public void removeSyncListener(SyncListener listener) {
		manager.removeListener(listener);
	}
	
	public SynchroUtils getSynchroUtils(){
		return su;
	}
	
	/** 
	 * If the synchro process is running in loop mode, 
	 * we stop it to force it to reload the sync parameters.
	 * If 
	 */
	public void parametersUpdated() {
		new Thread(){
			public void run(){
				if(manager.loopMode() && syncThread!=null){
					manager.stop();
					try {
						//LogUtils.logDebug(Activator.PLUGIN_ID, "Waiting for the sync process stops.");
						syncThread.join();					
						//LogUtils.logDebug(Activator.PLUGIN_ID, "Sync process stoped.");
					} catch (InterruptedException e) {						
						e.printStackTrace();
					}					
				}	
				
				SyncParameter sp = ci.getCurrentParameters();
				if(sp.isLoop()){
					//LogUtils.logDebug(Activator.PLUGIN_ID, "Restarting the sync process.");
					synchronize();
				}
			}
		}.start();		
	}
	
	/**
	 * Get the synchro client initializer
	 * @return
	 */
	public ClientInitializer getInitializer(){
		return ci;
	}		
	
	
	public static void main(String[] args) {
		FileSystemResource properties = new FileSystemResource(args[0]);
		ClientSynchronizer cs = new ClientSynchronizer(properties);
		cs.synchronize();
	}

}