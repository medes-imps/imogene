package org.imogene.web.server.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.imogene.lib.common.criteria.BasicCriteria;
import org.imogene.lib.common.criteria.ImogConjunction;
import org.imogene.lib.common.criteria.ImogJunction;
import org.imogene.lib.common.dynamicfields.DynamicFieldInstance;
import org.imogene.lib.common.dynamicfields.DynamicFieldInstanceDao;
import org.imogene.lib.common.dynamicfields.DynamicFieldTemplate;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.web.server.util.HttpSessionUtil;
import org.springframework.transaction.annotation.Transactional;

/**
 * A data handler for the DynamicFieldInstance beans
 * 
 * @author Medes-IMPS
 */
public class DynamicFieldInstanceHandler {

	private DynamicFieldInstanceDao dao;
	private DynamicFieldTemplateHandler fieldTemplateHandler;

	/**
	 * Loads the entity (secured) with the specified id
	 * 
	 * @param entityId the entity id
	 * @return the entity or null
	 */
	@Transactional(readOnly = true)
	public DynamicFieldInstance findById(String entityId) {
		DynamicFieldInstance bean = dao.load(entityId);
		return bean;
	}

	/**
	 * Saves or updates the entity
	 * 
	 * @param entity the entity to be saved or updated
	 * @param isNew true if it is a new entity added for the first time.
	 */
	@Transactional
	public void save(DynamicFieldInstance entity, boolean isNew) {
		if (entity != null && entity.getFieldValue() != null && !entity.getFieldValue().equals(""))
			dao.saveOrUpdate(entity, isNew);
	}

	/**
	 * Lists the entities of type DynamicFieldInstance
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @return list of dynamicField_Instance
	 */
	@Transactional(readOnly = true)
	public List<DynamicFieldInstance> listDynamicFieldInstance(int i, int j, String sortProperty, boolean sortOrder) {
		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);

		List<DynamicFieldInstance> beans = dao.load(i, j, sortProperty, sortOrder, junction);

		return beans;
	}

	/**
	 * Lists the entities of type DynamicFieldInstance
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param criterions request criteria
	 * @return list of dynamicField_Instance
	 */
	@Transactional(readOnly = true)
	public List<DynamicFieldInstance> listDynamicFieldInstance(int i, int j, String sortProperty, boolean sortOrder,
			ImogJunction criterions) {
		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);
		if (criterions != null)
			junction.add(criterions);

		List<DynamicFieldInstance> beans = dao.load(i, j, sortProperty, sortOrder, junction);

		return beans;
	}

	/**
	 * Lists the entities of type DynamicFieldInstance
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param criterions request criteria
	 * @return list of dynamicField_Instance
	 */
	@Transactional(readOnly = true)
	public List<DynamicFieldInstance> listDynamicFieldInstance(int i, int j, String sortProperty, boolean sortOrder,
			List<BasicCriteria> criterions) {
		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);

		ImogJunction junctionForCrit = new ImogConjunction();
		if (criterions != null) {
			for (BasicCriteria crit : criterions)
				junctionForCrit.add(crit);
		}
		junction.add(junctionForCrit);

		List<DynamicFieldInstance> beans = dao.load(i, j, sortProperty, sortOrder, junction);

		return beans;
	}

	/**
	 * Lists the entities of type DynamicFieldInstance
	 * 
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param criterions request criteria
	 * @return list of dynamicField_Instance
	 */
	public List<DynamicFieldInstance> listDynamicFieldInstance(String sortProperty, boolean sortOrder,
			ImogJunction criterions) {
		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);
		if (criterions != null)
			junction.add(criterions);

		List<DynamicFieldInstance> beans = dao.load(sortProperty, sortOrder, junction);
		return beans;
	}

	/**
	 * Lists the non affected entities of type DynamicFieldInstance
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param criterion request criteria
	 * @param property the property which is not affected
	 * @return list of dynamicField_Instance
	 */
	@Transactional(readOnly = true)
	public List<DynamicFieldInstance> listNonAffectedDynamicFieldInstance(int i, int j, String sortProperty,
			boolean sortOrder, ImogJunction criterions, String property) {
		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);
		if (criterions != null)
			junction.add(criterions);

		List<DynamicFieldInstance> beans = dao.loadNonAffected(i, j, sortProperty, sortOrder, property, junction);

		return beans;
	}

	/**
	 * Lists the non affected entities of type DynamicFieldInstance
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param property the property which is not affected
	 * @return list of dynamicField_Instance
	 */
	@Transactional(readOnly = true)
	public List<DynamicFieldInstance> listNonAffectedDynamicFieldInstance(int i, int j, String sortProperty,
			boolean sortOrder, String property) {
		return listNonAffectedDynamicFieldInstance(i, j, sortProperty, sortOrder, null, property);
	}

	/**
	 * Used when DynamicFieldInstance is involved in a Relation 1 <-> 1 Association and is the ReverseRelationField of
	 * the Relation Return all instance of DynamicFieldInstance non affected regarding specified property.
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param ImogJunction request criteria
	 * @param property the property which is not affected
	 * @return list of dynamicField_Instance
	 */
	@Transactional(readOnly = true)
	public List<DynamicFieldInstance> listNonAffectedDynamicFieldInstanceReverse(int i, int j, String sortProperty,
			boolean sortOrder, ImogJunction criterions, String property) {
		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);
		if (criterions != null)
			junction.add(criterions);

		List<DynamicFieldInstance> beans = dao
				.loadNonAffectedReverse(i, j, sortProperty, sortOrder, property, junction);

		return beans;
	}

	/**
	 * Used when DynamicFieldInstance is involved in a Relation 1 <-> 1 Association and is the ReverseRelationField of
	 * the Relation Return all instance of DynamicFieldInstance non affected regarding specified property.
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param property the property which is not affected
	 * @return list of dynamicField_Instance
	 */
	@Transactional(readOnly = true)
	public List<DynamicFieldInstance> listNonAffectedDynamicFieldInstanceReverse(int i, int j, String sortProperty,
			boolean sortOrder, String property) {
		return listNonAffectedDynamicFieldInstanceReverse(i, j, sortProperty, sortOrder, null, property);
	}

	/**
	 * Gets an empty list of DynamicFieldInstance
	 * 
	 * @return an empty list of DynamicFieldInstance
	 */
	@Transactional(readOnly = true)
	public List<DynamicFieldInstance> getDynamicFieldInstanceEmptyList() {
		return new ArrayList<DynamicFieldInstance>();
	}

	/**
	 * Counts the number of dynamicField_Instance in the database
	 * 
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countDynamicFieldInstance() {
		return countDynamicFieldInstance(null);
	}

	/**
	 * Counts the number of dynamicField_Instance in the database, that match the criteria
	 * 
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countDynamicFieldInstance(ImogJunction criterions) {
		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);
		if (criterions != null)
			junction.add(criterions);

		return dao.count(junction);
	}

	/**
	 * Counts the number of non affected dynamicField_Instance entities in the database
	 * 
	 * @param property the property which is not affected
	 * @param criterion request criteria
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedDynamicFieldInstance(String property, ImogJunction criterions) {
		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);
		if (criterions != null)
			junction.add(criterions);

		return dao.countNonAffected(property, junction);
	}

	/**
	 * Counts the number of non affected dynamicField_Instance entities in the database
	 * 
	 * @param property the property which is not affected
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedDynamicFieldInstance(String property) {
		return countNonAffectedDynamicFieldInstance(property, null);
	}

	/**
	 * Counts the number of non affected dynamicField_Instance entities in the database
	 * 
	 * @param property the property which is not affected
	 * @param criterion request criteria
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedDynamicFieldInstanceReverse(String property, ImogJunction criterions) {
		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);
		if (criterions != null)
			junction.add(criterions);
		return dao.countNonAffectedReverse(property, junction);
	}

	/**
	 * Counts the number of non affected dynamicField_Instance entities in the database
	 * 
	 * @param property the property which is not affected
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedDynamicFieldInstanceReverse(String property) {
		return countNonAffectedDynamicFieldInstanceReverse(property, null);
	}

	/**
	 * Deletes a group of entities identified by their IDs
	 * 
	 * @param ids Entities to delete IDs
	 * @return The list of deleted entities IDs
	 */
	@Transactional
	public void delete(Set<DynamicFieldInstance> entities) {
		if (entities != null) {
			for (DynamicFieldInstance entity : entities)
				delete(entity);
		}
	}

	/**
	 * Removes the specified entity from the database
	 * 
	 * @param entity The entity to be deleted
	 */
	@Transactional
	public void delete(DynamicFieldInstance entity) {
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
	 * Saves entity of type DynamicField_Template
	 * 
	 * @param entity the DynamicField_Template to be saved or updated
	 * @param isNew true if it is a new entity added for the first time.
	 */
	public void saveFieldTemplate(DynamicFieldTemplate entity, boolean isNew) {
		fieldTemplateHandler.save(entity, isNew);
	}

	/**
	 * Deletes entity of type DynamicField_Template
	 * 
	 * @param toDelete the DynamicField_Template to be deleted
	 */
	public void deleteFieldTemplate(DynamicFieldTemplate toDelete) {
		fieldTemplateHandler.delete(toDelete);
	}

	/**
	 * Setter for bean injection
	 * 
	 * @param dao the DynamicFieldInstance Dao
	 */
	public void setDao(DynamicFieldInstanceDao dao) {
		this.dao = dao;
	}

	/**
	 * Setter for bean injection
	 * 
	 * @param fieldTemplateHandler the DynamicField_Template Handler
	 */
	public void setFieldTemplateHandler(DynamicFieldTemplateHandler fieldTemplateHandler) {
		this.fieldTemplateHandler = fieldTemplateHandler;
	}

}
