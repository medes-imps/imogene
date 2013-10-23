package org.imogene.lib.sync.handler;

import java.util.Date;
import java.util.List;

import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.entity.ImogBean;

/**
 * Interface that describes an Entity manager that enables to load and store an
 * entity after data access permission checking.
 * 
 * @author MEDES-IMPS
 */
public interface ImogBeanHandler<T extends ImogBean> {

	/**
	 * Load an ImogBean from the database
	 * 
	 * @param entityId the Entity id
	 * @return Entity or null if it is not in the database
	 */
	public T loadEntity(String entityId);
	
	/**
	 * Load an ImogBean from the database
	 * 
	 * @param entityId the Entity id
	 * @param user the user which is performing the data access
	 * @return Entity or null if it is not in the database
	 */
	public T loadEntity(String entityId, ImogActor user);

	/**
	 * Gets a list of entities whose fields are filtered depending on the user
	 * privileges and user defined clients filters
	 * 
	 * @param user current user whose access has to be filtered (if null, no
	 *            filtering)
	 * @param terminalId Id of the terminal the current user is using
	 * @return list of entities
	 */
	public List<T> loadEntities(ImogActor user, String terminalId);

	/**
	 * Load the Entities modified since the specified date depending on the user
	 * privileges
	 * 
	 * @param date the modification date
	 * @param user the user which is performing the data access
	 * @return List of Entities modified since the specified date
	 */
	public List<T> loadModified(Date date, ImogActor user);
	
	/**
	 * Gets an entity that has been modified since a certain date
	 * 
	 * @param entityId the entity id
	 * @param date date since modifications are searched
	 * @param user current user whose access has to be filtered (if null, no
	 *            filtering)
	 * @return an entity
	 */
	public T loadModified(String entityId, Date date, ImogActor user);
	
	/**
	 * Load the Entities uploaded since the specified date depending on the user
	 * privileges and user defined clients filters
	 * 
	 * @param date the modification date
	 * @param user the user which is performing the data access
	 * @param terminalId Id of the terminal the current user is using
	 * @return List of Entities modified since the specified date
	 */
	public List<T> loadUploaded(Date date, ImogActor user, String terminalId);
	
	/**
	 * Gets an entity that has been uploaded since a certain date
	 * 
	 * @param entityId the entity id
	 * @param date date since modifications are searched
	 * @param user current user whose access has to be filtered (if null, no
	 *            filtering)
	 * @return an entity
	 */
	public T loadUploaded(String entityId, Date date, ImogActor user);

	/**
	 * Create an empty entity with this id, waiting for the update incoming in
	 * this synchronization session.
	 * 
	 * @param id the Entity id
	 * @return the new entity created
	 */
	public T createNewEntity(String id);
	
	/**
	 * Store or update an entity in the database
	 * 
	 * @param entity the entity to save
	 * @param user the user which is performing the data access
	 * @param neu indicates whether the entity is new or not
	 */
	public void saveOrUpdate(T entity, ImogActor user, boolean neu);
	
	/**
	 * Merge the state of the given entity into the current persistence context.
	 * 
	 * @param entity the entity to merge
	 * @param neu indicates whether the entity is new or not
	 */
	public T merge(T entity, boolean neu);
	
}
