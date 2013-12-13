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
public class ImogSecurityDetailService implements UserDetailsService {

	private Logger logger = Logger.getLogger(ImogSecurityDetailService.class);

	private GenericHandler genericHandler;

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
				List<EntityProfile> ep = profile.getEntityProfiles();
				if (ep != null) {
					logger.info("Entity profiles: " + ep.toString());
				}
			}
			for (Profile profile : profiles) {
				List<FieldGroupProfile> fgp = profile.getFieldGroupProfiles();
				if (fgp != null) {
					logger.info("Field group profiles: " + fgp.toString());
				}
			}
		}
		genericHandler.evict(actor);
		HttpSessionUtil.getHttpSession().setAttribute(ServerConstants.SESSION_USER, actor);
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

}
