package org.imogene.android.database.sqlite;

import org.imogene.android.common.model.CardEntity;
import org.imogene.android.database.DatabaseCache;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteQuery;

public class CardEntityCursor extends ImogBeanCursorImpl<CardEntity> {

	public CardEntityCursor(SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
		super(db, driver, editTable, query);
	}

	public static class Factory implements CursorFactory {
		@Override
		public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery, String editTable, SQLiteQuery query) {
			return new CardEntityCursor(db, masterQuery, editTable, query);
		}
	}

	@Override
	public CardEntity newBean() {
		DatabaseCache.getInstance().beginTransaction();
		CardEntity bean = DatabaseCache.getInstance().get(getId());
		if (bean == null) {
			bean = new CardEntity(this);
		}
		DatabaseCache.getInstance().endTransaction();
		return bean;
	}

	public final String getName() {
		return getString(CardEntity.Columns.NAME);
	}

	@Override
	public String getMainDisplay(Context context) {
		StringBuilder str = new StringBuilder();
		String name = getName();
		if (name != null) {
			str.append(name);
			str.append(" ");
		}
		return str.toString();
	}

	@Override
	public String getSecondaryDisplay(Context context) {
		StringBuilder str = new StringBuilder();
		return str.toString();
	}

}
