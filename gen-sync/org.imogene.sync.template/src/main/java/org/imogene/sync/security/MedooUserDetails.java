package org.imogene.sync.security;

import java.util.ArrayList;
import java.util.Collection;

import org.imogene.lib.common.entity.ImogActor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
public class MedooUserDetails implements UserDetails {

	ImogActor actor;

	public MedooUserDetails(ImogActor actor) {
		this.actor = actor;

	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> ga = new ArrayList<GrantedAuthority>();
		/*
		 * for(MedooRole role : actor.getRoles()){ ga.add(new
		 * GrantedAuthorityImpl(role.getId())); }
		 */
		return ga;
	}

	@Override
	public String getPassword() {
		return actor.getPassword();
	}

	@Override
	public String getUsername() {
		return actor.getLogin();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public ImogActor getMedooActor() {
		return actor;
	}

}
