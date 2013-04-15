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
 * A representation of the literals of the enumeration '<em><b>Notification Method</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.imogene.model.core.ImogenePackage#getNotificationMethod()
 * @model
 * @generated
 */
public enum NotificationMethod implements Enumerator {
	/**
	 * The '<em><b>SMS Method</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SMS_METHOD
	 * @generated
	 * @ordered
	 */
	SMS_METHOD_LITERAL(0, "SMSMethod", "SMSMethod"),

	/**
	 * The '<em><b>Mail Method</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MAIL_METHOD
	 * @generated
	 * @ordered
	 */
	MAIL_METHOD_LITERAL(1, "MailMethod", "MailMethod"),

	/**
	 * The '<em><b>Default Method</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DEFAULT_METHOD
	 * @generated
	 * @ordered
	 */
	DEFAULT_METHOD_LITERAL(2, "DefaultMethod", "DefaultMethod"),

	/**
	 * The '<em><b>Vocal Method</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #VOCAL_METHOD
	 * @generated
	 * @ordered
	 */
	VOCAL_METHOD_LITERAL(3, "VocalMethod", "VocalMethod"),

	/**
	 * The '<em><b>Web Service Method</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #WEB_SERVICE_METHOD
	 * @generated
	 * @ordered
	 */
	WEB_SERVICE_METHOD_LITERAL(4, "WebServiceMethod", "WebServiceMethod");

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Medes-IMPS 2011";

	/**
	 * The '<em><b>SMS Method</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SMS Method</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SMS_METHOD_LITERAL
	 * @model name="SMSMethod"
	 * @generated
	 * @ordered
	 */
	public static final int SMS_METHOD = 0;

	/**
	 * The '<em><b>Mail Method</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Mail Method</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MAIL_METHOD_LITERAL
	 * @model name="MailMethod"
	 * @generated
	 * @ordered
	 */
	public static final int MAIL_METHOD = 1;

	/**
	 * The '<em><b>Default Method</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Default Method</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DEFAULT_METHOD_LITERAL
	 * @model name="DefaultMethod"
	 * @generated
	 * @ordered
	 */
	public static final int DEFAULT_METHOD = 2;

	/**
	 * The '<em><b>Vocal Method</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Vocal Method</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #VOCAL_METHOD_LITERAL
	 * @model name="VocalMethod"
	 * @generated
	 * @ordered
	 */
	public static final int VOCAL_METHOD = 3;

	/**
	 * The '<em><b>Web Service Method</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Web Service Method</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #WEB_SERVICE_METHOD_LITERAL
	 * @model name="WebServiceMethod"
	 * @generated
	 * @ordered
	 */
	public static final int WEB_SERVICE_METHOD = 4;

	/**
	 * An array of all the '<em><b>Notification Method</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final NotificationMethod[] VALUES_ARRAY =
		new NotificationMethod[] {
			SMS_METHOD_LITERAL,
			MAIL_METHOD_LITERAL,
			DEFAULT_METHOD_LITERAL,
			VOCAL_METHOD_LITERAL,
			WEB_SERVICE_METHOD_LITERAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Notification Method</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<NotificationMethod> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Notification Method</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static NotificationMethod get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			NotificationMethod result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Notification Method</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static NotificationMethod getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			NotificationMethod result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Notification Method</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static NotificationMethod get(int value) {
		switch (value) {
			case SMS_METHOD: return SMS_METHOD_LITERAL;
			case MAIL_METHOD: return MAIL_METHOD_LITERAL;
			case DEFAULT_METHOD: return DEFAULT_METHOD_LITERAL;
			case VOCAL_METHOD: return VOCAL_METHOD_LITERAL;
			case WEB_SERVICE_METHOD: return WEB_SERVICE_METHOD_LITERAL;
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
	private NotificationMethod(int value, String name, String literal) {
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
	
} //NotificationMethod
