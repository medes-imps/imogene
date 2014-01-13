package org.imogene.android.xml.converters;

import org.imogene.android.util.FormatHelper;

/**
 * Converts a long primitive or java.lang.Long wrapper to a String.
 * 
 * @author Medes-IMPS
 * 
 */
public class LongConverter extends AbstractSingleValueConverter {

	@Override
	public boolean canConvert(Class<?> clazz) {
		return clazz.equals(long.class) || clazz.equals(Long.class);
	}

	@Override
	public Object fromString(String str) {
		return FormatHelper.toLong(str);
	}

}
