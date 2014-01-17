package org.imogene.android.database.sqlite;

import org.imogene.android.common.model.FieldGroup;
import org.imogene.android.common.profile.FieldGroupProfile;
import org.imogene.android.common.profile.Profile;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteQuery;
import android.net.Uri;

public class FieldGroupProfileCursor extends ImogBeanCursorImpl {

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
		return new FieldGroupProfile(this);
	}

	public final Uri getProfile() {
		return getEntity(Profile.Columns.CONTENT_URI, Profile.Columns.TABLE_NAME,
				getColumnIndexOrThrow(FieldGroupProfile.Columns.PROFILE));
	}

	public final Uri getFieldGroup() {
		return getEntity(FieldGroup.Columns.CONTENT_URI, FieldGroup.Columns.TABLE_NAME,
				getColumnIndexOrThrow(FieldGroupProfile.Columns.FIELDGROUP));
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
		StringBuilder str = new StringBuilder();
		buildRelationDisplay(context, str, getProfile());
		buildRelationDisplay(context, str, getFieldGroup());
		return str.toString();
	}

	@Override
	public String getSecondaryDisplay(Context context) {
		StringBuilder str = new StringBuilder();
		return str.toString();
	}

}
