package org.imogene.web.shared.request;

import org.imogene.web.server.locator.SpringServiceLocator;
import org.imogene.web.server.sync.SyncStatusHandler;
import org.imogene.web.shared.proxy.SyncStatusProxy;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

@Service(value = SyncStatusHandler.class, locator = SpringServiceLocator.class)
public interface SyncStatusRequest extends RequestContext {

	Request<SyncStatusProxy> getSyncStatus();

	Request<Void> resetSyncHistory();

}
