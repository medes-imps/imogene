package org.imogene.web.client.dynamicfields.ui.editor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.imogene.web.client.dynamicfields.i18n.DynFieldsNLS;
import org.imogene.web.client.dynamicfields.ui.field.DFConstants;
import org.imogene.web.client.dynamicfields.ui.field.FormType;
import org.imogene.web.client.dynamicfields.ui.field.FormTypeUtil;
import org.imogene.web.client.event.FieldValueChangeEvent;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.field.ImogBooleanBox;
import org.imogene.web.client.ui.field.ImogField;
import org.imogene.web.client.ui.field.ImogIntegerBox;
import org.imogene.web.client.ui.field.ImogSingleEnumBox;
import org.imogene.web.client.ui.field.ImogTextAreaBox;
import org.imogene.web.client.ui.field.ImogTextBox;
import org.imogene.web.client.ui.field.group.FieldGroupPanel;
import org.imogene.web.client.ui.field.relation.single.ImogBeanEditorWithoutUi;
import org.imogene.web.client.util.DateUtil;
import org.imogene.web.client.util.NumericUtil;
import org.imogene.web.client.util.ProfileUtil;
import org.imogene.web.shared.ImogRequestFactory;
import org.imogene.web.shared.proxy.DynamicFieldTemplateProxy;
import org.imogene.web.shared.proxy.ImogActorProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorDelegate;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.HasEditorDelegate;
import com.google.gwt.editor.client.HasEditorErrors;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * Editor that provides the UI components that allow a DynamicField_TemplateProxy to be viewed and edited
 * @author MEDES-IMPS
 */
public class DynamicFieldTemplateEditor extends Composite
		implements
			Editor<DynamicFieldTemplateProxy>, HasEditorDelegate<DynamicFieldTemplateProxy>, HasEditorErrors<DynamicFieldTemplateProxy> {

	interface Binder extends UiBinder<Widget, DynamicFieldTemplateEditor> {
	}

	private static final Binder BINDER = GWT.create(Binder.class);

	protected final ImogRequestFactory requestFactory;
	private EditorDelegate<DynamicFieldTemplateProxy> delegate;
	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();

	private boolean hideButtons = false;
	private boolean isNew = false;
	private FormTypeUtil formTypeUtil;

	/* Identification section widgets */
	@UiField
	@com.google.gwt.editor.client.Editor.Ignore
	FieldGroupPanel identificationSection;
	@UiField
	ImogTextBox fieldName;
	@UiField
	ImogSingleEnumBox fieldType;
	@UiField
	ImogTextAreaBox parameters;
	@UiField
	ImogSingleEnumBox formType;
//	@UiField(provided = true)
	ImogBeanEditorWithoutUi<ImogActorProxy> templateCreator;
	@UiField
	ImogTextAreaBox description;
	@UiField
	ImogTextAreaBox reason;
	@UiField
	ImogBooleanBox isDefault;

	/* Properties section widgets */
	@UiField
	@com.google.gwt.editor.client.Editor.Ignore
	FieldGroupPanel propertiesSection;
	@UiField
	ImogBooleanBox requiredValue;
	@UiField
	ImogIntegerBox fieldPosition;
	@UiField
	ImogTextBox minimumValue;
	@UiField
	ImogTextBox maximumValue;
	@UiField
	ImogTextBox defaultValue;

	/* Administration section widgets */
	@UiField
	@com.google.gwt.editor.client.Editor.Ignore
	FieldGroupPanel administrationSection;
	@UiField
	ImogBooleanBox allUsers;
	@UiField
	ImogBooleanBox isActivated;

	
	/**
	 * Constructor
	 * @param factory the application request factory
	 * @param hideButtons true if the relation field buttons shall be hidden
	 */
	public DynamicFieldTemplateEditor(ImogRequestFactory factory,	boolean hideButtons, boolean isNew, FormTypeUtil formTypeUtil) {

		this.requestFactory = factory;
		this.hideButtons = hideButtons;
		this.isNew = isNew;
		this.formTypeUtil = formTypeUtil;
		
		templateCreator = new ImogBeanEditorWithoutUi<ImogActorProxy>();
		
		initWidget(BINDER.createAndBindUi(this));

		properties();
	}
	
	/**
	 * Constructor
	 * @param factory the application request factory
	 * @param hideButtons true if the relation field buttons shall be hidden
	 */
	public DynamicFieldTemplateEditor(ImogRequestFactory factory,	boolean hideButtons, FormTypeUtil formTypeUtil) {
		this(factory, hideButtons, false, formTypeUtil);
	}

	/**
	 * Constructor
	 * @param factory the application request factory
	 */
	public DynamicFieldTemplateEditor(ImogRequestFactory factory, FormTypeUtil formTypeUtil) {
		this(factory, false, formTypeUtil);
	}

	/**
	 * Sets the properties of the fields
	 */
	public void properties() {

		/* Identification section widgets */
		identificationSection.setGroupTitle(DynFieldsNLS.constants()
				.dynamicField_Template_group_identification());
		fieldName.setLabel(DynFieldsNLS.constants()
				.dynamicField_Template_field_fieldName());
		fieldType.setLabel(DynFieldsNLS.constants()
				.dynamicField_Template_field_fieldType());
		fieldType.addItem(DFConstants.FIELD_TYPE_STRING, DynFieldsNLS.constants()
				.dynamicField_Template_fieldType_string_option());
		fieldType.addItem(DFConstants.FIELD_TYPE_TEXT, DynFieldsNLS.constants()
				.dynamicField_Template_fieldType_text_option());
		fieldType.addItem(DFConstants.FIELD_TYPE_INT, DynFieldsNLS.constants()
				.dynamicField_Template_fieldType_integer_option());
		fieldType.addItem(DFConstants.FIELD_TYPE_FLOAT, DynFieldsNLS.constants()
				.dynamicField_Template_fieldType_float_option());
		fieldType.addItem(DFConstants.FIELD_TYPE_DATE, DynFieldsNLS.constants()
				.dynamicField_Template_fieldType_date_option());
		fieldType.addItem(DFConstants.FIELD_TYPE_BOOL, DynFieldsNLS.constants()
				.dynamicField_Template_fieldType_boolean_option());
		fieldType.addItem(DFConstants.FIELD_TYPE_ENUM_S, DynFieldsNLS.constants()
				.dynamicField_Template_fieldType_singleEnumeration_option());
		fieldType.addItem(DFConstants.FIELD_TYPE_ENUM_M, DynFieldsNLS.constants()
				.dynamicField_Template_fieldType_multiEnumeration_option());
		fieldType.addItem(DFConstants.FIELD_TYPE_BIN, DynFieldsNLS.constants()
				.dynamicField_Template_fieldType_binary_option());
		fieldType.addItem(DFConstants.FIELD_TYPE_IMG, DynFieldsNLS.constants()
				.dynamicField_Template_fieldType_image_option());
		fieldType.addItem(DFConstants.FIELD_TYPE_GEO, DynFieldsNLS.constants()
				.dynamicField_Template_fieldType_geoField_option());
		fieldType.addItem(DFConstants.FIELD_TYPE_BCODE, DynFieldsNLS.constants()
				.dynamicField_Template_fieldType_barCode_option());
		// the value of fieldType affects the visibility of other fields
		fieldType.notifyChanges(requestFactory.getEventBus());
		parameters.setLabel(DynFieldsNLS.constants()
				.dynamicField_Template_field_parameters());
		parameters.setBoxWidth(180);
		// the visibility of parameters depends on the value of other fields
		parameters.addStyleName("dependentVisibility");
		
		formType.setLabel(DynFieldsNLS.constants().dynamicField_Template_field_formType());
		List<FormType> formTypes = formTypeUtil.getDynamicFieldFormTypes();
		for(FormType type:formTypes)
			formType.addItem(type.getValue(), type.getDisplayValue());		
		
//		templateCreator.setLabel(DynFieldsNLS.constants()
//				.dynamicField_Template_field_templateCreator());
		description.setLabel(DynFieldsNLS.constants()
				.dynamicField_Template_field_description());
		reason.setLabel(DynFieldsNLS.constants().dynamicField_Template_field_reason());
		isDefault.setLabel(DynFieldsNLS.constants()
				.dynamicField_Template_field_isDefault());
		isDefault.isStrict(true);

		/* Properties section widgets */
		propertiesSection.setGroupTitle(DynFieldsNLS.constants()
				.dynamicField_Template_group_properties());
		requiredValue.setLabel(DynFieldsNLS.constants()
				.dynamicField_Template_field_requiredValue());
		requiredValue.isStrict(true);
		fieldPosition.setLabel(DynFieldsNLS.constants()
				.dynamicField_Template_field_fieldPosition());
		fieldPosition.setBoxWidth(100);
		minimumValue.setLabel(DynFieldsNLS.constants()
				.dynamicField_Template_field_minimumValue());
		minimumValue.setBoxWidth(80);
		// the visibility of minimumValue depends on the value of other fields
		minimumValue.addStyleName("dependentVisibility");
		maximumValue.setLabel(DynFieldsNLS.constants()
				.dynamicField_Template_field_maximumValue());
		maximumValue.setBoxWidth(80);
		// the visibility of maximumValue depends on the value of other fields
		maximumValue.addStyleName("dependentVisibility");
		defaultValue.setLabel(DynFieldsNLS.constants()
				.dynamicField_Template_field_defaultValue());
		defaultValue.setBoxWidth(80);
		// the visibility of defaultValue depends on the value of other fields
		defaultValue.addStyleName("dependentVisibility");

		/* Administration section widgets */
		administrationSection.setGroupTitle(DynFieldsNLS.constants()
				.dynamicField_Template_group_administration());
		allUsers.setLabel(DynFieldsNLS.constants()
				.dynamicField_Template_field_allUsers());
		allUsers.isStrict(true);
		isActivated.setLabel(DynFieldsNLS.constants()
				.dynamicField_Template_field_isActivated());
		isActivated.isStrict(true);

	}

	/**
	 * Sets the edition mode
	 * @param isEdited true to enable the edition of the form
	 */
	public void setEdited(boolean isEdited) {

		if (!ProfileUtil.isAdmin())
			administrationSection.setVisible(false);

		/* Identification section widgets */
		fieldName.setEdited(isEdited);
		
		if(isNew)
			fieldType.setEdited(isEdited);
		else
			fieldType.setEdited(false);
		
		parameters.setEdited(isEdited);
		
		if (hideButtons) // in popup
			formType.setEdited(false);
		else
			formType.setEdited(isEdited);
//		if (hideButtons) // in popup
//			templateCreator.setEnabled(false);
//		else
//			templateCreator.setEnabled(isEdited);				
		description.setEdited(isEdited);
		reason.setEdited(isEdited);
		isDefault.setEdited(isEdited);

		/* Properties section widgets */
		requiredValue.setEdited(isEdited);
		fieldPosition.setEdited(isEdited);
		minimumValue.setEdited(isEdited);
		maximumValue.setEdited(isEdited);
		defaultValue.setEdited(isEdited);

		/* Administration section widgets */
		allUsers.setEdited(isEdited);
		isActivated.setEdited(isEdited);
	}


	/**
	 * Manages editor updates when a field value changes
	 */
	private void setFieldValueChangeHandler() {

		registrations.add(requestFactory.getEventBus().addHandler(
				FieldValueChangeEvent.TYPE,
				new FieldValueChangeEvent.Handler() {
					@Override
					public void onValueChange(ImogField<?> field) {

						// field dependent visibility management
						computeVisibility(field, false);

					}
				}));
	}

	/**
	 * Computes the field visibility
	 */
	public void computeVisibility(ImogField<?> source, boolean allValidation) {

		// the visibility of field parameters depends on the value of other fields
		if (allValidation || source.equals(fieldType)) {
			if ((fieldType.getValue() != null && fieldType.getValue().matches(
					DFConstants.FIELD_TYPE_ENUM_S + "|" + DFConstants.FIELD_TYPE_ENUM_M))) {
				parameters.setVisible(true);
			} else {
				parameters.setVisible(false);
			}
		}

		// the visibility of field minimumValue depends on the value of other fields
		if (allValidation || source.equals(fieldType)) {
			if ((fieldType.getValue() != null && fieldType.getValue().matches(
					DFConstants.FIELD_TYPE_INT + "|" + DFConstants.FIELD_TYPE_FLOAT + "|" + DFConstants.FIELD_TYPE_DATE))) {
				minimumValue.setVisible(true);
			} else {
				minimumValue.setVisible(false);
			}
		}

		// the visibility of field maximumValue depends on the value of other fields
		if (allValidation || source.equals(fieldType)) {
			if ((fieldType.getValue() != null && fieldType.getValue().matches(
					DFConstants.FIELD_TYPE_INT + "|" + DFConstants.FIELD_TYPE_FLOAT + "|" + DFConstants.FIELD_TYPE_DATE))) {
				maximumValue.setVisible(true);
			} else {
				maximumValue.setVisible(false);
			}
		}

		// the visibility of field defaultValue depends on the value of other fields
		if (allValidation || source.equals(fieldType)) {
			if ((fieldType.getValue() != null && fieldType.getValue().matches(
					DFConstants.FIELD_TYPE_INT + "|" + DFConstants.FIELD_TYPE_FLOAT + "|" + DFConstants.FIELD_TYPE_DATE
					+ "|" + DFConstants.FIELD_TYPE_BOOL + "|" + DFConstants.FIELD_TYPE_ENUM_S))) {
				defaultValue.setVisible(true);
			} else {
				defaultValue.setVisible(false);
			}
		}
	}
	
	/**
	 * 
	 * @param enumValues
	 */
	public void validateFields() {
		
		// fieldName is a required field
		if (fieldName.getValue() == null)
			delegate.recordError(BaseNLS.messages().error_required(), null,	"fieldName");
		// fieldType is a required field
		if (fieldType.getValue() == null)
			delegate.recordError(BaseNLS.messages().error_required(), null,	"fieldType");
		// formType is a required field
		if (formType.getValue() == null)
			delegate.recordError(BaseNLS.messages().error_required(), null,	"formType");
		
		String fieldTypeValue = fieldType.getValue();		
		if(fieldTypeValue!=null) {
			
			// validate enum values
			if(fieldTypeValue.equals(DFConstants.FIELD_TYPE_ENUM_S) || fieldTypeValue.equals(DFConstants.FIELD_TYPE_ENUM_M)) {
				
				boolean isValid = false;
				String enumValues = parameters.getValue();
				if(enumValues!=null && !enumValues.isEmpty()) {
					String[] valuesTab = enumValues.split(DFConstants.ENUM_SEPARATOR);
					if (valuesTab.length == 0 
							|| valuesTab.length == 1
							|| enumValues.startsWith(DFConstants.ENUM_SEPARATOR)
							|| enumValues.endsWith(DFConstants.ENUM_SEPARATOR))
						isValid = false;
					else {
						StringBuffer msg = new StringBuffer();
						for(String enumValue:valuesTab) {
							msg.append(" - " + enumValue + "\n");
						}
						isValid =  Window.confirm(DynFieldsNLS.constants().dynamicfield_enum_values() + "\n" + msg.toString());
					}	
				}
				if(!isValid)
					delegate.recordError(DynFieldsNLS.constants().dynamicfield_enum_wrong_format(), enumValues, "parameters");
			}
			
			// validate minimum value
			String minValueStr = minimumValue.getValue();
			Integer minInt = null;
			Double minDoubl = null;
			Date minDate = null;
			
			if(minValueStr!=null && !minValueStr.isEmpty()) {
				if(fieldTypeValue.equals(DFConstants.FIELD_TYPE_INT))
					minInt = validateIntValue(minValueStr, "minimumValue");
				
				if(fieldTypeValue.equals(DFConstants.FIELD_TYPE_FLOAT))
					minDoubl = validateFloatValue(minValueStr, "minimumValue");
				
				if(fieldTypeValue.equals(DFConstants.FIELD_TYPE_DATE))
					minDate = validateDateValue(minValueStr, "minimumValue");					
			}
			
			// validate maximum value
			String maxValueStr = maximumValue.getValue();
			Integer maxInt = null;
			Double maxDoubl = null;
			Date maxDate = null;
			
			if(maxValueStr!=null && !maxValueStr.isEmpty()) {	
				if(fieldTypeValue.equals(DFConstants.FIELD_TYPE_INT))
					maxInt = validateIntValue(maxValueStr, "maximumValue");
				
				if(fieldTypeValue.equals(DFConstants.FIELD_TYPE_FLOAT))
					maxDoubl = validateFloatValue(maxValueStr, "maximumValue");
				
				if(fieldTypeValue.equals(DFConstants.FIELD_TYPE_DATE))
					maxDate = validateDateValue(maxValueStr, "maximumValue");					
			}
			
			// validate that min<max
			if(minInt!=null && maxInt!=null && minInt>maxInt)
				delegate.recordError(BaseNLS.messages().error_min_max_num(), minValueStr + "-" + maxValueStr, "maximumValue");
			if(minDoubl!=null && maxDoubl!=null && minDoubl>maxDoubl)
				delegate.recordError(BaseNLS.messages().error_min_max_num(), minValueStr + "-" + maxValueStr, "maximumValue");
			if(minDate!=null && maxDate!=null && minDate.after(maxDate))
				delegate.recordError(BaseNLS.messages().error_min_max_date(), minValueStr + "-" + maxValueStr, "maximumValue");
			
			// validate default value
			String defaultValueStr = defaultValue.getValue();
			if(defaultValueStr!=null && !defaultValueStr.isEmpty()) {					
				if(fieldTypeValue.equals(DFConstants.FIELD_TYPE_INT))
					validateIntValue(defaultValueStr, "defaultValue");
				
				if(fieldTypeValue.equals(DFConstants.FIELD_TYPE_FLOAT))
					validateFloatValue(defaultValueStr, "defaultValue");
				
				if(fieldTypeValue.equals(DFConstants.FIELD_TYPE_DATE))
					validateDateValue(defaultValueStr, "defaultValue");		
				
				if(fieldTypeValue.equals(DFConstants.FIELD_TYPE_BOOL))
					validateBooleanValue(defaultValueStr, "defaultValue");	
				
				if(fieldTypeValue.equals(DFConstants.FIELD_TYPE_ENUM_S))
					validateEnumValue(defaultValueStr, "defaultValue");	
			}
			
			// validate position
			if(fieldPosition.getValueWithoutParseException()!=null && fieldPosition.getValueWithoutParseException()<1)
				delegate.recordError(DynFieldsNLS.constants().dynamicfield_index_error(), fieldPosition.getValueWithoutParseException(), "fieldPosition");			
		}
	}
	
	/**
	 * 
	 * @param intValue
	 */
	private Integer validateIntValue(String intValue, String field) {		
		try {
			return NumericUtil.parseToInteger(intValue);
		} catch (NumberFormatException e) {
			delegate.recordError(BaseNLS.messages().error_format_int(), intValue, field);
		}
		return null;
	}
	
	/**
	 * 
	 * @param floatValue
	 */
	private Double validateFloatValue(String floatValue, String field) {		
		try {
			return NumericUtil.parseToDouble(floatValue);
		} catch (NumberFormatException e) {
			delegate.recordError(BaseNLS.messages().error_format_float(), floatValue, field);
		}
		return null;
	}
	
	/**
	 * 
	 * @param dateValue
	 */
	private Date validateDateValue(String dateValue, String field) {
		
		if(field.equals(DynFieldsNLS.constants().dynamicField_Template_field_defaultValue()) && dateValue.equals(DFConstants.CURRENT_DATE))
			return null;
		
		try {
			return DateUtil.parseDate(dateValue);
		} catch (Exception e) {
			delegate.recordError(BaseNLS.messages().error_format_date(BaseNLS.constants().format_date()), dateValue, field);
		}
		return null;
	}
	
	/**
	 * 
	 * @param boolValue
	 */
	private void validateBooleanValue(String boolValue, String field) {
		if(!(boolValue.equals("true") || boolValue.equals("false")))
				delegate.recordError(BaseNLS.messages().error_format_bool(), boolValue, field);
	}
	
	/**
	 * 
	 * @param boolValue
	 */
	private void validateEnumValue(String enumValue, String field) {
		boolean isOk=false;
		String enumValues = parameters.getValue();
		if(enumValues!=null && !enumValues.isEmpty()) {
			String[] valuesTab = enumValues.split(DFConstants.ENUM_SEPARATOR);
			for(String valueTab:valuesTab) {
				if(enumValue.equals(valueTab))
					isOk=true;
			}
		}
		if(!isOk)
			delegate.recordError(DynFieldsNLS.constants().dynamicfield_enum_default_error(), enumValue, field);

	}

	/**
	 * Setter to inject a Doctor value
	 * @param value the value to be injected into the editor
	 * @param isLocked true if relation field shall be locked (non editable)
	 */
	public void setTemplateCreator(ImogActorProxy value, boolean isLocked) {
//		templateCreator.setLocked(isLocked);
		templateCreator.setValue(value);

	}
	
	public void setFormType(String value) {
		formType.setValue(value);
	}
	
	@Override
	public void setDelegate(EditorDelegate<DynamicFieldTemplateProxy> delegate) {
		this.delegate = delegate;
	}

	@Override
	protected void onUnload() {
		for (HandlerRegistration r : registrations)
			r.removeHandler();
		registrations.clear();
		super.onUnload();
	}

	@Override
	protected void onLoad() {
		setFieldValueChangeHandler();
		super.onLoad();
	}

	@Override
	public void showErrors(List<EditorError> errors) {
		if(errors!=null && errors.size()>0) {
			
			List<EditorError> fieldNameFieldErrors = new ArrayList<EditorError>();
			List<EditorError> fieldTypeFieldErrors = new ArrayList<EditorError>();
			List<EditorError> formTypeFieldErrors = new ArrayList<EditorError>();			
			List<EditorError> parametersFieldErrors = new ArrayList<EditorError>();		
			List<EditorError> minimumValueFieldErrors = new ArrayList<EditorError>();
			List<EditorError> maximumValueFieldErrors = new ArrayList<EditorError>();			
			List<EditorError> defaultValueFieldErrors = new ArrayList<EditorError>();
			List<EditorError> fieldPositionFieldErrors = new ArrayList<EditorError>();
			
			for (EditorError error : errors) {
				
				Object userData = error.getUserData();
				if(userData!=null && userData instanceof String) {					
					String field = (String)userData;
					
					if (field.equals("fieldName"))
						fieldNameFieldErrors.add(error);
					if (field.equals("fieldType"))
						fieldTypeFieldErrors.add(error);
					if (field.equals("formType"))
						formTypeFieldErrors.add(error);					
					if (field.equals("parameters"))
						parametersFieldErrors.add(error);
					if (field.equals("minimumValue"))
						minimumValueFieldErrors.add(error);
					if (field.equals("maximumValue"))
						maximumValueFieldErrors.add(error);
					if (field.equals("defaultValue"))
						defaultValueFieldErrors.add(error);
					if (field.equals("fieldPosition"))
						fieldPositionFieldErrors.add(error);
				}
			}
			
			if (fieldNameFieldErrors.size() > 0)
				fieldName.showErrors(fieldNameFieldErrors);
			if (fieldTypeFieldErrors.size() > 0)
				fieldType.showErrors(fieldTypeFieldErrors);
			if (formTypeFieldErrors.size() > 0)
				formType.showErrors(formTypeFieldErrors);			
			if (parametersFieldErrors.size() > 0)
				parameters.showErrors(parametersFieldErrors);
			if (minimumValueFieldErrors.size() > 0)
				minimumValue.showErrors(minimumValueFieldErrors);
			if (maximumValueFieldErrors.size() > 0)
				maximumValue.showErrors(maximumValueFieldErrors);
			if (defaultValueFieldErrors.size() > 0)
				defaultValue.showErrors(defaultValueFieldErrors);
			if (fieldPositionFieldErrors.size() > 0)
				fieldPosition.showErrors(fieldPositionFieldErrors);
		}
	}
}
