/**
 * Medes-IMPS 2011
 *
 * $Id$
 */
package org.imogene.model.core.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.imogene.model.core.ImogenePackage;
import org.imogene.model.core.PhoneField;
import org.imogene.model.core.PhoneType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Phone Field</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.imogene.model.core.impl.PhoneFieldImpl#getPhoneType <em>Phone Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PhoneFieldImpl extends TextFieldImpl implements PhoneField {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Medes-IMPS 2011";

	/**
	 * The default value of the '{@link #getPhoneType() <em>Phone Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPhoneType()
	 * @generated
	 * @ordered
	 */
	protected static final PhoneType PHONE_TYPE_EDEFAULT = PhoneType.PHONE_NUMBER_LITERAL;

	/**
	 * The cached value of the '{@link #getPhoneType() <em>Phone Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPhoneType()
	 * @generated
	 * @ordered
	 */
	protected PhoneType phoneType = PHONE_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PhoneFieldImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImogenePackage.Literals.PHONE_FIELD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PhoneType getPhoneType() {
		return phoneType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPhoneType(PhoneType newPhoneType) {
		PhoneType oldPhoneType = phoneType;
		phoneType = newPhoneType == null ? PHONE_TYPE_EDEFAULT : newPhoneType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.PHONE_FIELD__PHONE_TYPE, oldPhoneType, phoneType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ImogenePackage.PHONE_FIELD__PHONE_TYPE:
				return getPhoneType();
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
			case ImogenePackage.PHONE_FIELD__PHONE_TYPE:
				setPhoneType((PhoneType)newValue);
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
			case ImogenePackage.PHONE_FIELD__PHONE_TYPE:
				setPhoneType(PHONE_TYPE_EDEFAULT);
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
			case ImogenePackage.PHONE_FIELD__PHONE_TYPE:
				return phoneType != PHONE_TYPE_EDEFAULT;
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
		result.append(" (phoneType: ");
		result.append(phoneType);
		result.append(')');
		return result.toString();
	}

} //PhoneFieldImpl
