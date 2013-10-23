package org.imogene.admin.server.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.imogene.lib.common.constants.CommonConstants;
import org.imogene.lib.common.criteria.BasicCriteria;
import org.imogene.lib.common.criteria.ImogConjunction;
import org.imogene.lib.common.criteria.ImogJunction;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.sync.entity.SynchronizableEntity;
import org.imogene.lib.common.sync.entity.SynchronizableEntityDao;
import org.imogene.web.server.util.HttpSessionUtil;
import org.imogene.web.server.util.ServerConstants;
import org.springframework.transaction.annotation.Transactional;

/**
 * A data handler for the SynchronizableEntity beans
 * @author Medes-IMPS
 */
public class SynchronizableEntityHandler {

	private SynchronizableEntityDao dao;

	/**
	 * Loads the entity (secured) with the specified id
	 * @param entityId the entity id
	 * @return the entity or null
	 */
	@Transactional(readOnly = true)
	public SynchronizableEntity findById(String entityId) {
		return dao.load(entityId);
	}

	/**
	 * Saves or updates the entity
	 * @param entity the entity to be saved or updated
	 * @param isNew true if it is a new entity added for the first time.
	 */
	@Transactional
	public void save(SynchronizableEntity entity, boolean isNew) {

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
	 * Lists the entities of type SynchronizableEntity
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @return list of synchronizableEntity
	 */
	@Transactional(readOnly = true)
	public List<SynchronizableEntity> listSynchronizableEntity(int i, int j, String sortProperty, boolean sortOrder) {
		return dao.load(i, j, sortProperty, sortOrder);
	}

	/**
	 * Lists the entities of type SynchronizableEntity
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param criterions request criteria
	 * @return list of synchronizableEntity
	 */
	@Transactional(readOnly = true)
	public List<SynchronizableEntity> listSynchronizableEntity(int i, int j, String sortProperty, boolean sortOrder, ImogJunction criterions) {
		return dao.load(i, j, sortProperty, sortOrder, criterions);
	}

	/**
	 * Lists the entities of type SynchronizableEntity
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param criterions request criteria
	 * @return list of synchronizableEntity
	 */
	@Transactional(readOnly = true)
	public List<SynchronizableEntity> listSynchronizableEntity(int i, int j, String sortProperty, boolean sortOrder, List<BasicCriteria> criterions) {
		ImogJunction junctionForCrit = new ImogConjunction();
		if (criterions != null) {
			for (BasicCriteria crit : criterions)
				junctionForCrit.add(crit);
		}

		return dao.load(i, j, sortProperty, sortOrder, junctionForCrit);
	}

	/**
	 * Lists the non affected entities of type SynchronizableEntity
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param criterion request criteria
	 * @param property the property which is not affected
	 * @return list of synchronizableEntity
	 */
	@Transactional(readOnly = true)
	public List<SynchronizableEntity> listNonAffectedSynchronizableEntity(int i, int j, String sortProperty, boolean sortOrder,
			ImogJunction criterions, String property) {
		return dao.loadNonAffected(i, j, sortProperty, sortOrder, property, criterions);
	}

	/**
	 * Lists the non affected entities of type SynchronizableEntity
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param property the property which is not affected
	 * @return list of synchronizableEntity
	 */
	@Transactional(readOnly = true)
	public List<SynchronizableEntity> listNonAffectedSynchronizableEntity(int i, int j, String sortProperty, boolean sortOrder, String property) {
		return listNonAffectedSynchronizableEntity(i, j, sortProperty, sortOrder, null, property);
	}

	/**
	 * Used when SynchronizableEntity is involved in a Relation 1 <-> 1
	 * Association and is the ReverseRelationField of the Relation
	 * Return all instance of SynchronizableEntity non affected
	 * regarding specified property.
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param ImogJunction request criteria
	 * @param property the property which is not affected
	 * @return list of synchronizableEntity
	 */
	@Transactional(readOnly = true)
	public List<SynchronizableEntity> listNonAffectedSynchronizableEntityReverse(int i, int j, String sortProperty, boolean sortOrder,
			ImogJunction criterions, String property) {
		return dao.loadNonAffectedReverse(i, j, sortProperty, sortOrder, property, criterions);
	}

	/**
	 * Used when SynchronizableEntity is involved in a Relation 1 <-> 1
	 * Association and is the ReverseRelationField of the Relation
	 * Return all instance of SynchronizableEntity non affected
	 * regarding specified property.
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param property the property which is not affected
	 * @return list of synchronizableEntity
	 */
	@Transactional(readOnly = true)
	public List<SynchronizableEntity> listNonAffectedSynchronizableEntityReverse(int i, int j, String sortProperty, boolean sortOrder, String property) {
		return listNonAffectedSynchronizableEntityReverse(i, j, sortProperty, sortOrder, null, property);
	}

	/**
	 * Gets an empty list of SynchronizableEntity
	 * @return an empty list of SynchronizableEntity
	 */
	@Transactional(readOnly = true)
	public List<SynchronizableEntity> getSynchronizableEntityEmptyList() {
		return new ArrayList<SynchronizableEntity>();
	}

	/**
	 * Counts the number of synchronizableEntity in the database
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countSynchronizableEntity() {
		return countSynchronizableEntity(null);
	}

	/**
	 * Counts the number of synchronizableEntity in the database,
	 * that match the criteria
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countSynchronizableEntity(ImogJunction criterions) {
		return dao.count(criterions);
	}

	/**
	 * Counts the number of non affected synchronizableEntity entities in the database
	 * @param property the property which is not affected
	 * @param criterion request criteria
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedSynchronizableEntity(String property, ImogJunction criterions) {
		return dao.countNonAffected(property, criterions);
	}

	/**
	 * Counts the number of non affected synchronizableEntity entities in the database
	 * @param property the property which is not affected
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedSynchronizableEntity(String property) {
		return countNonAffectedSynchronizableEntity(property, null);
	}

	/**
	 * Counts the number of non affected synchronizableEntity entities in the database
	 * @param property the property which is not affected
	 * @param criterion request criteria
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedSynchronizableEntityReverse(String property, ImogJunction criterions) {
		return dao.countNonAffectedReverse(property, criterions);
	}

	/**
	 * Counts the number of non affected synchronizableEntity entities in the database
	 * @param property the property which is not affected
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedSynchronizableEntityReverse(String property) {
		return countNonAffectedSynchronizableEntityReverse(property, null);
	}

	/**
	 * Deletes a group of entities identified by their IDs
	 * @param ids Entities to delete IDs
	 * @return The list of deleted entities IDs
	 */
	@Transactional
	public void delete(Set<SynchronizableEntity> entities) {
		if (entities != null) {
			for (SynchronizableEntity entity : entities)
				delete(entity);
		}
	}

	/**
	 * Removes the specified entity from the database
	 * @param entity The entity to be deleted
	 */
	@Transactional
	public void delete(SynchronizableEntity entity) {
		// dao.delete(entity);
	}

	/**
	 * Setter for bean injection
	 * @param dao the SynchronizableEntity Dao
	 */
	public void setDao(SynchronizableEntityDao dao) {
		this.dao = dao;
	}

}
