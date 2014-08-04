package org.imogene.android.common.entity;

import java.util.List;

import org.imogene.android.common.profile.Profile;

public interface ImogActor extends ImogEntity {

	public static interface Columns extends ImogBean.Columns {
		public static final String TABLE_NAME = "imogactor";
		public static final String LOGIN = "login";
		public static final String PASSWORD = "password";
		public static final String PROFILES = "profiles";

		public static final String TABLE_ACTOR_PROFILES = "imogactor_profiles";
	}

	public void setLogin(String login);

	public String getLogin();

	public void setPassword(byte[] password);

	public byte[] getPassword();

	public List<Profile> getProfiles();

	public void setProfiles(List<Profile> profiles);

}
