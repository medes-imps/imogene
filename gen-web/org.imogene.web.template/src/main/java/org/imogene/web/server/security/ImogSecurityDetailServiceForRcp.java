package org.imogene.web.server.security;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.web.server.identity.Identity;
import org.imogene.web.server.identity.IdentityHelper;
import org.imogene.web.server.util.HttpSessionUtil;
import org.imogene.web.server.util.ServerConstants;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Manages the application access through spring security
 * @author MEDES-IMPS
 */
public class ImogSecurityDetailServiceForRcp implements UserDetailsService {

	private Logger logger = Logger.getLogger(ImogSecurityDetailServiceForRcp.class);


	@Override
	public UserDetails loadUserByUsername(String identityId)
			throws UsernameNotFoundException, DataAccessException {

		ImogActor current = null;

		logger.debug("Validating auhtentication for user: " + identityId);

		current = validateForImogActor(identityId);
		if (current != null)
			return setSessionUser(current);

		return null;
	}

	/**
	 * Sets the current user in session
	 * @param actor the current user
	 * @return
	 */
	private ImogUserDetails setSessionUser(ImogActor actor) {
		HttpSessionUtil.getHttpSession().setAttribute(
				ServerConstants.SESSION_USER, actor);
		return new ImogUserDetails(actor);
	}


	/**
	 * Validates the login for the default actor
	 * @param login the entered login
	 * @return the DefaultUser
	 */
	private ImogActor validateForImogActor(String identityId) {
		
		HttpSession session = HttpSessionUtil.getHttpSession();
		Map<String, Identity> identities = IdentityHelper.getIdentities(session.getServletContext());
		
		Identity selIdentity = identities.get(identityId);
		if(selIdentity!=null){
			
			String type = selIdentity.getType();			
			try {
				ImogActor actor = (ImogActor)Class.forName(type).newInstance();
				actor.setId(selIdentity.getId());
				actor.setLogin(selIdentity.getLogin());
				actor.setPassword(selIdentity.getPassword());
				actor.setRoles(IdentityHelper.getRoles(selIdentity.getAssignedRoles()));				
				return actor;
				
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}		
		}
		return null;	
	}
	


}
