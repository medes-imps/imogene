package org.imogene.android.common.entity;

import org.imogene.android.database.sqlite.ImogOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.provider.BaseColumns;
import fr.medes.android.database.sqlite.stmt.QueryBuilder;

public class GpsLocation extends Location {

	public static class Columns implements BaseColumns {
		public static final String TABLE_NAME = "gpslocation";

		public static final String ACCURACY = "accuracy";
		public static final String ALTITUDE = "altitude";
		public static final String BEARING = "bearing";
		public static final String LATITUDE = "latitude";
		public static final String LONGITUDE = "longitude";
		public static final String PROVIDER = "provider";
		public static final String SPEED = "speed";
		public static final String TIME = "time";
		public static final String HASACCURACY = "hasaccuracy";
		public static final String HASALTITUDE = "hasaltitude";
		public static final String HASBEARING = "hasbearing";
		public static final String HASSPEED = "hasspeed";
	}

	public static GpsLocation getLocation(Context context, long _id) {
		QueryBuilder builder = ImogOpenHelper.getHelper().queryBuilder(Columns.TABLE_NAME);
		builder.where().eq(Columns._ID, _id);
		Cursor c = builder.query();
		GpsLocation result = null;
		if (c.moveToFirst()) {
			result = new GpsLocation(c);
		}
		c.close();
		return result;
	}

	public static long saveLocation(Context context, Location location) {
		if (location == null)
			return -1;

		if (location instanceof GpsLocation) {
			return ((GpsLocation) location).saveOrUpdate(context);
		} else {
			return new GpsLocation(location).saveOrUpdate(context);
		}
	}

	private long _id = -1;

	public GpsLocation(Location l) {
		super(l);
	}

	protected GpsLocation(Cursor c) {
		super("cursor");
		_id = c.getLong(c.getColumnIndexOrThrow(Columns._ID));
		setAccuracy(c.getFloat(c.getColumnIndexOrThrow(Columns.ACCURACY)));
		setAltitude(c.getDouble(c.getColumnIndexOrThrow(Columns.ALTITUDE)));
		setBearing(c.getFloat(c.getColumnIndexOrThrow(Columns.BEARING)));
		setLatitude(c.getDouble(c.getColumnIndexOrThrow(Columns.LATITUDE)));
		setLongitude(c.getDouble(c.getColumnIndexOrThrow(Columns.LONGITUDE)));
		setProvider(c.getString(c.getColumnIndexOrThrow(Columns.PROVIDER)));
		setSpeed(c.getFloat(c.getColumnIndexOrThrow(Columns.SPEED)));
		setTime(c.getLong(c.getColumnIndexOrThrow(Columns.TIME)));
	}

	public long saveOrUpdate(Context context) {
		ContentValues values = new ContentValues();
		values.put(Columns.ACCURACY, getAccuracy());
		values.put(Columns.ALTITUDE, getAltitude());
		values.put(Columns.BEARING, getBearing());
		values.put(Columns.LATITUDE, getLatitude());
		values.put(Columns.LONGITUDE, getLongitude());
		values.put(Columns.PROVIDER, getProvider());
		values.put(Columns.SPEED, getSpeed());
		values.put(Columns.TIME, getTime());
		values.put(Columns.HASACCURACY, hasAccuracy() ? 1 : 0);
		values.put(Columns.HASALTITUDE, hasAltitude() ? 1 : 0);
		values.put(Columns.HASBEARING, hasBearing() ? 1 : 0);
		values.put(Columns.HASSPEED, hasSpeed() ? 1 : 0);

		if (_id != -1) {
			QueryBuilder builder = ImogOpenHelper.getHelper().queryBuilder(Columns.TABLE_NAME);
			builder.where().idEq(_id);
			builder.update(values);
		} else {
			_id = ImogOpenHelper.getHelper().insert(Columns.TABLE_NAME, values);
		}
		return _id;
	}

}
