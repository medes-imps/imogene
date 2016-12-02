package org.imogene.web.shared;

import org.imogene.web.shared.request.SyncStatusRequest;

import com.google.web.bindery.requestfactory.shared.RequestFactory;

public interface SynchronizationRequestFactory extends RequestFactory {

	SyncStatusRequest syncHistoryRequest();

}
