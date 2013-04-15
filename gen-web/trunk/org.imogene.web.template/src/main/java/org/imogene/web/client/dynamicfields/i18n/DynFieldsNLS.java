package org.imogene.web.client.dynamicfields.i18n;
import com.google.gwt.core.client.GWT;


public class DynFieldsNLS{

	private static DynFieldsConstants _const = (DynFieldsConstants)GWT.create(DynFieldsConstants.class);

	public static DynFieldsConstants constants(){
		return _const;
	}
	
}
