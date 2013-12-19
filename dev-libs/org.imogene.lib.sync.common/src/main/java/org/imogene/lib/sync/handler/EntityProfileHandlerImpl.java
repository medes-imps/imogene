package org.imogene.lib.sync.handler;

import java.util.Date;

import org.imogene.lib.common.criteria.ImogConjunction;
import org.imogene.lib.common.criteria.ImogJunction;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.profile.EntityProfile;
import org.imogene.lib.common.profile.EntityProfileDao;
import org.imogene.lib.sync.SyncConstants;

/**
 * Implements a data handler for the EntityProfile
 * 
 * @author Medes-IMPS
 */
public class EntityProfileHandlerImpl extends ImogBeanHandlerImpl<EntityProfile> implements EntityProfileHandler {

	private EntityProfileDao dao;

	@Override
	public EntityProfile createNewEntity(String id) {
		// TODO handle with not null constraint values
		EntityProfile entity = new EntityProfile();
		entity.setId(id);
		entity.setModified(new Date());
		entity.setCreatedBy(SyncConstants.SYNC_ID_SYS);
		entity.setModifiedBy(SyncConstants.SYNC_ID_SYS);
		entity.setModifiedFrom(SyncConstants.SYNC_ID_SYS);
		return entity;
	}

	@Override
	protected void saveOrUpdate(EntityProfile entity, boolean neu) {
		getDao().saveOrUpdate(entity, neu);
	}

	@Override
	protected ImogJunction createFilterJuntion(ImogActor user) {
		ImogConjunction filterConjunction = new ImogConjunction();

		return filterConjunction;
	}

	@Override
	protected ImogJunction createClientFilterJuntion(String userId, String terminalId) {
		return null;
	}

	@Override
	protected EntityProfileDao getDao() {
		return dao;
	}

	public void setDao(EntityProfileDao dao) {
		this.dao = dao;
	}

}
