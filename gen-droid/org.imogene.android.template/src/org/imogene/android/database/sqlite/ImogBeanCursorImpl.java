package org.imogene.android.database.sqlite;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.imogene.android.database.ImogBeanCursor;
import org.imogene.android.database.sqlite.stmt.QueryBuilder;
import org.imogene.android.domain.GpsLocation;
import org.imogene.android.domain.ImogBean;
import org.imogene.android.domain.ImogHelper;
import org.imogene.android.util.content.ContentUrisUtils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.location.Location;
import android.net.Uri;
import android.text.TextUtils;

public abstract class ImogBeanCursorImpl extends BaseCursor implements ImogBeanCursor {

	private HashMap<Uri, String> mBuffer;

	public ImogBeanCursorImpl(SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
		super(db, driver, editTable, query);
	}

	@Override
	public void close() {
		super.close();
		if (mBuffer != null) {
			mBuffer.clear();
			mBuffer = null;
		}
	}

	@Override
	public final String getId() {
		return getString(ImogBean.Columns._ID);
	}

	@Override
	public final Date getModified() {
		return getDate(ImogBean.Columns.MODIFIED);
	}

	@Override
	public final String getModifiedBy() {
		return getString(ImogBean.Columns.MODIFIEDBY);
	}

	@Override
	public final String getModifiedFrom() {
		return getString(ImogBean.Columns.MODIFIEDFROM);
	}

	@Override
	public final Date getUploadDate() {
		return getDate(ImogBean.Columns.UPLOADDATE);
	}

	@Override
	public final Date getCreated() {
		return getDate(ImogBean.Columns.CREATED);
	}

	@Override
	public final String getCreatedBy() {
		return getString(ImogBean.Columns.CREATEDBY);
	}

	@Override
	public final boolean getUnread() {
		return getBoolean(ImogBean.Columns.UNREAD);
	}

	@Override
	public final boolean getSynchronized() {
		return getBoolean(ImogBean.Columns.SYNCHRONIZED);
	}

	protected final Uri getEntity(String id) {
		ImogOpenHelper helper = ImogOpenHelper.getHelper();
		for (String table : ImogHelper.getInstance().getAllTables()) {
			QueryBuilder builder = helper.queryBuilder(table);
			builder.setCountOf(true);
			builder.where().idEq(id).and().ne(ImogBean.Columns.MODIFIEDFROM, ImogBean.Columns.SYNC_SYSTEM);
			long count = builder.queryForLong();
			if (count == 1) {
				return ContentUrisUtils.withAppendedId(ContentUrisUtils.buildUriForFragment(table), id);
			}
		}
		return null;
	}

	protected final Uri getEntity(Uri contentUri, String table, int columnIndex) {
		String id = getString(columnIndex);
		if (id == null) {
			return null;
		}
		ImogOpenHelper helper = ImogOpenHelper.getHelper();
		QueryBuilder builder = helper.queryBuilder(table);
		builder.selectColumns(ImogBean.Columns._ID);
		builder.where().idEq(id).and().ne(ImogBean.Columns.MODIFIEDFROM, ImogBean.Columns.SYNC_SYSTEM);
		String sId = builder.queryForString();
		if (sId != null) {
			return ContentUrisUtils.withAppendedId(contentUri, sId);
		}
		return null;
	}

	protected final Uri getEntity(Uri contentUri, String table, String key) {
		ImogOpenHelper helper = ImogOpenHelper.getHelper();
		QueryBuilder builder = helper.queryBuilder(table);
		builder.selectColumns(ImogBean.Columns._ID);
		builder.where().eq(key, getId()).and().ne(ImogBean.Columns.MODIFIEDFROM, ImogBean.Columns.SYNC_SYSTEM);
		String sId = builder.queryForString();
		if (sId != null) {
			return ContentUrisUtils.withAppendedId(contentUri, sId);
		}
		return null;
	}

	protected final List<Uri> getEntities(Uri contentUri, String table, String key) {
		ImogOpenHelper helper = ImogOpenHelper.getHelper();
		QueryBuilder builder = helper.queryBuilder(table);
		builder.selectColumns(ImogBean.Columns._ID);
		builder.where().eq(key, getId()).and().ne(ImogBean.Columns.MODIFIEDFROM, ImogBean.Columns.SYNC_SYSTEM);
		List<Uri> result = null;
		Cursor c = builder.query();
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			String sId = c.getString(0);
			if (!TextUtils.isEmpty(sId)) {
				if (result == null) {
					result = new ArrayList<Uri>();
				}
				result.add(ContentUrisUtils.withAppendedId(contentUri, sId));
			}
		}
		c.close();
		return result;
	}

	protected final List<Uri> getEntities(Uri contentUri, String table, String relTable, String fromKey, String toKey) {
		ArrayList<Uri> result = null;

		ImogOpenHelper helper = ImogOpenHelper.getHelper();
		
		QueryBuilder subQueryBuilder = helper.queryBuilder(relTable);
		subQueryBuilder.selectColumns(toKey);
		subQueryBuilder.where().eq(fromKey, getId());
		
		QueryBuilder builder = helper.queryBuilder(table);
		builder.selectColumns(new String[]{ImogBean.Columns._ID});
		builder.where().in(ImogBean.Columns._ID, subQueryBuilder).and().ne(ImogBean.Columns.MODIFIEDFROM, ImogBean.Columns.SYNC_SYSTEM);

		Cursor c = builder.query();
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			String sId = c.getString(0);
			if (!TextUtils.isEmpty(sId)) {
				if (result == null) {
					result = new ArrayList<Uri>();
				}
				result.add(ContentUrisUtils.withAppendedId(contentUri, sId));
			}
		}
		c.close();

		return result;
	}

	protected final boolean[] getEnumMulti(String key, int size) {
		boolean[] result = new boolean[size];
		String value = getString(key);
		if (value != null && value.length() != 0) {
			String[] sub = value.substring(1, value.length() - 1).split(",");
			if (size != sub.length)
				return result;
			for (int i = 0; i < sub.length; i++)
				result[i] = Boolean.parseBoolean(sub[i].trim());
		}
		return result;
	}

	protected final Location getAsLocation(String columnName) {
		Long rowId = getAsLong(columnName);
		if (rowId != null) {
			return GpsLocation.getLocation(null, rowId.longValue());
		} else {
			return null;
		}
	}

	protected final void buildRelationDisplay(Context context, StringBuilder builder, Uri uri) {
		if (uri != null) {
			if (mBuffer == null) {
				mBuffer = new HashMap<Uri, String>();
			}
			if (mBuffer.containsKey(uri)) {
				builder.append(mBuffer.get(uri)).append(" ");
			} else {
				ImogBeanCursor c = (ImogBeanCursor) ImogOpenHelper.getHelper().query(uri);
				c.moveToFirst();
				String main = c.getMainDisplay(context);
				c.close();
				builder.append(main).append(" ");
				mBuffer.put(uri, main);
			}
		}
	}
}
