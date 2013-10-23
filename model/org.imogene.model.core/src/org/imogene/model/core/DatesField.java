/**
 * Medes-IMPS 2011
 *
 * $Id$
 */
package org.imogene.model.core;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dates Field</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.imogene.model.core.DatesField#getMin <em>Min</em>}</li>
 *   <li>{@link org.imogene.model.core.DatesField#getMax <em>Max</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.imogene.model.core.ImogenePackage#getDatesField()
 * @model abstract="true"
 * @generated
 */
public interface DatesField extends FieldEntity {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Medes-IMPS 2011";

	/**
	 * Returns the value of the '<em><b>Min</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Min</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Min</em>' attribute.
	 * @see #setMin(String)
	 * @see org.imogene.model.core.ImogenePackage#getDatesField_Min()
	 * @model
	 * @generated
	 */
	String getMin();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.DatesField#getMin <em>Min</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min</em>' attribute.
	 * @see #getMin()
	 * @generated
	 */
	void setMin(String value);

	/**
	 * Returns the value of the '<em><b>Max</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max</em>' attribute.
	 * @see #setMax(String)
	 * @see org.imogene.model.core.ImogenePackage#getDatesField_Max()
	 * @model
	 * @generated
	 */
	String getMax();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.DatesField#getMax <em>Max</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max</em>' attribute.
	 * @see #getMax()
	 * @generated
	 */
	void setMax(String value);

} // DatesField
