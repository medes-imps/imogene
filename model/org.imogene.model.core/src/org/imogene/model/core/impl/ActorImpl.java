/**
 * Medes-IMPS 2011
 *
 * $Id$
 */
package org.imogene.model.core.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.imogene.model.core.Actor;
import org.imogene.model.core.FieldEntity;
import org.imogene.model.core.FilterField;
import org.imogene.model.core.ImogenePackage;
import org.imogene.model.core.NotificationInfo;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Actor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.imogene.model.core.impl.ActorImpl#getNotificationInfos <em>Notification Infos</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.ActorImpl#getAdminFields <em>Admin Fields</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.ActorImpl#getFilters <em>Filters</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ActorImpl extends CardEntityImpl implements Actor {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Medes-IMPS 2011";

	/**
	 * The cached value of the '{@link #getNotificationInfos() <em>Notification Infos</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNotificationInfos()
	 * @generated
	 * @ordered
	 */
	protected EList<NotificationInfo> notificationInfos;

	/**
	 * The cached value of the '{@link #getAdminFields() <em>Admin Fields</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdminFields()
	 * @generated
	 * @ordered
	 */
	protected EList<FieldEntity> adminFields;

	/**
	 * The cached value of the '{@link #getFilters() <em>Filters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFilters()
	 * @generated
	 * @ordered
	 */
	protected EList<FilterField> filters;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ActorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImogenePackage.Literals.ACTOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<NotificationInfo> getNotificationInfos() {
		if (notificationInfos == null) {
			notificationInfos = new EObjectContainmentEList<NotificationInfo>(NotificationInfo.class, this, ImogenePackage.ACTOR__NOTIFICATION_INFOS);
		}
		return notificationInfos;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FieldEntity> getAdminFields() {
		if (adminFields == null) {
			adminFields = new EObjectResolvingEList<FieldEntity>(FieldEntity.class, this, ImogenePackage.ACTOR__ADMIN_FIELDS);
		}
		return adminFields;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FilterField> getFilters() {
		if (filters == null) {
			filters = new EObjectContainmentWithInverseEList<FilterField>(FilterField.class, this, ImogenePackage.ACTOR__FILTERS, ImogenePackage.FILTER_FIELD__PARENT_ACTOR);
		}
		return filters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ImogenePackage.ACTOR__FILTERS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getFilters()).basicAdd(otherEnd, msgs);
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
			case ImogenePackage.ACTOR__NOTIFICATION_INFOS:
				return ((InternalEList<?>)getNotificationInfos()).basicRemove(otherEnd, msgs);
			case ImogenePackage.ACTOR__FILTERS:
				return ((InternalEList<?>)getFilters()).basicRemove(otherEnd, msgs);
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
			case ImogenePackage.ACTOR__NOTIFICATION_INFOS:
				return getNotificationInfos();
			case ImogenePackage.ACTOR__ADMIN_FIELDS:
				return getAdminFields();
			case ImogenePackage.ACTOR__FILTERS:
				return getFilters();
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
			case ImogenePackage.ACTOR__NOTIFICATION_INFOS:
				getNotificationInfos().clear();
				getNotificationInfos().addAll((Collection<? extends NotificationInfo>)newValue);
				return;
			case ImogenePackage.ACTOR__ADMIN_FIELDS:
				getAdminFields().clear();
				getAdminFields().addAll((Collection<? extends FieldEntity>)newValue);
				return;
			case ImogenePackage.ACTOR__FILTERS:
				getFilters().clear();
				getFilters().addAll((Collection<? extends FilterField>)newValue);
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
			case ImogenePackage.ACTOR__NOTIFICATION_INFOS:
				getNotificationInfos().clear();
				return;
			case ImogenePackage.ACTOR__ADMIN_FIELDS:
				getAdminFields().clear();
				return;
			case ImogenePackage.ACTOR__FILTERS:
				getFilters().clear();
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
			case ImogenePackage.ACTOR__NOTIFICATION_INFOS:
				return notificationInfos != null && !notificationInfos.isEmpty();
			case ImogenePackage.ACTOR__ADMIN_FIELDS:
				return adminFields != null && !adminFields.isEmpty();
			case ImogenePackage.ACTOR__FILTERS:
				return filters != null && !filters.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ActorImpl
