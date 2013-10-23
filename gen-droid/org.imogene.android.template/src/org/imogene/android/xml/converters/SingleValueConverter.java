package org.imogene.android.xml.converters;

/**
 * SingleValueConverter implementations are marshallable to/from a single value
 * String representation.
 * <p>
 * {@link AbstractSingleValueConverter} provides a starting point
 * for objects that can store all information in a single value String.
 * 
 * @author Medes-IMPS
 * 
 */
public interface SingleValueConverter extends Converter {

	/**
	 * Unmarshals an {@link Object} from its single value representation.
	 * 
	 * @param str the string with the single value of the object.
	 * @return the object.
	 */
	public Object fromString(String str);

	/**
	 * Marshals an {@link Object} into a single value representation.
	 * 
	 * @param obj the object to be converted.
	 * @return a String with the single value of the object.
	 */
	public String toString(Object obj);

}
