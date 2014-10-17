package org.imogene.sync.security;

import java.util.List;

import org.apache.log4j.Logger;
import org.imogene.lib.common.dao.GenericDao;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.profile.EntityProfile;
import org.imogene.lib.common.profile.FieldGroupProfile;
import org.imogene.lib.common.profile.Profile;
import org.imogene.lib.common.security.AccessPolicyFactory;
import org.imogene.lib.sync.server.util.HttpSessionUtil;
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

	@Autowired
	private AccessPolicyFactory accessPolicyFactory;

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
		genericDao.detach(actor);
		HttpSessionUtil.setCurrentUser(actor);
		HttpSessionUtil.setAccessPolicy(accessPolicyFactory, actor);
		return new ImogUserDetails(actor);
	}

}
