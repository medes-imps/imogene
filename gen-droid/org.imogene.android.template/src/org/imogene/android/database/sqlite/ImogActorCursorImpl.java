package org.imogene.android.database.sqlite;

import java.util.List;

import org.imogene.android.common.entity.ImogActor;
import org.imogene.android.common.profile.Profile;
import org.imogene.android.database.ImogActorCursor;

import android.content.Context;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.net.Uri;

public abstract class ImogActorCursorImpl extends ImogEntityCursorImpl implements ImogActorCursor {

	public ImogActorCursorImpl(SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
		super(db, driver, editTable, query);
	}

	@Override
	public final String getLogin() {
		return getString(ImogActor.Columns.LOGIN);
	}

	@Override
	public final byte[] getPassword() {
		return getBlob(ImogActor.Columns.PASSWORD);
	}

	@Override
	public final List<Uri> getProfiles() {
		return getEntities(Profile.Columns.CONTENT_URI, Profile.Columns.TABLE_NAME,
				ImogActor.Columns.TABLE_ACTOR_PROFILES, ImogActor.Columns.TABLE_NAME, Profile.Columns.TABLE_NAME);
	}

	@Override
	public abstract String getUserDisplay(Context context);

}
