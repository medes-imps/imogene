package org.imogene.web.shared.proxy;

import java.util.List;

import org.imogene.lib.common.entity.ImogActorImpl;
import org.imogene.web.server.locator.ImogBeanImplLocator;

import com.google.web.bindery.requestfactory.shared.ProxyFor;



@ProxyFor(value=ImogActorImpl.class, locator = ImogBeanImplLocator.class)
public interface ImogActorProxy extends ImogBeanProxy {

	/** 
	 * Get this actor login.
	 *@return the actor login
	 */
	public String getLogin();

	/**
	 * Set the actor login.
	 *@param pLogin the actor login.
	 */
	public void setLogin(String pLogin);
	
	/** 
	 * Get this actor password.
	 *@return the actor password
	 */
	public String getPassword();

	/**
	 * Set the actor password.
	 *@param pPassword the actor password.
	 */
	public void setPassword(String pPassword);
	
	/** 
	 * Get the assigned roles for this actor. 
	 * @return a list of role names (EMPTY_LIST if none). 	  
	 */
	public List<ImogRoleProxy> getRoleList();

	/**
	 * Set the list of roles assigned to this actor. 	 
	 * @param roles the list of roles assigned. 
	 */
	public void setRoleList(List<ImogRoleProxy> roles);

	/**
	 * Get the form types that this actor will be
	 * able to synchronize with remote applications
	 * @return SynchronizableEntity
	 */
	public List<SynchronizableEntityProxy> getSynchronizableList();
	
	/**
	 * Set the form types that this actor will be
	 * able to synchronize with remote applications
	 * @param value
	 */
	public void setSynchronizableList(List<SynchronizableEntityProxy> value);



}
