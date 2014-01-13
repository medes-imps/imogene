package org.imogene.lib.sync.security;

import java.util.List;

import org.imogene.lib.common.entity.ImogActor;

/**
 * Describes the services relative to the user access rights
 * 
 * @author MEDES-IMPS
 */
public interface UserAccessControl {

	/**
	 * Get the list of the entity that this user could synchronizable in both
	 * way
	 * 
	 * @param userId the user is
	 * @return the list of entity id
	 */
	public List<String> getSynchronizable(String userId);

	/**
	 * 
	 * @param userId
	 * @param entity
	 * @return
	 */
	public Object getRestriction(String userId, String entity);

	/**
	 * 
	 * @param user
	 * @param password
	 * @return
	 */
	public ImogActor authenticate(String user, String password);
}
