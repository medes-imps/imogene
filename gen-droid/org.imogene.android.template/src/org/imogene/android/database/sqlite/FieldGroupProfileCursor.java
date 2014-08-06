package org.imogene.android.database.sqlite;

import org.imogene.android.common.model.FieldGroup;
import org.imogene.android.common.profile.FieldGroupProfile;
import org.imogene.android.common.profile.Profile;
import org.imogene.android.database.DatabaseCache;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteQuery;

public class FieldGroupProfileCursor extends ImogBeanCursorImpl<FieldGroupProfile> {

	public FieldGroupProfileCursor(SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
		super(db, driver, editTable, query);
	}

	public static class Factory implements CursorFactory {
		@Override
		public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery, String editTable, SQLiteQuery query) {
			return new FieldGroupProfileCursor(db, masterQuery, editTable, query);
		}
	}

	@Override
	public FieldGroupProfile newBean() {
		DatabaseCache.getInstance().beginTransaction();
		FieldGroupProfile bean = DatabaseCache.getInstance().get(getId());
		if (bean == null) {
			bean = new FieldGroupProfile(this);
		}
		DatabaseCache.getInstance().endTransaction();
		return bean;
	}

	public final Profile getProfile() {
		return getEntity(Profile.Columns.CONTENT_URI, getColumnIndexOrThrow(FieldGroupProfile.Columns.PROFILE));
	}

	public final FieldGroup getFieldGroup() {
		return getEntity(FieldGroup.Columns.CONTENT_URI, getColumnIndexOrThrow(FieldGroupProfile.Columns.FIELDGROUP));
	}

	public final Boolean getRead() {
		return getAsBoolean(FieldGroupProfile.Columns.READ);
	}

	public final Boolean getWrite() {
		return getAsBoolean(FieldGroupProfile.Columns.WRITE);
	}

	public final Boolean getExport() {
		return getAsBoolean(FieldGroupProfile.Columns.EXPORT);
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
