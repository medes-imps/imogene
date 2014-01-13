package org.imogene.web.server.security;

import java.util.ArrayList;
import java.util.Collection;

import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.profile.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
public class ImogUserDetails implements UserDetails {

	ImogActor actor;

	public ImogUserDetails(ImogActor actor) {
		this.actor = actor;

	}

	public Collection<GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> ga = new ArrayList<GrantedAuthority>();
		for (Profile role : actor.getProfiles()) {
			ga.add(new SimpleGrantedAuthority(role.getId()));
		}
		return ga;
	}

	public String getPassword() {
		return actor.getPassword();
	}

	public String getUsername() {
		return actor.getLogin();
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		// Date passwordExpirationDate = actor.getPasswordExpirationDate();
		// if(passwordExpirationDate!=null)
		// return passwordExpirationDate.before(new Date());
		// else
		// return false;

		return true;
	}

	public boolean isEnabled() {
		return true;
	}

	public ImogActor getImogActor() {
		return actor;
	}
}
