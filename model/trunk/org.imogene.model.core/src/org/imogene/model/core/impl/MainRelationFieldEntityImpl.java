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
import org.imogene.model.core.MainRelationFieldEntity;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Main Relation Field Entity</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.imogene.model.core.impl.MainRelationFieldEntityImpl#getInverseCardinality <em>Inverse Cardinality</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MainRelationFieldEntityImpl extends RelationFieldEntityImpl implements MainRelationFieldEntity {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Medes-IMPS 2011";

	/**
	 * The default value of the '{@link #getInverseCardinality() <em>Inverse Cardinality</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInverseCardinality()
	 * @generated
	 * @ordered
	 */
	protected static final int INVERSE_CARDINALITY_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getInverseCardinality() <em>Inverse Cardinality</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInverseCardinality()
	 * @generated
	 * @ordered
	 */
	protected int inverseCardinality = INVERSE_CARDINALITY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MainRelationFieldEntityImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImogenePackage.Literals.MAIN_RELATION_FIELD_ENTITY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getInverseCardinality() {
		return inverseCardinality;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInverseCardinality(int newInverseCardinality) {
		int oldInverseCardinality = inverseCardinality;
		inverseCardinality = newInverseCardinality;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.MAIN_RELATION_FIELD_ENTITY__INVERSE_CARDINALITY, oldInverseCardinality, inverseCardinality));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ImogenePackage.MAIN_RELATION_FIELD_ENTITY__INVERSE_CARDINALITY:
				return getInverseCardinality();
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
			case ImogenePackage.MAIN_RELATION_FIELD_ENTITY__INVERSE_CARDINALITY:
				setInverseCardinality((Integer)newValue);
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
			case ImogenePackage.MAIN_RELATION_FIELD_ENTITY__INVERSE_CARDINALITY:
				setInverseCardinality(INVERSE_CARDINALITY_EDEFAULT);
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
			case ImogenePackage.MAIN_RELATION_FIELD_ENTITY__INVERSE_CARDINALITY:
				return inverseCardinality != INVERSE_CARDINALITY_EDEFAULT;
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
		result.append(" (InverseCardinality: ");
		result.append(inverseCardinality);
		result.append(')');
		return result.toString();
	}

} //MainRelationFieldEntityImpl
