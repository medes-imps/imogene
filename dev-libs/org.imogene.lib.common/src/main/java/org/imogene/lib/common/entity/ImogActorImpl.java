package org.imogene.lib.common.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.imogene.lib.common.role.ImogRole;
import org.imogene.lib.common.sync.entity.SynchronizableEntity;

/**
 * Implementation of the ImogActor interface
 * @author MEDES-IMPS
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ImogActorImpl extends ImogEntityImpl implements ImogActor {

	private static final long serialVersionUID = -8318230294406190063L;

	private String login;
	private String password;
	private String notificationLocale;
	private Boolean beNotified;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "sync_entities", joinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "synchronizable_id", referencedColumnName = "id"))
	protected Set<SynchronizableEntity> synchronizables;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "imog_roles", joinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	protected Set<ImogRole> roles;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginDate;

	/**
	 * Get this actor login.
	 * @return the actor login
	 */
	@Override
	public String getLogin() {
		return login;
	}

	/**
	 * Set the actor login.
	 * @param login the actor login.
	 */
	@Override
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Get the assigned password for this actor.
	 * @return the actor password
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/**
	 * Set the actor password
	 * @param password the actor password
	 */
	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Get the notification locale ISO code to use for this actor
	 * @return the locale ISO code
	 */
	@Override
	public String getNotificationLocale() {
		return notificationLocale;
	}

	/**
	 * Set the notification locale ISO code
	 * @param notificationLocale the locale ISO code
	 */
	@Override
	public void setNotificationLocale(String notificationLocale) {
		this.notificationLocale = notificationLocale;
	}

	/**
	 * This actor can be notified ?
	 * @return true if actor can be notified.
	 */
	@Override
	public Boolean getBeNotified() {
		return beNotified;
	}

	/**
	 * Set if this actor can be notified
	 * @param beNotified true if it can be notified
	 */
	@Override
	public void setBeNotified(Boolean beNotified) {
		this.beNotified = beNotified;
	}

	@Override
	public Set<SynchronizableEntity> getSynchronizables() {
		return synchronizables;
	}
	
	public List<SynchronizableEntity> getSynchronizableList() {
		if(synchronizables!=null)
			return new ArrayList<SynchronizableEntity>(synchronizables);
		else
			return null;
	}

	@Override
	public void setSynchronizables(Set<SynchronizableEntity> synchronizables) {
		this.synchronizables = synchronizables;
	}
	
	public void setSynchronizableList(List<SynchronizableEntity> synchronizables) {
		if(synchronizables!=null)
			this.synchronizables = new HashSet<SynchronizableEntity>(synchronizables);
		else
			this.synchronizables = null;
	}

	@Override
	public void addSynchronizable(SynchronizableEntity synchronizable) {
		this.synchronizables.add(synchronizable);
	}

	/**
	 * Get the assigned roles for this actor.
	 * @return a list of roles (EMPTY_LIST if none).
	 */
	@Override
	public Set<ImogRole> getRoles() {
		return roles;
	}
	
	public List<ImogRole> getRoleList() {
		if(roles!=null)
			return new ArrayList<ImogRole>(roles);
		else
			return null;
	}

	/**
	 * Set the list of roles assigned to this actor.
	 * @param roles the list of roles assigned.
	 */
	@Override
	public void setRoles(Set<ImogRole> roles) {
		this.roles = roles;
	}
	
	public void setRoleList(List<ImogRole> roles) {
		if(roles!=null)
			this.roles = new HashSet<ImogRole>(roles);
		else
			this.roles = null;
	}

	@Override
	public void addRole(ImogRole role) {
		this.roles.add(role);
	}

	@Override
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	@Override
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * Is the specified role already assigned to the user ?
	 * @param id the role id
	 * @return true it is already assigned
	 */
	@Override
	public boolean isAssignedRole(String id) {
		final Set<ImogRole> roles = getRoles();
		if (roles != null) {
			for (ImogRole role : roles) {
				if (role.getId().equals(id)) {
					return true;
				}
			}
		}
		return false;
	}

}
