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

import org.imogene.model.core.Description;
import org.imogene.model.core.ImogenePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Description</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.imogene.model.core.impl.DescriptionImpl#getDisplay <em>Display</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.DescriptionImpl#getHelp <em>Help</em>}</li>
 *   <li>{@link org.imogene.model.core.impl.DescriptionImpl#getLocale <em>Locale</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DescriptionImpl extends EObjectImpl implements Description {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Medes-IMPS 2011";

	/**
	 * The default value of the '{@link #getDisplay() <em>Display</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplay()
	 * @generated
	 * @ordered
	 */
	protected static final String DISPLAY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDisplay() <em>Display</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplay()
	 * @generated
	 * @ordered
	 */
	protected String display = DISPLAY_EDEFAULT;

	/**
	 * The default value of the '{@link #getHelp() <em>Help</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHelp()
	 * @generated
	 * @ordered
	 */
	protected static final String HELP_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHelp() <em>Help</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHelp()
	 * @generated
	 * @ordered
	 */
	protected String help = HELP_EDEFAULT;

	/**
	 * The default value of the '{@link #getLocale() <em>Locale</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocale()
	 * @generated
	 * @ordered
	 */
	protected static final String LOCALE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLocale() <em>Locale</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocale()
	 * @generated
	 * @ordered
	 */
	protected String locale = LOCALE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DescriptionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImogenePackage.Literals.DESCRIPTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDisplay() {
		return display;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDisplay(String newDisplay) {
		String oldDisplay = display;
		display = newDisplay;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.DESCRIPTION__DISPLAY, oldDisplay, display));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHelp() {
		return help;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHelp(String newHelp) {
		String oldHelp = help;
		help = newHelp;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.DESCRIPTION__HELP, oldHelp, help));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocale(String newLocale) {
		String oldLocale = locale;
		locale = newLocale;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImogenePackage.DESCRIPTION__LOCALE, oldLocale, locale));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ImogenePackage.DESCRIPTION__DISPLAY:
				return getDisplay();
			case ImogenePackage.DESCRIPTION__HELP:
				return getHelp();
			case ImogenePackage.DESCRIPTION__LOCALE:
				return getLocale();
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
			case ImogenePackage.DESCRIPTION__DISPLAY:
				setDisplay((String)newValue);
				return;
			case ImogenePackage.DESCRIPTION__HELP:
				setHelp((String)newValue);
				return;
			case ImogenePackage.DESCRIPTION__LOCALE:
				setLocale((String)newValue);
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
			case ImogenePackage.DESCRIPTION__DISPLAY:
				setDisplay(DISPLAY_EDEFAULT);
				return;
			case ImogenePackage.DESCRIPTION__HELP:
				setHelp(HELP_EDEFAULT);
				return;
			case ImogenePackage.DESCRIPTION__LOCALE:
				setLocale(LOCALE_EDEFAULT);
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
			case ImogenePackage.DESCRIPTION__DISPLAY:
				return DISPLAY_EDEFAULT == null ? display != null : !DISPLAY_EDEFAULT.equals(display);
			case ImogenePackage.DESCRIPTION__HELP:
				return HELP_EDEFAULT == null ? help != null : !HELP_EDEFAULT.equals(help);
			case ImogenePackage.DESCRIPTION__LOCALE:
				return LOCALE_EDEFAULT == null ? locale != null : !LOCALE_EDEFAULT.equals(locale);
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
		result.append(" (display: ");
		result.append(display);
		result.append(", help: ");
		result.append(help);
		result.append(", locale: ");
		result.append(locale);
		result.append(')');
		return result.toString();
	}

} //DescriptionImpl
