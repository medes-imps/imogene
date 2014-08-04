package org.imogene.android.common.entity;

import java.util.List;

import org.imogene.android.common.profile.Profile;
import org.imogene.android.database.ImogActorCursor;
import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.xml.converters.CollectionConverter;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import fr.medes.android.database.sqlite.stmt.QueryBuilder;
import fr.medes.android.xml.annotation.XmlAlias;
import fr.medes.android.xml.annotation.XmlConverter;

public abstract class ImogActorImpl extends ImogEntityImpl implements ImogActor {

	@XmlAlias("login")
	private String login;

	@XmlAlias("password")
	private byte[] password;

	@XmlAlias("profiles")
	@XmlConverter(CollectionConverter.class)
	private List<Profile> profiles;

	public ImogActorImpl(Bundle bundle) {
		super(bundle);
		if (bundle.containsKey(ImogActor.Columns.PROFILES)) {
			profiles = bundle.getParcelableArrayList(ImogActor.Columns.PROFILES);
		}
	}

	public ImogActorImpl(Parcel in) {
		super(in);
		login = in.readString();
	}

	public ImogActorImpl() {
	}

	public ImogActorImpl(ImogActorCursor<? extends ImogActor> cursor) {
		super(cursor);
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
	public final void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	@Override
	public final List<Profile> getProfiles() {
		return profiles;
	}

	@Override
	protected void addValues(Context context, ContentValues values) {
		super.addValues(context, values);
		values.put(ImogActor.Columns.LOGIN, login);
		values.put(ImogActor.Columns.PASSWORD, password);
	}

	@Override
	protected void postCommit(Context context) {
		super.postCommit(context);
		QueryBuilder builder = ImogOpenHelper.getHelper().queryBuilder(ImogActor.Columns.TABLE_ACTOR_PROFILES);
		builder.where().eq(ImogActor.Columns.TABLE_NAME, getId());
		builder.delete();
		if (profiles != null) {
			ContentValues v = new ContentValues();
			v.put(ImogActor.Columns.TABLE_NAME, getId());
			for (Profile profile : profiles) {
				v.put(Profile.Columns.TABLE_NAME, profile.getId());
				ImogOpenHelper.getHelper().insert(ImogActor.Columns.TABLE_ACTOR_PROFILES, v);
			}
		}
	}

	@Override
	public void reset() {
		super.reset();
		profiles = null;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
	}

}
