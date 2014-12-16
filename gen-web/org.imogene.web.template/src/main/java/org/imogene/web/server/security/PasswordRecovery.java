package org.imogene.web.server.security;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User request to change its password
 * @author yann
 */
@Entity
@Table(name = "imog_passwordrecovery")
public class PasswordRecovery {

	private Date requested;

	private Date modified;

	private String userId;

	@Id
	private String token;

	public Date getRequested() {
		return requested;
	}

	public void setRequested(Date requested) {
		this.requested = requested;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

}
