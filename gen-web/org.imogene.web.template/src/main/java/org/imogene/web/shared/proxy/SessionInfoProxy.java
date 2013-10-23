package org.imogene.web.shared.proxy;

import org.imogene.lib.common.entity.SessionInfo;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;



@ProxyFor(value=SessionInfo.class)
public interface SessionInfoProxy extends ValueProxy {
	
	public String getSessionId();

	public void setSessionId(String sessionId);

	public ImogActorProxy getActor();

	public void setActor(ImogActorProxy actor);
	
}
