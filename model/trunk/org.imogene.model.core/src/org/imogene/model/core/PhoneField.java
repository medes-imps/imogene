/**
 * Medes-IMPS 2011
 *
 * $Id$
 */
package org.imogene.model.core;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Phone Field</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.imogene.model.core.PhoneField#getPhoneType <em>Phone Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.imogene.model.core.ImogenePackage#getPhoneField()
 * @model
 * @generated
 */
public interface PhoneField extends TextField {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Medes-IMPS 2011";

	/**
	 * Returns the value of the '<em><b>Phone Type</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * The literals are from the enumeration {@link org.imogene.model.core.PhoneType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Phone Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Phone Type</em>' attribute.
	 * @see org.imogene.model.core.PhoneType
	 * @see #setPhoneType(PhoneType)
	 * @see org.imogene.model.core.ImogenePackage#getPhoneField_PhoneType()
	 * @model default="" required="true"
	 * @generated
	 */
	PhoneType getPhoneType();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.PhoneField#getPhoneType <em>Phone Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Phone Type</em>' attribute.
	 * @see org.imogene.model.core.PhoneType
	 * @see #getPhoneType()
	 * @generated
	 */
	void setPhoneType(PhoneType value);

} // PhoneField
