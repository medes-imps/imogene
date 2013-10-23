package org.imogene.web.shared.proxy;

import org.imogene.admin.server.locator.SynchronizableEntityLocator;
import org.imogene.lib.common.sync.entity.SynchronizableEntity;

import com.google.web.bindery.requestfactory.shared.ProxyFor;

/**
 * SynchronizableEntity proxy 
 * @author Medes-IMPS
 */
@ProxyFor(value = SynchronizableEntity.class, locator = SynchronizableEntityLocator.class)
public interface SynchronizableEntityProxy extends ImogBeanProxy {

	/* Identification section fields */

	public String getName();
	public void setName(String value);

}
