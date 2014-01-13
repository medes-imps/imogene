package org.imogene.web.shared.proxy;

import org.imogene.lib.common.dynamicfields.DynamicFieldTemplate;
import org.imogene.web.server.locator.DynamicFieldTemplateLocator;

import com.google.web.bindery.requestfactory.shared.ProxyFor;

/**
 * DynamicField_Template proxy 
 * @author Medes-IMPS
 */
@ProxyFor(value = DynamicFieldTemplate.class, locator = DynamicFieldTemplateLocator.class)
public interface DynamicFieldTemplateProxy extends ImogBeanProxy {

	/* Identification section fields */

	public String getFieldName();
	public void setFieldName(String value);

	public String getFieldType();
	public void setFieldType(String value);

	public String getParameters();
	public void setParameters(String value);

	public String getFormType();
	public void setFormType(String value);

	public ImogActorProxy getTemplateCreator();
	public void setTemplateCreator(ImogActorProxy value);

	public String getDescription();
	public void setDescription(String value);

	public String getReason();
	public void setReason(String value);

	public Boolean getIsDefault();
	public void setIsDefault(Boolean value);

	/* Properties section fields */

	public Boolean getRequiredValue();
	public void setRequiredValue(Boolean value);

	public Integer getFieldPosition();
	public void setFieldPosition(Integer value);

	public String getMinimumValue();
	public void setMinimumValue(String value);

	public String getMaximumValue();
	public void setMaximumValue(String value);

	public String getDefaultValue();
	public void setDefaultValue(String value);

	/* Administration section fields */

	public Boolean getAllUsers();
	public void setAllUsers(Boolean value);

	public Boolean getIsActivated();
	public void setIsActivated(Boolean value);

}
