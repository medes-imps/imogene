package org.imogene.admin.client.ui.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.imogene.admin.client.AdminRenderer;
import org.imogene.admin.client.event.list.ListFieldGroupProfileEvent;
import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.admin.client.ui.filter.FieldGroupProfileFilterPanel;
import org.imogene.admin.shared.AdminRequestFactory;
import org.imogene.admin.shared.request.FieldGroupProfileRequest;
import org.imogene.web.client.event.SelectionChangedInTableEvent;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.table.ImogBeanDataProvider;
import org.imogene.web.client.ui.table.ImogColumn;
import org.imogene.web.client.ui.table.ImogDynaTable;
import org.imogene.web.client.ui.table.filter.ImogFilterPanel;
import org.imogene.web.client.util.BooleanUtil;
import org.imogene.web.client.util.ProfileUtil;
import org.imogene.web.client.util.TokenHelper;
import org.imogene.web.shared.proxy.FieldGroupProfileProxy;

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
 * Composite that displays the list of FieldGroupProfile entries
 * 
 * @author MEDES-IMPS
 */
public class FieldGroupProfileDynaTable extends ImogDynaTable<FieldGroupProfileProxy> {

	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();

	private PushButton deleteButton;

	public FieldGroupProfileDynaTable(AdminRequestFactory requestFactory,
			ImogBeanDataProvider<FieldGroupProfileProxy> provider, boolean checkBoxesVisible) {
		super(requestFactory, provider, checkBoxesVisible);
	}

	public ImogFilterPanel getFilterPanel() {
		ImogFilterPanel filterPanel = new FieldGroupProfileFilterPanel();
		super.configureFilterPanel(filterPanel);
		return filterPanel;
	}

	/**
	 * 
	 */
	@Override
	protected void setColumns() {

		if (ProfileUtil.isAdmin()) {
			Column<FieldGroupProfileProxy, String> profileColumn = new ProfileColumn();
			profileColumn.setSortable(true);
			table.addColumn(profileColumn, AdminNLS.constants().fieldGroupProfile_field_s_profile());
		}
		if (ProfileUtil.isAdmin()) {
			Column<FieldGroupProfileProxy, String> fieldGroupColumn = new FieldGroupColumn();
			fieldGroupColumn.setSortable(true);
			table.addColumn(fieldGroupColumn, AdminNLS.constants().fieldGroupProfile_field_s_fieldGroup());
		}
		if (ProfileUtil.isAdmin()) {
			Column<FieldGroupProfileProxy, String> readColumn = new ReadColumn();
			readColumn.setSortable(true);
			table.addColumn(readColumn, AdminNLS.constants().fieldGroupProfile_field_s_read());
		}
		if (ProfileUtil.isAdmin()) {
			Column<FieldGroupProfileProxy, String> writeColumn = new WriteColumn();
			writeColumn.setSortable(true);
			table.addColumn(writeColumn, AdminNLS.constants().fieldGroupProfile_field_s_write());
		}
		if (ProfileUtil.isAdmin()) {
			Column<FieldGroupProfileProxy, String> exportColumn = new ExportColumn();
			exportColumn.setSortable(true);
			table.addColumn(exportColumn, AdminNLS.constants().fieldGroupProfile_field_s_export());
		}

	}

	@Override
	protected GwtEvent<?> getViewEvent(FieldGroupProfileProxy value) {
		History.newItem(TokenHelper.TK_VIEW + "/fieldgroupprofile/" + value.getId(), true);
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
					History.newItem(TokenHelper.TK_NEW + "/fieldgroupprofile/", true);
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

					Set<FieldGroupProfileProxy> selectedEntities = selectionModel.getSelectedSet();

					int count = selectedEntities.size();
					if (count > 0) {

						AdminRenderer renderer = AdminRenderer.get();

						StringBuffer msg = new StringBuffer();
						msg.append(BaseNLS.constants().confirmation_delete_several1() + " "
								+ AdminNLS.constants().fieldGroupProfile_name() + " "
								+ BaseNLS.constants().confirmation_delete_several2() + ": ");
						int i = 0;
						for (FieldGroupProfileProxy entity : selectedEntities) {
							if (count == 1 || i == count - 1)
								msg.append("'" + renderer.getDisplayValue(entity) + "' ?");
							else
								msg.append("'" + renderer.getDisplayValue(entity) + "', ");
							i = i + 1;
						}

						boolean toDelete = Window.confirm(msg.toString());
						if (toDelete) {

							Request<Void> deleteRequest = getFieldGroupProfileRequest().delete(selectedEntities);
							deleteRequest.fire(new Receiver<Void>() {
								@Override
								public void onSuccess(Void response) {
									// Window.alert("The selected FieldGroupProfile entries have been deleted");
									requestFactory.getEventBus().fireEvent(new ListFieldGroupProfileEvent());
								}

								@Override
								public void onFailure(ServerFailure error) {
									Window.alert("Error deleting the FieldGroupProfile entries");
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
	 * Creates the action command that enables to export the FieldGroupProfile entries in a csv file
	 * 
	 * @return the command
	 */
	public Command getCsvExportButton() {
		return null;
	}

	private FieldGroupProfileRequest getFieldGroupProfileRequest() {
		AdminRequestFactory AdminRequestFactory = (AdminRequestFactory) requestFactory;
		return AdminRequestFactory.fieldGroupProfileRequest();
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
	 * Column for field Profile
	 * 
	 * @author MEDES-IMPS
	 */
	private class ProfileColumn extends ImogColumn<FieldGroupProfileProxy, String> {

		private AdminRenderer renderer = AdminRenderer.get();

		public ProfileColumn() {
			super(new TextCell());
		}

		@Override
		public String getValue(FieldGroupProfileProxy object) {
			String value = null;
			if (object != null) {
				if (object.getProfile() == null)
					value = "";
				else
					value = renderer.getDisplayValue(object.getProfile());
			}
			return value;
		}

		public String getPropertyName() {
			return "profile";
		}
	}

	/**
	 * Column for field FieldGroup
	 * 
	 * @author MEDES-IMPS
	 */
	private class FieldGroupColumn extends ImogColumn<FieldGroupProfileProxy, String> {

		private AdminRenderer renderer = AdminRenderer.get();

		public FieldGroupColumn() {
			super(new TextCell());
		}

		@Override
		public String getValue(FieldGroupProfileProxy object) {
			String value = null;
			if (object != null) {
				if (object.getFieldGroup() == null)
					value = "";
				else
					value = renderer.getDisplayValue(object.getFieldGroup());
			}
			return value;
		}

		public String getPropertyName() {
			return "fieldGroup";
		}
	}

	/**
	 * Column for field Read
	 * 
	 * @author MEDES-IMPS
	 */
	private class ReadColumn extends ImogColumn<FieldGroupProfileProxy, String> {

		public ReadColumn() {
			super(new TextCell());
		}

		@Override
		public String getValue(FieldGroupProfileProxy object) {
			String value = null;
			if (object != null) {
				if (object.getRead() == null)
					value = "";
				else
					value = BooleanUtil.getBooleanDisplayValue(object.getRead());
			}
			return value;
		}

		public String getPropertyName() {
			return "read";
		}
	}

	/**
	 * Column for field Write
	 * 
	 * @author MEDES-IMPS
	 */
	private class WriteColumn extends ImogColumn<FieldGroupProfileProxy, String> {

		public WriteColumn() {
			super(new TextCell());
		}

		@Override
		public String getValue(FieldGroupProfileProxy object) {
			String value = null;
			if (object != null) {
				if (object.getWrite() == null)
					value = "";
				else
					value = BooleanUtil.getBooleanDisplayValue(object.getWrite());
			}
			return value;
		}

		public String getPropertyName() {
			return "write";
		}
	}

	/**
	 * Column for field Export
	 * 
	 * @author MEDES-IMPS
	 */
	private class ExportColumn extends ImogColumn<FieldGroupProfileProxy, String> {

		public ExportColumn() {
			super(new TextCell());
		}

		@Override
		public String getValue(FieldGroupProfileProxy object) {
			String value = null;
			if (object != null) {
				if (object.getExport() == null)
					value = "";
				else
					value = BooleanUtil.getBooleanDisplayValue(object.getExport());
			}
			return value;
		}

		public String getPropertyName() {
			return "export";
		}
	}

}
