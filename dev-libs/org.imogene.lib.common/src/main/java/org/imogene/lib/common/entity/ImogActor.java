package org.imogene.lib.common.entity;

import java.util.Date;
import java.util.Set;

import org.imogene.lib.common.role.ImogRole;
import org.imogene.lib.common.sync.entity.SynchronizableEntity;

/**
 * This interface describes an Imogene actor, which is an Imogene bean that is
 * an application user too.
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

	/** Get the roles associated to this user */
	public Set<ImogRole> getRoles();

	/** Set the roles associated to this user */
	public void setRoles(Set<ImogRole> pRoles);

	/** Add a role to the user assigned list of roles */
	public void addRole(ImogRole role);

	/** Get the synchronizable entities associated to this user */
	public Set<SynchronizableEntity> getSynchronizables();

	/** Set the synchronizable entities associated to this user **/
	public void setSynchronizables(Set<SynchronizableEntity> syncs);

	/**
	 * Add an entity type to the list of entity types that the user can
	 * synchronize
	 */
	public void addSynchronizable(SynchronizableEntity synchronizable);

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

	/**
	 * Check if the specified role is already assigned to the user
	 * @param id the role id
	 * @return true if the role is already assigned
	 */
	public boolean isAssignedRole(String id);
}
