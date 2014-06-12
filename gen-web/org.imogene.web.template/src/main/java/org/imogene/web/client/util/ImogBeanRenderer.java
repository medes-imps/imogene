package org.imogene.web.client.util;

import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.shared.proxy.ImogBeanProxy;

public abstract class ImogBeanRenderer {
	
	

	
	public ImogBeanRenderer() {
	}
	

	/**
	 * Get display representation for a ImogBean     
	 * @param bean the ImogBean
	 * @return string representation for IHM display
	 */
	public abstract String getDisplayValue(ImogBeanProxy bean);
	
	
	/**
	 * Get enumeration representation for a ImogBean type enumeration field
	 * @param beanClass a ImogBean class type
	 * @param fieldName the ImogBean field name     
	 * @param fieldValue the bean field value    
	 * @return string representation for IHM display
	 */
	public abstract String getEnumDisplayValue(Class<?> beanClass, String fieldName, String fieldValue);

	
	/**
	 * Internationalize the errors that are thrown on the server side
	 * Based on the implementation that error messages are keys to i18n property files
	 * @param key the message key
	 * @return the internalionalized message
	 */
	public String getI18nErrorMessage(String key) {

		if (key.equals("error_required"))
			return BaseNLS.messages().error_required();
		else
			return key;
	}
	
	
}
