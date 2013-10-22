package org.imogene.lib.sync.handler;

import java.util.List;

import org.imogene.lib.common.dao.ImogActorDao;
import org.imogene.lib.common.entity.ImogActor;

public abstract class ImogActorHandlerImpl<T extends ImogActor> extends ImogBeanHandlerImpl<T> implements ImogActorHandler<T> {

	@Override
	protected abstract ImogActorDao<T> getDao();
	
	@Override
	public List<T> getUserFromLogin(String login) {
		return getDao().loadFromLogin(login);
	}
}
