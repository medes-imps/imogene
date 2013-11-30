package org.imogene.admin.client.dataprovider;

import java.util.ArrayList;
import java.util.List;

import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.admin.shared.AdminRequestFactory;
import org.imogene.admin.shared.request.FieldGroupProfileRequest;
import org.imogene.lib.common.constants.CriteriaConstants;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.table.ImogBeanDataProvider;
import org.imogene.web.shared.proxy.FieldGroupProfileProxy;
import org.imogene.web.shared.proxy.criteria.BasicCriteriaProxy;
import org.imogene.web.shared.proxy.criteria.ImogCriterionProxy;
import org.imogene.web.shared.proxy.criteria.ImogDisjunctionProxy;
import org.imogene.web.shared.proxy.criteria.ImogJunctionProxy;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;

/**
 * Data provider to feed the FieldGroupProfile entry Table and Selection List
 * 
 * @author MEDES-IMPS
 */
public class FieldGroupProfileDataProvider extends ImogBeanDataProvider<FieldGroupProfileProxy> {

	private final AdminRequestFactory requestFactory;
	private boolean nonAffected = false;
	private boolean searchInReverse = false;
	private String property = null;

	/**
	 * For Relation Fields Provides instances of entity FieldGroupProfile with pagination
	 */
	public FieldGroupProfileDataProvider(AdminRequestFactory requestFactory) {
		this.requestFactory = requestFactory;
	}

	/**
	 * For Relation Fields Provides instances of entity FieldGroupProfile that have non affected values for a given
	 * property (RelationField with card==1) with pagination
	 * 
	 * @param pProperty the property for which non affected values are searched
	 */
	public FieldGroupProfileDataProvider(AdminRequestFactory requestFactory, String pProperty) {
		this.requestFactory = requestFactory;
		nonAffected = true;
		property = pProperty;
	}

	/**
	 * For Relation Fields Provides filtered instances of entity FieldGroupProfile that have non affected values for a
	 * given property (RelationField with card==1) with pagination
	 * 
	 * @param pProperty the property for which non affected values are searched
	 * @param searchInReverse true for 1:1 relations, if the property for which non affected values are searched shall
	 *            be looked in reverse relation
	 */
	public FieldGroupProfileDataProvider(AdminRequestFactory requestFactory, String pProperty, boolean searchInReverse) {
		this.requestFactory = requestFactory;
		nonAffected = true;
		property = pProperty;
		this.searchInReverse = searchInReverse;
	}

	/**
	 * Called by Relation Boxes
	 */
	@Override
	public Request<List<FieldGroupProfileProxy>> getList(int start, int numRows) {

		FieldGroupProfileRequest request = (FieldGroupProfileRequest) getContext();
		Request<List<FieldGroupProfileProxy>> result = null;

		if (isFiltered) {
			/* permanently filtered - hierarchical lists */
			if (filterCriteria != null) {

				if (searchCriterions != null) {
					/* permanent filter added to search criterion */
					if (nonAffected) {
						if (!searchInReverse)
							result = request.listNonAffectedFieldGroupProfile(start, numRows, "modified", false,
									searchCriterions, property);
						else
							result = request.listNonAffectedFieldGroupProfileReverse(start, numRows, "modified", false,
									searchCriterions, property);
					} else
						result = request.listFieldGroupProfile(start, numRows, "modified", false, searchCriterions);
				} else {
					/* permanent filter only */
					if (nonAffected) {
						if (!searchInReverse)
							result = request.listNonAffectedFieldGroupProfile(start, numRows, "modified", false,
									filterCriteria, property);
						else
							result = request.listNonAffectedFieldGroupProfileReverse(start, numRows, "modified", false,
									filterCriteria, property);
					} else
						result = request.listFieldGroupProfile(start, numRows, "modified", false, filterCriteria);
				}

			} else
				result = request.getFieldGroupProfileEmptyList();
		} else {
			if (searchCriterions != null) {

				if (nonAffected) {
					if (!searchInReverse)
						result = request.listNonAffectedFieldGroupProfile(start, numRows, "modified", false,
								searchCriterions, property);
					else
						result = request.listNonAffectedFieldGroupProfileReverse(start, numRows, "modified", false,
								searchCriterions, property);
				} else
					result = request.listFieldGroupProfile(start, numRows, "modified", false, searchCriterions);
			} else {

				if (nonAffected) {
					if (!searchInReverse)
						result = request.listNonAffectedFieldGroupProfile(start, numRows, "modified", false, property);
					else
						result = request.listNonAffectedFieldGroupProfileReverse(start, numRows, "modified", false,
								property);
				} else
					result = request.listFieldGroupProfile(start, numRows, "modified", false);
			}
		}

		if (isFiltered) {
			result.with("profile");
			result.with("fieldGroup");
		}

		else {
			result.with("profile");
			result.with("fieldGroup");
		}

		return result;
	}

	/**
	 * Called by Dynatable
	 */
	@Override
	public Request<List<FieldGroupProfileProxy>> getList(String property, int start, int numRows, boolean asc) {

		FieldGroupProfileRequest request = (FieldGroupProfileRequest) getContext();
		Request<List<FieldGroupProfileProxy>> result = null;

		if (isFiltered) {
			/* permanently filtered - hierarchical lists */
			if (filterCriteria != null) {

				if (searchCriterions != null)
					/* permanent filter added to search criterion */
					result = request.listFieldGroupProfile(start, numRows, property, asc, searchCriterions);
				else
					/* permanent filter only */
					result = request.listFieldGroupProfile(start, numRows, property, asc, filterCriteria);

			} else
				result = request.getFieldGroupProfileEmptyList();
		} else {
			if (searchCriterions != null)
				result = request.listFieldGroupProfile(start, numRows, property, asc, searchCriterions);
			else
				result = request.listFieldGroupProfile(start, numRows, property, asc);
		}

		result.with("profile");
		result.with("fieldGroup");

		return result;
	}

	@Override
	public Request<Long> getTotalRowCount() {
		FieldGroupProfileRequest request = (FieldGroupProfileRequest) getContext();

		if (isFiltered) {
			/* permanently filtered - hierarchical lists */
			if (filterCriteria != null) {

				if (searchCriterions != null) {
					/* permanent filter added to search criterion */
					if (nonAffected) {
						if (!searchInReverse)
							return request.countNonAffectedFieldGroupProfile(property, searchCriterions);
						else
							return request.countNonAffectedFieldGroupProfileReverse(property, searchCriterions);
					} else
						return request.countFieldGroupProfile(searchCriterions);
				} else {
					/* permanent filter only */
					if (nonAffected) {
						if (!searchInReverse)
							return request.countNonAffectedFieldGroupProfile(property, filterCriteria);
						else
							return request.countNonAffectedFieldGroupProfileReverse(property, filterCriteria);
					} else
						return request.countFieldGroupProfile(filterCriteria);
				}

			} else
				return request.countNonAffectedFieldGroupProfile("id");
		} else {

			if (searchCriterions != null) {
				if (nonAffected) {
					if (!searchInReverse)
						return request.countNonAffectedFieldGroupProfile(property, searchCriterions);
					else
						return request.countNonAffectedFieldGroupProfileReverse(property, searchCriterions);
				} else
					return request.countFieldGroupProfile(searchCriterions);
			} else {

				if (nonAffected) {
					if (!searchInReverse)
						return request.countNonAffectedFieldGroupProfile(property);
					else
						return request.countNonAffectedFieldGroupProfileReverse(property);
				} else
					return request.countFieldGroupProfile();
			}
		}
	}

	@Override
	public RequestContext getEntityContext() {
		return requestFactory.fieldGroupProfileRequest();
	}

	@Override
	public String fullTextSearch(String text) {

		boolean fullTextSearch = false;
		StringBuffer buffer = new StringBuffer(BaseNLS.constants().label_filtered() + " ");

		if (text == null || (text != null && text.equals(""))) {
			setSearchCriterions(null);
		} else {
			FieldGroupProfileRequest request = (FieldGroupProfileRequest) getContext();
			newRequest = false;

			ImogJunctionProxy disJunction = request.create(ImogDisjunctionProxy.class);
			List<ImogCriterionProxy> criterionList = new ArrayList<ImogCriterionProxy>();

			// Search field Name
			BasicCriteriaProxy profile_nameCrit = request.create(BasicCriteriaProxy.class);
			profile_nameCrit.setField("profile.name");
			profile_nameCrit.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
			profile_nameCrit.setValue(text);
			buffer.append("(" + AdminNLS.constants().profile_field_name() + ": " + text + ") " + SYMBOL_OR + " ");
			criterionList.add(profile_nameCrit);

			// Search field Name
			BasicCriteriaProxy fieldGroup_nameCrit = request.create(BasicCriteriaProxy.class);
			fieldGroup_nameCrit.setField("fieldGroup.name");
			fieldGroup_nameCrit.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
			fieldGroup_nameCrit.setValue(text);
			buffer.append("(" + AdminNLS.constants().fieldGroup_field_name() + ": " + text + ") " + SYMBOL_OR + " ");
			criterionList.add(fieldGroup_nameCrit);

			// Search field Read
			if (text.toLowerCase().equals("true") || text.toLowerCase().equals("false")) {
				BasicCriteriaProxy readCrit = request.create(BasicCriteriaProxy.class);
				readCrit.setField("read");
				readCrit.setOperation(CriteriaConstants.BOOLEAN_OPERATOR_EQUAL);
				readCrit.setValue(text);
				buffer.append("(" + AdminNLS.constants().fieldGroupProfile_field_read() + ": " + text + ") "
						+ SYMBOL_OR + " ");
				criterionList.add(readCrit);
			}

			// Search field Write
			if (text.toLowerCase().equals("true") || text.toLowerCase().equals("false")) {
				BasicCriteriaProxy writeCrit = request.create(BasicCriteriaProxy.class);
				writeCrit.setField("write");
				writeCrit.setOperation(CriteriaConstants.BOOLEAN_OPERATOR_EQUAL);
				writeCrit.setValue(text);
				buffer.append("(" + AdminNLS.constants().fieldGroupProfile_field_write() + ": " + text + ") "
						+ SYMBOL_OR + " ");
				criterionList.add(writeCrit);
			}

			// Search field Export
			if (text.toLowerCase().equals("true") || text.toLowerCase().equals("false")) {
				BasicCriteriaProxy exportCrit = request.create(BasicCriteriaProxy.class);
				exportCrit.setField("export");
				exportCrit.setOperation(CriteriaConstants.BOOLEAN_OPERATOR_EQUAL);
				exportCrit.setValue(text);
				buffer.append("(" + AdminNLS.constants().fieldGroupProfile_field_export() + ": " + text + ") "
						+ SYMBOL_OR + " ");
				criterionList.add(exportCrit);
			}

			disJunction.setCriterions(criterionList);
			fullTextSearch = true;

			// add FilterCriteria if exists
			if (isFiltered && filterCriteria != null)
				setSearchCriterions(mergeFilterCriteriaAndFullTextSearchCriterion(request, disJunction));
			else
				setSearchCriterions(disJunction);

		}
		if (fullTextSearch) {
			String message = buffer.toString();
			int lastSymbolIndex = message.lastIndexOf(SYMBOL_OR);
			return message.substring(0, lastSymbolIndex);
		} else
			return null;
	}

}
