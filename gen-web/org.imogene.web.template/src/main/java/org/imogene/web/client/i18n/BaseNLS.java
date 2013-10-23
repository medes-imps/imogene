package org.imogene.web.client.i18n;
import com.google.gwt.core.client.GWT;


public class BaseNLS{

	private static ImogConstants _const = (ImogConstants)GWT.create(ImogConstants.class);

	private static ImogMessages _messages = (ImogMessages)GWT.create(ImogMessages.class);
	
	private static ImogValidationRules _valid = (ImogValidationRules)GWT.create(ImogValidationRules.class);


	
	public static ImogConstants constants(){
		return _const;
	}
	
	public static ImogMessages messages(){
		return _messages;
	}	
	
	public static ImogValidationRules validationRules(){
		return _valid;
	}
}
