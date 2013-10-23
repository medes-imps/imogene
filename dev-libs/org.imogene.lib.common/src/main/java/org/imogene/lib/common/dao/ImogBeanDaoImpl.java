package org.imogene.lib.common.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.imogene.lib.common.criteria.DaoUtil;
import org.imogene.lib.common.criteria.ImogCriterion;
import org.imogene.lib.common.entity.ImogBean;

public abstract class ImogBeanDaoImpl<T extends ImogBean> implements ImogBeanDao<T> {

	@PersistenceContext
	protected EntityManager em;

	protected final Class<T> clazz;

	protected ImogBeanDaoImpl(Class<T> clazz) {
		this.clazz = clazz;
	}

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public T load(String id) {
		return em.find(clazz, id);
	}
	
	@Override
	public T getById(String id) {
		return em.find(clazz, id);
	}

	@Override
	public List<T> load(List<String> ids) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(clazz);
		Root<T> root = query.from(clazz);
		query.select(root);
		query.where(root.<String> get("id").in(ids));
		return em.createQuery(query).getResultList();
	}

	@Override
	public T load(String id, ImogCriterion criterion) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(clazz);
		Root<T> root = query.from(clazz);
		query.select(root);
		query.where(builder.equal(root.<String> get("id"), id), DaoUtil.<T> toPredicate(criterion, builder, root));
		try {
			return em.createQuery(query).setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<T> load(ImogCriterion criterion) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(clazz);
		Root<T> root = query.from(clazz);
		query.select(root);
		query.where(DaoUtil.<T> toPredicate(criterion, builder, root));
		return em.createQuery(query).getResultList();
	}

	@Override
	public List<T> load(String property, boolean asc, ImogCriterion criterion) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(clazz);
		Root<T> root = query.from(clazz);
		query.select(root);
		query.where(DaoUtil.<T> toPredicate(criterion, builder, root));
		if (property == null) {
			property = "modified";
		}
		Order o = asc ? builder.asc(DaoUtil.getCascadeRoot(root, property)) : builder.desc(DaoUtil.getCascadeRoot(root,
				property));
		query.orderBy(o, builder.desc(root.<String> get("id")));
		return em.createQuery(query).getResultList();
	}

	@Override
	public List<T> load(int first, int max, String property, boolean asc) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(clazz);
		Root<T> root = query.from(clazz);
		query.select(root);
		if (property == null) {
			property = "modified";
		}
		Order o = asc ? builder.asc(DaoUtil.getCascadeRoot(root, property)) : builder.desc(DaoUtil.getCascadeRoot(root,
				property));
		query.orderBy(o, builder.desc(root.<String> get("id")));
		return em.createQuery(query).setFirstResult(first).setMaxResults(max).getResultList();
	}

	@Override
	public List<T> load(int first, int max, String property, boolean asc, ImogCriterion criterion) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(clazz);
		Root<T> root = query.from(clazz);
		query.select(root);
		query.where(DaoUtil.<T> toPredicate(criterion, builder, root));
		if (property == null) {
			property = "modified";
		}
		Order o = asc ? builder.asc(DaoUtil.getCascadeRoot(root, property)) : builder.desc(DaoUtil.getCascadeRoot(root,
				property));
		query.orderBy(o, builder.desc(root.<String> get("id")));
		return em.createQuery(query).setFirstResult(first).setMaxResults(max).getResultList();
	}

	@Override
	public long count() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<T> root = query.from(clazz);
		query.select(builder.count(root));
		return em.createQuery(query).getSingleResult();
	}

	@Override
	public long count(ImogCriterion criterion) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<T> root = query.from(clazz);
		query.select(builder.count(root));
		query.where(DaoUtil.<T> toPredicate(criterion, builder, root));
		return em.createQuery(query).getSingleResult();
	}

	@Override
	public void delete(T entity) {
		em.remove(entity);
	}

	@Override
	public List<T> loadNonAffected(String property, ImogCriterion criterion) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(clazz);
		Root<T> root = query.from(clazz);
		query.select(root);
		query.where(root.get(property).isNull(), DaoUtil.<T> toPredicate(criterion, builder, root));
		return em.createQuery(query).getResultList();
	}

	@Override
	public List<T> loadNonAffected(int first, int max, String sortProperty, boolean asc, String property,
			ImogCriterion criterion) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(clazz);
		Root<T> root = query.from(clazz);
		query.select(root);
		query.where(root.get(property).isNull(), DaoUtil.<T> toPredicate(criterion, builder, root));
		if (sortProperty == null) {
			sortProperty = "modified";
		}
		if (asc) {
			query.orderBy(builder.asc(root.get(sortProperty)));
		} else {
			query.orderBy(builder.desc(root.get(sortProperty)));
		}
		return em.createQuery(query).setFirstResult(first).setMaxResults(max).getResultList();
	}

	@Override
	public List<T> loadNonAffectedReverse(int first, int max, String sortProperty, boolean asc, String property,
			ImogCriterion criterion) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(clazz);
		Root<T> root = query.from(clazz);
		query.select(root);
		query.where(root.<T, Object> join(property, JoinType.LEFT).<String> get("id").isNull(),
				DaoUtil.<T> toPredicate(criterion, builder, root));
		if (sortProperty == null) {
			sortProperty = "modified";
		}
		if (asc) {
			query.orderBy(builder.asc(root.get(sortProperty)));
		} else {
			query.orderBy(builder.desc(root.get(sortProperty)));
		}
		return em.createQuery(query).setFirstResult(first).setMaxResults(max).getResultList();
	}

	@Override
	public List<T> loadAffectedCardNProperty(String property, String id) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(clazz);
		Root<T> root = query.from(clazz);
		query.select(root);
		query.where(builder.equal(root.<T, Object> join(property, JoinType.INNER).<String> get("id"), id));
		return em.createQuery(query).getResultList();
	}

	@Override
	public long countNonAffected(String property, ImogCriterion criterion) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<T> root = query.from(clazz);
		query.select(builder.count(root));
		query.where(root.get(property).isNull(), DaoUtil.<T> toPredicate(criterion, builder, root));
		return em.createQuery(query).getSingleResult();
	}

	@Override
	public long countNonAffectedReverse(String property, ImogCriterion criterion) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<T> root = query.from(clazz);
		query.select(builder.count(root));
		query.where(root.join(property, JoinType.LEFT).<String> get("id").isNull(),
				DaoUtil.<T> toPredicate(criterion, builder, root));
		return em.createQuery(query).getSingleResult();
	}

	@Override
	public List<T> load() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(clazz);
		query.select(query.from(clazz));
		return em.createQuery(query).getResultList();
	}

	@Override
	public List<T> loadModified(Date date) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(clazz);
		Root<T> root = query.from(clazz);
		query.select(root);
		query.where(builder.<Date> greaterThanOrEqualTo(root.<Date> get("modified"), date));
		return em.createQuery(query).getResultList();
	}

	@Override
	public List<T> loadModified(Date date, ImogCriterion criterion) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(clazz);
		Root<T> root = query.from(clazz);
		query.select(root);
		query.where(builder.<Date> greaterThanOrEqualTo(root.<Date> get("modified"), date),
				DaoUtil.toPredicate(criterion, builder, root));
		return em.createQuery(query).getResultList();
	}

	@Override
	public T loadModified(Date date, String id) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(clazz);
		Root<T> root = query.from(clazz);
		query.select(root);
		query.where(builder.<Date> greaterThanOrEqualTo(root.<Date> get("modified"), date),
				builder.equal(root.<String> get("id"), id));
		try {
			return em.createQuery(query).setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public T loadModified(Date date, ImogCriterion criterion, String id) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(clazz);
		Root<T> root = query.from(clazz);
		query.select(root);
		query.where(builder.<Date> greaterThanOrEqualTo(root.<Date> get("modified"), date),
				builder.equal(root.<String> get("id"), id), DaoUtil.toPredicate(criterion, builder, root));
		try {
			return em.createQuery(query).setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<T> loadUploaded(Date date) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(clazz);
		Root<T> root = query.from(clazz);
		query.select(root);
		query.where(builder.<Date> greaterThanOrEqualTo(root.<Date> get("uploadDate"), date));
		return em.createQuery(query).getResultList();
	}

	@Override
	public List<T> loadUploaded(Date date, ImogCriterion criterion) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(clazz);
		Root<T> root = query.from(clazz);
		query.select(root);
		query.where(builder.<Date> greaterThanOrEqualTo(root.<Date> get("uploadDate"), date),
				DaoUtil.toPredicate(criterion, builder, root));
		return em.createQuery(query).getResultList();
	}

	@Override
	public T loadUploaded(Date date, String id) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(clazz);
		Root<T> root = query.from(clazz);
		query.select(root);
		query.where(builder.<Date> greaterThanOrEqualTo(root.<Date> get("uploadDate"), date),
				builder.equal(root.<String> get("id"), id));
		try {
			return em.createQuery(query).setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public T loadUploaded(Date date, ImogCriterion criterion, String id) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(clazz);
		Root<T> root = query.from(clazz);
		query.select(root);
		query.where(builder.<Date> greaterThanOrEqualTo(root.<Date> get("uploadDate"), date),
				builder.equal(root.<String> get("id"), id), DaoUtil.toPredicate(criterion, builder, root));
		try {
			return em.createQuery(query).setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void saveOrUpdate(T entity, boolean neu) {
		em.merge(entity);
	}

	@Override
	public T merge(T entity, boolean neu) {
		return em.merge(entity);
	}

	@Override
	public void detach(T entity) {
		em.detach(entity);
	};

	@Override
	public void refresh(T entity) {
		em.refresh(entity);
	};

	@Override
	public void clear() {
		em.clear();
	}

	@Override
	public void flush() {
		em.flush();
	}

}
