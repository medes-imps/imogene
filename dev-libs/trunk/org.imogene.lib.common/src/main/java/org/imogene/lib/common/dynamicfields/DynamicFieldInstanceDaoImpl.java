package org.imogene.lib.common.dynamicfields;

import org.imogene.lib.common.dao.ImogBeanDaoImpl;

/**
 * Manage persistence for DynamicField_Instance
 */
public class DynamicFieldInstanceDaoImpl extends ImogBeanDaoImpl<DynamicFieldInstance> implements
		DynamicFieldInstanceDao {

	protected DynamicFieldInstanceDaoImpl() {
		super(DynamicFieldInstance.class);
	}

	@Override
	public void delete() {
		// TODO
	}

}
