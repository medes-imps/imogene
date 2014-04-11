package org.imogene.admin.client.ui.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.imogene.admin.client.AdminRenderer;
import org.imogene.admin.client.event.list.ListFieldGroupEvent;
import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.admin.client.ui.filter.FieldGroupFilterPanel;
import org.imogene.admin.shared.AdminRequestFactory;
import org.imogene.admin.shared.request.FieldGroupRequest;
import org.imogene.web.client.event.SelectionChangedInTableEvent;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.table.ImogBeanDataProvider;
import org.imogene.web.client.ui.table.ImogColumn;
import org.imogene.web.client.ui.table.ImogDynaTable;
import org.imogene.web.client.ui.table.filter.ImogFilterPanel;
import org.imogene.web.client.util.ProfileUtil;
import org.imogene.web.client.util.TokenHelper;
import org.imogene.web.shared.proxy.FieldGroupProxy;

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
 * Composite that displays the list of FieldGroup entries
 * 
 * @author MEDES-IMPS
 */
public class FieldGroupDynaTable extends ImogDynaTable<FieldGroupProxy> {

	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();

	private PushButton deleteButton;

	public FieldGroupDynaTable(AdminRequestFactory requestFactory, ImogBeanDataProvider<FieldGroupProxy> provider,
			boolean checkBoxesVisible) {
		super(requestFactory, provider, checkBoxesVisible);
	}

	public ImogFilterPanel getFilterPanel() {
		ImogFilterPanel filterPanel = new FieldGroupFilterPanel();
		super.configureFilterPanel(filterPanel);
		return filterPanel;
	}

	/**
	 * 
	 */
	@Override
	protected void setColumns() {

		if (ProfileUtil.isAdmin()) {
			Column<FieldGroupProxy, String> entityColumn = new EntityColumn();
			entityColumn.setSortable(true);
			table.addColumn(entityColumn, AdminNLS.constants().fieldGroup_field_s_entity());
		}
		if (ProfileUtil.isAdmin()) {
			Column<FieldGroupProxy, String> nameColumn = new NameColumn();
			nameColumn.setSortable(true);
			table.addColumn(nameColumn, AdminNLS.constants().fieldGroup_field_s_name());
		}

	}

	@Override
	protected GwtEvent<?> getViewEvent(FieldGroupProxy value) {
		History.newItem(TokenHelper.TK_VIEW + "/fieldgroup/" + value.getId(), true);
		return null;
	}

	@Override
	protected String getDefaultSortProperty() {
		return "modified";
	}

	@Override
	protected boolean getDefaultSortPropertyOrder() {
		return false;
	}

	/**
	 * Creates the Create action command for the entity
	 * 
	 * @return the create command
	 */
	public Command getCreateCommand() {

		if (ProfileUtil.isAdmin()) {
			Command command = new Command() {
				public void execute() {
					History.newItem(TokenHelper.TK_NEW + "/fieldgroup/", true);
				}
			};
			return command;
		} else
			return null;
	}

	/**
	 * Creates the Delete action command for the entity
	 * 
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

					Set<FieldGroupProxy> selectedEntities = selectionModel.getSelectedSet();

					int count = selectedEntities.size();
					if (count > 0) {

						AdminRenderer renderer = AdminRenderer.get();

						StringBuffer msg = new StringBuffer();
						msg.append(BaseNLS.constants().confirmation_delete_several1() + " "
								+ AdminNLS.constants().fieldGroup_name() + " "
								+ BaseNLS.constants().confirmation_delete_several2() + ": ");
						int i = 0;
						for (FieldGroupProxy entity : selectedEntities) {
							if (count == 1 || i == count - 1)
								msg.append("'" + renderer.getDisplayValue(entity) + "' ?");
							else
								msg.append("'" + renderer.getDisplayValue(entity) + "', ");
							i = i + 1;
						}

						boolean toDelete = Window.confirm(msg.toString());
						if (toDelete) {

							Request<Void> deleteRequest = getFieldGroupRequest().delete(selectedEntities);
							deleteRequest.fire(new Receiver<Void>() {
								@Override
								public void onSuccess(Void response) {
									// Window.alert("The selected FieldGroup entries have been deleted");
									requestFactory.getEventBus().fireEvent(new ListFieldGroupEvent());
								}

								@Override
								public void onFailure(ServerFailure error) {
									Window.alert("Error deleting the FieldGroup entries");
									super.onFailure(error);
								}
							});
						}
					}

				}
			}));

			// Selection changed handler
			registrations.add(requestFactory.getEventBus().addHandler(SelectionChangedInTableEvent.TYPE,
					new SelectionChangedInTableEvent.Handler() {
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
	 * Creates the action command that enables to export the FieldGroup entries in a csv file
	 * 
	 * @return the command
	 */
	public Command getCsvExportButton() {
		return null;
	}

	private FieldGroupRequest getFieldGroupRequest() {
		AdminRequestFactory AdminRequestFactory = (AdminRequestFactory) requestFactory;
		return AdminRequestFactory.fieldGroupRequest();
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
	 * Column for field Entity
	 * 
	 * @author MEDES-IMPS
	 */
	private class EntityColumn extends ImogColumn<FieldGroupProxy, String> {

		private AdminRenderer renderer = AdminRenderer.get();

		public EntityColumn() {
			super(new TextCell());
		}

		@Override
		public String getValue(FieldGroupProxy object) {
			String value = null;
			if (object != null) {
				if (object.getEntity() == null)
					value = "";
				else
					value = renderer.getDisplayValue(object.getEntity());
			}
			return value;
		}

		public String getPropertyName() {
			return "entity";
		}
	}

	/**
	 * Column for field Name
	 * 
	 * @author MEDES-IMPS
	 */
	private class NameColumn extends ImogColumn<FieldGroupProxy, String> {

		public NameColumn() {
			super(new TextCell());
		}

		@Override
		public String getValue(FieldGroupProxy object) {
			String value = null;
			if (object != null) {
				if (object.getName() == null)
					value = "";
				else
					value = object.getName();
			}
			return value;
		}

		public String getPropertyName() {
			return "name";
		}
	}

}
