package org.imogene.android.maps.database.sqlite;

import org.imogene.android.database.sqlite.BaseCursor;
import org.imogene.android.maps.database.PreCache;

import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteQuery;

public class PreCacheCursor extends BaseCursor {

	public PreCacheCursor(SQLiteDatabase db, SQLiteCursorDriver driver,
			String editTable, SQLiteQuery query) {
		super(db, driver, editTable, query);
	}
	
	public static class Factory implements CursorFactory {
		@Override
		public Cursor newCursor(SQLiteDatabase db,
				SQLiteCursorDriver masterQuery, String editTable,
				SQLiteQuery query) {
			return new PreCacheCursor(db, masterQuery, editTable, query);
		}
	}
	
	public long getId() {
		return getLong(PreCache.Columns._ID);
	}
	
	public String getProvider() {
		return getString(PreCache.Columns.PROVIDER);
	}
	
	public double getNorth() {
		return getDouble(PreCache.Columns.NORTH);
	}
	
	public double getEast() {
		return getDouble(PreCache.Columns.EAST);
	}
	
	public double getSouth() {
		return getDouble(PreCache.Columns.SOUTH);
	}
	
	public double getWest() {
		return getDouble(PreCache.Columns.WEST);
	}
	
	public int getZoomMin() {
		return getInt(PreCache.Columns.ZOOM_MIN);
	}
	
	public int getZoomMax() {
		return getInt(PreCache.Columns.ZOOM_MAX);
	}

}
