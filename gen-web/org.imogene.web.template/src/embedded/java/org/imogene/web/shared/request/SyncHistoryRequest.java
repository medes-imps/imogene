package org.imogene.web.shared.request;

import org.imogene.lib.sync.client.SyncHandler;
import org.imogene.web.server.locator.SpringServiceLocator;
import org.imogene.web.shared.proxy.SyncHistoryProxy;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

@Service(value = SyncHandler.class, locator = SpringServiceLocator.class)
public interface SyncHistoryRequest extends RequestContext {

	Request<SyncHistoryProxy> loadLastSyncHistory();

	Request<Void> resetSyncHistory();

}
