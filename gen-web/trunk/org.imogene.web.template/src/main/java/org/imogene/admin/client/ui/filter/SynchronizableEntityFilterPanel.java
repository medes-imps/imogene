package org.imogene.admin.client.ui.filter;

import java.util.ArrayList;
import java.util.List;

import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.lib.common.constants.CriteriaConstants;
import org.imogene.web.client.ui.table.filter.ImogFilterBox;
import org.imogene.web.client.ui.table.filter.ImogFilterPanel;
import org.imogene.web.client.util.FilterCriteria;
import org.imogene.web.client.util.ImogRoleUtil;

import com.google.gwt.user.client.ui.TextBox;

/**
 * Filter panel to filter the SynchronizableEntity entries
 * @author MEDES-IMPS
 */
public class SynchronizableEntityFilterPanel extends ImogFilterPanel {

	/* filter widgets */
	private TextBox nameBox;
	private ImogFilterBox nameFilterBox;

	public SynchronizableEntityFilterPanel() {
		super();
		setFieldReadAccess();
	}

	@Override
	public void resetFilterWidgets() {
		nameBox.setValue(null);

	}

	@Override
	public void createFilterWidgets() {

		nameBox = new TextBox();
		nameFilterBox = new ImogFilterBox(nameBox);
		nameFilterBox.setFilterLabel(AdminNLS.constants()
				.synchronizableEntity_field_name());
		addTableFilter(nameFilterBox);

	}

	@Override
	public List<FilterCriteria> getFilterCriteria() {

		List<FilterCriteria> criteria = new ArrayList<FilterCriteria>();

		FilterCriteria nameCrit = new FilterCriteria();
		nameCrit.setField("name");
		nameCrit.setFieldDisplayName(AdminNLS.constants()
				.synchronizableEntity_field_name());
		nameCrit.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
		nameCrit.setValue(nameBox.getValue());
		nameCrit.setValueDisplayName(nameBox.getValue());
		criteria.add(nameCrit);

		return criteria;
	}

	/**
	 * Configures the visibility of the fields 
	 * in view mode depending on the user privileges
	 */
	public void setFieldReadAccess() {

		if (!ImogRoleUtil.isAdmin())
			nameFilterBox.setVisible(false);

	}
}
