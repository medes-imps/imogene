package org.imogene.android.database.sqlite;

import org.imogene.android.common.dynamicfields.DynamicFieldInstance;
import org.imogene.android.common.dynamicfields.DynamicFieldTemplate;
import org.imogene.android.common.entity.ImogBean;
import org.imogene.android.database.DatabaseCache;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteQuery;

public class DynamicFieldInstanceCursor extends ImogBeanCursorImpl<DynamicFieldInstance> {

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
		DatabaseCache.getInstance().beginTransaction();
		DynamicFieldInstance bean = DatabaseCache.getInstance().get(getId());
		if (bean == null) {
			bean = new DynamicFieldInstance(this);
		}
		DatabaseCache.getInstance().endTransaction();
		return bean;
	}

	public final DynamicFieldTemplate getFieldTemplate() {
		return getEntity(DynamicFieldTemplate.Columns.CONTENT_URI,
				getColumnIndexOrThrow(DynamicFieldInstance.Columns.FIELDTEMPLATE));
	}

	public final String getFieldValue() {
		return getString(DynamicFieldInstance.Columns.FIELDVALUE);
	}

	public final ImogBean getFormInstance() {
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
