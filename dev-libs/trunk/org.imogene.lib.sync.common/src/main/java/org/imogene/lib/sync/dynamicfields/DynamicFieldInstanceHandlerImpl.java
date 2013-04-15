package org.imogene.lib.sync.dynamicfields;

import java.util.Date;

import org.imogene.lib.common.criteria.ImogJunction;
import org.imogene.lib.common.dynamicfields.DynamicFieldInstance;
import org.imogene.lib.common.dynamicfields.DynamicFieldInstanceDao;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.sync.SyncConstants;
import org.imogene.lib.sync.handler.ImogBeanHandlerImpl;

/**
 * Implements a data handler for the DynamicFieldInstance 
 * @author Medes-IMPS
 */
public class DynamicFieldInstanceHandlerImpl extends ImogBeanHandlerImpl<DynamicFieldInstance> implements DynamicFieldInstanceHandler {

	private DynamicFieldInstanceDao dao;
	
	/**
	 * Setter for bean injection
	 * @param binaryFileDao
	 */
	public void setDao(DynamicFieldInstanceDao dao) {
		this.dao = dao;
	}
	
	@Override
	protected DynamicFieldInstanceDao getDao() {
		return dao;
	}

	@Override
	public DynamicFieldInstance createNewEntity(String id) {
		//TODO handle  with not null constraint values
		DynamicFieldInstance entity = new DynamicFieldInstance();
		entity.setId(id);
		entity.setModified(new Date());
		entity.setCreatedBy(SyncConstants.SYNC_ID_SYS);
		entity.setModifiedBy(SyncConstants.SYNC_ID_SYS);
		entity.setModifiedFrom(SyncConstants.SYNC_ID_SYS);
		return entity;
	}

	@Override
	protected void saveOrUpdate(DynamicFieldInstance entity, boolean neu) {
		getDao().saveOrUpdate(entity, neu);
	}

	@Override
	protected ImogJunction createFilterJuntion(ImogActor user) {
		return null;
	}

	@Override
	protected ImogJunction createClientFilterJuntion(String userId, String terminalId) {
		return null;
	}

}
