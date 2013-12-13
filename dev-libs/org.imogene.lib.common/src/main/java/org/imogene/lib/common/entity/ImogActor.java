package org.imogene.lib.common.entity;

import java.util.Date;
import java.util.List;

import org.imogene.lib.common.model.CardEntity;
import org.imogene.lib.common.profile.Profile;

/**
 * This interface describes an Imogene actor, which is an Imogene bean that is an application user too.
 * 
 * @author Medes-IMPS
 */
public interface ImogActor extends ImogEntity {

	/** Get the assigned login for this user */
	public String getLogin();

	/** Set the assigned login for this user */
	public void setLogin(String login);

	/** Get the assigned password for this user */
	public String getPassword();

	/** Set the assigned password for this user */
	public void setPassword(String password);

	/** Get the actor last login date */
	public Date getLastLoginDate();

	/** Set the actor last login date */
	public void setLastLoginDate(Date lastLoginDate);

	/** Get the synchronizable entities associated to this user */
	public List<CardEntity> getSynchronizables();

	/** Set the synchronizable entities associated to this user **/
	public void setSynchronizables(List<CardEntity> syncs);

	/**
	 * Add an entity type to the list of entity types that the user can synchronize
	 */
	public void addSynchronizable(CardEntity synchronizable);

	public List<Profile> getProfiles();

	public void setProfiles(List<Profile> value);

	/**
	 * @param param the Profile to add to the profiles collection
	 */
	public void addProfile(Profile param);

	/**
	 * @param param the Profile to remove from the profiles collection
	 */
	public void removeFromProfiles(Profile param);

	/** Get the local ISO code used by this actor */
	public String getNotificationLocale();

	/** Set the local ISO code used by this actor */
	public void setNotificationLocale(String iso);

	/** Get the data used to send a notification using the specified method */
	public String getNotificationData(String method);

	/** Is the notification activated for this user */
	public Boolean getBeNotified();

	/** Set if the notification is activated for this user */
	public void setBeNotified(Boolean notify);

}
