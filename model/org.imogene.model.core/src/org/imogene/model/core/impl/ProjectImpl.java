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

import org.imogene.model.core.CardEntity;
import org.imogene.model.core.CardEntityUIFormat;
import org.imogene.model.core.Description;
import org.imogene.model.core.ImogenePackage;
import org.imogene.model.core.Language;
import org.imogene.model.core.Project;
import org.imogene.model.core.Thema;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Project</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.imogene.model.core.impl.ProjectImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.ProjectImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.ProjectImpl#getEntities <em>Entities</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.ProjectImpl#getEntityUIFormats <em>Entity UI Formats</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.ProjectImpl#getThemas <em>Themas</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.ProjectImpl#getLanguages <em>Languages</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProjectImpl extends EObjectImpl implements Project {
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
	protected static final String NAME_EDEFAULT = "entities";

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
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected EList<Description> description;

	/**
	 * The cached value of the '{@link #getEntities() <em>Entities</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntities()
	 * @generated
	 * @ordered
	 */
	protected EList<CardEntity> entities;

	/**
	 * The cached value of the '{@link #getEntityUIFormats() <em>Entity UI Formats</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntityUIFormats()
	 * @generated
	 * @ordered
	 */
	protected EList<CardEntityUIFormat> entityUIFormats;

	/**
	 * The cached value of the '{@link #getThemas() <em>Themas</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThemas()
	 * @generated
	 * @ordered
	 */
	protected EList<Thema> themas;

	/**
	 * The cached value of the '{@link #getLanguages() <em>Languages</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLanguages()
	 * @generated
	 * @ordered
	 */
	protected EList<Language> languages;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImogenePackage.Literals.PROJECT;
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
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.PROJECT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Description> getDescription() {
		if (description == null) {
			description = new EObjectContainmentEList<Description>(Description.class, this, ImogenePackage.PROJECT__DESCRIPTION);
		}
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CardEntity> getEntities() {
		if (entities == null) {
			entities = new EObjectContainmentEList<CardEntity>(CardEntity.class, this, ImogenePackage.PROJECT__ENTITIES);
		}
		return entities;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CardEntityUIFormat> getEntityUIFormats() {
		if (entityUIFormats == null) {
			entityUIFormats = new EObjectContainmentEList<CardEntityUIFormat>(CardEntityUIFormat.class, this, ImogenePackage.PROJECT__ENTITY_UI_FORMATS);
		}
		return entityUIFormats;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Thema> getThemas() {
		if (themas == null) {
			themas = new EObjectContainmentEList<Thema>(Thema.class, this, ImogenePackage.PROJECT__THEMAS);
		}
		return themas;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Language> getLanguages() {
		if (languages == null) {
			languages = new EObjectContainmentEList<Language>(Language.class, this, ImogenePackage.PROJECT__LANGUAGES);
		}
		return languages;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ImogenePackage.PROJECT__DESCRIPTION:
				return ((InternalEList<?>)getDescription()).basicRemove(otherEnd, msgs);
			case ImogenePackage.PROJECT__ENTITIES:
				return ((InternalEList<?>)getEntities()).basicRemove(otherEnd, msgs);
			case ImogenePackage.PROJECT__ENTITY_UI_FORMATS:
				return ((InternalEList<?>)getEntityUIFormats()).basicRemove(otherEnd, msgs);
			case ImogenePackage.PROJECT__THEMAS:
				return ((InternalEList<?>)getThemas()).basicRemove(otherEnd, msgs);
			case ImogenePackage.PROJECT__LANGUAGES:
				return ((InternalEList<?>)getLanguages()).basicRemove(otherEnd, msgs);
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
			case ImogenePackage.PROJECT__NAME:
				return getName();
			case ImogenePackage.PROJECT__DESCRIPTION:
				return getDescription();
			case ImogenePackage.PROJECT__ENTITIES:
				return getEntities();
			case ImogenePackage.PROJECT__ENTITY_UI_FORMATS:
				return getEntityUIFormats();
			case ImogenePackage.PROJECT__THEMAS:
				return getThemas();
			case ImogenePackage.PROJECT__LANGUAGES:
				return getLanguages();
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
			case ImogenePackage.PROJECT__NAME:
				setName((String)newValue);
				return;
			case ImogenePackage.PROJECT__DESCRIPTION:
				getDescription().clear();
				getDescription().addAll((Collection<? extends Description>)newValue);
				return;
			case ImogenePackage.PROJECT__ENTITIES:
				getEntities().clear();
				getEntities().addAll((Collection<? extends CardEntity>)newValue);
				return;
			case ImogenePackage.PROJECT__ENTITY_UI_FORMATS:
				getEntityUIFormats().clear();
				getEntityUIFormats().addAll((Collection<? extends CardEntityUIFormat>)newValue);
				return;
			case ImogenePackage.PROJECT__THEMAS:
				getThemas().clear();
				getThemas().addAll((Collection<? extends Thema>)newValue);
				return;
			case ImogenePackage.PROJECT__LANGUAGES:
				getLanguages().clear();
				getLanguages().addAll((Collection<? extends Language>)newValue);
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
			case ImogenePackage.PROJECT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ImogenePackage.PROJECT__DESCRIPTION:
				getDescription().clear();
				return;
			case ImogenePackage.PROJECT__ENTITIES:
				getEntities().clear();
				return;
			case ImogenePackage.PROJECT__ENTITY_UI_FORMATS:
				getEntityUIFormats().clear();
				return;
			case ImogenePackage.PROJECT__THEMAS:
				getThemas().clear();
				return;
			case ImogenePackage.PROJECT__LANGUAGES:
				getLanguages().clear();
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
			case ImogenePackage.PROJECT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ImogenePackage.PROJECT__DESCRIPTION:
				return description != null && !description.isEmpty();
			case ImogenePackage.PROJECT__ENTITIES:
				return entities != null && !entities.isEmpty();
			case ImogenePackage.PROJECT__ENTITY_UI_FORMATS:
				return entityUIFormats != null && !entityUIFormats.isEmpty();
			case ImogenePackage.PROJECT__THEMAS:
				return themas != null && !themas.isEmpty();
			case ImogenePackage.PROJECT__LANGUAGES:
				return languages != null && !languages.isEmpty();
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
		result.append(')');
		return result.toString();
	}

} //ProjectImpl
