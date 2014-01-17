package org.imogene.android.database.sqlite;

import java.util.List;

import org.imogene.android.common.profile.EntityProfile;
import org.imogene.android.common.profile.FieldGroupProfile;
import org.imogene.android.common.profile.Profile;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteQuery;
import android.net.Uri;

public class ProfileCursor extends ImogBeanCursorImpl {

	public ProfileCursor(SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
		super(db, driver, editTable, query);
	}

	public static class Factory implements CursorFactory {
		@Override
		public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery, String editTable, SQLiteQuery query) {
			return new ProfileCursor(db, masterQuery, editTable, query);
		}
	}

	@Override
	public Profile newBean() {
		return new Profile(this);
	}

	public final String getName() {
		return getString(Profile.Columns.NAME);
	}

	public final List<Uri> getEntityProfiles() {
		return getEntities(EntityProfile.Columns.CONTENT_URI, EntityProfile.Columns.TABLE_NAME,
				EntityProfile.Columns.PROFILE);
	}

	public final List<Uri> getFieldGroupProfiles() {
		return getEntities(FieldGroupProfile.Columns.CONTENT_URI, FieldGroupProfile.Columns.TABLE_NAME,
				FieldGroupProfile.Columns.PROFILE);
	}

	@Override
	public String getMainDisplay(Context context) {
		StringBuilder str = new StringBuilder();
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
