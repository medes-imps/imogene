package org.imogene.lib.common.dao;

import java.util.Date;
import java.util.List;

import org.imogene.lib.common.criteria.ImogCriterion;
import org.imogene.lib.common.entity.ImogBean;

/**
 * Manages persistence for ImogBean
 * @author MEDES-IMPS
 */
public interface ImogBeanDao<T extends ImogBean> {

	/**
	 * Load all the entities
	 * @return list of all the entities in the database
	 */
	public List<T> load();

	/**
	 * Load the entity with the specified id
	 * @param id the entity id
	 * @return the entity or null
	 */
	public T load(String id);
	
	/**
	 * Load the entity with the specified id
	 * @param id the entity id
	 * @return the entity or null
	 */
	public T getById(String id);

	/**
	 * Load the entities with the specified ids
	 * @return list of entities with the specified ids
	 */
	public List<T> load(List<String> ids);

	/**
	 * Load the entity with the specified id
	 * @param id the entity id
	 * @param criterion request criteria
	 * @return the entity or null
	 */
	public T load(String id, ImogCriterion criterion);

	/**
	 * List entities of type ImogBean
	 * @param criterion request criteria
	 * @return list of ImogBean
	 */
	public List<T> load(ImogCriterion criterion);

	/**
	 * List entities of type ImogBean
	 * @param property the property used to sort
	 * @param asc the sort order
	 * @param criterion request criteria
	 * @return list of ImogBean
	 */
	public List<T> load(String property, boolean asc, ImogCriterion criterion);

	/**
	 * List entities of type ImogBean
	 * @param first first index to retrieve
	 * @param max nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @return list of ImogBean
	 */
	public List<T> load(int first, int max, String sortProperty, boolean sortOrder);

	/**
	 * List entities of type ImogBean
	 * @param first first index to retrieve
	 * @param max nb of items to retrieve
	 * @param property the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param criterion request criteria
	 * @return list of ImogBean
	 */
	public List<T> load(int first, int max, String property, boolean sortOrder, ImogCriterion criterion);

	/**
	 * List entities modified after the given date
	 * @param date modification date
	 * @return list of entities
	 */
	public List<T> loadModified(Date date);

	/**
	 * List entities modified after the given date and criteria
	 * @param date modification date
	 * @param criterion request criteria
	 * @return list of entities
	 */
	public List<T> loadModified(Date date, ImogCriterion criterion);

	/**
	 * Entity modified after the given date with the given id
	 * @param date modification date
	 * @param id
	 * @return the entity or null
	 */
	public T loadModified(Date date, String id);

	/**
	 * Entity modified after the given date and id and criteria
	 * @param date modification date
	 * @param criterion request criteria
	 * @param id the entity id
	 * @return the entity or null
	 */
	public T loadModified(Date date, ImogCriterion criterion, String id);

	/**
	 * List entities uploaded after the given date
	 * @param date uploaded date
	 * @return list of entities
	 */
	public List<T> loadUploaded(Date date);

	/**
	 * List entities uploaded after the given date and criteria
	 * @param date uploaded date
	 * @param criterion request criteria
	 * @return list of entities
	 */
	public List<T> loadUploaded(Date date, ImogCriterion criterion);

	/**
	 * Entity uploaded after the given date with the given id
	 * @param date uploaded date
	 * @param id
	 * @return the entity or null
	 */
	public T loadUploaded(Date date, String id);

	/**
	 * Entity uploaded after the given date and id and criteria
	 * @param date uploaded date
	 * @param criterion request criteria
	 * @param id the entity id
	 * @return the entity or null
	 */
	public T loadUploaded(Date date, ImogCriterion criterion, String id);

	/**
	 * Count number of ImogBean in the database
	 * @return the count
	 */
	public long count();

	/**
	 * Count number of ImogBean in the database, that matches the criteria
	 * @return the count
	 */
	public long count(ImogCriterion criterion);

	/**
	 * Delete the specified entity
	 * @param entity the entity to delete
	 */
	public void delete(T entity);

	/**
	 * List all the non affected ImogBean
	 * @param property the property used by the relation
	 * @param criterion the request criteria
	 * @return a list of ImogBean
	 */
	public List<T> loadNonAffected(String property, ImogCriterion criterion);

	/**
	 * List the non affected ImogBean
	 * @param first the first entities to retrieve
	 * @param max the number of entities to retrieve
	 * @param sortProperty the property used to sort
	 * @param asc the sort order
	 * @param property the property used by the relation
	 * @param criterion the request criteria
	 * @return a list of ImogBean
	 */
	public List<T> loadNonAffected(int first, int max, String sortProperty, boolean asc, String property,
			ImogCriterion criterion);

	/**
	 * List the non affected ImogBean for th reverse relations
	 * @param first the first entities to retrieve
	 * @param max the number of entities to retrieve
	 * @param sortProperty the property used to sort
	 * @param asc the sort order
	 * @param property the property used by the relation
	 * @param criterion the request criteria
	 * @return a list of ImogBean
	 */
	public List<T> loadNonAffectedReverse(int first, int max, String sortProperty, boolean asc, String property,
			ImogCriterion criterion);

	/**
	 * List ???
	 * @param property the property used for the relation
	 * @param id the entity id
	 * @return a list of ImogBean
	 */
	public List<T> loadAffectedCardNProperty(String property, String id);

	/**
	 * Count the non affected ImogBean
	 * @param property the property used by the relation
	 * @param criterion the request criterion
	 * @return the count
	 */
	public long countNonAffected(String property, ImogCriterion criterion);

	/**
	 * Count the non affected ImogBean reverse relation
	 * @param property the property used by the relation
	 * @param criterion the request criterion
	 * @return the count
	 */
	public long countNonAffectedReverse(String property, ImogCriterion criterion);

	/**
	 * Save or update the entity
	 * @param entity the entity to save or update
	 * @param neu indicates whether the entity is new or not (used by the AOP intercepter)
	 */
	public void saveOrUpdate(T entity, boolean neu);

	/**
	 * Merge the state of the given entity into the current persistence context.
	 * @param entity the entity to merge
	 * @param neu indicates whether the entity is new or not (used by the AOP intercepter)
	 */
	public T merge(T entity, boolean neu);

	/**
	 * Remove the given entity from the persistence context
	 * @param entity the entity to detach
	 */
	public void detach(T entity);

	/**
	 * Refresh the state of the instance from the database, overwriting changes
	 * made to the entity, if any.
	 * @param entity the entity to refresh
	 */
	public void refresh(T entity);

	/**
	 * Delete all the entities
	 */
	public void delete();

	/**
	 * Clear the persistence context, causing all managed entities to become
	 * detached.
	 */
	public void clear();

	/**
	 * Synchronize the persistence context to the underlying database.
	 */
	public void flush();

}
