package org.imogene.lib.sync.handler;

import org.imogene.lib.common.user.DefaultUser;
import org.imogene.lib.common.user.DefaultUserDao;

/**
 * Interface of a data handler for the DefaultUser
 * 
 * @author MEDES-IMPS
 */
public interface DefaultUserHandler extends ImogActorHandler<DefaultUser> {

	/**
	 * Setter for bean injection
	 * 
	 * @param dao
	 */
	public void setDao(DefaultUserDao dao);

}
