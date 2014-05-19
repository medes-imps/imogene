package org.imogene.web.server.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.imogene.lib.common.constants.CriteriaConstants;
import org.imogene.lib.common.criteria.BasicCriteria;
import org.imogene.lib.common.criteria.ImogConjunction;
import org.imogene.lib.common.criteria.ImogJunction;
import org.imogene.lib.common.dao.ImogActorDao;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.entity.ImogBean;
import org.imogene.lib.common.profile.EntityProfile;
import org.imogene.lib.common.profile.EntityProfileDao;
import org.imogene.lib.common.profile.FieldGroupProfile;
import org.imogene.lib.common.profile.FieldGroupProfileDao;
import org.imogene.lib.common.profile.Profile;
import org.imogene.lib.common.profile.ProfileDao;
import org.imogene.lib.common.security.ImogBeanFilter;
import org.imogene.lib.common.util.SystemUtil;
import org.imogene.web.server.util.HttpSessionUtil;
import org.springframework.transaction.annotation.Transactional;

/**
 * A data handler for the Profile beans
 * 
 * @author Medes-IMPS
 */
public class ProfileHandler {

	private ProfileDao dao;
	/* MyActorDao for Foreign Key Deletion */
	private ImogActorDao actorProfilesDao;
	/* EntityProfileDao for Foreign Key Deletion */
	private EntityProfileDao entityProfileDao;
	/* FieldGroupProfileDao for Foreign Key Deletion */
	private FieldGroupProfileDao fieldGroupProfileDao;

	private ImogBeanFilter filter;
	private SystemUtil systemUtil;
	private HandlerHelper handlerHelper;
	private ProfileHelper profileHelper;

	/**
	 * Loads the entity with the specified id
	 * 
	 * @param entityId the entity id
	 * @return the entity or null
	 */
	@Transactional(readOnly = true)
	public Profile findById(String entityId) {
		return dao.load(entityId);
	}

	/**
	 * Loads the entity with the specified id
	 * 
	 * @param entityId the entity id
	 * @return the entity or null
	 */
	@Transactional(readOnly = true)
	public Profile getById(String entityId) {
		return dao.getById(entityId);
	}

	/**
	 * Create a new profile filled with default values
	 * 
	 * @return the default profile
	 */
	public Profile createProfile() {
		return profileHelper.createProfile();
	}

	/**
	 * Saves or updates the entity
	 * 
	 * @param entity the entity to be saved or updated
	 * @param isNew true if it is a new entity added for the first time.
	 */
	@Transactional
	public void save(Profile entity, boolean isNew) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();

		if (entity != null) {

			if (isNew) {
				entity.setCreated(new Date(systemUtil.getCurrentTimeMillis()));
				entity.setCreatedBy(actor.getLogin());
			}

			entity.setModified(new Date(systemUtil.getCurrentTimeMillis()));
			entity.setModifiedBy(actor.getLogin());
			entity.setModifiedFrom(systemUtil.getTerminal());

			// manage related entityProfiles
			List<EntityProfile> listEntityProfiles = entity.getEntityProfiles();
			if (listEntityProfiles != null && listEntityProfiles.size() > 0) {
				for (EntityProfile item : listEntityProfiles) {
					if (item != null) {

						item.setModified(new Date(systemUtil.getCurrentTimeMillis()));
						item.setModifiedBy(actor.getLogin());
						item.setModifiedFrom(systemUtil.getTerminal());
						if (item.getCreatedBy() == null) {
							item.setCreatedBy(actor.getLogin());
						}
					}
				}
			}
			// manage related fieldGroupProfiles
			List<FieldGroupProfile> listFieldGroupProfiles = entity.getFieldGroupProfiles();
			if (listFieldGroupProfiles != null && listFieldGroupProfiles.size() > 0) {
				for (FieldGroupProfile item : listFieldGroupProfiles) {
					if (item != null) {

						item.setModified(new Date(systemUtil.getCurrentTimeMillis()));
						item.setModifiedBy(actor.getLogin());
						item.setModifiedFrom(systemUtil.getTerminal());
						if (item.getCreatedBy() == null) {
							item.setCreatedBy(actor.getLogin());
						}
					}
				}
			}

			dao.saveOrUpdate(entity, isNew);
		}
	}

	/**
	 * Saves or updates the bean
	 * 
	 * @param entity the bean to be saved or updated
	 * @param isNew true if it is a new entity added for the first time.
	 */
	@Transactional
	public void save(ImogBean entity, boolean isNew) {
		handlerHelper.save(entity, isNew);
	}

	/**
	 * Lists the entities of type Profile
	 * 
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @return list of profile
	 */
	@Transactional(readOnly = true)
	public List<Profile> listProfile(String sortProperty, boolean sortOrder) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);

		List<Profile> beans = dao.load(sortProperty, sortOrder, junction);

		return beans;
	}

	/**
	 * Lists the entities of type Profile
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @return list of profile
	 */
	@Transactional(readOnly = true)
	public List<Profile> listProfile(int i, int j, String sortProperty, boolean sortOrder) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);

		List<Profile> beans = dao.load(i, j, sortProperty, sortOrder, junction);

		return beans;
	}

	/**
	 * Lists the entities of type Profile
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param criterions request criteria
	 * @return list of profile
	 */
	@Transactional(readOnly = true)
	public List<Profile> listProfile(int i, int j, String sortProperty, boolean sortOrder, ImogJunction criterions) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);
		if (criterions != null)
			junction.add(criterions);

		List<Profile> beans = dao.load(i, j, sortProperty, sortOrder, junction);

		return beans;
	}

	/**
	 * Lists the entities of type Profile
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param criterions request criteria
	 * @return list of profile
	 */
	@Transactional(readOnly = true)
	public List<Profile> listProfile(int i, int j, String sortProperty, boolean sortOrder,
			List<BasicCriteria> criterions) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);

		ImogJunction junctionForCrit = new ImogConjunction();
		if (criterions != null) {
			for (BasicCriteria crit : criterions)
				junctionForCrit.add(crit);
		}
		junction.add(junctionForCrit);

		List<Profile> beans = dao.load(i, j, sortProperty, sortOrder, junction);

		return beans;
	}

	/**
	 * Lists the non affected entities of type Profile
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param criterion request criteria
	 * @param property the property which is not affected
	 * @return list of profile
	 */
	@Transactional(readOnly = true)
	public List<Profile> listNonAffectedProfile(int i, int j, String sortProperty, boolean sortOrder,
			ImogJunction criterions, String property) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);
		if (criterions != null)
			junction.add(criterions);

		List<Profile> beans = dao.loadNonAffected(i, j, sortProperty, sortOrder, property, junction);

		return beans;
	}

	/**
	 * Lists the non affected entities of type Profile
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param property the property which is not affected
	 * @return list of profile
	 */
	@Transactional(readOnly = true)
	public List<Profile> listNonAffectedProfile(int i, int j, String sortProperty, boolean sortOrder, String property) {
		return listNonAffectedProfile(i, j, sortProperty, sortOrder, null, property);
	}

	/**
	 * Used when Profile is involved in a Relation 1 <-> 1 Association and is the ReverseRelationField of the Relation
	 * Return all instance of Profile non affected regarding specified property.
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param ImogJunction request criteria
	 * @param property the property which is not affected
	 * @return list of profile
	 */
	@Transactional(readOnly = true)
	public List<Profile> listNonAffectedProfileReverse(int i, int j, String sortProperty, boolean sortOrder,
			ImogJunction criterions, String property) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);
		if (criterions != null)
			junction.add(criterions);

		List<Profile> beans = dao.loadNonAffectedReverse(i, j, sortProperty, sortOrder, property, junction);

		return beans;
	}

	/**
	 * Used when Profile is involved in a Relation 1 <-> 1 Association and is the ReverseRelationField of the Relation
	 * Return all instance of Profile non affected regarding specified property.
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param property the property which is not affected
	 * @return list of profile
	 */
	@Transactional(readOnly = true)
	public List<Profile> listNonAffectedProfileReverse(int i, int j, String sortProperty, boolean sortOrder,
			String property) {
		return listNonAffectedProfileReverse(i, j, sortProperty, sortOrder, null, property);
	}

	/**
	 * Gets an empty list of Profile
	 * 
	 * @return an empty list of Profile
	 */
	@Transactional(readOnly = true)
	public List<Profile> getProfileEmptyList() {
		return new ArrayList<Profile>();
	}

	/**
	 * Counts the number of profile in the database
	 * 
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countProfile() {
		return countProfile(null);
	}

	/**
	 * Counts the number of profile in the database, that match the criteria
	 * 
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countProfile(ImogJunction criterions) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);
		if (criterions != null)
			junction.add(criterions);

		return dao.count(junction);
	}

	/**
	 * Counts the number of non affected profile entities in the database
	 * 
	 * @param property the property which is not affected
	 * @param criterion request criteria
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedProfile(String property, ImogJunction criterions) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);
		if (criterions != null)
			junction.add(criterions);

		return dao.countNonAffected(property, junction);
	}

	/**
	 * Counts the number of non affected profile entities in the database
	 * 
	 * @param property the property which is not affected
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedProfile(String property) {
		return countNonAffectedProfile(property, null);
	}

	/**
	 * Counts the number of non affected profile entities in the database
	 * 
	 * @param property the property which is not affected
	 * @param criterion request criteria
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedProfileReverse(String property, ImogJunction criterions) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);
		if (criterions != null)
			junction.add(criterions);
		return dao.countNonAffectedReverse(property, junction);
	}

	/**
	 * Counts the number of non affected profile entities in the database
	 * 
	 * @param property the property which is not affected
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedProfileReverse(String property) {
		return countNonAffectedProfileReverse(property, null);
	}

	/**
	 * Deletes a group of entities identified by their IDs
	 * 
	 * @param ids Entities to delete IDs
	 * @return The list of deleted entities IDs
	 */
	@Transactional
	public void delete(Set<Profile> entities) {
		if (entities != null) {
			for (Profile entity : entities)
				delete(entity);
		}
	}

	/**
	 * Removes the specified entity from the database
	 * 
	 * @param entity The entity to be deleted
	 */
	@Transactional
	public void delete(Profile entity) {

		// Delete foreign key reference for field Profiles of entity MyActor

		List<ImogActor> resultForMyActorProfiles = actorProfilesDao.loadAffectedCardNProperty("profiles",
				entity.getId());
		if (resultForMyActorProfiles != null) {
			for (ImogActor foreignEntity : resultForMyActorProfiles) {
				foreignEntity.setModified(new Date());
				foreignEntity.removeFromProfiles(entity);
				actorProfilesDao.saveOrUpdate(foreignEntity, false);
			}
		}

		// Delete foreign key reference for field Profile of entity EntityProfile

		ImogJunction searchCriterionsForEntityProfileProfile = new ImogConjunction();
		BasicCriteria criteriaEntityProfileProfile = new BasicCriteria();
		criteriaEntityProfileProfile.setOperation(CriteriaConstants.RELATIONFIELD_OPERATOR_EQUAL);
		criteriaEntityProfileProfile.setValue(entity.getId());
		criteriaEntityProfileProfile.setField("profile.id");
		searchCriterionsForEntityProfileProfile.add(criteriaEntityProfileProfile);

		List<EntityProfile> resultForEntityProfileProfile = entityProfileDao.load("modified", false,
				searchCriterionsForEntityProfileProfile);
		if (resultForEntityProfileProfile != null) {
			for (EntityProfile foreignEntity : resultForEntityProfileProfile) {
				foreignEntity.setModified(new Date());
				foreignEntity.setProfile(null);
				entityProfileDao.saveOrUpdate(foreignEntity, false);
			}
		}

		// Delete foreign key reference for field Profile of entity FieldGroupProfile

		ImogJunction searchCriterionsForFieldGroupProfileProfile = new ImogConjunction();
		BasicCriteria criteriaFieldGroupProfileProfile = new BasicCriteria();
		criteriaFieldGroupProfileProfile.setOperation(CriteriaConstants.RELATIONFIELD_OPERATOR_EQUAL);
		criteriaFieldGroupProfileProfile.setValue(entity.getId());
		criteriaFieldGroupProfileProfile.setField("profile.id");
		searchCriterionsForFieldGroupProfileProfile.add(criteriaFieldGroupProfileProfile);

		List<FieldGroupProfile> resultForFieldGroupProfileProfile = fieldGroupProfileDao.load("modified", false,
				searchCriterionsForFieldGroupProfileProfile);
		if (resultForFieldGroupProfileProfile != null) {
			for (FieldGroupProfile foreignEntity : resultForFieldGroupProfileProfile) {
				foreignEntity.setModified(new Date());
				foreignEntity.setProfile(null);
				fieldGroupProfileDao.saveOrUpdate(foreignEntity, false);
			}
		}

		dao.delete(entity);
	}

	/**
	 * Removes the specified bean from the database
	 * 
	 * @param entity The bean to be deleted
	 */
	@Transactional
	public void delete(ImogBean entity) {
		handlerHelper.delete(entity);
	}

	/**
	 * Lists the entities of type Profile for the CSV export
	 */
	@Transactional(readOnly = true)
	public List<Profile> listForCsv(String sortProperty, boolean sortOrder, String name) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);

		if (name != null && !name.isEmpty()) {
			BasicCriteria criteria = new BasicCriteria();
			criteria.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
			criteria.setField("name");
			criteria.setValue(name);
			junction.add(criteria);
		}

		List<Profile> beans = dao.load(sortProperty, sortOrder, junction);
		List<Profile> securedBeans = filter.<Profile> toSecure(beans);
		return securedBeans;
	}

	/**
	 * Creates a junction based on the filter field declarations, for the current actor.
	 * 
	 * @param actor the current actor
	 */
	private ImogJunction createFilterJuntion(ImogActor actor) {
		ImogConjunction filterConjunction = new ImogConjunction();
		return filterConjunction;
	}

	/**
	 * Setter for bean injection
	 * 
	 * @param dao the Profile Dao
	 */
	public void setDao(ProfileDao dao) {
		this.dao = dao;
	}

	/**
	 * Setter for bean injection
	 * 
	 * @param imogBeanFilter
	 */
	public void setFilter(ImogBeanFilter filter) {
		this.filter = filter;
	}

	/**
	 * Setter for bean injection.
	 * 
	 * @param systemUtil
	 */
	public void setSystemUtil(SystemUtil systemUtil) {
		this.systemUtil = systemUtil;
	}

	/**
	 * Setter for bean injection.
	 * 
	 * @param helper
	 */
	public void setHandlerHelper(HandlerHelper helper) {
		this.handlerHelper = helper;
	}

	/**
	 * Setter for bean injection
	 * 
	 * @param profileHelper
	 */
	public void setProfileHelper(ProfileHelper profileHelper) {
		this.profileHelper = profileHelper;
	}

	/**
	 * Setter for bean injection
	 * 
	 * @param actorProfilesDao the Actor Dao
	 */
	public void setActorProfilesDao(ImogActorDao actorProfilesDao) {
		this.actorProfilesDao = actorProfilesDao;
	}

	/**
	 * Setter for bean injection
	 * 
	 * @param entityProfileDao the EntityProfile Dao
	 */
	public void setEntityProfileDao(EntityProfileDao entityProfileDao) {
		this.entityProfileDao = entityProfileDao;
	}

	/**
	 * Setter for bean injection
	 * 
	 * @param fieldGroupProfileDao the FieldGroupProfile Dao
	 */
	public void setFieldGroupProfileDao(FieldGroupProfileDao fieldGroupProfileDao) {
		this.fieldGroupProfileDao = fieldGroupProfileDao;
	}

}
