/**
 * Medes-IMPS 2011
 *
 * $Id$
 */
package org.imogene.model.core;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Validation Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Validation rule for field input validation when form saved (not for basic validations like "required")
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.imogene.model.core.ValidationRule#getValidationRegularExpression <em>Validation Regular Expression</em>}</li>
 *   <li>{@link org.imogene.model.core.ValidationRule#getDescriptions <em>Descriptions</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.imogene.model.core.ImogenePackage#getValidationRule()
 * @model
 * @generated
 */
public interface ValidationRule extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Medes-IMPS 2011";

	/**
	 * Returns the value of the '<em><b>Validation Regular Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Validation Regular Expression</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Validation Regular Expression</em>' attribute.
	 * @see #setValidationRegularExpression(String)
	 * @see org.imogene.model.core.ImogenePackage#getValidationRule_ValidationRegularExpression()
	 * @model
	 * @generated
	 */
	String getValidationRegularExpression();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.ValidationRule#getValidationRegularExpression <em>Validation Regular Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Validation Regular Expression</em>' attribute.
	 * @see #getValidationRegularExpression()
	 * @generated
	 */
	void setValidationRegularExpression(String value);

	/**
	 * Returns the value of the '<em><b>Descriptions</b></em>' containment reference list.
	 * The list contents are of type {@link org.imogene.model.core.Description}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Descriptions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Descriptions</em>' containment reference list.
	 * @see org.imogene.model.core.ImogenePackage#getValidationRule_Descriptions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Description> getDescriptions();

} // ValidationRule
