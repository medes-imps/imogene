package org.imogene.android.xml.mapper;

import android.net.Uri;

/**
 * Allows to navigate between objects and their serialized representation.
 * 
 * @author Medes-IMPS
 * 
 */
public interface Mapper {

	/**
	 * How a serialized class representation should be mapped back to a real
	 * class.
	 * 
	 * @param elementName
	 * @return
	 */
	public Class<?> realClass(String elementName);

	/**
	 * How a class name should be represented in its serialized form.
	 * 
	 * @param type
	 * @return
	 */
	public String serializedClass(Class<?> type);

	/**
	 * How a serialized class representation should be mapped back to a real
	 * uri.
	 * 
	 * @param elementName
	 * @return
	 */
	public Uri realUri(String elementName);

	/**
	 * How an Uri name should be represented in its serialized form.
	 * 
	 * @param uri
	 * @return
	 */
	public String serializeUri(Uri uri);

	/**
	 * How a serialized member representation should be mapped back to a real
	 * member.
	 * 
	 * @param type
	 * @param serialized
	 * @return
	 */
	public String realMember(Class<?> type, String serialized);

	/**
	 * How a class member should be represented in its serialized form.
	 * 
	 * @param type
	 * @param memberName
	 * @return
	 */
	public String serializedMember(Class<?> type, String memberName);

	/**
	 * Determine whether a specific member should be serialized.
	 * 
	 * @param definedIn
	 * @param fieldName
	 * @return
	 */
	public boolean shouldSerializeMember(Class<?> definedIn, String fieldName);

	/**
	 * Whether this type is a simple immutable value (int, boolean, String, URL,
	 * etc. Immutable types will be repeatedly written in the serialized stream,
	 * instead of using object references.
	 * 
	 * @param type
	 * @return
	 */
	public boolean isImmutableValueType(Class<?> type);

	/**
	 * Finds a Mapper convenient for the given Class.
	 * 
	 * @param type The class to find a mapper for.
	 * @return The mapper found or {@code null}.
	 */
	public Mapper lookupMapperOfType(Class<?> type);

}
