package org.imogene.android.database;

import android.net.Uri;

public interface BinaryCursor extends ImogBeanCursor {
	
	public String getFileName();
	
	public String getContentType();
	
	public long getLength();
	
	public Uri getData();

}
