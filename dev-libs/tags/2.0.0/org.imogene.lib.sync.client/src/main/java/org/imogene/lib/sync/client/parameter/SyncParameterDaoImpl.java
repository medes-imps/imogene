package org.imogene.lib.sync.client.parameter;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class SyncParameterDaoImpl implements SyncParameterDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public SyncParameter load() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<SyncParameter> query = builder.createQuery(SyncParameter.class);
		query.select(query.from(SyncParameter.class));
		try {
			return em.createQuery(query).setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void store(SyncParameter parameters) {
		em.<SyncParameter> merge(parameters);
	}

}
