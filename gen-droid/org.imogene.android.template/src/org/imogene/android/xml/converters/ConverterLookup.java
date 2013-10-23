package org.imogene.android.xml.converters;

/**
 * Responsible for looking up the correct Converter implementation for a
 * specific type.
 * 
 * @author Medes-IMPS
 * 
 */
public interface ConverterLookup {

	/**
	 * Lookup a converter of the specified type.
	 * 
	 * @param clazz The converter type to find.
	 * @return The converter found or {@code null} otherwise.
	 */
	public Converter lookupConverterOfType(Class<? extends Converter> clazz);

	/**
	 * Lookup a converter for a specific type. This type may be any Class,
	 * including primitive and array types. It may also be null, signifying the
	 * value to be converted is a null type.
	 * 
	 * @param clazz The type to convert.
	 * @return The converter found or {@code null} otherwise.
	 */
	public Converter lookupConverterForType(Class<?> clazz);

}
