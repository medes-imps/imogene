package org.imogene.admin.client.ui.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.imogene.admin.client.ui.filter.DynamicFieldTemplateFilterPanel;
import org.imogene.admin.shared.AdminRequestFactory;
import org.imogene.web.client.dynamicfields.i18n.DynFieldsNLS;
import org.imogene.web.client.dynamicfields.ui.field.FormTypeUtil;
import org.imogene.web.client.dynamicfields.ui.renderer.DynFieldsRenderer;
import org.imogene.web.client.event.ListDynamicFieldTemplateEvent;
import org.imogene.web.client.event.SelectionChangedInTableEvent;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.table.ImogBeanDataProvider;
import org.imogene.web.client.ui.table.ImogColumn;
import org.imogene.web.client.ui.table.ImogDynaTable;
import org.imogene.web.client.ui.table.filter.ImogFilterPanel;
import org.imogene.web.client.util.BooleanUtil;
import org.imogene.web.client.util.ProfileUtil;
import org.imogene.web.client.util.TokenHelper;
import org.imogene.web.shared.proxy.DynamicFieldTemplateProxy;
import org.imogene.web.shared.request.DynamicFieldTemplateRequest;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PushButton;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

/**
 * Composite that displays the list of DynamicField_Template entries
 * @author MEDES-IMPS
 */
public class DynamicFieldTemplateDynaTable extends ImogDynaTable<DynamicFieldTemplateProxy> {

	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();

	private PushButton deleteButton;

	public DynamicFieldTemplateDynaTable(AdminRequestFactory requestFactory, ImogBeanDataProvider<DynamicFieldTemplateProxy> provider, boolean checkBoxesVisible) {
		super(requestFactory, provider, checkBoxesVisible);
	}

	public ImogFilterPanel getFilterPanel(FormTypeUtil formTypeUtil) {
		ImogFilterPanel filterPanel = new DynamicFieldTemplateFilterPanel(formTypeUtil);
		super.configureFilterPanel(filterPanel);
		return filterPanel;
	}

	/**
	 * 
	 */
	@Override
	protected void setColumns() {

		if (ProfileUtil.isAdmin()) {

			Column<DynamicFieldTemplateProxy, String> formTypeColumn = new FormTypeColumn();
			formTypeColumn.setSortable(true);
			table.addColumn(formTypeColumn, DynFieldsNLS.constants().dynamicField_Template_field_s_formType());

			Column<DynamicFieldTemplateProxy, String> fieldNameColumn = new FieldNameColumn();
			fieldNameColumn.setSortable(true);
			table.addColumn(fieldNameColumn, DynFieldsNLS.constants().dynamicField_Template_field_s_fieldName());

			Column<DynamicFieldTemplateProxy, String> fieldTypeColumn = new FieldTypeColumn();
			fieldTypeColumn.setSortable(true);
			table.addColumn(fieldTypeColumn, DynFieldsNLS.constants().dynamicField_Template_field_s_fieldType());

			Column<DynamicFieldTemplateProxy, String> templateCreatorColumn = new TemplateCreatorColumn();
			templateCreatorColumn.setSortable(true);
			table.addColumn(templateCreatorColumn, DynFieldsNLS.constants().dynamicField_Template_field_s_templateCreator());

			Column<DynamicFieldTemplateProxy, String> descriptionColumn = new DescriptionColumn();
			descriptionColumn.setSortable(true);
			table.addColumn(descriptionColumn, DynFieldsNLS.constants().dynamicField_Template_field_s_description());

			Column<DynamicFieldTemplateProxy, String> fieldPositionColumn = new FieldPositionColumn();
			fieldPositionColumn.setSortable(true);
			table.addColumn(fieldPositionColumn, DynFieldsNLS.constants().dynamicField_Template_field_s_fieldPosition());

			Column<DynamicFieldTemplateProxy, String> isDefaultColumn = new IsDefaultColumn();
			isDefaultColumn.setSortable(true);
			table.addColumn(isDefaultColumn, DynFieldsNLS.constants().dynamicField_Template_field_s_isDefault());

			Column<DynamicFieldTemplateProxy, String> allUsersColumn = new AllUsersColumn();
			allUsersColumn.setSortable(true);
			table.addColumn(allUsersColumn, DynFieldsNLS.constants().dynamicField_Template_field_s_allUsers());

			Column<DynamicFieldTemplateProxy, String> isActivatedColumn = new IsActivatedColumn();
			isActivatedColumn.setSortable(true);
			table.addColumn(isActivatedColumn, DynFieldsNLS.constants().dynamicField_Template_field_s_isActivated());
		}

	}

	@Override
	protected GwtEvent<?> getViewEvent(DynamicFieldTemplateProxy value) {
		History.newItem(TokenHelper.TK_VIEW + "/dynamicfieldtemplate/" + value.getId(), true);
		return null;
	}

	@Override
	protected String getDefaultSortProperty() {
		return "formType";
	}

	@Override
	protected boolean getDefaultSortPropertyOrder() {
		return true;
	}

	/**
	 * Creates the Create action command for the entity
	 * @return the create command
	 */
	public Command getCreateCommand() {

		if (ProfileUtil.isAdmin()) {
			Command command = new Command() {
				public void execute() {
					History.newItem(TokenHelper.TK_NEW + "/dynamicfieldtemplate/", true);
				}
			};
			return command;
		} else
			return null;
	}

	/**
	 * Creates the Delete action command for the entity
	 * @return the delete command
	 */
	public PushButton getDeleteButton() {

		if (ProfileUtil.isAdmin()) {
			deleteButton = new PushButton(BaseNLS.constants().button_delete());
			deleteButton.setStyleName(imogResources.imogStyle().imogButton());
			deleteButton.addStyleName("Dynatable-Button");
			deleteButton.setVisible(false);
			return deleteButton;
		}

		return null;
	}

	/**
	 * Creates the Handlers linked to the delete button
	 */
	private void setDeleteButtonHandlers() {

		if (ProfileUtil.isAdmin()) {

			// Click handler
			registrations.add(deleteButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {

					Set<DynamicFieldTemplateProxy> selectedEntities = selectionModel.getSelectedSet();

					int count = selectedEntities.size();
					if (count > 0) {

						DynFieldsRenderer renderer = DynFieldsRenderer.get();

						StringBuffer msg = new StringBuffer();
						msg.append(BaseNLS.constants().confirmation_delete_several1() + " " + DynFieldsNLS.constants().dynamicField_Template_name() + " "
								+ BaseNLS.constants().confirmation_delete_several2() + ": ");
						int i = 0;
						for (DynamicFieldTemplateProxy entity : selectedEntities) {
							if (count == 1 || i == count - 1)
								msg.append("'" + renderer.getDisplayValue(entity) + "' ?");
							else
								msg.append("'" + renderer.getDisplayValue(entity) + "', ");
							i = i + 1;
						}

						boolean toDelete = Window.confirm(msg.toString());
						if (toDelete) {

							Request<Void> deleteRequest = getDynamicField_TemplateRequest().delete(selectedEntities);
							deleteRequest.fire(new Receiver<Void>() {
								@Override
								public void onSuccess(Void response) {
									// Window.alert("The selected DynamicField_Template entries have been deleted");
									requestFactory.getEventBus().fireEvent(new ListDynamicFieldTemplateEvent());
								}

								@Override
								public void onFailure(ServerFailure error) {
									Window.alert("Error deleting the DynamicField_Template entries");
									super.onFailure(error);
								}
							});
						}
					}

				}
			}));

			// Selection changed handler
			registrations.add(requestFactory.getEventBus().addHandler(SelectionChangedInTableEvent.TYPE, new SelectionChangedInTableEvent.Handler() {
				@Override
				public void noticeSelectionChange(int selectedItems) {
					if (selectedItems > 0)
						deleteButton.setVisible(true);
					else
						deleteButton.setVisible(false);
				}
			}));
		}
	}

	private DynamicFieldTemplateRequest getDynamicField_TemplateRequest() {
		AdminRequestFactory dynFieldsRequestFactory = (AdminRequestFactory) requestFactory;
		return dynFieldsRequestFactory.dynamicFieldTemplateRequest();
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
		setDeleteButtonHandlers();
		super.onLoad();
	}

	/**
	 * --------------------- * Internal classes * ----------------------
	 */

	/**
	 * Column for field FieldName
	 * @author MEDES-IMPS
	 */
	private class FieldNameColumn extends ImogColumn<DynamicFieldTemplateProxy, String> {

		public FieldNameColumn() {
			super(new TextCell());
		}

		@Override
		public String getValue(DynamicFieldTemplateProxy object) {
			String value = null;
			if (object.getFieldName() == null)
				value = "";
			else
				value = object.getFieldName();

			return value;
		}

		public String getPropertyName() {
			return "fieldName";
		}
	}

	/**
	 * Column for field FieldType
	 * @author MEDES-IMPS
	 */
	private class FieldTypeColumn extends ImogColumn<DynamicFieldTemplateProxy, String> {

		private DynFieldsRenderer renderer = DynFieldsRenderer.get();

		public FieldTypeColumn() {
			super(new TextCell());
		}

		@Override
		public String getValue(DynamicFieldTemplateProxy object) {
			String value = null;
			if (object.getFieldType() == null)
				value = "";
			else
				value = renderer.getEnumDisplayValue(DynamicFieldTemplateProxy.class, "fieldType", object.getFieldType());

			return value;
		}

		public String getPropertyName() {
			return "fieldType";
		}
	}

	/**
	 * Column for field FormType
	 * @author MEDES-IMPS
	 */
	private class FormTypeColumn extends ImogColumn<DynamicFieldTemplateProxy, String> {

		private DynFieldsRenderer renderer = DynFieldsRenderer.get();

		public FormTypeColumn() {
			super(new TextCell());
		}

		@Override
		public String getValue(DynamicFieldTemplateProxy object) {
			String value = null;
			if (object.getFormType() == null)
				value = "";
			else
				value = renderer.getEnumDisplayValue(DynamicFieldTemplateProxy.class, "formType", object.getFormType());

			return value;
		}

		public String getPropertyName() {
			return "formType";
		}
	}

	/**
	 * Column for field TemplateCreator
	 * @author MEDES-IMPS
	 */
	private class TemplateCreatorColumn extends ImogColumn<DynamicFieldTemplateProxy, String> {

		// private DynFieldsRenderer renderer = DynFieldsRenderer.get();

		public TemplateCreatorColumn() {
			super(new TextCell());
		}

		@Override
		public String getValue(DynamicFieldTemplateProxy object) {
			String value = null;
			if (object.getTemplateCreator() == null)
				value = "";
			else
				value = object.getTemplateCreator().getLogin();

			return value;
		}

		public String getPropertyName() {
			return "templateCreator";
		}
	}

	/**
	 * Column for field Description
	 * @author MEDES-IMPS
	 */
	private class DescriptionColumn extends ImogColumn<DynamicFieldTemplateProxy, String> {

		public DescriptionColumn() {
			super(new TextCell());
		}

		@Override
		public String getValue(DynamicFieldTemplateProxy object) {
			String value = null;
			if (object.getDescription() == null)
				value = "";
			else
				value = object.getDescription();

			return value;
		}

		public String getPropertyName() {
			return "description";
		}
	}

	/**
	 * Column for field IsDefault
	 * @author MEDES-IMPS
	 */
	private class IsDefaultColumn extends ImogColumn<DynamicFieldTemplateProxy, String> {

		public IsDefaultColumn() {
			super(new TextCell());
		}

		@Override
		public String getValue(DynamicFieldTemplateProxy object) {
			String value = null;
			if (object.getIsDefault() == null)
				value = "";
			else
				value = BooleanUtil.getBooleanDisplayValue(object.getIsDefault());

			return value;
		}

		public String getPropertyName() {
			return "isDefault";
		}
	}

	/**
	 * Column for field FieldPosition
	 * @author MEDES-IMPS
	 */
	private class FieldPositionColumn extends ImogColumn<DynamicFieldTemplateProxy, String> {

		public FieldPositionColumn() {
			super(new TextCell());
		}

		@Override
		public String getValue(DynamicFieldTemplateProxy object) {
			String value = null;
			if (object.getFieldPosition() == null)
				value = "";
			else
				value = object.getFieldPosition().toString();

			return value;
		}

		public String getPropertyName() {
			return "fieldPosition";
		}
	}

	/**
	 * Column for field AllUsers
	 * @author MEDES-IMPS
	 */
	private class AllUsersColumn extends ImogColumn<DynamicFieldTemplateProxy, String> {

		public AllUsersColumn() {
			super(new TextCell());
		}

		@Override
		public String getValue(DynamicFieldTemplateProxy object) {
			String value = null;
			if (object.getAllUsers() == null)
				value = "";
			else
				value = BooleanUtil.getBooleanDisplayValue(object.getAllUsers());

			return value;
		}

		public String getPropertyName() {
			return "allUsers";
		}
	}

	/**
	 * Column for field IsActivated
	 * @author MEDES-IMPS
	 */
	private class IsActivatedColumn extends ImogColumn<DynamicFieldTemplateProxy, String> {

		public IsActivatedColumn() {
			super(new TextCell());
		}

		@Override
		public String getValue(DynamicFieldTemplateProxy object) {
			String value = null;
			if (object.getIsActivated() == null)
				value = "";
			else
				value = BooleanUtil.getBooleanDisplayValue(object.getIsActivated());

			return value;
		}

		public String getPropertyName() {
			return "isActivated";
		}
	}

}
