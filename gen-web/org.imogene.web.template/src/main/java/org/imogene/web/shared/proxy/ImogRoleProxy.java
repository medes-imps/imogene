package org.imogene.web.shared.proxy;

import org.imogene.lib.common.role.ImogRole;
import org.imogene.web.server.locator.ImogRoleLocator;

import com.google.web.bindery.requestfactory.shared.ProxyFor;

/**
 * ImogRole proxy 
 * @author Medes-IMPS
 */
@ProxyFor(value = ImogRole.class, locator = ImogRoleLocator.class)
public interface ImogRoleProxy extends ImogBeanProxy {

	/* Identification section fields */

	public String getName();
	public void setName(String value);

}
