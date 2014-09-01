package org.imogene.android.provider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.imogene.android.Constants;
import org.imogene.android.Constants.Paths;
import org.imogene.android.common.binary.Binary;
import org.imogene.android.common.dynamicfields.DynamicFieldInstance;
import org.imogene.android.common.dynamicfields.DynamicFieldTemplate;
import org.imogene.android.common.entity.DefaultUser;
import org.imogene.android.common.entity.ImogBean;
import org.imogene.android.common.filter.ClientFilter;
import org.imogene.android.common.model.CardEntity;
import org.imogene.android.common.model.FieldGroup;
import org.imogene.android.common.profile.EntityProfile;
import org.imogene.android.common.profile.FieldGroupProfile;
import org.imogene.android.common.profile.Profile;
import org.imogene.android.database.sqlite.ImogOpenHelper;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import fr.medes.android.maps.MapsConstants;
import fr.medes.android.maps.database.PreCache;
import fr.medes.android.util.content.ContentUrisUtils;
import fr.medes.android.util.file.FileUtils;

public abstract class ImogProvider extends ContentProvider implements OpenableColumns {

	public static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

	public static final int PROFILE = 1;
	public static final int PROFILE_ID = 2;
	public static final int ENTITYPROFILE = 3;
	public static final int ENTITYPROFILE_ID = 4;
	public static final int FIELDGROUPPROFILE = 5;
	public static final int FIELDGROUPPROFILE_ID = 6;
	public static final int CARDENTITY = 7;
	public static final int CARDENTITY_ID = 8;
	public static final int FIELDGROUP = 9;
	public static final int FIELDGROUP_ID = 10;
	public static final int BINARIES = 11;
	public static final int BINARIES_ID = 12;
	public static final int CLIENT_FILTERS = 13;
	public static final int CLIENT_FILTERS_ID = 14;
	public static final int DEFAULTUSER = 15;
	public static final int DEFAULTUSER_ID = 16;
	public static final int DYNAMICFIELDTEMPLATE = 17;
	public static final int DYNAMICFIELDTEMPLATE_ID = 18;
	public static final int DYNAMICFIELDINSTANCE = 19;
	public static final int DYNAMICFIELDINSTANCE_ID = 20;
	public static final int PRECACHE_BOUNDS = 21;
	public static final int PRECACHE_BOUNDS_ID = 22;

	protected static final int LAST_INDEX = PRECACHE_BOUNDS_ID;

	static {
		//TODO God old trick to set the Maps library authority
		MapsConstants.AUTHORITY = "org.imogene.android.%realProjectName%.maps";
		URI_MATCHER.addURI(Constants.AUTHORITY, Profile.Columns.TABLE_NAME, PROFILE);
		URI_MATCHER.addURI(Constants.AUTHORITY, Profile.Columns.TABLE_NAME + "/*", PROFILE_ID);
		URI_MATCHER.addURI(Constants.AUTHORITY, EntityProfile.Columns.TABLE_NAME, ENTITYPROFILE);
		URI_MATCHER.addURI(Constants.AUTHORITY, EntityProfile.Columns.TABLE_NAME + "/*", ENTITYPROFILE_ID);
		URI_MATCHER.addURI(Constants.AUTHORITY, FieldGroupProfile.Columns.TABLE_NAME, FIELDGROUPPROFILE);
		URI_MATCHER.addURI(Constants.AUTHORITY, FieldGroupProfile.Columns.TABLE_NAME + "/*", FIELDGROUPPROFILE_ID);
		URI_MATCHER.addURI(Constants.AUTHORITY, CardEntity.Columns.TABLE_NAME, CARDENTITY);
		URI_MATCHER.addURI(Constants.AUTHORITY, CardEntity.Columns.TABLE_NAME + "/*", CARDENTITY_ID);
		URI_MATCHER.addURI(Constants.AUTHORITY, FieldGroup.Columns.TABLE_NAME, FIELDGROUP);
		URI_MATCHER.addURI(Constants.AUTHORITY, FieldGroup.Columns.TABLE_NAME + "/*", FIELDGROUP_ID);
		URI_MATCHER.addURI(Constants.AUTHORITY, Binary.Columns.TABLE_NAME, BINARIES);
		URI_MATCHER.addURI(Constants.AUTHORITY, Binary.Columns.TABLE_NAME + "/*", BINARIES_ID);
		URI_MATCHER.addURI(Constants.AUTHORITY, ClientFilter.Columns.TABLE_NAME, CLIENT_FILTERS);
		URI_MATCHER.addURI(Constants.AUTHORITY, ClientFilter.Columns.TABLE_NAME + "/*", CLIENT_FILTERS_ID);
		URI_MATCHER.addURI(Constants.AUTHORITY, DefaultUser.Columns.TABLE_NAME, DEFAULTUSER);
		URI_MATCHER.addURI(Constants.AUTHORITY, DefaultUser.Columns.TABLE_NAME + "/*", DEFAULTUSER_ID);
		URI_MATCHER.addURI(Constants.AUTHORITY, DynamicFieldInstance.Columns.TABLE_NAME, DYNAMICFIELDINSTANCE);
		URI_MATCHER
				.addURI(Constants.AUTHORITY, DynamicFieldInstance.Columns.TABLE_NAME + "/*", DYNAMICFIELDINSTANCE_ID);
		URI_MATCHER.addURI(Constants.AUTHORITY, DynamicFieldTemplate.Columns.TABLE_NAME, DYNAMICFIELDTEMPLATE);
		URI_MATCHER
				.addURI(Constants.AUTHORITY, DynamicFieldTemplate.Columns.TABLE_NAME + "/*", DYNAMICFIELDTEMPLATE_ID);
		URI_MATCHER.addURI(MapsConstants.AUTHORITY, PreCache.Columns.TABLE_NAME, PRECACHE_BOUNDS);
		URI_MATCHER.addURI(MapsConstants.AUTHORITY, PreCache.Columns.TABLE_NAME + "/#", PRECACHE_BOUNDS_ID);
	}

	protected abstract String getVndDir();

	protected abstract String getVndItem();

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int result = -1;
		switch (URI_MATCHER.match(uri)) {
		case PROFILE:
			result = deleteMulti(Profile.Columns.TABLE_NAME, selection, selectionArgs);
			break;
		case PROFILE_ID:
			String proId = uri.getLastPathSegment();
			result = deleteSingle(Profile.Columns.TABLE_NAME, proId, selection, selectionArgs);
			break;
		case ENTITYPROFILE:
			result = deleteMulti(EntityProfile.Columns.TABLE_NAME, selection, selectionArgs);
			break;
		case ENTITYPROFILE_ID:
			String pro_entId = uri.getLastPathSegment();
			result = deleteSingle(EntityProfile.Columns.TABLE_NAME, pro_entId, selection, selectionArgs);
			break;
		case FIELDGROUPPROFILE:
			result = deleteMulti(FieldGroupProfile.Columns.TABLE_NAME, selection, selectionArgs);
			break;
		case FIELDGROUPPROFILE_ID:
			String pro_grpId = uri.getLastPathSegment();
			result = deleteSingle(FieldGroupProfile.Columns.TABLE_NAME, pro_grpId, selection, selectionArgs);
			break;
		case CARDENTITY:
			result = deleteMulti(CardEntity.Columns.TABLE_NAME, selection, selectionArgs);
			break;
		case CARDENTITY_ID:
			String sync_entId = uri.getLastPathSegment();
			result = deleteSingle(CardEntity.Columns.TABLE_NAME, sync_entId, selection, selectionArgs);
			break;
		case FIELDGROUP:
			result = deleteMulti(FieldGroup.Columns.TABLE_NAME, selection, selectionArgs);
			break;
		case FIELDGROUP_ID:
			String grpId = uri.getLastPathSegment();
			result = deleteSingle(FieldGroup.Columns.TABLE_NAME, grpId, selection, selectionArgs);
			break;
		case BINARIES:
			result = deleteMultiBinary(Binary.Columns.TABLE_NAME, selection, selectionArgs);
			break;
		case BINARIES_ID:
			String binaryId = uri.getLastPathSegment();
			result = deleteSingleBinary(Binary.Columns.TABLE_NAME, binaryId, selection, selectionArgs);
			break;
		case CLIENT_FILTERS:
			result = deleteMulti(ClientFilter.Columns.TABLE_NAME, selection, selectionArgs);
			break;
		case CLIENT_FILTERS_ID:
			String clientFilterId = uri.getLastPathSegment();
			result = deleteSingle(ClientFilter.Columns.TABLE_NAME, clientFilterId, selection, selectionArgs);
			break;
		case DEFAULTUSER:
			result = deleteMulti(DefaultUser.Columns.TABLE_NAME, selection, selectionArgs);
			break;
		case DEFAULTUSER_ID:
			String defaultUserId = uri.getLastPathSegment();
			result = deleteSingle(DefaultUser.Columns.TABLE_NAME, defaultUserId, selection, selectionArgs);
			break;
		case DYNAMICFIELDINSTANCE:
			result = deleteMulti(DynamicFieldInstance.Columns.TABLE_NAME, selection, selectionArgs);
			break;
		case DYNAMICFIELDINSTANCE_ID:
			String dynFieldInstanceId = uri.getLastPathSegment();
			result = deleteSingle(DynamicFieldInstance.Columns.TABLE_NAME, dynFieldInstanceId, selection, selectionArgs);
			break;
		case DYNAMICFIELDTEMPLATE:
			result = deleteMulti(DynamicFieldTemplate.Columns.TABLE_NAME, selection, selectionArgs);
			break;
		case DYNAMICFIELDTEMPLATE_ID:
			String dynFieldTemplateId = uri.getLastPathSegment();
			result = deleteSingle(DynamicFieldTemplate.Columns.TABLE_NAME, dynFieldTemplateId, selection, selectionArgs);
			break;
		case PRECACHE_BOUNDS:
			result = deleteMulti(PreCache.Columns.TABLE_NAME, selection, selectionArgs);
			break;
		case PRECACHE_BOUNDS_ID:
			String precacheId = uri.getLastPathSegment();
			result = deleteSingle(PreCache.Columns.TABLE_NAME, precacheId, selection, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URL" + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return result;
	}

	@Override
	public String getType(Uri uri) {
		switch (URI_MATCHER.match(uri)) {
		case PROFILE:
			return getVndDir() + "profile";
		case PROFILE_ID:
			return getVndItem() + "profile";
		case ENTITYPROFILE:
			return getVndDir() + "entityprofile";
		case ENTITYPROFILE_ID:
			return getVndItem() + "entityprofile";
		case FIELDGROUPPROFILE:
			return getVndDir() + "fieldgroupprofile";
		case FIELDGROUPPROFILE_ID:
			return getVndItem() + "fieldgroupprofile";
		case CARDENTITY:
			return getVndDir() + "cardentity";
		case CARDENTITY_ID:
			return getVndItem() + "cardentity";
		case FIELDGROUP:
			return getVndDir() + "fieldgroup";
		case FIELDGROUP_ID:
			return getVndItem() + "fieldgroup";
		case BINARIES:
			return getVndDir() + Binary.Columns.TABLE_NAME;
		case BINARIES_ID:
			SQLiteDatabase sqlDB = ImogOpenHelper.getHelper().getReadableDatabase();
			SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
			qb.setTables(Binary.Columns.TABLE_NAME);
			qb.appendWhere(ImogBean.Columns._ID + "='" + uri.getLastPathSegment() + "'");
			Cursor c = qb.query(sqlDB, new String[] { Binary.Columns.CONTENT_TYPE }, null, null, null, null, null);
			String result = getVndItem() + "binaries";
			if (c.getCount() == 1) {
				c.moveToFirst();
				result = c.getString(0);
			}
			c.close();
			return result;
		case CLIENT_FILTERS:
			return getVndDir() + ClientFilter.Columns.TABLE_NAME;
		case CLIENT_FILTERS_ID:
			return getVndItem() + ClientFilter.Columns.TABLE_NAME;
		case DEFAULTUSER:
			return getVndDir() + DefaultUser.Columns.TABLE_NAME;
		case DEFAULTUSER_ID:
			return getVndItem() + DefaultUser.Columns.TABLE_NAME;
		case DYNAMICFIELDINSTANCE:
			return getVndDir() + DynamicFieldInstance.Columns.TABLE_NAME;
		case DYNAMICFIELDINSTANCE_ID:
			return getVndItem() + DynamicFieldInstance.Columns.TABLE_NAME;
		case DYNAMICFIELDTEMPLATE:
			return getVndDir() + DynamicFieldTemplate.Columns.TABLE_NAME;
		case DYNAMICFIELDTEMPLATE_ID:
			return getVndItem() + DynamicFieldTemplate.Columns.TABLE_NAME;
		case PRECACHE_BOUNDS:
			return getVndDir() + PreCache.Columns.TABLE_NAME;
		case PRECACHE_BOUNDS_ID:
			return getVndItem() + PreCache.Columns.TABLE_NAME;
		default:
			throw new IllegalArgumentException("Unknown URL " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		switch (URI_MATCHER.match(uri)) {
		case PROFILE:
			return insertInTable(Profile.Columns.TABLE_NAME, Profile.Columns.CONTENT_URI, values);
		case ENTITYPROFILE:
			return insertInTable(EntityProfile.Columns.TABLE_NAME, EntityProfile.Columns.CONTENT_URI, values);
		case FIELDGROUPPROFILE:
			return insertInTable(FieldGroupProfile.Columns.TABLE_NAME, FieldGroupProfile.Columns.CONTENT_URI, values);
		case CARDENTITY:
			return insertInTable(CardEntity.Columns.TABLE_NAME, CardEntity.Columns.CONTENT_URI, values);
		case FIELDGROUP:
			return insertInTable(FieldGroup.Columns.TABLE_NAME, FieldGroup.Columns.CONTENT_URI, values);
		case BINARIES:
			return insertInTableBinary(values);
		case CLIENT_FILTERS:
			return insertInTable(ClientFilter.Columns.TABLE_NAME, ClientFilter.Columns.CONTENT_URI, values);
		case DEFAULTUSER:
			return insertInTable(DefaultUser.Columns.TABLE_NAME, DefaultUser.Columns.CONTENT_URI, values);
		case DYNAMICFIELDINSTANCE:
			return insertInTable(DynamicFieldInstance.Columns.TABLE_NAME, DynamicFieldInstance.Columns.CONTENT_URI,
					values);
		case DYNAMICFIELDTEMPLATE:
			return insertInTable(DynamicFieldTemplate.Columns.TABLE_NAME, DynamicFieldTemplate.Columns.CONTENT_URI,
					values);
		case PRECACHE_BOUNDS:
			return insertInTableBase(PreCache.Columns.TABLE_NAME, PreCache.Columns.CONTENT_URI, values);
		default:
			throw new IllegalArgumentException("Unknown URL " + uri);
		}
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteDatabase sqlDB = ImogOpenHelper.getHelper().getWritableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		switch (URI_MATCHER.match(uri)) {
		case PROFILE:
			qb.setTables(Profile.Columns.TABLE_NAME);
			break;
		case PROFILE_ID:
			qb.setTables(Profile.Columns.TABLE_NAME);
			qb.appendWhere("_id='" + uri.getLastPathSegment() + "'");
			break;
		case ENTITYPROFILE:
			qb.setTables(EntityProfile.Columns.TABLE_NAME);
			break;
		case ENTITYPROFILE_ID:
			qb.setTables(EntityProfile.Columns.TABLE_NAME);
			qb.appendWhere("_id='" + uri.getLastPathSegment() + "'");
			break;
		case FIELDGROUPPROFILE:
			qb.setTables(FieldGroupProfile.Columns.TABLE_NAME);
			break;
		case FIELDGROUPPROFILE_ID:
			qb.setTables(FieldGroupProfile.Columns.TABLE_NAME);
			qb.appendWhere("_id='" + uri.getLastPathSegment() + "'");
			break;
		case CARDENTITY:
			qb.setTables(CardEntity.Columns.TABLE_NAME);
			break;
		case CARDENTITY_ID:
			qb.setTables(CardEntity.Columns.TABLE_NAME);
			qb.appendWhere("_id='" + uri.getLastPathSegment() + "'");
			break;
		case FIELDGROUP:
			qb.setTables(FieldGroup.Columns.TABLE_NAME);
			break;
		case FIELDGROUP_ID:
			qb.setTables(FieldGroup.Columns.TABLE_NAME);
			qb.appendWhere("_id='" + uri.getLastPathSegment() + "'");
			break;
		case BINARIES:
			qb.setTables(Binary.Columns.TABLE_NAME);
			break;
		case BINARIES_ID:
			qb.setTables(Binary.Columns.TABLE_NAME);
			qb.appendWhere(Binary.Columns._ID + "='" + uri.getLastPathSegment() + "'");
			break;
		case CLIENT_FILTERS:
			qb.setTables(ClientFilter.Columns.TABLE_NAME);
			break;
		case CLIENT_FILTERS_ID:
			qb.setTables(ClientFilter.Columns.TABLE_NAME);
			qb.appendWhere(ClientFilter.Columns._ID + "='" + uri.getLastPathSegment() + "'");
			break;
		case DEFAULTUSER:
			qb.setTables(DefaultUser.Columns.TABLE_NAME);
			break;
		case DEFAULTUSER_ID:
			qb.setTables(DefaultUser.Columns.TABLE_NAME);
			qb.appendWhere(DefaultUser.Columns._ID + "='" + uri.getLastPathSegment() + "'");
			break;
		case DYNAMICFIELDINSTANCE:
			qb.setTables(DynamicFieldInstance.Columns.TABLE_NAME);
			break;
		case DYNAMICFIELDINSTANCE_ID:
			qb.setTables(DynamicFieldInstance.Columns.TABLE_NAME);
			qb.appendWhere(DynamicFieldInstance.Columns._ID + "='" + uri.getLastPathSegment() + "'");
			break;
		case DYNAMICFIELDTEMPLATE:
			qb.setTables(DynamicFieldTemplate.Columns.TABLE_NAME);
			break;
		case DYNAMICFIELDTEMPLATE_ID:
			qb.setTables(DynamicFieldTemplate.Columns.TABLE_NAME);
			qb.appendWhere(DynamicFieldTemplate.Columns._ID + "='" + uri.getLastPathSegment() + "'");
			break;
		case PRECACHE_BOUNDS:
			qb.setTables(PreCache.Columns.TABLE_NAME);
			break;
		case PRECACHE_BOUNDS_ID:
			qb.setTables(PreCache.Columns.TABLE_NAME);
			qb.appendWhere(PreCache.Columns._ID + "='" + uri.getLastPathSegment() + "'");
			break;
		default:
			throw new IllegalArgumentException("Unknown URL " + uri);
		}
		Cursor c = qb.query(sqlDB, projection, selection, selectionArgs, null, null, sortOrder);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		int result = -1;
		switch (URI_MATCHER.match(uri)) {
		case PROFILE:
			result = updateMulti(Profile.Columns.TABLE_NAME, values, selection, selectionArgs);
			break;
		case PROFILE_ID:
			String proId = uri.getLastPathSegment();
			result = updateSingle(Profile.Columns.TABLE_NAME, proId, values, selection, selectionArgs);
			break;
		case ENTITYPROFILE:
			result = updateMulti(EntityProfile.Columns.TABLE_NAME, values, selection, selectionArgs);
			break;
		case ENTITYPROFILE_ID:
			String pro_entId = uri.getLastPathSegment();
			result = updateSingle(EntityProfile.Columns.TABLE_NAME, pro_entId, values, selection, selectionArgs);
			break;
		case FIELDGROUPPROFILE:
			result = updateMulti(FieldGroupProfile.Columns.TABLE_NAME, values, selection, selectionArgs);
			break;
		case FIELDGROUPPROFILE_ID:
			String pro_grpId = uri.getLastPathSegment();
			result = updateSingle(FieldGroupProfile.Columns.TABLE_NAME, pro_grpId, values, selection, selectionArgs);
			break;
		case CARDENTITY:
			result = updateMulti(CardEntity.Columns.TABLE_NAME, values, selection, selectionArgs);
			break;
		case CARDENTITY_ID:
			String sync_entId = uri.getLastPathSegment();
			result = updateSingle(CardEntity.Columns.TABLE_NAME, sync_entId, values, selection, selectionArgs);
			break;
		case FIELDGROUP:
			result = updateMulti(FieldGroup.Columns.TABLE_NAME, values, selection, selectionArgs);
			break;
		case FIELDGROUP_ID:
			String grpId = uri.getLastPathSegment();
			result = updateSingle(FieldGroup.Columns.TABLE_NAME, grpId, values, selection, selectionArgs);
			break;
		case BINARIES:
			result = updateMulti(Binary.Columns.TABLE_NAME, values, selection, selectionArgs);
			break;
		case BINARIES_ID:
			String binaryId = uri.getLastPathSegment();
			result = updateSingle(Binary.Columns.TABLE_NAME, binaryId, values, selection, selectionArgs);
			break;
		case CLIENT_FILTERS:
			result = updateMulti(ClientFilter.Columns.TABLE_NAME, values, selection, selectionArgs);
			break;
		case CLIENT_FILTERS_ID:
			String clientFilterId = uri.getLastPathSegment();
			result = updateSingle(ClientFilter.Columns.TABLE_NAME, clientFilterId, values, selection, selectionArgs);
			break;
		case DEFAULTUSER:
			result = updateMulti(DefaultUser.Columns.TABLE_NAME, values, selection, selectionArgs);
			break;
		case DEFAULTUSER_ID:
			String defaultUserId = uri.getLastPathSegment();
			result = updateSingle(DefaultUser.Columns.TABLE_NAME, defaultUserId, values, selection, selectionArgs);
			break;
		case DYNAMICFIELDINSTANCE:
			result = updateMulti(DynamicFieldInstance.Columns.TABLE_NAME, values, selection, selectionArgs);
			break;
		case DYNAMICFIELDINSTANCE_ID:
			String dynFieldInstanceId = uri.getLastPathSegment();
			result = updateSingle(DynamicFieldInstance.Columns.TABLE_NAME, dynFieldInstanceId, values, selection,
					selectionArgs);
			break;
		case DYNAMICFIELDTEMPLATE:
			result = updateMulti(DynamicFieldTemplate.Columns.TABLE_NAME, values, selection, selectionArgs);
			break;
		case DYNAMICFIELDTEMPLATE_ID:
			String dynFieldTemplateId = uri.getLastPathSegment();
			result = updateSingle(DynamicFieldTemplate.Columns.TABLE_NAME, dynFieldTemplateId, values, selection,
					selectionArgs);
			break;
		case PRECACHE_BOUNDS:
			result = updateMulti(PreCache.Columns.TABLE_NAME, values, selection, selectionArgs);
			break;
		case PRECACHE_BOUNDS_ID:
			String precacheId = uri.getLastPathSegment();
			result = updateSingle(PreCache.Columns.TABLE_NAME, precacheId, values, selection, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URL " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return result;
	}

	protected final int deleteMulti(String tableName, String where, String[] whereArgs) {
		SQLiteDatabase sqlDB = ImogOpenHelper.getHelper().getWritableDatabase();
		return sqlDB.delete(tableName, where, whereArgs);
	}

	protected final int deleteSingle(String tableName, String id, String where, String[] whereArgs) {
		String w = ImogBean.Columns._ID + "='" + id + "'" + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : "");
		SQLiteDatabase sqlDB = ImogOpenHelper.getHelper().getWritableDatabase();
		return sqlDB.delete(tableName, w, whereArgs);
	}

	protected final int deleteMultiBinary(String tableName, String where, String[] whereArgs) {
		SQLiteDatabase sqlDB = ImogOpenHelper.getHelper().getWritableDatabase();
		Cursor cursor = sqlDB
				.query(tableName, new String[] { Binary.Columns.DATA }, where, whereArgs, null, null, null);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			String path = cursor.getString(0);
			new File(path).delete();
		}
		cursor.close();
		return sqlDB.delete(tableName, where, whereArgs);
	}

	protected final int deleteSingleBinary(String tableName, String id, String where, String[] whereArgs) {
		String w = ImogBean.Columns._ID + "='" + id + "'" + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : "");
		SQLiteDatabase sqlDB = ImogOpenHelper.getHelper().getWritableDatabase();
		Cursor cursor = sqlDB.query(tableName, new String[] { Binary.Columns.DATA }, w, whereArgs, null, null, null);
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			String path = cursor.getString(cursor.getColumnIndexOrThrow(Binary.Columns.DATA));
			FileUtils.deleteFile(path);
		}
		cursor.close();
		return sqlDB.delete(tableName, w, whereArgs);
	}

	protected final Uri insertInTableBinary(ContentValues values) {
		SQLiteDatabase sqlDB = ImogOpenHelper.getHelper().getWritableDatabase();
		if (sqlDB.insert(Binary.Columns.TABLE_NAME, "", values) > 0) {
			String id = values.getAsString(ImogBean.Columns._ID);
			Uri rowUri = ContentUrisUtils.withAppendedId(Binary.Columns.CONTENT_URI, id);

			Paths.PATH_BINARIES.mkdirs();

			String filename = System.currentTimeMillis() + ".bin";
			File file = new File(Paths.PATH_BINARIES, filename);
			try {
				file.createNewFile();
			} catch (IOException e) {
				return null;
			}
			String path = file.getAbsolutePath();
			values.put(Binary.Columns.DATA, path);
			sqlDB.update(Binary.Columns.TABLE_NAME, values, ImogBean.Columns._ID + "='" + id + "'", null);
			getContext().getContentResolver().notifyChange(rowUri, null);
			return rowUri;
		}
		throw new SQLException("Failed to insert row into binaries");
	}

	protected final Uri insertInTableBase(String table, Uri contentUri, ContentValues values) {
		SQLiteDatabase sqlDB = ImogOpenHelper.getHelper().getWritableDatabase();
		long id = sqlDB.insert(table, "", values);
		if (id > 0) {
			Uri uri = ContentUris.withAppendedId(contentUri, id);
			getContext().getContentResolver().notifyChange(uri, null);
			return uri;
		}
		throw new SQLException("Failed to insert row into " + table);
	}

	protected final Uri insertInTable(String table, Uri contentUri, ContentValues values) {
		SQLiteDatabase sqlDB = ImogOpenHelper.getHelper().getWritableDatabase();
		long rowId = sqlDB.insert(table, "", values);
		if (rowId > 0) {
			String id = values.getAsString(ImogBean.Columns._ID);
			Uri rowUri = ContentUrisUtils.withAppendedId(contentUri, id);
			getContext().getContentResolver().notifyChange(rowUri, null);
			return rowUri;
		}
		throw new SQLException("Failed to insert row into " + table);
	}

	protected final int updateMulti(String tableName, ContentValues values, String selection, String[] selectionArgs) {
		SQLiteDatabase sqlDB = ImogOpenHelper.getHelper().getWritableDatabase();
		return sqlDB.update(tableName, values, selection, selectionArgs);
	}

	protected final int updateSingle(String tableName, String id, ContentValues values, String selection,
			String[] selectionArgs) {
		String where = ImogBean.Columns._ID + "='" + id + "'"
				+ (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
		SQLiteDatabase sqlDB = ImogOpenHelper.getHelper().getWritableDatabase();
		return sqlDB.update(tableName, values, where, selectionArgs);
	}

	@Override
	public final ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
		if (URI_MATCHER.match(uri) != BINARIES_ID) {
			throw new IllegalArgumentException("openFile not supported for directories");
		}
		try {
			return this.openFileHelper(uri, mode);
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		}
	}

}