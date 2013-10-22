package org.imogene.lib.common.sync.entity;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.imogene.lib.common.dao.ImogBeanDaoImpl;

/**
 * Manage persistence for SynchronizableEntity
 * @author MEDES-IMPS
 */
public class SynchronizableEntityDaoImpl extends ImogBeanDaoImpl<SynchronizableEntity> implements SynchronizableEntityDao {



	protected SynchronizableEntityDaoImpl() {
		super(SynchronizableEntity.class);
	}


	@Override
	public List<SynchronizableEntity> load() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<SynchronizableEntity> query = builder.createQuery(SynchronizableEntity.class);
		query.select(query.from(SynchronizableEntity.class));
		return em.createQuery(query).getResultList();
	}

	@Override
	public void store(SynchronizableEntity entity) {
		em.<SynchronizableEntity> merge(entity);
	}

	
	@Override
	public void delete() {
		//TODO
	}

}
