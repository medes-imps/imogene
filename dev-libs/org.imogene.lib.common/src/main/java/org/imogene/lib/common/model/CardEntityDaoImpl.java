package org.imogene.lib.common.model;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.imogene.lib.common.dao.ImogBeanDaoImpl;

/**
 * Manage persistence for SynchronizableEntity
 * @author MEDES-IMPS
 */
public class CardEntityDaoImpl extends ImogBeanDaoImpl<CardEntity> implements CardEntityDao {



	protected CardEntityDaoImpl() {
		super(CardEntity.class);
	}


	@Override
	public List<CardEntity> load() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<CardEntity> query = builder.createQuery(CardEntity.class);
		query.select(query.from(CardEntity.class));
		return em.createQuery(query).getResultList();
	}

	@Override
	public void store(CardEntity entity) {
		em.<CardEntity> merge(entity);
	}

	
	@Override
	public void delete() {
		//TODO
	}

}
