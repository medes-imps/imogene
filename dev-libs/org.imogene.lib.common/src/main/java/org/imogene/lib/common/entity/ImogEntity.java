package org.imogene.lib.common.entity;

import java.util.List;

import org.imogene.lib.common.dynamicfields.DynamicFieldInstance;

public interface ImogEntity extends ImogBean {

	public List<DynamicFieldInstance> getDynamicFieldValues();

	public void setDynamicFieldValues(List<DynamicFieldInstance> value);

}
