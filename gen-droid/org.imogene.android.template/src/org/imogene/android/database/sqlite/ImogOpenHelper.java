package org.imogene.android.database.sqlite;

import org.imogene.android.common.binary.Binary;
import org.imogene.android.common.dynamicfields.DynamicFieldInstance;
import org.imogene.android.common.dynamicfields.DynamicFieldTemplate;
import org.imogene.android.common.entity.DefaultUser;
import org.imogene.android.common.entity.GpsLocation;
import org.imogene.android.common.entity.ImogActor;
import org.imogene.android.common.entity.ImogBean;
import org.imogene.android.common.entity.SyncHistory;
import org.imogene.android.common.filter.ClientFilter;
import org.imogene.android.common.model.CardEntity;
import org.imogene.android.common.model.FieldGroup;
import org.imogene.android.common.profile.EntityProfile;
import org.imogene.android.common.profile.FieldGroupProfile;
import org.imogene.android.common.profile.Profile;
import org.imogene.android.database.ImogBeanCursor;
import org.imogene.android.provider.ImogProvider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;
import fr.medes.android.database.sqlite.AmlOpenHelper;
import fr.medes.android.database.sqlite.stmt.QueryBuilder;
import fr.medes.android.maps.database.PreCache;
import fr.medes.android.maps.database.sqlite.PreCacheCursor;

public abstract class ImogOpenHelper extends AmlOpenHelper {

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

	private static final String DATABASE_CREATE_PROFILE = "create table if not exists " + //
			Profile.Columns.TABLE_NAME + " (" + //
			Profile.Columns._ID + " text primary key, " + //
			Profile.Columns.MODIFIED + " integer, " + //
			Profile.Columns.MODIFIEDBY + " text, " + //
			Profile.Columns.MODIFIEDFROM + " text, " + //
			Profile.Columns.UPLOADDATE + " integer, " + //
			Profile.Columns.CREATED + " integer, " + //
			Profile.Columns.CREATEDBY + " text, " + //
			Profile.Columns.DELETED + " integer, " + //
			Profile.Columns.FLAG_READ + " integer, " + //
			Profile.Columns.NAME + " text, " + //
			Profile.Columns.FLAG_SYNCHRONIZED + " integer);";

	private static final String DATABASE_CREATE_ENTITYPROFILE = "create table if not exists " + //
			EntityProfile.Columns.TABLE_NAME + " (" + //
			EntityProfile.Columns._ID + " text primary key, " + //
			EntityProfile.Columns.MODIFIED + " integer, " + //
			EntityProfile.Columns.MODIFIEDBY + " text, " + //
			EntityProfile.Columns.MODIFIEDFROM + " text, " + //
			EntityProfile.Columns.UPLOADDATE + " integer, " + //
			EntityProfile.Columns.CREATED + " integer, " + //
			EntityProfile.Columns.CREATEDBY + " text, " + //
			EntityProfile.Columns.DELETED + " integer, " + //
			EntityProfile.Columns.FLAG_READ + " integer, " + //
			EntityProfile.Columns.PROFILE + " text, " + //
			EntityProfile.Columns.ENTITY + " text, " + //
			EntityProfile.Columns.CREATE + " text, " + //
			EntityProfile.Columns.DIRECTACCESS + " text, " + //
			EntityProfile.Columns.DELETE + " text, " + //
			EntityProfile.Columns.EXPORT + " text, " + //
			EntityProfile.Columns.FLAG_SYNCHRONIZED + " integer);";

	private static final String DATABASE_CREATE_FIELDGROUPPROFILE = "create table if not exists " + //
			FieldGroupProfile.Columns.TABLE_NAME + " (" + //
			FieldGroupProfile.Columns._ID + " text primary key, " + //
			FieldGroupProfile.Columns.MODIFIED + " integer, " + //
			FieldGroupProfile.Columns.MODIFIEDBY + " text, " + //
			FieldGroupProfile.Columns.MODIFIEDFROM + " text, " + //
			FieldGroupProfile.Columns.UPLOADDATE + " integer, " + //
			FieldGroupProfile.Columns.CREATED + " integer, " + //
			FieldGroupProfile.Columns.CREATEDBY + " text, " + //
			FieldGroupProfile.Columns.DELETED + " integer, " + //
			FieldGroupProfile.Columns.FLAG_READ + " integer, " + //
			FieldGroupProfile.Columns.PROFILE + " text, " + //
			FieldGroupProfile.Columns.FIELDGROUP + " text, " + //
			FieldGroupProfile.Columns.READ + " text, " + //
			FieldGroupProfile.Columns.WRITE + " text, " + //
			FieldGroupProfile.Columns.EXPORT + " text, " + //
			FieldGroupProfile.Columns.FLAG_SYNCHRONIZED + " integer);";

	private static final String DATABASE_CREATE_CARDENTITY = "create table if not exists " + //
			CardEntity.Columns.TABLE_NAME + " (" + //
			CardEntity.Columns._ID + " text primary key, " + //
			CardEntity.Columns.MODIFIED + " integer, " + //
			CardEntity.Columns.MODIFIEDBY + " text, " + //
			CardEntity.Columns.MODIFIEDFROM + " text, " + //
			CardEntity.Columns.UPLOADDATE + " integer, " + //
			CardEntity.Columns.CREATED + " integer, " + //
			CardEntity.Columns.CREATEDBY + " text, " + //
			CardEntity.Columns.DELETED + " integer, " + //
			CardEntity.Columns.FLAG_READ + " integer, " + //
			CardEntity.Columns.NAME + " text, " + //
			CardEntity.Columns.FLAG_SYNCHRONIZED + " integer);";

	private static final String DATABASE_CREATE_FIELDGROUP = "create table if not exists " + //
			FieldGroup.Columns.TABLE_NAME + " (" + //
			FieldGroup.Columns._ID + " text primary key, " + //
			FieldGroup.Columns.MODIFIED + " integer, " + //
			FieldGroup.Columns.MODIFIEDBY + " text, " + //
			FieldGroup.Columns.MODIFIEDFROM + " text, " + //
			FieldGroup.Columns.UPLOADDATE + " integer, " + //
			FieldGroup.Columns.CREATED + " integer, " + //
			FieldGroup.Columns.CREATEDBY + " text, " + //
			FieldGroup.Columns.DELETED + " integer, " + //
			FieldGroup.Columns.FLAG_READ + " integer, " + //
			FieldGroup.Columns.NAME + " text, " + //
			FieldGroup.Columns.ENTITY + " text, " + //
			FieldGroup.Columns.FLAG_SYNCHRONIZED + " integer);";

	private static final String DATABASE_CREATE_ACTOR_PROFILES = "create table if not exists " + //
			ImogActor.Columns.TABLE_ACTOR_PROFILES + " (" + //
			BaseColumns._ID + " integer primary key autoincrement, " + //
			ImogActor.Columns.TABLE_NAME + " text not null, " + //
			Profile.Columns.TABLE_NAME + " text not null);";

	private static final String DATABASE_CREATE_DEFAULTUSER = "create table if not exists " + //
			DefaultUser.Columns.TABLE_NAME + " (" + //
			DefaultUser.Columns._ID + " text primary key, " + //
			DefaultUser.Columns.MODIFIED + " integer, " + //
			DefaultUser.Columns.MODIFIEDBY + " text, " + //
			DefaultUser.Columns.MODIFIEDFROM + " text, " + //
			DefaultUser.Columns.UPLOADDATE + " integer, " + //
			DefaultUser.Columns.CREATED + " integer, " + //
			DefaultUser.Columns.CREATEDBY + " text, " + //
			DefaultUser.Columns.DELETED + " integer, " + //
			DefaultUser.Columns.FLAG_READ + " integer, " + //
			DefaultUser.Columns.LOGIN + " text, " + //
			DefaultUser.Columns.PASSWORD + " blob, " + //
			DefaultUser.Columns.FLAG_SYNCHRONIZED + " integer);";

	private static final String DATABASE_CREATE_CLIENTFILTER = "create table if not exists " + //
			ClientFilter.Columns.TABLE_NAME + " (" + //
			ClientFilter.Columns._ID + " text primary key, " + //
			ClientFilter.Columns.MODIFIED + " integer, " + //
			ClientFilter.Columns.MODIFIEDBY + " text, " + //
			ClientFilter.Columns.MODIFIEDFROM + " text, " + //
			ClientFilter.Columns.UPLOADDATE + " integer, " + //
			ClientFilter.Columns.CREATED + " integer, " + //
			ClientFilter.Columns.CREATEDBY + " text, " + //
			ClientFilter.Columns.DELETED + " integer, " + //
			ClientFilter.Columns.FLAG_READ + " integer, " + //
			ClientFilter.Columns.FLAG_SYNCHRONIZED + " integer, " + //
			ClientFilter.Columns.USERID + " text, " + //
			ClientFilter.Columns.TERMINALID + " text, " + //
			ClientFilter.Columns.CARDENTITY + " text, " + //
			ClientFilter.Columns.ENTITYFIELD + " text, " + //
			ClientFilter.Columns.OPERATOR + " text, " + //
			ClientFilter.Columns.FIELDVALUE + " text, " + //
			ClientFilter.Columns.DISPLAY + " text, " + //
			ClientFilter.Columns.ISNEW + " text);";

	private static final String DATABASE_CREATE_BINARIES = "create table if not exists " + //
			Binary.Columns.TABLE_NAME + " (" + //
			Binary.Columns._ID + " text primary key, " + //
			Binary.Columns.MODIFIED + " integer, " + //
			Binary.Columns.MODIFIEDBY + " text, " + //
			Binary.Columns.MODIFIEDFROM + " text, " + //
			Binary.Columns.UPLOADDATE + " integer, " + //
			Binary.Columns.CREATED + " integer, " + //
			Binary.Columns.CREATEDBY + " text, " + //
			Binary.Columns.DELETED + " integer, " + //
			Binary.Columns.FLAG_READ + " integer, " + //
			Binary.Columns.FLAG_SYNCHRONIZED + " integer, " + //
			Binary.Columns.LENGTH + " text, " + //
			Binary.Columns.DATA + " text, " + //
			Binary.Columns.CONTENT_TYPE + " text, " + //
			Binary.Columns.FILE_NAME + " text);";

	private static final String DATABASE_CREATE_DYNAMICFIELD_TEMPLATE = "create table if not exists " + //
			DynamicFieldTemplate.Columns.TABLE_NAME + " (" + //
			DynamicFieldTemplate.Columns._ID + " text primary key, " + //
			DynamicFieldTemplate.Columns.MODIFIED + " integer, " + //
			DynamicFieldTemplate.Columns.MODIFIEDBY + " text, " + //
			DynamicFieldTemplate.Columns.MODIFIEDFROM + " text, " + //
			DynamicFieldTemplate.Columns.UPLOADDATE + " integer, " + //
			DynamicFieldTemplate.Columns.CREATED + " integer, " + //
			DynamicFieldTemplate.Columns.CREATEDBY + " text, " + //
			DynamicFieldTemplate.Columns.DELETED + " integer, " + //
			DynamicFieldTemplate.Columns.FLAG_READ + " integer, " + //
			DynamicFieldTemplate.Columns.FIELDNAME + " text, " + //
			DynamicFieldTemplate.Columns.FIELDTYPE + " text, " + //
			DynamicFieldTemplate.Columns.PARAMETERS + " text, " + //
			DynamicFieldTemplate.Columns.FORMTYPE + " text, " + //
			DynamicFieldTemplate.Columns.TEMPLATECREATOR + " text, " + //
			DynamicFieldTemplate.Columns.DESCRIPTION + " text, " + //
			DynamicFieldTemplate.Columns.REASON + " text, " + //
			DynamicFieldTemplate.Columns.ISDEFAULT + " text, " + //
			DynamicFieldTemplate.Columns.REQUIREDVALUE + " text, " + //
			DynamicFieldTemplate.Columns.FIELDPOSITION + " integer, " + //
			DynamicFieldTemplate.Columns.MINIMUMVALUE + " text, " + //
			DynamicFieldTemplate.Columns.MAXIMUMVALUE + " text, " + //
			DynamicFieldTemplate.Columns.DEFAULTVALUE + " text, " + //
			DynamicFieldTemplate.Columns.ALLUSERS + " text, " + //
			DynamicFieldTemplate.Columns.ISACTIVATED + " text, " + //
			DynamicFieldTemplate.Columns.FLAG_SYNCHRONIZED + " integer);";

	private static final String DATABASE_CREATE_DYNAMICFIELD_INSTANCE = "create table if not exists " + //
			DynamicFieldInstance.Columns.TABLE_NAME + " (" + //
			DynamicFieldInstance.Columns._ID + " text primary key, " + //
			DynamicFieldInstance.Columns.MODIFIED + " integer, " + //
			DynamicFieldInstance.Columns.MODIFIEDBY + " text, " + //
			DynamicFieldInstance.Columns.MODIFIEDFROM + " text, " + //
			DynamicFieldInstance.Columns.UPLOADDATE + " integer, " + //
			DynamicFieldInstance.Columns.CREATED + " integer, " + //
			DynamicFieldInstance.Columns.CREATEDBY + " text, " + //
			DynamicFieldInstance.Columns.DELETED + " integer, " + //
			DynamicFieldInstance.Columns.FLAG_READ + " integer, " + //
			DynamicFieldInstance.Columns.FIELDTEMPLATE + " text, " + //
			DynamicFieldInstance.Columns.FIELDVALUE + " text, " + //
			DynamicFieldInstance.Columns.FORMINSTANCE + " text, " + //
			DynamicFieldInstance.Columns.FLAG_SYNCHRONIZED + " integer);";

	private static final String DATABASE_CREATE_SYNCHISTORY = "create table if not exists " + //
			SyncHistory.Columns.TABLE_NAME + " (" + //
			SyncHistory.Columns._ID + " integer primary key autoincrement, " + //
			SyncHistory.Columns.ID + " text not null, " + //
			SyncHistory.Columns.DATE + " integer not null, " + //
			SyncHistory.Columns.STATUS + " integer, " + //
			SyncHistory.Columns.LEVEL + " integer);";

	private static final String DATABASE_CREATE_GPSLOCATION = "create table if not exists " + //
			GpsLocation.Columns.TABLE_NAME + " (" + //
			GpsLocation.Columns._ID + " integer primary key autoincrement, " + //
			GpsLocation.Columns.ACCURACY + " real, " + //
			GpsLocation.Columns.ALTITUDE + " real, " + //
			GpsLocation.Columns.BEARING + " real, " + //
			GpsLocation.Columns.LATITUDE + " real, " + //
			GpsLocation.Columns.LONGITUDE + " real, " + //
			GpsLocation.Columns.PROVIDER + " text, " + //
			GpsLocation.Columns.SPEED + " real, " + //
			GpsLocation.Columns.TIME + " integer, " + //
			GpsLocation.Columns.HASACCURACY + " integer, " + //
			GpsLocation.Columns.HASALTITUDE + " integer, " + //
			GpsLocation.Columns.HASBEARING + " integer, " + //
			GpsLocation.Columns.HASSPEED + " integer);";

	private static final String DATABASE_CREATE_PRECACHEBOUNDS = "create table if not exists " + //
			PreCache.Columns.TABLE_NAME + " (" + //
			PreCache.Columns._ID + " integer primary key autoincrement, " + //
			PreCache.Columns.PROVIDER + " text, " + //
			PreCache.Columns.NORTH + " real, " + //
			PreCache.Columns.EAST + " real, " + //
			PreCache.Columns.SOUTH + " real, " + //
			PreCache.Columns.WEST + " real, " + //
			PreCache.Columns.ZOOM_MIN + " integer, " + //
			PreCache.Columns.ZOOM_MAX + " integer);";

	/**
	 * Convenient method to retrieve an {@link ImogBean} from an {@link Uri}.
	 * 
	 * @param uri The uri representing the bean
	 * @return The bean if any or {@code null}
	 */
	public static <T extends ImogBean> T fromUri(Uri uri) {
		if (uri == null) {
			return null;
		}
		ImogBeanCursor<T> cursor = ImogOpenHelper.getHelper().query(uri);
		if (cursor == null) {
			return null;
		}
		try {
			if (!cursor.moveToFirst()) {
				return null;
			}
			if (cursor.getCount() > 1) {
				throw new IllegalArgumentException("The uri do not represent a single entity: " + uri);
			}
			return cursor.newBean();
		} finally {
			cursor.close();
		}
	}

	public ImogOpenHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE_PROFILE);
		db.execSQL(DATABASE_CREATE_ENTITYPROFILE);
		db.execSQL(DATABASE_CREATE_FIELDGROUPPROFILE);
		db.execSQL(DATABASE_CREATE_CARDENTITY);
		db.execSQL(DATABASE_CREATE_FIELDGROUP);
		db.execSQL(DATABASE_CREATE_ACTOR_PROFILES);
		db.execSQL(DATABASE_CREATE_BINARIES);
		db.execSQL(DATABASE_CREATE_CLIENTFILTER);
		db.execSQL(DATABASE_CREATE_DEFAULTUSER);
		db.execSQL(DATABASE_CREATE_SYNCHISTORY);
		db.execSQL(DATABASE_CREATE_DYNAMICFIELD_INSTANCE);
		db.execSQL(DATABASE_CREATE_DYNAMICFIELD_TEMPLATE);
		db.execSQL(DATABASE_CREATE_GPSLOCATION);
		db.execSQL(DATABASE_CREATE_PRECACHEBOUNDS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		upgrade(db, Profile.Columns.TABLE_NAME, Profile.Columns.DELETED, "integer");
		upgrade(db, EntityProfile.Columns.TABLE_NAME, EntityProfile.Columns.DELETED, "integer");
		upgrade(db, FieldGroupProfile.Columns.TABLE_NAME, FieldGroupProfile.Columns.DELETED, "integer");
		upgrade(db, CardEntity.Columns.TABLE_NAME, CardEntity.Columns.DELETED, "integer");
		upgrade(db, FieldGroup.Columns.TABLE_NAME, FieldGroup.Columns.DELETED, "integer");
		upgrade(db, Binary.Columns.TABLE_NAME, Binary.Columns.DELETED, "integer");
		upgrade(db, ClientFilter.Columns.TABLE_NAME, ClientFilter.Columns.DELETED, "integer");
		upgrade(db, DefaultUser.Columns.TABLE_NAME, DefaultUser.Columns.DELETED, "integer");
		upgrade(db, DynamicFieldInstance.Columns.TABLE_NAME, DynamicFieldInstance.Columns.DELETED, "integer");
		upgrade(db, DynamicFieldTemplate.Columns.TABLE_NAME, DynamicFieldTemplate.Columns.DELETED, "integer");
	}

	public QueryBuilder queryBuilder(String tableName) {
		return new QueryBuilder(this, tableName);
	}

	public QueryBuilder queryBuilder(Uri uri) {
		QueryBuilder builder = null;
		switch (ImogProvider.URI_MATCHER.match(uri)) {
		case ImogProvider.PROFILE:
			builder = new QueryBuilder(this, Profile.Columns.TABLE_NAME);
			builder.setCursorFactory(new ProfileCursor.Factory());
			builder.setUri(uri);
			return builder;
		case ImogProvider.PROFILE_ID:
			builder = new QueryBuilder(this, Profile.Columns.TABLE_NAME);
			builder.setCursorFactory(new ProfileCursor.Factory());
			builder.setUri(uri);
			builder.where().idEq(uri.getLastPathSegment());
			return builder;
		case ImogProvider.ENTITYPROFILE:
			builder = new QueryBuilder(this, EntityProfile.Columns.TABLE_NAME);
			builder.setCursorFactory(new EntityProfileCursor.Factory());
			builder.setUri(uri);
			return builder;
		case ImogProvider.ENTITYPROFILE_ID:
			builder = new QueryBuilder(this, EntityProfile.Columns.TABLE_NAME);
			builder.setCursorFactory(new EntityProfileCursor.Factory());
			builder.setUri(uri);
			builder.where().idEq(uri.getLastPathSegment());
			return builder;
		case ImogProvider.FIELDGROUPPROFILE:
			builder = new QueryBuilder(this, FieldGroupProfile.Columns.TABLE_NAME);
			builder.setCursorFactory(new FieldGroupProfileCursor.Factory());
			builder.setUri(uri);
			return builder;
		case ImogProvider.FIELDGROUPPROFILE_ID:
			builder = new QueryBuilder(this, FieldGroupProfile.Columns.TABLE_NAME);
			builder.setCursorFactory(new FieldGroupProfileCursor.Factory());
			builder.setUri(uri);
			builder.where().idEq(uri.getLastPathSegment());
			return builder;
		case ImogProvider.CARDENTITY:
			builder = new QueryBuilder(this, CardEntity.Columns.TABLE_NAME);
			builder.setCursorFactory(new CardEntityCursor.Factory());
			builder.setUri(uri);
			return builder;
		case ImogProvider.CARDENTITY_ID:
			builder = new QueryBuilder(this, CardEntity.Columns.TABLE_NAME);
			builder.setCursorFactory(new CardEntityCursor.Factory());
			builder.setUri(uri);
			builder.where().idEq(uri.getLastPathSegment());
			return builder;
		case ImogProvider.FIELDGROUP:
			builder = new QueryBuilder(this, FieldGroup.Columns.TABLE_NAME);
			builder.setCursorFactory(new FieldGroupCursor.Factory());
			builder.setUri(uri);
			return builder;
		case ImogProvider.FIELDGROUP_ID:
			builder = new QueryBuilder(this, FieldGroup.Columns.TABLE_NAME);
			builder.setCursorFactory(new FieldGroupCursor.Factory());
			builder.setUri(uri);
			builder.where().idEq(uri.getLastPathSegment());
			return builder;
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

	@Override
	public <T extends Cursor> T query(Uri uri) {
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

	protected void upgrade(SQLiteDatabase db, String table, String column, String type) {
		try {
			db.compileStatement("select " + column + " from " + table + " limit 1").close();
		} catch (Exception e) {
			Log.i(ImogOpenHelper.class.getName(), "alter table " + table + " with column " + column + " of type " + type);
			db.execSQL("alter table " + table + " add column " + column + " " + type + ";");
		}
	}

}
