package org.imogene.android.database.sqlite;

import org.imogene.android.domain.DynamicFieldInstance;
import org.imogene.android.domain.DynamicFieldTemplate;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteQuery;
import android.net.Uri;

public class DynamicFieldInstanceCursor extends ImogBeanCursorImpl {

	public DynamicFieldInstanceCursor(SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
		super(db, driver, editTable, query);
	}

	public static class Factory implements CursorFactory {
		@Override
		public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery, String editTable, SQLiteQuery query) {
			return new DynamicFieldInstanceCursor(db, masterQuery, editTable, query);
		}
	}

	@Override
	public DynamicFieldInstance newBean() {
		return new DynamicFieldInstance(this);
	}

	public final Uri getFieldTemplate() {
		return getEntity(DynamicFieldTemplate.Columns.CONTENT_URI, DynamicFieldTemplate.Columns.TABLE_NAME,
				getColumnIndexOrThrow(DynamicFieldInstance.Columns.FIELDTEMPLATE));
	}

	public final String getFieldValue() {
		return getString(DynamicFieldInstance.Columns.FIELDVALUE);
	}
	
	public final Uri getFormInstance() {
		return getEntity(getString(DynamicFieldInstance.Columns.FORMINSTANCE));
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
