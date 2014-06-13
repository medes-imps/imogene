package org.imogene.admin.client.ui.table;

import java.util.ArrayList;
import java.util.List;

import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.admin.shared.AdminRequestFactory;
import org.imogene.web.client.ui.table.ImogBeanDataProvider;
import org.imogene.web.client.ui.table.ImogColumn;
import org.imogene.web.client.ui.table.ImogDynaTable;
import org.imogene.web.client.util.ImogBeanRenderer;
import org.imogene.web.client.util.ProfileUtil;
import org.imogene.web.client.util.TokenHelper;
import org.imogene.web.shared.proxy.CardEntityProxy;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.PushButton;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * Composite that displays the list of CardEntity entries
 * @author MEDES-IMPS
 */
public class CardEntityDynaTable extends ImogDynaTable<CardEntityProxy> {

	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();
	private ImogBeanRenderer renderer;

	public CardEntityDynaTable(AdminRequestFactory requestFactory, ImogBeanDataProvider<CardEntityProxy> provider, boolean checkBoxesVisible, ImogBeanRenderer renderer) {
		super(requestFactory, provider, checkBoxesVisible);
		this.renderer = renderer;
	}

	/**
	 * 
	 */
	@Override
	protected void setColumns() {

		if (ProfileUtil.isAdmin()) {
			Column<CardEntityProxy, String> nameColumn = new NameColumn();
			nameColumn.setSortable(true);
			table.addColumn(nameColumn, AdminNLS.constants().cardEntity_name());
		}

	}

	@Override
	protected GwtEvent<?> getViewEvent(CardEntityProxy value) {
		History.newItem(TokenHelper.TK_VIEW + "/cardentity/" + value.getId(), true);
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
		return null;
	}

	/**
	 * Creates the Delete action command for the entity
	 * @return the delete command
	 */
	public PushButton getDeleteButton() {
		return null;
	}

	/**
	 * Creates the action command that enables to export the CardEntity entries in a csv file
	 * @return the command
	 */
	public Command getCsvExportButton() {
		return null;
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
		super.onLoad();
	}

	/**
	 * --------------------- * Internal classes * ----------------------
	 */

	/**
	 * Column for field Name
	 * @author MEDES-IMPS
	 */
	private class NameColumn extends ImogColumn<CardEntityProxy, String> {

		public NameColumn() {
			super(new TextCell());
		}

		@Override
		public String getValue(CardEntityProxy object) {
			String value = null;
			if (object != null)
				value = renderer.getDisplayValue(object);
			return value;
		}

		public String getPropertyName() {
			return "name";
		}
	}

}
