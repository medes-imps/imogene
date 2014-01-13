package org.imogene.admin.client.ui.filter;

import java.util.ArrayList;
import java.util.List;

import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.lib.common.constants.CriteriaConstants;
import org.imogene.web.client.ui.table.filter.ImogFilterBox;
import org.imogene.web.client.ui.table.filter.ImogFilterPanel;
import org.imogene.web.client.util.FilterCriteria;
import org.imogene.web.client.util.ProfileUtil;

import com.google.gwt.user.client.ui.TextBox;

/**
 * Filter panel to filter the FieldGroup entries
 * 
 * @author MEDES-IMPS
 */
public class FieldGroupFilterPanel extends ImogFilterPanel {

	/* filter widgets */
	private TextBox entity_nameBox;
	private ImogFilterBox entity_nameFilterBox;
	private TextBox nameBox;
	private ImogFilterBox nameFilterBox;

	public FieldGroupFilterPanel() {
		super();
		setFieldReadAccess();
	}

	@Override
	public void resetFilterWidgets() {

		entity_nameBox.setValue(null);
		nameBox.setValue(null);

	}

	@Override
	public void createFilterWidgets() {

		entity_nameBox = new TextBox();
		entity_nameFilterBox = new ImogFilterBox(entity_nameBox);
		entity_nameFilterBox.setFilterLabel(AdminNLS.constants().cardEntity_field_name());
		addTableFilter(entity_nameFilterBox);

		nameBox = new TextBox();
		nameFilterBox = new ImogFilterBox(nameBox);
		nameFilterBox.setFilterLabel(AdminNLS.constants().fieldGroup_field_name());
		addTableFilter(nameFilterBox);

	}

	@Override
	public List<FilterCriteria> getFilterCriteria() {

		String locale = AdminNLS.constants().locale();

		List<FilterCriteria> criteria = new ArrayList<FilterCriteria>();

		FilterCriteria entity_nameCrit = new FilterCriteria();
		entity_nameCrit.setField("entity.name");
		entity_nameCrit.setFieldDisplayName(AdminNLS.constants().cardEntity_field_name());
		entity_nameCrit.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
		entity_nameCrit.setValue(entity_nameBox.getValue());
		entity_nameCrit.setValueDisplayName(entity_nameBox.getValue());
		criteria.add(entity_nameCrit);

		FilterCriteria nameCrit = new FilterCriteria();
		nameCrit.setField("name");
		nameCrit.setFieldDisplayName(AdminNLS.constants().fieldGroup_field_name());
		nameCrit.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
		nameCrit.setValue(nameBox.getValue());
		nameCrit.setValueDisplayName(nameBox.getValue());
		criteria.add(nameCrit);

		return criteria;
	}

	/**
	 * Configures the visibility of the fields in view mode depending on the user privileges
	 */
	public void setFieldReadAccess() {

		if (!ProfileUtil.isAdmin())
			entity_nameFilterBox.setVisible(false);

		if (!ProfileUtil.isAdmin())
			nameFilterBox.setVisible(false);

	}
}
