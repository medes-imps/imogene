package org.imogene.android.database.sqlite;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.imogene.android.Constants;
import org.imogene.android.common.entity.GpsLocation;
import org.imogene.android.common.entity.ImogBean;
import org.imogene.android.common.entity.ImogHelper;
import org.imogene.android.common.model.EntityInfo;
import org.imogene.android.database.ImogBeanCursor;

import android.content.Context;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.location.Location;
import android.net.Uri;
import fr.medes.android.database.sqlite.BaseCursor;
import fr.medes.android.database.sqlite.stmt.QueryBuilder;
import fr.medes.android.util.content.ContentUrisUtils;

public abstract class ImogBeanCursorImpl<T extends ImogBean> extends BaseCursor implements ImogBeanCursor<T> {

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
	public final Date getDeleted() {
		return getDate(ImogBean.Columns.DELETED);
	}

	@Override
	public final boolean getFlagRead() {
		return getBoolean(ImogBean.Columns.FLAG_READ);
	}

	@Override
	public final boolean getFlagSynchronized() {
		return getBoolean(ImogBean.Columns.FLAG_SYNCHRONIZED);
	}

	protected final ImogBean getEntity(String id) {
		ImogOpenHelper helper = ImogOpenHelper.getHelper();
		for (EntityInfo info : ImogHelper.getInstance().getEntityInfos()) {
			QueryBuilder builder = helper.queryBuilder(info.table);
			builder.setCountOf(true);
			builder.where().idEq(id).and().ne(ImogBean.Columns.MODIFIEDFROM, ImogBean.Columns.SYNC_SYSTEM);
			long count = builder.queryForLong();
			if (count == 1) {
				return ImogOpenHelper.fromUri(ContentUrisUtils.withAppendedId(
						ContentUrisUtils.buildUriForFragment(Constants.AUTHORITY, info.table), id));
			}
		}
		return null;
	}

	protected final Uri getEntityUri(Uri contentUri, int columnIndex) {
		String id = getString(columnIndex);
		if (id == null) {
			return null;
		}
		return ContentUrisUtils.withAppendedId(contentUri, id);
	}

	protected final <U extends ImogBean> U getEntity(Uri contentUri, int columnIndex) {
		String id = getString(columnIndex);
		if (id == null) {
			return null;
		}
		return ImogOpenHelper.fromUri(ContentUrisUtils.withAppendedId(contentUri, id));
	}

	protected final Uri getEntityUri(Uri contentUri, String key) {
		ImogOpenHelper helper = ImogOpenHelper.getHelper();
		QueryBuilder builder = helper.queryBuilder(contentUri);
		builder.selectColumns(ImogBean.Columns._ID);
		builder.where().eq(key, getId()).and().ne(ImogBean.Columns.MODIFIEDFROM, ImogBean.Columns.SYNC_SYSTEM);
		String sId = builder.queryForString();
		if (sId != null) {
			return ContentUrisUtils.withAppendedId(contentUri, sId);
		}
		return null;
	}

	protected final <U extends ImogBean> U getEntity(Uri contentUri, String key) {
		ImogOpenHelper helper = ImogOpenHelper.getHelper();
		QueryBuilder builder = helper.queryBuilder(contentUri);
		builder.where().eq(key, getId()).and().ne(ImogBean.Columns.MODIFIEDFROM, ImogBean.Columns.SYNC_SYSTEM);
		ImogBeanCursor<U> c = builder.query();
		U result = null;
		if (c.moveToFirst()) {
			result = c.newBean();
		}
		c.close();
		return result;
	}

	protected final <U extends ImogBean> List<U> getEntities(Uri contentUri, String key) {
		ImogOpenHelper helper = ImogOpenHelper.getHelper();
		QueryBuilder builder = helper.queryBuilder(contentUri);
		builder.where().eq(key, getId()).and().ne(ImogBean.Columns.MODIFIEDFROM, ImogBean.Columns.SYNC_SYSTEM);
		List<U> result = null;
		ImogBeanCursor<U> c = builder.query();
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			if (result == null) {
				result = new ArrayList<U>();
			}
			result.add(c.newBean());
		}
		c.close();
		return result;
	}

	protected final <U extends ImogBean> List<U> getEntities(Uri contentUri, String relTable, String fromKey,
			String toKey) {
		ArrayList<U> result = null;

		ImogOpenHelper helper = ImogOpenHelper.getHelper();

		QueryBuilder subQueryBuilder = helper.queryBuilder(relTable);
		subQueryBuilder.selectColumns(toKey);
		subQueryBuilder.where().eq(fromKey, getId());

		QueryBuilder builder = helper.queryBuilder(contentUri);
		builder.where().in(ImogBean.Columns._ID, subQueryBuilder).and()
				.ne(ImogBean.Columns.MODIFIEDFROM, ImogBean.Columns.SYNC_SYSTEM);

		ImogBeanCursor<U> c = builder.query();
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			if (result == null) {
				result = new ArrayList<U>();
			}
			result.add(c.newBean());
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
				ImogBeanCursor<?> c = (ImogBeanCursor<?>) ImogOpenHelper.getHelper().query(uri);
				String main = c.moveToFirst() ? c.getMainDisplay(context) : " ";
				builder.append(main).append(" ");
				mBuffer.put(uri, main);
				c.close();
			}
		}
	}
}
