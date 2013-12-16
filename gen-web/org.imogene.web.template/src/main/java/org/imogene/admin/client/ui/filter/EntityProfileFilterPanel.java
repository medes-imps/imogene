package org.imogene.admin.client.ui.filter;

import java.util.ArrayList;
import java.util.List;

import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.lib.common.constants.CriteriaConstants;
import org.imogene.web.client.ui.field.widget.ImogBooleanAsList;
import org.imogene.web.client.ui.table.filter.ImogFilterBox;
import org.imogene.web.client.ui.table.filter.ImogFilterPanel;
import org.imogene.web.client.util.BooleanUtil;
import org.imogene.web.client.util.FilterCriteria;
import org.imogene.web.client.util.ProfileUtil;

import com.google.gwt.user.client.ui.TextBox;

/**
 * Filter panel to filter the EntityProfile entries
 * 
 * @author MEDES-IMPS
 */
public class EntityProfileFilterPanel extends ImogFilterPanel {

	/* filter widgets */
	private TextBox profile_nameBox;
	private ImogFilterBox profile_nameFilterBox;
	private TextBox entity_nameBox;
	private ImogFilterBox entity_nameFilterBox;
	private ImogBooleanAsList directAccessBox;
	private ImogFilterBox directAccessFilterBox;
	private ImogBooleanAsList createBox;
	private ImogFilterBox createFilterBox;
	private ImogBooleanAsList deleteBox;
	private ImogFilterBox deleteFilterBox;
	private ImogBooleanAsList exportBox;
	private ImogFilterBox exportFilterBox;

	public EntityProfileFilterPanel() {
		super();
		setFieldReadAccess();
	}

	@Override
	public void resetFilterWidgets() {

		profile_nameBox.setValue(null);
		entity_nameBox.setValue(null);
		directAccessBox.setValue(null);
		createBox.setValue(null);
		deleteBox.setValue(null);
		exportBox.setValue(null);

	}

	@Override
	public void createFilterWidgets() {

		profile_nameBox = new TextBox();
		profile_nameFilterBox = new ImogFilterBox(profile_nameBox);
		profile_nameFilterBox.setFilterLabel(AdminNLS.constants().profile_field_name());
		addTableFilter(profile_nameFilterBox);

		entity_nameBox = new TextBox();
		entity_nameFilterBox = new ImogFilterBox(entity_nameBox);
		entity_nameFilterBox.setFilterLabel(AdminNLS.constants().cardEntity_field_name());
		addTableFilter(entity_nameFilterBox);

		directAccessBox = new ImogBooleanAsList();
		directAccessFilterBox = new ImogFilterBox(directAccessBox);
		directAccessFilterBox.setFilterLabel(AdminNLS.constants().entityProfile_field_directAccess());
		addTableFilter(directAccessFilterBox);

		createBox = new ImogBooleanAsList();
		createFilterBox = new ImogFilterBox(createBox);
		createFilterBox.setFilterLabel(AdminNLS.constants().entityProfile_field_create());
		addTableFilter(createFilterBox);

		deleteBox = new ImogBooleanAsList();
		deleteFilterBox = new ImogFilterBox(deleteBox);
		deleteFilterBox.setFilterLabel(AdminNLS.constants().entityProfile_field_delete());
		addTableFilter(deleteFilterBox);

		exportBox = new ImogBooleanAsList();
		exportFilterBox = new ImogFilterBox(exportBox);
		exportFilterBox.setFilterLabel(AdminNLS.constants().entityProfile_field_export());
		addTableFilter(exportFilterBox);

	}

	@Override
	public List<FilterCriteria> getFilterCriteria() {

		String locale = AdminNLS.constants().locale();

		List<FilterCriteria> criteria = new ArrayList<FilterCriteria>();

		FilterCriteria profile_nameCrit = new FilterCriteria();
		profile_nameCrit.setField("profile.name");
		profile_nameCrit.setFieldDisplayName(AdminNLS.constants().profile_field_name());
		profile_nameCrit.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
		profile_nameCrit.setValue(profile_nameBox.getValue());
		profile_nameCrit.setValueDisplayName(profile_nameBox.getValue());
		criteria.add(profile_nameCrit);

		FilterCriteria entity_nameCrit = new FilterCriteria();
		entity_nameCrit.setField("entity.name");
		entity_nameCrit.setFieldDisplayName(AdminNLS.constants().cardEntity_field_name());
		entity_nameCrit.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
		entity_nameCrit.setValue(entity_nameBox.getValue());
		entity_nameCrit.setValueDisplayName(entity_nameBox.getValue());
		criteria.add(entity_nameCrit);

		FilterCriteria directAccessCrit = new FilterCriteria();
		directAccessCrit.setField("directAccess");
		directAccessCrit.setFieldDisplayName(AdminNLS.constants().entityProfile_field_directAccess());
		directAccessCrit.setOperation(CriteriaConstants.BOOLEAN_OPERATOR_EQUAL);
		if (directAccessBox.getValue() == null)
			directAccessCrit.setValue(null);
		else
			directAccessCrit.setValue(String.valueOf(directAccessBox.getValue()));
		directAccessCrit.setValueDisplayName(BooleanUtil.getBooleanDisplayValue(directAccessBox.getValue()));
		criteria.add(directAccessCrit);

		FilterCriteria createCrit = new FilterCriteria();
		createCrit.setField("create");
		createCrit.setFieldDisplayName(AdminNLS.constants().entityProfile_field_create());
		createCrit.setOperation(CriteriaConstants.BOOLEAN_OPERATOR_EQUAL);
		if (createBox.getValue() == null)
			createCrit.setValue(null);
		else
			createCrit.setValue(String.valueOf(createBox.getValue()));
		createCrit.setValueDisplayName(BooleanUtil.getBooleanDisplayValue(createBox.getValue()));
		criteria.add(createCrit);

		FilterCriteria deleteCrit = new FilterCriteria();
		deleteCrit.setField("delete");
		deleteCrit.setFieldDisplayName(AdminNLS.constants().entityProfile_field_delete());
		deleteCrit.setOperation(CriteriaConstants.BOOLEAN_OPERATOR_EQUAL);
		if (deleteBox.getValue() == null)
			deleteCrit.setValue(null);
		else
			deleteCrit.setValue(String.valueOf(deleteBox.getValue()));
		deleteCrit.setValueDisplayName(BooleanUtil.getBooleanDisplayValue(deleteBox.getValue()));
		criteria.add(deleteCrit);

		FilterCriteria exportCrit = new FilterCriteria();
		exportCrit.setField("export");
		exportCrit.setFieldDisplayName(AdminNLS.constants().entityProfile_field_export());
		exportCrit.setOperation(CriteriaConstants.BOOLEAN_OPERATOR_EQUAL);
		if (exportBox.getValue() == null)
			exportCrit.setValue(null);
		else
			exportCrit.setValue(String.valueOf(exportBox.getValue()));
		exportCrit.setValueDisplayName(BooleanUtil.getBooleanDisplayValue(exportBox.getValue()));
		criteria.add(exportCrit);

		return criteria;
	}

	/**
	 * Configures the visibility of the fields in view mode depending on the user privileges
	 */
	public void setFieldReadAccess() {

		if (!ProfileUtil.isAdmin())
			profile_nameFilterBox.setVisible(false);

		if (!ProfileUtil.isAdmin())
			entity_nameFilterBox.setVisible(false);

		if (!ProfileUtil.isAdmin())
			directAccessFilterBox.setVisible(false);

		if (!ProfileUtil.isAdmin())
			createFilterBox.setVisible(false);

		if (!ProfileUtil.isAdmin())
			deleteFilterBox.setVisible(false);

		if (!ProfileUtil.isAdmin())
			exportFilterBox.setVisible(false);

	}
}
