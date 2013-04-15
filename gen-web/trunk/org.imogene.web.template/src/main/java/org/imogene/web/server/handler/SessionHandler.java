package org.imogene.web.server.handler;

import javax.servlet.http.HttpSession;

import org.imogene.lib.common.entity.ImogActorImpl;
import org.imogene.lib.common.entity.SessionInfo;
import org.imogene.web.server.security.ImogSecurityPolicy;
import org.imogene.web.server.util.HttpSessionUtil;
import org.imogene.web.server.util.ServerConstants;

/**
 * Handler to manage session information
 * @author MEDES-IMPS
 */
public class SessionHandler {
	
	

	/**
	 * Gets the current user information from session
	 */
	public SessionInfo getSessionInfo() {
		HttpSession session = HttpSessionUtil.getHttpSession();
		ImogActorImpl user = (ImogActorImpl) session.getAttribute(ServerConstants.SESSION_USER);
		
		if(user!=null && user.getLogin()!=null && !user.getLogin().isEmpty()) {			
			SessionInfo sessionInfo = new SessionInfo();
			sessionInfo.setSessionId(session.getId());
			sessionInfo.setActor(user);			
			return sessionInfo;
		}
		else
			return null;
	}
	
	/**
	 * Disconnects the current user
	 */
	public void disconnect() {
		HttpSessionUtil.getHttpSession().invalidate();
	}
	
	/**
	 * Validates an existing session
	 * @param sessionId the session id
	 * @return The associated ImogActor or null if the session is not validated
	 */
	public Boolean validateSession(String sessionId) {

		HttpSession session = HttpSessionUtil.getHttpSession();

		if (sessionId.equals(session.getId())) {
			return new Boolean(true);
		} else {
			return new Boolean(false);
		}
	}


}
