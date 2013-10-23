/**
 * Medes-IMPS 2011
 *
 * $Id$
 */
package org.imogene.model.core;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Text Field</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.imogene.model.core.TextField#isTranslatable <em>Translatable</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.imogene.model.core.ImogenePackage#getTextField()
 * @model
 * @generated
 */
public interface TextField extends StringField {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Medes-IMPS 2011";

	/**
	 * Returns the value of the '<em><b>Translatable</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indique si le champs est traduisible ou non
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Translatable</em>' attribute.
	 * @see #setTranslatable(boolean)
	 * @see org.imogene.model.core.ImogenePackage#getTextField_Translatable()
	 * @model default="false"
	 * @generated
	 */
	boolean isTranslatable();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.TextField#isTranslatable <em>Translatable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Translatable</em>' attribute.
	 * @see #isTranslatable()
	 * @generated
	 */
	void setTranslatable(boolean value);

} // TextField
