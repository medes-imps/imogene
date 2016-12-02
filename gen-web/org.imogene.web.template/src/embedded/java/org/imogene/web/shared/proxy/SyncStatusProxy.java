package org.imogene.web.shared.proxy;

import java.util.Date;

import org.imogene.web.server.sync.SyncStatus;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyFor(value = SyncStatus.class)
public interface SyncStatusProxy extends ValueProxy {

	public Date getLastSyncTime();

	public int getCurrentStatus();

}
