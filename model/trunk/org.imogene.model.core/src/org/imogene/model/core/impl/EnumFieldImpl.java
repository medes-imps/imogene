/**
 * Medes-IMPS 2011
 *
 * $Id$
 */
package org.imogene.model.core.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.imogene.model.core.EnumField;
import org.imogene.model.core.EnumValue;
import org.imogene.model.core.ImogenePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Enum Field</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.imogene.model.core.impl.EnumFieldImpl#getEnumValues <em>Enum Values</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.EnumFieldImpl#isMultipleSelection <em>Multiple Selection</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EnumFieldImpl extends FieldEntityImpl implements EnumField {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Medes-IMPS 2011";

	/**
	 * The cached value of the '{@link #getEnumValues() <em>Enum Values</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnumValues()
	 * @generated
	 * @ordered
	 */
	protected EList<EnumValue> enumValues;

	/**
	 * The default value of the '{@link #isMultipleSelection() <em>Multiple Selection</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMultipleSelection()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MULTIPLE_SELECTION_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMultipleSelection() <em>Multiple Selection</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMultipleSelection()
	 * @generated
	 * @ordered
	 */
	protected boolean multipleSelection = MULTIPLE_SELECTION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EnumFieldImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImogenePackage.Literals.ENUM_FIELD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EnumValue> getEnumValues() {
		if (enumValues == null) {
			enumValues = new EObjectContainmentEList<EnumValue>(EnumValue.class, this, ImogenePackage.ENUM_FIELD__ENUM_VALUES);
		}
		return enumValues;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMultipleSelection() {
		return multipleSelection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMultipleSelection(boolean newMultipleSelection) {
		boolean oldMultipleSelection = multipleSelection;
		multipleSelection = newMultipleSelection;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.ENUM_FIELD__MULTIPLE_SELECTION, oldMultipleSelection, multipleSelection));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ImogenePackage.ENUM_FIELD__ENUM_VALUES:
				return ((InternalEList<?>)getEnumValues()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ImogenePackage.ENUM_FIELD__ENUM_VALUES:
				return getEnumValues();
			case ImogenePackage.ENUM_FIELD__MULTIPLE_SELECTION:
				return isMultipleSelection();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ImogenePackage.ENUM_FIELD__ENUM_VALUES:
				getEnumValues().clear();
				getEnumValues().addAll((Collection<? extends EnumValue>)newValue);
				return;
			case ImogenePackage.ENUM_FIELD__MULTIPLE_SELECTION:
				setMultipleSelection((Boolean)newValue);
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
			case ImogenePackage.ENUM_FIELD__ENUM_VALUES:
				getEnumValues().clear();
				return;
			case ImogenePackage.ENUM_FIELD__MULTIPLE_SELECTION:
				setMultipleSelection(MULTIPLE_SELECTION_EDEFAULT);
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
			case ImogenePackage.ENUM_FIELD__ENUM_VALUES:
				return enumValues != null && !enumValues.isEmpty();
			case ImogenePackage.ENUM_FIELD__MULTIPLE_SELECTION:
				return multipleSelection != MULTIPLE_SELECTION_EDEFAULT;
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
		result.append(" (multipleSelection: ");
		result.append(multipleSelection);
		result.append(')');
		return result.toString();
	}

} //EnumFieldImpl
