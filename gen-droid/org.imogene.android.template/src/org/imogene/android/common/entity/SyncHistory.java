package org.imogene.android.common.entity;

import java.util.UUID;

import org.imogene.android.database.sqlite.ImogOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import fr.medes.android.database.sqlite.stmt.QueryBuilder;

public class SyncHistory {

	public static class Columns implements BaseColumns {
		public static final String TABLE_NAME = "synchistory";

		public static final String ID = "id";
		public static final String DATE = "date";
		public static final String STATUS = "status";
		public static final String LEVEL = "level";

		public static final int STATUS_OK = 0;
		public static final int STATUS_ERROR = 1;

		public static final int LEVEL_SEND = 0;
		public static final int LEVEL_RECEIVE = 1;

		public static final String WHERE_ERROR = STATUS + "=" + STATUS_ERROR;
	}

	public static SyncHistory getLastErrorSyncHistory(Context context) {
		ImogOpenHelper helper = ImogOpenHelper.getHelper();
		QueryBuilder builder = helper.queryBuilder(Columns.TABLE_NAME);
		builder.where().eq(Columns.STATUS, Columns.STATUS_ERROR);
		Cursor cursor = builder.query();
		try {
			if (cursor.moveToFirst()) {
				return new SyncHistory(cursor);
			}
		} finally {
			cursor.close();
		}
		return null;
	}

	public SyncHistory() {

	}

	protected SyncHistory(Cursor c) {
		_id = c.getLong(c.getColumnIndexOrThrow(Columns._ID));
		id = UUID.fromString(c.getString(c.getColumnIndexOrThrow(Columns.ID)));
		date = c.getLong(c.getColumnIndexOrThrow(Columns.DATE));
		status = c.getInt(c.getColumnIndexOrThrow(Columns.STATUS));
		level = c.getInt(c.getColumnIndexOrThrow(Columns.LEVEL));
	}

	public long _id = -1;
	public UUID id;
	public long date;
	public int status;
	public int level;

	public void saveOrUpdate(Context context) {
		ContentValues values = new ContentValues();
		values.put(Columns.ID, id.toString());
		values.put(Columns.DATE, date);
		values.put(Columns.STATUS, status);
		values.put(Columns.LEVEL, level);

		if (_id != -1) {
			QueryBuilder builder = ImogOpenHelper.getHelper().queryBuilder(Columns.TABLE_NAME);
			builder.where().idEq(_id);
			builder.update(values);
		} else {
			_id = ImogOpenHelper.getHelper().insert(Columns.TABLE_NAME, values);
		}
	}

}
