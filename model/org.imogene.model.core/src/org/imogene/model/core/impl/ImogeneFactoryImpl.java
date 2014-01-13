/**
 * Medes-IMPS 2011
 *
 * $Id$
 */
package org.imogene.model.core.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.imogene.model.core.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ImogeneFactoryImpl extends EFactoryImpl implements ImogeneFactory {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Medes-IMPS 2011";

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ImogeneFactory init() {
		try {
			ImogeneFactory theImogeneFactory = (ImogeneFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.i-mogene.org/imogene/1.0"); 
			if (theImogeneFactory != null) {
				return theImogeneFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ImogeneFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImogeneFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ImogenePackage.PROJECT: return createProject();
			case ImogenePackage.CARD_ENTITY: return createCardEntity();
			case ImogenePackage.FIELD_GROUP: return createFieldGroup();
			case ImogenePackage.BOOLEAN_FIELD: return createBooleanField();
			case ImogenePackage.INTEGER_FIELD: return createIntegerField();
			case ImogenePackage.FLOAT_FIELD: return createFloatField();
			case ImogenePackage.VALIDATION_RULE: return createValidationRule();
			case ImogenePackage.ADDRESS_FIELD: return createAddressField();
			case ImogenePackage.EMAIL_FIELD: return createEmailField();
			case ImogenePackage.GEO_FIELD: return createGeoField();
			case ImogenePackage.TEXT_FIELD: return createTextField();
			case ImogenePackage.TEXT_AREA_FIELD: return createTextAreaField();
			case ImogenePackage.BARCODE_FIELD: return createBarcodeField();
			case ImogenePackage.PHONE_FIELD: return createPhoneField();
			case ImogenePackage.MAIN_RELATION_FIELD_ENTITY: return createMainRelationFieldEntity();
			case ImogenePackage.REVERSE_RELATION_FIELD_ENTITY: return createReverseRelationFieldEntity();
			case ImogenePackage.ENUM_FIELD: return createEnumField();
			case ImogenePackage.ENUM_VALUE: return createEnumValue();
			case ImogenePackage.BINARY_FIELD: return createBinaryField();
			case ImogenePackage.VIDEO_FIELD: return createVideoField();
			case ImogenePackage.PHOTO_FIELD: return createPhotoField();
			case ImogenePackage.SOUND_FIELD: return createSoundField();
			case ImogenePackage.DATE_FIELD: return createDateField();
			case ImogenePackage.DATE_TIME_FIELD: return createDateTimeField();
			case ImogenePackage.TIME_FIELD: return createTimeField();
			case ImogenePackage.ACTOR: return createActor();
			case ImogenePackage.FILTER_FIELD: return createFilterField();
			case ImogenePackage.NOTIFICATION_INFO: return createNotificationInfo();
			case ImogenePackage.DESCRIPTION: return createDescription();
			case ImogenePackage.LANGUAGE: return createLanguage();
			case ImogenePackage.FIELD_DEPENDENT_VISIBILITY: return createFieldDependentVisibility();
			case ImogenePackage.ACTOR_FILTER: return createActorFilter();
			case ImogenePackage.THEMA: return createThema();
			case ImogenePackage.CARD_ENTITY_UI_FORMAT: return createCardEntityUIFormat();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case ImogenePackage.GEO_TYPE:
				return createGeoTypeFromString(eDataType, initialValue);
			case ImogenePackage.PHONE_TYPE:
				return createPhoneTypeFromString(eDataType, initialValue);
			case ImogenePackage.RELATION_TYPE:
				return createRelationTypeFromString(eDataType, initialValue);
			case ImogenePackage.NOTIFICATION_METHOD:
				return createNotificationMethodFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case ImogenePackage.GEO_TYPE:
				return convertGeoTypeToString(eDataType, instanceValue);
			case ImogenePackage.PHONE_TYPE:
				return convertPhoneTypeToString(eDataType, instanceValue);
			case ImogenePackage.RELATION_TYPE:
				return convertRelationTypeToString(eDataType, instanceValue);
			case ImogenePackage.NOTIFICATION_METHOD:
				return convertNotificationMethodToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CardEntity createCardEntity() {
		CardEntityImpl cardEntity = new CardEntityImpl();
		return cardEntity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Description createDescription() {
		DescriptionImpl description = new DescriptionImpl();
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MainRelationFieldEntity createMainRelationFieldEntity() {
		MainRelationFieldEntityImpl mainRelationFieldEntity = new MainRelationFieldEntityImpl();
		return mainRelationFieldEntity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReverseRelationFieldEntity createReverseRelationFieldEntity() {
		ReverseRelationFieldEntityImpl reverseRelationFieldEntity = new ReverseRelationFieldEntityImpl();
		return reverseRelationFieldEntity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntegerField createIntegerField() {
		IntegerFieldImpl integerField = new IntegerFieldImpl();
		return integerField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FloatField createFloatField() {
		FloatFieldImpl floatField = new FloatFieldImpl();
		return floatField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumField createEnumField() {
		EnumFieldImpl enumField = new EnumFieldImpl();
		return enumField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EmailField createEmailField() {
		EmailFieldImpl emailField = new EmailFieldImpl();
		return emailField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VideoField createVideoField() {
		VideoFieldImpl videoField = new VideoFieldImpl();
		return videoField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BinaryField createBinaryField() {
		BinaryFieldImpl binaryField = new BinaryFieldImpl();
		return binaryField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DateField createDateField() {
		DateFieldImpl dateField = new DateFieldImpl();
		return dateField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DateTimeField createDateTimeField() {
		DateTimeFieldImpl dateTimeField = new DateTimeFieldImpl();
		return dateTimeField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TimeField createTimeField() {
		TimeFieldImpl timeField = new TimeFieldImpl();
		return timeField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Project createProject() {
		ProjectImpl project = new ProjectImpl();
		return project;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PhoneField createPhoneField() {
		PhoneFieldImpl phoneField = new PhoneFieldImpl();
		return phoneField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PhotoField createPhotoField() {
		PhotoFieldImpl photoField = new PhotoFieldImpl();
		return photoField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SoundField createSoundField() {
		SoundFieldImpl soundField = new SoundFieldImpl();
		return soundField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanField createBooleanField() {
		BooleanFieldImpl booleanField = new BooleanFieldImpl();
		return booleanField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumValue createEnumValue() {
		EnumValueImpl enumValue = new EnumValueImpl();
		return enumValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TextField createTextField() {
		TextFieldImpl textField = new TextFieldImpl();
		return textField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TextAreaField createTextAreaField() {
		TextAreaFieldImpl textAreaField = new TextAreaFieldImpl();
		return textAreaField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FieldGroup createFieldGroup() {
		FieldGroupImpl fieldGroup = new FieldGroupImpl();
		return fieldGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Actor createActor() {
		ActorImpl actor = new ActorImpl();
		return actor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationInfo createNotificationInfo() {
		NotificationInfoImpl notificationInfo = new NotificationInfoImpl();
		return notificationInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Language createLanguage() {
		LanguageImpl language = new LanguageImpl();
		return language;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FieldDependentVisibility createFieldDependentVisibility() {
		FieldDependentVisibilityImpl fieldDependentVisibility = new FieldDependentVisibilityImpl();
		return fieldDependentVisibility;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValidationRule createValidationRule() {
		ValidationRuleImpl validationRule = new ValidationRuleImpl();
		return validationRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActorFilter createActorFilter() {
		ActorFilterImpl actorFilter = new ActorFilterImpl();
		return actorFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FilterField createFilterField() {
		FilterFieldImpl filterField = new FilterFieldImpl();
		return filterField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AddressField createAddressField() {
		AddressFieldImpl addressField = new AddressFieldImpl();
		return addressField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GeoField createGeoField() {
		GeoFieldImpl geoField = new GeoFieldImpl();
		return geoField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BarcodeField createBarcodeField() {
		BarcodeFieldImpl barcodeField = new BarcodeFieldImpl();
		return barcodeField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Thema createThema() {
		ThemaImpl thema = new ThemaImpl();
		return thema;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CardEntityUIFormat createCardEntityUIFormat() {
		CardEntityUIFormatImpl cardEntityUIFormat = new CardEntityUIFormatImpl();
		return cardEntityUIFormat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PhoneType createPhoneTypeFromString(EDataType eDataType, String initialValue) {
		PhoneType result = PhoneType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPhoneTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RelationType createRelationTypeFromString(EDataType eDataType, String initialValue) {
		RelationType result = RelationType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRelationTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationMethod createNotificationMethodFromString(EDataType eDataType, String initialValue) {
		NotificationMethod result = NotificationMethod.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertNotificationMethodToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GeoType createGeoTypeFromString(EDataType eDataType, String initialValue) {
		GeoType result = GeoType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertGeoTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImogenePackage getImogenePackage() {
		return (ImogenePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ImogenePackage getPackage() {
		return ImogenePackage.eINSTANCE;
	}

} //ImogeneFactoryImpl
