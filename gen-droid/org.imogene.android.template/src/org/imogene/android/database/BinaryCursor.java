package org.imogene.android.database;

import org.imogene.android.common.binary.Binary;

import android.net.Uri;

public interface BinaryCursor<T extends Binary> extends ImogBeanCursor<T> {

	public String getFileName();

	public String getContentType();

	public long getLength();

	public Uri getData();

}
