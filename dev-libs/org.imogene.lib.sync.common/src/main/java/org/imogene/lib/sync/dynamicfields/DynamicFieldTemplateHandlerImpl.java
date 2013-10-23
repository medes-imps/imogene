package org.imogene.lib.sync.dynamicfields;

import java.util.Date;

import org.imogene.lib.common.criteria.ImogJunction;
import org.imogene.lib.common.dynamicfields.DynamicFieldTemplate;
import org.imogene.lib.common.dynamicfields.DynamicFieldTemplateDao;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.sync.SyncConstants;
import org.imogene.lib.sync.handler.ImogBeanHandlerImpl;

/**
 * Implements a data handler for the DynamicFieldTemplate 
 * @author Medes-IMPS
 */
public class DynamicFieldTemplateHandlerImpl extends ImogBeanHandlerImpl<DynamicFieldTemplate> implements DynamicFieldTemplateHandler {

	private DynamicFieldTemplateDao dao;
	
	/**
	 * Setter for bean injection
	 * @param binaryFileDao
	 */
	public void setDao(DynamicFieldTemplateDao dao) {
		this.dao = dao;
	}

	@Override
	protected DynamicFieldTemplateDao getDao() {
		return dao;
	}

	@Override
	public DynamicFieldTemplate createNewEntity(String id) {
		//TODO handle  with not null constraint values
		DynamicFieldTemplate entity = new DynamicFieldTemplate();
		entity.setId(id);
		entity.setModified(new Date());
		entity.setCreatedBy(SyncConstants.SYNC_ID_SYS);
		entity.setModifiedBy(SyncConstants.SYNC_ID_SYS);
		entity.setModifiedFrom(SyncConstants.SYNC_ID_SYS);
		return entity;
	}

	@Override
	protected void saveOrUpdate(DynamicFieldTemplate entity, boolean neu) {
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
