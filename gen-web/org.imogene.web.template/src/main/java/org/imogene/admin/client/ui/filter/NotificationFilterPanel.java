package org.imogene.admin.client.ui.filter;

import java.util.ArrayList;
import java.util.List;

import org.imogene.admin.client.AdminRenderer;
import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.admin.shared.proxy.NotificationProxy;
import org.imogene.lib.common.constants.CriteriaConstants;
import org.imogene.lib.common.constants.NotificationConstants;
import org.imogene.web.client.dynamicfields.ui.field.FormType;
import org.imogene.web.client.dynamicfields.ui.field.FormTypeUtil;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.table.filter.ImogFilterBox;
import org.imogene.web.client.ui.table.filter.ImogFilterPanel;
import org.imogene.web.client.util.FilterCriteria;
import org.imogene.web.client.util.ProfileUtil;

import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Filter panel to filter the Notification entries
 * @author MEDES-IMPS
 */
public class NotificationFilterPanel extends ImogFilterPanel {
	
	/* filter widgets */
	private TextBox nameBox;
	private ImogFilterBox nameFilterBox;
	private ListBox formTypeBox;
	private ImogFilterBox formTypeFilterBox;
	private ListBox methodBox;
	private ImogFilterBox methodFilterBox;
	private ListBox operationBox;
	private ImogFilterBox operationFilterBox;

	public NotificationFilterPanel(FormTypeUtil formTypeUtil) {
		super();
		setFieldReadAccess();
		populateFormTypes(formTypeUtil);
	}

	@Override
	public void resetFilterWidgets() {
		nameBox.setValue(null);
		formTypeBox.setSelectedIndex(0);
		methodBox.setSelectedIndex(0);
		operationBox.setSelectedIndex(0);

	}

	@Override
	public void createFilterWidgets() {
		
		formTypeBox = new ListBox();
		formTypeBox.addItem("", BaseNLS.constants().enumeration_unknown());
		formTypeBox.setSelectedIndex(0);
		formTypeFilterBox = new ImogFilterBox(formTypeBox);
		formTypeFilterBox.setFilterLabel(AdminNLS.constants()
				.notification_field_formType());
		addTableFilter(formTypeFilterBox);

		nameBox = new TextBox();
		nameFilterBox = new ImogFilterBox(nameBox);
		nameFilterBox.setFilterLabel(AdminNLS.constants().notification_field_name());
		addTableFilter(nameFilterBox);

		methodBox = new ListBox();
		methodBox.addItem("", BaseNLS.constants().enumeration_unknown());
		methodBox.setSelectedIndex(0);
		methodBox.addItem(AdminNLS.constants().notification_method_mail_option(),
				NotificationConstants.EMAIL_NOTIF);
		methodBox
				.addItem(AdminNLS.constants().notification_method_sMS_option(), NotificationConstants.SMS_NOTIF);
		methodFilterBox = new ImogFilterBox(methodBox);
		methodFilterBox.setFilterLabel(AdminNLS.constants()
				.notification_field_method());
		addTableFilter(methodFilterBox);

		operationBox = new ListBox();
		operationBox.addItem("", BaseNLS.constants().enumeration_unknown());
		operationBox.setSelectedIndex(0);
		operationBox.addItem(AdminNLS.constants()
				.notification_operation_create_option(), NotificationConstants.CREATE_OP);
		operationBox.addItem(AdminNLS.constants()
				.notification_operation_update_option(), NotificationConstants.UPDATE_OP);
		operationBox.addItem(AdminNLS.constants()
				.notification_operation_delete_option(), NotificationConstants.DELETE_OP);
		operationFilterBox = new ImogFilterBox(operationBox);
		operationFilterBox.setFilterLabel(AdminNLS.constants()
				.notification_field_operation());
		addTableFilter(operationFilterBox);

	}
	
	private void populateFormTypes(FormTypeUtil formTypeUtil) {
		List<FormType> formTypes = formTypeUtil.getFormTypes();
		for(FormType type:formTypes)
			formTypeBox.addItem(type.getDisplayValue(), type.getValue());	
	}

	@Override
	public List<FilterCriteria> getFilterCriteria() {

		List<FilterCriteria> criteria = new ArrayList<FilterCriteria>();

		FilterCriteria nameCrit = new FilterCriteria();
		nameCrit.setField("name");
		nameCrit.setFieldDisplayName(AdminNLS.constants().notification_field_name());
		nameCrit.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
		nameCrit.setValue(nameBox.getValue());
		nameCrit.setValueDisplayName(nameBox.getValue());
		criteria.add(nameCrit);

		FilterCriteria formTypeCrit = new FilterCriteria();
		formTypeCrit.setField("formType");
		formTypeCrit.setFieldDisplayName(AdminNLS.constants()
				.notification_field_formType());
		formTypeCrit.setOperation(CriteriaConstants.STRING_OPERATOR_EQUAL);
		formTypeCrit.setValue(formTypeBox.getValue(formTypeBox
				.getSelectedIndex()));
		formTypeCrit.setValueDisplayName(AdminRenderer.get()
				.getEnumDisplayValue(NotificationProxy.class, "formType",
						formTypeBox.getValue(formTypeBox.getSelectedIndex())));
		criteria.add(formTypeCrit);

		FilterCriteria methodCrit = new FilterCriteria();
		methodCrit.setField("method");
		methodCrit.setFieldDisplayName(AdminNLS.constants()
				.notification_field_method());
		methodCrit.setOperation(CriteriaConstants.STRING_OPERATOR_EQUAL);
		methodCrit.setValue(methodBox.getValue(methodBox.getSelectedIndex()));
		methodCrit.setValueDisplayName(AdminRenderer.get().getEnumDisplayValue(
				NotificationProxy.class, "method",
				methodBox.getValue(methodBox.getSelectedIndex())));
		criteria.add(methodCrit);

		FilterCriteria operationCrit = new FilterCriteria();
		operationCrit.setField("operation");
		operationCrit.setFieldDisplayName(AdminNLS.constants()
				.notification_field_operation());
		operationCrit.setOperation(CriteriaConstants.STRING_OPERATOR_EQUAL);
		operationCrit.setValue(operationBox.getValue(operationBox
				.getSelectedIndex()));
		operationCrit
				.setValueDisplayName(AdminRenderer.get().getEnumDisplayValue(
						NotificationProxy.class, "operation",
						operationBox.getValue(operationBox.getSelectedIndex())));
		criteria.add(operationCrit);

		return criteria;
	}

	/**
	 * Configures the visibility of the fields 
	 * in view mode depending on the user privileges
	 */
	public void setFieldReadAccess() {

		if (!ProfileUtil.isAdmin()) {
			nameFilterBox.setVisible(false);
			formTypeFilterBox.setVisible(false);
			methodFilterBox.setVisible(false);
			operationFilterBox.setVisible(false);
		}
	}
}
