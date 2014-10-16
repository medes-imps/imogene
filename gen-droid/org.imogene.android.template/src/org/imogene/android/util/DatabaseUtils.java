package org.imogene.android.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.imogene.android.Constants;
import org.imogene.android.Constants.Databases;
import org.imogene.android.Constants.Paths;
import org.imogene.android.common.entity.ImogBean;
import org.imogene.android.common.entity.ImogEntity;
import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.preference.Preferences;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import fr.medes.android.database.sqlite.stmt.QueryBuilder;
import fr.medes.android.util.content.ContentUrisUtils;

public class DatabaseUtils {

	private static final String SQLITE_MASTER = "sqlite_master";
	private static final String KEY_NAME = "name";
	private static final String KEY_TYPE = "type";
	private static final String KEY_SQL = "sql";
	private static final String TYPE_TABLE = "table";
	private static final String ANDROID_METADATA = "android_metadata";

	/**
	 * Update the column {@link ImogBean.Columns#FLAG_READ} of the entity represented by the {@link Uri} with the given
	 * value.
	 * 
	 * @param res The current {@link ContentResolver}.
	 * @param uri The {@link Uri} of the element to update.
	 * @param unread The marker for the column.
	 */
	public static void markRead(ContentResolver res, Uri uri, boolean read) {
		ContentValues values = new ContentValues();
		values.put(ImogBean.Columns.FLAG_READ, read ? 1 : 0);
		res.update(uri, values, ImogBean.Columns.FLAG_READ + "=" + (read ? 0 : 1), null);
	}

	/**
	 * Update the column {@link ImogBean.Columns#FLAG_SYNCHRONIZED} with the given value for the entities represented by
	 * the {@link Uri} and which last modification date column {@link ImogBean.Columns#MODIFIED} is below the given
	 * time.
	 * 
	 * @param res The current {@link ContentResolver}.
	 * @param uri The {@link Uri} of the element to update.
	 * @param time The last synchronization time.
	 * @param sent The marker for the column.
	 */
	public static void markSent(ContentResolver res, Uri uri, long time, boolean sent) {
		ContentValues values = new ContentValues();
		values.put(ImogBean.Columns.FLAG_SYNCHRONIZED, sent ? 1 : 0);
		String where = "(" + ImogBean.Columns.MODIFIED + " < ?) AND (" + ImogBean.Columns.FLAG_SYNCHRONIZED + " = ?)";
		String[] args = new String[] { Long.toString(time), sent ? "0" : "1" };
		res.update(uri, values, where, args);
	}

	/**
	 * Should save the database of the application to the folder given by {@link Paths#PATH_BACKUP}.
	 * 
	 * @param context The current context.
	 */
	@Deprecated
	public static void dbBackup(Context context) {
		Paths.PATH_BACKUP.mkdirs();

		String name = "backup_" + System.currentTimeMillis() + ".db";
		File backup = new File(Paths.PATH_BACKUP, name);
		File db = context.getDatabasePath(Databases.DATABASE_NAME);

		try {
			FileInputStream fis = new FileInputStream(db);
			FileOutputStream fos = new FileOutputStream(backup);

			byte[] bytes = new byte[1024];
			while (fis.read(bytes) != -1) {
				fos.write(bytes);
			}
			fos.flush();
			fos.close();
			fis.close();
		} catch (IOException e) {
			// Maybe next time
		}
	}

	/**
	 * Delete all the entries in the database.
	 * 
	 * @param context
	 */
	public static void deleteAll(Context context) {
		QueryBuilder builder = ImogOpenHelper.getHelper().queryBuilder(SQLITE_MASTER);
		builder.selectColumns(KEY_NAME);
		builder.distinct();
		builder.where().eq(KEY_TYPE, TYPE_TABLE);

		Cursor c = builder.query();
		while (c.moveToNext()) {
			String table = c.getString(0);
			if (!ANDROID_METADATA.equals(table)) {
				ImogOpenHelper.getHelper().delete(table, null, null);
			}
		}
		c.close();

		context.getContentResolver()
				.notifyChange(ContentUrisUtils.buildUriForFragment(Constants.AUTHORITY, null), null);
		Preferences.getPreferences(context).setSyncTerminal(UUID.randomUUID().toString());
	}

	/**
	 * Check is there is still any unsynchronized entity in the database.
	 * 
	 * @param context
	 * @return true if there is at least one unsynchronized entity, false otherwise.
	 */
	public static boolean hasUnSync() {
		QueryBuilder builder = ImogOpenHelper.getHelper().queryBuilder(SQLITE_MASTER);
		builder.selectColumns(KEY_NAME);
		builder.where().like(KEY_SQL, ImogBean.Columns.FLAG_SYNCHRONIZED).and().eq(KEY_TYPE, TYPE_TABLE);

		Cursor c = builder.query();
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			QueryBuilder b = ImogOpenHelper.getHelper().queryBuilder(c.getString(0));
			b.setCountOf(true);
			b.where().eq(ImogBean.Columns.FLAG_SYNCHRONIZED, 0);
			long count = b.queryForLong();
			if (count > 0) {
				c.close();
				return true;
			}
		}
		c.close();
		return false;
	}

	/**
	 * Delete an {@link ImogEntity}. This do not really delete the entity from the database.
	 * 
	 * @param context the application context
	 * @param uri the uri of the bean to delete
	 */
	public static void delete(Context context, Uri uri) {
		ImogBean bean = ImogOpenHelper.fromUri(uri);
		bean.setDeleted(new Date());
		bean.prepareForSave(context);
		bean.saveOrUpdate(context);
	}
}
