package org.imogene.android.database.sqlite;

import org.imogene.android.common.model.CardEntity;
import org.imogene.android.common.profile.EntityProfile;
import org.imogene.android.common.profile.Profile;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteQuery;
import android.net.Uri;

public class EntityProfileCursor extends ImogBeanCursorImpl {

	public EntityProfileCursor(SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
		super(db, driver, editTable, query);
	}

	public static class Factory implements CursorFactory {
		@Override
		public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery, String editTable, SQLiteQuery query) {
			return new EntityProfileCursor(db, masterQuery, editTable, query);
		}
	}

	@Override
	public EntityProfile newBean() {
		return new EntityProfile(this);
	}

	public final Uri getProfile() {
		return getEntity(Profile.Columns.CONTENT_URI, Profile.Columns.TABLE_NAME,
				getColumnIndexOrThrow(EntityProfile.Columns.PROFILE));
	}

	public final Uri getEntity() {
		return getEntity(CardEntity.Columns.CONTENT_URI, CardEntity.Columns.TABLE_NAME,
				getColumnIndexOrThrow(EntityProfile.Columns.ENTITY));
	}

	public final Boolean getCreate() {
		return getAsBoolean(EntityProfile.Columns.CREATE);
	}

	public final Boolean getDirectAccess() {
		return getAsBoolean(EntityProfile.Columns.DIRECTACCESS);
	}

	public final Boolean getDelete() {
		return getAsBoolean(EntityProfile.Columns.DELETE);
	}

	public final Boolean getExport() {
		return getAsBoolean(EntityProfile.Columns.EXPORT);
	}

	@Override
	public String getMainDisplay(Context context) {
		StringBuilder str = new StringBuilder();
		buildRelationDisplay(context, str, getProfile());
		buildRelationDisplay(context, str, getEntity());
		return str.toString();
	}

	@Override
	public String getSecondaryDisplay(Context context) {
		StringBuilder str = new StringBuilder();
		return str.toString();
	}

}
