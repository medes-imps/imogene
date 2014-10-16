package org.imogene.android.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.imogene.android.common.dynamicfields.DynamicFieldInstance;
import org.imogene.android.common.entity.ImogActor;
import org.imogene.android.common.profile.Profile;

import android.content.ContentResolver;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

public class ImogActorCursorJoiner implements ImogActorCursor<ImogActor> {

	private final ArrayList<Join> mCursors = new ArrayList<Join>();

	private int current = 0;

	@Override
	public ImogActor newBean() {
		return null;
	}

	public boolean newCursor(ImogActorCursor<? extends ImogActor> cursor, Drawable drawable, Uri uri) {
		return mCursors.add(new Join(cursor, uri, drawable));
	}

	public Drawable getDrawable() {
		return mCursors.get(current).drawable;
	}

	public Uri getUri() {
		return mCursors.get(current).uri;
	}

	@Override
	public String getLogin() {
		return mCursors.get(current).cursor.getLogin();
	}

	@Override
	public byte[] getPassword() {
		return mCursors.get(current).cursor.getPassword();
	}

	@Override
	public List<Profile> getProfiles() {
		return mCursors.get(current).cursor.getProfiles();
	}

	@Override
	public String getUserDisplay(Context context) {
		return mCursors.get(current).cursor.getUserDisplay(context);
	}

	@Override
	public Date getCreated() {
		return mCursors.get(current).cursor.getCreated();
	}

	@Override
	public String getCreatedBy() {
		return mCursors.get(current).cursor.getCreatedBy();
	}

	@Override
	public Date getDeleted() {
		return mCursors.get(current).cursor.getDeleted();
	}

	@Override
	public String getId() {
		return mCursors.get(current).cursor.getId();
	}

	@Override
	public String getMainDisplay(Context context) {
		return mCursors.get(current).cursor.getMainDisplay(context);
	}

	@Override
	public Date getModified() {
		return mCursors.get(current).cursor.getModified();
	}

	@Override
	public String getModifiedBy() {
		return mCursors.get(current).cursor.getModifiedBy();
	}

	@Override
	public String getModifiedFrom() {
		return mCursors.get(current).cursor.getModifiedFrom();
	}

	@Override
	public List<DynamicFieldInstance> getDynamicFieldValues() {
		return mCursors.get(current).cursor.getDynamicFieldValues();
	}

	@Override
	public String getSecondaryDisplay(Context context) {
		return mCursors.get(current).cursor.getSecondaryDisplay(context);
	}

	@Override
	public boolean getFlagSynchronized() {
		return mCursors.get(current).cursor.getFlagSynchronized();
	}

	@Override
	public boolean getFlagRead() {
		return mCursors.get(current).cursor.getFlagRead();
	}

	@Override
	public Date getUploadDate() {
		return mCursors.get(current).cursor.getUploadDate();
	}

	@Override
	public void close() {
		for (Join join : mCursors)
			join.cursor.close();
	}

	@Override
	public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {
		mCursors.get(current).cursor.copyStringToBuffer(columnIndex, buffer);
	}

	@Override
	public void deactivate() {
		for (Join join : mCursors)
			join.cursor.deactivate();
	}

	@Override
	public byte[] getBlob(int columnIndex) {
		return mCursors.get(current).cursor.getBlob(columnIndex);
	}

	@Override
	public int getColumnCount() {
		return mCursors.get(current).cursor.getColumnCount();
	}

	@Override
	public int getColumnIndex(String columnName) {
		return mCursors.get(current).cursor.getColumnIndex(columnName);
	}

	@Override
	public int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException {
		return mCursors.get(current).cursor.getColumnIndexOrThrow(columnName);
	}

	@Override
	public String getColumnName(int columnIndex) {
		return mCursors.get(current).cursor.getColumnName(columnIndex);
	}

	@Override
	public String[] getColumnNames() {
		return mCursors.get(current).cursor.getColumnNames();
	}

	@Override
	public int getCount() {
		int result = 0;
		for (Join join : mCursors)
			result += join.cursor.getCount();
		return result;
	}

	@Override
	public double getDouble(int columnIndex) {
		return mCursors.get(current).cursor.getDouble(columnIndex);
	}

	@Override
	public Bundle getExtras() {
		return mCursors.get(current).cursor.getExtras();
	}

	@Override
	public float getFloat(int columnIndex) {
		return mCursors.get(current).cursor.getFloat(columnIndex);
	}

	@Override
	public int getInt(int columnIndex) {
		return mCursors.get(current).cursor.getInt(columnIndex);
	}

	@Override
	public long getLong(int columnIndex) {
		return mCursors.get(current).cursor.getLong(columnIndex);
	}

	@Override
	public int getPosition() {
		int result = 0;
		for (int i = 0; i < current; i++)
			result += mCursors.get(i).cursor.getCount();
		return result + mCursors.get(current).cursor.getPosition();
	}

	@Override
	public short getShort(int columnIndex) {
		return mCursors.get(current).cursor.getShort(columnIndex);
	}

	@Override
	public String getString(int columnIndex) {
		return mCursors.get(current).cursor.getString(columnIndex);
	}

	@Override
	public int getType(int columnIndex) {
		return mCursors.get(current).cursor.getType(columnIndex);
	}

	@Override
	public boolean getWantsAllOnMoveCalls() {
		return mCursors.get(current).cursor.getWantsAllOnMoveCalls();
	}

	@Override
	public boolean isAfterLast() {
		return getCount() == 0 ? true : getPosition() == getCount();
	}

	@Override
	public boolean isBeforeFirst() {
		return getCount() == 0 ? true : getPosition() == -1;
	}

	@Override
	public boolean isClosed() {
		boolean result = true;
		for (Join join : mCursors)
			result &= join.cursor.isClosed();
		return result;
	}

	@Override
	public boolean isFirst() {
		return getCount() != 0 && getPosition() == 0;
	}

	@Override
	public boolean isLast() {
		return getCount() != 0 && getPosition() == getCount() - 1;
	}

	@Override
	public boolean isNull(int columnIndex) {
		return mCursors.get(current).cursor.isNull(columnIndex);
	}

	@Override
	public boolean move(int offset) {
		return moveToPosition(getPosition() + offset);
	}

	@Override
	public boolean moveToFirst() {
		return moveToPosition(0);
	}

	@Override
	public boolean moveToLast() {
		return moveToPosition(getCount() - 1);
	}

	@Override
	public boolean moveToNext() {
		return moveToPosition(getPosition() + 1);
	}

	@Override
	public boolean moveToPosition(int position) {
		if (position < 0 || position >= getCount())
			return false;
		if (position == getPosition())
			return true;

		int relative = position;
		for (int i = 0; i < mCursors.size(); i++) {
			if (relative < mCursors.get(i).cursor.getCount()) {
				current = i;
				return mCursors.get(i).cursor.moveToPosition(relative);
			} else {
				relative -= mCursors.get(i).cursor.getCount();
			}
		}
		return false;
	}

	@Override
	public boolean moveToPrevious() {
		return moveToPosition(getPosition() - 1);
	}

	@Override
	public void registerContentObserver(ContentObserver observer) {
		for (Join join : mCursors)
			join.cursor.registerContentObserver(observer);
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		for (Join join : mCursors)
			join.cursor.registerDataSetObserver(observer);
	}

	@Override
	public boolean requery() {
		boolean result = true;
		for (Join join : mCursors)
			result &= join.cursor.requery();
		return result;
	}

	@Override
	public Bundle respond(Bundle extras) {
		return mCursors.get(current).cursor.respond(extras);
	}

	@Override
	public void setNotificationUri(ContentResolver cr, Uri uri) {
		for (Join join : mCursors)
			join.cursor.setNotificationUri(cr, uri);
	}

	@Override
	public void unregisterContentObserver(ContentObserver observer) {
		for (Join join : mCursors)
			join.cursor.unregisterContentObserver(observer);
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		for (Join join : mCursors)
			join.cursor.unregisterDataSetObserver(observer);
	}

	private static class Join {

		private final ImogActorCursor<? extends ImogActor> cursor;
		private final Drawable drawable;
		private final Uri uri;

		public Join(ImogActorCursor<? extends ImogActor> cursor, Uri uri, Drawable drawable) {
			this.cursor = cursor;
			this.drawable = drawable;
			this.uri = uri;
		}
	}

}
