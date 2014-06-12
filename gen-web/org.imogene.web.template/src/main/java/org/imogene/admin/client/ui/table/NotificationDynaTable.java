package org.imogene.admin.client.ui.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.imogene.admin.client.AdminRenderer;
import org.imogene.admin.client.event.list.ListNotificationEvent;
import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.admin.client.ui.filter.NotificationFilterPanel;
import org.imogene.admin.shared.AdminRequestFactory;
import org.imogene.admin.shared.proxy.NotificationProxy;
import org.imogene.admin.shared.request.NotificationRequest;
import org.imogene.web.client.dynamicfields.ui.field.FormTypeUtil;
import org.imogene.web.client.event.SelectionChangedInTableEvent;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.table.ImogBeanDataProvider;
import org.imogene.web.client.ui.table.ImogColumn;
import org.imogene.web.client.ui.table.ImogDynaTable;
import org.imogene.web.client.ui.table.filter.ImogFilterPanel;
import org.imogene.web.client.util.ProfileUtil;
import org.imogene.web.client.util.TokenHelper;

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
 * Composite that displays the list of Notification entries
 * @author MEDES-IMPS
 */
public class NotificationDynaTable extends ImogDynaTable<NotificationProxy> {

	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();

	private PushButton deleteButton;

	public NotificationDynaTable(AdminRequestFactory requestFactory, ImogBeanDataProvider<NotificationProxy> provider, boolean checkBoxesVisible) {
		super(requestFactory, provider, checkBoxesVisible);
	}

	public ImogFilterPanel getFilterPanel(FormTypeUtil formTypeUtil) {
		ImogFilterPanel filterPanel = new NotificationFilterPanel(formTypeUtil);
		super.configureFilterPanel(filterPanel);
		return filterPanel;
	}

	/**
	 * 
	 */
	@Override
	protected void setColumns() {

		if (ProfileUtil.isAdmin()) {

			Column<NotificationProxy, String> formTypeColumn = new FormTypeColumn();
			formTypeColumn.setSortable(true);
			table.addColumn(formTypeColumn, AdminNLS.constants().notification_field_s_formType());

			Column<NotificationProxy, String> nameColumn = new NameColumn();
			nameColumn.setSortable(true);
			table.addColumn(nameColumn, AdminNLS.constants().notification_field_s_name());

			Column<NotificationProxy, String> methodColumn = new MethodColumn();
			methodColumn.setSortable(true);
			table.addColumn(methodColumn, AdminNLS.constants().notification_field_s_method());

			Column<NotificationProxy, String> operationColumn = new OperationColumn();
			operationColumn.setSortable(true);
			table.addColumn(operationColumn, AdminNLS.constants().notification_field_s_operation());
		}
	}

	@Override
	protected GwtEvent<?> getViewEvent(NotificationProxy value) {
		History.newItem(TokenHelper.TK_VIEW + "/notification/" + value.getId(), true);
		return null;
	}

	@Override
	protected String getDefaultSortProperty() {
		return "name";
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
					History.newItem(TokenHelper.TK_NEW + "/notification/", true);
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

					Set<NotificationProxy> selectedEntities = selectionModel.getSelectedSet();

					int count = selectedEntities.size();
					if (count > 0) {

						AdminRenderer renderer = AdminRenderer.get();

						StringBuffer msg = new StringBuffer();
						msg.append(BaseNLS.constants().confirmation_delete_several1() + " " + AdminNLS.constants().notification_name() + " " + BaseNLS.constants().confirmation_delete_several2()
								+ ": ");
						int i = 0;
						for (NotificationProxy entity : selectedEntities) {
							if (count == 1 || i == count - 1)
								msg.append("'" + renderer.getDisplayValue(entity) + "' ?");
							else
								msg.append("'" + renderer.getDisplayValue(entity) + "', ");
							i = i + 1;
						}

						boolean toDelete = Window.confirm(msg.toString());
						if (toDelete) {

							Request<Void> deleteRequest = getNotificationRequest().delete(selectedEntities);
							deleteRequest.fire(new Receiver<Void>() {
								@Override
								public void onSuccess(Void response) {
									// Window.alert("The selected Notification entries have been deleted");
									requestFactory.getEventBus().fireEvent(new ListNotificationEvent());
								}

								@Override
								public void onFailure(ServerFailure error) {
									Window.alert("Error deleting the Notification entries");
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

	/**
	 * Creates the ExportExcel action command for the entity
	 * @return the ExportExcel command
	 */
	public PushButton getExportExcelCommand() {

		// if (AccessManager.canExport())

		/* action that enables to export a list of beans in an Excel file */
		// Command command = new Command() {
		// public void execute() {
		// // History.newItem("xls/company");
		// }
		// };
		return null;
	}

	private NotificationRequest getNotificationRequest() {
		AdminRequestFactory adminRequestFactory = (AdminRequestFactory) requestFactory;
		return adminRequestFactory.notificationRequest();
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
	 * Column for field Name
	 * @author MEDES-IMPS
	 */
	private class NameColumn extends ImogColumn<NotificationProxy, String> {

		public NameColumn() {
			super(new TextCell());
		}

		@Override
		public String getValue(NotificationProxy object) {
			String value = null;
			if (object.getName() == null)
				value = "";
			else
				value = object.getName();

			return value;
		}

		public String getPropertyName() {
			return "name";
		}
	}

	/**
	 * Column for field FormType
	 * @author MEDES-IMPS
	 */
	private class FormTypeColumn extends ImogColumn<NotificationProxy, String> {

		private AdminRenderer renderer = AdminRenderer.get();

		public FormTypeColumn() {
			super(new TextCell());
		}

		@Override
		public String getValue(NotificationProxy object) {
			String value = null;
			if (object.getFormType() == null)
				value = "";
			else
				value = renderer.getEnumDisplayValue(NotificationProxy.class, "formType", object.getFormType());

			return value;
		}

		public String getPropertyName() {
			return "formType";
		}
	}

	/**
	 * Column for field Method
	 * @author MEDES-IMPS
	 */
	private class MethodColumn extends ImogColumn<NotificationProxy, String> {

		private AdminRenderer renderer = AdminRenderer.get();

		public MethodColumn() {
			super(new TextCell());
		}

		@Override
		public String getValue(NotificationProxy object) {
			String value = null;
			if (object.getMethod() == null)
				value = "";
			else
				value = renderer.getEnumDisplayValue(NotificationProxy.class, "method", object.getMethod());

			return value;
		}

		public String getPropertyName() {
			return "method";
		}
	}

	/**
	 * Column for field Operation
	 * @author MEDES-IMPS
	 */
	private class OperationColumn extends ImogColumn<NotificationProxy, String> {

		private AdminRenderer renderer = AdminRenderer.get();

		public OperationColumn() {
			super(new TextCell());
		}

		@Override
		public String getValue(NotificationProxy object) {
			String value = null;
			if (object.getOperation() == null)
				value = "";
			else
				value = renderer.getEnumDisplayValue(NotificationProxy.class, "operation", object.getOperation());

			return value;
		}

		public String getPropertyName() {
			return "operation";
		}
	}

}
