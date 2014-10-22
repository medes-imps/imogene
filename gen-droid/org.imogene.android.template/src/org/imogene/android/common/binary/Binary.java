package org.imogene.android.common.binary;

import org.imogene.android.Constants;
import org.imogene.android.common.entity.ImogBean;

import android.net.Uri;
import fr.medes.android.util.content.ContentUrisUtils;

public interface Binary extends ImogBean {

	public static interface Columns extends ImogBean.Columns {
		public static final String TABLE_NAME = "binaries";
		public static final String BEAN_TYPE = "BIN";

		public static final Uri CONTENT_URI = ContentUrisUtils.buildUriForFragment(Constants.AUTHORITY, TABLE_NAME);

		public static final String SYNC_TMP = "temporary";

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
