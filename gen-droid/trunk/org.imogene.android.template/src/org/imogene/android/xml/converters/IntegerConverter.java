package org.imogene.android.xml.converters;

import org.imogene.android.util.FormatHelper;

/**
 * Converts an int primitive or java.lang.Integer wrapper to a String.
 * 
 * @author Medes-IMPS
 * 
 */
public class IntegerConverter extends AbstractSingleValueConverter {

	@Override
	public boolean canConvert(Class<?> clazz) {
		return clazz.equals(int.class) || clazz.equals(Integer.class);
	}

	@Override
	public Object fromString(String str) {
		return FormatHelper.toInteger(str);
	}

}
