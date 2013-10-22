package org.imogene.lib.common.role;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.imogene.lib.common.dao.ImogBeanDaoImpl;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.entity.ImogActorImpl;

/**
 * Manage persistence for ImogRole
 * @author MEDES-IMPS
 */
public class RoleActorDaoImpl extends ImogBeanDaoImpl<ImogRole> implements RoleActorDao  {

	
	protected RoleActorDaoImpl() {
		super(ImogRole.class);
	}

	@Override
	public ImogActor getActor(String actorId) {
		return em.find(ImogActorImpl.class, actorId);
	}

	@Override
	public ImogRole getRole(String id) {
		return em.find(ImogRole.class, id);
	}

	@Override
	public List<? extends ImogActor> listActors() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<ImogActorImpl> query = builder.createQuery(ImogActorImpl.class);
		query.select(query.from(ImogActorImpl.class));
		return em.createQuery(query).getResultList();
	}
	
	@Override
	public List<? extends ImogActor> listActorsForRole(ImogRole role) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<ImogActorImpl> query = builder.createQuery(ImogActorImpl.class);
		Root<ImogActorImpl> root = query.from(ImogActorImpl.class);
		query.select(root);
		query.where(builder.equal(root.join("roles", JoinType.LEFT), role));
		return em.createQuery(query).getResultList();
	}

	@Override
	public List<ImogRole> listRoles() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<ImogRole> query = builder.createQuery(ImogRole.class);
		query.select(query.from(ImogRole.class));
		return em.createQuery(query).getResultList();
	}
	
	@Override
	public void delete() {
		//TODO
	}

}
