package org.imogene.lib.sync.history;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * sync history DAO implementation using Hibernate.
 * 
 * @author Medes-IMPS
 */
public class SyncHistoryDaoImpl implements SyncHistoryDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void saveOrUpdate(SyncHistory history) {
		em.<SyncHistory> merge(history);
	}

	@Override
	public List<SyncHistory> load() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<SyncHistory> query = builder.createQuery(SyncHistory.class);
		Root<SyncHistory> root = query.from(SyncHistory.class);
		query.select(root);
		return em.createQuery(query).getResultList();
	}

	@Override
	public List<SyncHistory> load(String terminalId) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<SyncHistory> query = builder.createQuery(SyncHistory.class);
		Root<SyncHistory> root = query.from(SyncHistory.class);
		query.select(root);
		query.where(builder.equal(root.<String> get("terminalId"), terminalId));
		return em.createQuery(query).getResultList();
	}

	@Override
	public SyncHistory loadLastOk() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<SyncHistory> query = builder.createQuery(SyncHistory.class);
		Root<SyncHistory> root = query.from(SyncHistory.class);
		query.select(root);
		query.where(builder.equal(root.<Integer> get("status"), SyncHistory.STATUS_OK));
		query.orderBy(builder.desc(root.<Date> get("time")));
		try {
			return em.createQuery(query).setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public SyncHistory loadLastOk(String terminalId) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<SyncHistory> query = builder.createQuery(SyncHistory.class);
		Root<SyncHistory> root = query.from(SyncHistory.class);
		query.select(root);
		query.where(builder.equal(root.<String> get("terminalId"), terminalId),
				builder.equal(root.<Integer> get("status"), SyncHistory.STATUS_OK));
		query.orderBy(builder.desc(root.<Date> get("time")));
		try {
			return em.createQuery(query).setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public SyncHistory loadLastError() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<SyncHistory> query = builder.createQuery(SyncHistory.class);
		Root<SyncHistory> root = query.from(SyncHistory.class);
		query.select(root);
		query.where(builder.equal(root.<Integer> get("status"), SyncHistory.STATUS_ERROR));
		query.orderBy(builder.desc(root.<Date> get("time")));
		try {
			return em.createQuery(query).setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void delete() {
		em.createQuery("DELETE FROM SyncHostory").executeUpdate();
	}

	@Override
	public void deleteOld() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<SyncHistory> query = builder.createQuery(SyncHistory.class);
		Root<SyncHistory> root = query.from(SyncHistory.class);
		query.select(root);
		query.orderBy(builder.desc(root.<Date> get("time")));
		List<SyncHistory> list = em.createQuery(query).getResultList();
		if (list != null) {
			int i = 1;
			for (SyncHistory history : list) {
				if (history.getStatus() == SyncHistory.STATUS_OK) {
					if (i > 3) {
						em.remove(history);
					} else {
						i++;
					}
				}

			}
		}
	}

	@Override
	public void deleteOld(String terminalId) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<SyncHistory> query = builder.createQuery(SyncHistory.class);
		Root<SyncHistory> root = query.from(SyncHistory.class);
		query.select(root);
		query.where(builder.equal(root.<String> get("terminalId"), terminalId));
		query.orderBy(builder.desc(root.<Date> get("time")));
		List<SyncHistory> list = em.createQuery(query).getResultList();
		if (list != null) {
			int i = 1;
			for (SyncHistory history : list) {
				if (history.getStatus() == SyncHistory.STATUS_OK) {
					if (i > 3) {
						em.remove(history);
					} else {
						i++;
					}
				}

			}
		}

	}

}