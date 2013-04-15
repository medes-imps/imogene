package org.imogene.admin.client;

import java.util.List;

import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.admin.shared.proxy.NotificationProxy;
import org.imogene.lib.common.constants.NotificationConstants;
import org.imogene.web.client.dynamicfields.ui.field.FormType;
import org.imogene.web.client.dynamicfields.ui.field.FormTypeUtil;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.util.ImogBeanRenderer;
import org.imogene.web.shared.proxy.ImogBeanProxy;
import org.imogene.web.shared.proxy.ImogRoleProxy;
import org.imogene.web.shared.proxy.SynchronizableEntityProxy;

/**
 * Singleton that enables to render a display value of the beans
 * @author MEDES-IMPS
 */
public class AdminRenderer extends ImogBeanRenderer {

	private static AdminRenderer instance = new AdminRenderer();
	
	private FormTypeUtil formTypeUtil;
	
	

	private AdminRenderer() {
		super();
	}

	public static AdminRenderer get() {
		return instance;
	}

	/**
	 * Get display representation for a ImogBeanProxy     
	 * @param bean the ImogBeanProxy
	 * @return string representation for IHM display
	 */
	public String getDisplayValue(ImogBeanProxy bean) {

		if (bean instanceof ImogRoleProxy) {
			return getDisplayValue((ImogRoleProxy) bean);
		}
		if (bean instanceof SynchronizableEntityProxy) {
			return getDisplayValue((SynchronizableEntityProxy) bean);
		}
		if (bean instanceof NotificationProxy) {
			return getDisplayValue((NotificationProxy) bean);
		}
		return "";
	}

	/**	 */
	private String getDisplayValue(ImogRoleProxy bean) {
		String value = new String();
		if (bean.getName() != null) {
			value = value + bean.getName() + " ";
		}

		return value.trim();
	}
	/**	 */
	private String getDisplayValue(SynchronizableEntityProxy bean) {
		String value = new String();
		
		String entity = bean.getId();
		if (entity != null) {
			
			List<FormType> forms = formTypeUtil.getFormTypes();
			for(FormType type: forms) {
				if(type.getValue().equals(entity))
					return type.getDisplayValue();
			}
			
			value = value + bean.getName() + " ";
		}

		return value.trim();
	}
	/**	 */
	private String getDisplayValue(NotificationProxy bean) {
		String value = new String();
		if (bean.getName() != null) {
			value = value + bean.getName() + " ";
		}

		return value.trim();
	}

	/**
	 * Get an enumeration representation for a ImogBeanProxy type enumeration field
	 * @param beanClass a ImogBeanProxy class type
	 * @param fieldName the ImogBeanProxy field name     
	 * @param fieldValue the bean field value    
	 * @return string representation for IHM display
	 */
	public String getEnumDisplayValue(Class<?> beanClass, String fieldName,
			String fieldValue) {

		if (fieldValue != null && !fieldValue.equals("")) {

			if (beanClass.equals(ImogRoleProxy.class)) {
				return getImogRoleEnumDisplayValue(fieldName, fieldValue);
			}

			if (beanClass.equals(SynchronizableEntityProxy.class)) {
				return getSynchronizableEntityEnumDisplayValue(fieldName,
						fieldValue);
			}

			if (beanClass.equals(NotificationProxy.class)) {
				return getNotificationEnumDisplayValue(fieldName, fieldValue);
			}

		}
		return "";
	}


	/**
	 *
	 */
	private String getImogRoleEnumDisplayValue(String fieldName,
			String fieldValue) {
		String value = BaseNLS.constants().enumeration_unknown();

		return value.trim();
	}

	/**
	 *
	 */
	private String getSynchronizableEntityEnumDisplayValue(String fieldName,
			String fieldValue) {
		String value = BaseNLS.constants().enumeration_unknown();

		return value.trim();
	}

	/**
	 *
	 */
	private String getNotificationEnumDisplayValue(String fieldName,
			String fieldValue) {
		String value = BaseNLS.constants().enumeration_unknown();

		if (fieldName.equals("method")) {

			if (fieldValue.equals(NotificationConstants.EMAIL_NOTIF))
				value = AdminNLS.constants().notification_method_mail_option();
			else if (fieldValue.equals(NotificationConstants.SMS_NOTIF))
				value = AdminNLS.constants().notification_method_sMS_option();

		}

		if (fieldName.equals("formType")) {	
			if(formTypeUtil!=null) {
				
				List<FormType> formTypes = formTypeUtil.getFormTypes();
				for(FormType type:formTypes) {				
					if(fieldValue.equals(type.getValue()))
						value = type.getDisplayValue();
				}
			}
		}

		if (fieldName.equals("operation")) {

			if (fieldValue.equals(NotificationConstants.CREATE_OP))
				value = AdminNLS.constants().notification_operation_create_option();
			else if (fieldValue.equals(NotificationConstants.UPDATE_OP))
				value = AdminNLS.constants().notification_operation_update_option();
			else if (fieldValue.equals(NotificationConstants.DELETE_OP))
				value = AdminNLS.constants().notification_operation_delete_option();

		}

		return value.trim();
	}
	
	public FormTypeUtil getFormTypeUtil() {
		return formTypeUtil;
	}

	public void setFormTypeUtil(FormTypeUtil formTypeUtil) {
		this.formTypeUtil = formTypeUtil;
	}

}
