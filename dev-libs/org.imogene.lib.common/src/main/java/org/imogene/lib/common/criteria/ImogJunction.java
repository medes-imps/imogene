package org.imogene.lib.common.criteria;

import java.util.ArrayList;
import java.util.List;

public abstract class ImogJunction implements ImogCriterion {

	private static final long serialVersionUID = -1463366209991544812L;

	private List<ImogCriterion> criterions = new ArrayList<ImogCriterion>();

	public ImogJunction() {
	}

	/**
	 * Add a criterion
	 * @param criterion criterion to add
	 */
	public void add(ImogCriterion criterion) {
		criterions.add(criterion);
	}

	/**
	 * Get all criteria
	 * @return all criteria of this junction
	 */
	public List<ImogCriterion> getCriterions() {
		return criterions;
	}

	/**
	 * Set criteria
	 * @param criterions
	 */
	public void setCriterions(List<ImogCriterion> criterions) {
		this.criterions = criterions;
	}

	/**
	 * Get the type of junction (ex: Disjunction, Conjunction ...)
	 * @return
	 */
	public abstract String getType();
}
