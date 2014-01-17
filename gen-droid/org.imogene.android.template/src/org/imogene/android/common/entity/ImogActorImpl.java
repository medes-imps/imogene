package org.imogene.android.common.entity;

import java.util.List;

import org.imogene.android.common.profile.Profile;
import org.imogene.android.database.ImogActorCursor;
import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.database.sqlite.stmt.QueryBuilder;
import org.imogene.android.xml.annotation.XmlAlias;
import org.imogene.android.xml.annotation.XmlConverter;
import org.imogene.android.xml.converters.CollectionConverter;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

public abstract class ImogActorImpl extends ImogEntityImpl implements ImogActor {

	@XmlAlias("login")
	private String login;

	@XmlAlias("password")
	private byte[] password;

	@XmlAlias("profiles")
	@XmlConverter(CollectionConverter.class)
	private List<Uri> profiles;

	public ImogActorImpl() {
	}

	public ImogActorImpl(Bundle bundle) {
		if (bundle.containsKey(ImogActor.Columns.PROFILES)) {
			profiles = bundle.getParcelableArrayList(ImogActor.Columns.PROFILES);
		}
	}

	protected void init(ImogActorCursor cursor) {
		super.init(cursor);
		login = cursor.getLogin();
		password = cursor.getPassword();
		profiles = cursor.getProfiles();
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
	public final void setProfiles(List<Uri> profiles) {
		this.profiles = profiles;
	}

	@Override
	public final List<Uri> getProfiles() {
		return profiles;
	}

	@Override
	protected void addValues(Context context, ContentValues values) {
		if (!TextUtils.isEmpty(login) && password != null && password.length > 0) {
			values.put(ImogActor.Columns.LOGIN, login);
			values.put(ImogActor.Columns.PASSWORD, password);
		}
	}

	@Override
	protected void postCommit(Context context) {
		QueryBuilder builder = ImogOpenHelper.getHelper().queryBuilder(ImogActor.Columns.TABLE_ACTOR_PROFILES);
		builder.where().eq(ImogActor.Columns.TABLE_NAME, getId());
		builder.delete();
		if (profiles != null) {
			ContentValues v = new ContentValues();
			v.put(ImogActor.Columns.TABLE_NAME, getId());
			for (Uri uri : profiles) {
				v.put(Profile.Columns.TABLE_NAME, uri.getLastPathSegment());
				ImogOpenHelper.getHelper().insert(ImogActor.Columns.TABLE_ACTOR_PROFILES, v);
			}
		}
		super.postCommit(context);
	}

	@Override
	public void reset() {
		super.reset();
		profiles = null;
	}

}
