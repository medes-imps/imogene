package org.imogene.web.client.ui.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.imogene.lib.common.constants.CriteriaConstants;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.util.FilterCriteria;
import org.imogene.web.shared.proxy.ImogBeanProxy;
import org.imogene.web.shared.proxy.criteria.BasicCriteriaProxy;
import org.imogene.web.shared.proxy.criteria.ImogConjunctionProxy;
import org.imogene.web.shared.proxy.criteria.ImogCriterionProxy;
import org.imogene.web.shared.proxy.criteria.ImogDisjunctionProxy;
import org.imogene.web.shared.proxy.criteria.ImogJunctionProxy;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;

public abstract class ImogBeanDataProvider<T extends ImogBeanProxy> {

	protected final static String SYMBOL_OR = "||";

	/* for filters and search criterion */
	protected ImogJunctionProxy searchCriterions = null;
	/* for hierarchical lists */
	protected ImogJunctionProxy filterCriteria = null;
	protected boolean isFiltered = false;

	private RequestContext context;
	protected boolean newRequest = true;

	public ImogJunctionProxy getSearchCriterions() {
		return searchCriterions;
	}

	/**
	 * Sets criterions for which values have to be temporally searched
	 * @param criterions ImogJunctionProxy including the criterions for which the values have to be searched
	 */
	public void setSearchCriterions(ImogJunctionProxy criterions) {
		searchCriterions = criterions;
	}

	public ImogJunctionProxy getFilterCriteria() {
		return filterCriteria;
	}

	/**
	 * Sets criteria for which values have to be permanently filtered
	 * @param criteria ImogJunctionProxy including the criteria for which the values have to be filtered
	 */
	public void setFilterCriteria(ImogJunctionProxy criteria) {
		isFiltered = true;
		filterCriteria = criteria;
	}

	public void setIsFiltered(boolean isFiltered) {
		this.isFiltered = isFiltered;
	}

	/**
	 * Adds a Filter Criteria
	 * @param entityId the criteria entity id
	 * @param fieldName the criteria field name
	 */
	public void setFilterCriteria(String entityId, String fieldName) {

		RequestContext request = getContext();
		newRequest = false;

		ImogJunctionProxy conJunctionFilt = request.create(ImogConjunctionProxy.class);
		List<ImogCriterionProxy> criterionList = new ArrayList<ImogCriterionProxy>();
		BasicCriteriaProxy fitlerCrit = request.create(BasicCriteriaProxy.class);
		fitlerCrit.setField(fieldName);
		fitlerCrit.setOperation(CriteriaConstants.RELATIONFIELD_OPERATOR_EQUAL);
		fitlerCrit.setValue(entityId);
		criterionList.add(fitlerCrit);
		conJunctionFilt.setCriterions(criterionList);

		setFilterCriteria(conJunctionFilt);

	}

	/**
	 * Adds a list of Filter Criteria in a Disjunction
	 * @param criteria a map of filter criteria, key=entityId, value=fieldName
	 */
	public void addFilterCriteria(Map<String, String> criteria) {

		RequestContext request = getContext();
		newRequest = false;

		if (criteria != null && criteria.size() > 0) {
			ImogJunctionProxy conJunctionFilt = request.create(ImogDisjunctionProxy.class);
			List<ImogCriterionProxy> criterionList = new ArrayList<ImogCriterionProxy>();

			for (String key : criteria.keySet()) {
				BasicCriteriaProxy fitlerCrit = request.create(BasicCriteriaProxy.class);
				fitlerCrit.setField(criteria.get(key));
				fitlerCrit.setOperation(CriteriaConstants.RELATIONFIELD_OPERATOR_EQUAL);
				fitlerCrit.setValue(key);
				criterionList.add(fitlerCrit);
			}
			conJunctionFilt.setCriterions(criterionList);
			setFilterCriteria(conJunctionFilt);
		}
	}

	/**
	 * Gets if the data provider is permanently filtered
	 * @return true if the data provider is permanently filtered
	 */
	public boolean isFiltered() {
		return isFiltered;
	}

	/**
	 * @return
	 */
	protected RequestContext getContext() {
		if (newRequest)
			context = getEntityContext();
		else
			newRequest = true;
		return context;
	}

	/**
	 * Filters the table entries
	 * @param criteria
	 */
	public String filter(List<FilterCriteria> criteria) {

		boolean isFiltering = false;
		StringBuffer buffer = new StringBuffer(BaseNLS.constants().label_filtered() + " ");

		if (criteria == null || criteria.size() == 0) {
			setSearchCriterions(null);
		} else {

			RequestContext context = getContext();
			newRequest = false;
			ImogJunctionProxy main = context.create(ImogConjunctionProxy.class);

			List<ImogCriterionProxy> junctionCriterions = new ArrayList<ImogCriterionProxy>();
			for (FilterCriteria crit : criteria) {

				if (crit.getValue() != null && !crit.getValue().isEmpty()) {
					BasicCriteriaProxy critProxy = context.create(BasicCriteriaProxy.class);
					critProxy.setField(crit.getField());
					critProxy.setOperation(crit.getOperation());
					critProxy.setValue(crit.getValue());
					junctionCriterions.add(critProxy);

					appendFilterMessage(buffer, crit.getFieldDisplayName(), crit.getValueDisplayName());
				}
			}
			if (junctionCriterions.size() > 0) {
				main.setCriterions(junctionCriterions);

				// add FilterCriteria if exists
				if (isFiltered && filterCriteria != null) {
					ImogJunctionProxy conJunctionFilt = context.create(ImogConjunctionProxy.class);
					List<ImogCriterionProxy> criterionListFilt = new ArrayList<ImogCriterionProxy>();
					criterionListFilt.add(filterCriteria);
					criterionListFilt.add(main);
					conJunctionFilt.setCriterions(criterionListFilt);
					setSearchCriterions(conJunctionFilt);
				} else
					setSearchCriterions(main);
				isFiltering = true;
			} else {
				setSearchCriterions(null);
			}
		}

		if (isFiltering)
			return buffer.toString();
		else
			return null;
	}

	/**
	 * @param request the request context
	 * @param disJunction the searchCriterions with which the filter criteria have to be merged
	 * @return an ImogJunctionProxy that contains the merged filter and search criteria
	 */
	protected ImogJunctionProxy mergeFilterCriteriaAndFullTextSearchCriterion(RequestContext request, ImogJunctionProxy disJunction) {

		ImogJunctionProxy container = request.create(ImogConjunctionProxy.class);
		List<ImogCriterionProxy> containerCrits = new ArrayList<ImogCriterionProxy>();

		ImogJunctionProxy filter = filterCriteria;
		List<ImogCriterionProxy> filterCrits = filter.getCriterions();
		if (filterCrits != null && filterCrits.size() > 0) {

			// filter has to be created in the same context -> cloned
			ImogJunctionProxy filterConj = null;
			if (filter instanceof ImogConjunctionProxy)
				filterConj = request.create(ImogConjunctionProxy.class);
			else
				filterConj = request.create(ImogDisjunctionProxy.class);
			List<ImogCriterionProxy> filterConjList = new ArrayList<ImogCriterionProxy>();

			for (ImogCriterionProxy filterCrit : filterCrits) {
				if (filterCrit instanceof BasicCriteriaProxy) {
					BasicCriteriaProxy basic = (BasicCriteriaProxy) filterCrit;
					BasicCriteriaProxy basicCrit = request.create(BasicCriteriaProxy.class);
					basicCrit.setField(basic.getField());
					basicCrit.setOperation(basic.getOperation());
					basicCrit.setValue(basic.getValue());
					filterConjList.add(basicCrit);
				}
			}
			filterConj.setCriterions(filterConjList);
			// add filter criteria
			containerCrits.add(filterConj);
		}
		// add search criteria
		containerCrits.add(disJunction);

		container.setCriterions(containerCrits);
		return container;
	}

	/**
	 * @return
	 */
	public List<FilterCriteria> getDeletedEntityFilterCriteria(boolean isDeleted) {

		List<FilterCriteria> criteria = new ArrayList<FilterCriteria>();

		FilterCriteria deletedEntityCrit = new FilterCriteria();
		deletedEntityCrit.setField("deleted");
		deletedEntityCrit.setFieldDisplayName("Is deleted");
		if (isDeleted)
			deletedEntityCrit.setOperation(CriteriaConstants.OPERATOR_ISNOTNULL);
		else
			deletedEntityCrit.setOperation(CriteriaConstants.OPERATOR_ISNULL);
		deletedEntityCrit.setValue("false");
		deletedEntityCrit.setValueDisplayName(BaseNLS.constants().boolean_false());
		criteria.add(deletedEntityCrit);

		return criteria;
	}

	/**
	 * Appends the message that is displayed when the table is filtered or searched
	 * @param buffer the buffer containing the message
	 * @param fieldDisplayName the display name of the field
	 * @param valueDisplayName the display name of the field value
	 */
	protected void appendFilterMessage(StringBuffer buffer, String fieldDisplayName, String valueDisplayName) {
		buffer.append("(" + fieldDisplayName + ": " + valueDisplayName + ") ");
	}

	public abstract RequestContext getEntityContext();

	public abstract Request<List<T>> getList(int start, int numRows);

	public abstract Request<List<T>> getList(String property, int start, int numRows, boolean asc);

	public abstract Request<Long> getTotalRowCount();

	public abstract String fullTextSearch(String text);

}
