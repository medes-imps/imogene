package org.imogene.web.server.security;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.imogene.lib.common.constants.UserActionConstants;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.entity.ImogActorImpl;
import org.imogene.lib.common.security.AccessPolicyFactory;
import org.imogene.lib.common.useraction.UserAction;
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
 * @author MEDES-IMPS
 */
public class SecurityDetailService implements UserDetailsService {

	private Logger logger = Logger.getLogger(SecurityDetailService.class);

	private GenericHandler genericHandler;

	private HandlerHelper handlerHelper;

	private AccessPolicyFactory accessPolicyFactory;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException, DataAccessException {
		logger.debug("Validating authentication for user: " + login);
		ImogActorImpl actor = (ImogActorImpl) genericHandler.loadFromLogin(login);
		if (actor != null) {
			Date loginDate = new Date(System.currentTimeMillis());
			saveLastLoginDate(actor, loginDate);
			saveLoginAction(actor, loginDate);
			return setSessionUser(actor);
		}
		return null;
	}

	/**
	 * Configure the last login date in the application
	 * and saves the new last login date in the database
	 * @param actor
	 */
	private void saveLastLoginDate(ImogActorImpl actor, Date loginDate) {
		HttpSessionUtil.setLastLoginDate(actor.getLastLoginDate());
		actor.setLastLoginDate(loginDate);
		genericHandler.save(actor);
		genericHandler.flush();
	}
	
	/**
	 * Saves the login action in the application action journal in database
	 * @param actor
	 */
	private void saveLoginAction(ImogActorImpl actor, Date loginDate) {
		UserAction action = new UserAction();
		action.setId(UUID.randomUUID().toString());
		action.setActionDate(loginDate);
		action.setUserId(actor.getId());
		action.setActionType(UserActionConstants.USERACTION_TYPE_LOGIN);
		genericHandler.save(action);
		genericHandler.flush();
	}

	/**
	 * Sets the current user in session
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
	 * @param genericHandler
	 */
	public void setGenericHandler(GenericHandler genericHandler) {
		this.genericHandler = genericHandler;
	}

	/**
	 * Setter for bean injection
	 * @param securityPolicyFactory
	 */
	public void setAccessPolicyFactory(AccessPolicyFactory accessPolicyFactory) {
		this.accessPolicyFactory = accessPolicyFactory;
	}

	/**
	 * Setter for bean injection
	 * @param handlerHelper
	 */
	public void setHandlerHelper(HandlerHelper handlerHelper) {
		this.handlerHelper = handlerHelper;
	}
}
