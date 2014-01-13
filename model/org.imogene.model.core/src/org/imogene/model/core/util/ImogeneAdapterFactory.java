/**
 * Medes-IMPS 2007
 *
 * $Id$
 */
package org.imogene.model.core.util;


import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;
import org.imogene.model.core.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.imogene.model.core.ImogenePackage
 * @generated
 */
public class ImogeneAdapterFactory extends AdapterFactoryImpl {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Medes-IMPS 2011";

	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ImogenePackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImogeneAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ImogenePackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ImogeneSwitch<Adapter> modelSwitch =
		new ImogeneSwitch<Adapter>() {
			@Override
			public Adapter caseProject(Project object) {
				return createProjectAdapter();
			}
			@Override
			public Adapter caseCardEntity(CardEntity object) {
				return createCardEntityAdapter();
			}
			@Override
			public Adapter caseFieldGroup(FieldGroup object) {
				return createFieldGroupAdapter();
			}
			@Override
			public Adapter caseFieldEntity(FieldEntity object) {
				return createFieldEntityAdapter();
			}
			@Override
			public Adapter caseBooleanField(BooleanField object) {
				return createBooleanFieldAdapter();
			}
			@Override
			public Adapter caseNumericField(NumericField object) {
				return createNumericFieldAdapter();
			}
			@Override
			public Adapter caseIntegerField(IntegerField object) {
				return createIntegerFieldAdapter();
			}
			@Override
			public Adapter caseFloatField(FloatField object) {
				return createFloatFieldAdapter();
			}
			@Override
			public Adapter caseStringField(StringField object) {
				return createStringFieldAdapter();
			}
			@Override
			public Adapter caseValidationRule(ValidationRule object) {
				return createValidationRuleAdapter();
			}
			@Override
			public Adapter caseAddressField(AddressField object) {
				return createAddressFieldAdapter();
			}
			@Override
			public Adapter caseEmailField(EmailField object) {
				return createEmailFieldAdapter();
			}
			@Override
			public Adapter caseGeoField(GeoField object) {
				return createGeoFieldAdapter();
			}
			@Override
			public Adapter caseTextField(TextField object) {
				return createTextFieldAdapter();
			}
			@Override
			public Adapter caseTextAreaField(TextAreaField object) {
				return createTextAreaFieldAdapter();
			}
			@Override
			public Adapter caseBarcodeField(BarcodeField object) {
				return createBarcodeFieldAdapter();
			}
			@Override
			public Adapter casePhoneField(PhoneField object) {
				return createPhoneFieldAdapter();
			}
			@Override
			public Adapter caseMediaFileField(MediaFileField object) {
				return createMediaFileFieldAdapter();
			}
			@Override
			public Adapter caseRelationFieldEntity(RelationFieldEntity object) {
				return createRelationFieldEntityAdapter();
			}
			@Override
			public Adapter caseMainRelationFieldEntity(MainRelationFieldEntity object) {
				return createMainRelationFieldEntityAdapter();
			}
			@Override
			public Adapter caseReverseRelationFieldEntity(ReverseRelationFieldEntity object) {
				return createReverseRelationFieldEntityAdapter();
			}
			@Override
			public Adapter caseEnumField(EnumField object) {
				return createEnumFieldAdapter();
			}
			@Override
			public Adapter caseEnumValue(EnumValue object) {
				return createEnumValueAdapter();
			}
			@Override
			public Adapter caseBinaryField(BinaryField object) {
				return createBinaryFieldAdapter();
			}
			@Override
			public Adapter caseVideoField(VideoField object) {
				return createVideoFieldAdapter();
			}
			@Override
			public Adapter casePhotoField(PhotoField object) {
				return createPhotoFieldAdapter();
			}
			@Override
			public Adapter caseSoundField(SoundField object) {
				return createSoundFieldAdapter();
			}
			@Override
			public Adapter caseDatesField(DatesField object) {
				return createDatesFieldAdapter();
			}
			@Override
			public Adapter caseDateField(DateField object) {
				return createDateFieldAdapter();
			}
			@Override
			public Adapter caseDateTimeField(DateTimeField object) {
				return createDateTimeFieldAdapter();
			}
			@Override
			public Adapter caseTimeField(TimeField object) {
				return createTimeFieldAdapter();
			}
			@Override
			public Adapter caseActor(Actor object) {
				return createActorAdapter();
			}
			@Override
			public Adapter caseFilterField(FilterField object) {
				return createFilterFieldAdapter();
			}
			@Override
			public Adapter caseNotificationInfo(NotificationInfo object) {
				return createNotificationInfoAdapter();
			}
			@Override
			public Adapter caseDescription(Description object) {
				return createDescriptionAdapter();
			}
			@Override
			public Adapter caseLanguage(Language object) {
				return createLanguageAdapter();
			}
			@Override
			public Adapter caseFieldDependentVisibility(FieldDependentVisibility object) {
				return createFieldDependentVisibilityAdapter();
			}
			@Override
			public Adapter caseActorFilter(ActorFilter object) {
				return createActorFilterAdapter();
			}
			@Override
			public Adapter caseThema(Thema object) {
				return createThemaAdapter();
			}
			@Override
			public Adapter caseCardEntityUIFormat(CardEntityUIFormat object) {
				return createCardEntityUIFormatAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.CardEntity <em>Card Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.CardEntity
	 * @generated
	 */
	public Adapter createCardEntityAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.Description <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.Description
	 * @generated
	 */
	public Adapter createDescriptionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.FieldEntity <em>Field Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.FieldEntity
	 * @generated
	 */
	public Adapter createFieldEntityAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.NumericField <em>Numeric Field</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.NumericField
	 * @generated
	 */
	public Adapter createNumericFieldAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.StringField <em>String Field</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.StringField
	 * @generated
	 */
	public Adapter createStringFieldAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.MediaFileField <em>Media File Field</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.MediaFileField
	 * @generated
	 */
	public Adapter createMediaFileFieldAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.RelationFieldEntity <em>Relation Field Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.RelationFieldEntity
	 * @generated
	 */
	public Adapter createRelationFieldEntityAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.MainRelationFieldEntity <em>Main Relation Field Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.MainRelationFieldEntity
	 * @generated
	 */
	public Adapter createMainRelationFieldEntityAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.ReverseRelationFieldEntity <em>Reverse Relation Field Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.ReverseRelationFieldEntity
	 * @generated
	 */
	public Adapter createReverseRelationFieldEntityAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.IntegerField <em>Integer Field</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.IntegerField
	 * @generated
	 */
	public Adapter createIntegerFieldAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.FloatField <em>Float Field</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.FloatField
	 * @generated
	 */
	public Adapter createFloatFieldAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.EnumField <em>Enum Field</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.EnumField
	 * @generated
	 */
	public Adapter createEnumFieldAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.EmailField <em>Email Field</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.EmailField
	 * @generated
	 */
	public Adapter createEmailFieldAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.VideoField <em>Video Field</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.VideoField
	 * @generated
	 */
	public Adapter createVideoFieldAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.BinaryField <em>Binary Field</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.BinaryField
	 * @generated
	 */
	public Adapter createBinaryFieldAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.DateField <em>Date Field</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.DateField
	 * @generated
	 */
	public Adapter createDateFieldAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.DateTimeField <em>Date Time Field</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.DateTimeField
	 * @generated
	 */
	public Adapter createDateTimeFieldAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.TimeField <em>Time Field</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.TimeField
	 * @generated
	 */
	public Adapter createTimeFieldAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.Project <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.Project
	 * @generated
	 */
	public Adapter createProjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.DatesField <em>Dates Field</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.DatesField
	 * @generated
	 */
	public Adapter createDatesFieldAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.PhoneField <em>Phone Field</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.PhoneField
	 * @generated
	 */
	public Adapter createPhoneFieldAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.PhotoField <em>Photo Field</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.PhotoField
	 * @generated
	 */
	public Adapter createPhotoFieldAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.SoundField <em>Sound Field</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.SoundField
	 * @generated
	 */
	public Adapter createSoundFieldAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.BooleanField <em>Boolean Field</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.BooleanField
	 * @generated
	 */
	public Adapter createBooleanFieldAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.EnumValue <em>Enum Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.EnumValue
	 * @generated
	 */
	public Adapter createEnumValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.TextField <em>Text Field</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.TextField
	 * @generated
	 */
	public Adapter createTextFieldAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.TextAreaField <em>Text Area Field</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.TextAreaField
	 * @generated
	 */
	public Adapter createTextAreaFieldAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.FieldGroup <em>Field Group</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.FieldGroup
	 * @generated
	 */
	public Adapter createFieldGroupAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.Actor <em>Actor</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.Actor
	 * @generated
	 */
	public Adapter createActorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.NotificationInfo <em>Notification Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.NotificationInfo
	 * @generated
	 */
	public Adapter createNotificationInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.Language <em>Language</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.Language
	 * @generated
	 */
	public Adapter createLanguageAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.FieldDependentVisibility <em>Field Dependent Visibility</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.FieldDependentVisibility
	 * @generated
	 */
	public Adapter createFieldDependentVisibilityAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.ValidationRule <em>Validation Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.ValidationRule
	 * @generated
	 */
	public Adapter createValidationRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.ActorFilter <em>Actor Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.ActorFilter
	 * @generated
	 */
	public Adapter createActorFilterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.FilterField <em>Filter Field</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.FilterField
	 * @generated
	 */
	public Adapter createFilterFieldAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.AddressField <em>Address Field</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.AddressField
	 * @generated
	 */
	public Adapter createAddressFieldAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.GeoField <em>Geo Field</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.GeoField
	 * @generated
	 */
	public Adapter createGeoFieldAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.BarcodeField <em>Barcode Field</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.BarcodeField
	 * @generated
	 */
	public Adapter createBarcodeFieldAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.Thema <em>Thema</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.Thema
	 * @generated
	 */
	public Adapter createThemaAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imogene.model.core.CardEntityUIFormat <em>Card Entity UI Format</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imogene.model.core.CardEntityUIFormat
	 * @generated
	 */
	public Adapter createCardEntityUIFormatAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //ImogeneAdapterFactory
