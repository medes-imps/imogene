/**
 * Medes-IMPS 2011
 *
 * $Id$
 */
package org.imogene.model.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Geo Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.imogene.model.core.ImogenePackage#getGeoType()
 * @model
 * @generated
 */
public enum GeoType implements Enumerator {
	/**
	 * The '<em><b>Nothing</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NOTHING_VALUE
	 * @generated
	 * @ordered
	 */
	NOTHING(-1, "Nothing", "Nothing"),

	/**
	 * The '<em><b>Gps Value</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GPS_VALUE_VALUE
	 * @generated
	 * @ordered
	 */
	GPS_VALUE(0, "GpsValue", "GpsValue"),

	/**
	 * The '<em><b>Network Type</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NETWORK_TYPE_VALUE
	 * @generated
	 * @ordered
	 */
	NETWORK_TYPE(1, "NetworkType", "NetworkType"),

	/**
	 * The '<em><b>Map Type</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MAP_TYPE_VALUE
	 * @generated
	 * @ordered
	 */
	MAP_TYPE(2, "MapType", "MapType"),

	/**
	 * The '<em><b>Best Type</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BEST_TYPE_VALUE
	 * @generated
	 * @ordered
	 */
	BEST_TYPE(3, "BestType", "BestType");

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Medes-IMPS 2011";

	/**
	 * The '<em><b>Nothing</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Nothing</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NOTHING
	 * @model name="Nothing"
	 * @generated
	 * @ordered
	 */
	public static final int NOTHING_VALUE = -1;

	/**
	 * The '<em><b>Gps Value</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Gps Value</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #GPS_VALUE
	 * @model name="GpsValue"
	 * @generated
	 * @ordered
	 */
	public static final int GPS_VALUE_VALUE = 0;

	/**
	 * The '<em><b>Network Type</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Network Type</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NETWORK_TYPE
	 * @model name="NetworkType"
	 * @generated
	 * @ordered
	 */
	public static final int NETWORK_TYPE_VALUE = 1;

	/**
	 * The '<em><b>Map Type</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Map Type</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MAP_TYPE
	 * @model name="MapType"
	 * @generated
	 * @ordered
	 */
	public static final int MAP_TYPE_VALUE = 2;

	/**
	 * The '<em><b>Best Type</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Best Type</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BEST_TYPE
	 * @model name="BestType"
	 * @generated
	 * @ordered
	 */
	public static final int BEST_TYPE_VALUE = 3;

	/**
	 * An array of all the '<em><b>Geo Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final GeoType[] VALUES_ARRAY =
		new GeoType[] {
			NOTHING,
			GPS_VALUE,
			NETWORK_TYPE,
			MAP_TYPE,
			BEST_TYPE,
		};

	/**
	 * A public read-only list of all the '<em><b>Geo Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<GeoType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Geo Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static GeoType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			GeoType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Geo Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static GeoType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			GeoType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Geo Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static GeoType get(int value) {
		switch (value) {
			case NOTHING_VALUE: return NOTHING;
			case GPS_VALUE_VALUE: return GPS_VALUE;
			case NETWORK_TYPE_VALUE: return NETWORK_TYPE;
			case MAP_TYPE_VALUE: return MAP_TYPE;
			case BEST_TYPE_VALUE: return BEST_TYPE;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private GeoType(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //GeoType
