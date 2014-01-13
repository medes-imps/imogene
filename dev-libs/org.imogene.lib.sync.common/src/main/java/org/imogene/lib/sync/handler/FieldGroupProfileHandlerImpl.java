package org.imogene.lib.sync.handler;

import java.util.Date;

import org.imogene.lib.common.criteria.ImogConjunction;
import org.imogene.lib.common.criteria.ImogJunction;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.profile.FieldGroupProfile;
import org.imogene.lib.common.profile.FieldGroupProfileDao;
import org.imogene.lib.sync.SyncConstants;

/**
 * Implements a data handler for the FieldGroupProfile
 * 
 * @author Medes-IMPS
 */
public class FieldGroupProfileHandlerImpl extends ImogBeanHandlerImpl<FieldGroupProfile> implements
		FieldGroupProfileHandler {

	private FieldGroupProfileDao dao;

	@Override
	public FieldGroupProfile createNewEntity(String id) {
		// TODO handle with not null constraint values
		FieldGroupProfile entity = new FieldGroupProfile();
		entity.setId(id);
		entity.setModified(new Date());
		entity.setCreatedBy(SyncConstants.SYNC_ID_SYS);
		entity.setModifiedBy(SyncConstants.SYNC_ID_SYS);
		entity.setModifiedFrom(SyncConstants.SYNC_ID_SYS);
		return entity;
	}

	@Override
	protected void saveOrUpdate(FieldGroupProfile entity, boolean neu) {
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
	protected FieldGroupProfileDao getDao() {
		return dao;
	}

	public void setDao(FieldGroupProfileDao dao) {
		this.dao = dao;
	}

}
