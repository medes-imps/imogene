package org.imogene.android.common.entity;

import java.util.List;

import org.imogene.android.common.dynamicfields.DynamicFieldInstance;

public interface ImogEntity extends ImogBean {

	public void setDynamicFieldValues(List<DynamicFieldInstance> dynamicFieldValues);

	public List<DynamicFieldInstance> getDynamicFieldValues();

}