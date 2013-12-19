package org.imogene.lib.sync.handler;

import java.util.Date;

import org.imogene.lib.common.criteria.ImogConjunction;
import org.imogene.lib.common.criteria.ImogJunction;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.model.CardEntity;
import org.imogene.lib.common.model.CardEntityDao;
import org.imogene.lib.sync.SyncConstants;

/**
 * Implements a data handler for the CardEntity
 * 
 * @author Medes-IMPS
 */
public class CardEntityHandlerImpl extends ImogBeanHandlerImpl<CardEntity> implements CardEntityHandler {

	private CardEntityDao dao;

	@Override
	public CardEntity createNewEntity(String id) {
		// TODO handle with not null constraint values
		CardEntity entity = new CardEntity();
		entity.setId(id);
		entity.setModified(new Date());
		entity.setCreatedBy(SyncConstants.SYNC_ID_SYS);
		entity.setModifiedBy(SyncConstants.SYNC_ID_SYS);
		entity.setModifiedFrom(SyncConstants.SYNC_ID_SYS);
		return entity;
	}

	@Override
	protected void saveOrUpdate(CardEntity entity, boolean neu) {
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
	protected CardEntityDao getDao() {
		return dao;
	}

	public void setDao(CardEntityDao dao) {
		this.dao = dao;
	}

}
