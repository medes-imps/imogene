package org.imogene.admin.client.dataprovider;

import java.util.ArrayList;
import java.util.List;

import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.admin.shared.AdminRequestFactory;
import org.imogene.admin.shared.request.ProfileRequest;
import org.imogene.lib.common.constants.CriteriaConstants;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.table.ImogBeanDataProvider;
import org.imogene.web.shared.proxy.ProfileProxy;
import org.imogene.web.shared.proxy.criteria.BasicCriteriaProxy;
import org.imogene.web.shared.proxy.criteria.ImogCriterionProxy;
import org.imogene.web.shared.proxy.criteria.ImogDisjunctionProxy;
import org.imogene.web.shared.proxy.criteria.ImogJunctionProxy;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;

/**
 * Data provider to feed the Profile entry Table and Selection List
 * 
 * @author MEDES-IMPS
 */
public class ProfileDataProvider extends ImogBeanDataProvider<ProfileProxy> {

	private final AdminRequestFactory requestFactory;
	private boolean nonAffected = false;
	private boolean searchInReverse = false;
	private String property = null;

	/**
	 * For Relation Fields Provides instances of entity Profile with pagination
	 */
	public ProfileDataProvider(AdminRequestFactory requestFactory) {
		this.requestFactory = requestFactory;
	}

	/**
	 * For Relation Fields Provides instances of entity Profile that have non affected values for a given property
	 * (RelationField with card==1) with pagination
	 * 
	 * @param pProperty the property for which non affected values are searched
	 */
	public ProfileDataProvider(AdminRequestFactory requestFactory, String pProperty) {
		this.requestFactory = requestFactory;
		nonAffected = true;
		property = pProperty;
	}

	/**
	 * For Relation Fields Provides filtered instances of entity Profile that have non affected values for a given
	 * property (RelationField with card==1) with pagination
	 * 
	 * @param pProperty the property for which non affected values are searched
	 * @param searchInReverse true for 1:1 relations, if the property for which non affected values are searched shall
	 *            be looked in reverse relation
	 */
	public ProfileDataProvider(AdminRequestFactory requestFactory, String pProperty, boolean searchInReverse) {
		this.requestFactory = requestFactory;
		nonAffected = true;
		property = pProperty;
		this.searchInReverse = searchInReverse;
	}

	/**
	 * Called by Relation Boxes
	 */
	@Override
	public Request<List<ProfileProxy>> getList(int start, int numRows) {

		ProfileRequest request = (ProfileRequest) getContext();
		Request<List<ProfileProxy>> result = null;

		if (isFiltered) {
			/* permanently filtered - hierarchical lists */
			if (filterCriteria != null) {

				if (searchCriterions != null) {
					/* permanent filter added to search criterion */
					if (nonAffected) {
						if (!searchInReverse)
							result = request.listNonAffectedProfile(start, numRows, "modified", false,
									searchCriterions, property);
						else
							result = request.listNonAffectedProfileReverse(start, numRows, "modified", false,
									searchCriterions, property);
					} else
						result = request.listProfile(start, numRows, "modified", false, searchCriterions);
				} else {
					/* permanent filter only */
					if (nonAffected) {
						if (!searchInReverse)
							result = request.listNonAffectedProfile(start, numRows, "modified", false, filterCriteria,
									property);
						else
							result = request.listNonAffectedProfileReverse(start, numRows, "modified", false,
									filterCriteria, property);
					} else
						result = request.listProfile(start, numRows, "modified", false, filterCriteria);
				}

			} else
				result = request.getProfileEmptyList();
		} else {
			if (searchCriterions != null) {

				if (nonAffected) {
					if (!searchInReverse)
						result = request.listNonAffectedProfile(start, numRows, "modified", false, searchCriterions,
								property);
					else
						result = request.listNonAffectedProfileReverse(start, numRows, "modified", false,
								searchCriterions, property);
				} else
					result = request.listProfile(start, numRows, "modified", false, searchCriterions);
			} else {

				if (nonAffected) {
					if (!searchInReverse)
						result = request.listNonAffectedProfile(start, numRows, "modified", false, property);
					else
						result = request.listNonAffectedProfileReverse(start, numRows, "modified", false, property);
				} else
					result = request.listProfile(start, numRows, "modified", false);
			}
		}

		if (isFiltered) {
		}

		else {
		}

		return result;
	}

	/**
	 * Called by Dynatable
	 */
	@Override
	public Request<List<ProfileProxy>> getList(String property, int start, int numRows, boolean asc) {

		ProfileRequest request = (ProfileRequest) getContext();
		Request<List<ProfileProxy>> result = null;

		if (isFiltered) {
			/* permanently filtered - hierarchical lists */
			if (filterCriteria != null) {

				if (searchCriterions != null)
					/* permanent filter added to search criterion */
					result = request.listProfile(start, numRows, property, asc, searchCriterions);
				else
					/* permanent filter only */
					result = request.listProfile(start, numRows, property, asc, filterCriteria);

			} else
				result = request.getProfileEmptyList();
		} else {
			if (searchCriterions != null)
				result = request.listProfile(start, numRows, property, asc, searchCriterions);
			else
				result = request.listProfile(start, numRows, property, asc);
		}

		return result;
	}

	@Override
	public Request<Long> getTotalRowCount() {
		ProfileRequest request = (ProfileRequest) getContext();

		if (isFiltered) {
			/* permanently filtered - hierarchical lists */
			if (filterCriteria != null) {

				if (searchCriterions != null) {
					/* permanent filter added to search criterion */
					if (nonAffected) {
						if (!searchInReverse)
							return request.countNonAffectedProfile(property, searchCriterions);
						else
							return request.countNonAffectedProfileReverse(property, searchCriterions);
					} else
						return request.countProfile(searchCriterions);
				} else {
					/* permanent filter only */
					if (nonAffected) {
						if (!searchInReverse)
							return request.countNonAffectedProfile(property, filterCriteria);
						else
							return request.countNonAffectedProfileReverse(property, filterCriteria);
					} else
						return request.countProfile(filterCriteria);
				}

			} else
				return request.countNonAffectedProfile("id");
		} else {

			if (searchCriterions != null) {
				if (nonAffected) {
					if (!searchInReverse)
						return request.countNonAffectedProfile(property, searchCriterions);
					else
						return request.countNonAffectedProfileReverse(property, searchCriterions);
				} else
					return request.countProfile(searchCriterions);
			} else {

				if (nonAffected) {
					if (!searchInReverse)
						return request.countNonAffectedProfile(property);
					else
						return request.countNonAffectedProfileReverse(property);
				} else
					return request.countProfile();
			}
		}
	}

	@Override
	public RequestContext getEntityContext() {
		return requestFactory.profileRequest();
	}

	@Override
	public String fullTextSearch(String text) {

		boolean fullTextSearch = false;
		StringBuffer buffer = new StringBuffer(BaseNLS.constants().label_filtered() + " ");

		if (text == null || (text != null && text.equals(""))) {
			setSearchCriterions(null);
		} else {
			ProfileRequest request = (ProfileRequest) getContext();
			newRequest = false;

			ImogJunctionProxy disJunction = request.create(ImogDisjunctionProxy.class);
			List<ImogCriterionProxy> criterionList = new ArrayList<ImogCriterionProxy>();

			// Search field Name
			BasicCriteriaProxy nameCrit = request.create(BasicCriteriaProxy.class);
			nameCrit.setField("name");
			nameCrit.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
			nameCrit.setValue(text);
			buffer.append("(" + AdminNLS.constants().profile_field_name() + ": " + text + ") " + SYMBOL_OR + " ");
			criterionList.add(nameCrit);

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
