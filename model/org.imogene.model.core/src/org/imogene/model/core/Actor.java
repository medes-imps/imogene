/**
 * Medes-IMPS 2011
 *
 * $Id$
 */
package org.imogene.model.core;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Actor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Un acteur est une cardEntity, mais a les possiblité d'avoir en plus un login, password, et d'être identifié en tant que propriétaire d'une carte. 
 * Un acteur a la possibilité également d'avoir uniquement certains roles assignables.
 * 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.imogene.model.core.Actor#getNotificationInfos <em>Notification Infos</em>}</li>
 *   <li>{@link org.imogene.model.core.Actor#getAdminFields <em>Admin Fields</em>}</li>
 *   <li>{@link org.imogene.model.core.Actor#getFilters <em>Filters</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.imogene.model.core.ImogenePackage#getActor()
 * @model
 * @generated
 */
public interface Actor extends CardEntity {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Medes-IMPS 2011";

	/**
	 * Returns the value of the '<em><b>Notification Infos</b></em>' containment reference list.
	 * The list contents are of type {@link org.imogene.model.core.NotificationInfo}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Notification Infos</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Notification Infos</em>' containment reference list.
	 * @see org.imogene.model.core.ImogenePackage#getActor_NotificationInfos()
	 * @model containment="true"
	 * @generated
	 */
	EList<NotificationInfo> getNotificationInfos();

	/**
	 * Returns the value of the '<em><b>Admin Fields</b></em>' reference list.
	 * The list contents are of type {@link org.imogene.model.core.FieldEntity}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * adminFields are all fields that can be used for user administration. It can refer for instance, name, firstname, etc... These fields are then added to the strutural and implicit administration fields such as login, password, roles, delegator...
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Admin Fields</em>' reference list.
	 * @see org.imogene.model.core.ImogenePackage#getActor_AdminFields()
	 * @model required="true"
	 * @generated
	 */
	EList<FieldEntity> getAdminFields();

	/**
	 * Returns the value of the '<em><b>Filters</b></em>' containment reference list.
	 * The list contents are of type {@link org.imogene.model.core.FilterField}.
	 * It is bidirectional and its opposite is '{@link org.imogene.model.core.FilterField#getParentActor <em>Parent Actor</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Filters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Filters</em>' containment reference list.
	 * @see org.imogene.model.core.ImogenePackage#getActor_Filters()
	 * @see org.imogene.model.core.FilterField#getParentActor
	 * @model opposite="parentActor" containment="true"
	 * @generated
	 */
	EList<FilterField> getFilters();

} // Actor
