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
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.imogene.model.core.Description;
import org.imogene.model.core.ImogenePackage;
import org.imogene.model.core.ValidationRule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Validation Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.imogene.model.core.impl.ValidationRuleImpl#getValidationRegularExpression <em>Validation Regular Expression</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.ValidationRuleImpl#getDescriptions <em>Descriptions</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ValidationRuleImpl extends EObjectImpl implements ValidationRule {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Medes-IMPS 2011";

	/**
	 * The default value of the '{@link #getValidationRegularExpression() <em>Validation Regular Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValidationRegularExpression()
	 * @generated
	 * @ordered
	 */
	protected static final String VALIDATION_REGULAR_EXPRESSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValidationRegularExpression() <em>Validation Regular Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValidationRegularExpression()
	 * @generated
	 * @ordered
	 */
	protected String validationRegularExpression = VALIDATION_REGULAR_EXPRESSION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDescriptions() <em>Descriptions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescriptions()
	 * @generated
	 * @ordered
	 */
	protected EList<Description> descriptions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ValidationRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImogenePackage.Literals.VALIDATION_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getValidationRegularExpression() {
		return validationRegularExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValidationRegularExpression(String newValidationRegularExpression) {
		String oldValidationRegularExpression = validationRegularExpression;
		validationRegularExpression = newValidationRegularExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.VALIDATION_RULE__VALIDATION_REGULAR_EXPRESSION, oldValidationRegularExpression, validationRegularExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Description> getDescriptions() {
		if (descriptions == null) {
			descriptions = new EObjectContainmentEList<Description>(Description.class, this, ImogenePackage.VALIDATION_RULE__DESCRIPTIONS);
		}
		return descriptions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ImogenePackage.VALIDATION_RULE__DESCRIPTIONS:
				return ((InternalEList<?>)getDescriptions()).basicRemove(otherEnd, msgs);
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
			case ImogenePackage.VALIDATION_RULE__VALIDATION_REGULAR_EXPRESSION:
				return getValidationRegularExpression();
			case ImogenePackage.VALIDATION_RULE__DESCRIPTIONS:
				return getDescriptions();
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
			case ImogenePackage.VALIDATION_RULE__VALIDATION_REGULAR_EXPRESSION:
				setValidationRegularExpression((String)newValue);
				return;
			case ImogenePackage.VALIDATION_RULE__DESCRIPTIONS:
				getDescriptions().clear();
				getDescriptions().addAll((Collection<? extends Description>)newValue);
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
			case ImogenePackage.VALIDATION_RULE__VALIDATION_REGULAR_EXPRESSION:
				setValidationRegularExpression(VALIDATION_REGULAR_EXPRESSION_EDEFAULT);
				return;
			case ImogenePackage.VALIDATION_RULE__DESCRIPTIONS:
				getDescriptions().clear();
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
			case ImogenePackage.VALIDATION_RULE__VALIDATION_REGULAR_EXPRESSION:
				return VALIDATION_REGULAR_EXPRESSION_EDEFAULT == null ? validationRegularExpression != null : !VALIDATION_REGULAR_EXPRESSION_EDEFAULT.equals(validationRegularExpression);
			case ImogenePackage.VALIDATION_RULE__DESCRIPTIONS:
				return descriptions != null && !descriptions.isEmpty();
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
		result.append(" (validationRegularExpression: ");
		result.append(validationRegularExpression);
		result.append(')');
		return result.toString();
	}

} //ValidationRuleImpl
