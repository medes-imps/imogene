package org.imogene.admin.client.dataprovider;

import java.util.ArrayList;
import java.util.List;

import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.admin.shared.AdminRequestFactory;
import org.imogene.admin.shared.request.EntityProfileRequest;
import org.imogene.lib.common.constants.CriteriaConstants;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.table.ImogBeanDataProvider;
import org.imogene.web.shared.proxy.EntityProfileProxy;
import org.imogene.web.shared.proxy.criteria.BasicCriteriaProxy;
import org.imogene.web.shared.proxy.criteria.ImogCriterionProxy;
import org.imogene.web.shared.proxy.criteria.ImogDisjunctionProxy;
import org.imogene.web.shared.proxy.criteria.ImogJunctionProxy;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;

/**
 * Data provider to feed the EntityProfile entry Table and Selection List
 * 
 * @author MEDES-IMPS
 */
public class EntityProfileDataProvider extends ImogBeanDataProvider<EntityProfileProxy> {

	private final AdminRequestFactory requestFactory;
	private boolean nonAffected = false;
	private boolean searchInReverse = false;
	private String property = null;

	/**
	 * For Relation Fields Provides instances of entity EntityProfile with pagination
	 */
	public EntityProfileDataProvider(AdminRequestFactory requestFactory) {
		this.requestFactory = requestFactory;
	}

	/**
	 * For Relation Fields Provides instances of entity EntityProfile that have non affected values for a given property
	 * (RelationField with card==1) with pagination
	 * 
	 * @param pProperty the property for which non affected values are searched
	 */
	public EntityProfileDataProvider(AdminRequestFactory requestFactory, String pProperty) {
		this.requestFactory = requestFactory;
		nonAffected = true;
		property = pProperty;
	}

	/**
	 * For Relation Fields Provides filtered instances of entity EntityProfile that have non affected values for a given
	 * property (RelationField with card==1) with pagination
	 * 
	 * @param pProperty the property for which non affected values are searched
	 * @param searchInReverse true for 1:1 relations, if the property for which non affected values are searched shall
	 *            be looked in reverse relation
	 */
	public EntityProfileDataProvider(AdminRequestFactory requestFactory, String pProperty, boolean searchInReverse) {
		this.requestFactory = requestFactory;
		nonAffected = true;
		property = pProperty;
		this.searchInReverse = searchInReverse;
	}

	/**
	 * Called by Relation Boxes
	 */
	@Override
	public Request<List<EntityProfileProxy>> getList(int start, int numRows) {

		EntityProfileRequest request = (EntityProfileRequest) getContext();
		Request<List<EntityProfileProxy>> result = null;

		if (isFiltered) {
			/* permanently filtered - hierarchical lists */
			if (filterCriteria != null) {

				if (searchCriterions != null) {
					/* permanent filter added to search criterion */
					if (nonAffected) {
						if (!searchInReverse)
							result = request.listNonAffectedEntityProfile(start, numRows, "modified", false,
									searchCriterions, property);
						else
							result = request.listNonAffectedEntityProfileReverse(start, numRows, "modified", false,
									searchCriterions, property);
					} else
						result = request.listEntityProfile(start, numRows, "modified", false, searchCriterions);
				} else {
					/* permanent filter only */
					if (nonAffected) {
						if (!searchInReverse)
							result = request.listNonAffectedEntityProfile(start, numRows, "modified", false,
									filterCriteria, property);
						else
							result = request.listNonAffectedEntityProfileReverse(start, numRows, "modified", false,
									filterCriteria, property);
					} else
						result = request.listEntityProfile(start, numRows, "modified", false, filterCriteria);
				}

			} else
				result = request.getEntityProfileEmptyList();
		} else {
			if (searchCriterions != null) {

				if (nonAffected) {
					if (!searchInReverse)
						result = request.listNonAffectedEntityProfile(start, numRows, "modified", false,
								searchCriterions, property);
					else
						result = request.listNonAffectedEntityProfileReverse(start, numRows, "modified", false,
								searchCriterions, property);
				} else
					result = request.listEntityProfile(start, numRows, "modified", false, searchCriterions);
			} else {

				if (nonAffected) {
					if (!searchInReverse)
						result = request.listNonAffectedEntityProfile(start, numRows, "modified", false, property);
					else
						result = request.listNonAffectedEntityProfileReverse(start, numRows, "modified", false,
								property);
				} else
					result = request.listEntityProfile(start, numRows, "modified", false);
			}
		}

		if (isFiltered) {
			result.with("profile");
			result.with("entity");
		}

		else {
			result.with("profile");
			result.with("entity");
		}

		return result;
	}

	/**
	 * Called by Dynatable
	 */
	@Override
	public Request<List<EntityProfileProxy>> getList(String property, int start, int numRows, boolean asc) {

		EntityProfileRequest request = (EntityProfileRequest) getContext();
		Request<List<EntityProfileProxy>> result = null;

		if (isFiltered) {
			/* permanently filtered - hierarchical lists */
			if (filterCriteria != null) {

				if (searchCriterions != null)
					/* permanent filter added to search criterion */
					result = request.listEntityProfile(start, numRows, property, asc, searchCriterions);
				else
					/* permanent filter only */
					result = request.listEntityProfile(start, numRows, property, asc, filterCriteria);

			} else
				result = request.getEntityProfileEmptyList();
		} else {
			if (searchCriterions != null)
				result = request.listEntityProfile(start, numRows, property, asc, searchCriterions);
			else
				result = request.listEntityProfile(start, numRows, property, asc);
		}

		result.with("profile");
		result.with("entity");

		return result;
	}

	@Override
	public Request<Long> getTotalRowCount() {
		EntityProfileRequest request = (EntityProfileRequest) getContext();

		if (isFiltered) {
			/* permanently filtered - hierarchical lists */
			if (filterCriteria != null) {

				if (searchCriterions != null) {
					/* permanent filter added to search criterion */
					if (nonAffected) {
						if (!searchInReverse)
							return request.countNonAffectedEntityProfile(property, searchCriterions);
						else
							return request.countNonAffectedEntityProfileReverse(property, searchCriterions);
					} else
						return request.countEntityProfile(searchCriterions);
				} else {
					/* permanent filter only */
					if (nonAffected) {
						if (!searchInReverse)
							return request.countNonAffectedEntityProfile(property, filterCriteria);
						else
							return request.countNonAffectedEntityProfileReverse(property, filterCriteria);
					} else
						return request.countEntityProfile(filterCriteria);
				}

			} else
				return request.countNonAffectedEntityProfile("id");
		} else {

			if (searchCriterions != null) {
				if (nonAffected) {
					if (!searchInReverse)
						return request.countNonAffectedEntityProfile(property, searchCriterions);
					else
						return request.countNonAffectedEntityProfileReverse(property, searchCriterions);
				} else
					return request.countEntityProfile(searchCriterions);
			} else {

				if (nonAffected) {
					if (!searchInReverse)
						return request.countNonAffectedEntityProfile(property);
					else
						return request.countNonAffectedEntityProfileReverse(property);
				} else
					return request.countEntityProfile();
			}
		}
	}

	@Override
	public RequestContext getEntityContext() {
		return requestFactory.entityProfileRequest();
	}

	@Override
	public String fullTextSearch(String text) {

		boolean fullTextSearch = false;
		StringBuffer buffer = new StringBuffer(BaseNLS.constants().label_filtered() + " ");

		if (text == null || (text != null && text.equals(""))) {
			setSearchCriterions(null);
		} else {
			EntityProfileRequest request = (EntityProfileRequest) getContext();
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
			BasicCriteriaProxy entity_nameCrit = request.create(BasicCriteriaProxy.class);
			entity_nameCrit.setField("entity.name");
			entity_nameCrit.setOperation(CriteriaConstants.STRING_OPERATOR_CONTAINS);
			entity_nameCrit.setValue(text);
			buffer.append("(" + AdminNLS.constants().cardEntity_field_name() + ": " + text + ") " + SYMBOL_OR
					+ " ");
			criterionList.add(entity_nameCrit);

			// Search field DirectAccess
			if (text.toLowerCase().equals("true") || text.toLowerCase().equals("false")) {
				BasicCriteriaProxy directAccessCrit = request.create(BasicCriteriaProxy.class);
				directAccessCrit.setField("directAccess");
				directAccessCrit.setOperation(CriteriaConstants.BOOLEAN_OPERATOR_EQUAL);
				directAccessCrit.setValue(text);
				buffer.append("(" + AdminNLS.constants().entityProfile_field_directAccess() + ": " + text + ") "
						+ SYMBOL_OR + " ");
				criterionList.add(directAccessCrit);
			}

			// Search field Create
			if (text.toLowerCase().equals("true") || text.toLowerCase().equals("false")) {
				BasicCriteriaProxy createCrit = request.create(BasicCriteriaProxy.class);
				createCrit.setField("create");
				createCrit.setOperation(CriteriaConstants.BOOLEAN_OPERATOR_EQUAL);
				createCrit.setValue(text);
				buffer.append("(" + AdminNLS.constants().entityProfile_field_create() + ": " + text + ") " + SYMBOL_OR
						+ " ");
				criterionList.add(createCrit);
			}

			// Search field Delete
			if (text.toLowerCase().equals("true") || text.toLowerCase().equals("false")) {
				BasicCriteriaProxy deleteCrit = request.create(BasicCriteriaProxy.class);
				deleteCrit.setField("delete");
				deleteCrit.setOperation(CriteriaConstants.BOOLEAN_OPERATOR_EQUAL);
				deleteCrit.setValue(text);
				buffer.append("(" + AdminNLS.constants().entityProfile_field_delete() + ": " + text + ") " + SYMBOL_OR
						+ " ");
				criterionList.add(deleteCrit);
			}

			// Search field Export
			if (text.toLowerCase().equals("true") || text.toLowerCase().equals("false")) {
				BasicCriteriaProxy exportCrit = request.create(BasicCriteriaProxy.class);
				exportCrit.setField("export");
				exportCrit.setOperation(CriteriaConstants.BOOLEAN_OPERATOR_EQUAL);
				exportCrit.setValue(text);
				buffer.append("(" + AdminNLS.constants().entityProfile_field_export() + ": " + text + ") " + SYMBOL_OR
						+ " ");
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
