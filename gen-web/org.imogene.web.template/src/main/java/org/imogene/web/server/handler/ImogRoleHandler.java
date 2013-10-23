package org.imogene.web.server.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.imogene.lib.common.constants.CommonConstants;
import org.imogene.lib.common.criteria.BasicCriteria;
import org.imogene.lib.common.criteria.ImogConjunction;
import org.imogene.lib.common.criteria.ImogJunction;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.role.ImogRole;
import org.imogene.lib.common.role.RoleActorDao;
import org.imogene.web.server.util.HttpSessionUtil;
import org.imogene.web.server.util.ServerConstants;
import org.springframework.transaction.annotation.Transactional;

/**
 * A data handler for the ImogRole beans
 * @author Medes-IMPS
 */
public class ImogRoleHandler {

	private RoleActorDao dao;

	/**
	 * Loads the entity (secured) with the specified id
	 * @param entityId the entity id
	 * @return the entity or null
	 */
	@Transactional(readOnly = true)
	public ImogRole findById(String entityId) {
		return dao.load(entityId);
	}

	/**
	 * Saves or updates the entity
	 * @param entity the entity to be saved or updated
	 * @param isNew true if it is a new entity added for the first time.
	 */
	@Transactional
	public void save(ImogRole entity, boolean isNew) {

		ImogActor actor = (ImogActor) HttpSessionUtil.getHttpSession().getAttribute(ServerConstants.SESSION_USER);

		if (entity != null) {

			if (isNew) {
				entity.setCreated(new Date(System.currentTimeMillis()));
				entity.setCreatedBy(actor.getLogin());
			}

			entity.setModified(new Date(System.currentTimeMillis()));
			entity.setModifiedBy(actor.getLogin());
			entity.setModifiedFrom(CommonConstants.IS_WEB);

			dao.saveOrUpdate(entity, isNew);
		}
	}

	/**
	 * Lists the entities of type ImogRole
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @return list of imogRole
	 */
	@Transactional(readOnly = true)
	public List<ImogRole> listImogRole(int i, int j, String sortProperty, boolean sortOrder) {
		return dao.load(i, j, sortProperty, sortOrder);
	}

	/**
	 * Lists the entities of type ImogRole
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param criterions request criteria
	 * @return list of imogRole
	 */
	@Transactional(readOnly = true)
	public List<ImogRole> listImogRole(int i, int j, String sortProperty, boolean sortOrder, ImogJunction criterions) {
		return dao.load(i, j, sortProperty, sortOrder, criterions);
	}

	/**
	 * Lists the entities of type ImogRole
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param criterions request criteria
	 * @return list of imogRole
	 */
	@Transactional(readOnly = true)
	public List<ImogRole> listImogRole(int i, int j, String sortProperty, boolean sortOrder, List<BasicCriteria> criterions) {

		ImogJunction junctionForCrit = new ImogConjunction();
		if (criterions != null) {
			for (BasicCriteria crit : criterions)
				junctionForCrit.add(crit);
		}

		return dao.load(i, j, sortProperty, sortOrder, junctionForCrit);
	}

	/**
	 * Lists the non affected entities of type ImogRole
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param criterion request criteria
	 * @param property the property which is not affected
	 * @return list of imogRole
	 */
	@Transactional(readOnly = true)
	public List<ImogRole> listNonAffectedImogRole(int i, int j, String sortProperty, boolean sortOrder, ImogJunction criterions, String property) {
		return dao.loadNonAffected(i, j, sortProperty, sortOrder, property, criterions);
	}

	/**
	 * Lists the non affected entities of type ImogRole
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param property the property which is not affected
	 * @return list of imogRole
	 */
	@Transactional(readOnly = true)
	public List<ImogRole> listNonAffectedImogRole(int i, int j, String sortProperty, boolean sortOrder, String property) {
		return listNonAffectedImogRole(i, j, sortProperty, sortOrder, null, property);
	}

	/**
	 * Used when ImogRole is involved in a Relation 1 <-> 1
	 * Association and is the ReverseRelationField of the Relation
	 * Return all instance of ImogRole non affected
	 * regarding specified property.
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param ImogJunction request criteria
	 * @param property the property which is not affected
	 * @return list of imogRole
	 */
	@Transactional(readOnly = true)
	public List<ImogRole> listNonAffectedImogRoleReverse(int i, int j, String sortProperty, boolean sortOrder, ImogJunction criterions,
			String property) {
		return dao.loadNonAffectedReverse(i, j, sortProperty, sortOrder, property, criterions);
	}

	/**
	 * Used when ImogRole is involved in a Relation 1 <-> 1
	 * Association and is the ReverseRelationField of the Relation
	 * Return all instance of ImogRole non affected
	 * regarding specified property.
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param property the property which is not affected
	 * @return list of imogRole
	 */
	@Transactional(readOnly = true)
	public List<ImogRole> listNonAffectedImogRoleReverse(int i, int j, String sortProperty, boolean sortOrder, String property) {
		return listNonAffectedImogRoleReverse(i, j, sortProperty, sortOrder, null, property);
	}

	/**
	 * Gets an empty list of ImogRole
	 * @return an empty list of ImogRole
	 */
	@Transactional(readOnly = true)
	public List<ImogRole> getImogRoleEmptyList() {
		return new ArrayList<ImogRole>();
	}

	/**
	 * Counts the number of imogRole in the database
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countImogRole() {
		return countImogRole(null);
	}

	/**
	 * Counts the number of imogRole in the database,
	 * that match the criteria
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countImogRole(ImogJunction criterions) {
		return dao.count(criterions);
	}

	/**
	 * Counts the number of non affected imogRole entities in the database
	 * @param property the property which is not affected
	 * @param criterion request criteria
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedImogRole(String property, ImogJunction criterions) {
		return dao.countNonAffected(property, criterions);
	}

	/**
	 * Counts the number of non affected imogRole entities in the database
	 * @param property the property which is not affected
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedImogRole(String property) {
		return countNonAffectedImogRole(property, null);
	}

	/**
	 * Counts the number of non affected imogRole entities in the database
	 * @param property the property which is not affected
	 * @param criterion request criteria
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedImogRoleReverse(String property, ImogJunction criterions) {
		return dao.countNonAffectedReverse(property, criterions);
	}

	/**
	 * Counts the number of non affected imogRole entities in the database
	 * @param property the property which is not affected
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedImogRoleReverse(String property) {
		return countNonAffectedImogRoleReverse(property, null);
	}

	/**
	 * Deletes a group of entities identified by their IDs
	 * @param ids Entities to delete IDs
	 * @return The list of deleted entities IDs
	 */
	@Transactional
	public void delete(Set<ImogRole> entities) {
		if (entities != null) {
			for (ImogRole entity : entities)
				delete(entity);
		}
	}

	/**
	 * Removes the specified entity from the database
	 * @param entity The entity to be deleted
	 */
	@Transactional
	public void delete(ImogRole entity) {
		// dao.delete(entity);
	}

	/**
	 * Setter for bean injection
	 * @param dao the ImogRole Dao
	 */
	public void setDao(RoleActorDao dao) {
		this.dao = dao;
	}

}
