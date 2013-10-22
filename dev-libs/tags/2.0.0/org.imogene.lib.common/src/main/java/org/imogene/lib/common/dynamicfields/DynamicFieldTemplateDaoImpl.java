package org.imogene.lib.common.dynamicfields;

import org.imogene.lib.common.dao.ImogBeanDaoImpl;

/**
 * Manage persistence for DynamicField_Template
 */
public class DynamicFieldTemplateDaoImpl extends ImogBeanDaoImpl<DynamicFieldTemplate> implements
		DynamicFieldTemplateDao {

	protected DynamicFieldTemplateDaoImpl() {
		super(DynamicFieldTemplate.class);
	}

	@Override
	public void delete() {
		// TODO
	}

}
