package org.imogene.web.server.security;

import org.apache.log4j.Logger;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.security.AccessPolicyFactory;
import org.imogene.web.server.handler.GenericHandler;
import org.imogene.web.server.handler.HandlerHelper;
import org.imogene.web.server.util.HttpSessionUtil;
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

	private GenericHandler genericHandler;

	private HandlerHelper handlerHelper;

	private AccessPolicyFactory accessPolicyFactory;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException, DataAccessException {
		logger.debug("Validating auhtentication for user: " + login);
		ImogActor actor = genericHandler.loadFromLogin(login);
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
		handlerHelper.detach(actor);
		HttpSessionUtil.setCurrentUser(actor);
		HttpSessionUtil.setAccessPolicy(accessPolicyFactory, actor);
		return new ImogUserDetails(actor);
	}

	/**
	 * Setter for bean injection
	 * 
	 * @param genericHandler
	 */
	public void setGenericHandler(GenericHandler genericHandler) {
		this.genericHandler = genericHandler;
	}

	/**
	 * Setter for bean injection
	 * 
	 * @param securityPolicyFactory
	 */
	public void setAccessPolicyFactory(AccessPolicyFactory accessPolicyFactory) {
		this.accessPolicyFactory = accessPolicyFactory;
	}

	/**
	 * Setter for bean injection
	 * 
	 * @param handlerHelper
	 */
	public void setHandlerHelper(HandlerHelper handlerHelper) {
		this.handlerHelper = handlerHelper;
	}
}
