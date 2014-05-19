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
import org.imogene.lib.common.profile.FieldGroupProfile;
import org.imogene.lib.common.profile.FieldGroupProfileDao;
import org.imogene.lib.common.security.ImogBeanFilter;
import org.imogene.lib.common.util.SystemUtil;
import org.imogene.web.server.util.HttpSessionUtil;
import org.springframework.transaction.annotation.Transactional;

/**
 * A data handler for the FieldGroupProfile beans
 * 
 * @author Medes-IMPS
 */
public class FieldGroupProfileHandler {

	private FieldGroupProfileDao dao;

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
	public FieldGroupProfile findById(String entityId) {
		return dao.load(entityId);
	}

	/**
	 * Loads the entity with the specified id
	 * 
	 * @param entityId the entity id
	 * @return the entity or null
	 */
	@Transactional(readOnly = true)
	public FieldGroupProfile getById(String entityId) {
		return dao.getById(entityId);
	}

	/**
	 * Saves or updates the entity
	 * 
	 * @param entity the entity to be saved or updated
	 * @param isNew true if it is a new entity added for the first time.
	 */
	@Transactional
	public void save(FieldGroupProfile entity, boolean isNew) {

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
	 * Lists the entities of type FieldGroupProfile
	 * 
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @return list of fieldGroupProfile
	 */
	@Transactional(readOnly = true)
	public List<FieldGroupProfile> listFieldGroupProfile(String sortProperty, boolean sortOrder) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);

		List<FieldGroupProfile> beans = dao.load(sortProperty, sortOrder, junction);

		return beans;
	}

	/**
	 * Lists the entities of type FieldGroupProfile
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @return list of fieldGroupProfile
	 */
	@Transactional(readOnly = true)
	public List<FieldGroupProfile> listFieldGroupProfile(int i, int j, String sortProperty, boolean sortOrder) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);

		List<FieldGroupProfile> beans = dao.load(i, j, sortProperty, sortOrder, junction);

		return beans;
	}

	/**
	 * Lists the entities of type FieldGroupProfile
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param criterions request criteria
	 * @return list of fieldGroupProfile
	 */
	@Transactional(readOnly = true)
	public List<FieldGroupProfile> listFieldGroupProfile(int i, int j, String sortProperty, boolean sortOrder,
			ImogJunction criterions) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);
		if (criterions != null)
			junction.add(criterions);

		List<FieldGroupProfile> beans = dao.load(i, j, sortProperty, sortOrder, junction);

		return beans;
	}

	/**
	 * Lists the entities of type FieldGroupProfile
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param criterions request criteria
	 * @return list of fieldGroupProfile
	 */
	@Transactional(readOnly = true)
	public List<FieldGroupProfile> listFieldGroupProfile(int i, int j, String sortProperty, boolean sortOrder,
			List<BasicCriteria> criterions) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);

		ImogJunction junctionForCrit = new ImogConjunction();
		if (criterions != null) {
			for (BasicCriteria crit : criterions)
				junctionForCrit.add(crit);
		}
		junction.add(junctionForCrit);

		List<FieldGroupProfile> beans = dao.load(i, j, sortProperty, sortOrder, junction);

		return beans;
	}

	/**
	 * Lists the non affected entities of type FieldGroupProfile
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param criterion request criteria
	 * @param property the property which is not affected
	 * @return list of fieldGroupProfile
	 */
	@Transactional(readOnly = true)
	public List<FieldGroupProfile> listNonAffectedFieldGroupProfile(int i, int j, String sortProperty,
			boolean sortOrder, ImogJunction criterions, String property) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);
		if (criterions != null)
			junction.add(criterions);

		List<FieldGroupProfile> beans = dao.loadNonAffected(i, j, sortProperty, sortOrder, property, junction);

		return beans;
	}

	/**
	 * Lists the non affected entities of type FieldGroupProfile
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param property the property which is not affected
	 * @return list of fieldGroupProfile
	 */
	@Transactional(readOnly = true)
	public List<FieldGroupProfile> listNonAffectedFieldGroupProfile(int i, int j, String sortProperty,
			boolean sortOrder, String property) {
		return listNonAffectedFieldGroupProfile(i, j, sortProperty, sortOrder, null, property);
	}

	/**
	 * Used when FieldGroupProfile is involved in a Relation 1 <-> 1 Association and is the ReverseRelationField of the
	 * Relation Return all instance of FieldGroupProfile non affected regarding specified property.
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param ImogJunction request criteria
	 * @param property the property which is not affected
	 * @return list of fieldGroupProfile
	 */
	@Transactional(readOnly = true)
	public List<FieldGroupProfile> listNonAffectedFieldGroupProfileReverse(int i, int j, String sortProperty,
			boolean sortOrder, ImogJunction criterions, String property) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);
		if (criterions != null)
			junction.add(criterions);

		List<FieldGroupProfile> beans = dao.loadNonAffectedReverse(i, j, sortProperty, sortOrder, property, junction);

		return beans;
	}

	/**
	 * Used when FieldGroupProfile is involved in a Relation 1 <-> 1 Association and is the ReverseRelationField of the
	 * Relation Return all instance of FieldGroupProfile non affected regarding specified property.
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param property the property which is not affected
	 * @return list of fieldGroupProfile
	 */
	@Transactional(readOnly = true)
	public List<FieldGroupProfile> listNonAffectedFieldGroupProfileReverse(int i, int j, String sortProperty,
			boolean sortOrder, String property) {
		return listNonAffectedFieldGroupProfileReverse(i, j, sortProperty, sortOrder, null, property);
	}

	/**
	 * Gets an empty list of FieldGroupProfile
	 * 
	 * @return an empty list of FieldGroupProfile
	 */
	@Transactional(readOnly = true)
	public List<FieldGroupProfile> getFieldGroupProfileEmptyList() {
		return new ArrayList<FieldGroupProfile>();
	}

	/**
	 * Counts the number of fieldGroupProfile in the database
	 * 
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countFieldGroupProfile() {
		return countFieldGroupProfile(null);
	}

	/**
	 * Counts the number of fieldGroupProfile in the database, that match the criteria
	 * 
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countFieldGroupProfile(ImogJunction criterions) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);
		if (criterions != null)
			junction.add(criterions);

		return dao.count(junction);
	}

	/**
	 * Counts the number of non affected fieldGroupProfile entities in the database
	 * 
	 * @param property the property which is not affected
	 * @param criterion request criteria
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedFieldGroupProfile(String property, ImogJunction criterions) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);
		if (criterions != null)
			junction.add(criterions);

		return dao.countNonAffected(property, junction);
	}

	/**
	 * Counts the number of non affected fieldGroupProfile entities in the database
	 * 
	 * @param property the property which is not affected
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedFieldGroupProfile(String property) {
		return countNonAffectedFieldGroupProfile(property, null);
	}

	/**
	 * Counts the number of non affected fieldGroupProfile entities in the database
	 * 
	 * @param property the property which is not affected
	 * @param criterion request criteria
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedFieldGroupProfileReverse(String property, ImogJunction criterions) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);
		if (criterions != null)
			junction.add(criterions);
		return dao.countNonAffectedReverse(property, junction);
	}

	/**
	 * Counts the number of non affected fieldGroupProfile entities in the database
	 * 
	 * @param property the property which is not affected
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedFieldGroupProfileReverse(String property) {
		return countNonAffectedFieldGroupProfileReverse(property, null);
	}

	/**
	 * Deletes a group of entities identified by their IDs
	 * 
	 * @param ids Entities to delete IDs
	 * @return The list of deleted entities IDs
	 */
	@Transactional
	public void delete(Set<FieldGroupProfile> entities) {
		if (entities != null) {
			for (FieldGroupProfile entity : entities)
				delete(entity);
		}
	}

	/**
	 * Removes the specified entity from the database
	 * 
	 * @param entity The entity to be deleted
	 */
	@Transactional
	public void delete(FieldGroupProfile entity) {

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
	 * Lists the entities of type FieldGroupProfile for the CSV export
	 */
	@Transactional(readOnly = true)
	public List<FieldGroupProfile> listForCsv(String sortProperty, boolean sortOrder, String profile_name,
			String fieldGroup_entity_name, String fieldGroup_name, String read, String write, String export) {

		ImogActor actor = HttpSessionUtil.getCurrentUser();
		ImogJunction junction = createFilterJuntion(actor);

		if (profile_name != null && !profile_name.isEmpty()) {
			BasicCriteria criteria = new BasicCriteria();
			criteria.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
			criteria.setField("profile.name");
			criteria.setValue(profile_name);
			junction.add(criteria);
		}
		if (fieldGroup_entity_name != null && !fieldGroup_entity_name.isEmpty()) {
			BasicCriteria criteria = new BasicCriteria();
			criteria.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
			criteria.setField("fieldGroup.entity.name");
			criteria.setValue(fieldGroup_entity_name);
			junction.add(criteria);
		}
		if (fieldGroup_name != null && !fieldGroup_name.isEmpty()) {
			BasicCriteria criteria = new BasicCriteria();
			criteria.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
			criteria.setField("fieldGroup.name");
			criteria.setValue(fieldGroup_name);
			junction.add(criteria);
		}
		if (read != null && !read.isEmpty()) {
			BasicCriteria criteria = new BasicCriteria();
			criteria.setOperation(CriteriaConstants.BOOLEAN_OPERATOR_EQUAL);
			criteria.setField("read");
			criteria.setValue(read);
			junction.add(criteria);
		}
		if (write != null && !write.isEmpty()) {
			BasicCriteria criteria = new BasicCriteria();
			criteria.setOperation(CriteriaConstants.BOOLEAN_OPERATOR_EQUAL);
			criteria.setField("write");
			criteria.setValue(write);
			junction.add(criteria);
		}
		if (export != null && !export.isEmpty()) {
			BasicCriteria criteria = new BasicCriteria();
			criteria.setOperation(CriteriaConstants.BOOLEAN_OPERATOR_EQUAL);
			criteria.setField("export");
			criteria.setValue(export);
			junction.add(criteria);
		}

		List<FieldGroupProfile> beans = dao.load(sortProperty, sortOrder, junction);
		List<FieldGroupProfile> securedBeans = filter.<FieldGroupProfile> toSecure(beans);
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
	 * @param dao the FieldGroupProfile Dao
	 */
	public void setDao(FieldGroupProfileDao dao) {
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

}
