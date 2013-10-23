package org.imogene.web.client.util;

/**
 * A basic request condition.
 */
public class FilterCriteria {

	/* the target field*/
	private String field;
	
	/* the target field*/
	private String fieldDisplayName;
	
	/* the operation */
	private String operation;
	
	/* the value to compare with*/
	private String value;
	
	/* the value to compare with*/
	private String valueDisplayName;
	
	
	public FilterCriteria() {
	}	

	public FilterCriteria(String field, String operation, String value) {
		this.field = field;
		this.operation = operation;
		this.value = value;
	}

	/**
	 * Get the target field
	 * @return the name of the target field
	 */
	public String getField() {
		return field;
	}

	/**
	 * Set the target field
	 * @param field name or path of the target field
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * The operation id.
	 * @return operatoin id
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * Set the operation id
	 * @param operation the opertaion id.
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * Get the value to compare with
	 * @return the value to compare with
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Set the value to compare with
	 * @param value the value to compare with
	 */
	public void setValue(String value) {
		this.value = value;
	}

	public String getFieldDisplayName() {
		return fieldDisplayName;
	}

	public void setFieldDisplayName(String fieldDisplayName) {
		this.fieldDisplayName = fieldDisplayName;
	}

	public String getValueDisplayName() {
		return valueDisplayName;
	}

	public void setValueDisplayName(String valueDisplayName) {
		this.valueDisplayName = valueDisplayName;
	}
	
	
	
	
}
