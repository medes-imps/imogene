package org.imogene.lib.sync.handler;

import org.imogene.lib.common.criteria.ImogJunction;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.user.DefaultUser;
import org.imogene.lib.common.user.DefaultUserDao;

/**
 * Implements a data handler for the DefaultUser
 * 
 * @author MEDES-IMPS
 */
public class DefaultUserHandlerImpl extends ImogActorHandlerImpl<DefaultUser> implements DefaultUserHandler {

	private DefaultUserDao dao;

	@Override
	public void setDao(DefaultUserDao dao) {
		this.dao = dao;
	}

	@Override
	protected DefaultUserDao getDao() {
		return dao;
	}
	
	@Override
	public DefaultUser createNewEntity(String id) {
		return null;
	}
	
	@Override
	protected ImogJunction createFilterJuntion(ImogActor actor) {
		return null;
	}

	@Override
	protected ImogJunction createClientFilterJuntion(String userId, String terminalId) {
		return null;
	}

}
