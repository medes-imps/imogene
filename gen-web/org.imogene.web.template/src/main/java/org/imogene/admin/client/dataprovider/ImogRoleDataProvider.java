package org.imogene.admin.client.dataprovider;

import java.util.ArrayList;
import java.util.List;

import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.admin.shared.AdminRequestFactory;
import org.imogene.admin.shared.request.ImogRoleRequest;
import org.imogene.lib.common.constants.CriteriaConstants;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.table.ImogBeanDataProvider;
import org.imogene.web.shared.proxy.ImogRoleProxy;
import org.imogene.web.shared.proxy.criteria.BasicCriteriaProxy;
import org.imogene.web.shared.proxy.criteria.ImogConjunctionProxy;
import org.imogene.web.shared.proxy.criteria.ImogCriterionProxy;
import org.imogene.web.shared.proxy.criteria.ImogDisjunctionProxy;
import org.imogene.web.shared.proxy.criteria.ImogJunctionProxy;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;

/**
 * Data provider to feed the ImogRole entry Table and Selection List
 * @author MEDES-IMPS
 */
public class ImogRoleDataProvider extends ImogBeanDataProvider<ImogRoleProxy> {

	private final AdminRequestFactory requestFactory;
	private boolean nonAffected = false;
	private boolean searchInReverse = false;
	private String property = null;

	/**
	 * For Relation Fields
	 * Provides instances of entity ImogRole with pagination
	 */
	public ImogRoleDataProvider(AdminRequestFactory requestFactory) {
		this.requestFactory = requestFactory;
	}

	/**
	 * For Relation Fields
	 * Provides instances of entity ImogRole that have non affected values for a given property (RelationField with card==1) with pagination
	 * @param pProperty the property for which non affected values are searched
	 */
	public ImogRoleDataProvider(AdminRequestFactory requestFactory,
			String pProperty) {
		this.requestFactory = requestFactory;
		nonAffected = true;
		property = pProperty;
	}

	/**
	 * For Relation Fields
	 * Provides filtered instances of entity ImogRole that have non affected values for a given property (RelationField with card==1) with pagination
	 * @param pProperty the property for which non affected values are searched
	 * @param searchInReverse true for 1:1 relations, if the property for which non affected values are searched shall be looked in reverse relation
	 */
	public ImogRoleDataProvider(AdminRequestFactory requestFactory,
			String pProperty, boolean searchInReverse) {
		this.requestFactory = requestFactory;
		nonAffected = true;
		property = pProperty;
		this.searchInReverse = searchInReverse;
	}

	/**
	 * Called by Relation Boxes
	 */
	@Override
	public Request<List<ImogRoleProxy>> getList(int start, int numRows) {

		ImogRoleRequest request = (ImogRoleRequest) getContext();
		Request<List<ImogRoleProxy>> result = null;

		if (isFiltered) {
			/* permanently filtered - hierarchical lists */
			if (filterCriteria != null) {

				if (searchCriterions != null) {
					/* permanent filter added to search criterion */
					if (nonAffected) {
						if (!searchInReverse)
							result = request.listNonAffectedImogRole(start,
									numRows, "name", true, searchCriterions,
									property);
						else
							result = request.listNonAffectedImogRoleReverse(
									start, numRows, "name", true,
									searchCriterions, property);
					} else
						result = request.listImogRole(start, numRows, "name",
								true, searchCriterions);
				} else {
					/* permanent filter only */
					if (nonAffected) {
						if (!searchInReverse)
							result = request.listNonAffectedImogRole(start,
									numRows, "name", true, filterCriteria,
									property);
						else
							result = request.listNonAffectedImogRoleReverse(
									start, numRows, "name", true,
									filterCriteria, property);
					} else
						result = request.listImogRole(start, numRows, "name",
								true, filterCriteria);
				}

			} else
				result = request.getImogRoleEmptyList();
		} else {
			if (searchCriterions != null) {

				if (nonAffected) {
					if (!searchInReverse)
						result = request.listNonAffectedImogRole(start,
								numRows, "name", true, searchCriterions,
								property);
					else
						result = request.listNonAffectedImogRoleReverse(start,
								numRows, "name", true, searchCriterions,
								property);
				} else
					result = request.listImogRole(start, numRows, "name", true,
							searchCriterions);
			} else {

				if (nonAffected) {
					if (!searchInReverse)
						result = request.listNonAffectedImogRole(start,
								numRows, "name", true, property);
					else
						result = request.listNonAffectedImogRoleReverse(start,
								numRows, "name", true, property);
				} else
					result = request.listImogRole(start, numRows, "name", true);
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
	public Request<List<ImogRoleProxy>> getList(String property, int start,
			int numRows, boolean asc) {

		ImogRoleRequest request = (ImogRoleRequest) getContext();
		Request<List<ImogRoleProxy>> result = null;
		if (getSearchCriterions() == null)
			result = request.listImogRole(start, numRows, property, asc);
		else
			result = request.listImogRole(start, numRows, property, asc,
					getSearchCriterions());

		return result;
	}

	@Override
	public Request<Long> getTotalRowCount() {
		ImogRoleRequest request = (ImogRoleRequest) getContext();

		if (isFiltered) {
			/* permanently filtered - hierarchical lists */
			if (filterCriteria != null) {

				if (searchCriterions != null) {
					/* permanent filter added to search criterion */
					if (nonAffected) {
						if (!searchInReverse)
							return request.countNonAffectedImogRole(property,
									searchCriterions);
						else
							return request.countNonAffectedImogRoleReverse(
									property, searchCriterions);
					} else
						return request.countImogRole(searchCriterions);
				} else {
					/* permanent filter only */
					if (nonAffected) {
						if (!searchInReverse)
							return request.countNonAffectedImogRole(property,
									filterCriteria);
						else
							return request.countNonAffectedImogRoleReverse(
									property, filterCriteria);
					} else
						return request.countImogRole(filterCriteria);
				}

			} else
				return request.countNonAffectedImogRole("id");
		} else {

			if (searchCriterions != null) {
				if (nonAffected) {
					if (!searchInReverse)
						return request.countNonAffectedImogRole(property,
								searchCriterions);
					else
						return request.countNonAffectedImogRoleReverse(
								property, searchCriterions);
				} else
					return request.countImogRole(searchCriterions);
			} else {

				if (nonAffected) {
					if (!searchInReverse)
						return request.countNonAffectedImogRole(property);
					else
						return request
								.countNonAffectedImogRoleReverse(property);
				} else
					return request.countImogRole();
			}
		}
	}

	@Override
	public RequestContext getEntityContext() {
		return requestFactory.imogRoleRequest();
	}

	@Override
	public String fullTextSearch(String text) {

		boolean fullTextSearch = false;
		StringBuffer buffer = new StringBuffer(BaseNLS.constants()
				.label_filtered() + " ");

		if (text == null || (text != null && text.equals(""))) {
			setSearchCriterions(null);
		} else {

			ImogRoleRequest request = (ImogRoleRequest) getContext();
			newRequest = false;

			ImogJunctionProxy disJunction = request
					.create(ImogDisjunctionProxy.class);
			List<ImogCriterionProxy> criterionList = new ArrayList<ImogCriterionProxy>();

			// Search field Name
			BasicCriteriaProxy nameCrit = request
					.create(BasicCriteriaProxy.class);
			nameCrit.setField("name");
			nameCrit.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
			nameCrit.setValue(text);
			buffer.append("(" + AdminNLS.constants().imogRole_field_name() + ": "
					+ text + ") " + SYMBOL_OR + " ");
			criterionList.add(nameCrit);

			disJunction.setCriterions(criterionList);
			fullTextSearch = true;

			//add FilterCriteria if exists
			if (isFiltered && filterCriteria != null) {
				ImogJunctionProxy conJunctionFilt = request
						.create(ImogConjunctionProxy.class);
				List<ImogCriterionProxy> criterionListFilt = new ArrayList<ImogCriterionProxy>();
				criterionListFilt.add(filterCriteria);
				criterionListFilt.add(disJunction);
				conJunctionFilt.setCriterions(criterionListFilt);
				setSearchCriterions(conJunctionFilt);
			} else
				setSearchCriterions(disJunction);

		}
		if (fullTextSearch) {
			String message = buffer.toString();
			int lastSymbolIndex = message.lastIndexOf(SYMBOL_OR);
			return message.substring(0, lastSymbolIndex);
		} else
			return null;
	}
	
	/**
	 * 
	 * @param authorizedRoles
	 */
	public void setAuthorizedRoles(String[] authorizedRoles) {
		
		ImogRoleRequest request = (ImogRoleRequest) getContext();
		newRequest = false;

		ImogJunctionProxy disJunction = request.create(ImogDisjunctionProxy.class);
		List<ImogCriterionProxy> criterionList = new ArrayList<ImogCriterionProxy>();

		for(String role:authorizedRoles) {			
			BasicCriteriaProxy nameCrit = request.create(BasicCriteriaProxy.class);
			nameCrit.setField("id");
			nameCrit.setOperation(CriteriaConstants.STRING_OPERATOR_EQUAL);
			nameCrit.setValue(role);
			criterionList.add(nameCrit);
		}
		disJunction.setCriterions(criterionList);
		setFilterCriteria(disJunction);	
	}

}
