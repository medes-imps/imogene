package org.imogene.web.client.util;

import java.util.List;

import org.imogene.web.shared.constants.ProfileConstants;
import org.imogene.web.shared.proxy.ImogActorProxy;
import org.imogene.web.shared.proxy.ProfileProxy;

/**
 * 
 * @author MEDES-IMPS
 */
public class ProfileUtil {

	/**
	 * 
	 * @return
	 */
	public static boolean isAdmin() {
		for (ProfileProxy profile : LocalSession.get().getCurrentUser().getProfiles()) {
			if (profile.getId().equals(ProfileConstants.ADMINISTRATOR))
				return true;
		}
		return false;
	}

	/**
	 * 
	 * @param actor
	 * @param profiles
	 * @return
	 */
	public static boolean hasProfile(ImogActorProxy actor, List<ProfileProxy> profiles) {
		if (actor != null && actor.getProfiles() != null && profiles != null) {
			for (ProfileProxy role : actor.getProfiles()) {
				if (profiles.contains(role))
					return true;
			}
		}
		return false;
	}

}
