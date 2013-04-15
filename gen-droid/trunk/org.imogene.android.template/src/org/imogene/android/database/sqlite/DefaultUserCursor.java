package org.imogene.android.database.sqlite;

import org.imogene.android.domain.DefaultUser;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteQuery;

public class DefaultUserCursor extends ImogActorCursorImpl {

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
		return new DefaultUser(this);
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
