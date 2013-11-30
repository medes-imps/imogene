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
import org.imogene.web.client.util.ImogRoleUtil;

import com.google.gwt.user.client.ui.TextBox;

/**
 * Filter panel to filter the FieldGroupProfile entries
 * 
 * @author MEDES-IMPS
 */
public class FieldGroupProfileFilterPanel extends ImogFilterPanel {

	/* filter widgets */
	private TextBox profile_nameBox;
	private ImogFilterBox profile_nameFilterBox;
	private TextBox fieldgroup_nameBox;
	private ImogFilterBox fieldgroup_nameFilterBox;
	private ImogBooleanAsList readBox;
	private ImogFilterBox readFilterBox;
	private ImogBooleanAsList writeBox;
	private ImogFilterBox writeFilterBox;
	private ImogBooleanAsList exportBox;
	private ImogFilterBox exportFilterBox;

	public FieldGroupProfileFilterPanel() {
		super();
		setFieldReadAccess();
	}

	@Override
	public void resetFilterWidgets() {

		profile_nameBox.setValue(null);
		fieldgroup_nameBox.setValue(null);
		readBox.setValue(null);
		writeBox.setValue(null);
		exportBox.setValue(null);

	}

	@Override
	public void createFilterWidgets() {

		profile_nameBox = new TextBox();
		profile_nameFilterBox = new ImogFilterBox(profile_nameBox);
		profile_nameFilterBox.setFilterLabel(AdminNLS.constants().profile_field_name());
		addTableFilter(profile_nameFilterBox);

		fieldgroup_nameBox = new TextBox();
		fieldgroup_nameFilterBox = new ImogFilterBox(fieldgroup_nameBox);
		fieldgroup_nameFilterBox.setFilterLabel(AdminNLS.constants().fieldGroup_field_name());
		addTableFilter(fieldgroup_nameFilterBox);

		readBox = new ImogBooleanAsList();
		readFilterBox = new ImogFilterBox(readBox);
		readFilterBox.setFilterLabel(AdminNLS.constants().fieldGroupProfile_field_read());
		addTableFilter(readFilterBox);

		writeBox = new ImogBooleanAsList();
		writeFilterBox = new ImogFilterBox(writeBox);
		writeFilterBox.setFilterLabel(AdminNLS.constants().fieldGroupProfile_field_write());
		addTableFilter(writeFilterBox);

		exportBox = new ImogBooleanAsList();
		exportFilterBox = new ImogFilterBox(exportBox);
		exportFilterBox.setFilterLabel(AdminNLS.constants().fieldGroupProfile_field_export());
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

		FilterCriteria fieldgroup_nameCrit = new FilterCriteria();
		fieldgroup_nameCrit.setField("fieldGroup.name");
		fieldgroup_nameCrit.setFieldDisplayName(AdminNLS.constants().fieldGroup_field_name());
		fieldgroup_nameCrit.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
		fieldgroup_nameCrit.setValue(fieldgroup_nameBox.getValue());
		fieldgroup_nameCrit.setValueDisplayName(fieldgroup_nameBox.getValue());
		criteria.add(fieldgroup_nameCrit);

		FilterCriteria readCrit = new FilterCriteria();
		readCrit.setField("read");
		readCrit.setFieldDisplayName(AdminNLS.constants().fieldGroupProfile_field_read());
		readCrit.setOperation(CriteriaConstants.BOOLEAN_OPERATOR_EQUAL);
		if (readBox.getValue() == null)
			readCrit.setValue(null);
		else
			readCrit.setValue(String.valueOf(readBox.getValue()));
		readCrit.setValueDisplayName(BooleanUtil.getBooleanDisplayValue(readBox.getValue()));
		criteria.add(readCrit);

		FilterCriteria writeCrit = new FilterCriteria();
		writeCrit.setField("write");
		writeCrit.setFieldDisplayName(AdminNLS.constants().fieldGroupProfile_field_write());
		writeCrit.setOperation(CriteriaConstants.BOOLEAN_OPERATOR_EQUAL);
		if (writeBox.getValue() == null)
			writeCrit.setValue(null);
		else
			writeCrit.setValue(String.valueOf(writeBox.getValue()));
		writeCrit.setValueDisplayName(BooleanUtil.getBooleanDisplayValue(writeBox.getValue()));
		criteria.add(writeCrit);

		FilterCriteria exportCrit = new FilterCriteria();
		exportCrit.setField("export");
		exportCrit.setFieldDisplayName(AdminNLS.constants().fieldGroupProfile_field_export());
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

		if (!ImogRoleUtil.isAdmin())
			profile_nameFilterBox.setVisible(false);

		if (!ImogRoleUtil.isAdmin())
			fieldgroup_nameFilterBox.setVisible(false);

		if (!ImogRoleUtil.isAdmin())
			readFilterBox.setVisible(false);

		if (!ImogRoleUtil.isAdmin())
			writeFilterBox.setVisible(false);

		if (!ImogRoleUtil.isAdmin())
			exportFilterBox.setVisible(false);

	}
}
