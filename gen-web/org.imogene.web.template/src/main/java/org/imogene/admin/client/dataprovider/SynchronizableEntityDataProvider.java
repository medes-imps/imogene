package org.imogene.admin.client.dataprovider;

import java.util.ArrayList;
import java.util.List;

import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.admin.shared.AdminRequestFactory;
import org.imogene.admin.shared.request.SynchronizableEntityRequest;
import org.imogene.lib.common.constants.CriteriaConstants;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.table.ImogBeanDataProvider;
import org.imogene.web.shared.proxy.SynchronizableEntityProxy;
import org.imogene.web.shared.proxy.criteria.BasicCriteriaProxy;
import org.imogene.web.shared.proxy.criteria.ImogConjunctionProxy;
import org.imogene.web.shared.proxy.criteria.ImogCriterionProxy;
import org.imogene.web.shared.proxy.criteria.ImogDisjunctionProxy;
import org.imogene.web.shared.proxy.criteria.ImogJunctionProxy;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;

/**
 * Data provider to feed the SynchronizableEntity entry Table and Selection List
 * @author MEDES-IMPS
 */
public class SynchronizableEntityDataProvider
		extends
			ImogBeanDataProvider<SynchronizableEntityProxy> {

	private final AdminRequestFactory requestFactory;
	private boolean nonAffected = false;
	private boolean searchInReverse = false;
	private String property = null;

	/**
	 * For Relation Fields
	 * Provides instances of entity SynchronizableEntity with pagination
	 */
	public SynchronizableEntityDataProvider(AdminRequestFactory requestFactory) {
		this.requestFactory = requestFactory;
	}

	/**
	 * For Relation Fields
	 * Provides instances of entity SynchronizableEntity that have non affected values for a given property (RelationField with card==1) with pagination
	 * @param pProperty the property for which non affected values are searched
	 */
	public SynchronizableEntityDataProvider(AdminRequestFactory requestFactory,
			String pProperty) {
		this.requestFactory = requestFactory;
		nonAffected = true;
		property = pProperty;
	}

	/**
	 * For Relation Fields
	 * Provides filtered instances of entity SynchronizableEntity that have non affected values for a given property (RelationField with card==1) with pagination
	 * @param pProperty the property for which non affected values are searched
	 * @param searchInReverse true for 1:1 relations, if the property for which non affected values are searched shall be looked in reverse relation
	 */
	public SynchronizableEntityDataProvider(AdminRequestFactory requestFactory,
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
	public Request<List<SynchronizableEntityProxy>> getList(int start,
			int numRows) {

		SynchronizableEntityRequest request = (SynchronizableEntityRequest) getContext();
		Request<List<SynchronizableEntityProxy>> result = null;

		if (isFiltered) {
			/* permanently filtered - hierarchical lists */
			if (filterCriteria != null) {

				if (searchCriterions != null) {
					/* permanent filter added to search criterion */
					if (nonAffected) {
						if (!searchInReverse)
							result = request
									.listNonAffectedSynchronizableEntity(start,
											numRows, "name", true,
											searchCriterions, property);
						else
							result = request
									.listNonAffectedSynchronizableEntityReverse(
											start, numRows, "name", true,
											searchCriterions, property);
					} else
						result = request.listSynchronizableEntity(start,
								numRows, "name", true, searchCriterions);
				} else {
					/* permanent filter only */
					if (nonAffected) {
						if (!searchInReverse)
							result = request
									.listNonAffectedSynchronizableEntity(start,
											numRows, "name", true,
											filterCriteria, property);
						else
							result = request
									.listNonAffectedSynchronizableEntityReverse(
											start, numRows, "name", true,
											filterCriteria, property);
					} else
						result = request.listSynchronizableEntity(start,
								numRows, "name", true, filterCriteria);
				}

			} else
				result = request.getSynchronizableEntityEmptyList();
		} else {
			if (searchCriterions != null) {

				if (nonAffected) {
					if (!searchInReverse)
						result = request.listNonAffectedSynchronizableEntity(
								start, numRows, "name", true, searchCriterions,
								property);
					else
						result = request
								.listNonAffectedSynchronizableEntityReverse(
										start, numRows, "name", true,
										searchCriterions, property);
				} else
					result = request.listSynchronizableEntity(start, numRows,
							"name", true, searchCriterions);
			} else {

				if (nonAffected) {
					if (!searchInReverse)
						result = request.listNonAffectedSynchronizableEntity(
								start, numRows, "name", true, property);
					else
						result = request
								.listNonAffectedSynchronizableEntityReverse(
										start, numRows, "name", true, property);
				} else
					result = request.listSynchronizableEntity(start, numRows,
							"name", true);
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
	public Request<List<SynchronizableEntityProxy>> getList(String property,
			int start, int numRows, boolean asc) {

		SynchronizableEntityRequest request = (SynchronizableEntityRequest) getContext();
		Request<List<SynchronizableEntityProxy>> result = null;
		if (getSearchCriterions() == null)
			result = request.listSynchronizableEntity(start, numRows, property,
					asc);
		else
			result = request.listSynchronizableEntity(start, numRows, property,
					asc, getSearchCriterions());

		return result;
	}

	@Override
	public Request<Long> getTotalRowCount() {
		SynchronizableEntityRequest request = (SynchronizableEntityRequest) getContext();

		if (isFiltered) {
			/* permanently filtered - hierarchical lists */
			if (filterCriteria != null) {

				if (searchCriterions != null) {
					/* permanent filter added to search criterion */
					if (nonAffected) {
						if (!searchInReverse)
							return request
									.countNonAffectedSynchronizableEntity(
											property, searchCriterions);
						else
							return request
									.countNonAffectedSynchronizableEntityReverse(
											property, searchCriterions);
					} else
						return request
								.countSynchronizableEntity(searchCriterions);
				} else {
					/* permanent filter only */
					if (nonAffected) {
						if (!searchInReverse)
							return request
									.countNonAffectedSynchronizableEntity(
											property, filterCriteria);
						else
							return request
									.countNonAffectedSynchronizableEntityReverse(
											property, filterCriteria);
					} else
						return request
								.countSynchronizableEntity(filterCriteria);
				}

			} else
				return request.countNonAffectedSynchronizableEntity("id");
		} else {

			if (searchCriterions != null) {
				if (nonAffected) {
					if (!searchInReverse)
						return request.countNonAffectedSynchronizableEntity(
								property, searchCriterions);
					else
						return request
								.countNonAffectedSynchronizableEntityReverse(
										property, searchCriterions);
				} else
					return request.countSynchronizableEntity(searchCriterions);
			} else {

				if (nonAffected) {
					if (!searchInReverse)
						return request
								.countNonAffectedSynchronizableEntity(property);
					else
						return request
								.countNonAffectedSynchronizableEntityReverse(property);
				} else
					return request.countSynchronizableEntity();
			}
		}
	}

	@Override
	public RequestContext getEntityContext() {
		return requestFactory.synchronizableEntityRequest();
	}

	@Override
	public String fullTextSearch(String text) {

		boolean fullTextSearch = false;
		StringBuffer buffer = new StringBuffer(BaseNLS.constants()
				.label_filtered() + " ");

		if (text == null || (text != null && text.equals(""))) {
			setSearchCriterions(null);
		} else {

			SynchronizableEntityRequest request = (SynchronizableEntityRequest) getContext();
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
			buffer.append("("
					+ AdminNLS.constants().synchronizableEntity_field_name() + ": "
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

}
