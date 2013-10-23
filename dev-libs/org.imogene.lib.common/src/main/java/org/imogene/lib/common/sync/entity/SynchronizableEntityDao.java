package org.imogene.lib.common.sync.entity;

import org.imogene.lib.common.dao.ImogBeanDao;

/**
 * Manage persistence for SynchronizableEntity
 * @author MEDES-IMPS
 */
public interface SynchronizableEntityDao extends ImogBeanDao<SynchronizableEntity>{


	public void store(SynchronizableEntity entity);


}
