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
 * A representation of the literals of the enumeration '<em><b>Phone Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.imogene.model.core.ImogenePackage#getPhoneType()
 * @model
 * @generated
 */
public enum PhoneType implements Enumerator {
	/**
	 * The '<em><b>Phone Number</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PHONE_NUMBER
	 * @generated
	 * @ordered
	 */
	PHONE_NUMBER_LITERAL(0, "PhoneNumber", "PhoneNumber"),

	/**
	 * The '<em><b>Fax Number</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FAX_NUMBER
	 * @generated
	 * @ordered
	 */
	FAX_NUMBER_LITERAL(1, "FaxNumber", "FaxNumber"),

	/**
	 * The '<em><b>Mobile Number</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MOBILE_NUMBER
	 * @generated
	 * @ordered
	 */
	MOBILE_NUMBER_LITERAL(2, "MobileNumber", "MobileNumber"),

	/**
	 * The '<em><b>Fixe Number</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FIXE_NUMBER
	 * @generated
	 * @ordered
	 */
	FIXE_NUMBER_LITERAL(3, "FixeNumber", "FixeNumber");

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Medes-IMPS 2011";

	/**
	 * The '<em><b>Phone Number</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Phone Number</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PHONE_NUMBER_LITERAL
	 * @model name="PhoneNumber"
	 * @generated
	 * @ordered
	 */
	public static final int PHONE_NUMBER = 0;

	/**
	 * The '<em><b>Fax Number</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Fax Number</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #FAX_NUMBER_LITERAL
	 * @model name="FaxNumber"
	 * @generated
	 * @ordered
	 */
	public static final int FAX_NUMBER = 1;

	/**
	 * The '<em><b>Mobile Number</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Mobile Number</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MOBILE_NUMBER_LITERAL
	 * @model name="MobileNumber"
	 * @generated
	 * @ordered
	 */
	public static final int MOBILE_NUMBER = 2;

	/**
	 * The '<em><b>Fixe Number</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Fixe Number</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #FIXE_NUMBER_LITERAL
	 * @model name="FixeNumber"
	 * @generated
	 * @ordered
	 */
	public static final int FIXE_NUMBER = 3;

	/**
	 * An array of all the '<em><b>Phone Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final PhoneType[] VALUES_ARRAY =
		new PhoneType[] {
			PHONE_NUMBER_LITERAL,
			FAX_NUMBER_LITERAL,
			MOBILE_NUMBER_LITERAL,
			FIXE_NUMBER_LITERAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Phone Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<PhoneType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Phone Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PhoneType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PhoneType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Phone Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PhoneType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PhoneType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Phone Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PhoneType get(int value) {
		switch (value) {
			case PHONE_NUMBER: return PHONE_NUMBER_LITERAL;
			case FAX_NUMBER: return FAX_NUMBER_LITERAL;
			case MOBILE_NUMBER: return MOBILE_NUMBER_LITERAL;
			case FIXE_NUMBER: return FIXE_NUMBER_LITERAL;
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
	private PhoneType(int value, String name, String literal) {
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
	
} //PhoneType
