package org.imogene.android.domain;

import java.util.List;

import android.net.Uri;

public interface ImogEntity extends ImogBean {

	public void setDynamicFieldValues(List<Uri> dynamicFieldValues);

	public List<Uri> getDynamicFieldValues();

}