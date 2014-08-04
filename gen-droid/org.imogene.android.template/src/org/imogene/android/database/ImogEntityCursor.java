package org.imogene.android.database;

import java.util.List;

import org.imogene.android.common.dynamicfields.DynamicFieldInstance;
import org.imogene.android.common.entity.ImogEntity;

public interface ImogEntityCursor<T extends ImogEntity> extends ImogBeanCursor<T> {

	public List<DynamicFieldInstance> getDynamicFieldValues();

}
