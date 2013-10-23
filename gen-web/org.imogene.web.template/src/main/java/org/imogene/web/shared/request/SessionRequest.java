package org.imogene.web.shared.request;

import org.imogene.web.server.handler.SessionHandler;
import org.imogene.web.server.locator.SpringServiceLocator;
import org.imogene.web.shared.proxy.SessionInfoProxy;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;



@Service(value= SessionHandler.class, locator = SpringServiceLocator.class)
public interface SessionRequest extends RequestContext {
	
	
	Request<SessionInfoProxy> getSessionInfo();
	Request<Void> disconnect();
	Request<Boolean> validateSession(String sessionId);

}