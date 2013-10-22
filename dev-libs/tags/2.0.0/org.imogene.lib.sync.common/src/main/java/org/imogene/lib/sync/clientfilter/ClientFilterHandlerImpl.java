package org.imogene.lib.sync.clientfilter;

import java.util.Date;

import org.imogene.lib.common.criteria.ImogJunction;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.filter.ClientFilter;
import org.imogene.lib.common.filter.ClientFilterDao;
import org.imogene.lib.sync.SyncConstants;
import org.imogene.lib.sync.handler.ImogBeanHandlerImpl;

/**
 * Implements a data handler for the ClientFilter
 * 
 * @author Medes-IMPS
 */
public class ClientFilterHandlerImpl extends ImogBeanHandlerImpl<ClientFilter> implements ClientFilterHandler {

	private ClientFilterDao dao;
	
	/**
	 * Setter for bean injection
	 * 
	 * @param dao
	 */
	public void setDao(ClientFilterDao dao) {
		this.dao = dao;
	}

	@Override
	public ClientFilter createNewEntity(String id) {
		ClientFilter entity = new ClientFilter();
		entity.setId(id);
		entity.setModified(new Date());
		entity.setCreatedBy(SyncConstants.SYNC_ID_SYS);
		entity.setModifiedBy(SyncConstants.SYNC_ID_SYS);
		entity.setModifiedFrom(SyncConstants.SYNC_ID_SYS);
		return entity;
	}

	@Override
	protected ClientFilterDao getDao() {
		return dao;
	}

	@Override
	protected ImogJunction createFilterJuntion(ImogActor actor) {
		return null;
	}

	@Override
	protected ImogJunction createClientFilterJuntion(String userId, String terminalId) {
		return null;
	}
	
	@Override
	public void saveOrUpdate(ClientFilter entity, ImogActor user, boolean neu) {
		saveOrUpdate(entity, neu);
	}

}
