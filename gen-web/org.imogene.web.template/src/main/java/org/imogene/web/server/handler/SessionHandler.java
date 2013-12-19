package org.imogene.web.server.handler;

import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.entity.SessionInfo;
import org.imogene.web.server.util.HttpSessionUtil;

/**
 * Handler to manage session information
 * 
 * @author MEDES-IMPS
 */
public class SessionHandler {

	/**
	 * Gets the current user information from session
	 */
	public SessionInfo getSessionInfo() {
		ImogActor user = HttpSessionUtil.getCurrentUser();

		if (user != null && user.getLogin() != null && !user.getLogin().isEmpty()) {
			SessionInfo sessionInfo = new SessionInfo();
			sessionInfo.setSessionId(HttpSessionUtil.getHttpSessionId());
			sessionInfo.setActor(user);
			return sessionInfo;
		} else
			return null;
	}

	/**
	 * Disconnects the current user
	 */
	public void disconnect() {
		HttpSessionUtil.invalidate();
	}

	/**
	 * Validates an existing session
	 * 
	 * @param sessionId the session id
	 * @return The associated ImogActor or null if the session is not validated
	 */
	public Boolean validateSession(String sessionId) {
		if (sessionId.equals(HttpSessionUtil.getHttpSessionId())) {
			return new Boolean(true);
		} else {
			return new Boolean(false);
		}
	}

}
