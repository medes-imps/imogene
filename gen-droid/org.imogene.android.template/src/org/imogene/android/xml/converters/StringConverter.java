package org.imogene.android.xml.converters;

/**
 * Converts a String to a String ;).
 * 
 * @author Medes-IMPS
 * 
 */
public class StringConverter extends AbstractSingleValueConverter {
	
	@Override
	public boolean canConvert(Class<?> clazz) {
		return clazz.equals(String.class);
	}

	@Override
	public Object fromString(String str) {
		return str;
	}

}
