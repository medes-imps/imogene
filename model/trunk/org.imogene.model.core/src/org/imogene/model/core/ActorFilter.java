/**
 * Medes-IMPS 2011
 *
 * $Id$
 */
package org.imogene.model.core;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Actor Filter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.imogene.model.core.ActorFilter#getEntityField <em>Entity Field</em>}</li>
 *   <li>{@link org.imogene.model.core.ActorFilter#getActorField <em>Actor Field</em>}</li>
 *   <li>{@link org.imogene.model.core.ActorFilter#isSufficient <em>Sufficient</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.imogene.model.core.ImogenePackage#getActorFilter()
 * @model
 * @generated
 */
public interface ActorFilter extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Medes-IMPS 2011";

	/**
	 * Returns the value of the '<em><b>Entity Field</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entity Field</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entity Field</em>' reference.
	 * @see #setEntityField(RelationFieldEntity)
	 * @see org.imogene.model.core.ImogenePackage#getActorFilter_EntityField()
	 * @model required="true"
	 * @generated
	 */
	RelationFieldEntity getEntityField();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.ActorFilter#getEntityField <em>Entity Field</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entity Field</em>' reference.
	 * @see #getEntityField()
	 * @generated
	 */
	void setEntityField(RelationFieldEntity value);

	/**
	 * Returns the value of the '<em><b>Actor Field</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Actor Field</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Actor Field</em>' reference.
	 * @see #setActorField(FilterField)
	 * @see org.imogene.model.core.ImogenePackage#getActorFilter_ActorField()
	 * @model required="true"
	 * @generated
	 */
	FilterField getActorField();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.ActorFilter#getActorField <em>Actor Field</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Actor Field</em>' reference.
	 * @see #getActorField()
	 * @generated
	 */
	void setActorField(FilterField value);

	/**
	 * Returns the value of the '<em><b>Sufficient</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sufficient</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sufficient</em>' attribute.
	 * @see #setSufficient(boolean)
	 * @see org.imogene.model.core.ImogenePackage#getActorFilter_Sufficient()
	 * @model
	 * @generated
	 */
	boolean isSufficient();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.ActorFilter#isSufficient <em>Sufficient</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sufficient</em>' attribute.
	 * @see #isSufficient()
	 * @generated
	 */
	void setSufficient(boolean value);

} // ActorFilter
