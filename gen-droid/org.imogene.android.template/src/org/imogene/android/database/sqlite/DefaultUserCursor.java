package org.imogene.android.database.sqlite;

import org.imogene.android.common.entity.DefaultUser;
import org.imogene.android.database.DatabaseCache;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteQuery;

public class DefaultUserCursor extends ImogActorCursorImpl<DefaultUser> {

	public static class Factory implements CursorFactory {

		@Override
		public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery, String editTable, SQLiteQuery query) {
			return new DefaultUserCursor(db, masterQuery, editTable, query);
		}

	}

	public DefaultUserCursor(SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
		super(db, driver, editTable, query);
	}

	@Override
	public DefaultUser newBean() {
		DatabaseCache.getInstance().beginTransaction();
		DefaultUser bean = DatabaseCache.getInstance().get(getId());
		if (bean == null) {
			bean = new DefaultUser(this);
		}
		DatabaseCache.getInstance().endTransaction();
		return bean;
	}

	@Override
	public String getMainDisplay(Context context) {
		return null;
	}

	@Override
	public String getSecondaryDisplay(Context context) {
		return null;
	}

	@Override
	public String getUserDisplay(Context context) {
		return null;
	}

}
