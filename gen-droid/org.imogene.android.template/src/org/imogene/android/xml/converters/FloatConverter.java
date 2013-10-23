package org.imogene.android.xml.converters;

import org.imogene.android.util.FormatHelper;

/**
 * Converts a float primitive or java.lang.Float wrapper to a String.
 * 
 * @author Medes-IMPS
 * 
 */
public class FloatConverter extends AbstractSingleValueConverter {

	@Override
	public boolean canConvert(Class<?> clazz) {
		return clazz.equals(float.class) || clazz.equals(Float.class);
	}

	@Override
	public Object fromString(String str) {
		return FormatHelper.toFloat(str);
	}

}