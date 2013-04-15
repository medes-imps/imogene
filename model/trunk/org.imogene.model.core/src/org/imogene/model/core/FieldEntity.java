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
 * A representation of the model object '<em><b>Field Entity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.imogene.model.core.FieldEntity#getName <em>Name</em>}</li>
 *   <li>{@link org.imogene.model.core.FieldEntity#getShortName <em>Short Name</em>}</li>
 *   <li>{@link org.imogene.model.core.FieldEntity#getDescriptions <em>Descriptions</em>}</li>
 *   <li>{@link org.imogene.model.core.FieldEntity#getDefaultValue <em>Default Value</em>}</li>
 *   <li>{@link org.imogene.model.core.FieldEntity#isRequired <em>Required</em>}</li>
 *   <li>{@link org.imogene.model.core.FieldEntity#isHidden <em>Hidden</em>}</li>
 *   <li>{@link org.imogene.model.core.FieldEntity#isReadOnly <em>Read Only</em>}</li>
 *   <li>{@link org.imogene.model.core.FieldEntity#getParentGroup <em>Parent Group</em>}</li>
 *   <li>{@link org.imogene.model.core.FieldEntity#getCalculationFunctionName <em>Calculation Function Name</em>}</li>
 *   <li>{@link org.imogene.model.core.FieldEntity#getFieldDependentVisibility <em>Field Dependent Visibility</em>}</li>
 *   <li>{@link org.imogene.model.core.FieldEntity#getRdfPredicate <em>Rdf Predicate</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.imogene.model.core.ImogenePackage#getFieldEntity()
 * @model abstract="true"
 * @generated
 */
public interface FieldEntity extends EObject {
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
	 * @see org.imogene.model.core.ImogenePackage#getFieldEntity_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.FieldEntity#getName <em>Name</em>}' attribute.
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
	 * @see org.imogene.model.core.ImogenePackage#getFieldEntity_ShortName()
	 * @model required="true"
	 * @generated
	 */
	String getShortName();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.FieldEntity#getShortName <em>Short Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Short Name</em>' attribute.
	 * @see #getShortName()
	 * @generated
	 */
	void setShortName(String value);

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
	 * @see org.imogene.model.core.ImogenePackage#getFieldEntity_Descriptions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Description> getDescriptions();

	/**
	 * Returns the value of the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Value</em>' attribute.
	 * @see #setDefaultValue(String)
	 * @see org.imogene.model.core.ImogenePackage#getFieldEntity_DefaultValue()
	 * @model
	 * @generated
	 */
	String getDefaultValue();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.FieldEntity#getDefaultValue <em>Default Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Value</em>' attribute.
	 * @see #getDefaultValue()
	 * @generated
	 */
	void setDefaultValue(String value);

	/**
	 * Returns the value of the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Required</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Required</em>' attribute.
	 * @see #setRequired(boolean)
	 * @see org.imogene.model.core.ImogenePackage#getFieldEntity_Required()
	 * @model required="true"
	 * @generated
	 */
	boolean isRequired();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.FieldEntity#isRequired <em>Required</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Required</em>' attribute.
	 * @see #isRequired()
	 * @generated
	 */
	void setRequired(boolean value);

	/**
	 * Returns the value of the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hidden</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hidden</em>' attribute.
	 * @see #setHidden(boolean)
	 * @see org.imogene.model.core.ImogenePackage#getFieldEntity_Hidden()
	 * @model
	 * @generated
	 */
	boolean isHidden();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.FieldEntity#isHidden <em>Hidden</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hidden</em>' attribute.
	 * @see #isHidden()
	 * @generated
	 */
	void setHidden(boolean value);

	/**
	 * Returns the value of the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Read Only</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Read Only</em>' attribute.
	 * @see #setReadOnly(boolean)
	 * @see org.imogene.model.core.ImogenePackage#getFieldEntity_ReadOnly()
	 * @model
	 * @generated
	 */
	boolean isReadOnly();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.FieldEntity#isReadOnly <em>Read Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Read Only</em>' attribute.
	 * @see #isReadOnly()
	 * @generated
	 */
	void setReadOnly(boolean value);

	/**
	 * Returns the value of the '<em><b>Parent Group</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.imogene.model.core.FieldGroup#getFields <em>Fields</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Group</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Group</em>' container reference.
	 * @see #setParentGroup(FieldGroup)
	 * @see org.imogene.model.core.ImogenePackage#getFieldEntity_ParentGroup()
	 * @see org.imogene.model.core.FieldGroup#getFields
	 * @model opposite="fields" transient="false"
	 * @generated
	 */
	FieldGroup getParentGroup();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.FieldEntity#getParentGroup <em>Parent Group</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Group</em>' container reference.
	 * @see #getParentGroup()
	 * @generated
	 */
	void setParentGroup(FieldGroup value);

	/**
	 * Returns the value of the '<em><b>Calculation Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Calculation Function Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Calculation Function Name</em>' attribute.
	 * @see #setCalculationFunctionName(String)
	 * @see org.imogene.model.core.ImogenePackage#getFieldEntity_CalculationFunctionName()
	 * @model
	 * @generated
	 */
	String getCalculationFunctionName();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.FieldEntity#getCalculationFunctionName <em>Calculation Function Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Calculation Function Name</em>' attribute.
	 * @see #getCalculationFunctionName()
	 * @generated
	 */
	void setCalculationFunctionName(String value);

	/**
	 * Returns the value of the '<em><b>Field Dependent Visibility</b></em>' containment reference list.
	 * The list contents are of type {@link org.imogene.model.core.FieldDependentVisibility}.
	 * It is bidirectional and its opposite is '{@link org.imogene.model.core.FieldDependentVisibility#getParentFieldEntity <em>Parent Field Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If several field dependent visibility rules are defined, all of them will have to be satisfied so that the field gets visible
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Field Dependent Visibility</em>' containment reference list.
	 * @see org.imogene.model.core.ImogenePackage#getFieldEntity_FieldDependentVisibility()
	 * @see org.imogene.model.core.FieldDependentVisibility#getParentFieldEntity
	 * @model opposite="parentFieldEntity" containment="true"
	 * @generated
	 */
	EList<FieldDependentVisibility> getFieldDependentVisibility();

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
	 * @see org.imogene.model.core.ImogenePackage#getFieldEntity_RdfPredicate()
	 * @model
	 * @generated
	 */
	String getRdfPredicate();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.FieldEntity#getRdfPredicate <em>Rdf Predicate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rdf Predicate</em>' attribute.
	 * @see #getRdfPredicate()
	 * @generated
	 */
	void setRdfPredicate(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void validate();
	
	 /**
     * Returns the value of the '<em><b>Card Entity</b></em>' in which the
     * Field is contained (accessible through its FieldGroup). Kept for
     * compatibilities reasons
     * 
     * @generated NOT
     */
    CardEntity getParentCard();

} // FieldEntity
