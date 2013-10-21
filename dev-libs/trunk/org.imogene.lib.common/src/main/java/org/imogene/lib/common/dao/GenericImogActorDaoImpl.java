package org.imogene.lib.common.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.imogene.lib.common.entity.ImogActorImpl;

public class GenericImogActorDaoImpl {

	@PersistenceContext
	protected EntityManager em;

	protected GenericImogActorDaoImpl() {
	}

	/**
	 * 
	 * @param login
	 * @return
	 */
	public ImogActorImpl loadFromLogin(String login) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<ImogActorImpl> query = builder.createQuery(ImogActorImpl.class);
		Root<ImogActorImpl> root = query.from(ImogActorImpl.class);
		query.select(root);
		query.where(builder.equal(root.<String> get("login"), login));
		List<ImogActorImpl> list = em.createQuery(query).getResultList();
		if (list != null && list.size() == 1)
			return list.get(0);
		else
			return null;
	}

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

}