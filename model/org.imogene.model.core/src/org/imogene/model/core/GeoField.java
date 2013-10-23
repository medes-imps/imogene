/**
 * Medes-IMPS 2011
 *
 * $Id$
 */
package org.imogene.model.core;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Geo Field</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.imogene.model.core.GeoField#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.imogene.model.core.ImogenePackage#getGeoField()
 * @model
 * @generated
 */
public interface GeoField extends StringField {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Medes-IMPS 2011";

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * The literals are from the enumeration {@link org.imogene.model.core.GeoType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.imogene.model.core.GeoType
	 * @see #setType(GeoType)
	 * @see org.imogene.model.core.ImogenePackage#getGeoField_Type()
	 * @model default="" required="true"
	 * @generated
	 */
	GeoType getType();

	/**
	 * Sets the value of the '{@link org.imogene.model.core.GeoField#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.imogene.model.core.GeoType
	 * @see #getType()
	 * @generated
	 */
	void setType(GeoType value);

} // GeoField
