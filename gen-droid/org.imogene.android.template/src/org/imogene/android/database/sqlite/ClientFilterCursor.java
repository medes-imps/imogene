package org.imogene.android.database.sqlite;

import org.imogene.android.common.filter.ClientFilter;
import org.imogene.android.database.DatabaseCache;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteQuery;

public class ClientFilterCursor extends ImogBeanCursorImpl<ClientFilter> {

	public ClientFilterCursor(SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
		super(db, driver, editTable, query);
	}

	public static class Factory implements CursorFactory {
		@Override
		public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery, String editTable, SQLiteQuery query) {
			return new ClientFilterCursor(db, masterQuery, editTable, query);
		}
	}

	@Override
	public ClientFilter newBean() {
		DatabaseCache.getInstance().beginTransaction();
		ClientFilter bean = DatabaseCache.getInstance().get(getId());
		if (bean == null) {
			bean = new ClientFilter(this);
		}
		DatabaseCache.getInstance().endTransaction();
		return bean;
	}

	public final String getUserId() {
		return getString(ClientFilter.Columns.USERID);
	}

	public final String getTerminalId() {
		return getString(ClientFilter.Columns.TERMINALID);
	}

	public final String getCardEntity() {
		return getString(ClientFilter.Columns.CARDENTITY);
	}

	public final String getEntityField() {
		return getString(ClientFilter.Columns.ENTITYFIELD);
	}

	public final String getOperator() {
		return getString(ClientFilter.Columns.OPERATOR);
	}

	public final String getFieldValue() {
		return getString(ClientFilter.Columns.FIELDVALUE);
	}

	public final String getDisplay() {
		return getString(ClientFilter.Columns.DISPLAY);
	}

	public final Boolean getIsNew() {
		return getAsBoolean(ClientFilter.Columns.ISNEW);
	}

	@Override
	public String getMainDisplay(Context context) {
		StringBuilder str = new StringBuilder();
		if (getUserId() != null) {
			str.append(getUserId());
			str.append(" ");
		}
		if (getCardEntity() != null) {
			str.append(getCardEntity());
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
