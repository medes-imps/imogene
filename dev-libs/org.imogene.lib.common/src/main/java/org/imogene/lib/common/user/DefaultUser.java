package org.imogene.lib.common.user;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.imogene.lib.common.entity.ImogActorImpl;

@Entity
@Table(name = "default_user")
public class DefaultUser extends ImogActorImpl {

	private static final long serialVersionUID = 5928417593161219603L;
	
	private String email;

	@Override
	public String getNotificationData(String method) {
		return null;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}