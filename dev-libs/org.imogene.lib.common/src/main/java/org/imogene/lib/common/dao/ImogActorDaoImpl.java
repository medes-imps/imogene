package org.imogene.lib.common.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.profile.Profile;

public abstract class ImogActorDaoImpl<T extends ImogActor> extends ImogBeanDaoImpl<T> implements ImogActorDao<T> {

	protected ImogActorDaoImpl(Class<T> clazz) {
		super(clazz);
	}

	@Override
	public List<T> loadFromLogin(String login) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(clazz);
		Root<T> root = query.from(clazz);
		query.select(root);
		query.where(builder.equal(root.<String> get("login"), login));
		return em.createQuery(query).getResultList();
	}

	@Override
	public List<Profile> loadProfiles(ImogActor parent) {
		if (parent == null) {
			return new ArrayList<Profile>();
		}
		return parent.getProfiles();
	}

}
