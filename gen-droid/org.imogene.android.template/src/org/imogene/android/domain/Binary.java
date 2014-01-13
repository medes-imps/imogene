package org.imogene.android.domain;

import org.imogene.android.util.content.ContentUrisUtils;

import android.net.Uri;

public interface Binary extends ImogBean {
	
	public static interface Columns extends ImogBean.Columns {
		public static final String TABLE_NAME = "binaries";
		public static final String TYPE = "BIN";

		public static final Uri CONTENT_URI = ContentUrisUtils.buildUriForFragment(TABLE_NAME);
		
		public static final String LENGTH = "length";
		public static final String CONTENT_TYPE = "contentType";
		public static final String FILE_NAME = "fileName";
		public static final String DATA = "_data";
	}

	public String getFileName();

	public void setFileName(String name);

	public String getContentType();

	public void setContentType(String contentType);

	public long getLength();

	public void setLength(long _length);
	
	public Uri getData();

	public void setData(Uri uri);

}
