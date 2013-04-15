package org.imogene.sync.client;

import org.imogene.lib.sync.history.SyncHistory;
import org.imogene.lib.sync.history.SyncHistoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class SynchroUtils {
	
	@Autowired
	@Qualifier(value = "syncHistoryDao")
	private SyncHistoryDao syncHistoryDao;
	
	
	public SyncHistory lastOk(){
		return syncHistoryDao.loadLastOk();
	}
	
	public SyncHistory lastError(){
		return syncHistoryDao.loadLastError();
	}
	
}
