package org.imogene.android.database.sqlite;

import org.imogene.android.database.ImogActorCursor;
import org.imogene.android.domain.ImogActor;

import android.content.Context;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;

public abstract class ImogActorCursorImpl extends ImogEntityCursorImpl implements ImogActorCursor {
	
	public ImogActorCursorImpl(SQLiteDatabase db, SQLiteCursorDriver driver,
			String editTable, SQLiteQuery query) {
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
	public final String getRoles() {
		return getString(ImogActor.Columns.ROLES);
	}

	@Override
	public abstract String getUserDisplay(Context context);

}
