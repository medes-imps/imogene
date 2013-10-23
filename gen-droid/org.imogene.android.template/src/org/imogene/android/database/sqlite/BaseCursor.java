package org.imogene.android.database.sqlite;

import java.util.Date;

import org.imogene.android.util.FormatHelper;

import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;

public class BaseCursor extends SQLiteCursor {

	public BaseCursor(SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
		super(db, driver, editTable, query);
	}
	
	public byte[] getBlob(String columnName) {
		return getBlob(getColumnIndexOrThrow(columnName));
	}

	public String getString(String columnName) {
		return getString(getColumnIndexOrThrow(columnName));
	}
	
	public long getLong(String columnName) {
		return getLong(getColumnIndexOrThrow(columnName));
	}
	
	public Date getDate(String columnName) {
		Long date = getAsLong(columnName);
		if (date != null) {
			return new Date(date);
		}
		return null;
	}
	
	public double getDouble(String columnName) {
		return getDouble(getColumnIndexOrThrow(columnName));
	}
	
	public int getInt(String columnName) {
		return getInt(getColumnIndexOrThrow(columnName));
	}
	
	public boolean getBoolean(String columnName) {
		return getInt(columnName) != 0;
	}
	
	public Long getAsLong(String columnName) {
		return FormatHelper.toLong(getString(columnName));
	}
	
	public Integer getAsInteger(String columnName) {
		return FormatHelper.toInteger(getString(columnName));
	}
	
	public Float getAsFloat(String columnName) {
		return FormatHelper.toFloat(getString(columnName));
	}
	
	public Boolean getAsBoolean(String columnName) {
		return FormatHelper.toBoolean(getString(columnName));
	}
}
