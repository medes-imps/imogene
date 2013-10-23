package org.imogene.android.domain;


import org.imogene.android.database.ImogActorCursor;
import org.imogene.android.xml.annotation.XmlAlias;
import org.imogene.android.xml.annotation.XmlConverter;
import org.imogene.android.xml.converters.RolesConverter;

import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;

public abstract class ImogActorImpl extends ImogEntityImpl implements ImogActor {

	@XmlAlias("login")
	private String login = null;
	
	@XmlAlias("password")
	private byte[] password = null;
	
	@XmlAlias("roles")
	@XmlConverter(RolesConverter.class)
	private String roles = null;
	
	protected void init(ImogActorCursor cursor) {
		super.init(cursor);
		login = cursor.getLogin();
		password = cursor.getPassword();
		roles = cursor.getRoles();
	};
	
	@Override
	public final void setLogin(String login) {
		this.login = login;
	}
	
	@Override
	public final String getLogin() {
		return login;
	}
	
	@Override
	public final void setPassword(byte[] password) {
		this.password = password;
	}
	
	@Override
	public final byte[] getPassword() {
		return password;
	}
	
	@Override
	public final void setRoles(String roles) {
		this.roles = roles;
	}
	
	@Override
	public final String getRoles() {
		return roles;
	}
	
	@Override
	protected void addValues(Context context, ContentValues values) {
		if (!TextUtils.isEmpty(login) && password != null && password.length > 0) {
			values.put(ImogActor.Columns.LOGIN, login);
			values.put(ImogActor.Columns.PASSWORD, password);
			values.put(ImogActor.Columns.ROLES, roles);
		}
	}
	
}
