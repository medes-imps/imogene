package org.imogene.android.database.sqlite;

import org.imogene.android.common.model.CardEntity;
import org.imogene.android.common.model.FieldGroup;
import org.imogene.android.database.DatabaseCache;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteQuery;

public class FieldGroupCursor extends ImogBeanCursorImpl<FieldGroup> {

	public FieldGroupCursor(SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
		super(db, driver, editTable, query);
	}

	public static class Factory implements CursorFactory {
		@Override
		public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery, String editTable, SQLiteQuery query) {
			return new FieldGroupCursor(db, masterQuery, editTable, query);
		}
	}

	@Override
	public FieldGroup newBean() {
		DatabaseCache.getInstance().beginTransaction();
		FieldGroup bean = DatabaseCache.getInstance().get(getId());
		if (bean == null) {
			bean = new FieldGroup(this);
		}
		DatabaseCache.getInstance().endTransaction();
		return bean;
	}

	public final String getName() {
		return getString(FieldGroup.Columns.NAME);
	}

	public final CardEntity getEntity() {
		return getEntity(CardEntity.Columns.CONTENT_URI, getColumnIndexOrThrow(FieldGroup.Columns.ENTITY));
	}

	@Override
	public String getMainDisplay(Context context) {
		return null;
	}

	@Override
	public String getSecondaryDisplay(Context context) {
		return null;
	}

}
