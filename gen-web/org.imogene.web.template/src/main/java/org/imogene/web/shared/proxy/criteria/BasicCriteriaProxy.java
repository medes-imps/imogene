package org.imogene.web.shared.proxy.criteria;

import org.imogene.lib.common.criteria.BasicCriteria;

import com.google.web.bindery.requestfactory.shared.ProxyFor;

/**
 * A basic request condition.
 */
@ProxyFor(value=BasicCriteria.class)
public interface BasicCriteriaProxy extends ImogCriterionProxy {


	/**
	 * Get the target field
	 * @return the name of the target field
	 */
	public String getField();

	/**
	 * Set the target field
	 * @param field name or path of the target field
	 */
	public void setField(String field);

	/**
	 * The operation id.
	 * @return operation id
	 */
	public String getOperation();

	/**
	 * Set the operation id
	 * @param operation the operation id.
	 */
	public void setOperation(String operation);

	/**
	 * Get the value to compare with
	 * @return the value to compare with
	 */
	public String getValue();

	/**
	 * Set the value to compare with
	 * @param value the value to compare with
	 */
	public void setValue(String value);
	
	
}
