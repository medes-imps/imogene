package org.imogene.lib.common.profile;

import org.imogene.lib.common.dao.ImogBeanDaoImpl;

/**
 * Manage persistence for EntityProfile
 */
public class EntityProfileDaoImpl extends ImogBeanDaoImpl<EntityProfile> implements EntityProfileDao {

	protected EntityProfileDaoImpl() {
		super(EntityProfile.class);
	}

	@Override
	public void delete() {
		// TODO
	}

	/* relation dependencies */

}
