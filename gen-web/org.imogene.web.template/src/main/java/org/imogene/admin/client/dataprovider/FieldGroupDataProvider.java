package org.imogene.admin.client.dataprovider;

import java.util.ArrayList;
import java.util.List;

import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.admin.shared.AdminRequestFactory;
import org.imogene.admin.shared.request.FieldGroupRequest;
import org.imogene.lib.common.constants.CriteriaConstants;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.table.ImogBeanDataProvider;
import org.imogene.web.shared.proxy.FieldGroupProxy;
import org.imogene.web.shared.proxy.criteria.BasicCriteriaProxy;
import org.imogene.web.shared.proxy.criteria.ImogCriterionProxy;
import org.imogene.web.shared.proxy.criteria.ImogDisjunctionProxy;
import org.imogene.web.shared.proxy.criteria.ImogJunctionProxy;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;

/**
 * Data provider to feed the FieldGroup entry Table and Selection List
 * 
 * @author MEDES-IMPS
 */
public class FieldGroupDataProvider extends ImogBeanDataProvider<FieldGroupProxy> {

	private final AdminRequestFactory requestFactory;
	private boolean nonAffected = false;
	private boolean searchInReverse = false;
	private String property = null;

	/**
	 * For Relation Fields Provides instances of entity FieldGroup with pagination
	 */
	public FieldGroupDataProvider(AdminRequestFactory requestFactory) {
		this.requestFactory = requestFactory;
	}

	/**
	 * For Relation Fields Provides instances of entity FieldGroup that have non affected values for a given property
	 * (RelationField with card==1) with pagination
	 * 
	 * @param pProperty the property for which non affected values are searched
	 */
	public FieldGroupDataProvider(AdminRequestFactory requestFactory, String pProperty) {
		this.requestFactory = requestFactory;
		nonAffected = true;
		property = pProperty;
	}

	/**
	 * For Relation Fields Provides filtered instances of entity FieldGroup that have non affected values for a given
	 * property (RelationField with card==1) with pagination
	 * 
	 * @param pProperty the property for which non affected values are searched
	 * @param searchInReverse true for 1:1 relations, if the property for which non affected values are searched shall
	 *        be looked in reverse relation
	 */
	public FieldGroupDataProvider(AdminRequestFactory requestFactory, String pProperty, boolean searchInReverse) {
		this.requestFactory = requestFactory;
		nonAffected = true;
		property = pProperty;
		this.searchInReverse = searchInReverse;
	}

	/**
	 * Called by Relation Boxes
	 */
	@Override
	public Request<List<FieldGroupProxy>> getList(int start, int numRows) {

		FieldGroupRequest request = (FieldGroupRequest) getContext();
		Request<List<FieldGroupProxy>> result = null;

		if (isFiltered) {
			/* permanently filtered - hierarchical lists */
			if (filterCriteria != null) {

				if (searchCriterions != null) {
					/* permanent filter added to search criterion */
					if (nonAffected) {
						if (!searchInReverse)
							result = request.listNonAffectedFieldGroup(start, numRows, "modified", false,
									searchCriterions, property);
						else
							result = request.listNonAffectedFieldGroupReverse(start, numRows, "modified", false,
									searchCriterions, property);
					} else
						result = request.listFieldGroup(start, numRows, "modified", false, searchCriterions);
				} else {
					/* permanent filter only */
					if (nonAffected) {
						if (!searchInReverse)
							result = request.listNonAffectedFieldGroup(start, numRows, "modified", false,
									filterCriteria, property);
						else
							result = request.listNonAffectedFieldGroupReverse(start, numRows, "modified", false,
									filterCriteria, property);
					} else
						result = request.listFieldGroup(start, numRows, "modified", false, filterCriteria);
				}

			} else
				result = request.getFieldGroupEmptyList();
		} else {
			if (searchCriterions != null) {

				if (nonAffected) {
					if (!searchInReverse)
						result = request.listNonAffectedFieldGroup(start, numRows, "modified", false, searchCriterions,
								property);
					else
						result = request.listNonAffectedFieldGroupReverse(start, numRows, "modified", false,
								searchCriterions, property);
				} else
					result = request.listFieldGroup(start, numRows, "modified", false, searchCriterions);
			} else {

				if (nonAffected) {
					if (!searchInReverse)
						result = request.listNonAffectedFieldGroup(start, numRows, "modified", false, property);
					else
						result = request.listNonAffectedFieldGroupReverse(start, numRows, "modified", false, property);
				} else
					result = request.listFieldGroup(start, numRows, "modified", false);
			}
		}

		if (isFiltered) {
			result.with("entity");
		}

		else {
			result.with("entity");
		}

		return result;
	}

	/**
	 * Called by Dynatable
	 */
	@Override
	public Request<List<FieldGroupProxy>> getList(String property, int start, int numRows, boolean asc) {

		FieldGroupRequest request = (FieldGroupRequest) getContext();
		Request<List<FieldGroupProxy>> result = null;

		if (isFiltered) {
			/* permanently filtered - hierarchical lists */
			if (filterCriteria != null) {

				if (searchCriterions != null)
					/* permanent filter added to search criterion */
					result = request.listFieldGroup(start, numRows, property, asc, searchCriterions);
				else
					/* permanent filter only */
					result = request.listFieldGroup(start, numRows, property, asc, filterCriteria);

			} else
				result = request.getFieldGroupEmptyList();
		} else {
			if (searchCriterions != null)
				result = request.listFieldGroup(start, numRows, property, asc, searchCriterions);
			else
				result = request.listFieldGroup(start, numRows, property, asc);
		}

		result.with("entity");

		return result;
	}

	@Override
	public Request<Long> getTotalRowCount() {
		FieldGroupRequest request = (FieldGroupRequest) getContext();

		if (isFiltered) {
			/* permanently filtered - hierarchical lists */
			if (filterCriteria != null) {

				if (searchCriterions != null) {
					/* permanent filter added to search criterion */
					if (nonAffected) {
						if (!searchInReverse)
							return request.countNonAffectedFieldGroup(property, searchCriterions);
						else
							return request.countNonAffectedFieldGroupReverse(property, searchCriterions);
					} else
						return request.countFieldGroup(searchCriterions);
				} else {
					/* permanent filter only */
					if (nonAffected) {
						if (!searchInReverse)
							return request.countNonAffectedFieldGroup(property, filterCriteria);
						else
							return request.countNonAffectedFieldGroupReverse(property, filterCriteria);
					} else
						return request.countFieldGroup(filterCriteria);
				}

			} else
				return request.countNonAffectedFieldGroup("id");
		} else {

			if (searchCriterions != null) {
				if (nonAffected) {
					if (!searchInReverse)
						return request.countNonAffectedFieldGroup(property, searchCriterions);
					else
						return request.countNonAffectedFieldGroupReverse(property, searchCriterions);
				} else
					return request.countFieldGroup(searchCriterions);
			} else {

				if (nonAffected) {
					if (!searchInReverse)
						return request.countNonAffectedFieldGroup(property);
					else
						return request.countNonAffectedFieldGroupReverse(property);
				} else
					return request.countFieldGroup();
			}
		}
	}

	@Override
	public RequestContext getEntityContext() {
		return requestFactory.fieldGroupRequest();
	}

	@Override
	public String fullTextSearch(String text) {

		boolean fullTextSearch = false;
		StringBuffer buffer = new StringBuffer(BaseNLS.constants().label_filtered() + " ");

		if (text == null || (text != null && text.equals(""))) {
			setSearchCriterions(null);
		} else {
			FieldGroupRequest request = (FieldGroupRequest) getContext();
			newRequest = false;

			ImogJunctionProxy disJunction = request.create(ImogDisjunctionProxy.class);
			List<ImogCriterionProxy> criterionList = new ArrayList<ImogCriterionProxy>();

			// Search field Name
			BasicCriteriaProxy entity_nameCrit = request.create(BasicCriteriaProxy.class);
			entity_nameCrit.setField("entity.name");
			entity_nameCrit.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
			entity_nameCrit.setValue(text);
			buffer.append("(" + AdminNLS.constants().cardEntity_field_name() + ": " + text + ") " + SYMBOL_OR + " ");
			criterionList.add(entity_nameCrit);

			// Search field Name
			BasicCriteriaProxy nameCrit = request.create(BasicCriteriaProxy.class);
			nameCrit.setField("name");
			nameCrit.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
			nameCrit.setValue(text);
			buffer.append("(" + AdminNLS.constants().fieldGroup_field_name() + ": " + text + ") " + SYMBOL_OR + " ");
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
