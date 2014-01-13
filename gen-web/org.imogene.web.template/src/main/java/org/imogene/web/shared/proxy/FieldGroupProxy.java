package org.imogene.web.shared.proxy;

import org.imogene.lib.common.model.FieldGroup;
import org.imogene.web.server.locator.FieldGroupLocator;

import com.google.web.bindery.requestfactory.shared.ProxyFor;

/**
 * FieldGroup proxy
 * 
 * @author Medes-IMPS
 */
@ProxyFor(value = FieldGroup.class, locator = FieldGroupLocator.class)
public interface FieldGroupProxy extends ImogBeanProxy {

	/* Description section fields */
	public String getName();

	public void setName(String value);

	public CardEntityProxy getEntity();

	public void setEntity(CardEntityProxy value);

}
