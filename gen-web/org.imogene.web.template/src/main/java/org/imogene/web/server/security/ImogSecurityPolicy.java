package org.imogene.web.server.security;


/**
 * Interfaces that defines an ImogSecurityPolicy
 * @author MEDES-IMPS
 */
public interface ImogSecurityPolicy {


	/**
	 * Tells if the current user can access an Object property
	 * @param bean the object whose property access has to be checked
	 * @param property the property whose access has to be checked
	 * @return true if the current user can access an Object property
	 */
	public boolean canAccessProperty(Object bean, String property);
	
	
	/**
	 * Tells if the current user can update an Object property
	 * @param bean the object whose property update privilege has to be checked
	 * @param property the property whose update privilege has to be checked
	 * @return true if the current user can update an Object property
	 */
	public boolean canUpdateProperty(Object bean, String property);
	
}
