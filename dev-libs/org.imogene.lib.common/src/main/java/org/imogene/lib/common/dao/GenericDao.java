package org.imogene.lib.common.dao;

import java.util.List;

import org.imogene.lib.common.entity.ImogActor;

/**
 * Generic dao
 * 
 * @author Medes-IMPS
 */
public interface GenericDao {

	/**
	 * Gets the list of entity instances for the specified class
	 * 
	 * @param clazz the entity class
	 * @return the list of entity instances
	 */
	public <T> List<T> load(Class<T> clazz);

	/**
	 * Loads an entity instance
	 * 
	 * @param clazz the entity class
	 * @param id the instance id
	 * @return the entity instance if it exists
	 */
	public <T> T load(Class<T> clazz, String id);

	/**
	 * Loads an entity instance
	 * 
	 * @param className the entity class name
	 * @param id the instance id
	 * @return the entity instance if it exists
	 */
	public Object load(String className, String id);

	/**
	 * Saves or updates the specified entity instance
	 * 
	 * @param object the entity instance to be saved or updated
	 */
	public <T> void saveOrUpdate(T object);

	/**
	 * Counts the number of entity instances for the specified class
	 * 
	 * @param clazz the entity class
	 * @return the number of entity instances
	 */
	public <T> long count(Class<T> clazz);

	/**
	 * Check if the instance belongs to the current persistence context.
	 * 
	 * @param object
	 * @return true if the instance belongs to the current persistence context.
	 */
	public <T> boolean contains(T object);

	/**
	 * Detach an entity instance from the persistence session
	 * 
	 * @param o the entity instance to be evicted from persistence session
	 */
	public void detach(Object o);

	/**
	 * Merge the state of the given entity into the current persistence context.
	 * 
	 * @param o entity -
	 * @return the instance that the state was merged to
	 */
	public <T> T merge(T o);

	/**
	 * Load an actor with the given login
	 * 
	 * @param login the login to search
	 * @return the actor or {@code null} if not found
	 */
	public ImogActor loadFromLogin(String login);

}
