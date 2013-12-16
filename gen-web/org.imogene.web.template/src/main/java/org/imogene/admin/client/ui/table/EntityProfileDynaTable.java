package org.imogene.admin.client.ui.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.imogene.admin.client.AdminRenderer;
import org.imogene.admin.client.event.create.CreateEntityProfileEvent;
import org.imogene.admin.client.event.list.ListEntityProfileEvent;
import org.imogene.admin.client.event.view.ViewEntityProfileEvent;
import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.admin.client.ui.filter.EntityProfileFilterPanel;
import org.imogene.admin.shared.AdminRequestFactory;
import org.imogene.admin.shared.request.EntityProfileRequest;
import org.imogene.web.client.event.SelectionChangedInTableEvent;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.table.ImogBeanDataProvider;
import org.imogene.web.client.ui.table.ImogColumn;
import org.imogene.web.client.ui.table.ImogDynaTable;
import org.imogene.web.client.ui.table.filter.ImogFilterPanel;
import org.imogene.web.client.util.BooleanUtil;
import org.imogene.web.client.util.ProfileUtil;
import org.imogene.web.shared.proxy.EntityProfileProxy;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PushButton;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

/**
 * Composite that displays the list of EntityProfile entries
 * 
 * @author MEDES-IMPS
 */
public class EntityProfileDynaTable extends ImogDynaTable<EntityProfileProxy> {

	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();

	private PushButton deleteButton;

	public EntityProfileDynaTable(AdminRequestFactory requestFactory,
			ImogBeanDataProvider<EntityProfileProxy> provider, boolean checkBoxesVisible) {
		super(requestFactory, provider, checkBoxesVisible);
	}

	public ImogFilterPanel getFilterPanel() {
		ImogFilterPanel filterPanel = new EntityProfileFilterPanel();
		super.configureFilterPanel(filterPanel);
		return filterPanel;
	}

	/**
	 * 
	 */
	@Override
	protected void setColumns() {

		if (ProfileUtil.isAdmin()) {
			Column<EntityProfileProxy, String> profileColumn = new ProfileColumn();
			profileColumn.setSortable(true);
			table.addColumn(profileColumn, AdminNLS.constants().entityProfile_field_s_profile());
		}
		if (ProfileUtil.isAdmin()) {
			Column<EntityProfileProxy, String> entityColumn = new EntityColumn();
			entityColumn.setSortable(true);
			table.addColumn(entityColumn, AdminNLS.constants().entityProfile_field_s_entity());
		}
		if (ProfileUtil.isAdmin()) {
			Column<EntityProfileProxy, String> directAccessColumn = new DirectAccessColumn();
			directAccessColumn.setSortable(true);
			table.addColumn(directAccessColumn, AdminNLS.constants().entityProfile_field_s_directAccess());
		}
		if (ProfileUtil.isAdmin()) {
			Column<EntityProfileProxy, String> createColumn = new CreateColumn();
			createColumn.setSortable(true);
			table.addColumn(createColumn, AdminNLS.constants().entityProfile_field_s_create());
		}
		if (ProfileUtil.isAdmin()) {
			Column<EntityProfileProxy, String> deleteColumn = new DeleteColumn();
			deleteColumn.setSortable(true);
			table.addColumn(deleteColumn, AdminNLS.constants().entityProfile_field_s_delete());
		}
		if (ProfileUtil.isAdmin()) {
			Column<EntityProfileProxy, String> exportColumn = new ExportColumn();
			exportColumn.setSortable(true);
			table.addColumn(exportColumn, AdminNLS.constants().entityProfile_field_s_export());
		}

	}

	@Override
	protected GwtEvent<?> getViewEvent(EntityProfileProxy value) {
		return new ViewEntityProfileEvent(value.getId());
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
					requestFactory.getEventBus().fireEvent(new CreateEntityProfileEvent());
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

					Set<EntityProfileProxy> selectedEntities = selectionModel.getSelectedSet();

					int count = selectedEntities.size();
					if (count > 0) {

						AdminRenderer renderer = AdminRenderer.get();

						StringBuffer msg = new StringBuffer();
						msg.append(BaseNLS.constants().confirmation_delete_several1() + " "
								+ AdminNLS.constants().entityProfile_name() + " "
								+ BaseNLS.constants().confirmation_delete_several2() + ": ");
						int i = 0;
						for (EntityProfileProxy entity : selectedEntities) {
							if (count == 1 || i == count - 1)
								msg.append("'" + renderer.getDisplayValue(entity) + "' ?");
							else
								msg.append("'" + renderer.getDisplayValue(entity) + "', ");
							i = i + 1;
						}

						boolean toDelete = Window.confirm(msg.toString());
						if (toDelete) {

							Request<Void> deleteRequest = getEntityProfileRequest().delete(selectedEntities);
							deleteRequest.fire(new Receiver<Void>() {
								@Override
								public void onSuccess(Void response) {
									// Window.alert("The selected EntityProfile entries have been deleted");
									requestFactory.getEventBus().fireEvent(new ListEntityProfileEvent());
								}

								@Override
								public void onFailure(ServerFailure error) {
									Window.alert("Error deleting the EntityProfile entries");
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
	 * Creates the action command that enables to export the EntityProfile entries in a csv file
	 * 
	 * @return the command
	 */
	public Command getCsvExportButton() {

		return null;
	}

	private EntityProfileRequest getEntityProfileRequest() {
		AdminRequestFactory AdminRequestFactory = (AdminRequestFactory) requestFactory;
		return AdminRequestFactory.entityProfileRequest();
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
	private class ProfileColumn extends ImogColumn<EntityProfileProxy, String> {

		private AdminRenderer renderer = AdminRenderer.get();

		public ProfileColumn() {
			super(new TextCell());
		}

		@Override
		public String getValue(EntityProfileProxy object) {
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
	 * Column for field Entity
	 * 
	 * @author MEDES-IMPS
	 */
	private class EntityColumn extends ImogColumn<EntityProfileProxy, String> {

		private AdminRenderer renderer = AdminRenderer.get();

		public EntityColumn() {
			super(new TextCell());
		}

		@Override
		public String getValue(EntityProfileProxy object) {
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
	 * Column for field DirectAccess
	 * 
	 * @author MEDES-IMPS
	 */
	private class DirectAccessColumn extends ImogColumn<EntityProfileProxy, String> {

		public DirectAccessColumn() {
			super(new TextCell());
		}

		@Override
		public String getValue(EntityProfileProxy object) {
			String value = null;
			if (object != null) {
				if (object.getDirectAccess() == null)
					value = "";
				else
					value = BooleanUtil.getBooleanDisplayValue(object.getDirectAccess());
			}
			return value;
		}

		public String getPropertyName() {
			return "directAccess";
		}
	}

	/**
	 * Column for field Create
	 * 
	 * @author MEDES-IMPS
	 */
	private class CreateColumn extends ImogColumn<EntityProfileProxy, String> {

		public CreateColumn() {
			super(new TextCell());
		}

		@Override
		public String getValue(EntityProfileProxy object) {
			String value = null;
			if (object != null) {
				if (object.getCreate() == null)
					value = "";
				else
					value = BooleanUtil.getBooleanDisplayValue(object.getCreate());
			}
			return value;
		}

		public String getPropertyName() {
			return "create";
		}
	}

	/**
	 * Column for field Delete
	 * 
	 * @author MEDES-IMPS
	 */
	private class DeleteColumn extends ImogColumn<EntityProfileProxy, String> {

		public DeleteColumn() {
			super(new TextCell());
		}

		@Override
		public String getValue(EntityProfileProxy object) {
			String value = null;
			if (object != null) {
				if (object.getDelete() == null)
					value = "";
				else
					value = BooleanUtil.getBooleanDisplayValue(object.getDelete());
			}
			return value;
		}

		public String getPropertyName() {
			return "delete";
		}
	}

	/**
	 * Column for field Export
	 * 
	 * @author MEDES-IMPS
	 */
	private class ExportColumn extends ImogColumn<EntityProfileProxy, String> {

		public ExportColumn() {
			super(new TextCell());
		}

		@Override
		public String getValue(EntityProfileProxy object) {
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
