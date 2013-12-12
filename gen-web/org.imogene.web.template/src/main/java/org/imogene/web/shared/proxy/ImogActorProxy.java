package org.imogene.web.shared.proxy;

import java.util.List;

import org.imogene.lib.common.entity.ImogActorImpl;
import org.imogene.web.server.locator.ImogBeanImplLocator;

import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(value = ImogActorImpl.class, locator = ImogBeanImplLocator.class)
public interface ImogActorProxy extends ImogBeanProxy {

	/**
	 * Get this actor login.
	 * 
	 * @return the actor login
	 */
	public String getLogin();

	/**
	 * Set the actor login.
	 * 
	 * @param pLogin the actor login.
	 */
	public void setLogin(String pLogin);

	/**
	 * Get this actor password.
	 * 
	 * @return the actor password
	 */
	public String getPassword();

	/**
	 * Set the actor password.
	 * 
	 * @param pPassword the actor password.
	 */
	public void setPassword(String pPassword);

	/**
	 * Get the form types that this actor will be able to synchronize with remote applications
	 * 
	 * @return SynchronizableEntity
	 */
	public List<CardEntityProxy> getSynchronizables();

	/**
	 * Set the form types that this actor will be able to synchronize with remote applications
	 * 
	 * @param value
	 */
	public void setSynchronizables(List<CardEntityProxy> value);

	/**
	 * Get the profiles list of an actor
	 * 
	 * @return The profile list
	 */
	public List<ProfileProxy> getProfiles();

	/**
	 * Set the profiles list of an actor
	 * 
	 * @param value The new profile list
	 */
	public void setProfiles(List<ProfileProxy> value);

}
