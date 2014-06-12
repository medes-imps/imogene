package org.imogene.admin.client;

import java.util.List;

import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.admin.shared.proxy.NotificationProxy;
import org.imogene.lib.common.constants.NotificationConstants;
import org.imogene.web.client.dynamicfields.ui.field.FormType;
import org.imogene.web.client.dynamicfields.ui.field.FormTypeUtil;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.util.ImogBeanRenderer;
import org.imogene.web.shared.proxy.CardEntityProxy;
import org.imogene.web.shared.proxy.EntityProfileProxy;
import org.imogene.web.shared.proxy.FieldGroupProfileProxy;
import org.imogene.web.shared.proxy.FieldGroupProxy;
import org.imogene.web.shared.proxy.ImogBeanProxy;
import org.imogene.web.shared.proxy.ProfileProxy;

/**
 * Singleton that enables to render a display value of the beans
 * 
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
	 * 
	 * @param bean the ImogBeanProxy
	 * @return string representation for IHM display
	 */
	public String getDisplayValue(ImogBeanProxy bean) {

		if (bean instanceof ProfileProxy) {
			return getDisplayValue((ProfileProxy) bean);
		}
		if (bean instanceof EntityProfileProxy) {
			return getDisplayValue((EntityProfileProxy) bean);
		}
		if (bean instanceof FieldGroupProfileProxy) {
			return getDisplayValue((FieldGroupProfileProxy) bean);
		}
		if (bean instanceof CardEntityProxy) {
			return getDisplayValue((CardEntityProxy) bean);
		}
		if (bean instanceof FieldGroupProxy) {
			return getDisplayValue((FieldGroupProxy) bean);
		}
		if (bean instanceof NotificationProxy) {
			return getDisplayValue((NotificationProxy) bean);
		}
		return "";
	}

	/**	 */
	private String getDisplayValue(ProfileProxy bean) {
		String value = new String();
		if (bean.getName() != null) {
			value = value + bean.getName() + " ";
		}

		return value.trim();
	}

	/**	 */
	private String getDisplayValue(EntityProfileProxy bean) {
		String value = new String();

		if (bean.getProfile() != null)
			value = value + getDisplayValue(bean.getProfile()) + " ";

		if (bean.getEntity() != null)
			value = value + getDisplayValue(bean.getEntity()) + " ";

		return value.trim();
	}

	/**	 */
	private String getDisplayValue(FieldGroupProfileProxy bean) {
		String value = new String();

		if (bean.getProfile() != null)
			value = value + getDisplayValue(bean.getProfile()) + " ";

		if (bean.getFieldGroup() != null)
			value = value + getDisplayValue(bean.getFieldGroup()) + " ";

		return value.trim();
	}

	/**	 */
	private String getDisplayValue(CardEntityProxy bean) {
		String value = new String();
		if (bean.getName() != null) {
			value = value + bean.getName() + " ";
		}

		return value.trim();
	}

	/**	 */
	private String getDisplayValue(FieldGroupProxy bean) {
		String value = new String();
		if (bean.getEntity() != null) {
			value = value + getDisplayValue(bean.getEntity()) + " ";
		}
		if (bean.getName() != null) {
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
	 * 
	 * @param beanClass a ImogBeanProxy class type
	 * @param fieldName the ImogBeanProxy field name
	 * @param fieldValue the bean field value
	 * @return string representation for IHM display
	 */
	public String getEnumDisplayValue(Class<?> beanClass, String fieldName, String fieldValue) {

		if (fieldValue != null && !fieldValue.equals("")) {

			if (beanClass.equals(ProfileProxy.class)) {
				return getProfileEnumDisplayValue(fieldName, fieldValue);
			}

			if (beanClass.equals(EntityProfileProxy.class)) {
				return getEntityProfileEnumDisplayValue(fieldName, fieldValue);
			}

			if (beanClass.equals(FieldGroupProfileProxy.class)) {
				return getFieldGroupProfileEnumDisplayValue(fieldName, fieldValue);
			}

			if (beanClass.equals(FieldGroupProxy.class)) {
				return getFieldGroupEnumDisplayValue(fieldName, fieldValue);
			}

			if (beanClass.equals(CardEntityProxy.class)) {
				return getCardEntityEnumDisplayValue(fieldName, fieldValue);
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
	private String getProfileEnumDisplayValue(String fieldName, String fieldValue) {
		String value = BaseNLS.constants().enumeration_unknown();

		return value.trim();
	}

	/**
	 *
	 */
	private String getEntityProfileEnumDisplayValue(String fieldName, String fieldValue) {
		String value = BaseNLS.constants().enumeration_unknown();

		return value.trim();
	}

	/**
	 *
	 */
	private String getFieldGroupProfileEnumDisplayValue(String fieldName, String fieldValue) {
		String value = BaseNLS.constants().enumeration_unknown();

		return value.trim();
	}

	/**
	 *
	 */
	private String getCardEntityEnumDisplayValue(String fieldName, String fieldValue) {
		String value = BaseNLS.constants().enumeration_unknown();

		return value.trim();
	}

	/**
	 *
	 */
	private String getFieldGroupEnumDisplayValue(String fieldName, String fieldValue) {
		String value = BaseNLS.constants().enumeration_unknown();

		return value.trim();
	}

	/**
	 *
	 */
	private String getNotificationEnumDisplayValue(String fieldName, String fieldValue) {
		String value = BaseNLS.constants().enumeration_unknown();

		if (fieldName.equals("method")) {

			if (fieldValue.equals(NotificationConstants.EMAIL_NOTIF))
				value = AdminNLS.constants().notification_method_mail_option();
			else if (fieldValue.equals(NotificationConstants.SMS_NOTIF))
				value = AdminNLS.constants().notification_method_sMS_option();

		}

		if (fieldName.equals("formType")) {
			if (formTypeUtil != null) {

				List<FormType> formTypes = formTypeUtil.getFormTypes();
				for (FormType type : formTypes) {
					if (fieldValue.equals(type.getValue()))
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
