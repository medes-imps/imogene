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

import org.eclipse.emf.ecore.util.EcoreUtil;

import org.imogene.model.core.Actor;
import org.imogene.model.core.FilterField;
import org.imogene.model.core.ImogenePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Filter Field</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.imogene.model.core.impl.FilterFieldImpl#getParentActor <em>Parent Actor</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FilterFieldImpl extends RelationFieldEntityImpl implements FilterField {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Medes-IMPS 2011";

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FilterFieldImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImogenePackage.Literals.FILTER_FIELD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Actor getParentActor() {
		if (eContainerFeatureID() != ImogenePackage.FILTER_FIELD__PARENT_ACTOR) return null;
		return (Actor)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParentActor(Actor newParentActor, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newParentActor, ImogenePackage.FILTER_FIELD__PARENT_ACTOR, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentActor(Actor newParentActor) {
		if (newParentActor != eInternalContainer() || (eContainerFeatureID() != ImogenePackage.FILTER_FIELD__PARENT_ACTOR && newParentActor != null)) {
			if (EcoreUtil.isAncestor(this, newParentActor))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParentActor != null)
				msgs = ((InternalEObject)newParentActor).eInverseAdd(this, ImogenePackage.ACTOR__FILTERS, Actor.class, msgs);
			msgs = basicSetParentActor(newParentActor, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.FILTER_FIELD__PARENT_ACTOR, newParentActor, newParentActor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ImogenePackage.FILTER_FIELD__PARENT_ACTOR:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetParentActor((Actor)otherEnd, msgs);
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
			case ImogenePackage.FILTER_FIELD__PARENT_ACTOR:
				return basicSetParentActor(null, msgs);
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
			case ImogenePackage.FILTER_FIELD__PARENT_ACTOR:
				return eInternalContainer().eInverseRemove(this, ImogenePackage.ACTOR__FILTERS, Actor.class, msgs);
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
			case ImogenePackage.FILTER_FIELD__PARENT_ACTOR:
				return getParentActor();
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
			case ImogenePackage.FILTER_FIELD__PARENT_ACTOR:
				setParentActor((Actor)newValue);
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
			case ImogenePackage.FILTER_FIELD__PARENT_ACTOR:
				setParentActor((Actor)null);
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
			case ImogenePackage.FILTER_FIELD__PARENT_ACTOR:
				return getParentActor() != null;
		}
		return super.eIsSet(featureID);
	}

} //FilterFieldImpl
