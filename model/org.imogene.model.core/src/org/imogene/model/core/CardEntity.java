/**
 * Medes-IMPS 2011
 *
 * $Id$
 */
package org.imogene.model.core;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Card Entity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.imogene.model.core.CardEntity#getName <em>Name</em>}</li>
 *   <li>{@link org.imogene.model.core.CardEntity#getShortName <em>Short Name</em>}</li>
 *   <li>{@link org.imogene.model.core.CardEntity#getDescriptions <em>Descriptions</em>}</li>
 *   <li>{@link org.imogene.model.core.CardEntity#getColumnFields <em>Column Fields</em>}</li>
 *   <li>{@link org.imogene.model.core.CardEntity#getMainFields <em>Main Fields</em>}</li>
 *   <li>{@link org.imogene.model.core.CardEntity#getSecondaryFields <em>Secondary Fields</em>}</li>
 *   <li>{@link org.imogene.model.core.CardEntity#getIcon <em>Icon</em>}</li>
 *   <li>{@link org.imogene.model.core.CardEntity#isTopLevel <em>Top Level</em>}</li>
 *   <li>{@link org.imogene.model.core.CardEntity#getClientFilterFields <em>Client Filter Fields</em>}</li>
 *   <li>{@link org.imogene.model.core.CardEntity#getGroups <em>Groups</em>}</li>
 *   <li>{@link org.imogene.model.core.CardEntity#getSortFields <em>Sort Fields</em>}</li>
 *   <li>{@link org.imogene.model.core.CardEntity#getActorFilterFields <em>Actor Filter Fields</em>}</li>
 *   <li>{@link org.imogene.model.core.CardEntity#getColor <em>Color</em>}</li>
 *   <li>{@link org.imogene.model.core.CardEntity#isClientPeriodFilterable <em>Client Period Filterable</em>}</li>
 *   <li>{@link org.imogene.model.core.CardEntity#getGeoreferenced <em>Georeferenced</em>}</li>
 *   <li>{@link org.imogene.model.core.CardEntity#getRdfSubject <em>Rdf Subject</em>}</li>
 *   <li>{@link org.imogene.model.core.CardEntity#getRdfPredicate <em>Rdf Predicate</em>}</li>
 *   <li>{@link org.imogene.model.core.CardEntity#getNestedFields <em>Nested Fields</em>}</li>
 *   <li>{@link org.imogene.model.core.CardEntity#isHasDynamicFields <em>Has Dynamic Fields</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.imogene.model.core.ImogenePackage#getCardEntity()
 * @model
 * @generated
 */
public interface CardEntity extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Medes-IMPS 2011";

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.imogene.model.core.ImogenePackage#getCardEntity_Name()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.CardEntity#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Descriptions</b></em>' containment reference list.
	 * The list contents are of type {@link org.imogene.model.core.Description}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Descriptions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Descriptions</em>' containment reference list.
	 * @see org.imogene.model.core.ImogenePackage#getCardEntity_Descriptions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Description> getDescriptions();

	/**
	 * Returns the value of the '<em><b>Main Fields</b></em>' reference list.
	 * The list contents are of type {@link org.imogene.model.core.FieldEntity}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Les main fields indiquent les champs à utiliser pour representer la fiche. Si on met Nom, Prenom, on aura des cartes présentées sous la forme :  Durand Marcel si on met Prenom,Nom, sous la forme : Marcel Durand.
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Main Fields</em>' reference list.
	 * @see org.imogene.model.core.ImogenePackage#getCardEntity_MainFields()
	 * @model required="true"
	 * @generated
	 */
	EList<FieldEntity> getMainFields();

	/**
	 * Returns the value of the '<em><b>Column Fields</b></em>' reference list.
	 * The list contents are of type {@link org.imogene.model.core.FieldEntity}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Column Fields</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Column Fields</em>' reference list.
	 * @see org.imogene.model.core.ImogenePackage#getCardEntity_ColumnFields()
	 * @model required="true"
	 * @generated
	 */
	EList<FieldEntity> getColumnFields();

	/**
	 * Returns the value of the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Short Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Short Name</em>' attribute.
	 * @see #setShortName(String)
	 * @see org.imogene.model.core.ImogenePackage#getCardEntity_ShortName()
	 * @model required="true"
	 * @generated
	 */
	String getShortName();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.CardEntity#getShortName <em>Short Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Short Name</em>' attribute.
	 * @see #getShortName()
	 * @generated
	 */
	void setShortName(String value);

	/**
	 * Returns the value of the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Icon</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Icon</em>' attribute.
	 * @see #setIcon(String)
	 * @see org.imogene.model.core.ImogenePackage#getCardEntity_Icon()
	 * @model
	 * @generated
	 */
	String getIcon();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.CardEntity#getIcon <em>Icon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Icon</em>' attribute.
	 * @see #getIcon()
	 * @generated
	 */
	void setIcon(String value);

	/**
	 * Returns the value of the '<em><b>Top Level</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * notion de top level card  entity :  on indique au niveau des cards entity, que la carte est accessible dès le menu principal. Cela permet de 'cacher' certaines cartes pour ne les rendre accessibles que par l'édition de la carte parente. 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Top Level</em>' attribute.
	 * @see #setTopLevel(boolean)
	 * @see org.imogene.model.core.ImogenePackage#getCardEntity_TopLevel()
	 * @model default="true"
	 * @generated
	 */
	boolean isTopLevel();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.CardEntity#isTopLevel <em>Top Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Top Level</em>' attribute.
	 * @see #isTopLevel()
	 * @generated
	 */
	void setTopLevel(boolean value);

	/**
	 * Returns the value of the '<em><b>Client Filter Fields</b></em>' reference list.
	 * The list contents are of type {@link org.imogene.model.core.FieldEntity}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Ajout de 'filterFields' permettant d'indiquer quels sont les champs qu'on utilise sur le filtre pour la récupération des fiches lors de la replication. Ces champs vont générer des écrans de preference pour chaque card entity dans l'application device. L'utilisateur pourra ainsi avoir que les fiches de son service, etc...  D'autres champs implicites seront aussi ajoutés dans ce preference dialog (nb max de cartes, date de creation, périodicité, etc...).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Client Filter Fields</em>' reference list.
	 * @see org.imogene.model.core.ImogenePackage#getCardEntity_ClientFilterFields()
	 * @model
	 * @generated
	 */
	EList<FieldEntity> getClientFilterFields();

	/**
	 * Returns the value of the '<em><b>Groups</b></em>' containment reference list.
	 * The list contents are of type {@link org.imogene.model.core.FieldGroup}.
	 * It is bidirectional and its opposite is '{@link org.imogene.model.core.FieldGroup#getParentCard <em>Parent Card</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Groups</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Groups</em>' containment reference list.
	 * @see org.imogene.model.core.ImogenePackage#getCardEntity_Groups()
	 * @see org.imogene.model.core.FieldGroup#getParentCard
	 * @model opposite="parentCard" containment="true" required="true"
	 * @generated
	 */
	EList<FieldGroup> getGroups();

	/**
	 * Returns the value of the '<em><b>Sort Fields</b></em>' reference list.
	 * The list contents are of type {@link org.imogene.model.core.FieldEntity}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Le champs sortField permet d'indiquer quels sont les champs à utiliser pour trier les données qui remontent de la base. Utiliser de préférence des champs simples, et non des references... sinon ca va trier sur les clés...
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Sort Fields</em>' reference list.
	 * @see org.imogene.model.core.ImogenePackage#getCardEntity_SortFields()
	 * @model
	 * @generated
	 */
	EList<FieldEntity> getSortFields();

	/**
	 * Returns the value of the '<em><b>Actor Filter Fields</b></em>' reference list.
	 * The list contents are of type {@link org.imogene.model.core.ActorFilter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Actor Filter Fields</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Actor Filter Fields</em>' reference list.
	 * @see org.imogene.model.core.ImogenePackage#getCardEntity_ActorFilterFields()
	 * @model
	 * @generated
	 */
	EList<ActorFilter> getActorFilterFields();

	/**
	 * Returns the value of the '<em><b>Secondary Fields</b></em>' reference list.
	 * The list contents are of type {@link org.imogene.model.core.FieldEntity}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Secondary Fields</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Secondary Fields</em>' reference list.
	 * @see org.imogene.model.core.ImogenePackage#getCardEntity_SecondaryFields()
	 * @model
	 * @generated
	 */
	EList<FieldEntity> getSecondaryFields();

	/**
	 * Returns the value of the '<em><b>Color</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Color</em>' attribute.
	 * @see #setColor(String)
	 * @see org.imogene.model.core.ImogenePackage#getCardEntity_Color()
	 * @model default=""
	 * @generated
	 */
	String getColor();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.CardEntity#getColor <em>Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Color</em>' attribute.
	 * @see #getColor()
	 * @generated
	 */
	void setColor(String value);

	/**
	 * Returns the value of the '<em><b>Client Period Filterable</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Client Period Filterable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Client Period Filterable</em>' attribute.
	 * @see #setClientPeriodFilterable(boolean)
	 * @see org.imogene.model.core.ImogenePackage#getCardEntity_ClientPeriodFilterable()
	 * @model default="false"
	 * @generated
	 */
	boolean isClientPeriodFilterable();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.CardEntity#isClientPeriodFilterable <em>Client Period Filterable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Client Period Filterable</em>' attribute.
	 * @see #isClientPeriodFilterable()
	 * @generated
	 */
	void setClientPeriodFilterable(boolean value);

	/**
	 * Returns the value of the '<em><b>Georeferenced</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Georeferenced</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Georeferenced</em>' reference.
	 * @see #setGeoreferenced(GeoField)
	 * @see org.imogene.model.core.ImogenePackage#getCardEntity_Georeferenced()
	 * @model
	 * @generated
	 */
	GeoField getGeoreferenced();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.CardEntity#getGeoreferenced <em>Georeferenced</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Georeferenced</em>' reference.
	 * @see #getGeoreferenced()
	 * @generated
	 */
	void setGeoreferenced(GeoField value);

	/**
	 * Returns the value of the '<em><b>Rdf Subject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rdf Subject</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rdf Subject</em>' reference.
	 * @see #setRdfSubject(RelationFieldEntity)
	 * @see org.imogene.model.core.ImogenePackage#getCardEntity_RdfSubject()
	 * @model
	 * @generated
	 */
	RelationFieldEntity getRdfSubject();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.CardEntity#getRdfSubject <em>Rdf Subject</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rdf Subject</em>' reference.
	 * @see #getRdfSubject()
	 * @generated
	 */
	void setRdfSubject(RelationFieldEntity value);

	/**
	 * Returns the value of the '<em><b>Rdf Predicate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rdf Predicate</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rdf Predicate</em>' attribute.
	 * @see #setRdfPredicate(String)
	 * @see org.imogene.model.core.ImogenePackage#getCardEntity_RdfPredicate()
	 * @model
	 * @generated
	 */
	String getRdfPredicate();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.CardEntity#getRdfPredicate <em>Rdf Predicate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rdf Predicate</em>' attribute.
	 * @see #getRdfPredicate()
	 * @generated
	 */
	void setRdfPredicate(String value);

	/**
	 * Returns the value of the '<em><b>Nested Fields</b></em>' reference list.
	 * The list contents are of type {@link org.imogene.model.core.FieldEntity}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nested Fields</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nested Fields</em>' reference list.
	 * @see org.imogene.model.core.ImogenePackage#getCardEntity_NestedFields()
	 * @model
	 * @generated
	 */
	EList<FieldEntity> getNestedFields();

	/**
	 * Returns the value of the '<em><b>Has Dynamic Fields</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * notion de top level card  entity :  on indique au niveau des cards entity, que la carte est accessible dès le menu principal. Cela permet de 'cacher' certaines cartes pour ne les rendre accessibles que par l'édition de la carte parente. 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Has Dynamic Fields</em>' attribute.
	 * @see #setHasDynamicFields(boolean)
	 * @see org.imogene.model.core.ImogenePackage#getCardEntity_HasDynamicFields()
	 * @model default="false"
	 * @generated
	 */
	boolean isHasDynamicFields();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.CardEntity#isHasDynamicFields <em>Has Dynamic Fields</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Has Dynamic Fields</em>' attribute.
	 * @see #isHasDynamicFields()
	 * @generated
	 */
	void setHasDynamicFields(boolean value);

} // CardEntity
