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

import org.imogene.model.core.CardEntity;
import org.imogene.model.core.CardEntityUIFormat;
import org.imogene.model.core.ImogenePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Card Entity UI Format</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.imogene.model.core.impl.CardEntityUIFormatImpl#getEntity <em>Entity</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.CardEntityUIFormatImpl#isWithTabulations <em>With Tabulations</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CardEntityUIFormatImpl extends EObjectImpl implements CardEntityUIFormat {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Medes-IMPS 2011";

	/**
	 * The cached value of the '{@link #getEntity() <em>Entity</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntity()
	 * @generated
	 * @ordered
	 */
	protected CardEntity entity;

	/**
	 * The default value of the '{@link #isWithTabulations() <em>With Tabulations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isWithTabulations()
	 * @generated
	 * @ordered
	 */
	protected static final boolean WITH_TABULATIONS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isWithTabulations() <em>With Tabulations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isWithTabulations()
	 * @generated
	 * @ordered
	 */
	protected boolean withTabulations = WITH_TABULATIONS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CardEntityUIFormatImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImogenePackage.Literals.CARD_ENTITY_UI_FORMAT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CardEntity getEntity() {
		if (entity != null && entity.eIsProxy()) {
			InternalEObject oldEntity = (InternalEObject)entity;
			entity = (CardEntity)eResolveProxy(oldEntity);
			if (entity != oldEntity) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ImogenePackage.CARD_ENTITY_UI_FORMAT__ENTITY, oldEntity, entity));
			}
		}
		return entity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CardEntity basicGetEntity() {
		return entity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntity(CardEntity newEntity) {
		CardEntity oldEntity = entity;
		entity = newEntity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.CARD_ENTITY_UI_FORMAT__ENTITY, oldEntity, entity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isWithTabulations() {
		return withTabulations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWithTabulations(boolean newWithTabulations) {
		boolean oldWithTabulations = withTabulations;
		withTabulations = newWithTabulations;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.CARD_ENTITY_UI_FORMAT__WITH_TABULATIONS, oldWithTabulations, withTabulations));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ImogenePackage.CARD_ENTITY_UI_FORMAT__ENTITY:
				if (resolve) return getEntity();
				return basicGetEntity();
			case ImogenePackage.CARD_ENTITY_UI_FORMAT__WITH_TABULATIONS:
				return isWithTabulations();
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
			case ImogenePackage.CARD_ENTITY_UI_FORMAT__ENTITY:
				setEntity((CardEntity)newValue);
				return;
			case ImogenePackage.CARD_ENTITY_UI_FORMAT__WITH_TABULATIONS:
				setWithTabulations((Boolean)newValue);
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
			case ImogenePackage.CARD_ENTITY_UI_FORMAT__ENTITY:
				setEntity((CardEntity)null);
				return;
			case ImogenePackage.CARD_ENTITY_UI_FORMAT__WITH_TABULATIONS:
				setWithTabulations(WITH_TABULATIONS_EDEFAULT);
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
			case ImogenePackage.CARD_ENTITY_UI_FORMAT__ENTITY:
				return entity != null;
			case ImogenePackage.CARD_ENTITY_UI_FORMAT__WITH_TABULATIONS:
				return withTabulations != WITH_TABULATIONS_EDEFAULT;
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
		result.append(" (withTabulations: ");
		result.append(withTabulations);
		result.append(')');
		return result.toString();
	}

} //CardEntityUIFormatImpl
