/**
 * Medes-IMPS 2011
 *
 * $Id$
 */
package org.imogene.model.core.impl;

import org.eclipse.emf.ecore.EClass;

import org.imogene.model.core.ImogenePackage;
import org.imogene.model.core.MediaFileField;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Media File Field</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public abstract class MediaFileFieldImpl extends BinaryFieldImpl implements MediaFileField {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Medes-IMPS 2011";

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MediaFileFieldImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImogenePackage.Literals.MEDIA_FILE_FIELD;
	}

} //MediaFileFieldImpl
