/**
 * Medes-IMPS 2011
 *
 * $Id$
 */
package org.imogene.model.core;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Main Relation Field Entity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.imogene.model.core.MainRelationFieldEntity#getInverseCardinality <em>Inverse Cardinality</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.imogene.model.core.ImogenePackage#getMainRelationFieldEntity()
 * @model
 * @generated
 */
public interface MainRelationFieldEntity extends RelationFieldEntity {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Medes-IMPS 2011";

	/**
	 * Returns the value of the '<em><b>Inverse Cardinality</b></em>' attribute.
	 * The default value is <code>"-1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inverse Cardinality</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inverse Cardinality</em>' attribute.
	 * @see #setInverseCardinality(int)
	 * @see org.imogene.model.core.ImogenePackage#getMainRelationFieldEntity_InverseCardinality()
	 * @model default="-1"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel Documentation='Set the cardinality of the opposite side of the relation.\nMandatory if the OppositeRelationField is not declared.'"
	 * @generated
	 */
	int getInverseCardinality();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.MainRelationFieldEntity#getInverseCardinality <em>Inverse Cardinality</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inverse Cardinality</em>' attribute.
	 * @see #getInverseCardinality()
	 * @generated
	 */
	void setInverseCardinality(int value);

} // MainRelationFieldEntity
