/**
 * Medes-IMPS 2011
 *
 * $Id$
 */
package org.imogene.model.core.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

import org.imogene.model.core.FieldDependentVisibility;
import org.imogene.model.core.FieldEntity;
import org.imogene.model.core.ImogenePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Field Dependent Visibility</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.imogene.model.core.impl.FieldDependentVisibilityImpl#getDependencyField <em>Dependency Field</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.FieldDependentVisibilityImpl#getDependencyFieldValue <em>Dependency Field Value</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.FieldDependentVisibilityImpl#getParentFieldEntity <em>Parent Field Entity</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FieldDependentVisibilityImpl extends EObjectImpl implements FieldDependentVisibility {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Medes-IMPS 2011";

	/**
	 * The cached value of the '{@link #getDependencyField() <em>Dependency Field</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDependencyField()
	 * @generated
	 * @ordered
	 */
	protected FieldEntity dependencyField;

	/**
	 * The default value of the '{@link #getDependencyFieldValue() <em>Dependency Field Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDependencyFieldValue()
	 * @generated
	 * @ordered
	 */
	protected static final String DEPENDENCY_FIELD_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDependencyFieldValue() <em>Dependency Field Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDependencyFieldValue()
	 * @generated
	 * @ordered
	 */
	protected String dependencyFieldValue = DEPENDENCY_FIELD_VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FieldDependentVisibilityImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImogenePackage.Literals.FIELD_DEPENDENT_VISIBILITY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FieldEntity getDependencyField() {
		if (dependencyField != null && dependencyField.eIsProxy()) {
			InternalEObject oldDependencyField = (InternalEObject)dependencyField;
			dependencyField = (FieldEntity)eResolveProxy(oldDependencyField);
			if (dependencyField != oldDependencyField) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ImogenePackage.FIELD_DEPENDENT_VISIBILITY__DEPENDENCY_FIELD, oldDependencyField, dependencyField));
			}
		}
		return dependencyField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FieldEntity basicGetDependencyField() {
		return dependencyField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDependencyField(FieldEntity newDependencyField) {
		FieldEntity oldDependencyField = dependencyField;
		dependencyField = newDependencyField;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.FIELD_DEPENDENT_VISIBILITY__DEPENDENCY_FIELD, oldDependencyField, dependencyField));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDependencyFieldValue() {
		return dependencyFieldValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDependencyFieldValue(String newDependencyFieldValue) {
		String oldDependencyFieldValue = dependencyFieldValue;
		dependencyFieldValue = newDependencyFieldValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.FIELD_DEPENDENT_VISIBILITY__DEPENDENCY_FIELD_VALUE, oldDependencyFieldValue, dependencyFieldValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FieldEntity getParentFieldEntity() {
		if (eContainerFeatureID() != ImogenePackage.FIELD_DEPENDENT_VISIBILITY__PARENT_FIELD_ENTITY) return null;
		return (FieldEntity)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParentFieldEntity(FieldEntity newParentFieldEntity, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newParentFieldEntity, ImogenePackage.FIELD_DEPENDENT_VISIBILITY__PARENT_FIELD_ENTITY, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentFieldEntity(FieldEntity newParentFieldEntity) {
		if (newParentFieldEntity != eInternalContainer() || (eContainerFeatureID() != ImogenePackage.FIELD_DEPENDENT_VISIBILITY__PARENT_FIELD_ENTITY && newParentFieldEntity != null)) {
			if (EcoreUtil.isAncestor(this, newParentFieldEntity))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParentFieldEntity != null)
				msgs = ((InternalEObject)newParentFieldEntity).eInverseAdd(this, ImogenePackage.FIELD_ENTITY__FIELD_DEPENDENT_VISIBILITY, FieldEntity.class, msgs);
			msgs = basicSetParentFieldEntity(newParentFieldEntity, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.FIELD_DEPENDENT_VISIBILITY__PARENT_FIELD_ENTITY, newParentFieldEntity, newParentFieldEntity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ImogenePackage.FIELD_DEPENDENT_VISIBILITY__PARENT_FIELD_ENTITY:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetParentFieldEntity((FieldEntity)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ImogenePackage.FIELD_DEPENDENT_VISIBILITY__PARENT_FIELD_ENTITY:
				return basicSetParentFieldEntity(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case ImogenePackage.FIELD_DEPENDENT_VISIBILITY__PARENT_FIELD_ENTITY:
				return eInternalContainer().eInverseRemove(this, ImogenePackage.FIELD_ENTITY__FIELD_DEPENDENT_VISIBILITY, FieldEntity.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ImogenePackage.FIELD_DEPENDENT_VISIBILITY__DEPENDENCY_FIELD:
				if (resolve) return getDependencyField();
				return basicGetDependencyField();
			case ImogenePackage.FIELD_DEPENDENT_VISIBILITY__DEPENDENCY_FIELD_VALUE:
				return getDependencyFieldValue();
			case ImogenePackage.FIELD_DEPENDENT_VISIBILITY__PARENT_FIELD_ENTITY:
				return getParentFieldEntity();
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
			case ImogenePackage.FIELD_DEPENDENT_VISIBILITY__DEPENDENCY_FIELD:
				setDependencyField((FieldEntity)newValue);
				return;
			case ImogenePackage.FIELD_DEPENDENT_VISIBILITY__DEPENDENCY_FIELD_VALUE:
				setDependencyFieldValue((String)newValue);
				return;
			case ImogenePackage.FIELD_DEPENDENT_VISIBILITY__PARENT_FIELD_ENTITY:
				setParentFieldEntity((FieldEntity)newValue);
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
			case ImogenePackage.FIELD_DEPENDENT_VISIBILITY__DEPENDENCY_FIELD:
				setDependencyField((FieldEntity)null);
				return;
			case ImogenePackage.FIELD_DEPENDENT_VISIBILITY__DEPENDENCY_FIELD_VALUE:
				setDependencyFieldValue(DEPENDENCY_FIELD_VALUE_EDEFAULT);
				return;
			case ImogenePackage.FIELD_DEPENDENT_VISIBILITY__PARENT_FIELD_ENTITY:
				setParentFieldEntity((FieldEntity)null);
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
			case ImogenePackage.FIELD_DEPENDENT_VISIBILITY__DEPENDENCY_FIELD:
				return dependencyField != null;
			case ImogenePackage.FIELD_DEPENDENT_VISIBILITY__DEPENDENCY_FIELD_VALUE:
				return DEPENDENCY_FIELD_VALUE_EDEFAULT == null ? dependencyFieldValue != null : !DEPENDENCY_FIELD_VALUE_EDEFAULT.equals(dependencyFieldValue);
			case ImogenePackage.FIELD_DEPENDENT_VISIBILITY__PARENT_FIELD_ENTITY:
				return getParentFieldEntity() != null;
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
		result.append(" (dependencyFieldValue: ");
		result.append(dependencyFieldValue);
		result.append(')');
		return result.toString();
	}

} //FieldDependentVisibilityImpl
