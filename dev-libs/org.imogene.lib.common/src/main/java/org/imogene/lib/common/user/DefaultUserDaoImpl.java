package org.imogene.lib.common.user;

import org.imogene.lib.common.dao.ImogActorDaoImpl;

public class DefaultUserDaoImpl extends ImogActorDaoImpl<DefaultUser> implements DefaultUserDao {

	protected DefaultUserDaoImpl() {
		super(DefaultUser.class);
	}

	@Override
	public void delete() {
		em.createQuery("DELETE FROM DefaultUser").executeUpdate();
	}

}
