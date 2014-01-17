package org.imogene.android.database.sqlite;

import org.imogene.android.common.model.CardEntity;
import org.imogene.android.common.model.FieldGroup;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteQuery;
import android.net.Uri;

public class FieldGroupCursor extends ImogBeanCursorImpl {

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
		return new FieldGroup(this);
	}

	public final String getName() {
		return getString(FieldGroup.Columns.NAME);
	}

	public final Uri getEntity() {
		return getEntity(CardEntity.Columns.CONTENT_URI, CardEntity.Columns.TABLE_NAME,
				getColumnIndexOrThrow(FieldGroup.Columns.ENTITY));
	}

	@Override
	public String getMainDisplay(Context context) {
		StringBuilder str = new StringBuilder();
		buildRelationDisplay(context, str, getEntity());
		String name = getName();
		if (name != null) {
			str.append(name);
			str.append(" ");
		}
		return str.toString();
	}

	@Override
	public String getSecondaryDisplay(Context context) {
		StringBuilder str = new StringBuilder();
		return str.toString();
	}

}
