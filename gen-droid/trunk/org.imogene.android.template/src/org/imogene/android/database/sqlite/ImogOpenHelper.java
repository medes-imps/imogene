package org.imogene.android.database.sqlite;

import org.imogene.android.database.sqlite.stmt.QueryBuilder;
import org.imogene.android.domain.Binary;
import org.imogene.android.domain.ClientFilter;
import org.imogene.android.domain.DefaultUser;
import org.imogene.android.domain.DynamicFieldInstance;
import org.imogene.android.domain.DynamicFieldTemplate;
import org.imogene.android.domain.GpsLocation;
import org.imogene.android.domain.SyncHistory;
import org.imogene.android.maps.database.PreCache;
import org.imogene.android.maps.database.sqlite.PreCacheCursor;
import org.imogene.android.provider.ImogProvider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public abstract class ImogOpenHelper extends SQLiteOpenHelper {
	
	protected static volatile ImogOpenHelper sInstance;
	
	/**
	 * Returns the current instance of the {@link ImogOpenHelper}
	 * 
	 * @return The {@code ImogOpenHelper} current instance.
	 */
	public static ImogOpenHelper getHelper() {
		if (sInstance == null) {
			throw new IllegalArgumentException("ImogHelper has not been instantiated");
		}
		return sInstance;
	}
	
	private static final String DATABASE_CREATE_DEFAULTUSER = "create table if not exists " + DefaultUser.Columns.TABLE_NAME
			+ " (" + DefaultUser.Columns._ID + " text primary key, " + DefaultUser.Columns.MODIFIED + " integer, "
			+ DefaultUser.Columns.MODIFIEDBY + " text, " + DefaultUser.Columns.MODIFIEDFROM + " text, "
			+ DefaultUser.Columns.UPLOADDATE + " integer, " + DefaultUser.Columns.CREATED + " integer, "
			+ DefaultUser.Columns.CREATEDBY + " text, " + DefaultUser.Columns.UNREAD + " integer, " + DefaultUser.Columns.LOGIN
			+ " text, " + DefaultUser.Columns.PASSWORD + " blob, " + DefaultUser.Columns.ROLES + " text, "
			+ DefaultUser.Columns.SYNCHRONIZED + " integer);";
	private static final String DATABASE_CREATE_CLIENTFILTER = "create table if not exists " + ClientFilter.Columns.TABLE_NAME
			+ " (" + ClientFilter.Columns._ID + " text primary key, " + ClientFilter.Columns.MODIFIED + " integer, "
			+ ClientFilter.Columns.MODIFIEDBY + " text, " + ClientFilter.Columns.MODIFIEDFROM + " text, "
			+ ClientFilter.Columns.UPLOADDATE + " integer, " + ClientFilter.Columns.CREATED + " integer, "
			+ ClientFilter.Columns.CREATEDBY + " text, " + ClientFilter.Columns.UNREAD + " integer, "
			+ ClientFilter.Columns.SYNCHRONIZED + " integer, " + ClientFilter.Columns.USERID + " text, "
			+ ClientFilter.Columns.TERMINALID + " text, " + ClientFilter.Columns.CARDENTITY + " text, "
			+ ClientFilter.Columns.ENTITYFIELD + " text, " + ClientFilter.Columns.OPERATOR + " text, "
			+ ClientFilter.Columns.FIELDVALUE + " text, " + ClientFilter.Columns.DISPLAY + " text, " + ClientFilter.Columns.ISNEW
			+ " text);";
	private static final String DATABASE_CREATE_BINARIES = "create table if not exists " + Binary.Columns.TABLE_NAME + " ("
			+ Binary.Columns._ID + " text primary key, " + Binary.Columns.MODIFIED + " integer, " + Binary.Columns.MODIFIEDBY
			+ " text, " + Binary.Columns.MODIFIEDFROM + " text, " + Binary.Columns.UPLOADDATE + " integer, "
			+ Binary.Columns.CREATED + " integer, " + Binary.Columns.CREATEDBY + " text, " + Binary.Columns.UNREAD + " integer, "
			+ Binary.Columns.SYNCHRONIZED + " integer, " + Binary.Columns.LENGTH + " text, " + Binary.Columns.DATA + " text, "
			+ Binary.Columns.CONTENT_TYPE + " text, " + Binary.Columns.FILE_NAME + " text);";
	private static final String DATABASE_CREATE_DYNAMICFIELD_TEMPLATE = "create table if not exists "
			+ DynamicFieldTemplate.Columns.TABLE_NAME + " (" + DynamicFieldTemplate.Columns._ID + " text primary key, "
			+ DynamicFieldTemplate.Columns.MODIFIED + " integer, " + DynamicFieldTemplate.Columns.MODIFIEDBY + " text, "
			+ DynamicFieldTemplate.Columns.MODIFIEDFROM + " text, " + DynamicFieldTemplate.Columns.UPLOADDATE + " integer, "
			+ DynamicFieldTemplate.Columns.CREATED + " integer, " + DynamicFieldTemplate.Columns.CREATEDBY + " text, "
			+ DynamicFieldTemplate.Columns.UNREAD + " integer, " + DynamicFieldTemplate.Columns.FIELDNAME + " text, "
			+ DynamicFieldTemplate.Columns.FIELDTYPE + " text, " + DynamicFieldTemplate.Columns.PARAMETERS + " text, "
			+ DynamicFieldTemplate.Columns.FORMTYPE + " text, " + DynamicFieldTemplate.Columns.TEMPLATECREATOR + " text, "
			+ DynamicFieldTemplate.Columns.DESCRIPTION + " text, " + DynamicFieldTemplate.Columns.REASON + " text, "
			+ DynamicFieldTemplate.Columns.ISDEFAULT + " text, " + DynamicFieldTemplate.Columns.REQUIREDVALUE + " text, "
			+ DynamicFieldTemplate.Columns.FIELDPOSITION + " integer, " + DynamicFieldTemplate.Columns.MINIMUMVALUE + " text, "
			+ DynamicFieldTemplate.Columns.MAXIMUMVALUE + " text, " + DynamicFieldTemplate.Columns.DEFAULTVALUE + " text, "
			+ DynamicFieldTemplate.Columns.ALLUSERS + " text, " + DynamicFieldTemplate.Columns.ISACTIVATED + " text, "
			+ DynamicFieldTemplate.Columns.SYNCHRONIZED + " integer);";
	private static final String DATABASE_CREATE_DYNAMICFIELD_INSTANCE = "create table if not exists "
			+ DynamicFieldInstance.Columns.TABLE_NAME + " (" + DynamicFieldInstance.Columns._ID + " text primary key, "
			+ DynamicFieldInstance.Columns.MODIFIED + " integer, " + DynamicFieldInstance.Columns.MODIFIEDBY + " text, "
			+ DynamicFieldInstance.Columns.MODIFIEDFROM + " text, " + DynamicFieldInstance.Columns.UPLOADDATE + " integer, "
			+ DynamicFieldInstance.Columns.CREATED + " integer, " + DynamicFieldInstance.Columns.CREATEDBY + " text, "
			+ DynamicFieldInstance.Columns.UNREAD + " integer, " + DynamicFieldInstance.Columns.FIELDTEMPLATE + " text, "
			+ DynamicFieldInstance.Columns.FIELDVALUE + " text, " + DynamicFieldInstance.Columns.FORMINSTANCE + " text, "
			+ DynamicFieldInstance.Columns.SYNCHRONIZED + " integer);";
	private static final String DATABASE_CREATE_SYNCHISTORY = "create table if not exists " + SyncHistory.Columns.TABLE_NAME
			+ " (" + SyncHistory.Columns._ID + " integer primary key autoincrement, " + SyncHistory.Columns.ID
			+ " text not null, " + SyncHistory.Columns.DATE + " integer not null, " + SyncHistory.Columns.STATUS + " integer, "
			+ SyncHistory.Columns.LEVEL + " integer);";
	private static final String DATABASE_CREATE_GPSLOCATION = "create table if not exists " + GpsLocation.Columns.TABLE_NAME
			+ " (" + GpsLocation.Columns._ID + " integer primary key autoincrement, " + GpsLocation.Columns.ACCURACY + " real, "
			+ GpsLocation.Columns.ALTITUDE + " real, " + GpsLocation.Columns.BEARING + " real, " + GpsLocation.Columns.LATITUDE
			+ " real, " + GpsLocation.Columns.LONGITUDE + " real, " + GpsLocation.Columns.PROVIDER + " text, "
			+ GpsLocation.Columns.SPEED + " real, " + GpsLocation.Columns.TIME + " integer, " + GpsLocation.Columns.HASACCURACY
			+ " integer, " + GpsLocation.Columns.HASALTITUDE + " integer, " + GpsLocation.Columns.HASBEARING + " integer, "
			+ GpsLocation.Columns.HASSPEED + " integer);";
	private static final String DATABASE_CREATE_PRECACHEBOUNDS = "create table if not exists " + PreCache.Columns.TABLE_NAME
			+ " (" + PreCache.Columns._ID + " integer primary key autoincrement, " + PreCache.Columns.PROVIDER + " text, "
			+ PreCache.Columns.NORTH + " real, " + PreCache.Columns.EAST + " real, " + PreCache.Columns.SOUTH + " real, "
			+ PreCache.Columns.WEST + " real, " + PreCache.Columns.ZOOM_MIN + " integer, " + PreCache.Columns.ZOOM_MAX
			+ " integer);";

	private final Context mContext;

	public ImogOpenHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE_BINARIES);
		db.execSQL(DATABASE_CREATE_CLIENTFILTER);
		db.execSQL(DATABASE_CREATE_DEFAULTUSER);
		db.execSQL(DATABASE_CREATE_SYNCHISTORY);
		db.execSQL(DATABASE_CREATE_DYNAMICFIELD_INSTANCE);
		db.execSQL(DATABASE_CREATE_DYNAMICFIELD_TEMPLATE);
		db.execSQL(DATABASE_CREATE_GPSLOCATION);
		db.execSQL(DATABASE_CREATE_PRECACHEBOUNDS);
	}

	public QueryBuilder queryBuilder(String tableName) {
		return new QueryBuilder(this, tableName);
	}

	public QueryBuilder queryBuilder(Uri uri) {
		QueryBuilder builder = null;
		switch (ImogProvider.URI_MATCHER.match(uri)) {
		case ImogProvider.BINARIES:
			builder = new QueryBuilder(this, Binary.Columns.TABLE_NAME);
			builder.setUri(uri);
			builder.setCursorFactory(new BinaryCursorImpl.Factory());
			return builder;
		case ImogProvider.BINARIES_ID:
			builder = new QueryBuilder(this, Binary.Columns.TABLE_NAME);
			builder.setCursorFactory(new BinaryCursorImpl.Factory());
			builder.setUri(uri);
			builder.where().idEq(uri.getLastPathSegment());
			return builder;
		case ImogProvider.CLIENT_FILTERS:
			builder = new QueryBuilder(this, ClientFilter.Columns.TABLE_NAME);
			builder.setCursorFactory(new ClientFilterCursor.Factory());
			builder.setUri(uri);
			return builder;
		case ImogProvider.CLIENT_FILTERS_ID:
			builder = new QueryBuilder(this, ClientFilter.Columns.TABLE_NAME);
			builder.setCursorFactory(new ClientFilterCursor.Factory());
			builder.setUri(uri);
			builder.where().idEq(uri.getLastPathSegment());
			return builder;
		case ImogProvider.DEFAULTUSER:
			builder = new QueryBuilder(this, DefaultUser.Columns.TABLE_NAME);
			builder.setCursorFactory(new DefaultUserCursor.Factory());
			builder.setUri(uri);
			return builder;
		case ImogProvider.DEFAULTUSER_ID:
			builder = new QueryBuilder(this, DefaultUser.Columns.TABLE_NAME);
			builder.setCursorFactory(new DefaultUserCursor.Factory());
			builder.setUri(uri);
			builder.where().idEq(uri.getLastPathSegment());
			return builder;
		case ImogProvider.DYNAMICFIELDINSTANCE:
			builder = new QueryBuilder(this, DynamicFieldInstance.Columns.TABLE_NAME);
			builder.setCursorFactory(new DynamicFieldInstanceCursor.Factory());
			builder.setUri(uri);
			return builder;
		case ImogProvider.DYNAMICFIELDINSTANCE_ID:
			builder = new QueryBuilder(this, DynamicFieldInstance.Columns.TABLE_NAME);
			builder.setCursorFactory(new DynamicFieldInstanceCursor.Factory());
			builder.setUri(uri);
			builder.where().idEq(uri.getLastPathSegment());
			return builder;
		case ImogProvider.DYNAMICFIELDTEMPLATE:
			builder = new QueryBuilder(this, DynamicFieldTemplate.Columns.TABLE_NAME);
			builder.setCursorFactory(new DynamicFieldTemplateCursor.Factory());
			builder.setUri(uri);
			return builder;
		case ImogProvider.DYNAMICFIELDTEMPLATE_ID:
			builder = new QueryBuilder(this, DynamicFieldTemplate.Columns.TABLE_NAME);
			builder.setCursorFactory(new DynamicFieldTemplateCursor.Factory());
			builder.setUri(uri);
			builder.where().idEq(uri.getLastPathSegment());
			return builder;
		case ImogProvider.PRECACHE_BOUNDS:
			builder = new QueryBuilder(this, PreCache.Columns.TABLE_NAME);
			builder.setCursorFactory(new PreCacheCursor.Factory());
			builder.setUri(uri);
			return builder;
		case ImogProvider.PRECACHE_BOUNDS_ID:
			builder = new QueryBuilder(this, PreCache.Columns.TABLE_NAME);
			builder.setCursorFactory(new PreCacheCursor.Factory());
			builder.setUri(uri);
			builder.where().idEq(uri.getLastPathSegment());
			return builder;
		default:
			throw new IllegalArgumentException("Unknown URL " + uri);
		}
	}

	public Cursor query(Uri uri) {
		return queryBuilder(uri).query();
	}

	public boolean exists(String tableName, String id) {
		QueryBuilder builder = ImogOpenHelper.getHelper().queryBuilder(tableName);
		builder.setCountOf(true);
		builder.where().idEq(id);
		return builder.queryForLong() > 0;
	}

	public long insert(String tableName, ContentValues values) {
		return getWritableDatabase().insert(tableName, "", values);
	}

	public int update(String tableName, ContentValues values, String whereClause, String[] whereArgs) {
		return getWritableDatabase().update(tableName, values, whereClause, whereArgs);
	}
	
	public int delete(String tableName, String whereClause, String[] whereArgs) {
		return getWritableDatabase().delete(tableName, whereClause, whereArgs);
	}

	public Context getContext() {
		return mContext;
	}
	
	protected void upgrade(SQLiteDatabase db, String table, String column, String type) {
		try {
			db.compileStatement("select " + column + " from " + table + " limit 1").close();
		} catch (Exception e) {
			db.execSQL("alter table " + table + " add column " + column + " " + type + ";");
		}
	}

}
