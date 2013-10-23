/**
 * Medes-IMPS 2011
 *
 * $Id$
 */
package org.imogene.model.core;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Field Dependent Visibility</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Field Dependence enables to define that one field visibility depends on one or several other fields of the same cardentity
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.imogene.model.core.FieldDependentVisibility#getDependencyField <em>Dependency Field</em>}</li>
 *   <li>{@link org.imogene.model.core.FieldDependentVisibility#getDependencyFieldValue <em>Dependency Field Value</em>}</li>
 *   <li>{@link org.imogene.model.core.FieldDependentVisibility#getParentFieldEntity <em>Parent Field Entity</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.imogene.model.core.ImogenePackage#getFieldDependentVisibility()
 * @model
 * @generated
 */
public interface FieldDependentVisibility extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Medes-IMPS 2011";

	/**
	 * Returns the value of the '<em><b>Dependency Field</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dependency Field</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dependency Field</em>' reference.
	 * @see #setDependencyField(FieldEntity)
	 * @see org.imogene.model.core.ImogenePackage#getFieldDependentVisibility_DependencyField()
	 * @model required="true"
	 * @generated
	 */
	FieldEntity getDependencyField();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.FieldDependentVisibility#getDependencyField <em>Dependency Field</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dependency Field</em>' reference.
	 * @see #getDependencyField()
	 * @generated
	 */
	void setDependencyField(FieldEntity value);

	/**
	 * Returns the value of the '<em><b>Dependency Field Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dependency Field Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dependency Field Value</em>' attribute.
	 * @see #setDependencyFieldValue(String)
	 * @see org.imogene.model.core.ImogenePackage#getFieldDependentVisibility_DependencyFieldValue()
	 * @model required="true"
	 * @generated
	 */
	String getDependencyFieldValue();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.FieldDependentVisibility#getDependencyFieldValue <em>Dependency Field Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dependency Field Value</em>' attribute.
	 * @see #getDependencyFieldValue()
	 * @generated
	 */
	void setDependencyFieldValue(String value);

	/**
	 * Returns the value of the '<em><b>Parent Field Entity</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.imogene.model.core.FieldEntity#getFieldDependentVisibility <em>Field Dependent Visibility</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Field Entity</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Field Entity</em>' container reference.
	 * @see #setParentFieldEntity(FieldEntity)
	 * @see org.imogene.model.core.ImogenePackage#getFieldDependentVisibility_ParentFieldEntity()
	 * @see org.imogene.model.core.FieldEntity#getFieldDependentVisibility
	 * @model opposite="fieldDependentVisibility" transient="false"
	 * @generated
	 */
	FieldEntity getParentFieldEntity();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.FieldDependentVisibility#getParentFieldEntity <em>Parent Field Entity</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Field Entity</em>' container reference.
	 * @see #getParentFieldEntity()
	 * @generated
	 */
	void setParentFieldEntity(FieldEntity value);

} // FieldDependentVisibility
