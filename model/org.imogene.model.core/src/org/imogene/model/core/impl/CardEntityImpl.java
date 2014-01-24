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
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.imogene.model.core.ActorFilter;
import org.imogene.model.core.CardEntity;
import org.imogene.model.core.Description;
import org.imogene.model.core.FieldEntity;
import org.imogene.model.core.FieldGroup;
import org.imogene.model.core.GeoField;
import org.imogene.model.core.ImogenePackage;
import org.imogene.model.core.RelationFieldEntity;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Card Entity</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.imogene.model.core.impl.CardEntityImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.CardEntityImpl#getShortName <em>Short Name</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.CardEntityImpl#getDescriptions <em>Descriptions</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.CardEntityImpl#getColumnFields <em>Column Fields</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.CardEntityImpl#getMainFields <em>Main Fields</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.CardEntityImpl#getSecondaryFields <em>Secondary Fields</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.CardEntityImpl#getIcon <em>Icon</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.CardEntityImpl#isTopLevel <em>Top Level</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.CardEntityImpl#getClientFilterFields <em>Client Filter Fields</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.CardEntityImpl#getGroups <em>Groups</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.CardEntityImpl#getSortFields <em>Sort Fields</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.CardEntityImpl#getActorFilterFields <em>Actor Filter Fields</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.CardEntityImpl#getColor <em>Color</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.CardEntityImpl#isClientPeriodFilterable <em>Client Period Filterable</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.CardEntityImpl#getGeoreferenced <em>Georeferenced</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.CardEntityImpl#getRdfSubject <em>Rdf Subject</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.CardEntityImpl#getRdfPredicate <em>Rdf Predicate</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.CardEntityImpl#getNestedFields <em>Nested Fields</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.CardEntityImpl#isHasDynamicFields <em>Has Dynamic Fields</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CardEntityImpl extends EObjectImpl implements CardEntity {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Medes-IMPS 2011";

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getShortName() <em>Short Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getShortName()
	 * @generated
	 * @ordered
	 */
	protected static final String SHORT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getShortName() <em>Short Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getShortName()
	 * @generated
	 * @ordered
	 */
	protected String shortName = SHORT_NAME_EDEFAULT;

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
	 * The cached value of the '{@link #getColumnFields() <em>Column Fields</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumnFields()
	 * @generated
	 * @ordered
	 */
	protected EList<FieldEntity> columnFields;

	/**
	 * The cached value of the '{@link #getMainFields() <em>Main Fields</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMainFields()
	 * @generated
	 * @ordered
	 */
	protected EList<FieldEntity> mainFields;

	/**
	 * The cached value of the '{@link #getSecondaryFields() <em>Secondary Fields</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecondaryFields()
	 * @generated
	 * @ordered
	 */
	protected EList<FieldEntity> secondaryFields;

	/**
	 * The default value of the '{@link #getIcon() <em>Icon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIcon()
	 * @generated
	 * @ordered
	 */
	protected static final String ICON_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIcon() <em>Icon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIcon()
	 * @generated
	 * @ordered
	 */
	protected String icon = ICON_EDEFAULT;

	/**
	 * The default value of the '{@link #isTopLevel() <em>Top Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTopLevel()
	 * @generated
	 * @ordered
	 */
	protected static final boolean TOP_LEVEL_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isTopLevel() <em>Top Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTopLevel()
	 * @generated
	 * @ordered
	 */
	protected boolean topLevel = TOP_LEVEL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getClientFilterFields() <em>Client Filter Fields</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClientFilterFields()
	 * @generated
	 * @ordered
	 */
	protected EList<FieldEntity> clientFilterFields;

	/**
	 * The cached value of the '{@link #getGroups() <em>Groups</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroups()
	 * @generated
	 * @ordered
	 */
	protected EList<FieldGroup> groups;

	/**
	 * The cached value of the '{@link #getSortFields() <em>Sort Fields</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSortFields()
	 * @generated
	 * @ordered
	 */
	protected EList<FieldEntity> sortFields;

	/**
	 * The cached value of the '{@link #getActorFilterFields() <em>Actor Filter Fields</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActorFilterFields()
	 * @generated
	 * @ordered
	 */
	protected EList<ActorFilter> actorFilterFields;

	/**
	 * The default value of the '{@link #getColor() <em>Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColor()
	 * @generated
	 * @ordered
	 */
	protected static final String COLOR_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getColor() <em>Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColor()
	 * @generated
	 * @ordered
	 */
	protected String color = COLOR_EDEFAULT;

	/**
	 * The default value of the '{@link #isClientPeriodFilterable() <em>Client Period Filterable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isClientPeriodFilterable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CLIENT_PERIOD_FILTERABLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isClientPeriodFilterable() <em>Client Period Filterable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isClientPeriodFilterable()
	 * @generated
	 * @ordered
	 */
	protected boolean clientPeriodFilterable = CLIENT_PERIOD_FILTERABLE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getGeoreferenced() <em>Georeferenced</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGeoreferenced()
	 * @generated
	 * @ordered
	 */
	protected GeoField georeferenced;

	/**
	 * The cached value of the '{@link #getRdfSubject() <em>Rdf Subject</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRdfSubject()
	 * @generated
	 * @ordered
	 */
	protected RelationFieldEntity rdfSubject;

	/**
	 * The default value of the '{@link #getRdfPredicate() <em>Rdf Predicate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRdfPredicate()
	 * @generated
	 * @ordered
	 */
	protected static final String RDF_PREDICATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRdfPredicate() <em>Rdf Predicate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRdfPredicate()
	 * @generated
	 * @ordered
	 */
	protected String rdfPredicate = RDF_PREDICATE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getNestedFields() <em>Nested Fields</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNestedFields()
	 * @generated
	 * @ordered
	 */
	protected EList<FieldEntity> nestedFields;

	/**
	 * The default value of the '{@link #isHasDynamicFields() <em>Has Dynamic Fields</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasDynamicFields()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HAS_DYNAMIC_FIELDS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isHasDynamicFields() <em>Has Dynamic Fields</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasDynamicFields()
	 * @generated
	 * @ordered
	 */
	protected boolean hasDynamicFields = HAS_DYNAMIC_FIELDS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CardEntityImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImogenePackage.Literals.CARD_ENTITY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.CARD_ENTITY__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Description> getDescriptions() {
		if (descriptions == null) {
			descriptions = new EObjectContainmentEList<Description>(Description.class, this, ImogenePackage.CARD_ENTITY__DESCRIPTIONS);
		}
		return descriptions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FieldEntity> getMainFields() {
		if (mainFields == null) {
			mainFields = new EObjectResolvingEList<FieldEntity>(FieldEntity.class, this, ImogenePackage.CARD_ENTITY__MAIN_FIELDS);
		}
		return mainFields;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FieldEntity> getColumnFields() {
		if (columnFields == null) {
			columnFields = new EObjectResolvingEList<FieldEntity>(FieldEntity.class, this, ImogenePackage.CARD_ENTITY__COLUMN_FIELDS);
		}
		return columnFields;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShortName(String newShortName) {
		String oldShortName = shortName;
		shortName = newShortName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.CARD_ENTITY__SHORT_NAME, oldShortName, shortName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIcon(String newIcon) {
		String oldIcon = icon;
		icon = newIcon;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.CARD_ENTITY__ICON, oldIcon, icon));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isTopLevel() {
		return topLevel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTopLevel(boolean newTopLevel) {
		boolean oldTopLevel = topLevel;
		topLevel = newTopLevel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.CARD_ENTITY__TOP_LEVEL, oldTopLevel, topLevel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FieldEntity> getClientFilterFields() {
		if (clientFilterFields == null) {
			clientFilterFields = new EObjectResolvingEList<FieldEntity>(FieldEntity.class, this, ImogenePackage.CARD_ENTITY__CLIENT_FILTER_FIELDS);
		}
		return clientFilterFields;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FieldGroup> getGroups() {
		if (groups == null) {
			groups = new EObjectContainmentWithInverseEList<FieldGroup>(FieldGroup.class, this, ImogenePackage.CARD_ENTITY__GROUPS, ImogenePackage.FIELD_GROUP__PARENT_CARD);
		}
		return groups;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FieldEntity> getSortFields() {
		if (sortFields == null) {
			sortFields = new EObjectResolvingEList<FieldEntity>(FieldEntity.class, this, ImogenePackage.CARD_ENTITY__SORT_FIELDS);
		}
		return sortFields;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ActorFilter> getActorFilterFields() {
		if (actorFilterFields == null) {
			actorFilterFields = new EObjectResolvingEList<ActorFilter>(ActorFilter.class, this, ImogenePackage.CARD_ENTITY__ACTOR_FILTER_FIELDS);
		}
		return actorFilterFields;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FieldEntity> getSecondaryFields() {
		if (secondaryFields == null) {
			secondaryFields = new EObjectResolvingEList<FieldEntity>(FieldEntity.class, this, ImogenePackage.CARD_ENTITY__SECONDARY_FIELDS);
		}
		return secondaryFields;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getColor() {
		return color;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setColor(String newColor) {
		String oldColor = color;
		color = newColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.CARD_ENTITY__COLOR, oldColor, color));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isClientPeriodFilterable() {
		return clientPeriodFilterable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClientPeriodFilterable(boolean newClientPeriodFilterable) {
		boolean oldClientPeriodFilterable = clientPeriodFilterable;
		clientPeriodFilterable = newClientPeriodFilterable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.CARD_ENTITY__CLIENT_PERIOD_FILTERABLE, oldClientPeriodFilterable, clientPeriodFilterable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GeoField getGeoreferenced() {
		if (georeferenced != null && georeferenced.eIsProxy()) {
			InternalEObject oldGeoreferenced = (InternalEObject)georeferenced;
			georeferenced = (GeoField)eResolveProxy(oldGeoreferenced);
			if (georeferenced != oldGeoreferenced) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ImogenePackage.CARD_ENTITY__GEOREFERENCED, oldGeoreferenced, georeferenced));
			}
		}
		return georeferenced;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GeoField basicGetGeoreferenced() {
		return georeferenced;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGeoreferenced(GeoField newGeoreferenced) {
		GeoField oldGeoreferenced = georeferenced;
		georeferenced = newGeoreferenced;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.CARD_ENTITY__GEOREFERENCED, oldGeoreferenced, georeferenced));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RelationFieldEntity getRdfSubject() {
		if (rdfSubject != null && rdfSubject.eIsProxy()) {
			InternalEObject oldRdfSubject = (InternalEObject)rdfSubject;
			rdfSubject = (RelationFieldEntity)eResolveProxy(oldRdfSubject);
			if (rdfSubject != oldRdfSubject) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ImogenePackage.CARD_ENTITY__RDF_SUBJECT, oldRdfSubject, rdfSubject));
			}
		}
		return rdfSubject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RelationFieldEntity basicGetRdfSubject() {
		return rdfSubject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRdfSubject(RelationFieldEntity newRdfSubject) {
		RelationFieldEntity oldRdfSubject = rdfSubject;
		rdfSubject = newRdfSubject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.CARD_ENTITY__RDF_SUBJECT, oldRdfSubject, rdfSubject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRdfPredicate() {
		return rdfPredicate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRdfPredicate(String newRdfPredicate) {
		String oldRdfPredicate = rdfPredicate;
		rdfPredicate = newRdfPredicate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.CARD_ENTITY__RDF_PREDICATE, oldRdfPredicate, rdfPredicate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FieldEntity> getNestedFields() {
		if (nestedFields == null) {
			nestedFields = new EObjectResolvingEList<FieldEntity>(FieldEntity.class, this, ImogenePackage.CARD_ENTITY__NESTED_FIELDS);
		}
		return nestedFields;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isHasDynamicFields() {
		return hasDynamicFields;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHasDynamicFields(boolean newHasDynamicFields) {
		boolean oldHasDynamicFields = hasDynamicFields;
		hasDynamicFields = newHasDynamicFields;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.CARD_ENTITY__HAS_DYNAMIC_FIELDS, oldHasDynamicFields, hasDynamicFields));
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
			case ImogenePackage.CARD_ENTITY__GROUPS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getGroups()).basicAdd(otherEnd, msgs);
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
			case ImogenePackage.CARD_ENTITY__DESCRIPTIONS:
				return ((InternalEList<?>)getDescriptions()).basicRemove(otherEnd, msgs);
			case ImogenePackage.CARD_ENTITY__GROUPS:
				return ((InternalEList<?>)getGroups()).basicRemove(otherEnd, msgs);
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
			case ImogenePackage.CARD_ENTITY__NAME:
				return getName();
			case ImogenePackage.CARD_ENTITY__SHORT_NAME:
				return getShortName();
			case ImogenePackage.CARD_ENTITY__DESCRIPTIONS:
				return getDescriptions();
			case ImogenePackage.CARD_ENTITY__COLUMN_FIELDS:
				return getColumnFields();
			case ImogenePackage.CARD_ENTITY__MAIN_FIELDS:
				return getMainFields();
			case ImogenePackage.CARD_ENTITY__SECONDARY_FIELDS:
				return getSecondaryFields();
			case ImogenePackage.CARD_ENTITY__ICON:
				return getIcon();
			case ImogenePackage.CARD_ENTITY__TOP_LEVEL:
				return isTopLevel();
			case ImogenePackage.CARD_ENTITY__CLIENT_FILTER_FIELDS:
				return getClientFilterFields();
			case ImogenePackage.CARD_ENTITY__GROUPS:
				return getGroups();
			case ImogenePackage.CARD_ENTITY__SORT_FIELDS:
				return getSortFields();
			case ImogenePackage.CARD_ENTITY__ACTOR_FILTER_FIELDS:
				return getActorFilterFields();
			case ImogenePackage.CARD_ENTITY__COLOR:
				return getColor();
			case ImogenePackage.CARD_ENTITY__CLIENT_PERIOD_FILTERABLE:
				return isClientPeriodFilterable();
			case ImogenePackage.CARD_ENTITY__GEOREFERENCED:
				if (resolve) return getGeoreferenced();
				return basicGetGeoreferenced();
			case ImogenePackage.CARD_ENTITY__RDF_SUBJECT:
				if (resolve) return getRdfSubject();
				return basicGetRdfSubject();
			case ImogenePackage.CARD_ENTITY__RDF_PREDICATE:
				return getRdfPredicate();
			case ImogenePackage.CARD_ENTITY__NESTED_FIELDS:
				return getNestedFields();
			case ImogenePackage.CARD_ENTITY__HAS_DYNAMIC_FIELDS:
				return isHasDynamicFields();
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
			case ImogenePackage.CARD_ENTITY__NAME:
				setName((String)newValue);
				return;
			case ImogenePackage.CARD_ENTITY__SHORT_NAME:
				setShortName((String)newValue);
				return;
			case ImogenePackage.CARD_ENTITY__DESCRIPTIONS:
				getDescriptions().clear();
				getDescriptions().addAll((Collection<? extends Description>)newValue);
				return;
			case ImogenePackage.CARD_ENTITY__COLUMN_FIELDS:
				getColumnFields().clear();
				getColumnFields().addAll((Collection<? extends FieldEntity>)newValue);
				return;
			case ImogenePackage.CARD_ENTITY__MAIN_FIELDS:
				getMainFields().clear();
				getMainFields().addAll((Collection<? extends FieldEntity>)newValue);
				return;
			case ImogenePackage.CARD_ENTITY__SECONDARY_FIELDS:
				getSecondaryFields().clear();
				getSecondaryFields().addAll((Collection<? extends FieldEntity>)newValue);
				return;
			case ImogenePackage.CARD_ENTITY__ICON:
				setIcon((String)newValue);
				return;
			case ImogenePackage.CARD_ENTITY__TOP_LEVEL:
				setTopLevel((Boolean)newValue);
				return;
			case ImogenePackage.CARD_ENTITY__CLIENT_FILTER_FIELDS:
				getClientFilterFields().clear();
				getClientFilterFields().addAll((Collection<? extends FieldEntity>)newValue);
				return;
			case ImogenePackage.CARD_ENTITY__GROUPS:
				getGroups().clear();
				getGroups().addAll((Collection<? extends FieldGroup>)newValue);
				return;
			case ImogenePackage.CARD_ENTITY__SORT_FIELDS:
				getSortFields().clear();
				getSortFields().addAll((Collection<? extends FieldEntity>)newValue);
				return;
			case ImogenePackage.CARD_ENTITY__ACTOR_FILTER_FIELDS:
				getActorFilterFields().clear();
				getActorFilterFields().addAll((Collection<? extends ActorFilter>)newValue);
				return;
			case ImogenePackage.CARD_ENTITY__COLOR:
				setColor((String)newValue);
				return;
			case ImogenePackage.CARD_ENTITY__CLIENT_PERIOD_FILTERABLE:
				setClientPeriodFilterable((Boolean)newValue);
				return;
			case ImogenePackage.CARD_ENTITY__GEOREFERENCED:
				setGeoreferenced((GeoField)newValue);
				return;
			case ImogenePackage.CARD_ENTITY__RDF_SUBJECT:
				setRdfSubject((RelationFieldEntity)newValue);
				return;
			case ImogenePackage.CARD_ENTITY__RDF_PREDICATE:
				setRdfPredicate((String)newValue);
				return;
			case ImogenePackage.CARD_ENTITY__NESTED_FIELDS:
				getNestedFields().clear();
				getNestedFields().addAll((Collection<? extends FieldEntity>)newValue);
				return;
			case ImogenePackage.CARD_ENTITY__HAS_DYNAMIC_FIELDS:
				setHasDynamicFields((Boolean)newValue);
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
			case ImogenePackage.CARD_ENTITY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ImogenePackage.CARD_ENTITY__SHORT_NAME:
				setShortName(SHORT_NAME_EDEFAULT);
				return;
			case ImogenePackage.CARD_ENTITY__DESCRIPTIONS:
				getDescriptions().clear();
				return;
			case ImogenePackage.CARD_ENTITY__COLUMN_FIELDS:
				getColumnFields().clear();
				return;
			case ImogenePackage.CARD_ENTITY__MAIN_FIELDS:
				getMainFields().clear();
				return;
			case ImogenePackage.CARD_ENTITY__SECONDARY_FIELDS:
				getSecondaryFields().clear();
				return;
			case ImogenePackage.CARD_ENTITY__ICON:
				setIcon(ICON_EDEFAULT);
				return;
			case ImogenePackage.CARD_ENTITY__TOP_LEVEL:
				setTopLevel(TOP_LEVEL_EDEFAULT);
				return;
			case ImogenePackage.CARD_ENTITY__CLIENT_FILTER_FIELDS:
				getClientFilterFields().clear();
				return;
			case ImogenePackage.CARD_ENTITY__GROUPS:
				getGroups().clear();
				return;
			case ImogenePackage.CARD_ENTITY__SORT_FIELDS:
				getSortFields().clear();
				return;
			case ImogenePackage.CARD_ENTITY__ACTOR_FILTER_FIELDS:
				getActorFilterFields().clear();
				return;
			case ImogenePackage.CARD_ENTITY__COLOR:
				setColor(COLOR_EDEFAULT);
				return;
			case ImogenePackage.CARD_ENTITY__CLIENT_PERIOD_FILTERABLE:
				setClientPeriodFilterable(CLIENT_PERIOD_FILTERABLE_EDEFAULT);
				return;
			case ImogenePackage.CARD_ENTITY__GEOREFERENCED:
				setGeoreferenced((GeoField)null);
				return;
			case ImogenePackage.CARD_ENTITY__RDF_SUBJECT:
				setRdfSubject((RelationFieldEntity)null);
				return;
			case ImogenePackage.CARD_ENTITY__RDF_PREDICATE:
				setRdfPredicate(RDF_PREDICATE_EDEFAULT);
				return;
			case ImogenePackage.CARD_ENTITY__NESTED_FIELDS:
				getNestedFields().clear();
				return;
			case ImogenePackage.CARD_ENTITY__HAS_DYNAMIC_FIELDS:
				setHasDynamicFields(HAS_DYNAMIC_FIELDS_EDEFAULT);
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
			case ImogenePackage.CARD_ENTITY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ImogenePackage.CARD_ENTITY__SHORT_NAME:
				return SHORT_NAME_EDEFAULT == null ? shortName != null : !SHORT_NAME_EDEFAULT.equals(shortName);
			case ImogenePackage.CARD_ENTITY__DESCRIPTIONS:
				return descriptions != null && !descriptions.isEmpty();
			case ImogenePackage.CARD_ENTITY__COLUMN_FIELDS:
				return columnFields != null && !columnFields.isEmpty();
			case ImogenePackage.CARD_ENTITY__MAIN_FIELDS:
				return mainFields != null && !mainFields.isEmpty();
			case ImogenePackage.CARD_ENTITY__SECONDARY_FIELDS:
				return secondaryFields != null && !secondaryFields.isEmpty();
			case ImogenePackage.CARD_ENTITY__ICON:
				return ICON_EDEFAULT == null ? icon != null : !ICON_EDEFAULT.equals(icon);
			case ImogenePackage.CARD_ENTITY__TOP_LEVEL:
				return topLevel != TOP_LEVEL_EDEFAULT;
			case ImogenePackage.CARD_ENTITY__CLIENT_FILTER_FIELDS:
				return clientFilterFields != null && !clientFilterFields.isEmpty();
			case ImogenePackage.CARD_ENTITY__GROUPS:
				return groups != null && !groups.isEmpty();
			case ImogenePackage.CARD_ENTITY__SORT_FIELDS:
				return sortFields != null && !sortFields.isEmpty();
			case ImogenePackage.CARD_ENTITY__ACTOR_FILTER_FIELDS:
				return actorFilterFields != null && !actorFilterFields.isEmpty();
			case ImogenePackage.CARD_ENTITY__COLOR:
				return COLOR_EDEFAULT == null ? color != null : !COLOR_EDEFAULT.equals(color);
			case ImogenePackage.CARD_ENTITY__CLIENT_PERIOD_FILTERABLE:
				return clientPeriodFilterable != CLIENT_PERIOD_FILTERABLE_EDEFAULT;
			case ImogenePackage.CARD_ENTITY__GEOREFERENCED:
				return georeferenced != null;
			case ImogenePackage.CARD_ENTITY__RDF_SUBJECT:
				return rdfSubject != null;
			case ImogenePackage.CARD_ENTITY__RDF_PREDICATE:
				return RDF_PREDICATE_EDEFAULT == null ? rdfPredicate != null : !RDF_PREDICATE_EDEFAULT.equals(rdfPredicate);
			case ImogenePackage.CARD_ENTITY__NESTED_FIELDS:
				return nestedFields != null && !nestedFields.isEmpty();
			case ImogenePackage.CARD_ENTITY__HAS_DYNAMIC_FIELDS:
				return hasDynamicFields != HAS_DYNAMIC_FIELDS_EDEFAULT;
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
		result.append(" (name: ");
		result.append(name);
		result.append(", shortName: ");
		result.append(shortName);
		result.append(", icon: ");
		result.append(icon);
		result.append(", topLevel: ");
		result.append(topLevel);
		result.append(", color: ");
		result.append(color);
		result.append(", clientPeriodFilterable: ");
		result.append(clientPeriodFilterable);
		result.append(", rdfPredicate: ");
		result.append(rdfPredicate);
		result.append(", hasDynamicFields: ");
		result.append(hasDynamicFields);
		result.append(')');
		return result.toString();
	}

} //CardEntityImpl
