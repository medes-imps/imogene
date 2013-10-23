/**
 * Medes-IMPS 2011
 *
 * $Id$
 */
package org.imogene.model.core;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Notification Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Notification Information contains, for a given notification method, the reference to the field that will contain the value needed for notification (SMS number for instance, mail, ...). 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.imogene.model.core.NotificationInfo#getDataField <em>Data Field</em>}</li>
 *   <li>{@link org.imogene.model.core.NotificationInfo#getMethod <em>Method</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.imogene.model.core.ImogenePackage#getNotificationInfo()
 * @model
 * @generated
 */
public interface NotificationInfo extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Medes-IMPS 2011";

	/**
	 * Returns the value of the '<em><b>Data Field</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Field</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Field</em>' reference.
	 * @see #setDataField(FieldEntity)
	 * @see org.imogene.model.core.ImogenePackage#getNotificationInfo_DataField()
	 * @model required="true"
	 *        extendedMetaData="name='dataField' namespace=''"
	 * @generated
	 */
	FieldEntity getDataField();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.NotificationInfo#getDataField <em>Data Field</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data Field</em>' reference.
	 * @see #getDataField()
	 * @generated
	 */
	void setDataField(FieldEntity value);

	/**
	 * Returns the value of the '<em><b>Method</b></em>' attribute.
	 * The literals are from the enumeration {@link org.imogene.model.core.NotificationMethod}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Method</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Method</em>' attribute.
	 * @see org.imogene.model.core.NotificationMethod
	 * @see #setMethod(NotificationMethod)
	 * @see org.imogene.model.core.ImogenePackage#getNotificationInfo_Method()
	 * @model
	 * @generated
	 */
	NotificationMethod getMethod();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.NotificationInfo#getMethod <em>Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Method</em>' attribute.
	 * @see org.imogene.model.core.NotificationMethod
	 * @see #getMethod()
	 * @generated
	 */
	void setMethod(NotificationMethod value);

} // NotificationInfo
