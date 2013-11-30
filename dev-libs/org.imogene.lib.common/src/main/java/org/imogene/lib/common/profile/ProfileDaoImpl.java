package org.imogene.lib.common.profile;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.imogene.lib.common.dao.ImogBeanDaoImpl;

/**
 * Manage persistence for Profile
 */
public class ProfileDaoImpl extends ImogBeanDaoImpl<Profile> implements ProfileDao {

	protected ProfileDaoImpl() {
		super(Profile.class);
	}

	@Override
	public void delete() {
		// TODO
	}

	/* relation dependencies */

	/**
	 * List associated EntityProfile, on the field entityProfiles
	 * 
	 * @param parent the parent entity
	 * @return the list of the associated entities
	 */
	@Override
	public List<EntityProfile> loadEntityProfiles(Profile parent) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<EntityProfile> query = builder.createQuery(EntityProfile.class);
		Root<EntityProfile> root = query.from(EntityProfile.class);
		query.select(root);
		query.where(builder.equal(root.<Profile> get("profile"), parent));
		return em.createQuery(query).getResultList();
	}

	/**
	 * List associated FieldGroupProfile, on the field fieldGroupProfiles
	 * 
	 * @param parent the parent entity
	 * @return the list of the associated entities
	 */
	@Override
	public List<FieldGroupProfile> loadFieldGroupProfiles(Profile parent) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<FieldGroupProfile> query = builder.createQuery(FieldGroupProfile.class);
		Root<FieldGroupProfile> root = query.from(FieldGroupProfile.class);
		query.select(root);
		query.where(builder.equal(root.<Profile> get("profile"), parent));
		return em.createQuery(query).getResultList();
	}

}
