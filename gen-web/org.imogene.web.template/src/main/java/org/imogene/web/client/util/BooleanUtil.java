package org.imogene.web.client.util;

import org.imogene.web.client.i18n.BaseNLS;

public class BooleanUtil {

	public static String getBooleanDisplayValue(Boolean bool) {
		if (bool!=null)
		{
			if(bool.booleanValue())
				return BaseNLS.constants().boolean_true();
			else
				return BaseNLS.constants().boolean_false();			
		}
		else
			//return BaseNLS.constants().boolean_unknown();		
			return "";
	}
}
