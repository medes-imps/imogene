package org.imogene.admin.client.dataprovider;

import java.util.ArrayList;
import java.util.List;

import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.admin.shared.AdminRequestFactory;
import org.imogene.admin.shared.proxy.NotificationProxy;
import org.imogene.admin.shared.request.NotificationRequest;
import org.imogene.lib.common.constants.CriteriaConstants;
import org.imogene.lib.common.constants.NotificationConstants;
import org.imogene.web.client.dynamicfields.i18n.DynFieldsNLS;
import org.imogene.web.client.dynamicfields.ui.field.FormType;
import org.imogene.web.client.dynamicfields.ui.field.FormTypeUtil;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.table.ImogBeanDataProvider;
import org.imogene.web.shared.proxy.criteria.BasicCriteriaProxy;
import org.imogene.web.shared.proxy.criteria.ImogConjunctionProxy;
import org.imogene.web.shared.proxy.criteria.ImogCriterionProxy;
import org.imogene.web.shared.proxy.criteria.ImogDisjunctionProxy;
import org.imogene.web.shared.proxy.criteria.ImogJunctionProxy;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;

/**
 * Data provider to feed the Notification entry Table and Selection List
 * @author MEDES-IMPS
 */
public class NotificationDataProvider
		extends
			ImogBeanDataProvider<NotificationProxy> {

	private final AdminRequestFactory requestFactory;
	private boolean nonAffected = false;
	private boolean searchInReverse = false;
	private String property = null;
	private FormTypeUtil formTypeUtil;

	/**
	 * For Relation Fields
	 * Provides instances of entity Notification with pagination
	 */
	public NotificationDataProvider(AdminRequestFactory requestFactory, FormTypeUtil formTypeUtil) {
		this.requestFactory = requestFactory;
		this.formTypeUtil = formTypeUtil;
	}

	/**
	 * For Relation Fields
	 * Provides instances of entity Notification that have non affected values for a given property (RelationField with card==1) with pagination
	 * @param pProperty the property for which non affected values are searched
	 */
	public NotificationDataProvider(AdminRequestFactory requestFactory,
			String pProperty, FormTypeUtil formTypeUtil) {
		this.requestFactory = requestFactory;
		nonAffected = true;
		property = pProperty;
		this.formTypeUtil = formTypeUtil;
	}

	/**
	 * For Relation Fields
	 * Provides filtered instances of entity Notification that have non affected values for a given property (RelationField with card==1) with pagination
	 * @param pProperty the property for which non affected values are searched
	 * @param searchInReverse true for 1:1 relations, if the property for which non affected values are searched shall be looked in reverse relation
	 */
	public NotificationDataProvider(AdminRequestFactory requestFactory,
			String pProperty, boolean searchInReverse, FormTypeUtil formTypeUtil) {
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
	public Request<List<NotificationProxy>> getList(int start, int numRows) {

		NotificationRequest request = (NotificationRequest) getContext();
		Request<List<NotificationProxy>> result = null;

		if (isFiltered) {
			/* permanently filtered - hierarchical lists */
			if (filterCriteria != null) {

				if (searchCriterions != null) {
					/* permanent filter added to search criterion */
					if (nonAffected) {
						if (!searchInReverse)
							result = request.listNonAffectedNotification(start,
									numRows, "name", true, searchCriterions,
									property);
						else
							result = request
									.listNonAffectedNotificationReverse(start,
											numRows, "name", true,
											searchCriterions, property);
					} else
						result = request.listNotification(start, numRows,
								"name", true, searchCriterions);
				} else {
					/* permanent filter only */
					if (nonAffected) {
						if (!searchInReverse)
							result = request.listNonAffectedNotification(start,
									numRows, "name", true, filterCriteria,
									property);
						else
							result = request
									.listNonAffectedNotificationReverse(start,
											numRows, "name", true,
											filterCriteria, property);
					} else
						result = request.listNotification(start, numRows,
								"name", true, filterCriteria);
				}

			} else
				result = request.getNotificationEmptyList();
		} else {
			if (searchCriterions != null) {

				if (nonAffected) {
					if (!searchInReverse)
						result = request.listNonAffectedNotification(start,
								numRows, "name", true, searchCriterions,
								property);
					else
						result = request.listNonAffectedNotificationReverse(
								start, numRows, "name", true, searchCriterions,
								property);
				} else
					result = request.listNotification(start, numRows, "name",
							true, searchCriterions);
			} else {

				if (nonAffected) {
					if (!searchInReverse)
						result = request.listNonAffectedNotification(start,
								numRows, "name", true, property);
					else
						result = request.listNonAffectedNotificationReverse(
								start, numRows, "name", true, property);
				} else
					result = request.listNotification(start, numRows, "name",
							true);
			}
		}

		if (isFiltered) {
		}

		else {
		}

		return result;
	}

	/**
	 * Called by Dynatable
	 */
	@Override
	public Request<List<NotificationProxy>> getList(String property, int start,
			int numRows, boolean asc) {

		NotificationRequest request = (NotificationRequest) getContext();
		Request<List<NotificationProxy>> result = null;
		if (getSearchCriterions() == null)
			result = request.listNotification(start, numRows, property, asc);
		else
			result = request.listNotification(start, numRows, property, asc,
					getSearchCriterions());

		return result;
	}

	@Override
	public Request<Long> getTotalRowCount() {
		NotificationRequest request = (NotificationRequest) getContext();

		if (isFiltered) {
			/* permanently filtered - hierarchical lists */
			if (filterCriteria != null) {

				if (searchCriterions != null) {
					/* permanent filter added to search criterion */
					if (nonAffected) {
						if (!searchInReverse)
							return request.countNonAffectedNotification(
									property, searchCriterions);
						else
							return request.countNonAffectedNotificationReverse(
									property, searchCriterions);
					} else
						return request.countNotification(searchCriterions);
				} else {
					/* permanent filter only */
					if (nonAffected) {
						if (!searchInReverse)
							return request.countNonAffectedNotification(
									property, filterCriteria);
						else
							return request.countNonAffectedNotificationReverse(
									property, filterCriteria);
					} else
						return request.countNotification(filterCriteria);
				}

			} else
				return request.countNonAffectedNotification("id");
		} else {

			if (searchCriterions != null) {
				if (nonAffected) {
					if (!searchInReverse)
						return request.countNonAffectedNotification(property,
								searchCriterions);
					else
						return request.countNonAffectedNotificationReverse(
								property, searchCriterions);
				} else
					return request.countNotification(searchCriterions);
			} else {

				if (nonAffected) {
					if (!searchInReverse)
						return request.countNonAffectedNotification(property);
					else
						return request
								.countNonAffectedNotificationReverse(property);
				} else
					return request.countNotification();
			}
		}
	}

	@Override
	public RequestContext getEntityContext() {
		return requestFactory.notificationRequest();
	}

	@Override
	public String fullTextSearch(String text) {

		boolean fullTextSearch = false;
		StringBuffer buffer = new StringBuffer(BaseNLS.constants()
				.label_filtered() + " ");

		if (text == null || (text != null && text.equals(""))) {
			setSearchCriterions(null);
		} else {

			NotificationRequest request = (NotificationRequest) getContext();
			newRequest = false;

			ImogJunctionProxy disJunction = request
					.create(ImogDisjunctionProxy.class);
			List<ImogCriterionProxy> criterionList = new ArrayList<ImogCriterionProxy>();

			// Search field Name
			BasicCriteriaProxy nameCrit = request
					.create(BasicCriteriaProxy.class);
			nameCrit.setField("name");
			nameCrit.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
			nameCrit.setValue(text);
			buffer.append("(" + AdminNLS.constants().notification_field_name()
					+ ": " + text + ") " + SYMBOL_OR + " ");
			criterionList.add(nameCrit);
			
			// Search field FormType
			List<FormType> formTypes = formTypeUtil.getFormTypes();
			for(FormType type:formTypes) {
				
				if (text.toLowerCase().equals(type.getDisplayValue().toLowerCase())) {
					BasicCriteriaProxy formTypeCrit = request
							.create(BasicCriteriaProxy.class);
					formTypeCrit.setField("formType");
					formTypeCrit
							.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
					formTypeCrit.setValue(type.getValue());
					buffer.append("("
							+ DynFieldsNLS.constants()
									.dynamicField_Template_field_formType() + ": "
							+ text + ") " + SYMBOL_OR + " ");
					criterionList.add(formTypeCrit);
				}
			}	

			// Search field Method
			if (text.toLowerCase().equals(
					AdminNLS.constants().notification_method_mail_option()
							.toLowerCase())) {
				BasicCriteriaProxy methodCrit = request
						.create(BasicCriteriaProxy.class);
				methodCrit.setField("method");
				methodCrit
						.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
				methodCrit.setValue(NotificationConstants.EMAIL_NOTIF);
				buffer.append("(" + AdminNLS.constants().notification_field_method()
						+ ": " + text + ") " + SYMBOL_OR + " ");
				criterionList.add(methodCrit);
			}
			if (text.toLowerCase().equals(
					AdminNLS.constants().notification_method_sMS_option()
							.toLowerCase())) {
				BasicCriteriaProxy methodCrit = request
						.create(BasicCriteriaProxy.class);
				methodCrit.setField("method");
				methodCrit
						.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
				methodCrit.setValue(NotificationConstants.SMS_NOTIF);
				buffer.append("(" + AdminNLS.constants().notification_field_method()
						+ ": " + text + ") " + SYMBOL_OR + " ");
				criterionList.add(methodCrit);
			}

			// Search field Operation
			if (text.toLowerCase().equals(
					AdminNLS.constants().notification_operation_create_option()
							.toLowerCase())) {
				BasicCriteriaProxy operationCrit = request
						.create(BasicCriteriaProxy.class);
				operationCrit.setField("operation");
				operationCrit
						.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
				operationCrit.setValue(NotificationConstants.CREATE_OP);
				buffer.append("("
						+ AdminNLS.constants().notification_field_operation() + ": "
						+ text + ") " + SYMBOL_OR + " ");
				criterionList.add(operationCrit);
			}
			if (text.toLowerCase().equals(
					AdminNLS.constants().notification_operation_update_option()
							.toLowerCase())) {
				BasicCriteriaProxy operationCrit = request
						.create(BasicCriteriaProxy.class);
				operationCrit.setField("operation");
				operationCrit
						.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
				operationCrit.setValue(NotificationConstants.UPDATE_OP);
				buffer.append("("
						+ AdminNLS.constants().notification_field_operation() + ": "
						+ text + ") " + SYMBOL_OR + " ");
				criterionList.add(operationCrit);
			}
			if (text.toLowerCase().equals(
					AdminNLS.constants().notification_operation_delete_option()
							.toLowerCase())) {
				BasicCriteriaProxy operationCrit = request
						.create(BasicCriteriaProxy.class);
				operationCrit.setField("operation");
				operationCrit
						.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
				operationCrit.setValue(NotificationConstants.DELETE_OP);
				buffer.append("("
						+ AdminNLS.constants().notification_field_operation() + ": "
						+ text + ") " + SYMBOL_OR + " ");
				criterionList.add(operationCrit);
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
