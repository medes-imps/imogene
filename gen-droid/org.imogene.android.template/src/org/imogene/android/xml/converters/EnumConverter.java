package org.imogene.android.xml.converters;

import org.imogene.android.util.FormatHelper;

public class EnumConverter extends AbstractSingleValueConverter {
	
	@Override
	public Object fromString(String str) {
		return FormatHelper.toInteger(str);
	}
	
}
