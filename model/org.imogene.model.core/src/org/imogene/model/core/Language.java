/**
 * Medes-IMPS 2011
 *
 * $Id$
 */
package org.imogene.model.core;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Language</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.imogene.model.core.Language#getName <em>Name</em>}</li>
 *   <li>{@link org.imogene.model.core.Language#getDateFormat <em>Date Format</em>}</li>
 *   <li>{@link org.imogene.model.core.Language#getTimeFormat <em>Time Format</em>}</li>
 *   <li>{@link org.imogene.model.core.Language#getIntegerFormat <em>Integer Format</em>}</li>
 *   <li>{@link org.imogene.model.core.Language#getFloatFormat <em>Float Format</em>}</li>
 *   <li>{@link org.imogene.model.core.Language#getIsoCode <em>Iso Code</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.imogene.model.core.ImogenePackage#getLanguage()
 * @model
 * @generated
 */
public interface Language extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Medes-IMPS 2011";

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.imogene.model.core.ImogenePackage#getLanguage_Name()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.Language#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Date Format</b></em>' attribute.
	 * The default value is <code>"dd/MM/yyyy"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Date Format</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Date Format</em>' attribute.
	 * @see #setDateFormat(String)
	 * @see org.imogene.model.core.ImogenePackage#getLanguage_DateFormat()
	 * @model default="dd/MM/yyyy" required="true"
	 * @generated
	 */
	String getDateFormat();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.Language#getDateFormat <em>Date Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Date Format</em>' attribute.
	 * @see #getDateFormat()
	 * @generated
	 */
	void setDateFormat(String value);

	/**
	 * Returns the value of the '<em><b>Time Format</b></em>' attribute.
	 * The default value is <code>"HH:mm"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Time Format</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Time Format</em>' attribute.
	 * @see #setTimeFormat(String)
	 * @see org.imogene.model.core.ImogenePackage#getLanguage_TimeFormat()
	 * @model default="HH:mm" required="true"
	 * @generated
	 */
	String getTimeFormat();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.Language#getTimeFormat <em>Time Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time Format</em>' attribute.
	 * @see #getTimeFormat()
	 * @generated
	 */
	void setTimeFormat(String value);

	/**
	 * Returns the value of the '<em><b>Integer Format</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Integer Format</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Integer Format</em>' attribute.
	 * @see #setIntegerFormat(String)
	 * @see org.imogene.model.core.ImogenePackage#getLanguage_IntegerFormat()
	 * @model default="" required="true"
	 * @generated
	 */
	String getIntegerFormat();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.Language#getIntegerFormat <em>Integer Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Integer Format</em>' attribute.
	 * @see #getIntegerFormat()
	 * @generated
	 */
	void setIntegerFormat(String value);

	/**
	 * Returns the value of the '<em><b>Float Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Float Format</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Float Format</em>' attribute.
	 * @see #setFloatFormat(String)
	 * @see org.imogene.model.core.ImogenePackage#getLanguage_FloatFormat()
	 * @model required="true"
	 * @generated
	 */
	String getFloatFormat();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.Language#getFloatFormat <em>Float Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Float Format</em>' attribute.
	 * @see #getFloatFormat()
	 * @generated
	 */
	void setFloatFormat(String value);

	/**
	 * Returns the value of the '<em><b>Iso Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Iso Code</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Iso Code</em>' attribute.
	 * @see #setIsoCode(String)
	 * @see org.imogene.model.core.ImogenePackage#getLanguage_IsoCode()
	 * @model required="true"
	 * @generated
	 */
	String getIsoCode();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.Language#getIsoCode <em>Iso Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Iso Code</em>' attribute.
	 * @see #getIsoCode()
	 * @generated
	 */
	void setIsoCode(String value);

} // Language
