package org.imogene.android.database.sqlite;

import java.io.File;

import org.imogene.android.common.binary.Binary;
import org.imogene.android.common.binary.BinaryFile;
import org.imogene.android.database.BinaryCursor;
import org.imogene.android.database.DatabaseCache;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteQuery;
import android.net.Uri;

public class BinaryCursorImpl extends ImogBeanCursorImpl<BinaryFile> implements BinaryCursor<BinaryFile> {

	public BinaryCursorImpl(SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
		super(db, driver, editTable, query);
	}

	public static class Factory implements CursorFactory {
		@Override
		public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery, String editTable, SQLiteQuery query) {
			return new BinaryCursorImpl(db, masterQuery, editTable, query);
		}
	}

	@Override
	public BinaryFile newBean() {
		DatabaseCache.getInstance().beginTransaction();
		BinaryFile bean = DatabaseCache.getInstance().get(getId());
		if (bean == null) {
			bean = new BinaryFile(this);
		}
		DatabaseCache.getInstance().endTransaction();
		return bean;
	}

	@Override
	public final String getFileName() {
		return getString(Binary.Columns.FILE_NAME);
	}

	@Override
	public final String getContentType() {
		return getString(Binary.Columns.CONTENT_TYPE);
	}

	@Override
	public final long getLength() {
		return getLong(Binary.Columns.LENGTH);
	}

	@Override
	public final Uri getData() {
		File file = new File(getString(Binary.Columns.DATA));
		return file.exists() ? Uri.fromFile(file) : null;
	}

	@Override
	public String getMainDisplay(Context context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSecondaryDisplay(Context context) {
		// TODO Auto-generated method stub
		return null;
	}

}
