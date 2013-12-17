/**
 * Medes-IMPS 2007
 *
 * $Id$
 */
package org.imogene.model.core.util;


import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.imogene.model.core.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.imogene.model.core.ImogenePackage
 * @generated
 */
public class ImogeneSwitch<T> extends Switch<T> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Medes-IMPS 2011";

	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ImogenePackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImogeneSwitch() {
		if (modelPackage == null) {
			modelPackage = ImogenePackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case ImogenePackage.PROJECT: {
				Project project = (Project)theEObject;
				T result = caseProject(project);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.CARD_ENTITY: {
				CardEntity cardEntity = (CardEntity)theEObject;
				T result = caseCardEntity(cardEntity);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.FIELD_GROUP: {
				FieldGroup fieldGroup = (FieldGroup)theEObject;
				T result = caseFieldGroup(fieldGroup);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.FIELD_ENTITY: {
				FieldEntity fieldEntity = (FieldEntity)theEObject;
				T result = caseFieldEntity(fieldEntity);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.BOOLEAN_FIELD: {
				BooleanField booleanField = (BooleanField)theEObject;
				T result = caseBooleanField(booleanField);
				if (result == null) result = caseFieldEntity(booleanField);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.NUMERIC_FIELD: {
				NumericField numericField = (NumericField)theEObject;
				T result = caseNumericField(numericField);
				if (result == null) result = caseFieldEntity(numericField);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.INTEGER_FIELD: {
				IntegerField integerField = (IntegerField)theEObject;
				T result = caseIntegerField(integerField);
				if (result == null) result = caseNumericField(integerField);
				if (result == null) result = caseFieldEntity(integerField);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.FLOAT_FIELD: {
				FloatField floatField = (FloatField)theEObject;
				T result = caseFloatField(floatField);
				if (result == null) result = caseNumericField(floatField);
				if (result == null) result = caseFieldEntity(floatField);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.STRING_FIELD: {
				StringField stringField = (StringField)theEObject;
				T result = caseStringField(stringField);
				if (result == null) result = caseFieldEntity(stringField);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.VALIDATION_RULE: {
				ValidationRule validationRule = (ValidationRule)theEObject;
				T result = caseValidationRule(validationRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.ADDRESS_FIELD: {
				AddressField addressField = (AddressField)theEObject;
				T result = caseAddressField(addressField);
				if (result == null) result = caseTextAreaField(addressField);
				if (result == null) result = caseTextField(addressField);
				if (result == null) result = caseStringField(addressField);
				if (result == null) result = caseFieldEntity(addressField);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.EMAIL_FIELD: {
				EmailField emailField = (EmailField)theEObject;
				T result = caseEmailField(emailField);
				if (result == null) result = caseTextField(emailField);
				if (result == null) result = caseStringField(emailField);
				if (result == null) result = caseFieldEntity(emailField);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.GEO_FIELD: {
				GeoField geoField = (GeoField)theEObject;
				T result = caseGeoField(geoField);
				if (result == null) result = caseStringField(geoField);
				if (result == null) result = caseFieldEntity(geoField);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.TEXT_FIELD: {
				TextField textField = (TextField)theEObject;
				T result = caseTextField(textField);
				if (result == null) result = caseStringField(textField);
				if (result == null) result = caseFieldEntity(textField);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.TEXT_AREA_FIELD: {
				TextAreaField textAreaField = (TextAreaField)theEObject;
				T result = caseTextAreaField(textAreaField);
				if (result == null) result = caseTextField(textAreaField);
				if (result == null) result = caseStringField(textAreaField);
				if (result == null) result = caseFieldEntity(textAreaField);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.BARCODE_FIELD: {
				BarcodeField barcodeField = (BarcodeField)theEObject;
				T result = caseBarcodeField(barcodeField);
				if (result == null) result = caseStringField(barcodeField);
				if (result == null) result = caseFieldEntity(barcodeField);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.PHONE_FIELD: {
				PhoneField phoneField = (PhoneField)theEObject;
				T result = casePhoneField(phoneField);
				if (result == null) result = caseTextField(phoneField);
				if (result == null) result = caseStringField(phoneField);
				if (result == null) result = caseFieldEntity(phoneField);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.MEDIA_FILE_FIELD: {
				MediaFileField mediaFileField = (MediaFileField)theEObject;
				T result = caseMediaFileField(mediaFileField);
				if (result == null) result = caseBinaryField(mediaFileField);
				if (result == null) result = caseFieldEntity(mediaFileField);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.RELATION_FIELD_ENTITY: {
				RelationFieldEntity relationFieldEntity = (RelationFieldEntity)theEObject;
				T result = caseRelationFieldEntity(relationFieldEntity);
				if (result == null) result = caseFieldEntity(relationFieldEntity);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.MAIN_RELATION_FIELD_ENTITY: {
				MainRelationFieldEntity mainRelationFieldEntity = (MainRelationFieldEntity)theEObject;
				T result = caseMainRelationFieldEntity(mainRelationFieldEntity);
				if (result == null) result = caseRelationFieldEntity(mainRelationFieldEntity);
				if (result == null) result = caseFieldEntity(mainRelationFieldEntity);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.REVERSE_RELATION_FIELD_ENTITY: {
				ReverseRelationFieldEntity reverseRelationFieldEntity = (ReverseRelationFieldEntity)theEObject;
				T result = caseReverseRelationFieldEntity(reverseRelationFieldEntity);
				if (result == null) result = caseRelationFieldEntity(reverseRelationFieldEntity);
				if (result == null) result = caseFieldEntity(reverseRelationFieldEntity);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.ENUM_FIELD: {
				EnumField enumField = (EnumField)theEObject;
				T result = caseEnumField(enumField);
				if (result == null) result = caseFieldEntity(enumField);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.ENUM_VALUE: {
				EnumValue enumValue = (EnumValue)theEObject;
				T result = caseEnumValue(enumValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.BINARY_FIELD: {
				BinaryField binaryField = (BinaryField)theEObject;
				T result = caseBinaryField(binaryField);
				if (result == null) result = caseFieldEntity(binaryField);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.VIDEO_FIELD: {
				VideoField videoField = (VideoField)theEObject;
				T result = caseVideoField(videoField);
				if (result == null) result = caseMediaFileField(videoField);
				if (result == null) result = caseBinaryField(videoField);
				if (result == null) result = caseFieldEntity(videoField);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.PHOTO_FIELD: {
				PhotoField photoField = (PhotoField)theEObject;
				T result = casePhotoField(photoField);
				if (result == null) result = caseMediaFileField(photoField);
				if (result == null) result = caseBinaryField(photoField);
				if (result == null) result = caseFieldEntity(photoField);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.SOUND_FIELD: {
				SoundField soundField = (SoundField)theEObject;
				T result = caseSoundField(soundField);
				if (result == null) result = caseMediaFileField(soundField);
				if (result == null) result = caseBinaryField(soundField);
				if (result == null) result = caseFieldEntity(soundField);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.DATES_FIELD: {
				DatesField datesField = (DatesField)theEObject;
				T result = caseDatesField(datesField);
				if (result == null) result = caseFieldEntity(datesField);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.DATE_FIELD: {
				DateField dateField = (DateField)theEObject;
				T result = caseDateField(dateField);
				if (result == null) result = caseDatesField(dateField);
				if (result == null) result = caseFieldEntity(dateField);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.DATE_TIME_FIELD: {
				DateTimeField dateTimeField = (DateTimeField)theEObject;
				T result = caseDateTimeField(dateTimeField);
				if (result == null) result = caseDatesField(dateTimeField);
				if (result == null) result = caseFieldEntity(dateTimeField);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.TIME_FIELD: {
				TimeField timeField = (TimeField)theEObject;
				T result = caseTimeField(timeField);
				if (result == null) result = caseDatesField(timeField);
				if (result == null) result = caseFieldEntity(timeField);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.ACTOR: {
				Actor actor = (Actor)theEObject;
				T result = caseActor(actor);
				if (result == null) result = caseCardEntity(actor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.FILTER_FIELD: {
				FilterField filterField = (FilterField)theEObject;
				T result = caseFilterField(filterField);
				if (result == null) result = caseRelationFieldEntity(filterField);
				if (result == null) result = caseFieldEntity(filterField);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.NOTIFICATION_INFO: {
				NotificationInfo notificationInfo = (NotificationInfo)theEObject;
				T result = caseNotificationInfo(notificationInfo);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.DESCRIPTION: {
				Description description = (Description)theEObject;
				T result = caseDescription(description);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.LANGUAGE: {
				Language language = (Language)theEObject;
				T result = caseLanguage(language);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.FIELD_DEPENDENT_VISIBILITY: {
				FieldDependentVisibility fieldDependentVisibility = (FieldDependentVisibility)theEObject;
				T result = caseFieldDependentVisibility(fieldDependentVisibility);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.ACTOR_FILTER: {
				ActorFilter actorFilter = (ActorFilter)theEObject;
				T result = caseActorFilter(actorFilter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.THEMA: {
				Thema thema = (Thema)theEObject;
				T result = caseThema(thema);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImogenePackage.CARD_ENTITY_UI_FORMAT: {
				CardEntityUIFormat cardEntityUIFormat = (CardEntityUIFormat)theEObject;
				T result = caseCardEntityUIFormat(cardEntityUIFormat);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Card Entity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Card Entity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCardEntity(CardEntity object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Description</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Description</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDescription(Description object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Field Entity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Field Entity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFieldEntity(FieldEntity object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Numeric Field</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Numeric Field</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNumericField(NumericField object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String Field</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String Field</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStringField(StringField object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Media File Field</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Media File Field</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMediaFileField(MediaFileField object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Relation Field Entity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Relation Field Entity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRelationFieldEntity(RelationFieldEntity object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Main Relation Field Entity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Main Relation Field Entity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMainRelationFieldEntity(MainRelationFieldEntity object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Reverse Relation Field Entity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Reverse Relation Field Entity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReverseRelationFieldEntity(ReverseRelationFieldEntity object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Integer Field</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Integer Field</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIntegerField(IntegerField object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Float Field</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Float Field</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFloatField(FloatField object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Enum Field</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enum Field</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEnumField(EnumField object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Email Field</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Email Field</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEmailField(EmailField object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Video Field</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Video Field</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVideoField(VideoField object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Binary Field</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Binary Field</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBinaryField(BinaryField object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Date Field</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Date Field</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDateField(DateField object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Date Time Field</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Date Time Field</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDateTimeField(DateTimeField object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Time Field</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Time Field</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTimeField(TimeField object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Project</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Project</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProject(Project object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Dates Field</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dates Field</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDatesField(DatesField object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Phone Field</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Phone Field</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePhoneField(PhoneField object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Photo Field</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Photo Field</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePhotoField(PhotoField object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sound Field</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sound Field</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSoundField(SoundField object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Boolean Field</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Boolean Field</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBooleanField(BooleanField object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Enum Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enum Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEnumValue(EnumValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Text Field</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Text Field</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTextField(TextField object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Text Area Field</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Text Area Field</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTextAreaField(TextAreaField object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Field Group</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Field Group</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFieldGroup(FieldGroup object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Actor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Actor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseActor(Actor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Notification Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Notification Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNotificationInfo(NotificationInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Language</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Language</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLanguage(Language object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Field Dependent Visibility</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Field Dependent Visibility</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFieldDependentVisibility(FieldDependentVisibility object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Validation Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Validation Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseValidationRule(ValidationRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Actor Filter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Actor Filter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseActorFilter(ActorFilter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Filter Field</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Filter Field</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFilterField(FilterField object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Address Field</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Address Field</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAddressField(AddressField object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Geo Field</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Geo Field</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGeoField(GeoField object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Barcode Field</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Barcode Field</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBarcodeField(BarcodeField object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Thema</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Thema</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseThema(Thema object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Card Entity UI Format</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Card Entity UI Format</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCardEntityUIFormat(CardEntityUIFormat object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //ImogeneSwitch
