package org.imogene.lib.common.profile;

import org.imogene.lib.common.dao.ImogBeanDaoImpl;

/**
 * Manage persistence for FieldGroupProfile
 */
public class FieldGroupProfileDaoImpl extends ImogBeanDaoImpl<FieldGroupProfile> implements FieldGroupProfileDao {

	protected FieldGroupProfileDaoImpl() {
		super(FieldGroupProfile.class);
	}

	@Override
	public void delete() {
		// TODO
	}

	/* relation dependencies */

}
