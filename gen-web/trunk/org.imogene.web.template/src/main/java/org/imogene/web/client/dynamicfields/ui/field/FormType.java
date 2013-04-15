package org.imogene.web.client.dynamicfields.ui.field;

public class FormType {
	
	private String value;
	private String displayValue;
	
	
	public FormType(String value, String displayValue) {
		super();
		this.value = value;
		this.displayValue = displayValue;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public String getDisplayValue() {
		return displayValue;
	}


	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}
	
	
	
	

}
