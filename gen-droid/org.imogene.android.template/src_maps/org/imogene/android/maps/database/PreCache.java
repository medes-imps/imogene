package org.imogene.android.maps.database;

import org.imogene.android.util.content.ContentUrisUtils;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.BaseColumns;

public class PreCache  {

	public static class Columns implements BaseColumns {
		public static final String TABLE_NAME = "preCacheBounds";
		public static final Uri CONTENT_URI = ContentUrisUtils.buildUriForFragment(TABLE_NAME);

		public static final String PROVIDER = "provider";
		public static final String NORTH = "north";
		public static final String EAST = "east";
		public static final String SOUTH = "south";
		public static final String WEST = "west";
		public static final String ZOOM_MIN = "zoomMin";
		public static final String ZOOM_MAX = "zoomMax";
	}
	
	public long _id = -1;
	public String provider;
	public double north;
	public double east;
	public double south;
	public double west;
	public int zoomMin;
	public int zoomMax;
	
	public PreCache() {
		
	}
	
	public Uri saveOrUpdate(Context context) {
		ContentValues values = new ContentValues();
		values.put(Columns.PROVIDER, provider);
		values.put(Columns.NORTH, north);
		values.put(Columns.EAST, east);
		values.put(Columns.SOUTH, south);
		values.put(Columns.WEST, west);
		values.put(Columns.ZOOM_MIN, zoomMin);
		values.put(Columns.ZOOM_MAX, zoomMax);
		
		Uri uri = null;
		if (_id != -1) {
			uri = ContentUris.withAppendedId(Columns.CONTENT_URI, _id);
			context.getContentResolver().update(uri, values, null, null);
		} else {
			uri = context.getContentResolver().insert(Columns.CONTENT_URI, values);
			_id = ContentUris.parseId(uri);
		}
		return uri;
	}

}
