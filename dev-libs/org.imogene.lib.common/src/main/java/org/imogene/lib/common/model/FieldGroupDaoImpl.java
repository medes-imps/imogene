package org.imogene.lib.common.model;

import org.imogene.lib.common.dao.ImogBeanDaoImpl;

/**
 * Manage persistence for FieldGroup
 */
public class FieldGroupDaoImpl extends ImogBeanDaoImpl<FieldGroup> implements FieldGroupDao {

	protected FieldGroupDaoImpl() {
		super(FieldGroup.class);
	}

	@Override
	public void delete() {
		// TODO
	}

	/* relation dependencies */

}
