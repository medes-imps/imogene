package org.imogene.lib.common.dao;

import org.imogene.lib.common.entity.ImogActorImpl;

/**
 * Manage persistence for ImogActorImpl
 */
public class ImogActorImplDaoImpl extends ImogActorDaoImpl<ImogActorImpl>
		implements
		ImogActorImplDao {

	protected ImogActorImplDaoImpl() {
		super(ImogActorImpl.class);
	}

	@Override
	public void delete() {
		//TODO
	}



}
