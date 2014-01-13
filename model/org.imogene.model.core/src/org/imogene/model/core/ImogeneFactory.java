/**
 * Medes-IMPS 2011
 *
 * $Id$
 */
package org.imogene.model.core;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.imogene.model.core.ImogenePackage
 * @generated
 */
public interface ImogeneFactory extends EFactory {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Medes-IMPS 2011";

	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ImogeneFactory eINSTANCE = org.imogene.model.core.impl.ImogeneFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Card Entity</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Card Entity</em>'.
	 * @generated
	 */
	CardEntity createCardEntity();

	/**
	 * Returns a new object of class '<em>Description</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Description</em>'.
	 * @generated
	 */
	Description createDescription();

	/**
	 * Returns a new object of class '<em>Main Relation Field Entity</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Main Relation Field Entity</em>'.
	 * @generated
	 */
	MainRelationFieldEntity createMainRelationFieldEntity();

	/**
	 * Returns a new object of class '<em>Reverse Relation Field Entity</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Reverse Relation Field Entity</em>'.
	 * @generated
	 */
	ReverseRelationFieldEntity createReverseRelationFieldEntity();

	/**
	 * Returns a new object of class '<em>Integer Field</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Integer Field</em>'.
	 * @generated
	 */
	IntegerField createIntegerField();

	/**
	 * Returns a new object of class '<em>Float Field</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Float Field</em>'.
	 * @generated
	 */
	FloatField createFloatField();

	/**
	 * Returns a new object of class '<em>Enum Field</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Enum Field</em>'.
	 * @generated
	 */
	EnumField createEnumField();

	/**
	 * Returns a new object of class '<em>Email Field</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Email Field</em>'.
	 * @generated
	 */
	EmailField createEmailField();

	/**
	 * Returns a new object of class '<em>Video Field</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Video Field</em>'.
	 * @generated
	 */
	VideoField createVideoField();

	/**
	 * Returns a new object of class '<em>Binary Field</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Binary Field</em>'.
	 * @generated
	 */
	BinaryField createBinaryField();

	/**
	 * Returns a new object of class '<em>Date Field</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Date Field</em>'.
	 * @generated
	 */
	DateField createDateField();

	/**
	 * Returns a new object of class '<em>Date Time Field</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Date Time Field</em>'.
	 * @generated
	 */
	DateTimeField createDateTimeField();

	/**
	 * Returns a new object of class '<em>Time Field</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Time Field</em>'.
	 * @generated
	 */
	TimeField createTimeField();

	/**
	 * Returns a new object of class '<em>Project</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Project</em>'.
	 * @generated
	 */
	Project createProject();

	/**
	 * Returns a new object of class '<em>Phone Field</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Phone Field</em>'.
	 * @generated
	 */
	PhoneField createPhoneField();

	/**
	 * Returns a new object of class '<em>Photo Field</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Photo Field</em>'.
	 * @generated
	 */
	PhotoField createPhotoField();

	/**
	 * Returns a new object of class '<em>Sound Field</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Sound Field</em>'.
	 * @generated
	 */
	SoundField createSoundField();

	/**
	 * Returns a new object of class '<em>Boolean Field</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Boolean Field</em>'.
	 * @generated
	 */
	BooleanField createBooleanField();

	/**
	 * Returns a new object of class '<em>Enum Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Enum Value</em>'.
	 * @generated
	 */
	EnumValue createEnumValue();

	/**
	 * Returns a new object of class '<em>Text Field</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Text Field</em>'.
	 * @generated
	 */
	TextField createTextField();

	/**
	 * Returns a new object of class '<em>Text Area Field</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Text Area Field</em>'.
	 * @generated
	 */
	TextAreaField createTextAreaField();

	/**
	 * Returns a new object of class '<em>Field Group</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Field Group</em>'.
	 * @generated
	 */
	FieldGroup createFieldGroup();

	/**
	 * Returns a new object of class '<em>Actor</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Actor</em>'.
	 * @generated
	 */
	Actor createActor();

	/**
	 * Returns a new object of class '<em>Notification Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Notification Info</em>'.
	 * @generated
	 */
	NotificationInfo createNotificationInfo();

	/**
	 * Returns a new object of class '<em>Language</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Language</em>'.
	 * @generated
	 */
	Language createLanguage();

	/**
	 * Returns a new object of class '<em>Field Dependent Visibility</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Field Dependent Visibility</em>'.
	 * @generated
	 */
	FieldDependentVisibility createFieldDependentVisibility();

	/**
	 * Returns a new object of class '<em>Validation Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Validation Rule</em>'.
	 * @generated
	 */
	ValidationRule createValidationRule();

	/**
	 * Returns a new object of class '<em>Actor Filter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Actor Filter</em>'.
	 * @generated
	 */
	ActorFilter createActorFilter();

	/**
	 * Returns a new object of class '<em>Filter Field</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Filter Field</em>'.
	 * @generated
	 */
	FilterField createFilterField();

	/**
	 * Returns a new object of class '<em>Address Field</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Address Field</em>'.
	 * @generated
	 */
	AddressField createAddressField();

	/**
	 * Returns a new object of class '<em>Geo Field</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Geo Field</em>'.
	 * @generated
	 */
	GeoField createGeoField();

	/**
	 * Returns a new object of class '<em>Barcode Field</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Barcode Field</em>'.
	 * @generated
	 */
	BarcodeField createBarcodeField();

	/**
	 * Returns a new object of class '<em>Thema</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Thema</em>'.
	 * @generated
	 */
	Thema createThema();

	/**
	 * Returns a new object of class '<em>Card Entity UI Format</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Card Entity UI Format</em>'.
	 * @generated
	 */
	CardEntityUIFormat createCardEntityUIFormat();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ImogenePackage getImogenePackage();

} //ImogeneFactory
