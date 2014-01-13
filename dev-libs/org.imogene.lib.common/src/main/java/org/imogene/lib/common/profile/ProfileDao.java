package org.imogene.lib.common.profile;

import java.util.List;

import org.imogene.lib.common.dao.ImogBeanDao;

/**
 * Manage persistence for Profile
 * 
 * @author MEDES-IMPS
 */
public interface ProfileDao extends ImogBeanDao<Profile> {

	/* relation dependencies */

	/**
	 * List associated EntityProfile, on the field entityProfiles
	 * 
	 * @param parent the parent entity
	 * @return the list of the associated entities
	 */
	public List<EntityProfile> loadEntityProfiles(Profile parent);

	/**
	 * List associated FieldGroupProfile, on the field fieldGroupProfiles
	 * 
	 * @param parent the parent entity
	 * @return the list of the associated entities
	 */
	public List<FieldGroupProfile> loadFieldGroupProfiles(Profile parent);

}
