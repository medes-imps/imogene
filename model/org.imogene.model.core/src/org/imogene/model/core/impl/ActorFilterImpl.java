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

import org.imogene.model.core.ActorFilter;
import org.imogene.model.core.FilterField;
import org.imogene.model.core.ImogenePackage;
import org.imogene.model.core.RelationFieldEntity;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Actor Filter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.imogene.model.core.impl.ActorFilterImpl#getEntityField <em>Entity Field</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.ActorFilterImpl#getActorField <em>Actor Field</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.ActorFilterImpl#isSufficient <em>Sufficient</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ActorFilterImpl extends EObjectImpl implements ActorFilter {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Medes-IMPS 2011";

	/**
	 * The cached value of the '{@link #getEntityField() <em>Entity Field</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntityField()
	 * @generated
	 * @ordered
	 */
	protected RelationFieldEntity entityField;

	/**
	 * The cached value of the '{@link #getActorField() <em>Actor Field</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActorField()
	 * @generated
	 * @ordered
	 */
	protected FilterField actorField;

	/**
	 * The default value of the '{@link #isSufficient() <em>Sufficient</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSufficient()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SUFFICIENT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSufficient() <em>Sufficient</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSufficient()
	 * @generated
	 * @ordered
	 */
	protected boolean sufficient = SUFFICIENT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ActorFilterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImogenePackage.Literals.ACTOR_FILTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RelationFieldEntity getEntityField() {
		if (entityField != null && entityField.eIsProxy()) {
			InternalEObject oldEntityField = (InternalEObject)entityField;
			entityField = (RelationFieldEntity)eResolveProxy(oldEntityField);
			if (entityField != oldEntityField) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ImogenePackage.ACTOR_FILTER__ENTITY_FIELD, oldEntityField, entityField));
			}
		}
		return entityField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RelationFieldEntity basicGetEntityField() {
		return entityField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntityField(RelationFieldEntity newEntityField) {
		RelationFieldEntity oldEntityField = entityField;
		entityField = newEntityField;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.ACTOR_FILTER__ENTITY_FIELD, oldEntityField, entityField));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FilterField getActorField() {
		if (actorField != null && actorField.eIsProxy()) {
			InternalEObject oldActorField = (InternalEObject)actorField;
			actorField = (FilterField)eResolveProxy(oldActorField);
			if (actorField != oldActorField) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ImogenePackage.ACTOR_FILTER__ACTOR_FIELD, oldActorField, actorField));
			}
		}
		return actorField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FilterField basicGetActorField() {
		return actorField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActorField(FilterField newActorField) {
		FilterField oldActorField = actorField;
		actorField = newActorField;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.ACTOR_FILTER__ACTOR_FIELD, oldActorField, actorField));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSufficient() {
		return sufficient;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSufficient(boolean newSufficient) {
		boolean oldSufficient = sufficient;
		sufficient = newSufficient;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.ACTOR_FILTER__SUFFICIENT, oldSufficient, sufficient));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ImogenePackage.ACTOR_FILTER__ENTITY_FIELD:
				if (resolve) return getEntityField();
				return basicGetEntityField();
			case ImogenePackage.ACTOR_FILTER__ACTOR_FIELD:
				if (resolve) return getActorField();
				return basicGetActorField();
			case ImogenePackage.ACTOR_FILTER__SUFFICIENT:
				return isSufficient();
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
			case ImogenePackage.ACTOR_FILTER__ENTITY_FIELD:
				setEntityField((RelationFieldEntity)newValue);
				return;
			case ImogenePackage.ACTOR_FILTER__ACTOR_FIELD:
				setActorField((FilterField)newValue);
				return;
			case ImogenePackage.ACTOR_FILTER__SUFFICIENT:
				setSufficient((Boolean)newValue);
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
			case ImogenePackage.ACTOR_FILTER__ENTITY_FIELD:
				setEntityField((RelationFieldEntity)null);
				return;
			case ImogenePackage.ACTOR_FILTER__ACTOR_FIELD:
				setActorField((FilterField)null);
				return;
			case ImogenePackage.ACTOR_FILTER__SUFFICIENT:
				setSufficient(SUFFICIENT_EDEFAULT);
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
			case ImogenePackage.ACTOR_FILTER__ENTITY_FIELD:
				return entityField != null;
			case ImogenePackage.ACTOR_FILTER__ACTOR_FIELD:
				return actorField != null;
			case ImogenePackage.ACTOR_FILTER__SUFFICIENT:
				return sufficient != SUFFICIENT_EDEFAULT;
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
		result.append(" (sufficient: ");
		result.append(sufficient);
		result.append(')');
		return result.toString();
	}

} //ActorFilterImpl
