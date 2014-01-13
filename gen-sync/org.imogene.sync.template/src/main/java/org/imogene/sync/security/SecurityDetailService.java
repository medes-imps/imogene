package org.imogene.sync.security;

import org.apache.log4j.Logger;
import org.imogene.lib.common.dao.GenericDao;
import org.imogene.lib.common.entity.ImogActor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

/**
 * Manages the application access through spring security
 * 
 * @author MEDES-IMPS
 */
public class SecurityDetailService implements UserDetailsService {

	private Logger logger = Logger.getLogger(SecurityDetailService.class);

	@Autowired
	private GenericDao genericDao;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException, DataAccessException {
		logger.debug("Validating auhtentication for user: " + login);
		ImogActor actor = genericDao.loadFromLogin(login);
		if (actor != null) {
			return setSessionUser(actor);
		}
		return null;
	}

	/**
	 * Sets the current user in session
	 * 
	 * @param actor the current user
	 * @return
	 */
	private ImogUserDetails setSessionUser(ImogActor actor) {
		genericDao.detach(actor);
		return new ImogUserDetails(actor);
	}

	/**
	 * Setter for bean injection
	 * 
	 * @param genericHandler
	 */
	public void setGenericDao(GenericDao genericDao) {
		this.genericDao = genericDao;
	}

}
