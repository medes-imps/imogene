package org.imogene.android.database;

import java.util.List;

import android.net.Uri;

public interface ImogEntityCursor extends ImogBeanCursor {

	public List<Uri> getDynamicFieldValues();

}
