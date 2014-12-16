package org.imogene.lib.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Session information for the current user
 * @author Medes-IMPS
 */
public class SessionInfo implements Serializable {

	private static final long serialVersionUID = -453939375643784554L;

	private String sessionId;
	private ImogActor actor;
	private Date lastLoginDate;

	public SessionInfo() {
	}

	public SessionInfo(String sessionId, ImogActorImpl actor) {
		this.sessionId = sessionId;
		this.actor = actor;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public ImogActorImpl getActor() {
		return (ImogActorImpl) actor;
	}

	public void setActor(ImogActor actor) {
		this.actor = actor;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

}
