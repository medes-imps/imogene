package org.imogene.web.shared.proxy;

import java.util.Date;

import org.imogene.lib.sync.history.SyncHistory;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyFor(value = SyncHistory.class)
public interface SyncHistoryProxy extends ValueProxy {

	public Date getTime();

	public int getStatus();

	public int getLevel();

}
