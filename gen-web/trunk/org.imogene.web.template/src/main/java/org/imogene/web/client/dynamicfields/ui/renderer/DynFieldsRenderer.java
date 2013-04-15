package org.imogene.web.client.dynamicfields.ui.renderer;

import java.util.List;

import org.imogene.web.client.dynamicfields.i18n.DynFieldsNLS;
import org.imogene.web.client.dynamicfields.ui.field.DFConstants;
import org.imogene.web.client.dynamicfields.ui.field.FormType;
import org.imogene.web.client.dynamicfields.ui.field.FormTypeUtil;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.util.ImogBeanRenderer;
import org.imogene.web.shared.proxy.DynamicFieldInstanceProxy;
import org.imogene.web.shared.proxy.DynamicFieldTemplateProxy;
import org.imogene.web.shared.proxy.ImogBeanProxy;

/**
 * Singleton that enables to render a display value of the beans
 * @author MEDES-IMPS
 */
public class DynFieldsRenderer extends ImogBeanRenderer {

	private static DynFieldsRenderer instance = new DynFieldsRenderer();
	
	private FormTypeUtil formTypeUtil;



	private DynFieldsRenderer() {
		super();
	}

	public static DynFieldsRenderer get() {
		return instance;
	}

	/**
	 * Get display representation for a ImogBeanProxy     
	 * @param bean the ImogBeanProxy
	 * @return string representation for IHM display
	 */
	public String getDisplayValue(ImogBeanProxy bean) {

		if (bean instanceof DynamicFieldTemplateProxy) {
			return getDisplayValue((DynamicFieldTemplateProxy) bean);
		}
		if (bean instanceof DynamicFieldInstanceProxy) {
			return getDisplayValue((DynamicFieldInstanceProxy) bean);
		}
		return "";
	}


	/**	 */
	private String getDisplayValue(DynamicFieldTemplateProxy bean) {
		String value = new String();
		if (bean.getFieldName() != null) {
			value = value + bean.getFieldName() + " ";
		}
		String fieldType = bean.getFieldType();
		if (fieldType != null) {
			if (fieldType.equals(DFConstants.FIELD_TYPE_STRING))
				value = value
						+ DynFieldsNLS.constants()
								.dynamicField_Template_fieldType_string_option()
						+ " ";
			else if (fieldType.equals(DFConstants.FIELD_TYPE_TEXT))
				value = value
						+ DynFieldsNLS.constants()
								.dynamicField_Template_fieldType_text_option()
						+ " ";
			else if (fieldType.equals(DFConstants.FIELD_TYPE_INT))
				value = value
						+ DynFieldsNLS.constants()
								.dynamicField_Template_fieldType_integer_option()
						+ " ";
			else if (fieldType.equals(DFConstants.FIELD_TYPE_FLOAT))
				value = value
						+ DynFieldsNLS.constants()
								.dynamicField_Template_fieldType_float_option()
						+ " ";
			else if (fieldType.equals(DFConstants.FIELD_TYPE_DATE))
				value = value
						+ DynFieldsNLS.constants()
								.dynamicField_Template_fieldType_date_option()
						+ " ";
			else if (fieldType.equals(DFConstants.FIELD_TYPE_BOOL))
				value = value
						+ DynFieldsNLS.constants()
								.dynamicField_Template_fieldType_boolean_option()
						+ " ";
			else if (fieldType.equals(DFConstants.FIELD_TYPE_ENUM_S))
				value = value
						+ DynFieldsNLS.constants()
								.dynamicField_Template_fieldType_singleEnumeration_option()
						+ " ";
			else if (fieldType.equals(DFConstants.FIELD_TYPE_ENUM_M))
				value = value
						+ DynFieldsNLS.constants()
								.dynamicField_Template_fieldType_multiEnumeration_option()
						+ " ";
			else if (fieldType.equals(DFConstants.FIELD_TYPE_BIN))
				value = value
						+ DynFieldsNLS.constants()
								.dynamicField_Template_fieldType_binary_option()
						+ " ";
			else if (fieldType.equals(DFConstants.FIELD_TYPE_IMG))
				value = value
						+ DynFieldsNLS.constants()
								.dynamicField_Template_fieldType_image_option()
						+ " ";
			else if (fieldType.equals(DFConstants.FIELD_TYPE_GEO))
				value = value
						+ DynFieldsNLS.constants()
								.dynamicField_Template_fieldType_geoField_option()
						+ " ";
			else if (fieldType.equals(DFConstants.FIELD_TYPE_BCODE))
				value = value
						+ DynFieldsNLS.constants()
								.dynamicField_Template_fieldType_barCode_option()
						+ " ";
		}

		return value.trim();
	}
	/**	 */
	private String getDisplayValue(DynamicFieldInstanceProxy bean) {
		String value = new String();
		if (bean.getFieldValue() != null) {
			value = value + bean.getFieldValue() + " ";
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

			if (beanClass.equals(DynamicFieldTemplateProxy.class)) {
				return getDynamicField_TemplateEnumDisplayValue(fieldName,
						fieldValue);
			}

			if (beanClass.equals(DynamicFieldInstanceProxy.class)) {
				return getDynamicField_InstanceEnumDisplayValue(fieldName,
						fieldValue);
			}

		}
		return "";
	}


	/**
	 *
	 */
	private String getDynamicField_TemplateEnumDisplayValue(String fieldName, String fieldValue) {
		String value = BaseNLS.constants().enumeration_unknown();

		if (fieldName.equals("fieldType")) {

			if (fieldValue.equals(DFConstants.FIELD_TYPE_STRING))
				value = DynFieldsNLS.constants()
						.dynamicField_Template_fieldType_string_option();
			else if (fieldValue.equals(DFConstants.FIELD_TYPE_TEXT))
				value = DynFieldsNLS.constants()
						.dynamicField_Template_fieldType_text_option();
			else if (fieldValue.equals(DFConstants.FIELD_TYPE_INT))
				value = DynFieldsNLS.constants()
						.dynamicField_Template_fieldType_integer_option();
			else if (fieldValue.equals(DFConstants.FIELD_TYPE_FLOAT))
				value = DynFieldsNLS.constants()
						.dynamicField_Template_fieldType_float_option();
			else if (fieldValue.equals(DFConstants.FIELD_TYPE_DATE))
				value = DynFieldsNLS.constants()
						.dynamicField_Template_fieldType_date_option();
			else if (fieldValue.equals(DFConstants.FIELD_TYPE_BOOL))
				value = DynFieldsNLS.constants()
						.dynamicField_Template_fieldType_boolean_option();
			else if (fieldValue.equals(DFConstants.FIELD_TYPE_ENUM_S))
				value = DynFieldsNLS.constants()
						.dynamicField_Template_fieldType_singleEnumeration_option();
			else if (fieldValue.equals(DFConstants.FIELD_TYPE_ENUM_M))
				value = DynFieldsNLS.constants()
						.dynamicField_Template_fieldType_multiEnumeration_option();
			else if (fieldValue.equals(DFConstants.FIELD_TYPE_BIN))
				value = DynFieldsNLS.constants()
						.dynamicField_Template_fieldType_binary_option();
			else if (fieldValue.equals(DFConstants.FIELD_TYPE_IMG))
				value = DynFieldsNLS.constants()
						.dynamicField_Template_fieldType_image_option();
			else if (fieldValue.equals(DFConstants.FIELD_TYPE_GEO))
				value = DynFieldsNLS.constants()
						.dynamicField_Template_fieldType_geoField_option();
			else if (fieldValue.equals(DFConstants.FIELD_TYPE_BCODE))
				value = DynFieldsNLS.constants()
						.dynamicField_Template_fieldType_barCode_option();
		}

		if (fieldName.equals("formType")) {	
			if(formTypeUtil!=null) {
				
				List<FormType> formTypes = formTypeUtil.getDynamicFieldFormTypes();
				for(FormType type:formTypes) {				
					if(fieldValue.equals(type.getValue()))
						value = type.getDisplayValue();
				}
			}
		}

		return value.trim();
	}

	/**
	 *
	 */
	private String getDynamicField_InstanceEnumDisplayValue(String fieldName,
			String fieldValue) {
		String value = BaseNLS.constants().enumeration_unknown();

		return value.trim();
	}
	
	public FormTypeUtil getFormTypeUtil() {
		return formTypeUtil;
	}

	public void setFormTypeUtil(FormTypeUtil formTypeUtil) {
		this.formTypeUtil = formTypeUtil;
	}

}
