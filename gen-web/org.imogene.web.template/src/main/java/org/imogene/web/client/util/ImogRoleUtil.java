package org.imogene.web.client.util;

import java.util.List;

import org.imogene.web.shared.constants.ImogRoleConstants;
import org.imogene.web.shared.proxy.ImogActorProxy;
import org.imogene.web.shared.proxy.ImogRoleProxy;


/**
 * 
 * @author MEDES-IMPS
 */
public class ImogRoleUtil {
	
	/**
	 * 
	 * @return
	 */
	public static boolean isAdmin() {
		for (ImogRoleProxy role : LocalSession.get().getCurrentUser().getRoleList()) {
			if (role.getId().equals(ImogRoleConstants.ADMINISTRATOR))
				return true;
		}
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public static boolean canDelete() {
		for (ImogRoleProxy role : LocalSession.get().getCurrentUser().getRoleList()) {
			if (isAdmin() || role.getId().equals(ImogRoleConstants.DELETE))
				return true;
		}
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public static boolean canExport() {
		for (ImogRoleProxy role : LocalSession.get().getCurrentUser()
				.getRoleList()) {
			if (isAdmin() || role.getId().equals(ImogRoleConstants.EXPORT))
				return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param actor
	 * @param roles
	 * @return
	 */
	public static boolean hasRole(ImogActorProxy actor, List<ImogRoleProxy> roles) {
		
		if(actor!=null && actor.getRoleList()!=null && roles!=null) {			
			for(ImogRoleProxy role:actor.getRoleList()) {				
				if(roles.contains(role))
					return true;				
			}	
		}	
		return false;
	}

}
