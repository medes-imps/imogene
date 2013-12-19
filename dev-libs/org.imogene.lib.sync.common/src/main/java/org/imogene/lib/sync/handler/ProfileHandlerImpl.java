package org.imogene.lib.sync.handler;

import java.util.Date;

import org.imogene.lib.common.criteria.ImogConjunction;
import org.imogene.lib.common.criteria.ImogJunction;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.profile.Profile;
import org.imogene.lib.common.profile.ProfileDao;
import org.imogene.lib.sync.SyncConstants;

/**
 * Implements a data handler for the Profile
 * 
 * @author Medes-IMPS
 */
public class ProfileHandlerImpl extends ImogBeanHandlerImpl<Profile> implements ProfileHandler {

	private ProfileDao dao;

	@Override
	public Profile createNewEntity(String id) {
		// TODO handle with not null constraint values
		Profile entity = new Profile();
		entity.setId(id);
		entity.setModified(new Date());
		entity.setCreatedBy(SyncConstants.SYNC_ID_SYS);
		entity.setModifiedBy(SyncConstants.SYNC_ID_SYS);
		entity.setModifiedFrom(SyncConstants.SYNC_ID_SYS);
		return entity;
	}

	@Override
	protected void saveOrUpdate(Profile entity, boolean neu) {
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
	protected ProfileDao getDao() {
		return dao;
	}

	public void setDao(ProfileDao dao) {
		this.dao = dao;
	}

}
