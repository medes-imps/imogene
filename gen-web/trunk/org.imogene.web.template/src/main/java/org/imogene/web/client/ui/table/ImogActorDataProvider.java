package org.imogene.web.client.ui.table;

import java.util.ArrayList;
import java.util.List;

import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.admin.shared.AdminRequestFactory;
import org.imogene.lib.common.constants.CriteriaConstants;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.shared.proxy.ImogActorProxy;
import org.imogene.web.shared.proxy.criteria.BasicCriteriaProxy;
import org.imogene.web.shared.proxy.criteria.ImogConjunctionProxy;
import org.imogene.web.shared.proxy.criteria.ImogCriterionProxy;
import org.imogene.web.shared.proxy.criteria.ImogDisjunctionProxy;
import org.imogene.web.shared.proxy.criteria.ImogJunctionProxy;
import org.imogene.web.shared.request.ImogActorRequest;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;

/**
 * Data provider to feed the AppliUser entry Table and Selection List
 * @author MEDES-IMPS
 */
public class ImogActorDataProvider extends ImogBeanDataProvider<ImogActorProxy> {


	private final AdminRequestFactory requestFactory;

	/**
	 * For Relation Fields
	 * Provides instances of entity AppliUser with pagination
	 */
	public ImogActorDataProvider(AdminRequestFactory requestFactory) {
		this.requestFactory = requestFactory;
	}

	/**
	 * For Relation Fields
	 * Provides instances of entity AppliUser that have non affected values for a given property (RelationField with card==1) with pagination
	 * @param pProperty the property for which non affected values are searched
	 */
	public ImogActorDataProvider(AdminRequestFactory requestFactory,
			String pProperty) {
		this.requestFactory = requestFactory;
	}

	/**
	 * For Relation Fields
	 * Provides filtered instances of entity AppliUser that have non affected values for a given property (RelationField with card==1) with pagination
	 * @param pProperty the property for which non affected values are searched
	 * @param searchInReverse true for 1:1 relations, if the property for which non affected values are searched shall be looked in reverse relation
	 */
	public ImogActorDataProvider(AdminRequestFactory requestFactory,
			String pProperty, boolean searchInReverse) {
		this.requestFactory = requestFactory;
	}

	/**
	 * Called by Relation Boxes
	 */
	@Override
	public Request<List<ImogActorProxy>> getList(int start, int numRows) {

		ImogActorRequest request = (ImogActorRequest) getContext();
		Request<List<ImogActorProxy>> result = null;

		if (isFiltered) {
			/* permanently filtered - hierarchical lists */
			if (filterCriteria != null) {

				if (searchCriterions != null) {
					/* permanent filter added to search criterion */
						result = request.listAppliUser(start, numRows, "login",
								true, searchCriterions);
				} else {
					/* permanent filter only */
						result = request.listAppliUser(start, numRows, "login",
								true, filterCriteria);
				}

			} else
				result = request.getAppliUserEmptyList();
		} else {
			if (searchCriterions != null) {
					result = request.listAppliUser(start, numRows, "login",
							true, searchCriterions);
			} else {
					result = request
							.listAppliUser(start, numRows, "login", true);
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
	public Request<List<ImogActorProxy>> getList(String property, int start,
			int numRows, boolean asc) {

		ImogActorRequest request = (ImogActorRequest) getContext();
		Request<List<ImogActorProxy>> result = null;
		if (getSearchCriterions() == null)
			result = request.listAppliUser(start, numRows, property, asc);
		else
			result = request.listAppliUser(start, numRows, property, asc,
					getSearchCriterions());

		return result;
	}

	@Override
	public Request<Long> getTotalRowCount() {
		ImogActorRequest request = (ImogActorRequest) getContext();

		if (isFiltered) {
			/* permanently filtered - hierarchical lists */
			if (filterCriteria != null) {

				if (searchCriterions != null) {
					/* permanent filter added to search criterion */
						return request.countAppliUser(searchCriterions);
				} else {
					/* permanent filter only */
						return request.countAppliUser(filterCriteria);
				}

			} else {
				ImogJunctionProxy conJunction = request.create(ImogConjunctionProxy.class);
				List<ImogCriterionProxy> criterionList = new ArrayList<ImogCriterionProxy>();
				BasicCriteriaProxy crit = request.create(BasicCriteriaProxy.class);
				crit.setField("id");
				crit.setOperation(CriteriaConstants.STRING_OPERATOR_EQUAL);
				crit.setValue("");
				criterionList.add(crit);
				conJunction.setCriterions(criterionList);
				return request.countAppliUser(conJunction);
			}
				
		} else {

			if (searchCriterions != null) {
					return request.countAppliUser(searchCriterions);
			} else {
					return request.countAppliUser();
			}
		}
	}

	@Override
	public RequestContext getEntityContext() {
		return requestFactory.imogActorRequest();
	}

	@Override
	public String fullTextSearch(String text) {

		boolean fullTextSearch = false;
		StringBuffer buffer = new StringBuffer(BaseNLS.constants()
				.label_filtered() + " ");

		if (text == null || (text != null && text.equals(""))) {
			setSearchCriterions(null);
		} else {

			ImogActorRequest request = (ImogActorRequest) getContext();
			newRequest = false;

			ImogJunctionProxy disJunction = request.create(ImogDisjunctionProxy.class);
			List<ImogCriterionProxy> criterionList = new ArrayList<ImogCriterionProxy>();

			// Search field Login
			BasicCriteriaProxy loginCrit = request
					.create(BasicCriteriaProxy.class);
			loginCrit.setField("login");
			loginCrit.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
			loginCrit.setValue(text);
			buffer.append("(" + AdminNLS.constants().imogActor_field_login() + ": "
					+ text + ") " + SYMBOL_OR + " ");
			criterionList.add(loginCrit);

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

}
