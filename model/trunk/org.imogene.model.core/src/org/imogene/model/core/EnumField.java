/**
 * Medes-IMPS 2011
 *
 * $Id$
 */
package org.imogene.model.core;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Enum Field</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.imogene.model.core.EnumField#getEnumValues <em>Enum Values</em>}</li>
 *   <li>{@link org.imogene.model.core.EnumField#isMultipleSelection <em>Multiple Selection</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.imogene.model.core.ImogenePackage#getEnumField()
 * @model
 * @generated
 */
public interface EnumField extends FieldEntity {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Medes-IMPS 2011";

	/**
	 * Returns the value of the '<em><b>Enum Values</b></em>' containment reference list.
	 * The list contents are of type {@link org.imogene.model.core.EnumValue}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enum Values</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enum Values</em>' containment reference list.
	 * @see org.imogene.model.core.ImogenePackage#getEnumField_EnumValues()
	 * @model containment="true" lower="2"
	 * @generated
	 */
	EList<EnumValue> getEnumValues();

	/**
	 * Returns the value of the '<em><b>Multiple Selection</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multiple Selection</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multiple Selection</em>' attribute.
	 * @see #setMultipleSelection(boolean)
	 * @see org.imogene.model.core.ImogenePackage#getEnumField_MultipleSelection()
	 * @model default="false"
	 * @generated
	 */
	boolean isMultipleSelection();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.EnumField#isMultipleSelection <em>Multiple Selection</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Multiple Selection</em>' attribute.
	 * @see #isMultipleSelection()
	 * @generated
	 */
	void setMultipleSelection(boolean value);

} // EnumField
