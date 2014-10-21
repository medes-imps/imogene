package org.imogene.web.server.util;

import java.util.List;

import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.profile.Profile;

/**
 * @author MEDES-IMPS
 */
public class ProfileUtil {

	/**
	 * @return
	 */
	public static boolean isAdmin() {
		for (Profile profile : HttpSessionUtil.getCurrentUser().getProfiles()) {
			if (profile.getId().equals(Profile.ADMINISTRATOR))
				return true;
		}
		return false;
	}

	public static boolean isAdmin(ImogActor actor) {
		for (Profile profile : actor.getProfiles()) {
			if (profile.getId().equals(Profile.ADMINISTRATOR))
				return true;
		}
		return false;
	}

	/**
	 * @param actor
	 * @param profiles
	 * @return
	 */
	public static boolean hasProfile(ImogActor actor, List<Profile> profiles) {
		if (actor != null && actor.getProfiles() != null && profiles != null) {
			for (Profile role : actor.getProfiles()) {
				if (profiles.contains(role))
					return true;
			}
		}
		return false;
	}

}
