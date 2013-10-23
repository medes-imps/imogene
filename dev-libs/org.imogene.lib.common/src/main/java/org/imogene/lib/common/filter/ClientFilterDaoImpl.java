package org.imogene.lib.common.filter;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.imogene.lib.common.dao.ImogBeanDaoImpl;

/**
 * Implements a Hibernate DAO for the ClientFilter
 * @author MEDES-IMPS
 */
public class ClientFilterDaoImpl extends ImogBeanDaoImpl<ClientFilter> implements ClientFilterDao {

	protected ClientFilterDaoImpl() {
		super(ClientFilter.class);
	}

	@Override
	public List<ClientFilter> loadFilters(String userId, String terminalId, String classname) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<ClientFilter> query = builder.createQuery(ClientFilter.class);
		Root<ClientFilter> root = query.from(ClientFilter.class);
		query.select(root);
		query.where(builder.equal(root.<String> get("userId"), userId),
				builder.equal(root.<String> get("terminalId"), terminalId),
				builder.equal(root.<String> get("cardEntity"), classname));
		return em.createQuery(query).getResultList();
	}

	@Override
	public List<ClientFilter> loadFilters(String userId, String terminalId) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<ClientFilter> query = builder.createQuery(ClientFilter.class);
		Root<ClientFilter> root = query.from(ClientFilter.class);
		query.select(root);
		query.where(builder.equal(root.<String> get("userId"), userId),
				builder.equal(root.<String> get("terminalId"), terminalId));
		return em.createQuery(query).getResultList();
	}

	@Override
	public void delete() {
		em.createQuery("DELETE FROM ClienFilter").executeUpdate();
	}

}
