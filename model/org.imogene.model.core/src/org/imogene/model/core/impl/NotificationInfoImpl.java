/**
 * Medes-IMPS 2011
 *
 * $Id$
 */
package org.imogene.model.core.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.imogene.model.core.FieldEntity;
import org.imogene.model.core.ImogenePackage;
import org.imogene.model.core.NotificationInfo;
import org.imogene.model.core.NotificationMethod;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Notification Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.imogene.model.core.impl.NotificationInfoImpl#getDataField <em>Data Field</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.NotificationInfoImpl#getMethod <em>Method</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NotificationInfoImpl extends EObjectImpl implements NotificationInfo {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Medes-IMPS 2011";

	/**
	 * The cached value of the '{@link #getDataField() <em>Data Field</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataField()
	 * @generated
	 * @ordered
	 */
	protected FieldEntity dataField;

	/**
	 * The default value of the '{@link #getMethod() <em>Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethod()
	 * @generated
	 * @ordered
	 */
	protected static final NotificationMethod METHOD_EDEFAULT = NotificationMethod.SMS_METHOD_LITERAL;

	/**
	 * The cached value of the '{@link #getMethod() <em>Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethod()
	 * @generated
	 * @ordered
	 */
	protected NotificationMethod method = METHOD_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NotificationInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImogenePackage.Literals.NOTIFICATION_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FieldEntity getDataField() {
		if (dataField != null && dataField.eIsProxy()) {
			InternalEObject oldDataField = (InternalEObject)dataField;
			dataField = (FieldEntity)eResolveProxy(oldDataField);
			if (dataField != oldDataField) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ImogenePackage.NOTIFICATION_INFO__DATA_FIELD, oldDataField, dataField));
			}
		}
		return dataField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FieldEntity basicGetDataField() {
		return dataField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDataField(FieldEntity newDataField) {
		FieldEntity oldDataField = dataField;
		dataField = newDataField;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.NOTIFICATION_INFO__DATA_FIELD, oldDataField, dataField));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationMethod getMethod() {
		return method;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMethod(NotificationMethod newMethod) {
		NotificationMethod oldMethod = method;
		method = newMethod == null ? METHOD_EDEFAULT : newMethod;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.NOTIFICATION_INFO__METHOD, oldMethod, method));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ImogenePackage.NOTIFICATION_INFO__DATA_FIELD:
				if (resolve) return getDataField();
				return basicGetDataField();
			case ImogenePackage.NOTIFICATION_INFO__METHOD:
				return getMethod();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ImogenePackage.NOTIFICATION_INFO__DATA_FIELD:
				setDataField((FieldEntity)newValue);
				return;
			case ImogenePackage.NOTIFICATION_INFO__METHOD:
				setMethod((NotificationMethod)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ImogenePackage.NOTIFICATION_INFO__DATA_FIELD:
				setDataField((FieldEntity)null);
				return;
			case ImogenePackage.NOTIFICATION_INFO__METHOD:
				setMethod(METHOD_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ImogenePackage.NOTIFICATION_INFO__DATA_FIELD:
				return dataField != null;
			case ImogenePackage.NOTIFICATION_INFO__METHOD:
				return method != METHOD_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (method: ");
		result.append(method);
		result.append(')');
		return result.toString();
	}

} //NotificationInfoImpl
