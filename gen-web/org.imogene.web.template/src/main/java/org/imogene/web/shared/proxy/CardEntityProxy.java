package org.imogene.web.shared.proxy;

import org.imogene.lib.common.model.CardEntity;
import org.imogene.web.server.locator.CardEntityLocator;

import com.google.web.bindery.requestfactory.shared.ProxyFor;

/**
 * SynchronizableEntity proxy 
 * @author Medes-IMPS
 */
@ProxyFor(value = CardEntity.class, locator = CardEntityLocator.class)
public interface CardEntityProxy extends ImogBeanProxy {

	/* Identification section fields */

	public String getName();
	public void setName(String value);

}
