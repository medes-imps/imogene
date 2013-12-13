package org.imogene.lib.common.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.entity.ImogActorImpl;

public class GenericDaoImpl implements GenericDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public <T> T load(Class<T> clazz, String id) {
		return em.find(clazz, id);
	}

	@Override
	public <T> List<T> load(Class<T> clazz) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(clazz);
		query.select(query.from(clazz));
		return em.createQuery(query).getResultList();
	}

	@Override
	public Object load(String className, String id) {
		try {
			return em.find(Class.forName(className), id);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void detach(Object o) {
		em.detach(o);
	}

	@Override
	public <T> void saveOrUpdate(T object) {
		em.<T> merge(object);
	};

	@Override
	public <T> long count(Class<T> clazz) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<T> root = query.from(clazz);
		query.select(builder.count(root));
		return em.createQuery(query).getSingleResult();
	}

	@Override
	public <T> boolean contains(T object) {
		return em.contains(object);
	}

	@Override
	public <T> T merge(T o) {
		return em.<T> merge(o);
	}

	@Override
	public ImogActor loadFromLogin(String login) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<ImogActorImpl> query = builder.createQuery(ImogActorImpl.class);
		Root<ImogActorImpl> root = query.from(ImogActorImpl.class);
		query.select(root);
		query.where(builder.equal(root.<String> get("login"), login));
		return em.createQuery(query).getSingleResult();
	}

}
