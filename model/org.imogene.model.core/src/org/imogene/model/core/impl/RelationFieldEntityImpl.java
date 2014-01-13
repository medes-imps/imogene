/**
 * Medes-IMPS 2011
 *
 * $Id$
 */
package org.imogene.model.core.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.imogene.model.core.CardEntity;
import org.imogene.model.core.ImogenePackage;
import org.imogene.model.core.RelationFieldEntity;
import org.imogene.model.core.RelationType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Relation Field Entity</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.imogene.model.core.impl.RelationFieldEntityImpl#getEntity <em>Entity</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.RelationFieldEntityImpl#getCardinality <em>Cardinality</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.RelationFieldEntityImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.RelationFieldEntityImpl#getCommonFields <em>Common Fields</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.RelationFieldEntityImpl#getOppositeRelationField <em>Opposite Relation Field</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.RelationFieldEntityImpl#getRelationHierarchicalFilter <em>Relation Hierarchical Filter</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.RelationFieldEntityImpl#isNestedForm <em>Nested Form</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class RelationFieldEntityImpl extends FieldEntityImpl implements RelationFieldEntity {
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
	 * The default value of the '{@link #getCardinality() <em>Cardinality</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCardinality()
	 * @generated
	 * @ordered
	 */
	protected static final int CARDINALITY_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getCardinality() <em>Cardinality</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCardinality()
	 * @generated
	 * @ordered
	 */
	protected int cardinality = CARDINALITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final RelationType TYPE_EDEFAULT = RelationType.ASSOCIATION_LITERAL;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected RelationType type = TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getCommonFields() <em>Common Fields</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommonFields()
	 * @generated
	 * @ordered
	 */
	protected EList<RelationFieldEntity> commonFields;

	/**
	 * The cached value of the '{@link #getOppositeRelationField() <em>Opposite Relation Field</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOppositeRelationField()
	 * @generated
	 * @ordered
	 */
	protected RelationFieldEntity oppositeRelationField;

	/**
	 * The cached value of the '{@link #getRelationHierarchicalFilter() <em>Relation Hierarchical Filter</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelationHierarchicalFilter()
	 * @generated
	 * @ordered
	 */
	protected EList<RelationFieldEntity> relationHierarchicalFilter;

	/**
	 * The default value of the '{@link #isNestedForm() <em>Nested Form</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNestedForm()
	 * @generated
	 * @ordered
	 */
	protected static final boolean NESTED_FORM_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isNestedForm() <em>Nested Form</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNestedForm()
	 * @generated
	 * @ordered
	 */
	protected boolean nestedForm = NESTED_FORM_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RelationFieldEntityImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImogenePackage.Literals.RELATION_FIELD_ENTITY;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ImogenePackage.RELATION_FIELD_ENTITY__ENTITY, oldEntity, entity));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.RELATION_FIELD_ENTITY__ENTITY, oldEntity, entity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getCardinality() {
		return cardinality;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCardinality(int newCardinality) {
		int oldCardinality = cardinality;
		cardinality = newCardinality;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.RELATION_FIELD_ENTITY__CARDINALITY, oldCardinality, cardinality));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RelationType getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(RelationType newType) {
		RelationType oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.RELATION_FIELD_ENTITY__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<RelationFieldEntity> getCommonFields() {
		if (commonFields == null) {
			commonFields = new EObjectResolvingEList<RelationFieldEntity>(RelationFieldEntity.class, this, ImogenePackage.RELATION_FIELD_ENTITY__COMMON_FIELDS);
		}
		return commonFields;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RelationFieldEntity getOppositeRelationField() {
		if (oppositeRelationField != null && oppositeRelationField.eIsProxy()) {
			InternalEObject oldOppositeRelationField = (InternalEObject)oppositeRelationField;
			oppositeRelationField = (RelationFieldEntity)eResolveProxy(oldOppositeRelationField);
			if (oppositeRelationField != oldOppositeRelationField) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ImogenePackage.RELATION_FIELD_ENTITY__OPPOSITE_RELATION_FIELD, oldOppositeRelationField, oppositeRelationField));
			}
		}
		return oppositeRelationField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RelationFieldEntity basicGetOppositeRelationField() {
		return oppositeRelationField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOppositeRelationField(RelationFieldEntity newOppositeRelationField) {
		RelationFieldEntity oldOppositeRelationField = oppositeRelationField;
		oppositeRelationField = newOppositeRelationField;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.RELATION_FIELD_ENTITY__OPPOSITE_RELATION_FIELD, oldOppositeRelationField, oppositeRelationField));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<RelationFieldEntity> getRelationHierarchicalFilter() {
		if (relationHierarchicalFilter == null) {
			relationHierarchicalFilter = new EObjectResolvingEList<RelationFieldEntity>(RelationFieldEntity.class, this, ImogenePackage.RELATION_FIELD_ENTITY__RELATION_HIERARCHICAL_FILTER);
		}
		return relationHierarchicalFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isNestedForm() {
		return nestedForm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNestedForm(boolean newNestedForm) {
		boolean oldNestedForm = nestedForm;
		nestedForm = newNestedForm;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.RELATION_FIELD_ENTITY__NESTED_FORM, oldNestedForm, nestedForm));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ImogenePackage.RELATION_FIELD_ENTITY__ENTITY:
				if (resolve) return getEntity();
				return basicGetEntity();
			case ImogenePackage.RELATION_FIELD_ENTITY__CARDINALITY:
				return getCardinality();
			case ImogenePackage.RELATION_FIELD_ENTITY__TYPE:
				return getType();
			case ImogenePackage.RELATION_FIELD_ENTITY__COMMON_FIELDS:
				return getCommonFields();
			case ImogenePackage.RELATION_FIELD_ENTITY__OPPOSITE_RELATION_FIELD:
				if (resolve) return getOppositeRelationField();
				return basicGetOppositeRelationField();
			case ImogenePackage.RELATION_FIELD_ENTITY__RELATION_HIERARCHICAL_FILTER:
				return getRelationHierarchicalFilter();
			case ImogenePackage.RELATION_FIELD_ENTITY__NESTED_FORM:
				return isNestedForm();
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
			case ImogenePackage.RELATION_FIELD_ENTITY__ENTITY:
				setEntity((CardEntity)newValue);
				return;
			case ImogenePackage.RELATION_FIELD_ENTITY__CARDINALITY:
				setCardinality((Integer)newValue);
				return;
			case ImogenePackage.RELATION_FIELD_ENTITY__TYPE:
				setType((RelationType)newValue);
				return;
			case ImogenePackage.RELATION_FIELD_ENTITY__COMMON_FIELDS:
				getCommonFields().clear();
				getCommonFields().addAll((Collection<? extends RelationFieldEntity>)newValue);
				return;
			case ImogenePackage.RELATION_FIELD_ENTITY__OPPOSITE_RELATION_FIELD:
				setOppositeRelationField((RelationFieldEntity)newValue);
				return;
			case ImogenePackage.RELATION_FIELD_ENTITY__RELATION_HIERARCHICAL_FILTER:
				getRelationHierarchicalFilter().clear();
				getRelationHierarchicalFilter().addAll((Collection<? extends RelationFieldEntity>)newValue);
				return;
			case ImogenePackage.RELATION_FIELD_ENTITY__NESTED_FORM:
				setNestedForm((Boolean)newValue);
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
			case ImogenePackage.RELATION_FIELD_ENTITY__ENTITY:
				setEntity((CardEntity)null);
				return;
			case ImogenePackage.RELATION_FIELD_ENTITY__CARDINALITY:
				setCardinality(CARDINALITY_EDEFAULT);
				return;
			case ImogenePackage.RELATION_FIELD_ENTITY__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case ImogenePackage.RELATION_FIELD_ENTITY__COMMON_FIELDS:
				getCommonFields().clear();
				return;
			case ImogenePackage.RELATION_FIELD_ENTITY__OPPOSITE_RELATION_FIELD:
				setOppositeRelationField((RelationFieldEntity)null);
				return;
			case ImogenePackage.RELATION_FIELD_ENTITY__RELATION_HIERARCHICAL_FILTER:
				getRelationHierarchicalFilter().clear();
				return;
			case ImogenePackage.RELATION_FIELD_ENTITY__NESTED_FORM:
				setNestedForm(NESTED_FORM_EDEFAULT);
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
			case ImogenePackage.RELATION_FIELD_ENTITY__ENTITY:
				return entity != null;
			case ImogenePackage.RELATION_FIELD_ENTITY__CARDINALITY:
				return cardinality != CARDINALITY_EDEFAULT;
			case ImogenePackage.RELATION_FIELD_ENTITY__TYPE:
				return type != TYPE_EDEFAULT;
			case ImogenePackage.RELATION_FIELD_ENTITY__COMMON_FIELDS:
				return commonFields != null && !commonFields.isEmpty();
			case ImogenePackage.RELATION_FIELD_ENTITY__OPPOSITE_RELATION_FIELD:
				return oppositeRelationField != null;
			case ImogenePackage.RELATION_FIELD_ENTITY__RELATION_HIERARCHICAL_FILTER:
				return relationHierarchicalFilter != null && !relationHierarchicalFilter.isEmpty();
			case ImogenePackage.RELATION_FIELD_ENTITY__NESTED_FORM:
				return nestedForm != NESTED_FORM_EDEFAULT;
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
		result.append(" (cardinality: ");
		result.append(cardinality);
		result.append(", type: ");
		result.append(type);
		result.append(", nestedForm: ");
		result.append(nestedForm);
		result.append(')');
		return result.toString();
	}

} //RelationFieldEntityImpl
