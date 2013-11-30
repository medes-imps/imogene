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

import org.imogene.lib.common.model.CardEntity;
import org.imogene.lib.common.profile.Profile;

/**
 * Implementation of the ImogActor interface
 * 
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
	protected Set<CardEntity> synchronizables;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "imog_actor_profiles", joinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "profile_id", referencedColumnName = "id"))
	private Set<Profile> profiles;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginDate;

	/**
	 * Get this actor login.
	 * 
	 * @return the actor login
	 */
	@Override
	public String getLogin() {
		return login;
	}

	/**
	 * Set the actor login.
	 * 
	 * @param login the actor login.
	 */
	@Override
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Get the assigned password for this actor.
	 * 
	 * @return the actor password
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/**
	 * Set the actor password
	 * 
	 * @param password the actor password
	 */
	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Get the notification locale ISO code to use for this actor
	 * 
	 * @return the locale ISO code
	 */
	@Override
	public String getNotificationLocale() {
		return notificationLocale;
	}

	/**
	 * Set the notification locale ISO code
	 * 
	 * @param notificationLocale the locale ISO code
	 */
	@Override
	public void setNotificationLocale(String notificationLocale) {
		this.notificationLocale = notificationLocale;
	}

	/**
	 * This actor can be notified ?
	 * 
	 * @return true if actor can be notified.
	 */
	@Override
	public Boolean getBeNotified() {
		return beNotified;
	}

	/**
	 * Set if this actor can be notified
	 * 
	 * @param beNotified true if it can be notified
	 */
	@Override
	public void setBeNotified(Boolean beNotified) {
		this.beNotified = beNotified;
	}

	@Override
	public Set<CardEntity> getSynchronizables() {
		return synchronizables;
	}

	public List<CardEntity> getSynchronizableList() {
		if (synchronizables != null)
			return new ArrayList<CardEntity>(synchronizables);
		else
			return null;
	}

	@Override
	public void setSynchronizables(Set<CardEntity> synchronizables) {
		this.synchronizables = synchronizables;
	}

	public void setSynchronizableList(List<CardEntity> synchronizables) {
		if (synchronizables != null)
			this.synchronizables = new HashSet<CardEntity>(synchronizables);
		else
			this.synchronizables = null;
	}

	@Override
	public void addSynchronizable(CardEntity synchronizable) {
		this.synchronizables.add(synchronizable);
	}

	@Override
	public Set<Profile> getProfiles() {
		return profiles;
	}

	public List<Profile> getProfilesList() {
		if (profiles == null) {
			return null;
		}
		return new ArrayList<Profile>(profiles);
	}

	@Override
	public void setProfiles(Set<Profile> value) {
		profiles = value;
	}

	public void setProfilesList(List<Profile> value) {
		if (profiles == null) {
			profiles = new HashSet<Profile>();
		}
		profiles.clear();
		profiles.addAll(value);
	}

	@Override
	public void addToProfiles(Profile param) {
		profiles.add(param);
	}

	@Override
	public void removeFromProfiles(Profile param) {
		profiles.remove(param);
	}

	@Override
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	@Override
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

}
