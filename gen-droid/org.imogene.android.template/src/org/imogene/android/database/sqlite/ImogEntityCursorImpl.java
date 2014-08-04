package org.imogene.android.database.sqlite;

import java.util.List;

import org.imogene.android.common.dynamicfields.DynamicFieldInstance;
import org.imogene.android.common.entity.ImogEntity;
import org.imogene.android.database.ImogEntityCursor;

import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;

public abstract class ImogEntityCursorImpl<T extends ImogEntity> extends ImogBeanCursorImpl<T> implements
		ImogEntityCursor<T> {

	public ImogEntityCursorImpl(SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
		super(db, driver, editTable, query);
	}

	@Override
	public final List<DynamicFieldInstance> getDynamicFieldValues() {
		return getEntities(DynamicFieldInstance.Columns.CONTENT_URI, DynamicFieldInstance.Columns.FORMINSTANCE);
	}

}
