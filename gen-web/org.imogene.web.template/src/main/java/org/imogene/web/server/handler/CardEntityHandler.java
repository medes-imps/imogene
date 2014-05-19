package org.imogene.web.server.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.imogene.lib.common.constants.CriteriaConstants;
import org.imogene.lib.common.criteria.BasicCriteria;
import org.imogene.lib.common.criteria.ImogConjunction;
import org.imogene.lib.common.criteria.ImogJunction;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.entity.ImogBean;
import org.imogene.lib.common.model.CardEntity;
import org.imogene.lib.common.model.CardEntityDao;
import org.imogene.lib.common.model.FieldGroup;
import org.imogene.lib.common.model.FieldGroupDao;
import org.imogene.lib.common.profile.EntityProfile;
import org.imogene.lib.common.profile.EntityProfileDao;
import org.imogene.lib.common.profile.FieldGroupProfile;
import org.imogene.lib.common.profile.FieldGroupProfileDao;
import org.imogene.lib.common.security.ImogBeanFilter;
import org.imogene.lib.common.util.SystemUtil;
import org.imogene.web.server.util.HttpSessionUtil;
import org.springframework.transaction.annotation.Transactional;

/**
 * A data handler for the CardEntity beans
 * 
 * @author Medes-IMPS
 */
public class CardEntityHandler {

	private CardEntityDao dao;
	/* EntityProfileDao for Foreign Key Deletion */
	private EntityProfileDao entityProfileDao;
	/* FieldGroupProfileDao for Foreign Key Deletion */
	private FieldGroupProfileDao fieldGroupProfileDao;
	/* FieldGroupDao for Foreign Key Deletion */
	private FieldGroupDao fieldGroupDao;

	private ImogBeanFilter filter;
	private SystemUtil systemUtil;
	private HandlerHelper handlerHelper;

	/**
	 * Loads the entity with the specified id
	 * 
	 * @param entityId the entity id
	 * @return the entity or null
	 */
	@Transactional(readOnly = true)
	public CardEntity findById(String entityId) {
		return dao.load(entityId);
	}

	/**
	 * Loads the entity with the specified id
	 * 
	 * @param entityId the entity id
	 * @return the entity or null
	 */
	@Transactional(readOnly = true)
	public CardEntity getById(String entityId) {
		return dao.getById(entityId);
	}

	/**
	 * Saves or updates the entity
	 * 
	 * @param entity the entity to be saved or updated
	 * @param isNew true if it is a new entity added for the first time.
	 */
	@Transactional
	public void save(CardEntity entity, boolean isNew) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();

		if (entity != null) {

			if (isNew) {
				entity.setCreated(new Date(systemUtil.getCurrentTimeMillis()));
				entity.setCreatedBy(actor.getLogin());
			}

			entity.setModified(new Date(systemUtil.getCurrentTimeMillis()));
			entity.setModifiedBy(actor.getLogin());
			entity.setModifiedFrom(systemUtil.getTerminal());

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
	 * Lists the entities of type CardEntity
	 * 
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @return list of cardEntity
	 */
	@Transactional(readOnly = true)
	public List<CardEntity> listCardEntity(String sortProperty, boolean sortOrder) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);

		List<CardEntity> beans = dao.load(sortProperty, sortOrder, junction);

		return beans;
	}

	/**
	 * Lists the entities of type CardEntity
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @return list of cardEntity
	 */
	@Transactional(readOnly = true)
	public List<CardEntity> listCardEntity(int i, int j, String sortProperty, boolean sortOrder) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);

		List<CardEntity> beans = dao.load(i, j, sortProperty, sortOrder, junction);

		return beans;
	}

	/**
	 * Lists the entities of type CardEntity
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param criterions request criteria
	 * @return list of cardEntity
	 */
	@Transactional(readOnly = true)
	public List<CardEntity> listCardEntity(int i, int j, String sortProperty, boolean sortOrder, ImogJunction criterions) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);
		if (criterions != null)
			junction.add(criterions);

		List<CardEntity> beans = dao.load(i, j, sortProperty, sortOrder, junction);

		return beans;
	}

	/**
	 * Lists the entities of type CardEntity
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param criterions request criteria
	 * @return list of cardEntity
	 */
	@Transactional(readOnly = true)
	public List<CardEntity> listCardEntity(int i, int j, String sortProperty, boolean sortOrder,
			List<BasicCriteria> criterions) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);

		ImogJunction junctionForCrit = new ImogConjunction();
		if (criterions != null) {
			for (BasicCriteria crit : criterions)
				junctionForCrit.add(crit);
		}
		junction.add(junctionForCrit);

		List<CardEntity> beans = dao.load(i, j, sortProperty, sortOrder, junction);

		return beans;
	}

	/**
	 * Lists the non affected entities of type CardEntity
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param criterion request criteria
	 * @param property the property which is not affected
	 * @return list of cardEntity
	 */
	@Transactional(readOnly = true)
	public List<CardEntity> listNonAffectedCardEntity(int i, int j, String sortProperty, boolean sortOrder,
			ImogJunction criterions, String property) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);
		if (criterions != null)
			junction.add(criterions);

		List<CardEntity> beans = dao.loadNonAffected(i, j, sortProperty, sortOrder, property, junction);

		return beans;
	}

	/**
	 * Lists the non affected entities of type CardEntity
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param property the property which is not affected
	 * @return list of cardEntity
	 */
	@Transactional(readOnly = true)
	public List<CardEntity> listNonAffectedCardEntity(int i, int j, String sortProperty, boolean sortOrder,
			String property) {
		return listNonAffectedCardEntity(i, j, sortProperty, sortOrder, null, property);
	}

	/**
	 * Used when CardEntity is involved in a Relation 1 <-> 1 Association and is the ReverseRelationField of the
	 * Relation Return all instance of CardEntity non affected regarding specified property.
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param ImogJunction request criteria
	 * @param property the property which is not affected
	 * @return list of cardEntity
	 */
	@Transactional(readOnly = true)
	public List<CardEntity> listNonAffectedCardEntityReverse(int i, int j, String sortProperty, boolean sortOrder,
			ImogJunction criterions, String property) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);
		if (criterions != null)
			junction.add(criterions);

		List<CardEntity> beans = dao.loadNonAffectedReverse(i, j, sortProperty, sortOrder, property, junction);

		return beans;
	}

	/**
	 * Used when CardEntity is involved in a Relation 1 <-> 1 Association and is the ReverseRelationField of the
	 * Relation Return all instance of CardEntity non affected regarding specified property.
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param property the property which is not affected
	 * @return list of cardEntity
	 */
	@Transactional(readOnly = true)
	public List<CardEntity> listNonAffectedCardEntityReverse(int i, int j, String sortProperty, boolean sortOrder,
			String property) {
		return listNonAffectedCardEntityReverse(i, j, sortProperty, sortOrder, null, property);
	}

	/**
	 * Gets an empty list of CardEntity
	 * 
	 * @return an empty list of CardEntity
	 */
	@Transactional(readOnly = true)
	public List<CardEntity> getCardEntityEmptyList() {
		return new ArrayList<CardEntity>();
	}

	/**
	 * Counts the number of cardEntity in the database
	 * 
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countCardEntity() {
		return countCardEntity(null);
	}

	/**
	 * Counts the number of cardEntity in the database, that match the criteria
	 * 
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countCardEntity(ImogJunction criterions) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);
		if (criterions != null)
			junction.add(criterions);

		return dao.count(junction);
	}

	/**
	 * Counts the number of non affected cardEntity entities in the database
	 * 
	 * @param property the property which is not affected
	 * @param criterion request criteria
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedCardEntity(String property, ImogJunction criterions) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);
		if (criterions != null)
			junction.add(criterions);

		return dao.countNonAffected(property, junction);
	}

	/**
	 * Counts the number of non affected cardEntity entities in the database
	 * 
	 * @param property the property which is not affected
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedCardEntity(String property) {
		return countNonAffectedCardEntity(property, null);
	}

	/**
	 * Counts the number of non affected cardEntity entities in the database
	 * 
	 * @param property the property which is not affected
	 * @param criterion request criteria
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedCardEntityReverse(String property, ImogJunction criterions) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);
		if (criterions != null)
			junction.add(criterions);
		return dao.countNonAffectedReverse(property, junction);
	}

	/**
	 * Counts the number of non affected cardEntity entities in the database
	 * 
	 * @param property the property which is not affected
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedCardEntityReverse(String property) {
		return countNonAffectedCardEntityReverse(property, null);
	}

	/**
	 * Deletes a group of entities identified by their IDs
	 * 
	 * @param ids Entities to delete IDs
	 * @return The list of deleted entities IDs
	 */
	@Transactional
	public void delete(Set<CardEntity> entities) {
		if (entities != null) {
			for (CardEntity entity : entities)
				delete(entity);
		}
	}

	/**
	 * Removes the specified entity from the database
	 * 
	 * @param entity The entity to be deleted
	 */
	@Transactional
	public void delete(CardEntity entity) {

		// Delete foreign key reference for field Entity of entity EntityProfile

		ImogJunction searchCriterionsForEntityProfileEntity = new ImogConjunction();
		BasicCriteria criteriaEntityProfileEntity = new BasicCriteria();
		criteriaEntityProfileEntity.setOperation(CriteriaConstants.RELATIONFIELD_OPERATOR_EQUAL);
		criteriaEntityProfileEntity.setValue(entity.getId());
		criteriaEntityProfileEntity.setField("entity.id");
		searchCriterionsForEntityProfileEntity.add(criteriaEntityProfileEntity);

		List<EntityProfile> resultForEntityProfileEntity = entityProfileDao.load("modified", false,
				searchCriterionsForEntityProfileEntity);
		if (resultForEntityProfileEntity != null) {
			for (EntityProfile foreignEntity : resultForEntityProfileEntity) {
				foreignEntity.setModified(new Date());
				foreignEntity.setEntity(null);
				entityProfileDao.saveOrUpdate(foreignEntity, false);
			}
		}

		// Delete foreign key reference for field CardEntity of entity FieldGroupProfile

		ImogJunction searchCriterionsForFieldGroupProfileCardEntity = new ImogConjunction();
		BasicCriteria criteriaFieldGroupProfileCardEntity = new BasicCriteria();
		criteriaFieldGroupProfileCardEntity.setOperation(CriteriaConstants.RELATIONFIELD_OPERATOR_EQUAL);
		criteriaFieldGroupProfileCardEntity.setValue(entity.getId());
		criteriaFieldGroupProfileCardEntity.setField("cardEntity.id");
		searchCriterionsForFieldGroupProfileCardEntity.add(criteriaFieldGroupProfileCardEntity);

		List<FieldGroupProfile> resultForFieldGroupProfileCardEntity = fieldGroupProfileDao.load("modified", false,
				searchCriterionsForFieldGroupProfileCardEntity);
		if (resultForFieldGroupProfileCardEntity != null) {
			for (FieldGroupProfile foreignEntity : resultForFieldGroupProfileCardEntity) {
				foreignEntity.setModified(new Date());
				foreignEntity.setCardEntity(null);
				fieldGroupProfileDao.saveOrUpdate(foreignEntity, false);
			}
		}

		// Delete foreign key reference for field Entity of entity FieldGroup

		ImogJunction searchCriterionsForFieldGroupEntity = new ImogConjunction();
		BasicCriteria criteriaFieldGroupEntity = new BasicCriteria();
		criteriaFieldGroupEntity.setOperation(CriteriaConstants.RELATIONFIELD_OPERATOR_EQUAL);
		criteriaFieldGroupEntity.setValue(entity.getId());
		criteriaFieldGroupEntity.setField("entity.id");
		searchCriterionsForFieldGroupEntity.add(criteriaFieldGroupEntity);

		List<FieldGroup> resultForFieldGroupEntity = fieldGroupDao.load("modified", false,
				searchCriterionsForFieldGroupEntity);
		if (resultForFieldGroupEntity != null) {
			for (FieldGroup foreignEntity : resultForFieldGroupEntity) {
				foreignEntity.setModified(new Date());
				foreignEntity.setEntity(null);
				fieldGroupDao.saveOrUpdate(foreignEntity, false);
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
	 * Lists the entities of type CardEntity for the CSV export
	 */
	@Transactional(readOnly = true)
	public List<CardEntity> listForCsv(String sortProperty, boolean sortOrder, String name) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);

		if (name != null && !name.isEmpty()) {
			BasicCriteria criteria = new BasicCriteria();
			criteria.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
			criteria.setField("name");
			criteria.setValue(name);
			junction.add(criteria);
		}

		List<CardEntity> beans = dao.load(sortProperty, sortOrder, junction);
		List<CardEntity> securedBeans = filter.<CardEntity> toSecure(beans);
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
	 * @param dao the CardEntity Dao
	 */
	public void setDao(CardEntityDao dao) {
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

	/**
	 * Setter for bean injection
	 * 
	 * @param fieldGroupDao the FieldGroup Dao
	 */
	public void setFieldGroupDao(FieldGroupDao fieldGroupDao) {
		this.fieldGroupDao = fieldGroupDao;
	}

}
