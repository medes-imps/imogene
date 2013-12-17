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
 * A representation of the model object '<em><b>Project</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.imogene.model.core.Project#getName <em>Name</em>}</li>
 *   <li>{@link org.imogene.model.core.Project#getDescription <em>Description</em>}</li>
 *   <li>{@link org.imogene.model.core.Project#getEntities <em>Entities</em>}</li>
 *   <li>{@link org.imogene.model.core.Project#getEntityUIFormats <em>Entity UI Formats</em>}</li>
 *   <li>{@link org.imogene.model.core.Project#getThemas <em>Themas</em>}</li>
 *   <li>{@link org.imogene.model.core.Project#getLanguages <em>Languages</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.imogene.model.core.ImogenePackage#getProject()
 * @model
 * @generated
 */
public interface Project extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Medes-IMPS 2011";

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * The default value is <code>"entities"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.imogene.model.core.ImogenePackage#getProject_Name()
	 * @model default="entities" required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.Project#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' containment reference list.
	 * The list contents are of type {@link org.imogene.model.core.Description}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' containment reference list.
	 * @see org.imogene.model.core.ImogenePackage#getProject_Description()
	 * @model containment="true"
	 * @generated
	 */
	EList<Description> getDescription();

	/**
	 * Returns the value of the '<em><b>Entities</b></em>' containment reference list.
	 * The list contents are of type {@link org.imogene.model.core.CardEntity}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entities</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entities</em>' containment reference list.
	 * @see org.imogene.model.core.ImogenePackage#getProject_Entities()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<CardEntity> getEntities();

	/**
	 * Returns the value of the '<em><b>Entity UI Formats</b></em>' containment reference list.
	 * The list contents are of type {@link org.imogene.model.core.CardEntityUIFormat}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entity UI Formats</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entity UI Formats</em>' containment reference list.
	 * @see org.imogene.model.core.ImogenePackage#getProject_EntityUIFormats()
	 * @model containment="true"
	 * @generated
	 */
	EList<CardEntityUIFormat> getEntityUIFormats();

	/**
	 * Returns the value of the '<em><b>Themas</b></em>' containment reference list.
	 * The list contents are of type {@link org.imogene.model.core.Thema}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Themas</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Themas</em>' containment reference list.
	 * @see org.imogene.model.core.ImogenePackage#getProject_Themas()
	 * @model containment="true"
	 * @generated
	 */
	EList<Thema> getThemas();

	/**
	 * Returns the value of the '<em><b>Languages</b></em>' containment reference list.
	 * The list contents are of type {@link org.imogene.model.core.Language}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Languages</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Languages</em>' containment reference list.
	 * @see org.imogene.model.core.ImogenePackage#getProject_Languages()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<Language> getLanguages();

} // Project
