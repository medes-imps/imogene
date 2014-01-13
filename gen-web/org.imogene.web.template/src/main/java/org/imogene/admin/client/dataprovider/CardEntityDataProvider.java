package org.imogene.admin.client.dataprovider;

import java.util.ArrayList;
import java.util.List;

import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.admin.shared.AdminRequestFactory;
import org.imogene.admin.shared.request.CardEntityRequest;
import org.imogene.lib.common.constants.CriteriaConstants;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.table.ImogBeanDataProvider;
import org.imogene.web.shared.proxy.CardEntityProxy;
import org.imogene.web.shared.proxy.criteria.BasicCriteriaProxy;
import org.imogene.web.shared.proxy.criteria.ImogConjunctionProxy;
import org.imogene.web.shared.proxy.criteria.ImogCriterionProxy;
import org.imogene.web.shared.proxy.criteria.ImogDisjunctionProxy;
import org.imogene.web.shared.proxy.criteria.ImogJunctionProxy;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;

/**
 * Data provider to feed the CardEntity entry Table and Selection List
 * 
 * @author MEDES-IMPS
 */
public class CardEntityDataProvider extends ImogBeanDataProvider<CardEntityProxy> {

	private final AdminRequestFactory requestFactory;
	private boolean nonAffected = false;
	private boolean searchInReverse = false;
	private String property = null;

	/**
	 * For Relation Fields Provides instances of entity CardEntity with pagination
	 */
	public CardEntityDataProvider(AdminRequestFactory requestFactory) {
		this.requestFactory = requestFactory;
	}

	/**
	 * For Relation Fields Provides instances of entity CardEntity that have non affected values for a given property
	 * (RelationField with card==1) with pagination
	 * 
	 * @param pProperty the property for which non affected values are searched
	 */
	public CardEntityDataProvider(AdminRequestFactory requestFactory, String pProperty) {
		this.requestFactory = requestFactory;
		nonAffected = true;
		property = pProperty;
	}

	/**
	 * For Relation Fields Provides filtered instances of entity CardEntity that have non affected values for a given
	 * property (RelationField with card==1) with pagination
	 * 
	 * @param pProperty the property for which non affected values are searched
	 * @param searchInReverse true for 1:1 relations, if the property for which non affected values are searched shall
	 *            be looked in reverse relation
	 */
	public CardEntityDataProvider(AdminRequestFactory requestFactory, String pProperty, boolean searchInReverse) {
		this.requestFactory = requestFactory;
		nonAffected = true;
		property = pProperty;
		this.searchInReverse = searchInReverse;
	}

	/**
	 * Called by Relation Boxes
	 */
	@Override
	public Request<List<CardEntityProxy>> getList(int start, int numRows) {

		CardEntityRequest request = (CardEntityRequest) getContext();
		Request<List<CardEntityProxy>> result = null;

		if (isFiltered) {
			/* permanently filtered - hierarchical lists */
			if (filterCriteria != null) {

				if (searchCriterions != null) {
					/* permanent filter added to search criterion */
					if (nonAffected) {
						if (!searchInReverse)
							result = request.listNonAffectedCardEntity(start, numRows, "name", true, searchCriterions,
									property);
						else
							result = request.listNonAffectedCardEntityReverse(start, numRows, "name", true,
									searchCriterions, property);
					} else
						result = request.listCardEntity(start, numRows, "name", true, searchCriterions);
				} else {
					/* permanent filter only */
					if (nonAffected) {
						if (!searchInReverse)
							result = request.listNonAffectedCardEntity(start, numRows, "name", true, filterCriteria,
									property);
						else
							result = request.listNonAffectedCardEntityReverse(start, numRows, "name", true,
									filterCriteria, property);
					} else
						result = request.listCardEntity(start, numRows, "name", true, filterCriteria);
				}

			} else
				result = request.getCardEntityEmptyList();
		} else {
			if (searchCriterions != null) {

				if (nonAffected) {
					if (!searchInReverse)
						result = request.listNonAffectedCardEntity(start, numRows, "name", true, searchCriterions,
								property);
					else
						result = request.listNonAffectedCardEntityReverse(start, numRows, "name", true,
								searchCriterions, property);
				} else
					result = request.listCardEntity(start, numRows, "name", true, searchCriterions);
			} else {

				if (nonAffected) {
					if (!searchInReverse)
						result = request.listNonAffectedCardEntity(start, numRows, "name", true, property);
					else
						result = request.listNonAffectedCardEntityReverse(start, numRows, "name", true, property);
				} else
					result = request.listCardEntity(start, numRows, "name", true);
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
	public Request<List<CardEntityProxy>> getList(String property, int start, int numRows, boolean asc) {

		CardEntityRequest request = (CardEntityRequest) getContext();
		Request<List<CardEntityProxy>> result = null;
		if (getSearchCriterions() == null)
			result = request.listCardEntity(start, numRows, property, asc);
		else
			result = request.listCardEntity(start, numRows, property, asc, getSearchCriterions());

		return result;
	}

	@Override
	public Request<Long> getTotalRowCount() {
		CardEntityRequest request = (CardEntityRequest) getContext();

		if (isFiltered) {
			/* permanently filtered - hierarchical lists */
			if (filterCriteria != null) {

				if (searchCriterions != null) {
					/* permanent filter added to search criterion */
					if (nonAffected) {
						if (!searchInReverse)
							return request.countNonAffectedCardEntity(property, searchCriterions);
						else
							return request.countNonAffectedCardEntityReverse(property, searchCriterions);
					} else
						return request.countCardEntity(searchCriterions);
				} else {
					/* permanent filter only */
					if (nonAffected) {
						if (!searchInReverse)
							return request.countNonAffectedCardEntity(property, filterCriteria);
						else
							return request.countNonAffectedCardEntityReverse(property, filterCriteria);
					} else
						return request.countCardEntity(filterCriteria);
				}

			} else
				return request.countNonAffectedCardEntity("id");
		} else {

			if (searchCriterions != null) {
				if (nonAffected) {
					if (!searchInReverse)
						return request.countNonAffectedCardEntity(property, searchCriterions);
					else
						return request.countNonAffectedCardEntityReverse(property, searchCriterions);
				} else
					return request.countCardEntity(searchCriterions);
			} else {

				if (nonAffected) {
					if (!searchInReverse)
						return request.countNonAffectedCardEntity(property);
					else
						return request.countNonAffectedCardEntityReverse(property);
				} else
					return request.countCardEntity();
			}
		}
	}

	@Override
	public RequestContext getEntityContext() {
		return requestFactory.cardEntityRequest();
	}

	@Override
	public String fullTextSearch(String text) {

		boolean fullTextSearch = false;
		StringBuffer buffer = new StringBuffer(BaseNLS.constants().label_filtered() + " ");

		if (text == null || (text != null && text.equals(""))) {
			setSearchCriterions(null);
		} else {

			CardEntityRequest request = (CardEntityRequest) getContext();
			newRequest = false;

			ImogJunctionProxy disJunction = request.create(ImogDisjunctionProxy.class);
			List<ImogCriterionProxy> criterionList = new ArrayList<ImogCriterionProxy>();

			// Search field Name
			BasicCriteriaProxy nameCrit = request.create(BasicCriteriaProxy.class);
			nameCrit.setField("name");
			nameCrit.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
			nameCrit.setValue(text);
			buffer.append("(" + AdminNLS.constants().cardEntity_name() + ": " + text + ") " + SYMBOL_OR + " ");
			criterionList.add(nameCrit);

			disJunction.setCriterions(criterionList);
			fullTextSearch = true;

			// add FilterCriteria if exists
			if (isFiltered && filterCriteria != null) {
				ImogJunctionProxy conJunctionFilt = request.create(ImogConjunctionProxy.class);
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
