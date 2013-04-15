package org.imogene.android.database.sqlite;

import java.util.List;

import org.imogene.android.database.ImogEntityCursor;
import org.imogene.android.domain.DynamicFieldInstance;

import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.net.Uri;

public abstract class ImogEntityCursorImpl extends ImogBeanCursorImpl implements ImogEntityCursor {

	public ImogEntityCursorImpl(SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
		super(db, driver, editTable, query);
	}

	@Override
	public final List<Uri> getDynamicFieldValues() {
		return getEntities(DynamicFieldInstance.Columns.CONTENT_URI, DynamicFieldInstance.Columns.TABLE_NAME,
				DynamicFieldInstance.Columns.FORMINSTANCE);
	}

}
