package org.imogene.lib.common.model;

import org.imogene.lib.common.dao.ImogBeanDao;

/**
 * Manage persistence for SynchronizableEntity
 * @author MEDES-IMPS
 */
public interface CardEntityDao extends ImogBeanDao<CardEntity>{


	public void store(CardEntity entity);


}
