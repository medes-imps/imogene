package org.imogene.web.server.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.imogene.lib.common.constants.CriteriaConstants;
import org.imogene.lib.common.criteria.BasicCriteria;
import org.imogene.lib.common.criteria.ImogConjunction;
import org.imogene.lib.common.criteria.ImogDisjunction;
import org.imogene.lib.common.criteria.ImogJunction;
import org.imogene.lib.common.dynamicfields.DynamicFieldInstance;
import org.imogene.lib.common.dynamicfields.DynamicFieldInstanceDao;
import org.imogene.lib.common.dynamicfields.DynamicFieldTemplate;
import org.imogene.lib.common.dynamicfields.DynamicFieldTemplateDao;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.util.SystemUtil;
import org.imogene.web.server.util.HttpSessionUtil;
import org.springframework.transaction.annotation.Transactional;

/**
 * A data handler for the DynamicFieldTemplate beans
 * 
 * @author Medes-IMPS
 */
public class DynamicFieldTemplateHandler {

	private DynamicFieldTemplateDao dao;

	/* DynamicField_InstanceDao for Foreign Key Deletion */
	private DynamicFieldInstanceDao dynamicFieldInstanceFieldTemplateDao;

	private SystemUtil systemUtil;

	/**
	 * Loads the entity (secured) with the specified id
	 * 
	 * @param entityId the entity id
	 * @return the entity or null
	 */
	@Transactional(readOnly = true)
	public DynamicFieldTemplate findById(String entityId) {
		DynamicFieldTemplate bean = dao.load(entityId);
		return bean;
	}

	/**
	 * Saves or updates the entity
	 * 
	 * @param entity the entity to be saved or updated
	 * @param isNew true if it is a new entity added for the first time.
	 */
	@Transactional
	public void save(DynamicFieldTemplate entity, boolean isNew) {
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
	 * Lists the entities of type DynamicFieldTemplate
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @return list of dynamicField_Template
	 */
	@Transactional(readOnly = true)
	public List<DynamicFieldTemplate> listDynamicFieldTemplate(int i, int j, String sortProperty, boolean sortOrder) {
		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);

		List<DynamicFieldTemplate> beans = dao.load(i, j, sortProperty, sortOrder, junction);

		return beans;
	}

	/**
	 * Lists the entities of type DynamicFieldTemplate
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param criterions request criteria
	 * @return list of dynamicField_Template
	 */
	@Transactional(readOnly = true)
	public List<DynamicFieldTemplate> listDynamicFieldTemplate(int i, int j, String sortProperty, boolean sortOrder,
			ImogJunction criterions) {
		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);
		if (criterions != null)
			junction.add(criterions);

		List<DynamicFieldTemplate> beans = dao.load(i, j, sortProperty, sortOrder, junction);

		return beans;
	}

	/**
	 * Lists the entities of type DynamicFieldTemplate
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param criterions request criteria
	 * @return list of dynamicField_Template
	 */
	@Transactional(readOnly = true)
	public List<DynamicFieldTemplate> listDynamicFieldTemplate(int i, int j, String sortProperty, boolean sortOrder,
			List<BasicCriteria> criterions) {
		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);

		ImogJunction junctionForCrit = new ImogConjunction();
		if (criterions != null) {
			for (BasicCriteria crit : criterions)
				junctionForCrit.add(crit);
		}
		junction.add(junctionForCrit);

		List<DynamicFieldTemplate> beans = dao.load(i, j, sortProperty, sortOrder, junction);

		return beans;
	}

	/**
	 * Lists the non affected entities of type DynamicFieldTemplate
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param criterion request criteria
	 * @param property the property which is not affected
	 * @return list of dynamicField_Template
	 */
	@Transactional(readOnly = true)
	public List<DynamicFieldTemplate> listNonAffectedDynamicFieldTemplate(int i, int j, String sortProperty,
			boolean sortOrder, ImogJunction criterions, String property) {
		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);
		if (criterions != null)
			junction.add(criterions);

		List<DynamicFieldTemplate> beans = dao.loadNonAffected(i, j, sortProperty, sortOrder, property, junction);

		return beans;
	}

	/**
	 * Lists the non affected entities of type DynamicFieldTemplate
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param property the property which is not affected
	 * @return list of dynamicField_Template
	 */
	@Transactional(readOnly = true)
	public List<DynamicFieldTemplate> listNonAffectedDynamicFieldTemplate(int i, int j, String sortProperty,
			boolean sortOrder, String property) {
		return listNonAffectedDynamicFieldTemplate(i, j, sortProperty, sortOrder, null, property);
	}

	/**
	 * Used when DynamicFieldTemplate is involved in a Relation 1 <-> 1 Association and is the ReverseRelationField of
	 * the Relation Return all instance of DynamicFieldTemplate non affected regarding specified property.
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param ImogJunction request criteria
	 * @param property the property which is not affected
	 * @return list of dynamicField_Template
	 */
	@Transactional(readOnly = true)
	public List<DynamicFieldTemplate> listNonAffectedDynamicFieldTemplateReverse(int i, int j, String sortProperty,
			boolean sortOrder, ImogJunction criterions, String property) {
		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);
		if (criterions != null)
			junction.add(criterions);

		List<DynamicFieldTemplate> beans = dao
				.loadNonAffectedReverse(i, j, sortProperty, sortOrder, property, junction);

		return beans;
	}

	/**
	 * Used when DynamicFieldTemplate is involved in a Relation 1 <-> 1 Association and is the ReverseRelationField of
	 * the Relation Return all instance of DynamicFieldTemplate non affected regarding specified property.
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param property the property which is not affected
	 * @return list of dynamicField_Template
	 */
	@Transactional(readOnly = true)
	public List<DynamicFieldTemplate> listNonAffectedDynamicFieldTemplateReverse(int i, int j, String sortProperty,
			boolean sortOrder, String property) {
		return listNonAffectedDynamicFieldTemplateReverse(i, j, sortProperty, sortOrder, null, property);
	}

	/**
	 * Gets an empty list of DynamicFieldTemplate
	 * 
	 * @return an empty list of DynamicFieldTemplate
	 */
	@Transactional(readOnly = true)
	public List<DynamicFieldTemplate> getDynamicFieldTemplateEmptyList() {
		return new ArrayList<DynamicFieldTemplate>();
	}

	/**
	 * Counts the number of dynamicField_Template in the database
	 * 
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countDynamicFieldTemplate() {
		return countDynamicFieldTemplate(null);
	}

	/**
	 * Counts the number of dynamicField_Template in the database, that match the criteria
	 * 
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countDynamicFieldTemplate(ImogJunction criterions) {
		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);
		if (criterions != null)
			junction.add(criterions);

		return dao.count(junction);
	}

	/**
	 * Counts the number of non affected dynamicField_Template entities in the database
	 * 
	 * @param property the property which is not affected
	 * @param criterion request criteria
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedDynamicFieldTemplate(String property, ImogJunction criterions) {
		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);
		if (criterions != null)
			junction.add(criterions);

		return dao.countNonAffected(property, junction);
	}

	/**
	 * Counts the number of non affected dynamicField_Template entities in the database
	 * 
	 * @param property the property which is not affected
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedDynamicFieldTemplate(String property) {
		return countNonAffectedDynamicFieldTemplate(property, null);
	}

	/**
	 * Counts the number of non affected dynamicField_Template entities in the database
	 * 
	 * @param property the property which is not affected
	 * @param criterion request criteria
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedDynamicFieldTemplateReverse(String property, ImogJunction criterions) {
		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);
		if (criterions != null)
			junction.add(criterions);
		return dao.countNonAffectedReverse(property, junction);
	}

	/**
	 * Counts the number of non affected dynamicField_Template entities in the database
	 * 
	 * @param property the property which is not affected
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedDynamicFieldTemplateReverse(String property) {
		return countNonAffectedDynamicFieldTemplateReverse(property, null);
	}

	/**
	 * Deletes a group of entities identified by their IDs
	 * 
	 * @param ids Entities to delete IDs
	 * @return The list of deleted entities IDs
	 */
	@Transactional
	public void delete(Set<DynamicFieldTemplate> entities) {
		if (entities != null) {
			for (DynamicFieldTemplate entity : entities)
				delete(entity);
		}
	}

	/**
	 * Removes the specified entity from the database
	 * 
	 * @param entity The entity to be deleted
	 */
	@Transactional
	public void delete(DynamicFieldTemplate entity) {
		// Delete foreign key reference for field FieldTemplate of entity DynamicField_Instance
		ImogJunction searchCriterionsForDynamicField_InstanceFieldTemplate = new ImogConjunction();
		BasicCriteria criteriaDynamicField_InstanceFieldTemplate = new BasicCriteria();
		criteriaDynamicField_InstanceFieldTemplate.setOperation(CriteriaConstants.RELATIONFIELD_OPERATOR_EQUAL);
		criteriaDynamicField_InstanceFieldTemplate.setValue(entity.getId());
		criteriaDynamicField_InstanceFieldTemplate.setField("fieldTemplate.id");
		searchCriterionsForDynamicField_InstanceFieldTemplate.add(criteriaDynamicField_InstanceFieldTemplate);

		List<DynamicFieldInstance> resultForDynamicField_InstanceFieldTemplate = dynamicFieldInstanceFieldTemplateDao
				.load("modified", false, searchCriterionsForDynamicField_InstanceFieldTemplate);
		if (resultForDynamicField_InstanceFieldTemplate != null) {
			for (DynamicFieldInstance foreignEntity : resultForDynamicField_InstanceFieldTemplate) {
				foreignEntity.setModified(new Date());
				foreignEntity.setFieldTemplate(null);
				dynamicFieldInstanceFieldTemplateDao.saveOrUpdate(foreignEntity, false);
			}
		}

		dao.delete(entity);
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
	 * @param dao the DynamicFieldTemplate Dao
	 */
	public void setDao(DynamicFieldTemplateDao dao) {
		this.dao = dao;
	}

	/**
	 * Setter for bean injection
	 * 
	 * @param dynamicField_InstanceFieldTemplateDao the DynamicField_Instance Dao
	 */
	public void setDynamicFieldInstanceFieldTemplateDao(DynamicFieldInstanceDao dynamicField_InstanceFieldTemplateDao) {
		this.dynamicFieldInstanceFieldTemplateDao = dynamicField_InstanceFieldTemplateDao;
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
	 * 
	 * @param formType
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<DynamicFieldTemplate> listDynamicFieldTemplate(String formType) {
		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogConjunction filterConjunction = new ImogConjunction();

		BasicCriteria formTypeCrit = new BasicCriteria();
		formTypeCrit.setField("formType");
		formTypeCrit.setOperation(CriteriaConstants.STRING_OPERATOR_EQUAL);
		formTypeCrit.setValue(formType);
		filterConjunction.add(formTypeCrit);

		BasicCriteria isActivatedCrit = new BasicCriteria();
		isActivatedCrit.setField("isActivated");
		isActivatedCrit.setOperation(CriteriaConstants.BOOLEAN_OPERATOR_EQUAL);
		isActivatedCrit.setValue("true");
		filterConjunction.add(isActivatedCrit);

		ImogDisjunction usersDisjunction = new ImogDisjunction();

		BasicCriteria allUsersCrit = new BasicCriteria();
		allUsersCrit.setField("allUsers");
		allUsersCrit.setOperation(CriteriaConstants.BOOLEAN_OPERATOR_EQUAL);
		allUsersCrit.setValue("true");
		usersDisjunction.add(allUsersCrit);

		ImogConjunction currentUserConjunction = new ImogConjunction();
		BasicCriteria creatorCrit = new BasicCriteria();
		creatorCrit.setField("templateCreator.id");
		creatorCrit.setOperation(CriteriaConstants.RELATIONFIELD_OPERATOR_EQUAL);
		creatorCrit.setValue(actor.getId());
		currentUserConjunction.add(creatorCrit);
		BasicCriteria isDefaultCrit = new BasicCriteria();
		isDefaultCrit.setField("isDefault");
		isDefaultCrit.setOperation(CriteriaConstants.BOOLEAN_OPERATOR_EQUAL);
		isDefaultCrit.setValue("true");
		currentUserConjunction.add(isDefaultCrit);
		usersDisjunction.add(currentUserConjunction);

		filterConjunction.add(usersDisjunction);

		List<DynamicFieldTemplate> beans = dao.load("fieldPosition", true, filterConjunction);
		return beans;
	}
}
