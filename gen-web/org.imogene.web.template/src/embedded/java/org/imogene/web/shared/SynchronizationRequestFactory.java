package org.imogene.web.shared;

import org.imogene.web.shared.request.SyncHistoryRequest;

import com.google.web.bindery.requestfactory.shared.RequestFactory;

public interface SynchronizationRequestFactory extends RequestFactory {

	SyncHistoryRequest syncHistoryRequest();

}
