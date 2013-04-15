package org.imogene.lib.common.dao;

import java.util.List;

import org.imogene.lib.common.entity.ImogActor;

public interface ImogActorDao<T extends ImogActor> extends ImogBeanDao<T> {

	/**
	 * Gets the associated actor from the login id.
	 * @param login the actor login
	 * @return the actor if it exists or null
	 */
	public List<T> loadFromLogin(String login);

}
