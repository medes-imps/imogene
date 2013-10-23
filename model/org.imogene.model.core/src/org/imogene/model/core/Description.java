/**
 * Medes-IMPS 2011
 *
 * $Id$
 */
package org.imogene.model.core;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Description</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.imogene.model.core.Description#getDisplay <em>Display</em>}</li>
 *   <li>{@link org.imogene.model.core.Description#getHelp <em>Help</em>}</li>
 *   <li>{@link org.imogene.model.core.Description#getLocale <em>Locale</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.imogene.model.core.ImogenePackage#getDescription()
 * @model
 * @generated
 */
public interface Description extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Medes-IMPS 2011";

	/**
	 * Returns the value of the '<em><b>Display</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Display</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Display</em>' attribute.
	 * @see #setDisplay(String)
	 * @see org.imogene.model.core.ImogenePackage#getDescription_Display()
	 * @model required="true"
	 * @generated
	 */
	String getDisplay();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.Description#getDisplay <em>Display</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display</em>' attribute.
	 * @see #getDisplay()
	 * @generated
	 */
	void setDisplay(String value);

	/**
	 * Returns the value of the '<em><b>Help</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Help</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Help</em>' attribute.
	 * @see #setHelp(String)
	 * @see org.imogene.model.core.ImogenePackage#getDescription_Help()
	 * @model
	 * @generated
	 */
	String getHelp();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.Description#getHelp <em>Help</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Help</em>' attribute.
	 * @see #getHelp()
	 * @generated
	 */
	void setHelp(String value);

	/**
	 * Returns the value of the '<em><b>Locale</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Locale</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Locale</em>' attribute.
	 * @see #setLocale(String)
	 * @see org.imogene.model.core.ImogenePackage#getDescription_Locale()
	 * @model
	 * @generated
	 */
	String getLocale();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.Description#getLocale <em>Locale</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Locale</em>' attribute.
	 * @see #getLocale()
	 * @generated
	 */
	void setLocale(String value);

} // Description
