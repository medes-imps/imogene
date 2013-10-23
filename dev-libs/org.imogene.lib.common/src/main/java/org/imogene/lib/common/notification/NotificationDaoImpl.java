package org.imogene.lib.common.notification;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.imogene.lib.common.dao.ImogBeanDaoImpl;


/**
 * Manage persistence for Notification
 * @author MEDES-IMPS
 */
public class NotificationDaoImpl extends ImogBeanDaoImpl<Notification> implements NotificationDao {

	protected NotificationDaoImpl() {
		super(Notification.class);
	}

	@Override
	public List<Notification> load(String formType, String operation) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Notification> query = builder.createQuery(Notification.class);
		Root<Notification> root = query.from(Notification.class);
		query.select(root);
		query.where(builder.like(root.<String> get("operation"), operation),
				builder.like(root.<String> get("formType"), formType));
		return em.createQuery(query).getResultList();
	}

	@Override
	public long count() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Notification> root = query.from(Notification.class);
		query.select(builder.count(root));
		return em.createQuery(query).getSingleResult();
	}

	@Override
	public void saveOrUpdate(Notification entity) {
		em.<Notification> merge(entity);
	}
	
	@Override
	public void delete() {
		//TODO
	}

}
