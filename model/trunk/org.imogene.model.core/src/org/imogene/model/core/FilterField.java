/**
 * Medes-IMPS 2011
 *
 * $Id$
 */
package org.imogene.model.core;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Filter Field</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.imogene.model.core.FilterField#getParentActor <em>Parent Actor</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.imogene.model.core.ImogenePackage#getFilterField()
 * @model
 * @generated
 */
public interface FilterField extends RelationFieldEntity {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Medes-IMPS 2011";

	/**
	 * Returns the value of the '<em><b>Parent Actor</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.imogene.model.core.Actor#getFilters <em>Filters</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Actor</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Actor</em>' container reference.
	 * @see #setParentActor(Actor)
	 * @see org.imogene.model.core.ImogenePackage#getFilterField_ParentActor()
	 * @see org.imogene.model.core.Actor#getFilters
	 * @model opposite="filters" transient="false"
	 * @generated
	 */
	Actor getParentActor();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.FilterField#getParentActor <em>Parent Actor</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Actor</em>' container reference.
	 * @see #getParentActor()
	 * @generated
	 */
	void setParentActor(Actor value);

} // FilterField
