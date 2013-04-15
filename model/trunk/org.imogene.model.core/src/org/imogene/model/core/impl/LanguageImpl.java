/**
 * Medes-IMPS 2011
 *
 * $Id$
 */
package org.imogene.model.core.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.imogene.model.core.ImogenePackage;
import org.imogene.model.core.Language;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Language</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.imogene.model.core.impl.LanguageImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.LanguageImpl#getDateFormat <em>Date Format</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.LanguageImpl#getTimeFormat <em>Time Format</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.LanguageImpl#getIntegerFormat <em>Integer Format</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.LanguageImpl#getFloatFormat <em>Float Format</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.LanguageImpl#getIsoCode <em>Iso Code</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LanguageImpl extends EObjectImpl implements Language {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Medes-IMPS 2011";

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDateFormat() <em>Date Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDateFormat()
	 * @generated
	 * @ordered
	 */
	protected static final String DATE_FORMAT_EDEFAULT = "dd/MM/yyyy";

	/**
	 * The cached value of the '{@link #getDateFormat() <em>Date Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDateFormat()
	 * @generated
	 * @ordered
	 */
	protected String dateFormat = DATE_FORMAT_EDEFAULT;

	/**
	 * The default value of the '{@link #getTimeFormat() <em>Time Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeFormat()
	 * @generated
	 * @ordered
	 */
	protected static final String TIME_FORMAT_EDEFAULT = "HH:mm";

	/**
	 * The cached value of the '{@link #getTimeFormat() <em>Time Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeFormat()
	 * @generated
	 * @ordered
	 */
	protected String timeFormat = TIME_FORMAT_EDEFAULT;

	/**
	 * The default value of the '{@link #getIntegerFormat() <em>Integer Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIntegerFormat()
	 * @generated
	 * @ordered
	 */
	protected static final String INTEGER_FORMAT_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getIntegerFormat() <em>Integer Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIntegerFormat()
	 * @generated
	 * @ordered
	 */
	protected String integerFormat = INTEGER_FORMAT_EDEFAULT;

	/**
	 * The default value of the '{@link #getFloatFormat() <em>Float Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFloatFormat()
	 * @generated
	 * @ordered
	 */
	protected static final String FLOAT_FORMAT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFloatFormat() <em>Float Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFloatFormat()
	 * @generated
	 * @ordered
	 */
	protected String floatFormat = FLOAT_FORMAT_EDEFAULT;

	/**
	 * The default value of the '{@link #getIsoCode() <em>Iso Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIsoCode()
	 * @generated
	 * @ordered
	 */
	protected static final String ISO_CODE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIsoCode() <em>Iso Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIsoCode()
	 * @generated
	 * @ordered
	 */
	protected String isoCode = ISO_CODE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LanguageImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImogenePackage.Literals.LANGUAGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.LANGUAGE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDateFormat() {
		return dateFormat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDateFormat(String newDateFormat) {
		String oldDateFormat = dateFormat;
		dateFormat = newDateFormat;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.LANGUAGE__DATE_FORMAT, oldDateFormat, dateFormat));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTimeFormat() {
		return timeFormat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimeFormat(String newTimeFormat) {
		String oldTimeFormat = timeFormat;
		timeFormat = newTimeFormat;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.LANGUAGE__TIME_FORMAT, oldTimeFormat, timeFormat));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getIntegerFormat() {
		return integerFormat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIntegerFormat(String newIntegerFormat) {
		String oldIntegerFormat = integerFormat;
		integerFormat = newIntegerFormat;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.LANGUAGE__INTEGER_FORMAT, oldIntegerFormat, integerFormat));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFloatFormat() {
		return floatFormat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFloatFormat(String newFloatFormat) {
		String oldFloatFormat = floatFormat;
		floatFormat = newFloatFormat;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.LANGUAGE__FLOAT_FORMAT, oldFloatFormat, floatFormat));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getIsoCode() {
		return isoCode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsoCode(String newIsoCode) {
		String oldIsoCode = isoCode;
		isoCode = newIsoCode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.LANGUAGE__ISO_CODE, oldIsoCode, isoCode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ImogenePackage.LANGUAGE__NAME:
				return getName();
			case ImogenePackage.LANGUAGE__DATE_FORMAT:
				return getDateFormat();
			case ImogenePackage.LANGUAGE__TIME_FORMAT:
				return getTimeFormat();
			case ImogenePackage.LANGUAGE__INTEGER_FORMAT:
				return getIntegerFormat();
			case ImogenePackage.LANGUAGE__FLOAT_FORMAT:
				return getFloatFormat();
			case ImogenePackage.LANGUAGE__ISO_CODE:
				return getIsoCode();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ImogenePackage.LANGUAGE__NAME:
				setName((String)newValue);
				return;
			case ImogenePackage.LANGUAGE__DATE_FORMAT:
				setDateFormat((String)newValue);
				return;
			case ImogenePackage.LANGUAGE__TIME_FORMAT:
				setTimeFormat((String)newValue);
				return;
			case ImogenePackage.LANGUAGE__INTEGER_FORMAT:
				setIntegerFormat((String)newValue);
				return;
			case ImogenePackage.LANGUAGE__FLOAT_FORMAT:
				setFloatFormat((String)newValue);
				return;
			case ImogenePackage.LANGUAGE__ISO_CODE:
				setIsoCode((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ImogenePackage.LANGUAGE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ImogenePackage.LANGUAGE__DATE_FORMAT:
				setDateFormat(DATE_FORMAT_EDEFAULT);
				return;
			case ImogenePackage.LANGUAGE__TIME_FORMAT:
				setTimeFormat(TIME_FORMAT_EDEFAULT);
				return;
			case ImogenePackage.LANGUAGE__INTEGER_FORMAT:
				setIntegerFormat(INTEGER_FORMAT_EDEFAULT);
				return;
			case ImogenePackage.LANGUAGE__FLOAT_FORMAT:
				setFloatFormat(FLOAT_FORMAT_EDEFAULT);
				return;
			case ImogenePackage.LANGUAGE__ISO_CODE:
				setIsoCode(ISO_CODE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ImogenePackage.LANGUAGE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ImogenePackage.LANGUAGE__DATE_FORMAT:
				return DATE_FORMAT_EDEFAULT == null ? dateFormat != null : !DATE_FORMAT_EDEFAULT.equals(dateFormat);
			case ImogenePackage.LANGUAGE__TIME_FORMAT:
				return TIME_FORMAT_EDEFAULT == null ? timeFormat != null : !TIME_FORMAT_EDEFAULT.equals(timeFormat);
			case ImogenePackage.LANGUAGE__INTEGER_FORMAT:
				return INTEGER_FORMAT_EDEFAULT == null ? integerFormat != null : !INTEGER_FORMAT_EDEFAULT.equals(integerFormat);
			case ImogenePackage.LANGUAGE__FLOAT_FORMAT:
				return FLOAT_FORMAT_EDEFAULT == null ? floatFormat != null : !FLOAT_FORMAT_EDEFAULT.equals(floatFormat);
			case ImogenePackage.LANGUAGE__ISO_CODE:
				return ISO_CODE_EDEFAULT == null ? isoCode != null : !ISO_CODE_EDEFAULT.equals(isoCode);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", dateFormat: ");
		result.append(dateFormat);
		result.append(", timeFormat: ");
		result.append(timeFormat);
		result.append(", integerFormat: ");
		result.append(integerFormat);
		result.append(", floatFormat: ");
		result.append(floatFormat);
		result.append(", isoCode: ");
		result.append(isoCode);
		result.append(')');
		return result.toString();
	}

} //LanguageImpl
