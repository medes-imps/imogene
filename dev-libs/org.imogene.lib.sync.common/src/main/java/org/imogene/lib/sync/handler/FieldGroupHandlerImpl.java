package org.imogene.lib.sync.handler;

import java.util.Date;

import org.imogene.lib.common.criteria.ImogConjunction;
import org.imogene.lib.common.criteria.ImogJunction;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.model.FieldGroup;
import org.imogene.lib.common.model.FieldGroupDao;
import org.imogene.lib.sync.SyncConstants;

/**
 * Implements a data handler for the FieldGroup
 * 
 * @author Medes-IMPS
 */
public class FieldGroupHandlerImpl extends ImogBeanHandlerImpl<FieldGroup> implements FieldGroupHandler {

	private FieldGroupDao dao;

	@Override
	public FieldGroup createNewEntity(String id) {
		// TODO handle with not null constraint values
		FieldGroup entity = new FieldGroup();
		entity.setId(id);
		entity.setModified(new Date());
		entity.setCreatedBy(SyncConstants.SYNC_ID_SYS);
		entity.setModifiedBy(SyncConstants.SYNC_ID_SYS);
		entity.setModifiedFrom(SyncConstants.SYNC_ID_SYS);
		return entity;
	}

	@Override
	protected void saveOrUpdate(FieldGroup entity, boolean neu) {
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
	protected FieldGroupDao getDao() {
		return dao;
	}

	public void setDao(FieldGroupDao dao) {
		this.dao = dao;
	}

}
