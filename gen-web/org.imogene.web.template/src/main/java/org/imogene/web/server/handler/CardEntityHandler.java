package org.imogene.web.server.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.imogene.lib.common.criteria.BasicCriteria;
import org.imogene.lib.common.criteria.ImogConjunction;
import org.imogene.lib.common.criteria.ImogJunction;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.model.CardEntity;
import org.imogene.lib.common.model.CardEntityDao;
import org.imogene.web.server.util.HttpSessionUtil;
import org.imogene.web.server.util.SystemUtil;
import org.springframework.transaction.annotation.Transactional;

/**
 * A data handler for the CardEntity beans
 * 
 * @author Medes-IMPS
 */
public class CardEntityHandler {

	private CardEntityDao dao;
	private SystemUtil systemUtil;

	/**
	 * Loads the entity (secured) with the specified id
	 * 
	 * @param entityId the entity id
	 * @return the entity or null
	 */
	@Transactional(readOnly = true)
	public CardEntity findById(String entityId) {
		return dao.load(entityId);
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
	 * Lists the entities of type CardEntity
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @return list of CardEntity
	 */
	@Transactional(readOnly = true)
	public List<CardEntity> listCardEntity(int i, int j, String sortProperty, boolean sortOrder) {
		return dao.load(i, j, sortProperty, sortOrder);
	}

	/**
	 * Lists the entities of type CardEntity
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param criterions request criteria
	 * @return list of CardEntity
	 */
	@Transactional(readOnly = true)
	public List<CardEntity> listCardEntity(int i, int j, String sortProperty, boolean sortOrder, ImogJunction criterions) {
		return dao.load(i, j, sortProperty, sortOrder, criterions);
	}

	/**
	 * Lists the entities of type CardEntity
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param criterions request criteria
	 * @return list of CardEntity
	 */
	@Transactional(readOnly = true)
	public List<CardEntity> listCardEntity(int i, int j, String sortProperty, boolean sortOrder,
			List<BasicCriteria> criterions) {
		ImogJunction junctionForCrit = new ImogConjunction();
		if (criterions != null) {
			for (BasicCriteria crit : criterions)
				junctionForCrit.add(crit);
		}

		return dao.load(i, j, sortProperty, sortOrder, junctionForCrit);
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
	 * @return list of CardEntity
	 */
	@Transactional(readOnly = true)
	public List<CardEntity> listNonAffectedCardEntity(int i, int j, String sortProperty, boolean sortOrder,
			ImogJunction criterions, String property) {
		return dao.loadNonAffected(i, j, sortProperty, sortOrder, property, criterions);
	}

	/**
	 * Lists the non affected entities of type CardEntity
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param property the property which is not affected
	 * @return list of CardEntity
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
	 * @return list of CardEntity
	 */
	@Transactional(readOnly = true)
	public List<CardEntity> listNonAffectedCardEntityReverse(int i, int j, String sortProperty, boolean sortOrder,
			ImogJunction criterions, String property) {
		return dao.loadNonAffectedReverse(i, j, sortProperty, sortOrder, property, criterions);
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
	 * @return list of CardEntity
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
	 * Counts the number of CardEntity in the database
	 * 
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countCardEntity() {
		return countCardEntity(null);
	}

	/**
	 * Counts the number of CardEntity in the database, that match the criteria
	 * 
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countCardEntity(ImogJunction criterions) {
		return dao.count(criterions);
	}

	/**
	 * Counts the number of non affected CardEntity entities in the database
	 * 
	 * @param property the property which is not affected
	 * @param criterion request criteria
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedCardEntity(String property, ImogJunction criterions) {
		return dao.countNonAffected(property, criterions);
	}

	/**
	 * Counts the number of non affected CardEntity entities in the database
	 * 
	 * @param property the property which is not affected
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedCardEntity(String property) {
		return countNonAffectedCardEntity(property, null);
	}

	/**
	 * Counts the number of non affected CardEntity entities in the database
	 * 
	 * @param property the property which is not affected
	 * @param criterion request criteria
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedCardEntityReverse(String property, ImogJunction criterions) {
		return dao.countNonAffectedReverse(property, criterions);
	}

	/**
	 * Counts the number of non affected CardEntity entities in the database
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
		// dao.delete(entity);
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
	 * Setter for bean injection.
	 * 
	 * @param systemUtil
	 */
	public void setSystemUtil(SystemUtil systemUtil) {
		this.systemUtil = systemUtil;
	}

}
