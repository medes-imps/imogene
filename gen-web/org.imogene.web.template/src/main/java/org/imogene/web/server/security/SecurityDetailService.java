package org.imogene.web.server.security;

import java.util.List;

import org.apache.log4j.Logger;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.profile.EntityProfile;
import org.imogene.lib.common.profile.FieldGroupProfile;
import org.imogene.lib.common.profile.Profile;
import org.imogene.web.server.handler.GenericHandler;
import org.imogene.web.server.util.HttpSessionUtil;
import org.imogene.web.server.util.ServerConstants;
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
		List<Profile> profiles = actor.getProfiles();
		if (profiles != null) {
			for (Profile profile : profiles) {
				List<EntityProfile> entities = profile.getEntityProfiles();
				if (entities != null) {
					for (EntityProfile entity : entities) {
						logger.info("has profile : " + entity.getId());
					}
				}
				List<FieldGroupProfile> fieldGroups = profile.getFieldGroupProfiles();
				if (fieldGroups != null) {
					for (FieldGroupProfile fieldGroup : fieldGroups) {
						logger.info("has profile : " + fieldGroup.getId());
					}
				}
			}
		}
		AccessPolicy securityPolicy = accessPolicyFactory.create(actor);
		genericHandler.detach(actor);
		HttpSessionUtil.getHttpSession().setAttribute(ServerConstants.SESSION_USER, actor);
		HttpSessionUtil.getHttpSession().setAttribute(ServerConstants.SESSION_SECURITY_POLICY, securityPolicy);
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

}
