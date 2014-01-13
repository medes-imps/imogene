package org.imogene.admin.client.ui.filter;

import java.util.ArrayList;
import java.util.List;

import org.imogene.lib.common.constants.CriteriaConstants;
import org.imogene.web.client.dynamicfields.i18n.DynFieldsNLS;
import org.imogene.web.client.dynamicfields.ui.field.DFConstants;
import org.imogene.web.client.dynamicfields.ui.field.FormType;
import org.imogene.web.client.dynamicfields.ui.field.FormTypeUtil;
import org.imogene.web.client.dynamicfields.ui.renderer.DynFieldsRenderer;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.field.widget.ImogBooleanAsList;
import org.imogene.web.client.ui.table.filter.ImogFilterBox;
import org.imogene.web.client.ui.table.filter.ImogFilterPanel;
import org.imogene.web.client.util.BooleanUtil;
import org.imogene.web.client.util.FilterCriteria;
import org.imogene.web.client.util.ProfileUtil;
import org.imogene.web.shared.proxy.DynamicFieldTemplateProxy;

import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Filter panel to filter the DynamicField_Template entries
 * @author MEDES-IMPS
 */
public class DynamicFieldTemplateFilterPanel extends ImogFilterPanel {
	
	/* filter widgets */
	private TextBox fieldNameBox;
	private ImogFilterBox fieldNameFilterBox;
	private ListBox fieldTypeBox;
	private ImogFilterBox fieldTypeFilterBox;
	private ListBox formTypeBox;
	private ImogFilterBox formTypeFilterBox;
	private TextBox templateCreatorBox;
	private ImogFilterBox templateCreatorFilterBox;
	private TextBox descriptionBox;
	private ImogFilterBox descriptionFilterBox;
	private ImogBooleanAsList isDefaultBox;
	private ImogFilterBox isDefaultFilterBox;
	private IntegerBox fieldPositionBox;
	private ImogFilterBox fieldPositionFilterBox;
	private ImogBooleanAsList allUsersBox;
	private ImogFilterBox allUsersFilterBox;
	private ImogBooleanAsList isActivatedBox;
	private ImogFilterBox isActivatedFilterBox;

	public DynamicFieldTemplateFilterPanel(FormTypeUtil formTypeUtil) {
		super();
		setFieldReadAccess();
		populateFormTypes(formTypeUtil);
	}

	@Override
	public void resetFilterWidgets() {
		fieldNameBox.setValue(null);
		fieldTypeBox.setSelectedIndex(0);
		formTypeBox.setSelectedIndex(0);
		templateCreatorBox.setValue(null);
		descriptionBox.setValue(null);
		isDefaultBox.setValue(null);
		fieldPositionBox.setValue(null);
		allUsersBox.setValue(null);
		isActivatedBox.setValue(null);

	}

	@Override
	public void createFilterWidgets() {
		
		formTypeBox = new ListBox();
		formTypeBox.addItem("", BaseNLS.constants().enumeration_unknown());
		formTypeBox.setSelectedIndex(0);		
		formTypeFilterBox = new ImogFilterBox(formTypeBox);
		formTypeFilterBox.setFilterLabel(DynFieldsNLS.constants()
				.dynamicField_Template_field_formType());
		addTableFilter(formTypeFilterBox);

		fieldNameBox = new TextBox();
		fieldNameFilterBox = new ImogFilterBox(fieldNameBox);
		fieldNameFilterBox.setFilterLabel(DynFieldsNLS.constants()
				.dynamicField_Template_field_fieldName());
		addTableFilter(fieldNameFilterBox);

		fieldTypeBox = new ListBox();
		fieldTypeBox.addItem("", BaseNLS.constants().enumeration_unknown());
		fieldTypeBox.setSelectedIndex(0);
		fieldTypeBox.addItem(DynFieldsNLS.constants()
				.dynamicField_Template_fieldType_string_option(), DFConstants.FIELD_TYPE_STRING);
		fieldTypeBox.addItem(DynFieldsNLS.constants()
				.dynamicField_Template_fieldType_text_option(), DFConstants.FIELD_TYPE_TEXT);
		fieldTypeBox.addItem(DynFieldsNLS.constants()
				.dynamicField_Template_fieldType_integer_option(), DFConstants.FIELD_TYPE_INT);
		fieldTypeBox.addItem(DynFieldsNLS.constants()
				.dynamicField_Template_fieldType_float_option(), DFConstants.FIELD_TYPE_FLOAT);
		fieldTypeBox.addItem(DynFieldsNLS.constants()
				.dynamicField_Template_fieldType_date_option(), DFConstants.FIELD_TYPE_DATE);
		fieldTypeBox.addItem(DynFieldsNLS.constants()
				.dynamicField_Template_fieldType_boolean_option(), DFConstants.FIELD_TYPE_BOOL);
		fieldTypeBox.addItem(DynFieldsNLS.constants()
				.dynamicField_Template_fieldType_singleEnumeration_option(), DFConstants.FIELD_TYPE_ENUM_S);
		fieldTypeBox.addItem(DynFieldsNLS.constants()
				.dynamicField_Template_fieldType_multiEnumeration_option(), DFConstants.FIELD_TYPE_ENUM_M);
		fieldTypeBox.addItem(DynFieldsNLS.constants()
				.dynamicField_Template_fieldType_binary_option(), DFConstants.FIELD_TYPE_BIN);
		fieldTypeBox.addItem(DynFieldsNLS.constants()
				.dynamicField_Template_fieldType_image_option(), DFConstants.FIELD_TYPE_IMG);
		fieldTypeBox.addItem(DynFieldsNLS.constants()
				.dynamicField_Template_fieldType_geoField_option(), DFConstants.FIELD_TYPE_GEO);
		fieldTypeBox.addItem(DynFieldsNLS.constants()
				.dynamicField_Template_fieldType_barCode_option(), DFConstants.FIELD_TYPE_BCODE);
		fieldTypeFilterBox = new ImogFilterBox(fieldTypeBox);
		fieldTypeFilterBox.setFilterLabel(DynFieldsNLS.constants()
				.dynamicField_Template_field_fieldType());
		addTableFilter(fieldTypeFilterBox);

		templateCreatorBox = new TextBox();
		templateCreatorFilterBox = new ImogFilterBox(templateCreatorBox);
		templateCreatorFilterBox.setFilterLabel(DynFieldsNLS.constants()
				.dynamicField_Template_field_templateCreator());
		addTableFilter(templateCreatorFilterBox);

		descriptionBox = new TextBox();
		descriptionFilterBox = new ImogFilterBox(descriptionBox);
		descriptionFilterBox.setFilterLabel(DynFieldsNLS.constants()
				.dynamicField_Template_field_description());
		addTableFilter(descriptionFilterBox);
		
		fieldPositionBox = new IntegerBox();
		fieldPositionFilterBox = new ImogFilterBox(fieldPositionBox);
		fieldPositionFilterBox.setFilterLabel(DynFieldsNLS.constants()
				.dynamicField_Template_field_fieldPosition());
		addTableFilter(fieldPositionFilterBox);

		isDefaultBox = new ImogBooleanAsList();
		isDefaultFilterBox = new ImogFilterBox(isDefaultBox);
		isDefaultFilterBox.setFilterLabel(DynFieldsNLS.constants()
				.dynamicField_Template_field_s_isDefault());
		addTableFilter(isDefaultFilterBox);

		allUsersBox = new ImogBooleanAsList();
		allUsersFilterBox = new ImogFilterBox(allUsersBox);
		allUsersFilterBox.setFilterLabel(DynFieldsNLS.constants()
				.dynamicField_Template_field_s_allUsers());
		addTableFilter(allUsersFilterBox);

		isActivatedBox = new ImogBooleanAsList();
		isActivatedFilterBox = new ImogFilterBox(isActivatedBox);
		isActivatedFilterBox.setFilterLabel(DynFieldsNLS.constants()
				.dynamicField_Template_field_isActivated());
		addTableFilter(isActivatedFilterBox);

	}
	
	private void populateFormTypes(FormTypeUtil formTypeUtil) {
		List<FormType> formTypes = formTypeUtil.getDynamicFieldFormTypes();
		for(FormType type:formTypes)
			formTypeBox.addItem(type.getDisplayValue(), type.getValue());	
	}

	@Override
	public List<FilterCriteria> getFilterCriteria() {

		List<FilterCriteria> criteria = new ArrayList<FilterCriteria>();

		FilterCriteria fieldNameCrit = new FilterCriteria();
		fieldNameCrit.setField("fieldName");
		fieldNameCrit.setFieldDisplayName(DynFieldsNLS.constants()
				.dynamicField_Template_field_fieldName());
		fieldNameCrit.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
		fieldNameCrit.setValue(fieldNameBox.getValue());
		fieldNameCrit.setValueDisplayName(fieldNameBox.getValue());
		criteria.add(fieldNameCrit);

		FilterCriteria fieldTypeCrit = new FilterCriteria();
		fieldTypeCrit.setField("fieldType");
		fieldTypeCrit.setFieldDisplayName(DynFieldsNLS.constants()
				.dynamicField_Template_field_fieldType());
		fieldTypeCrit.setOperation(CriteriaConstants.STRING_OPERATOR_EQUAL);
		fieldTypeCrit.setValue(fieldTypeBox.getValue(fieldTypeBox
				.getSelectedIndex()));
		fieldTypeCrit
				.setValueDisplayName(DynFieldsRenderer.get()
						.getEnumDisplayValue(
								DynamicFieldTemplateProxy.class,
								"fieldType",
								fieldTypeBox.getValue(fieldTypeBox
										.getSelectedIndex())));
		criteria.add(fieldTypeCrit);

		FilterCriteria formTypeCrit = new FilterCriteria();
		formTypeCrit.setField("formType");
		formTypeCrit.setFieldDisplayName(DynFieldsNLS.constants()
				.dynamicField_Template_field_formType());
		formTypeCrit.setOperation(CriteriaConstants.STRING_OPERATOR_EQUAL);
		formTypeCrit.setValue(formTypeBox.getValue(formTypeBox
				.getSelectedIndex()));
		formTypeCrit.setValueDisplayName(DynFieldsRenderer.get()
				.getEnumDisplayValue(DynamicFieldTemplateProxy.class,
						"formType",
						formTypeBox.getValue(formTypeBox.getSelectedIndex())));
		criteria.add(formTypeCrit);

		FilterCriteria templateCreatorCrit = new FilterCriteria();
		templateCreatorCrit.setField("templateCreator.login");
		templateCreatorCrit.setFieldDisplayName(DynFieldsNLS.constants()
				.dynamicField_Template_field_templateCreator());
		templateCreatorCrit
				.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
		templateCreatorCrit.setValue(templateCreatorBox.getValue());
		templateCreatorCrit.setValueDisplayName(templateCreatorBox.getValue());
		criteria.add(templateCreatorCrit);

		FilterCriteria descriptionCrit = new FilterCriteria();
		descriptionCrit.setField("description");
		descriptionCrit.setFieldDisplayName(DynFieldsNLS.constants()
				.dynamicField_Template_field_description());
		descriptionCrit
				.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
		descriptionCrit.setValue(descriptionBox.getValue());
		descriptionCrit.setValueDisplayName(descriptionBox.getValue());
		criteria.add(descriptionCrit);

		FilterCriteria isDefaultCrit = new FilterCriteria();
		isDefaultCrit.setField("isDefault");
		isDefaultCrit.setFieldDisplayName(DynFieldsNLS.constants()
				.dynamicField_Template_field_isDefault());
		isDefaultCrit.setOperation(CriteriaConstants.BOOLEAN_OPERATOR_EQUAL);
		if (isDefaultBox.getValue() == null)
			isDefaultCrit.setValue(null);
		else
			isDefaultCrit.setValue(String.valueOf(isDefaultBox.getValue()));
		isDefaultCrit.setValueDisplayName(BooleanUtil
				.getBooleanDisplayValue(isDefaultBox.getValue()));
		criteria.add(isDefaultCrit);

		FilterCriteria fieldPositionCrit = new FilterCriteria();
		fieldPositionCrit.setField("fieldPosition");
		fieldPositionCrit.setFieldDisplayName(DynFieldsNLS.constants()
				.dynamicField_Template_field_fieldPosition());
		fieldPositionCrit.setOperation(CriteriaConstants.INT_OPERATOR_EQUAL);
		if (fieldPositionBox.getValue() == null)
			fieldPositionCrit.setValue(null);
		else
			fieldPositionCrit.setValue(String.valueOf(fieldPositionBox
					.getValue()));
		fieldPositionCrit.setValueDisplayName(String.valueOf(fieldPositionBox
				.getValue()));
		criteria.add(fieldPositionCrit);

		FilterCriteria allUsersCrit = new FilterCriteria();
		allUsersCrit.setField("allUsers");
		allUsersCrit.setFieldDisplayName(DynFieldsNLS.constants()
				.dynamicField_Template_field_allUsers());
		allUsersCrit.setOperation(CriteriaConstants.BOOLEAN_OPERATOR_EQUAL);
		if (allUsersBox.getValue() == null)
			allUsersCrit.setValue(null);
		else
			allUsersCrit.setValue(String.valueOf(allUsersBox.getValue()));
		allUsersCrit.setValueDisplayName(BooleanUtil
				.getBooleanDisplayValue(allUsersBox.getValue()));
		criteria.add(allUsersCrit);

		FilterCriteria isActivatedCrit = new FilterCriteria();
		isActivatedCrit.setField("isActivated");
		isActivatedCrit.setFieldDisplayName(DynFieldsNLS.constants()
				.dynamicField_Template_field_isActivated());
		isActivatedCrit.setOperation(CriteriaConstants.BOOLEAN_OPERATOR_EQUAL);
		if (isActivatedBox.getValue() == null)
			isActivatedCrit.setValue(null);
		else
			isActivatedCrit.setValue(String.valueOf(isActivatedBox.getValue()));
		isActivatedCrit.setValueDisplayName(BooleanUtil
				.getBooleanDisplayValue(isActivatedBox.getValue()));
		criteria.add(isActivatedCrit);

		return criteria;
	}

	/**
	 * Configures the visibility of the fields 
	 * in view mode depending on the user privileges
	 */
	public void setFieldReadAccess() {

		if (!ProfileUtil.isAdmin()) {
			allUsersFilterBox.setVisible(false);
			isActivatedFilterBox.setVisible(false);
		}
	}
}
