package org.imogene.admin.client.dataprovider;

import java.util.ArrayList;
import java.util.List;

import org.imogene.admin.shared.AdminRequestFactory;
import org.imogene.lib.common.constants.CriteriaConstants;
import org.imogene.web.client.dynamicfields.i18n.DynFieldsNLS;
import org.imogene.web.client.dynamicfields.ui.field.DFConstants;
import org.imogene.web.client.dynamicfields.ui.field.FormType;
import org.imogene.web.client.dynamicfields.ui.field.FormTypeUtil;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.table.ImogBeanDataProvider;
import org.imogene.web.shared.proxy.DynamicFieldTemplateProxy;
import org.imogene.web.shared.proxy.criteria.BasicCriteriaProxy;
import org.imogene.web.shared.proxy.criteria.ImogConjunctionProxy;
import org.imogene.web.shared.proxy.criteria.ImogCriterionProxy;
import org.imogene.web.shared.proxy.criteria.ImogDisjunctionProxy;
import org.imogene.web.shared.proxy.criteria.ImogJunctionProxy;
import org.imogene.web.shared.request.DynamicFieldTemplateRequest;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;

/**
 * Data provider to feed the DynamicFieldTemplate entry Table and Selection List
 * @author MEDES-IMPS
 */
public class DynamicFieldTemplateDataProvider
		extends
			ImogBeanDataProvider<DynamicFieldTemplateProxy> {

	private final AdminRequestFactory requestFactory;
	private boolean nonAffected = false;
	private boolean searchInReverse = false;
	private String property = null;
	private FormTypeUtil formTypeUtil;

	/**
	 * For Relation Fields
	 * Provides instances of entity DynamicFieldTemplate with pagination
	 */
	public DynamicFieldTemplateDataProvider(
			AdminRequestFactory requestFactory, FormTypeUtil formTypeUtil) {
		this.requestFactory = requestFactory;
		this.formTypeUtil = formTypeUtil;
	}

	/**
	 * For Relation Fields
	 * Provides instances of entity DynamicFieldTemplate that have non affected values for a given property (RelationField with card==1) with pagination
	 * @param pProperty the property for which non affected values are searched
	 */
	public DynamicFieldTemplateDataProvider(
			AdminRequestFactory requestFactory, String pProperty, FormTypeUtil formTypeUtil) {
		this.requestFactory = requestFactory;
		nonAffected = true;
		property = pProperty;
		this.formTypeUtil = formTypeUtil;
	}

	/**
	 * For Relation Fields
	 * Provides filtered instances of entity DynamicFieldTemplate that have non affected values for a given property (RelationField with card==1) with pagination
	 * @param pProperty the property for which non affected values are searched
	 * @param searchInReverse true for 1:1 relations, if the property for which non affected values are searched shall be looked in reverse relation
	 */
	public DynamicFieldTemplateDataProvider(
			AdminRequestFactory requestFactory, String pProperty,
			boolean searchInReverse, FormTypeUtil formTypeUtil) {
		this.requestFactory = requestFactory;
		nonAffected = true;
		property = pProperty;
		this.searchInReverse = searchInReverse;
		this.formTypeUtil = formTypeUtil;
	}

	/**
	 * Called by Relation Boxes
	 */
	@Override
	public Request<List<DynamicFieldTemplateProxy>> getList(int start,
			int numRows) {

		DynamicFieldTemplateRequest request = (DynamicFieldTemplateRequest) getContext();
		Request<List<DynamicFieldTemplateProxy>> result = null;

		if (isFiltered) {
			/* permanently filtered - hierarchical lists */
			if (filterCriteria != null) {

				if (searchCriterions != null) {
					/* permanent filter added to search criterion */
						result = request.listDynamicFieldTemplate(start,
								numRows, "formType", false, searchCriterions);
				} else {
					/* permanent filter only */
						result = request.listDynamicFieldTemplate(start,
								numRows, "formType", false, filterCriteria);
				}

			} else
				result = request.getDynamicFieldTemplateEmptyList();
		} else {
			if (searchCriterions != null) {
					result = request.listDynamicFieldTemplate(start, numRows,
							"formType", false, searchCriterions);
			} else {
					result = request.listDynamicFieldTemplate(start, numRows,
							"formType", false);
			}
		}

		if (isFiltered) {
			result.with("templateCreator");
		}

		else {
		}

		return result;
	}

	/**
	 * Called by Dynatable
	 */
	@Override
	public Request<List<DynamicFieldTemplateProxy>> getList(String property,
			int start, int numRows, boolean asc) {

		DynamicFieldTemplateRequest request = (DynamicFieldTemplateRequest) getContext();
		Request<List<DynamicFieldTemplateProxy>> result = null;
		if (getSearchCriterions() == null)
			result = request.listDynamicFieldTemplate(start, numRows,
					property, asc);
		else
			result = request.listDynamicFieldTemplate(start, numRows,
					property, asc, getSearchCriterions());
		result.with("templateCreator");

		return result;
	}

	@Override
	public Request<Long> getTotalRowCount() {
		DynamicFieldTemplateRequest request = (DynamicFieldTemplateRequest) getContext();

		if (isFiltered) {
			/* permanently filtered - hierarchical lists */
			if (filterCriteria != null) {

				if (searchCriterions != null) {
					/* permanent filter added to search criterion */
					if (nonAffected) {
						if (!searchInReverse)
							return request
									.countNonAffectedDynamicFieldTemplate(
											property, searchCriterions);
						else
							return request
									.countNonAffectedDynamicFieldTemplateReverse(
											property, searchCriterions);
					} else
						return request
								.countDynamicFieldTemplate(searchCriterions);
				} else {
					/* permanent filter only */
					if (nonAffected) {
						if (!searchInReverse)
							return request
									.countNonAffectedDynamicFieldTemplate(
											property, filterCriteria);
						else
							return request
									.countNonAffectedDynamicFieldTemplateReverse(
											property, filterCriteria);
					} else
						return request
								.countDynamicFieldTemplate(filterCriteria);
				}

			} else
				return request.countNonAffectedDynamicFieldTemplate("id");
		} else {

			if (searchCriterions != null) {
				if (nonAffected) {
					if (!searchInReverse)
						return request.countNonAffectedDynamicFieldTemplate(
								property, searchCriterions);
					else
						return request
								.countNonAffectedDynamicFieldTemplateReverse(
										property, searchCriterions);
				} else
					return request.countDynamicFieldTemplate(searchCriterions);
			} else {

				if (nonAffected) {
					if (!searchInReverse)
						return request
								.countNonAffectedDynamicFieldTemplate(property);
					else
						return request
								.countNonAffectedDynamicFieldTemplateReverse(property);
				} else
					return request.countDynamicFieldTemplate();
			}
		}
	}

	@Override
	public RequestContext getEntityContext() {
		return requestFactory.dynamicFieldTemplateRequest();
	}

	@Override
	public String fullTextSearch(String text) {

		boolean fullTextSearch = false;
		StringBuffer buffer = new StringBuffer(BaseNLS.constants()
				.label_filtered() + " ");

		if (text == null || (text != null && text.equals(""))) {
			setSearchCriterions(null);
		} else {

			DynamicFieldTemplateRequest request = (DynamicFieldTemplateRequest) getContext();
			newRequest = false;

			ImogJunctionProxy disJunction = request
					.create(ImogDisjunctionProxy.class);
			List<ImogCriterionProxy> criterionList = new ArrayList<ImogCriterionProxy>();

			// Search field FieldName
			BasicCriteriaProxy fieldNameCrit = request
					.create(BasicCriteriaProxy.class);
			fieldNameCrit.setField("fieldName");
			fieldNameCrit
					.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
			fieldNameCrit.setValue(text);
			buffer.append("("
					+ DynFieldsNLS.constants().dynamicField_Template_field_fieldName()
					+ ": " + text + ") " + SYMBOL_OR + " ");
			criterionList.add(fieldNameCrit);

			// Search field FieldType
			if (text.toLowerCase().equals(
					DynFieldsNLS.constants()
							.dynamicField_Template_fieldType_string_option()
							.toLowerCase())) {
				BasicCriteriaProxy fieldTypeCrit = request
						.create(BasicCriteriaProxy.class);
				fieldTypeCrit.setField("fieldType");
				fieldTypeCrit
						.setOperation(CriteriaConstants.STRING_OPERATOR_EQUAL);
				fieldTypeCrit.setValue(DFConstants.FIELD_TYPE_STRING);
				buffer.append("("
						+ DynFieldsNLS.constants()
								.dynamicField_Template_field_fieldType() + ": "
						+ text + ") " + SYMBOL_OR + " ");
				criterionList.add(fieldTypeCrit);
			}
			if (text.toLowerCase().equals(
					DynFieldsNLS.constants()
							.dynamicField_Template_fieldType_text_option()
							.toLowerCase())) {
				BasicCriteriaProxy fieldTypeCrit = request
						.create(BasicCriteriaProxy.class);
				fieldTypeCrit.setField("fieldType");
				fieldTypeCrit
						.setOperation(CriteriaConstants.STRING_OPERATOR_EQUAL);
				fieldTypeCrit.setValue(DFConstants.FIELD_TYPE_TEXT);
				buffer.append("("
						+ DynFieldsNLS.constants()
								.dynamicField_Template_field_fieldType() + ": "
						+ text + ") " + SYMBOL_OR + " ");
				criterionList.add(fieldTypeCrit);
			}
			if (text.toLowerCase().equals(
					DynFieldsNLS.constants()
							.dynamicField_Template_fieldType_integer_option()
							.toLowerCase())) {
				BasicCriteriaProxy fieldTypeCrit = request
						.create(BasicCriteriaProxy.class);
				fieldTypeCrit.setField("fieldType");
				fieldTypeCrit
						.setOperation(CriteriaConstants.STRING_OPERATOR_EQUAL);
				fieldTypeCrit.setValue(DFConstants.FIELD_TYPE_INT);
				buffer.append("("
						+ DynFieldsNLS.constants()
								.dynamicField_Template_field_fieldType() + ": "
						+ text + ") " + SYMBOL_OR + " ");
				criterionList.add(fieldTypeCrit);
			}
			if (text.toLowerCase().equals(
					DynFieldsNLS.constants()
							.dynamicField_Template_fieldType_float_option()
							.toLowerCase())) {
				BasicCriteriaProxy fieldTypeCrit = request
						.create(BasicCriteriaProxy.class);
				fieldTypeCrit.setField("fieldType");
				fieldTypeCrit
						.setOperation(CriteriaConstants.STRING_OPERATOR_EQUAL);
				fieldTypeCrit.setValue(DFConstants.FIELD_TYPE_FLOAT);
				buffer.append("("
						+ DynFieldsNLS.constants()
								.dynamicField_Template_field_fieldType() + ": "
						+ text + ") " + SYMBOL_OR + " ");
				criterionList.add(fieldTypeCrit);
			}
			if (text.toLowerCase().equals(
					DynFieldsNLS.constants()
							.dynamicField_Template_fieldType_date_option()
							.toLowerCase())) {
				BasicCriteriaProxy fieldTypeCrit = request
						.create(BasicCriteriaProxy.class);
				fieldTypeCrit.setField("fieldType");
				fieldTypeCrit
						.setOperation(CriteriaConstants.STRING_OPERATOR_EQUAL);
				fieldTypeCrit.setValue(DFConstants.FIELD_TYPE_DATE);
				buffer.append("("
						+ DynFieldsNLS.constants()
								.dynamicField_Template_field_fieldType() + ": "
						+ text + ") " + SYMBOL_OR + " ");
				criterionList.add(fieldTypeCrit);
			}
			if (text.toLowerCase().equals(
					DynFieldsNLS.constants()
							.dynamicField_Template_fieldType_boolean_option()
							.toLowerCase())) {
				BasicCriteriaProxy fieldTypeCrit = request
						.create(BasicCriteriaProxy.class);
				fieldTypeCrit.setField("fieldType");
				fieldTypeCrit
						.setOperation(CriteriaConstants.STRING_OPERATOR_EQUAL);
				fieldTypeCrit.setValue(DFConstants.FIELD_TYPE_BOOL);
				buffer.append("("
						+ DynFieldsNLS.constants()
								.dynamicField_Template_field_fieldType() + ": "
						+ text + ") " + SYMBOL_OR + " ");
				criterionList.add(fieldTypeCrit);
			}
			if (text.toLowerCase()
					.equals(DynFieldsNLS
							.constants()
							.dynamicField_Template_fieldType_singleEnumeration_option()
							.toLowerCase())) {
				BasicCriteriaProxy fieldTypeCrit = request
						.create(BasicCriteriaProxy.class);
				fieldTypeCrit.setField("fieldType");
				fieldTypeCrit
						.setOperation(CriteriaConstants.STRING_OPERATOR_EQUAL);
				fieldTypeCrit.setValue(DFConstants.FIELD_TYPE_ENUM_S);
				buffer.append("("
						+ DynFieldsNLS.constants()
								.dynamicField_Template_field_fieldType() + ": "
						+ text + ") " + SYMBOL_OR + " ");
				criterionList.add(fieldTypeCrit);
			}
			if (text.toLowerCase()
					.equals(DynFieldsNLS
							.constants()
							.dynamicField_Template_fieldType_multiEnumeration_option()
							.toLowerCase())) {
				BasicCriteriaProxy fieldTypeCrit = request
						.create(BasicCriteriaProxy.class);
				fieldTypeCrit.setField("fieldType");
				fieldTypeCrit
						.setOperation(CriteriaConstants.STRING_OPERATOR_EQUAL);
				fieldTypeCrit.setValue(DFConstants.FIELD_TYPE_ENUM_M);
				buffer.append("("
						+ DynFieldsNLS.constants()
								.dynamicField_Template_field_fieldType() + ": "
						+ text + ") " + SYMBOL_OR + " ");
				criterionList.add(fieldTypeCrit);
			}
			if (text.toLowerCase().equals(
					DynFieldsNLS.constants()
							.dynamicField_Template_fieldType_binary_option()
							.toLowerCase())) {
				BasicCriteriaProxy fieldTypeCrit = request
						.create(BasicCriteriaProxy.class);
				fieldTypeCrit.setField("fieldType");
				fieldTypeCrit
						.setOperation(CriteriaConstants.STRING_OPERATOR_EQUAL);
				fieldTypeCrit.setValue(DFConstants.FIELD_TYPE_BIN);
				buffer.append("("
						+ DynFieldsNLS.constants()
								.dynamicField_Template_field_fieldType() + ": "
						+ text + ") " + SYMBOL_OR + " ");
				criterionList.add(fieldTypeCrit);
			}
			if (text.toLowerCase().equals(
					DynFieldsNLS.constants()
							.dynamicField_Template_fieldType_image_option()
							.toLowerCase())) {
				BasicCriteriaProxy fieldTypeCrit = request
						.create(BasicCriteriaProxy.class);
				fieldTypeCrit.setField("fieldType");
				fieldTypeCrit
						.setOperation(CriteriaConstants.STRING_OPERATOR_EQUAL);
				fieldTypeCrit.setValue(DFConstants.FIELD_TYPE_IMG);
				buffer.append("("
						+ DynFieldsNLS.constants()
								.dynamicField_Template_field_fieldType() + ": "
						+ text + ") " + SYMBOL_OR + " ");
				criterionList.add(fieldTypeCrit);
			}
			if (text.toLowerCase().equals(
					DynFieldsNLS.constants()
							.dynamicField_Template_fieldType_geoField_option()
							.toLowerCase())) {
				BasicCriteriaProxy fieldTypeCrit = request
						.create(BasicCriteriaProxy.class);
				fieldTypeCrit.setField("fieldType");
				fieldTypeCrit
						.setOperation(CriteriaConstants.STRING_OPERATOR_EQUAL);
				fieldTypeCrit.setValue(DFConstants.FIELD_TYPE_GEO);
				buffer.append("("
						+ DynFieldsNLS.constants()
								.dynamicField_Template_field_fieldType() + ": "
						+ text + ") " + SYMBOL_OR + " ");
				criterionList.add(fieldTypeCrit);
			}
			if (text.toLowerCase().equals(
					DynFieldsNLS.constants()
							.dynamicField_Template_fieldType_barCode_option()
							.toLowerCase())) {
				BasicCriteriaProxy fieldTypeCrit = request
						.create(BasicCriteriaProxy.class);
				fieldTypeCrit.setField("fieldType");
				fieldTypeCrit
						.setOperation(CriteriaConstants.STRING_OPERATOR_EQUAL);
				fieldTypeCrit.setValue(DFConstants.FIELD_TYPE_BCODE);
				buffer.append("("
						+ DynFieldsNLS.constants()
								.dynamicField_Template_field_fieldType() + ": "
						+ text + ") " + SYMBOL_OR + " ");
				criterionList.add(fieldTypeCrit);
			}

			// Search field FormType
			List<FormType> formTypes = formTypeUtil.getDynamicFieldFormTypes();
			for(FormType type:formTypes) {
				
				if (text.toLowerCase().equals(type.getDisplayValue().toLowerCase())) {
					BasicCriteriaProxy formTypeCrit = request
							.create(BasicCriteriaProxy.class);
					formTypeCrit.setField("formType");
					formTypeCrit
							.setOperation(CriteriaConstants.STRING_OPERATOR_EQUAL);
					formTypeCrit.setValue(type.getValue());
					buffer.append("("
							+ DynFieldsNLS.constants()
									.dynamicField_Template_field_formType() + ": "
							+ text + ") " + SYMBOL_OR + " ");
					criterionList.add(formTypeCrit);
				}
			}			

			// Search field TemplateCreator
			BasicCriteriaProxy templateCreatorCrit = request
					.create(BasicCriteriaProxy.class);
			templateCreatorCrit.setField("templateCreator.login");
			templateCreatorCrit
					.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
			templateCreatorCrit.setValue(text);
			buffer.append("("
					+ DynFieldsNLS.constants()
							.dynamicField_Template_field_templateCreator()
					+ ": " + text + ") " + SYMBOL_OR + " ");
			criterionList.add(templateCreatorCrit);

			// Search field Description
			BasicCriteriaProxy descriptionCrit = request
					.create(BasicCriteriaProxy.class);
			descriptionCrit.setField("description");
			descriptionCrit
					.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
			descriptionCrit.setValue(text);
			buffer.append("("
					+ DynFieldsNLS.constants().dynamicField_Template_field_description()
					+ ": " + text + ") " + SYMBOL_OR + " ");
			criterionList.add(descriptionCrit);

			// Search field IsDefault
			if (text.toLowerCase().equals("true")
					|| text.toLowerCase().equals("false")) {
				BasicCriteriaProxy isDefaultCrit = request
						.create(BasicCriteriaProxy.class);
				isDefaultCrit.setField("isDefault");
				isDefaultCrit
						.setOperation(CriteriaConstants.BOOLEAN_OPERATOR_EQUAL);
				isDefaultCrit.setValue(text);
				buffer.append("("
						+ DynFieldsNLS.constants()
								.dynamicField_Template_field_isDefault() + ": "
						+ text + ") " + SYMBOL_OR + " ");
				criterionList.add(isDefaultCrit);
			}

			// Search field FieldPosition
			try {
				Integer.valueOf(text);
				BasicCriteriaProxy fieldPositionCrit = request
						.create(BasicCriteriaProxy.class);
				fieldPositionCrit.setField("fieldPosition");
				fieldPositionCrit
						.setOperation(CriteriaConstants.INT_OPERATOR_EQUAL);
				fieldPositionCrit.setValue(text);
				buffer.append("("
						+ DynFieldsNLS.constants()
								.dynamicField_Template_field_fieldPosition()
						+ ": " + text + ") " + SYMBOL_OR + " ");
				criterionList.add(fieldPositionCrit);
			} catch (Exception ex) {/*criteria not added*/
			}

			// Search field AllUsers
			if (text.toLowerCase().equals("true")
					|| text.toLowerCase().equals("false")) {
				BasicCriteriaProxy allUsersCrit = request
						.create(BasicCriteriaProxy.class);
				allUsersCrit.setField("allUsers");
				allUsersCrit
						.setOperation(CriteriaConstants.BOOLEAN_OPERATOR_EQUAL);
				allUsersCrit.setValue(text);
				buffer.append("("
						+ DynFieldsNLS.constants()
								.dynamicField_Template_field_allUsers() + ": "
						+ text + ") " + SYMBOL_OR + " ");
				criterionList.add(allUsersCrit);
			}

			// Search field IsActivated
			if (text.toLowerCase().equals("true")
					|| text.toLowerCase().equals("false")) {
				BasicCriteriaProxy isActivatedCrit = request
						.create(BasicCriteriaProxy.class);
				isActivatedCrit.setField("isActivated");
				isActivatedCrit
						.setOperation(CriteriaConstants.BOOLEAN_OPERATOR_EQUAL);
				isActivatedCrit.setValue(text);
				buffer.append("("
						+ DynFieldsNLS.constants()
								.dynamicField_Template_field_isActivated()
						+ ": " + text + ") " + SYMBOL_OR + " ");
				criterionList.add(isActivatedCrit);
			}

			disJunction.setCriterions(criterionList);
			fullTextSearch = true;

			//add FilterCriteria if exists
			if (isFiltered && filterCriteria != null) {
				ImogJunctionProxy conJunctionFilt = request
						.create(ImogConjunctionProxy.class);
				List<ImogCriterionProxy> criterionListFilt = new ArrayList<ImogCriterionProxy>();
				criterionListFilt.add(filterCriteria);
				criterionListFilt.add(disJunction);
				conJunctionFilt.setCriterions(criterionListFilt);
				setSearchCriterions(conJunctionFilt);
			} else
				setSearchCriterions(disJunction);

		}
		if (fullTextSearch) {
			String message = buffer.toString();
			int lastSymbolIndex = message.lastIndexOf(SYMBOL_OR);
			return message.substring(0, lastSymbolIndex);
		} else
			return null;
	}

}
