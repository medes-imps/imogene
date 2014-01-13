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
 * A representation of the model object '<em><b>Field Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.imogene.model.core.FieldGroup#getDescriptions <em>Descriptions</em>}</li>
 *   <li>{@link org.imogene.model.core.FieldGroup#getFields <em>Fields</em>}</li>
 *   <li>{@link org.imogene.model.core.FieldGroup#getParentCard <em>Parent Card</em>}</li>
 *   <li>{@link org.imogene.model.core.FieldGroup#getName <em>Name</em>}</li>
 *   <li>{@link org.imogene.model.core.FieldGroup#getShortName <em>Short Name</em>}</li>
 *   <li>{@link org.imogene.model.core.FieldGroup#getIcon <em>Icon</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.imogene.model.core.ImogenePackage#getFieldGroup()
 * @model
 * @generated
 */
public interface FieldGroup extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Medes-IMPS 2011";

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
	 * @see org.imogene.model.core.ImogenePackage#getFieldGroup_Descriptions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Description> getDescriptions();

	/**
	 * Returns the value of the '<em><b>Fields</b></em>' containment reference list.
	 * The list contents are of type {@link org.imogene.model.core.FieldEntity}.
	 * It is bidirectional and its opposite is '{@link org.imogene.model.core.FieldEntity#getParentGroup <em>Parent Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fields</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fields</em>' containment reference list.
	 * @see org.imogene.model.core.ImogenePackage#getFieldGroup_Fields()
	 * @see org.imogene.model.core.FieldEntity#getParentGroup
	 * @model opposite="parentGroup" containment="true" required="true"
	 * @generated
	 */
	EList<FieldEntity> getFields();

	/**
	 * Returns the value of the '<em><b>Parent Card</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.imogene.model.core.CardEntity#getGroups <em>Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Card</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Card</em>' container reference.
	 * @see #setParentCard(CardEntity)
	 * @see org.imogene.model.core.ImogenePackage#getFieldGroup_ParentCard()
	 * @see org.imogene.model.core.CardEntity#getGroups
	 * @model opposite="groups" required="true" transient="false"
	 * @generated
	 */
	CardEntity getParentCard();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.FieldGroup#getParentCard <em>Parent Card</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Card</em>' container reference.
	 * @see #getParentCard()
	 * @generated
	 */
	void setParentCard(CardEntity value);

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
	 * @see org.imogene.model.core.ImogenePackage#getFieldGroup_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.FieldGroup#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

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
	 * @see org.imogene.model.core.ImogenePackage#getFieldGroup_ShortName()
	 * @model
	 * @generated
	 */
	String getShortName();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.FieldGroup#getShortName <em>Short Name</em>}' attribute.
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
	 * @see org.imogene.model.core.ImogenePackage#getFieldGroup_Icon()
	 * @model
	 * @generated
	 */
	String getIcon();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.FieldGroup#getIcon <em>Icon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Icon</em>' attribute.
	 * @see #getIcon()
	 * @generated
	 */
	void setIcon(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model required="true"
	 * @generated
	 */
	boolean containsOnlyHiddenFields();

} // FieldGroup
