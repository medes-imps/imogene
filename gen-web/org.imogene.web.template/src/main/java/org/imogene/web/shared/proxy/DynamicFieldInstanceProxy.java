package org.imogene.web.shared.proxy;

import org.imogene.lib.common.dynamicfields.DynamicFieldInstance;
import org.imogene.web.server.locator.DynamicFieldInstanceLocator;

import com.google.web.bindery.requestfactory.shared.ProxyFor;

/**
 * DynamicField_Instance proxy 
 * @author Medes-IMPS
 */
@ProxyFor(value = DynamicFieldInstance.class, locator = DynamicFieldInstanceLocator.class)
public interface DynamicFieldInstanceProxy extends ImogBeanProxy {

	/* Identification section fields */

	public DynamicFieldTemplateProxy getFieldTemplate();
	public void setFieldTemplate(DynamicFieldTemplateProxy value);

	public String getFieldValue();
	public void setFieldValue(String value);

}
