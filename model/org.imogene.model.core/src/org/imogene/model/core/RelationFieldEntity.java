/**
 * Medes-IMPS 2011
 *
 * $Id$
 */
package org.imogene.model.core;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Relation Field Entity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.imogene.model.core.RelationFieldEntity#getEntity <em>Entity</em>}</li>
 *   <li>{@link org.imogene.model.core.RelationFieldEntity#getCardinality <em>Cardinality</em>}</li>
 *   <li>{@link org.imogene.model.core.RelationFieldEntity#getType <em>Type</em>}</li>
 *   <li>{@link org.imogene.model.core.RelationFieldEntity#getCommonFields <em>Common Fields</em>}</li>
 *   <li>{@link org.imogene.model.core.RelationFieldEntity#getOppositeRelationField <em>Opposite Relation Field</em>}</li>
 *   <li>{@link org.imogene.model.core.RelationFieldEntity#getRelationHierarchicalFilter <em>Relation Hierarchical Filter</em>}</li>
 *   <li>{@link org.imogene.model.core.RelationFieldEntity#isNestedForm <em>Nested Form</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.imogene.model.core.ImogenePackage#getRelationFieldEntity()
 * @model abstract="true"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel Documentation='WARNING DEPRECATED\nThis element will become Abstract soon.\nMainRelationField and ReverseRelationField should be used instead.'"
 * @generated
 */
public interface RelationFieldEntity extends FieldEntity {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Medes-IMPS 2011";

	/**
	 * Returns the value of the '<em><b>Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entity</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entity</em>' reference.
	 * @see #setEntity(CardEntity)
	 * @see org.imogene.model.core.ImogenePackage#getRelationFieldEntity_Entity()
	 * @model required="true"
	 * @generated
	 */
	CardEntity getEntity();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.RelationFieldEntity#getEntity <em>Entity</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entity</em>' reference.
	 * @see #getEntity()
	 * @generated
	 */
	void setEntity(CardEntity value);

	/**
	 * Returns the value of the '<em><b>Cardinality</b></em>' attribute.
	 * The default value is <code>"-1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cardinality</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cardinality</em>' attribute.
	 * @see #setCardinality(int)
	 * @see org.imogene.model.core.ImogenePackage#getRelationFieldEntity_Cardinality()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getCardinality();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.RelationFieldEntity#getCardinality <em>Cardinality</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cardinality</em>' attribute.
	 * @see #getCardinality()
	 * @generated
	 */
	void setCardinality(int value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.imogene.model.core.RelationType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.imogene.model.core.RelationType
	 * @see #setType(RelationType)
	 * @see org.imogene.model.core.ImogenePackage#getRelationFieldEntity_Type()
	 * @model required="true"
	 * @generated
	 */
	RelationType getType();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.RelationFieldEntity#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.imogene.model.core.RelationType
	 * @see #getType()
	 * @generated
	 */
	void setType(RelationType value);

	/**
	 * Returns the value of the '<em><b>Common Fields</b></em>' reference list.
	 * The list contents are of type {@link org.imogene.model.core.RelationFieldEntity}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Common Fields</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Common Fields</em>' reference list.
	 * @see org.imogene.model.core.ImogenePackage#getRelationFieldEntity_CommonFields()
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel Documentation='RelationField that are common bewteen the two entities used of this Relation\r\nShould be configured as follow:\r\n-- Defined in the MainRelationEntity of a Relation\r\n--  The commonField List should reference couples of RelationFields as follows: \r\ncommonfield n (where n is pair) belongs to entity 1 and is associated with commonField n+1 which belongs to entity 2\r\n'"
	 * @generated
	 */
	EList<RelationFieldEntity> getCommonFields();

	/**
	 * Returns the value of the '<em><b>Opposite Relation Field</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Opposite Relation Field</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Opposite Relation Field</em>' reference.
	 * @see #setOppositeRelationField(RelationFieldEntity)
	 * @see org.imogene.model.core.ImogenePackage#getRelationFieldEntity_OppositeRelationField()
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel Documentation='WARNING DEPRECATED\npoint to the reverse RelationField of the other side of the relation.'"
	 * @generated
	 */
	RelationFieldEntity getOppositeRelationField();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.RelationFieldEntity#getOppositeRelationField <em>Opposite Relation Field</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Opposite Relation Field</em>' reference.
	 * @see #getOppositeRelationField()
	 * @generated
	 */
	void setOppositeRelationField(RelationFieldEntity value);

	/**
	 * Returns the value of the '<em><b>Relation Hierarchical Filter</b></em>' reference list.
	 * The list contents are of type {@link org.imogene.model.core.RelationFieldEntity}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Relation Hierarchical Filter</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Relation Hierarchical Filter</em>' reference list.
	 * @see org.imogene.model.core.ImogenePackage#getRelationFieldEntity_RelationHierarchicalFilter()
	 * @model
	 * @generated
	 */
	EList<RelationFieldEntity> getRelationHierarchicalFilter();

	/**
	 * Returns the value of the '<em><b>Nested Form</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nested Form</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nested Form</em>' attribute.
	 * @see #setNestedForm(boolean)
	 * @see org.imogene.model.core.ImogenePackage#getRelationFieldEntity_NestedForm()
	 * @model default="false"
	 * @generated
	 */
	boolean isNestedForm();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.RelationFieldEntity#isNestedForm <em>Nested Form</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Nested Form</em>' attribute.
	 * @see #isNestedForm()
	 * @generated
	 */
	void setNestedForm(boolean value);

} // RelationFieldEntity
