/**
 * Medes-IMPS 2011
 *
 * $Id$
 */
package org.imogene.model.core;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.imogene.model.core.ImogeneFactory
 * @model kind="package"
 * @generated
 */
public interface ImogenePackage extends EPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Medes-IMPS 2011";

	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "core";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.i-mogene.org/imogene/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "imogene";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ImogenePackage eINSTANCE = org.imogene.model.core.impl.ImogenePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.CardEntityImpl <em>Card Entity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.CardEntityImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getCardEntity()
	 * @generated
	 */
	int CARD_ENTITY = 1;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.DescriptionImpl <em>Description</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.DescriptionImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getDescription()
	 * @generated
	 */
	int DESCRIPTION = 34;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.FieldEntityImpl <em>Field Entity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.FieldEntityImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getFieldEntity()
	 * @generated
	 */
	int FIELD_ENTITY = 3;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.NumericFieldImpl <em>Numeric Field</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.NumericFieldImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getNumericField()
	 * @generated
	 */
	int NUMERIC_FIELD = 5;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.StringFieldImpl <em>String Field</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.StringFieldImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getStringField()
	 * @generated
	 */
	int STRING_FIELD = 8;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.BinaryFieldImpl <em>Binary Field</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.BinaryFieldImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getBinaryField()
	 * @generated
	 */
	int BINARY_FIELD = 23;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.MediaFileFieldImpl <em>Media File Field</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.MediaFileFieldImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getMediaFileField()
	 * @generated
	 */
	int MEDIA_FILE_FIELD = 17;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.RelationFieldEntityImpl <em>Relation Field Entity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.RelationFieldEntityImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getRelationFieldEntity()
	 * @generated
	 */
	int RELATION_FIELD_ENTITY = 18;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.MainRelationFieldEntityImpl <em>Main Relation Field Entity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.MainRelationFieldEntityImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getMainRelationFieldEntity()
	 * @generated
	 */
	int MAIN_RELATION_FIELD_ENTITY = 19;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.ReverseRelationFieldEntityImpl <em>Reverse Relation Field Entity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.ReverseRelationFieldEntityImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getReverseRelationFieldEntity()
	 * @generated
	 */
	int REVERSE_RELATION_FIELD_ENTITY = 20;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.IntegerFieldImpl <em>Integer Field</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.IntegerFieldImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getIntegerField()
	 * @generated
	 */
	int INTEGER_FIELD = 6;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.FloatFieldImpl <em>Float Field</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.FloatFieldImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getFloatField()
	 * @generated
	 */
	int FLOAT_FIELD = 7;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.EnumFieldImpl <em>Enum Field</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.EnumFieldImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getEnumField()
	 * @generated
	 */
	int ENUM_FIELD = 21;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.EmailFieldImpl <em>Email Field</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.EmailFieldImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getEmailField()
	 * @generated
	 */
	int EMAIL_FIELD = 11;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.VideoFieldImpl <em>Video Field</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.VideoFieldImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getVideoField()
	 * @generated
	 */
	int VIDEO_FIELD = 24;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.DatesFieldImpl <em>Dates Field</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.DatesFieldImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getDatesField()
	 * @generated
	 */
	int DATES_FIELD = 27;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.DateFieldImpl <em>Date Field</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.DateFieldImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getDateField()
	 * @generated
	 */
	int DATE_FIELD = 28;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.DateTimeFieldImpl <em>Date Time Field</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.DateTimeFieldImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getDateTimeField()
	 * @generated
	 */
	int DATE_TIME_FIELD = 29;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.TimeFieldImpl <em>Time Field</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.TimeFieldImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getTimeField()
	 * @generated
	 */
	int TIME_FIELD = 30;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.ProjectImpl <em>Project</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.ProjectImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getProject()
	 * @generated
	 */
	int PROJECT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__DESCRIPTION = 1;

	/**
	 * The feature id for the '<em><b>Entities</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__ENTITIES = 2;

	/**
	 * The feature id for the '<em><b>Entity UI Formats</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__ENTITY_UI_FORMATS = 3;

	/**
	 * The feature id for the '<em><b>Themas</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__THEMAS = 4;

	/**
	 * The feature id for the '<em><b>Languages</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__LANGUAGES = 5;

	/**
	 * The number of structural features of the '<em>Project</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_FEATURE_COUNT = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARD_ENTITY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARD_ENTITY__SHORT_NAME = 1;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARD_ENTITY__DESCRIPTIONS = 2;

	/**
	 * The feature id for the '<em><b>Column Fields</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARD_ENTITY__COLUMN_FIELDS = 3;

	/**
	 * The feature id for the '<em><b>Main Fields</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARD_ENTITY__MAIN_FIELDS = 4;

	/**
	 * The feature id for the '<em><b>Secondary Fields</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARD_ENTITY__SECONDARY_FIELDS = 5;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARD_ENTITY__ICON = 6;

	/**
	 * The feature id for the '<em><b>Top Level</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARD_ENTITY__TOP_LEVEL = 7;

	/**
	 * The feature id for the '<em><b>Client Filter Fields</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARD_ENTITY__CLIENT_FILTER_FIELDS = 8;

	/**
	 * The feature id for the '<em><b>Groups</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARD_ENTITY__GROUPS = 9;

	/**
	 * The feature id for the '<em><b>Sort Fields</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARD_ENTITY__SORT_FIELDS = 10;

	/**
	 * The feature id for the '<em><b>Actor Filter Fields</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARD_ENTITY__ACTOR_FILTER_FIELDS = 11;

	/**
	 * The feature id for the '<em><b>Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARD_ENTITY__COLOR = 12;

	/**
	 * The feature id for the '<em><b>Client Period Filterable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARD_ENTITY__CLIENT_PERIOD_FILTERABLE = 13;

	/**
	 * The feature id for the '<em><b>Georeferenced</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARD_ENTITY__GEOREFERENCED = 14;

	/**
	 * The feature id for the '<em><b>Rdf Subject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARD_ENTITY__RDF_SUBJECT = 15;

	/**
	 * The feature id for the '<em><b>Rdf Predicate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARD_ENTITY__RDF_PREDICATE = 16;

	/**
	 * The feature id for the '<em><b>Nested Fields</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARD_ENTITY__NESTED_FIELDS = 17;

	/**
	 * The feature id for the '<em><b>Has Dynamic Fields</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARD_ENTITY__HAS_DYNAMIC_FIELDS = 18;

	/**
	 * The number of structural features of the '<em>Card Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARD_ENTITY_FEATURE_COUNT = 19;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.PhoneFieldImpl <em>Phone Field</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.PhoneFieldImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getPhoneField()
	 * @generated
	 */
	int PHONE_FIELD = 16;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.PhotoFieldImpl <em>Photo Field</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.PhotoFieldImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getPhotoField()
	 * @generated
	 */
	int PHOTO_FIELD = 25;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.SoundFieldImpl <em>Sound Field</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.SoundFieldImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getSoundField()
	 * @generated
	 */
	int SOUND_FIELD = 26;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.BooleanFieldImpl <em>Boolean Field</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.BooleanFieldImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getBooleanField()
	 * @generated
	 */
	int BOOLEAN_FIELD = 4;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.EnumValueImpl <em>Enum Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.EnumValueImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getEnumValue()
	 * @generated
	 */
	int ENUM_VALUE = 22;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.TextFieldImpl <em>Text Field</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.TextFieldImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getTextField()
	 * @generated
	 */
	int TEXT_FIELD = 13;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.TextAreaFieldImpl <em>Text Area Field</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.TextAreaFieldImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getTextAreaField()
	 * @generated
	 */
	int TEXT_AREA_FIELD = 14;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.FieldGroupImpl <em>Field Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.FieldGroupImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getFieldGroup()
	 * @generated
	 */
	int FIELD_GROUP = 2;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_GROUP__DESCRIPTIONS = 0;

	/**
	 * The feature id for the '<em><b>Fields</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_GROUP__FIELDS = 1;

	/**
	 * The feature id for the '<em><b>Parent Card</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_GROUP__PARENT_CARD = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_GROUP__NAME = 3;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_GROUP__SHORT_NAME = 4;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_GROUP__ICON = 5;

	/**
	 * The number of structural features of the '<em>Field Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_GROUP_FEATURE_COUNT = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ENTITY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ENTITY__SHORT_NAME = 1;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ENTITY__DESCRIPTIONS = 2;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ENTITY__DEFAULT_VALUE = 3;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ENTITY__REQUIRED = 4;

	/**
	 * The feature id for the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ENTITY__HIDDEN = 5;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ENTITY__READ_ONLY = 6;

	/**
	 * The feature id for the '<em><b>Parent Group</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ENTITY__PARENT_GROUP = 7;

	/**
	 * The feature id for the '<em><b>Calculation Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ENTITY__CALCULATION_FUNCTION_NAME = 8;

	/**
	 * The feature id for the '<em><b>Field Dependent Visibility</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ENTITY__FIELD_DEPENDENT_VISIBILITY = 9;

	/**
	 * The feature id for the '<em><b>Rdf Predicate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ENTITY__RDF_PREDICATE = 10;

	/**
	 * The number of structural features of the '<em>Field Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ENTITY_FEATURE_COUNT = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD__NAME = FIELD_ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD__SHORT_NAME = FIELD_ENTITY__SHORT_NAME;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD__DESCRIPTIONS = FIELD_ENTITY__DESCRIPTIONS;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD__DEFAULT_VALUE = FIELD_ENTITY__DEFAULT_VALUE;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD__REQUIRED = FIELD_ENTITY__REQUIRED;

	/**
	 * The feature id for the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD__HIDDEN = FIELD_ENTITY__HIDDEN;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD__READ_ONLY = FIELD_ENTITY__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Parent Group</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD__PARENT_GROUP = FIELD_ENTITY__PARENT_GROUP;

	/**
	 * The feature id for the '<em><b>Calculation Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD__CALCULATION_FUNCTION_NAME = FIELD_ENTITY__CALCULATION_FUNCTION_NAME;

	/**
	 * The feature id for the '<em><b>Field Dependent Visibility</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD__FIELD_DEPENDENT_VISIBILITY = FIELD_ENTITY__FIELD_DEPENDENT_VISIBILITY;

	/**
	 * The feature id for the '<em><b>Rdf Predicate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD__RDF_PREDICATE = FIELD_ENTITY__RDF_PREDICATE;

	/**
	 * The number of structural features of the '<em>Boolean Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD_FEATURE_COUNT = FIELD_ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_FIELD__NAME = FIELD_ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_FIELD__SHORT_NAME = FIELD_ENTITY__SHORT_NAME;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_FIELD__DESCRIPTIONS = FIELD_ENTITY__DESCRIPTIONS;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_FIELD__DEFAULT_VALUE = FIELD_ENTITY__DEFAULT_VALUE;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_FIELD__REQUIRED = FIELD_ENTITY__REQUIRED;

	/**
	 * The feature id for the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_FIELD__HIDDEN = FIELD_ENTITY__HIDDEN;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_FIELD__READ_ONLY = FIELD_ENTITY__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Parent Group</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_FIELD__PARENT_GROUP = FIELD_ENTITY__PARENT_GROUP;

	/**
	 * The feature id for the '<em><b>Calculation Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_FIELD__CALCULATION_FUNCTION_NAME = FIELD_ENTITY__CALCULATION_FUNCTION_NAME;

	/**
	 * The feature id for the '<em><b>Field Dependent Visibility</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_FIELD__FIELD_DEPENDENT_VISIBILITY = FIELD_ENTITY__FIELD_DEPENDENT_VISIBILITY;

	/**
	 * The feature id for the '<em><b>Rdf Predicate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_FIELD__RDF_PREDICATE = FIELD_ENTITY__RDF_PREDICATE;

	/**
	 * The feature id for the '<em><b>Min</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_FIELD__MIN = FIELD_ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Max</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_FIELD__MAX = FIELD_ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_FIELD__UNIT = FIELD_ENTITY_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Numeric Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_FIELD_FEATURE_COUNT = FIELD_ENTITY_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD__NAME = NUMERIC_FIELD__NAME;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD__SHORT_NAME = NUMERIC_FIELD__SHORT_NAME;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD__DESCRIPTIONS = NUMERIC_FIELD__DESCRIPTIONS;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD__DEFAULT_VALUE = NUMERIC_FIELD__DEFAULT_VALUE;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD__REQUIRED = NUMERIC_FIELD__REQUIRED;

	/**
	 * The feature id for the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD__HIDDEN = NUMERIC_FIELD__HIDDEN;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD__READ_ONLY = NUMERIC_FIELD__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Parent Group</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD__PARENT_GROUP = NUMERIC_FIELD__PARENT_GROUP;

	/**
	 * The feature id for the '<em><b>Calculation Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD__CALCULATION_FUNCTION_NAME = NUMERIC_FIELD__CALCULATION_FUNCTION_NAME;

	/**
	 * The feature id for the '<em><b>Field Dependent Visibility</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD__FIELD_DEPENDENT_VISIBILITY = NUMERIC_FIELD__FIELD_DEPENDENT_VISIBILITY;

	/**
	 * The feature id for the '<em><b>Rdf Predicate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD__RDF_PREDICATE = NUMERIC_FIELD__RDF_PREDICATE;

	/**
	 * The feature id for the '<em><b>Min</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD__MIN = NUMERIC_FIELD__MIN;

	/**
	 * The feature id for the '<em><b>Max</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD__MAX = NUMERIC_FIELD__MAX;

	/**
	 * The feature id for the '<em><b>Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD__UNIT = NUMERIC_FIELD__UNIT;

	/**
	 * The number of structural features of the '<em>Integer Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD_FEATURE_COUNT = NUMERIC_FIELD_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_FIELD__NAME = NUMERIC_FIELD__NAME;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_FIELD__SHORT_NAME = NUMERIC_FIELD__SHORT_NAME;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_FIELD__DESCRIPTIONS = NUMERIC_FIELD__DESCRIPTIONS;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_FIELD__DEFAULT_VALUE = NUMERIC_FIELD__DEFAULT_VALUE;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_FIELD__REQUIRED = NUMERIC_FIELD__REQUIRED;

	/**
	 * The feature id for the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_FIELD__HIDDEN = NUMERIC_FIELD__HIDDEN;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_FIELD__READ_ONLY = NUMERIC_FIELD__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Parent Group</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_FIELD__PARENT_GROUP = NUMERIC_FIELD__PARENT_GROUP;

	/**
	 * The feature id for the '<em><b>Calculation Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_FIELD__CALCULATION_FUNCTION_NAME = NUMERIC_FIELD__CALCULATION_FUNCTION_NAME;

	/**
	 * The feature id for the '<em><b>Field Dependent Visibility</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_FIELD__FIELD_DEPENDENT_VISIBILITY = NUMERIC_FIELD__FIELD_DEPENDENT_VISIBILITY;

	/**
	 * The feature id for the '<em><b>Rdf Predicate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_FIELD__RDF_PREDICATE = NUMERIC_FIELD__RDF_PREDICATE;

	/**
	 * The feature id for the '<em><b>Min</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_FIELD__MIN = NUMERIC_FIELD__MIN;

	/**
	 * The feature id for the '<em><b>Max</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_FIELD__MAX = NUMERIC_FIELD__MAX;

	/**
	 * The feature id for the '<em><b>Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_FIELD__UNIT = NUMERIC_FIELD__UNIT;

	/**
	 * The feature id for the '<em><b>Decimal Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_FIELD__DECIMAL_NUMBER = NUMERIC_FIELD_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Float Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_FIELD_FEATURE_COUNT = NUMERIC_FIELD_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD__NAME = FIELD_ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD__SHORT_NAME = FIELD_ENTITY__SHORT_NAME;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD__DESCRIPTIONS = FIELD_ENTITY__DESCRIPTIONS;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD__DEFAULT_VALUE = FIELD_ENTITY__DEFAULT_VALUE;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD__REQUIRED = FIELD_ENTITY__REQUIRED;

	/**
	 * The feature id for the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD__HIDDEN = FIELD_ENTITY__HIDDEN;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD__READ_ONLY = FIELD_ENTITY__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Parent Group</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD__PARENT_GROUP = FIELD_ENTITY__PARENT_GROUP;

	/**
	 * The feature id for the '<em><b>Calculation Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD__CALCULATION_FUNCTION_NAME = FIELD_ENTITY__CALCULATION_FUNCTION_NAME;

	/**
	 * The feature id for the '<em><b>Field Dependent Visibility</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD__FIELD_DEPENDENT_VISIBILITY = FIELD_ENTITY__FIELD_DEPENDENT_VISIBILITY;

	/**
	 * The feature id for the '<em><b>Rdf Predicate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD__RDF_PREDICATE = FIELD_ENTITY__RDF_PREDICATE;

	/**
	 * The feature id for the '<em><b>Validation Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD__VALIDATION_RULES = FIELD_ENTITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>String Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD_FEATURE_COUNT = FIELD_ENTITY_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.ActorImpl <em>Actor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.ActorImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getActor()
	 * @generated
	 */
	int ACTOR = 31;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.NotificationInfoImpl <em>Notification Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.NotificationInfoImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getNotificationInfo()
	 * @generated
	 */
	int NOTIFICATION_INFO = 33;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.LanguageImpl <em>Language</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.LanguageImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getLanguage()
	 * @generated
	 */
	int LANGUAGE = 35;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.FieldDependentVisibilityImpl <em>Field Dependent Visibility</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.FieldDependentVisibilityImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getFieldDependentVisibility()
	 * @generated
	 */
	int FIELD_DEPENDENT_VISIBILITY = 36;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.ValidationRuleImpl <em>Validation Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.ValidationRuleImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getValidationRule()
	 * @generated
	 */
	int VALIDATION_RULE = 9;

	/**
	 * The feature id for the '<em><b>Validation Regular Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_RULE__VALIDATION_REGULAR_EXPRESSION = 0;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_RULE__DESCRIPTIONS = 1;

	/**
	 * The number of structural features of the '<em>Validation Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_RULE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.ActorFilterImpl <em>Actor Filter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.ActorFilterImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getActorFilter()
	 * @generated
	 */
	int ACTOR_FILTER = 37;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.FilterFieldImpl <em>Filter Field</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.FilterFieldImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getFilterField()
	 * @generated
	 */
	int FILTER_FIELD = 32;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.AddressFieldImpl <em>Address Field</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.AddressFieldImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getAddressField()
	 * @generated
	 */
	int ADDRESS_FIELD = 10;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.GeoFieldImpl <em>Geo Field</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.GeoFieldImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getGeoField()
	 * @generated
	 */
	int GEO_FIELD = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD__NAME = STRING_FIELD__NAME;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD__SHORT_NAME = STRING_FIELD__SHORT_NAME;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD__DESCRIPTIONS = STRING_FIELD__DESCRIPTIONS;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD__DEFAULT_VALUE = STRING_FIELD__DEFAULT_VALUE;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD__REQUIRED = STRING_FIELD__REQUIRED;

	/**
	 * The feature id for the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD__HIDDEN = STRING_FIELD__HIDDEN;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD__READ_ONLY = STRING_FIELD__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Parent Group</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD__PARENT_GROUP = STRING_FIELD__PARENT_GROUP;

	/**
	 * The feature id for the '<em><b>Calculation Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD__CALCULATION_FUNCTION_NAME = STRING_FIELD__CALCULATION_FUNCTION_NAME;

	/**
	 * The feature id for the '<em><b>Field Dependent Visibility</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD__FIELD_DEPENDENT_VISIBILITY = STRING_FIELD__FIELD_DEPENDENT_VISIBILITY;

	/**
	 * The feature id for the '<em><b>Rdf Predicate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD__RDF_PREDICATE = STRING_FIELD__RDF_PREDICATE;

	/**
	 * The feature id for the '<em><b>Validation Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD__VALIDATION_RULES = STRING_FIELD__VALIDATION_RULES;

	/**
	 * The feature id for the '<em><b>Translatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD__TRANSLATABLE = STRING_FIELD_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Text Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD_FEATURE_COUNT = STRING_FIELD_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_AREA_FIELD__NAME = TEXT_FIELD__NAME;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_AREA_FIELD__SHORT_NAME = TEXT_FIELD__SHORT_NAME;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_AREA_FIELD__DESCRIPTIONS = TEXT_FIELD__DESCRIPTIONS;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_AREA_FIELD__DEFAULT_VALUE = TEXT_FIELD__DEFAULT_VALUE;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_AREA_FIELD__REQUIRED = TEXT_FIELD__REQUIRED;

	/**
	 * The feature id for the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_AREA_FIELD__HIDDEN = TEXT_FIELD__HIDDEN;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_AREA_FIELD__READ_ONLY = TEXT_FIELD__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Parent Group</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_AREA_FIELD__PARENT_GROUP = TEXT_FIELD__PARENT_GROUP;

	/**
	 * The feature id for the '<em><b>Calculation Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_AREA_FIELD__CALCULATION_FUNCTION_NAME = TEXT_FIELD__CALCULATION_FUNCTION_NAME;

	/**
	 * The feature id for the '<em><b>Field Dependent Visibility</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_AREA_FIELD__FIELD_DEPENDENT_VISIBILITY = TEXT_FIELD__FIELD_DEPENDENT_VISIBILITY;

	/**
	 * The feature id for the '<em><b>Rdf Predicate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_AREA_FIELD__RDF_PREDICATE = TEXT_FIELD__RDF_PREDICATE;

	/**
	 * The feature id for the '<em><b>Validation Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_AREA_FIELD__VALIDATION_RULES = TEXT_FIELD__VALIDATION_RULES;

	/**
	 * The feature id for the '<em><b>Translatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_AREA_FIELD__TRANSLATABLE = TEXT_FIELD__TRANSLATABLE;

	/**
	 * The number of structural features of the '<em>Text Area Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_AREA_FIELD_FEATURE_COUNT = TEXT_FIELD_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_FIELD__NAME = TEXT_AREA_FIELD__NAME;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_FIELD__SHORT_NAME = TEXT_AREA_FIELD__SHORT_NAME;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_FIELD__DESCRIPTIONS = TEXT_AREA_FIELD__DESCRIPTIONS;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_FIELD__DEFAULT_VALUE = TEXT_AREA_FIELD__DEFAULT_VALUE;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_FIELD__REQUIRED = TEXT_AREA_FIELD__REQUIRED;

	/**
	 * The feature id for the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_FIELD__HIDDEN = TEXT_AREA_FIELD__HIDDEN;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_FIELD__READ_ONLY = TEXT_AREA_FIELD__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Parent Group</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_FIELD__PARENT_GROUP = TEXT_AREA_FIELD__PARENT_GROUP;

	/**
	 * The feature id for the '<em><b>Calculation Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_FIELD__CALCULATION_FUNCTION_NAME = TEXT_AREA_FIELD__CALCULATION_FUNCTION_NAME;

	/**
	 * The feature id for the '<em><b>Field Dependent Visibility</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_FIELD__FIELD_DEPENDENT_VISIBILITY = TEXT_AREA_FIELD__FIELD_DEPENDENT_VISIBILITY;

	/**
	 * The feature id for the '<em><b>Rdf Predicate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_FIELD__RDF_PREDICATE = TEXT_AREA_FIELD__RDF_PREDICATE;

	/**
	 * The feature id for the '<em><b>Validation Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_FIELD__VALIDATION_RULES = TEXT_AREA_FIELD__VALIDATION_RULES;

	/**
	 * The feature id for the '<em><b>Translatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_FIELD__TRANSLATABLE = TEXT_AREA_FIELD__TRANSLATABLE;

	/**
	 * The number of structural features of the '<em>Address Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_FIELD_FEATURE_COUNT = TEXT_AREA_FIELD_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMAIL_FIELD__NAME = TEXT_FIELD__NAME;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMAIL_FIELD__SHORT_NAME = TEXT_FIELD__SHORT_NAME;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMAIL_FIELD__DESCRIPTIONS = TEXT_FIELD__DESCRIPTIONS;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMAIL_FIELD__DEFAULT_VALUE = TEXT_FIELD__DEFAULT_VALUE;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMAIL_FIELD__REQUIRED = TEXT_FIELD__REQUIRED;

	/**
	 * The feature id for the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMAIL_FIELD__HIDDEN = TEXT_FIELD__HIDDEN;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMAIL_FIELD__READ_ONLY = TEXT_FIELD__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Parent Group</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMAIL_FIELD__PARENT_GROUP = TEXT_FIELD__PARENT_GROUP;

	/**
	 * The feature id for the '<em><b>Calculation Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMAIL_FIELD__CALCULATION_FUNCTION_NAME = TEXT_FIELD__CALCULATION_FUNCTION_NAME;

	/**
	 * The feature id for the '<em><b>Field Dependent Visibility</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMAIL_FIELD__FIELD_DEPENDENT_VISIBILITY = TEXT_FIELD__FIELD_DEPENDENT_VISIBILITY;

	/**
	 * The feature id for the '<em><b>Rdf Predicate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMAIL_FIELD__RDF_PREDICATE = TEXT_FIELD__RDF_PREDICATE;

	/**
	 * The feature id for the '<em><b>Validation Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMAIL_FIELD__VALIDATION_RULES = TEXT_FIELD__VALIDATION_RULES;

	/**
	 * The feature id for the '<em><b>Translatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMAIL_FIELD__TRANSLATABLE = TEXT_FIELD__TRANSLATABLE;

	/**
	 * The number of structural features of the '<em>Email Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMAIL_FIELD_FEATURE_COUNT = TEXT_FIELD_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEO_FIELD__NAME = STRING_FIELD__NAME;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEO_FIELD__SHORT_NAME = STRING_FIELD__SHORT_NAME;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEO_FIELD__DESCRIPTIONS = STRING_FIELD__DESCRIPTIONS;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEO_FIELD__DEFAULT_VALUE = STRING_FIELD__DEFAULT_VALUE;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEO_FIELD__REQUIRED = STRING_FIELD__REQUIRED;

	/**
	 * The feature id for the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEO_FIELD__HIDDEN = STRING_FIELD__HIDDEN;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEO_FIELD__READ_ONLY = STRING_FIELD__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Parent Group</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEO_FIELD__PARENT_GROUP = STRING_FIELD__PARENT_GROUP;

	/**
	 * The feature id for the '<em><b>Calculation Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEO_FIELD__CALCULATION_FUNCTION_NAME = STRING_FIELD__CALCULATION_FUNCTION_NAME;

	/**
	 * The feature id for the '<em><b>Field Dependent Visibility</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEO_FIELD__FIELD_DEPENDENT_VISIBILITY = STRING_FIELD__FIELD_DEPENDENT_VISIBILITY;

	/**
	 * The feature id for the '<em><b>Rdf Predicate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEO_FIELD__RDF_PREDICATE = STRING_FIELD__RDF_PREDICATE;

	/**
	 * The feature id for the '<em><b>Validation Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEO_FIELD__VALIDATION_RULES = STRING_FIELD__VALIDATION_RULES;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEO_FIELD__TYPE = STRING_FIELD_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Geo Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEO_FIELD_FEATURE_COUNT = STRING_FIELD_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.BarcodeFieldImpl <em>Barcode Field</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.BarcodeFieldImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getBarcodeField()
	 * @generated
	 */
	int BARCODE_FIELD = 15;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BARCODE_FIELD__NAME = STRING_FIELD__NAME;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BARCODE_FIELD__SHORT_NAME = STRING_FIELD__SHORT_NAME;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BARCODE_FIELD__DESCRIPTIONS = STRING_FIELD__DESCRIPTIONS;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BARCODE_FIELD__DEFAULT_VALUE = STRING_FIELD__DEFAULT_VALUE;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BARCODE_FIELD__REQUIRED = STRING_FIELD__REQUIRED;

	/**
	 * The feature id for the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BARCODE_FIELD__HIDDEN = STRING_FIELD__HIDDEN;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BARCODE_FIELD__READ_ONLY = STRING_FIELD__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Parent Group</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BARCODE_FIELD__PARENT_GROUP = STRING_FIELD__PARENT_GROUP;

	/**
	 * The feature id for the '<em><b>Calculation Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BARCODE_FIELD__CALCULATION_FUNCTION_NAME = STRING_FIELD__CALCULATION_FUNCTION_NAME;

	/**
	 * The feature id for the '<em><b>Field Dependent Visibility</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BARCODE_FIELD__FIELD_DEPENDENT_VISIBILITY = STRING_FIELD__FIELD_DEPENDENT_VISIBILITY;

	/**
	 * The feature id for the '<em><b>Rdf Predicate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BARCODE_FIELD__RDF_PREDICATE = STRING_FIELD__RDF_PREDICATE;

	/**
	 * The feature id for the '<em><b>Validation Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BARCODE_FIELD__VALIDATION_RULES = STRING_FIELD__VALIDATION_RULES;

	/**
	 * The number of structural features of the '<em>Barcode Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BARCODE_FIELD_FEATURE_COUNT = STRING_FIELD_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHONE_FIELD__NAME = TEXT_FIELD__NAME;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHONE_FIELD__SHORT_NAME = TEXT_FIELD__SHORT_NAME;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHONE_FIELD__DESCRIPTIONS = TEXT_FIELD__DESCRIPTIONS;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHONE_FIELD__DEFAULT_VALUE = TEXT_FIELD__DEFAULT_VALUE;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHONE_FIELD__REQUIRED = TEXT_FIELD__REQUIRED;

	/**
	 * The feature id for the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHONE_FIELD__HIDDEN = TEXT_FIELD__HIDDEN;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHONE_FIELD__READ_ONLY = TEXT_FIELD__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Parent Group</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHONE_FIELD__PARENT_GROUP = TEXT_FIELD__PARENT_GROUP;

	/**
	 * The feature id for the '<em><b>Calculation Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHONE_FIELD__CALCULATION_FUNCTION_NAME = TEXT_FIELD__CALCULATION_FUNCTION_NAME;

	/**
	 * The feature id for the '<em><b>Field Dependent Visibility</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHONE_FIELD__FIELD_DEPENDENT_VISIBILITY = TEXT_FIELD__FIELD_DEPENDENT_VISIBILITY;

	/**
	 * The feature id for the '<em><b>Rdf Predicate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHONE_FIELD__RDF_PREDICATE = TEXT_FIELD__RDF_PREDICATE;

	/**
	 * The feature id for the '<em><b>Validation Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHONE_FIELD__VALIDATION_RULES = TEXT_FIELD__VALIDATION_RULES;

	/**
	 * The feature id for the '<em><b>Translatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHONE_FIELD__TRANSLATABLE = TEXT_FIELD__TRANSLATABLE;

	/**
	 * The feature id for the '<em><b>Phone Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHONE_FIELD__PHONE_TYPE = TEXT_FIELD_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Phone Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHONE_FIELD_FEATURE_COUNT = TEXT_FIELD_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_FIELD__NAME = FIELD_ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_FIELD__SHORT_NAME = FIELD_ENTITY__SHORT_NAME;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_FIELD__DESCRIPTIONS = FIELD_ENTITY__DESCRIPTIONS;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_FIELD__DEFAULT_VALUE = FIELD_ENTITY__DEFAULT_VALUE;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_FIELD__REQUIRED = FIELD_ENTITY__REQUIRED;

	/**
	 * The feature id for the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_FIELD__HIDDEN = FIELD_ENTITY__HIDDEN;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_FIELD__READ_ONLY = FIELD_ENTITY__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Parent Group</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_FIELD__PARENT_GROUP = FIELD_ENTITY__PARENT_GROUP;

	/**
	 * The feature id for the '<em><b>Calculation Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_FIELD__CALCULATION_FUNCTION_NAME = FIELD_ENTITY__CALCULATION_FUNCTION_NAME;

	/**
	 * The feature id for the '<em><b>Field Dependent Visibility</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_FIELD__FIELD_DEPENDENT_VISIBILITY = FIELD_ENTITY__FIELD_DEPENDENT_VISIBILITY;

	/**
	 * The feature id for the '<em><b>Rdf Predicate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_FIELD__RDF_PREDICATE = FIELD_ENTITY__RDF_PREDICATE;

	/**
	 * The feature id for the '<em><b>Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_FIELD__CATEGORY = FIELD_ENTITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Binary Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_FIELD_FEATURE_COUNT = FIELD_ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEDIA_FILE_FIELD__NAME = BINARY_FIELD__NAME;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEDIA_FILE_FIELD__SHORT_NAME = BINARY_FIELD__SHORT_NAME;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEDIA_FILE_FIELD__DESCRIPTIONS = BINARY_FIELD__DESCRIPTIONS;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEDIA_FILE_FIELD__DEFAULT_VALUE = BINARY_FIELD__DEFAULT_VALUE;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEDIA_FILE_FIELD__REQUIRED = BINARY_FIELD__REQUIRED;

	/**
	 * The feature id for the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEDIA_FILE_FIELD__HIDDEN = BINARY_FIELD__HIDDEN;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEDIA_FILE_FIELD__READ_ONLY = BINARY_FIELD__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Parent Group</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEDIA_FILE_FIELD__PARENT_GROUP = BINARY_FIELD__PARENT_GROUP;

	/**
	 * The feature id for the '<em><b>Calculation Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEDIA_FILE_FIELD__CALCULATION_FUNCTION_NAME = BINARY_FIELD__CALCULATION_FUNCTION_NAME;

	/**
	 * The feature id for the '<em><b>Field Dependent Visibility</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEDIA_FILE_FIELD__FIELD_DEPENDENT_VISIBILITY = BINARY_FIELD__FIELD_DEPENDENT_VISIBILITY;

	/**
	 * The feature id for the '<em><b>Rdf Predicate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEDIA_FILE_FIELD__RDF_PREDICATE = BINARY_FIELD__RDF_PREDICATE;

	/**
	 * The feature id for the '<em><b>Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEDIA_FILE_FIELD__CATEGORY = BINARY_FIELD__CATEGORY;

	/**
	 * The number of structural features of the '<em>Media File Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEDIA_FILE_FIELD_FEATURE_COUNT = BINARY_FIELD_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION_FIELD_ENTITY__NAME = FIELD_ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION_FIELD_ENTITY__SHORT_NAME = FIELD_ENTITY__SHORT_NAME;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION_FIELD_ENTITY__DESCRIPTIONS = FIELD_ENTITY__DESCRIPTIONS;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION_FIELD_ENTITY__DEFAULT_VALUE = FIELD_ENTITY__DEFAULT_VALUE;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION_FIELD_ENTITY__REQUIRED = FIELD_ENTITY__REQUIRED;

	/**
	 * The feature id for the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION_FIELD_ENTITY__HIDDEN = FIELD_ENTITY__HIDDEN;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION_FIELD_ENTITY__READ_ONLY = FIELD_ENTITY__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Parent Group</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION_FIELD_ENTITY__PARENT_GROUP = FIELD_ENTITY__PARENT_GROUP;

	/**
	 * The feature id for the '<em><b>Calculation Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION_FIELD_ENTITY__CALCULATION_FUNCTION_NAME = FIELD_ENTITY__CALCULATION_FUNCTION_NAME;

	/**
	 * The feature id for the '<em><b>Field Dependent Visibility</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION_FIELD_ENTITY__FIELD_DEPENDENT_VISIBILITY = FIELD_ENTITY__FIELD_DEPENDENT_VISIBILITY;

	/**
	 * The feature id for the '<em><b>Rdf Predicate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION_FIELD_ENTITY__RDF_PREDICATE = FIELD_ENTITY__RDF_PREDICATE;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION_FIELD_ENTITY__ENTITY = FIELD_ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Cardinality</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION_FIELD_ENTITY__CARDINALITY = FIELD_ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION_FIELD_ENTITY__TYPE = FIELD_ENTITY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Common Fields</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION_FIELD_ENTITY__COMMON_FIELDS = FIELD_ENTITY_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Opposite Relation Field</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION_FIELD_ENTITY__OPPOSITE_RELATION_FIELD = FIELD_ENTITY_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Relation Hierarchical Filter</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION_FIELD_ENTITY__RELATION_HIERARCHICAL_FILTER = FIELD_ENTITY_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Nested Form</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION_FIELD_ENTITY__NESTED_FORM = FIELD_ENTITY_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Relation Field Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION_FIELD_ENTITY_FEATURE_COUNT = FIELD_ENTITY_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_RELATION_FIELD_ENTITY__NAME = RELATION_FIELD_ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_RELATION_FIELD_ENTITY__SHORT_NAME = RELATION_FIELD_ENTITY__SHORT_NAME;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_RELATION_FIELD_ENTITY__DESCRIPTIONS = RELATION_FIELD_ENTITY__DESCRIPTIONS;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_RELATION_FIELD_ENTITY__DEFAULT_VALUE = RELATION_FIELD_ENTITY__DEFAULT_VALUE;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_RELATION_FIELD_ENTITY__REQUIRED = RELATION_FIELD_ENTITY__REQUIRED;

	/**
	 * The feature id for the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_RELATION_FIELD_ENTITY__HIDDEN = RELATION_FIELD_ENTITY__HIDDEN;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_RELATION_FIELD_ENTITY__READ_ONLY = RELATION_FIELD_ENTITY__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Parent Group</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_RELATION_FIELD_ENTITY__PARENT_GROUP = RELATION_FIELD_ENTITY__PARENT_GROUP;

	/**
	 * The feature id for the '<em><b>Calculation Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_RELATION_FIELD_ENTITY__CALCULATION_FUNCTION_NAME = RELATION_FIELD_ENTITY__CALCULATION_FUNCTION_NAME;

	/**
	 * The feature id for the '<em><b>Field Dependent Visibility</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_RELATION_FIELD_ENTITY__FIELD_DEPENDENT_VISIBILITY = RELATION_FIELD_ENTITY__FIELD_DEPENDENT_VISIBILITY;

	/**
	 * The feature id for the '<em><b>Rdf Predicate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_RELATION_FIELD_ENTITY__RDF_PREDICATE = RELATION_FIELD_ENTITY__RDF_PREDICATE;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_RELATION_FIELD_ENTITY__ENTITY = RELATION_FIELD_ENTITY__ENTITY;

	/**
	 * The feature id for the '<em><b>Cardinality</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_RELATION_FIELD_ENTITY__CARDINALITY = RELATION_FIELD_ENTITY__CARDINALITY;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_RELATION_FIELD_ENTITY__TYPE = RELATION_FIELD_ENTITY__TYPE;

	/**
	 * The feature id for the '<em><b>Common Fields</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_RELATION_FIELD_ENTITY__COMMON_FIELDS = RELATION_FIELD_ENTITY__COMMON_FIELDS;

	/**
	 * The feature id for the '<em><b>Opposite Relation Field</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_RELATION_FIELD_ENTITY__OPPOSITE_RELATION_FIELD = RELATION_FIELD_ENTITY__OPPOSITE_RELATION_FIELD;

	/**
	 * The feature id for the '<em><b>Relation Hierarchical Filter</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_RELATION_FIELD_ENTITY__RELATION_HIERARCHICAL_FILTER = RELATION_FIELD_ENTITY__RELATION_HIERARCHICAL_FILTER;

	/**
	 * The feature id for the '<em><b>Nested Form</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_RELATION_FIELD_ENTITY__NESTED_FORM = RELATION_FIELD_ENTITY__NESTED_FORM;

	/**
	 * The feature id for the '<em><b>Inverse Cardinality</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_RELATION_FIELD_ENTITY__INVERSE_CARDINALITY = RELATION_FIELD_ENTITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Main Relation Field Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_RELATION_FIELD_ENTITY_FEATURE_COUNT = RELATION_FIELD_ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVERSE_RELATION_FIELD_ENTITY__NAME = RELATION_FIELD_ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVERSE_RELATION_FIELD_ENTITY__SHORT_NAME = RELATION_FIELD_ENTITY__SHORT_NAME;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVERSE_RELATION_FIELD_ENTITY__DESCRIPTIONS = RELATION_FIELD_ENTITY__DESCRIPTIONS;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVERSE_RELATION_FIELD_ENTITY__DEFAULT_VALUE = RELATION_FIELD_ENTITY__DEFAULT_VALUE;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVERSE_RELATION_FIELD_ENTITY__REQUIRED = RELATION_FIELD_ENTITY__REQUIRED;

	/**
	 * The feature id for the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVERSE_RELATION_FIELD_ENTITY__HIDDEN = RELATION_FIELD_ENTITY__HIDDEN;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVERSE_RELATION_FIELD_ENTITY__READ_ONLY = RELATION_FIELD_ENTITY__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Parent Group</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVERSE_RELATION_FIELD_ENTITY__PARENT_GROUP = RELATION_FIELD_ENTITY__PARENT_GROUP;

	/**
	 * The feature id for the '<em><b>Calculation Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVERSE_RELATION_FIELD_ENTITY__CALCULATION_FUNCTION_NAME = RELATION_FIELD_ENTITY__CALCULATION_FUNCTION_NAME;

	/**
	 * The feature id for the '<em><b>Field Dependent Visibility</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVERSE_RELATION_FIELD_ENTITY__FIELD_DEPENDENT_VISIBILITY = RELATION_FIELD_ENTITY__FIELD_DEPENDENT_VISIBILITY;

	/**
	 * The feature id for the '<em><b>Rdf Predicate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVERSE_RELATION_FIELD_ENTITY__RDF_PREDICATE = RELATION_FIELD_ENTITY__RDF_PREDICATE;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVERSE_RELATION_FIELD_ENTITY__ENTITY = RELATION_FIELD_ENTITY__ENTITY;

	/**
	 * The feature id for the '<em><b>Cardinality</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVERSE_RELATION_FIELD_ENTITY__CARDINALITY = RELATION_FIELD_ENTITY__CARDINALITY;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVERSE_RELATION_FIELD_ENTITY__TYPE = RELATION_FIELD_ENTITY__TYPE;

	/**
	 * The feature id for the '<em><b>Common Fields</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVERSE_RELATION_FIELD_ENTITY__COMMON_FIELDS = RELATION_FIELD_ENTITY__COMMON_FIELDS;

	/**
	 * The feature id for the '<em><b>Opposite Relation Field</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVERSE_RELATION_FIELD_ENTITY__OPPOSITE_RELATION_FIELD = RELATION_FIELD_ENTITY__OPPOSITE_RELATION_FIELD;

	/**
	 * The feature id for the '<em><b>Relation Hierarchical Filter</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVERSE_RELATION_FIELD_ENTITY__RELATION_HIERARCHICAL_FILTER = RELATION_FIELD_ENTITY__RELATION_HIERARCHICAL_FILTER;

	/**
	 * The feature id for the '<em><b>Nested Form</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVERSE_RELATION_FIELD_ENTITY__NESTED_FORM = RELATION_FIELD_ENTITY__NESTED_FORM;

	/**
	 * The number of structural features of the '<em>Reverse Relation Field Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVERSE_RELATION_FIELD_ENTITY_FEATURE_COUNT = RELATION_FIELD_ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_FIELD__NAME = FIELD_ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_FIELD__SHORT_NAME = FIELD_ENTITY__SHORT_NAME;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_FIELD__DESCRIPTIONS = FIELD_ENTITY__DESCRIPTIONS;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_FIELD__DEFAULT_VALUE = FIELD_ENTITY__DEFAULT_VALUE;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_FIELD__REQUIRED = FIELD_ENTITY__REQUIRED;

	/**
	 * The feature id for the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_FIELD__HIDDEN = FIELD_ENTITY__HIDDEN;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_FIELD__READ_ONLY = FIELD_ENTITY__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Parent Group</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_FIELD__PARENT_GROUP = FIELD_ENTITY__PARENT_GROUP;

	/**
	 * The feature id for the '<em><b>Calculation Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_FIELD__CALCULATION_FUNCTION_NAME = FIELD_ENTITY__CALCULATION_FUNCTION_NAME;

	/**
	 * The feature id for the '<em><b>Field Dependent Visibility</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_FIELD__FIELD_DEPENDENT_VISIBILITY = FIELD_ENTITY__FIELD_DEPENDENT_VISIBILITY;

	/**
	 * The feature id for the '<em><b>Rdf Predicate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_FIELD__RDF_PREDICATE = FIELD_ENTITY__RDF_PREDICATE;

	/**
	 * The feature id for the '<em><b>Enum Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_FIELD__ENUM_VALUES = FIELD_ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Multiple Selection</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_FIELD__MULTIPLE_SELECTION = FIELD_ENTITY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Enum Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_FIELD_FEATURE_COUNT = FIELD_ENTITY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_VALUE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_VALUE__NAME = 1;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_VALUE__DESCRIPTIONS = 2;

	/**
	 * The number of structural features of the '<em>Enum Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_VALUE_FEATURE_COUNT = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIDEO_FIELD__NAME = MEDIA_FILE_FIELD__NAME;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIDEO_FIELD__SHORT_NAME = MEDIA_FILE_FIELD__SHORT_NAME;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIDEO_FIELD__DESCRIPTIONS = MEDIA_FILE_FIELD__DESCRIPTIONS;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIDEO_FIELD__DEFAULT_VALUE = MEDIA_FILE_FIELD__DEFAULT_VALUE;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIDEO_FIELD__REQUIRED = MEDIA_FILE_FIELD__REQUIRED;

	/**
	 * The feature id for the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIDEO_FIELD__HIDDEN = MEDIA_FILE_FIELD__HIDDEN;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIDEO_FIELD__READ_ONLY = MEDIA_FILE_FIELD__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Parent Group</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIDEO_FIELD__PARENT_GROUP = MEDIA_FILE_FIELD__PARENT_GROUP;

	/**
	 * The feature id for the '<em><b>Calculation Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIDEO_FIELD__CALCULATION_FUNCTION_NAME = MEDIA_FILE_FIELD__CALCULATION_FUNCTION_NAME;

	/**
	 * The feature id for the '<em><b>Field Dependent Visibility</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIDEO_FIELD__FIELD_DEPENDENT_VISIBILITY = MEDIA_FILE_FIELD__FIELD_DEPENDENT_VISIBILITY;

	/**
	 * The feature id for the '<em><b>Rdf Predicate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIDEO_FIELD__RDF_PREDICATE = MEDIA_FILE_FIELD__RDF_PREDICATE;

	/**
	 * The feature id for the '<em><b>Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIDEO_FIELD__CATEGORY = MEDIA_FILE_FIELD__CATEGORY;

	/**
	 * The number of structural features of the '<em>Video Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIDEO_FIELD_FEATURE_COUNT = MEDIA_FILE_FIELD_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHOTO_FIELD__NAME = MEDIA_FILE_FIELD__NAME;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHOTO_FIELD__SHORT_NAME = MEDIA_FILE_FIELD__SHORT_NAME;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHOTO_FIELD__DESCRIPTIONS = MEDIA_FILE_FIELD__DESCRIPTIONS;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHOTO_FIELD__DEFAULT_VALUE = MEDIA_FILE_FIELD__DEFAULT_VALUE;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHOTO_FIELD__REQUIRED = MEDIA_FILE_FIELD__REQUIRED;

	/**
	 * The feature id for the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHOTO_FIELD__HIDDEN = MEDIA_FILE_FIELD__HIDDEN;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHOTO_FIELD__READ_ONLY = MEDIA_FILE_FIELD__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Parent Group</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHOTO_FIELD__PARENT_GROUP = MEDIA_FILE_FIELD__PARENT_GROUP;

	/**
	 * The feature id for the '<em><b>Calculation Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHOTO_FIELD__CALCULATION_FUNCTION_NAME = MEDIA_FILE_FIELD__CALCULATION_FUNCTION_NAME;

	/**
	 * The feature id for the '<em><b>Field Dependent Visibility</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHOTO_FIELD__FIELD_DEPENDENT_VISIBILITY = MEDIA_FILE_FIELD__FIELD_DEPENDENT_VISIBILITY;

	/**
	 * The feature id for the '<em><b>Rdf Predicate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHOTO_FIELD__RDF_PREDICATE = MEDIA_FILE_FIELD__RDF_PREDICATE;

	/**
	 * The feature id for the '<em><b>Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHOTO_FIELD__CATEGORY = MEDIA_FILE_FIELD__CATEGORY;

	/**
	 * The number of structural features of the '<em>Photo Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHOTO_FIELD_FEATURE_COUNT = MEDIA_FILE_FIELD_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOUND_FIELD__NAME = MEDIA_FILE_FIELD__NAME;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOUND_FIELD__SHORT_NAME = MEDIA_FILE_FIELD__SHORT_NAME;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOUND_FIELD__DESCRIPTIONS = MEDIA_FILE_FIELD__DESCRIPTIONS;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOUND_FIELD__DEFAULT_VALUE = MEDIA_FILE_FIELD__DEFAULT_VALUE;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOUND_FIELD__REQUIRED = MEDIA_FILE_FIELD__REQUIRED;

	/**
	 * The feature id for the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOUND_FIELD__HIDDEN = MEDIA_FILE_FIELD__HIDDEN;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOUND_FIELD__READ_ONLY = MEDIA_FILE_FIELD__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Parent Group</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOUND_FIELD__PARENT_GROUP = MEDIA_FILE_FIELD__PARENT_GROUP;

	/**
	 * The feature id for the '<em><b>Calculation Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOUND_FIELD__CALCULATION_FUNCTION_NAME = MEDIA_FILE_FIELD__CALCULATION_FUNCTION_NAME;

	/**
	 * The feature id for the '<em><b>Field Dependent Visibility</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOUND_FIELD__FIELD_DEPENDENT_VISIBILITY = MEDIA_FILE_FIELD__FIELD_DEPENDENT_VISIBILITY;

	/**
	 * The feature id for the '<em><b>Rdf Predicate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOUND_FIELD__RDF_PREDICATE = MEDIA_FILE_FIELD__RDF_PREDICATE;

	/**
	 * The feature id for the '<em><b>Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOUND_FIELD__CATEGORY = MEDIA_FILE_FIELD__CATEGORY;

	/**
	 * The number of structural features of the '<em>Sound Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOUND_FIELD_FEATURE_COUNT = MEDIA_FILE_FIELD_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATES_FIELD__NAME = FIELD_ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATES_FIELD__SHORT_NAME = FIELD_ENTITY__SHORT_NAME;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATES_FIELD__DESCRIPTIONS = FIELD_ENTITY__DESCRIPTIONS;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATES_FIELD__DEFAULT_VALUE = FIELD_ENTITY__DEFAULT_VALUE;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATES_FIELD__REQUIRED = FIELD_ENTITY__REQUIRED;

	/**
	 * The feature id for the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATES_FIELD__HIDDEN = FIELD_ENTITY__HIDDEN;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATES_FIELD__READ_ONLY = FIELD_ENTITY__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Parent Group</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATES_FIELD__PARENT_GROUP = FIELD_ENTITY__PARENT_GROUP;

	/**
	 * The feature id for the '<em><b>Calculation Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATES_FIELD__CALCULATION_FUNCTION_NAME = FIELD_ENTITY__CALCULATION_FUNCTION_NAME;

	/**
	 * The feature id for the '<em><b>Field Dependent Visibility</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATES_FIELD__FIELD_DEPENDENT_VISIBILITY = FIELD_ENTITY__FIELD_DEPENDENT_VISIBILITY;

	/**
	 * The feature id for the '<em><b>Rdf Predicate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATES_FIELD__RDF_PREDICATE = FIELD_ENTITY__RDF_PREDICATE;

	/**
	 * The feature id for the '<em><b>Min</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATES_FIELD__MIN = FIELD_ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Max</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATES_FIELD__MAX = FIELD_ENTITY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Dates Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATES_FIELD_FEATURE_COUNT = FIELD_ENTITY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_FIELD__NAME = DATES_FIELD__NAME;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_FIELD__SHORT_NAME = DATES_FIELD__SHORT_NAME;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_FIELD__DESCRIPTIONS = DATES_FIELD__DESCRIPTIONS;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_FIELD__DEFAULT_VALUE = DATES_FIELD__DEFAULT_VALUE;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_FIELD__REQUIRED = DATES_FIELD__REQUIRED;

	/**
	 * The feature id for the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_FIELD__HIDDEN = DATES_FIELD__HIDDEN;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_FIELD__READ_ONLY = DATES_FIELD__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Parent Group</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_FIELD__PARENT_GROUP = DATES_FIELD__PARENT_GROUP;

	/**
	 * The feature id for the '<em><b>Calculation Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_FIELD__CALCULATION_FUNCTION_NAME = DATES_FIELD__CALCULATION_FUNCTION_NAME;

	/**
	 * The feature id for the '<em><b>Field Dependent Visibility</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_FIELD__FIELD_DEPENDENT_VISIBILITY = DATES_FIELD__FIELD_DEPENDENT_VISIBILITY;

	/**
	 * The feature id for the '<em><b>Rdf Predicate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_FIELD__RDF_PREDICATE = DATES_FIELD__RDF_PREDICATE;

	/**
	 * The feature id for the '<em><b>Min</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_FIELD__MIN = DATES_FIELD__MIN;

	/**
	 * The feature id for the '<em><b>Max</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_FIELD__MAX = DATES_FIELD__MAX;

	/**
	 * The number of structural features of the '<em>Date Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_FIELD_FEATURE_COUNT = DATES_FIELD_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_FIELD__NAME = DATES_FIELD__NAME;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_FIELD__SHORT_NAME = DATES_FIELD__SHORT_NAME;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_FIELD__DESCRIPTIONS = DATES_FIELD__DESCRIPTIONS;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_FIELD__DEFAULT_VALUE = DATES_FIELD__DEFAULT_VALUE;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_FIELD__REQUIRED = DATES_FIELD__REQUIRED;

	/**
	 * The feature id for the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_FIELD__HIDDEN = DATES_FIELD__HIDDEN;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_FIELD__READ_ONLY = DATES_FIELD__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Parent Group</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_FIELD__PARENT_GROUP = DATES_FIELD__PARENT_GROUP;

	/**
	 * The feature id for the '<em><b>Calculation Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_FIELD__CALCULATION_FUNCTION_NAME = DATES_FIELD__CALCULATION_FUNCTION_NAME;

	/**
	 * The feature id for the '<em><b>Field Dependent Visibility</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_FIELD__FIELD_DEPENDENT_VISIBILITY = DATES_FIELD__FIELD_DEPENDENT_VISIBILITY;

	/**
	 * The feature id for the '<em><b>Rdf Predicate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_FIELD__RDF_PREDICATE = DATES_FIELD__RDF_PREDICATE;

	/**
	 * The feature id for the '<em><b>Min</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_FIELD__MIN = DATES_FIELD__MIN;

	/**
	 * The feature id for the '<em><b>Max</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_FIELD__MAX = DATES_FIELD__MAX;

	/**
	 * The number of structural features of the '<em>Date Time Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_FIELD_FEATURE_COUNT = DATES_FIELD_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_FIELD__NAME = DATES_FIELD__NAME;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_FIELD__SHORT_NAME = DATES_FIELD__SHORT_NAME;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_FIELD__DESCRIPTIONS = DATES_FIELD__DESCRIPTIONS;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_FIELD__DEFAULT_VALUE = DATES_FIELD__DEFAULT_VALUE;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_FIELD__REQUIRED = DATES_FIELD__REQUIRED;

	/**
	 * The feature id for the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_FIELD__HIDDEN = DATES_FIELD__HIDDEN;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_FIELD__READ_ONLY = DATES_FIELD__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Parent Group</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_FIELD__PARENT_GROUP = DATES_FIELD__PARENT_GROUP;

	/**
	 * The feature id for the '<em><b>Calculation Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_FIELD__CALCULATION_FUNCTION_NAME = DATES_FIELD__CALCULATION_FUNCTION_NAME;

	/**
	 * The feature id for the '<em><b>Field Dependent Visibility</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_FIELD__FIELD_DEPENDENT_VISIBILITY = DATES_FIELD__FIELD_DEPENDENT_VISIBILITY;

	/**
	 * The feature id for the '<em><b>Rdf Predicate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_FIELD__RDF_PREDICATE = DATES_FIELD__RDF_PREDICATE;

	/**
	 * The feature id for the '<em><b>Min</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_FIELD__MIN = DATES_FIELD__MIN;

	/**
	 * The feature id for the '<em><b>Max</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_FIELD__MAX = DATES_FIELD__MAX;

	/**
	 * The number of structural features of the '<em>Time Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_FIELD_FEATURE_COUNT = DATES_FIELD_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__NAME = CARD_ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__SHORT_NAME = CARD_ENTITY__SHORT_NAME;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__DESCRIPTIONS = CARD_ENTITY__DESCRIPTIONS;

	/**
	 * The feature id for the '<em><b>Column Fields</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__COLUMN_FIELDS = CARD_ENTITY__COLUMN_FIELDS;

	/**
	 * The feature id for the '<em><b>Main Fields</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__MAIN_FIELDS = CARD_ENTITY__MAIN_FIELDS;

	/**
	 * The feature id for the '<em><b>Secondary Fields</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__SECONDARY_FIELDS = CARD_ENTITY__SECONDARY_FIELDS;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__ICON = CARD_ENTITY__ICON;

	/**
	 * The feature id for the '<em><b>Top Level</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__TOP_LEVEL = CARD_ENTITY__TOP_LEVEL;

	/**
	 * The feature id for the '<em><b>Client Filter Fields</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__CLIENT_FILTER_FIELDS = CARD_ENTITY__CLIENT_FILTER_FIELDS;

	/**
	 * The feature id for the '<em><b>Groups</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__GROUPS = CARD_ENTITY__GROUPS;

	/**
	 * The feature id for the '<em><b>Sort Fields</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__SORT_FIELDS = CARD_ENTITY__SORT_FIELDS;

	/**
	 * The feature id for the '<em><b>Actor Filter Fields</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__ACTOR_FILTER_FIELDS = CARD_ENTITY__ACTOR_FILTER_FIELDS;

	/**
	 * The feature id for the '<em><b>Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__COLOR = CARD_ENTITY__COLOR;

	/**
	 * The feature id for the '<em><b>Client Period Filterable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__CLIENT_PERIOD_FILTERABLE = CARD_ENTITY__CLIENT_PERIOD_FILTERABLE;

	/**
	 * The feature id for the '<em><b>Georeferenced</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__GEOREFERENCED = CARD_ENTITY__GEOREFERENCED;

	/**
	 * The feature id for the '<em><b>Rdf Subject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__RDF_SUBJECT = CARD_ENTITY__RDF_SUBJECT;

	/**
	 * The feature id for the '<em><b>Rdf Predicate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__RDF_PREDICATE = CARD_ENTITY__RDF_PREDICATE;

	/**
	 * The feature id for the '<em><b>Nested Fields</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__NESTED_FIELDS = CARD_ENTITY__NESTED_FIELDS;

	/**
	 * The feature id for the '<em><b>Has Dynamic Fields</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__HAS_DYNAMIC_FIELDS = CARD_ENTITY__HAS_DYNAMIC_FIELDS;

	/**
	 * The feature id for the '<em><b>Notification Infos</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__NOTIFICATION_INFOS = CARD_ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Admin Fields</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__ADMIN_FIELDS = CARD_ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Filters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__FILTERS = CARD_ENTITY_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Actor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR_FEATURE_COUNT = CARD_ENTITY_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILTER_FIELD__NAME = RELATION_FIELD_ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILTER_FIELD__SHORT_NAME = RELATION_FIELD_ENTITY__SHORT_NAME;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILTER_FIELD__DESCRIPTIONS = RELATION_FIELD_ENTITY__DESCRIPTIONS;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILTER_FIELD__DEFAULT_VALUE = RELATION_FIELD_ENTITY__DEFAULT_VALUE;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILTER_FIELD__REQUIRED = RELATION_FIELD_ENTITY__REQUIRED;

	/**
	 * The feature id for the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILTER_FIELD__HIDDEN = RELATION_FIELD_ENTITY__HIDDEN;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILTER_FIELD__READ_ONLY = RELATION_FIELD_ENTITY__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Parent Group</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILTER_FIELD__PARENT_GROUP = RELATION_FIELD_ENTITY__PARENT_GROUP;

	/**
	 * The feature id for the '<em><b>Calculation Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILTER_FIELD__CALCULATION_FUNCTION_NAME = RELATION_FIELD_ENTITY__CALCULATION_FUNCTION_NAME;

	/**
	 * The feature id for the '<em><b>Field Dependent Visibility</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILTER_FIELD__FIELD_DEPENDENT_VISIBILITY = RELATION_FIELD_ENTITY__FIELD_DEPENDENT_VISIBILITY;

	/**
	 * The feature id for the '<em><b>Rdf Predicate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILTER_FIELD__RDF_PREDICATE = RELATION_FIELD_ENTITY__RDF_PREDICATE;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILTER_FIELD__ENTITY = RELATION_FIELD_ENTITY__ENTITY;

	/**
	 * The feature id for the '<em><b>Cardinality</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILTER_FIELD__CARDINALITY = RELATION_FIELD_ENTITY__CARDINALITY;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILTER_FIELD__TYPE = RELATION_FIELD_ENTITY__TYPE;

	/**
	 * The feature id for the '<em><b>Common Fields</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILTER_FIELD__COMMON_FIELDS = RELATION_FIELD_ENTITY__COMMON_FIELDS;

	/**
	 * The feature id for the '<em><b>Opposite Relation Field</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILTER_FIELD__OPPOSITE_RELATION_FIELD = RELATION_FIELD_ENTITY__OPPOSITE_RELATION_FIELD;

	/**
	 * The feature id for the '<em><b>Relation Hierarchical Filter</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILTER_FIELD__RELATION_HIERARCHICAL_FILTER = RELATION_FIELD_ENTITY__RELATION_HIERARCHICAL_FILTER;

	/**
	 * The feature id for the '<em><b>Nested Form</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILTER_FIELD__NESTED_FORM = RELATION_FIELD_ENTITY__NESTED_FORM;

	/**
	 * The feature id for the '<em><b>Parent Actor</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILTER_FIELD__PARENT_ACTOR = RELATION_FIELD_ENTITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Filter Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILTER_FIELD_FEATURE_COUNT = RELATION_FIELD_ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Data Field</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION_INFO__DATA_FIELD = 0;

	/**
	 * The feature id for the '<em><b>Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION_INFO__METHOD = 1;

	/**
	 * The number of structural features of the '<em>Notification Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION_INFO_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Display</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESCRIPTION__DISPLAY = 0;

	/**
	 * The feature id for the '<em><b>Help</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESCRIPTION__HELP = 1;

	/**
	 * The feature id for the '<em><b>Locale</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESCRIPTION__LOCALE = 2;

	/**
	 * The number of structural features of the '<em>Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESCRIPTION_FEATURE_COUNT = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Date Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE__DATE_FORMAT = 1;

	/**
	 * The feature id for the '<em><b>Time Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE__TIME_FORMAT = 2;

	/**
	 * The feature id for the '<em><b>Integer Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE__INTEGER_FORMAT = 3;

	/**
	 * The feature id for the '<em><b>Float Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE__FLOAT_FORMAT = 4;

	/**
	 * The feature id for the '<em><b>Iso Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE__ISO_CODE = 5;

	/**
	 * The number of structural features of the '<em>Language</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE_FEATURE_COUNT = 6;

	/**
	 * The feature id for the '<em><b>Dependency Field</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_DEPENDENT_VISIBILITY__DEPENDENCY_FIELD = 0;

	/**
	 * The feature id for the '<em><b>Dependency Field Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_DEPENDENT_VISIBILITY__DEPENDENCY_FIELD_VALUE = 1;

	/**
	 * The feature id for the '<em><b>Parent Field Entity</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_DEPENDENT_VISIBILITY__PARENT_FIELD_ENTITY = 2;

	/**
	 * The number of structural features of the '<em>Field Dependent Visibility</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_DEPENDENT_VISIBILITY_FEATURE_COUNT = 3;

	/**
	 * The feature id for the '<em><b>Entity Field</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR_FILTER__ENTITY_FIELD = 0;

	/**
	 * The feature id for the '<em><b>Actor Field</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR_FILTER__ACTOR_FIELD = 1;

	/**
	 * The feature id for the '<em><b>Sufficient</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR_FILTER__SUFFICIENT = 2;

	/**
	 * The number of structural features of the '<em>Actor Filter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR_FILTER_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.ThemaImpl <em>Thema</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.ThemaImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getThema()
	 * @generated
	 */
	int THEMA = 38;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THEMA__NAME = 0;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THEMA__DESCRIPTIONS = 1;

	/**
	 * The feature id for the '<em><b>Entities</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THEMA__ENTITIES = 2;

	/**
	 * The number of structural features of the '<em>Thema</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THEMA_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.impl.CardEntityUIFormatImpl <em>Card Entity UI Format</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.impl.CardEntityUIFormatImpl
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getCardEntityUIFormat()
	 * @generated
	 */
	int CARD_ENTITY_UI_FORMAT = 39;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARD_ENTITY_UI_FORMAT__ENTITY = 0;

	/**
	 * The feature id for the '<em><b>With Tabulations</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARD_ENTITY_UI_FORMAT__WITH_TABULATIONS = 1;

	/**
	 * The number of structural features of the '<em>Card Entity UI Format</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARD_ENTITY_UI_FORMAT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.PhoneType <em>Phone Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.PhoneType
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getPhoneType()
	 * @generated
	 */
	int PHONE_TYPE = 41;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.RelationType <em>Relation Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.RelationType
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getRelationType()
	 * @generated
	 */
	int RELATION_TYPE = 42;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.NotificationMethod <em>Notification Method</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.NotificationMethod
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getNotificationMethod()
	 * @generated
	 */
	int NOTIFICATION_METHOD = 43;

	/**
	 * The meta object id for the '{@link org.imogene.model.core.GeoType <em>Geo Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imogene.model.core.GeoType
	 * @see org.imogene.model.core.impl.ImogenePackageImpl#getGeoType()
	 * @generated
	 */
	int GEO_TYPE = 40;


	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.CardEntity <em>Card Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Card Entity</em>'.
	 * @see org.imogene.model.core.CardEntity
	 * @generated
	 */
	EClass getCardEntity();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.CardEntity#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.imogene.model.core.CardEntity#getName()
	 * @see #getCardEntity()
	 * @generated
	 */
	EAttribute getCardEntity_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.imogene.model.core.CardEntity#getDescriptions <em>Descriptions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Descriptions</em>'.
	 * @see org.imogene.model.core.CardEntity#getDescriptions()
	 * @see #getCardEntity()
	 * @generated
	 */
	EReference getCardEntity_Descriptions();

	/**
	 * Returns the meta object for the reference list '{@link org.imogene.model.core.CardEntity#getMainFields <em>Main Fields</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Main Fields</em>'.
	 * @see org.imogene.model.core.CardEntity#getMainFields()
	 * @see #getCardEntity()
	 * @generated
	 */
	EReference getCardEntity_MainFields();

	/**
	 * Returns the meta object for the reference list '{@link org.imogene.model.core.CardEntity#getColumnFields <em>Column Fields</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Column Fields</em>'.
	 * @see org.imogene.model.core.CardEntity#getColumnFields()
	 * @see #getCardEntity()
	 * @generated
	 */
	EReference getCardEntity_ColumnFields();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.CardEntity#getShortName <em>Short Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Short Name</em>'.
	 * @see org.imogene.model.core.CardEntity#getShortName()
	 * @see #getCardEntity()
	 * @generated
	 */
	EAttribute getCardEntity_ShortName();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.CardEntity#getIcon <em>Icon</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Icon</em>'.
	 * @see org.imogene.model.core.CardEntity#getIcon()
	 * @see #getCardEntity()
	 * @generated
	 */
	EAttribute getCardEntity_Icon();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.CardEntity#isTopLevel <em>Top Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Top Level</em>'.
	 * @see org.imogene.model.core.CardEntity#isTopLevel()
	 * @see #getCardEntity()
	 * @generated
	 */
	EAttribute getCardEntity_TopLevel();

	/**
	 * Returns the meta object for the reference list '{@link org.imogene.model.core.CardEntity#getClientFilterFields <em>Client Filter Fields</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Client Filter Fields</em>'.
	 * @see org.imogene.model.core.CardEntity#getClientFilterFields()
	 * @see #getCardEntity()
	 * @generated
	 */
	EReference getCardEntity_ClientFilterFields();

	/**
	 * Returns the meta object for the containment reference list '{@link org.imogene.model.core.CardEntity#getGroups <em>Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Groups</em>'.
	 * @see org.imogene.model.core.CardEntity#getGroups()
	 * @see #getCardEntity()
	 * @generated
	 */
	EReference getCardEntity_Groups();

	/**
	 * Returns the meta object for the reference list '{@link org.imogene.model.core.CardEntity#getSortFields <em>Sort Fields</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Sort Fields</em>'.
	 * @see org.imogene.model.core.CardEntity#getSortFields()
	 * @see #getCardEntity()
	 * @generated
	 */
	EReference getCardEntity_SortFields();

	/**
	 * Returns the meta object for the reference list '{@link org.imogene.model.core.CardEntity#getActorFilterFields <em>Actor Filter Fields</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Actor Filter Fields</em>'.
	 * @see org.imogene.model.core.CardEntity#getActorFilterFields()
	 * @see #getCardEntity()
	 * @generated
	 */
	EReference getCardEntity_ActorFilterFields();

	/**
	 * Returns the meta object for the reference list '{@link org.imogene.model.core.CardEntity#getSecondaryFields <em>Secondary Fields</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Secondary Fields</em>'.
	 * @see org.imogene.model.core.CardEntity#getSecondaryFields()
	 * @see #getCardEntity()
	 * @generated
	 */
	EReference getCardEntity_SecondaryFields();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.CardEntity#getColor <em>Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Color</em>'.
	 * @see org.imogene.model.core.CardEntity#getColor()
	 * @see #getCardEntity()
	 * @generated
	 */
	EAttribute getCardEntity_Color();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.CardEntity#isClientPeriodFilterable <em>Client Period Filterable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Client Period Filterable</em>'.
	 * @see org.imogene.model.core.CardEntity#isClientPeriodFilterable()
	 * @see #getCardEntity()
	 * @generated
	 */
	EAttribute getCardEntity_ClientPeriodFilterable();

	/**
	 * Returns the meta object for the reference '{@link org.imogene.model.core.CardEntity#getGeoreferenced <em>Georeferenced</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Georeferenced</em>'.
	 * @see org.imogene.model.core.CardEntity#getGeoreferenced()
	 * @see #getCardEntity()
	 * @generated
	 */
	EReference getCardEntity_Georeferenced();

	/**
	 * Returns the meta object for the reference '{@link org.imogene.model.core.CardEntity#getRdfSubject <em>Rdf Subject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Rdf Subject</em>'.
	 * @see org.imogene.model.core.CardEntity#getRdfSubject()
	 * @see #getCardEntity()
	 * @generated
	 */
	EReference getCardEntity_RdfSubject();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.CardEntity#getRdfPredicate <em>Rdf Predicate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rdf Predicate</em>'.
	 * @see org.imogene.model.core.CardEntity#getRdfPredicate()
	 * @see #getCardEntity()
	 * @generated
	 */
	EAttribute getCardEntity_RdfPredicate();

	/**
	 * Returns the meta object for the reference list '{@link org.imogene.model.core.CardEntity#getNestedFields <em>Nested Fields</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Nested Fields</em>'.
	 * @see org.imogene.model.core.CardEntity#getNestedFields()
	 * @see #getCardEntity()
	 * @generated
	 */
	EReference getCardEntity_NestedFields();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.CardEntity#isHasDynamicFields <em>Has Dynamic Fields</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Has Dynamic Fields</em>'.
	 * @see org.imogene.model.core.CardEntity#isHasDynamicFields()
	 * @see #getCardEntity()
	 * @generated
	 */
	EAttribute getCardEntity_HasDynamicFields();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.Description <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Description</em>'.
	 * @see org.imogene.model.core.Description
	 * @generated
	 */
	EClass getDescription();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.Description#getDisplay <em>Display</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display</em>'.
	 * @see org.imogene.model.core.Description#getDisplay()
	 * @see #getDescription()
	 * @generated
	 */
	EAttribute getDescription_Display();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.Description#getHelp <em>Help</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Help</em>'.
	 * @see org.imogene.model.core.Description#getHelp()
	 * @see #getDescription()
	 * @generated
	 */
	EAttribute getDescription_Help();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.Description#getLocale <em>Locale</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Locale</em>'.
	 * @see org.imogene.model.core.Description#getLocale()
	 * @see #getDescription()
	 * @generated
	 */
	EAttribute getDescription_Locale();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.FieldEntity <em>Field Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Field Entity</em>'.
	 * @see org.imogene.model.core.FieldEntity
	 * @generated
	 */
	EClass getFieldEntity();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.FieldEntity#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.imogene.model.core.FieldEntity#getName()
	 * @see #getFieldEntity()
	 * @generated
	 */
	EAttribute getFieldEntity_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.FieldEntity#getShortName <em>Short Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Short Name</em>'.
	 * @see org.imogene.model.core.FieldEntity#getShortName()
	 * @see #getFieldEntity()
	 * @generated
	 */
	EAttribute getFieldEntity_ShortName();

	/**
	 * Returns the meta object for the containment reference list '{@link org.imogene.model.core.FieldEntity#getDescriptions <em>Descriptions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Descriptions</em>'.
	 * @see org.imogene.model.core.FieldEntity#getDescriptions()
	 * @see #getFieldEntity()
	 * @generated
	 */
	EReference getFieldEntity_Descriptions();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.FieldEntity#getDefaultValue <em>Default Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Value</em>'.
	 * @see org.imogene.model.core.FieldEntity#getDefaultValue()
	 * @see #getFieldEntity()
	 * @generated
	 */
	EAttribute getFieldEntity_DefaultValue();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.FieldEntity#isRequired <em>Required</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Required</em>'.
	 * @see org.imogene.model.core.FieldEntity#isRequired()
	 * @see #getFieldEntity()
	 * @generated
	 */
	EAttribute getFieldEntity_Required();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.FieldEntity#isHidden <em>Hidden</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hidden</em>'.
	 * @see org.imogene.model.core.FieldEntity#isHidden()
	 * @see #getFieldEntity()
	 * @generated
	 */
	EAttribute getFieldEntity_Hidden();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.FieldEntity#isReadOnly <em>Read Only</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Read Only</em>'.
	 * @see org.imogene.model.core.FieldEntity#isReadOnly()
	 * @see #getFieldEntity()
	 * @generated
	 */
	EAttribute getFieldEntity_ReadOnly();

	/**
	 * Returns the meta object for the container reference '{@link org.imogene.model.core.FieldEntity#getParentGroup <em>Parent Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Parent Group</em>'.
	 * @see org.imogene.model.core.FieldEntity#getParentGroup()
	 * @see #getFieldEntity()
	 * @generated
	 */
	EReference getFieldEntity_ParentGroup();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.FieldEntity#getCalculationFunctionName <em>Calculation Function Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Calculation Function Name</em>'.
	 * @see org.imogene.model.core.FieldEntity#getCalculationFunctionName()
	 * @see #getFieldEntity()
	 * @generated
	 */
	EAttribute getFieldEntity_CalculationFunctionName();

	/**
	 * Returns the meta object for the containment reference list '{@link org.imogene.model.core.FieldEntity#getFieldDependentVisibility <em>Field Dependent Visibility</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Field Dependent Visibility</em>'.
	 * @see org.imogene.model.core.FieldEntity#getFieldDependentVisibility()
	 * @see #getFieldEntity()
	 * @generated
	 */
	EReference getFieldEntity_FieldDependentVisibility();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.FieldEntity#getRdfPredicate <em>Rdf Predicate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rdf Predicate</em>'.
	 * @see org.imogene.model.core.FieldEntity#getRdfPredicate()
	 * @see #getFieldEntity()
	 * @generated
	 */
	EAttribute getFieldEntity_RdfPredicate();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.NumericField <em>Numeric Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Numeric Field</em>'.
	 * @see org.imogene.model.core.NumericField
	 * @generated
	 */
	EClass getNumericField();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.NumericField#getMin <em>Min</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min</em>'.
	 * @see org.imogene.model.core.NumericField#getMin()
	 * @see #getNumericField()
	 * @generated
	 */
	EAttribute getNumericField_Min();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.NumericField#getMax <em>Max</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max</em>'.
	 * @see org.imogene.model.core.NumericField#getMax()
	 * @see #getNumericField()
	 * @generated
	 */
	EAttribute getNumericField_Max();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.NumericField#getUnit <em>Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unit</em>'.
	 * @see org.imogene.model.core.NumericField#getUnit()
	 * @see #getNumericField()
	 * @generated
	 */
	EAttribute getNumericField_Unit();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.StringField <em>String Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Field</em>'.
	 * @see org.imogene.model.core.StringField
	 * @generated
	 */
	EClass getStringField();

	/**
	 * Returns the meta object for the containment reference list '{@link org.imogene.model.core.StringField#getValidationRules <em>Validation Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Validation Rules</em>'.
	 * @see org.imogene.model.core.StringField#getValidationRules()
	 * @see #getStringField()
	 * @generated
	 */
	EReference getStringField_ValidationRules();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.MediaFileField <em>Media File Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Media File Field</em>'.
	 * @see org.imogene.model.core.MediaFileField
	 * @generated
	 */
	EClass getMediaFileField();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.RelationFieldEntity <em>Relation Field Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Relation Field Entity</em>'.
	 * @see org.imogene.model.core.RelationFieldEntity
	 * @generated
	 */
	EClass getRelationFieldEntity();

	/**
	 * Returns the meta object for the reference '{@link org.imogene.model.core.RelationFieldEntity#getEntity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Entity</em>'.
	 * @see org.imogene.model.core.RelationFieldEntity#getEntity()
	 * @see #getRelationFieldEntity()
	 * @generated
	 */
	EReference getRelationFieldEntity_Entity();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.RelationFieldEntity#getCardinality <em>Cardinality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cardinality</em>'.
	 * @see org.imogene.model.core.RelationFieldEntity#getCardinality()
	 * @see #getRelationFieldEntity()
	 * @generated
	 */
	EAttribute getRelationFieldEntity_Cardinality();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.RelationFieldEntity#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.imogene.model.core.RelationFieldEntity#getType()
	 * @see #getRelationFieldEntity()
	 * @generated
	 */
	EAttribute getRelationFieldEntity_Type();

	/**
	 * Returns the meta object for the reference list '{@link org.imogene.model.core.RelationFieldEntity#getCommonFields <em>Common Fields</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Common Fields</em>'.
	 * @see org.imogene.model.core.RelationFieldEntity#getCommonFields()
	 * @see #getRelationFieldEntity()
	 * @generated
	 */
	EReference getRelationFieldEntity_CommonFields();

	/**
	 * Returns the meta object for the reference '{@link org.imogene.model.core.RelationFieldEntity#getOppositeRelationField <em>Opposite Relation Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Opposite Relation Field</em>'.
	 * @see org.imogene.model.core.RelationFieldEntity#getOppositeRelationField()
	 * @see #getRelationFieldEntity()
	 * @generated
	 */
	EReference getRelationFieldEntity_OppositeRelationField();

	/**
	 * Returns the meta object for the reference list '{@link org.imogene.model.core.RelationFieldEntity#getRelationHierarchicalFilter <em>Relation Hierarchical Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Relation Hierarchical Filter</em>'.
	 * @see org.imogene.model.core.RelationFieldEntity#getRelationHierarchicalFilter()
	 * @see #getRelationFieldEntity()
	 * @generated
	 */
	EReference getRelationFieldEntity_RelationHierarchicalFilter();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.RelationFieldEntity#isNestedForm <em>Nested Form</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Nested Form</em>'.
	 * @see org.imogene.model.core.RelationFieldEntity#isNestedForm()
	 * @see #getRelationFieldEntity()
	 * @generated
	 */
	EAttribute getRelationFieldEntity_NestedForm();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.MainRelationFieldEntity <em>Main Relation Field Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Main Relation Field Entity</em>'.
	 * @see org.imogene.model.core.MainRelationFieldEntity
	 * @generated
	 */
	EClass getMainRelationFieldEntity();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.MainRelationFieldEntity#getInverseCardinality <em>Inverse Cardinality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Inverse Cardinality</em>'.
	 * @see org.imogene.model.core.MainRelationFieldEntity#getInverseCardinality()
	 * @see #getMainRelationFieldEntity()
	 * @generated
	 */
	EAttribute getMainRelationFieldEntity_InverseCardinality();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.ReverseRelationFieldEntity <em>Reverse Relation Field Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Reverse Relation Field Entity</em>'.
	 * @see org.imogene.model.core.ReverseRelationFieldEntity
	 * @generated
	 */
	EClass getReverseRelationFieldEntity();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.IntegerField <em>Integer Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Integer Field</em>'.
	 * @see org.imogene.model.core.IntegerField
	 * @generated
	 */
	EClass getIntegerField();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.FloatField <em>Float Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Float Field</em>'.
	 * @see org.imogene.model.core.FloatField
	 * @generated
	 */
	EClass getFloatField();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.FloatField#getDecimalNumber <em>Decimal Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Decimal Number</em>'.
	 * @see org.imogene.model.core.FloatField#getDecimalNumber()
	 * @see #getFloatField()
	 * @generated
	 */
	EAttribute getFloatField_DecimalNumber();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.EnumField <em>Enum Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enum Field</em>'.
	 * @see org.imogene.model.core.EnumField
	 * @generated
	 */
	EClass getEnumField();

	/**
	 * Returns the meta object for the containment reference list '{@link org.imogene.model.core.EnumField#getEnumValues <em>Enum Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Enum Values</em>'.
	 * @see org.imogene.model.core.EnumField#getEnumValues()
	 * @see #getEnumField()
	 * @generated
	 */
	EReference getEnumField_EnumValues();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.EnumField#isMultipleSelection <em>Multiple Selection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Multiple Selection</em>'.
	 * @see org.imogene.model.core.EnumField#isMultipleSelection()
	 * @see #getEnumField()
	 * @generated
	 */
	EAttribute getEnumField_MultipleSelection();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.EmailField <em>Email Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Email Field</em>'.
	 * @see org.imogene.model.core.EmailField
	 * @generated
	 */
	EClass getEmailField();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.VideoField <em>Video Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Video Field</em>'.
	 * @see org.imogene.model.core.VideoField
	 * @generated
	 */
	EClass getVideoField();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.BinaryField <em>Binary Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Binary Field</em>'.
	 * @see org.imogene.model.core.BinaryField
	 * @generated
	 */
	EClass getBinaryField();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.BinaryField#getCategory <em>Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Category</em>'.
	 * @see org.imogene.model.core.BinaryField#getCategory()
	 * @see #getBinaryField()
	 * @generated
	 */
	EAttribute getBinaryField_Category();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.DateField <em>Date Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Date Field</em>'.
	 * @see org.imogene.model.core.DateField
	 * @generated
	 */
	EClass getDateField();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.DateTimeField <em>Date Time Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Date Time Field</em>'.
	 * @see org.imogene.model.core.DateTimeField
	 * @generated
	 */
	EClass getDateTimeField();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.TimeField <em>Time Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Time Field</em>'.
	 * @see org.imogene.model.core.TimeField
	 * @generated
	 */
	EClass getTimeField();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.Project <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Project</em>'.
	 * @see org.imogene.model.core.Project
	 * @generated
	 */
	EClass getProject();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.Project#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.imogene.model.core.Project#getName()
	 * @see #getProject()
	 * @generated
	 */
	EAttribute getProject_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.imogene.model.core.Project#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Description</em>'.
	 * @see org.imogene.model.core.Project#getDescription()
	 * @see #getProject()
	 * @generated
	 */
	EReference getProject_Description();

	/**
	 * Returns the meta object for the containment reference list '{@link org.imogene.model.core.Project#getEntities <em>Entities</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entities</em>'.
	 * @see org.imogene.model.core.Project#getEntities()
	 * @see #getProject()
	 * @generated
	 */
	EReference getProject_Entities();

	/**
	 * Returns the meta object for the containment reference list '{@link org.imogene.model.core.Project#getEntityUIFormats <em>Entity UI Formats</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entity UI Formats</em>'.
	 * @see org.imogene.model.core.Project#getEntityUIFormats()
	 * @see #getProject()
	 * @generated
	 */
	EReference getProject_EntityUIFormats();

	/**
	 * Returns the meta object for the containment reference list '{@link org.imogene.model.core.Project#getThemas <em>Themas</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Themas</em>'.
	 * @see org.imogene.model.core.Project#getThemas()
	 * @see #getProject()
	 * @generated
	 */
	EReference getProject_Themas();

	/**
	 * Returns the meta object for the containment reference list '{@link org.imogene.model.core.Project#getLanguages <em>Languages</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Languages</em>'.
	 * @see org.imogene.model.core.Project#getLanguages()
	 * @see #getProject()
	 * @generated
	 */
	EReference getProject_Languages();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.DatesField <em>Dates Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dates Field</em>'.
	 * @see org.imogene.model.core.DatesField
	 * @generated
	 */
	EClass getDatesField();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.DatesField#getMin <em>Min</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min</em>'.
	 * @see org.imogene.model.core.DatesField#getMin()
	 * @see #getDatesField()
	 * @generated
	 */
	EAttribute getDatesField_Min();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.DatesField#getMax <em>Max</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max</em>'.
	 * @see org.imogene.model.core.DatesField#getMax()
	 * @see #getDatesField()
	 * @generated
	 */
	EAttribute getDatesField_Max();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.PhoneField <em>Phone Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Phone Field</em>'.
	 * @see org.imogene.model.core.PhoneField
	 * @generated
	 */
	EClass getPhoneField();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.PhoneField#getPhoneType <em>Phone Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Phone Type</em>'.
	 * @see org.imogene.model.core.PhoneField#getPhoneType()
	 * @see #getPhoneField()
	 * @generated
	 */
	EAttribute getPhoneField_PhoneType();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.PhotoField <em>Photo Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Photo Field</em>'.
	 * @see org.imogene.model.core.PhotoField
	 * @generated
	 */
	EClass getPhotoField();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.SoundField <em>Sound Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sound Field</em>'.
	 * @see org.imogene.model.core.SoundField
	 * @generated
	 */
	EClass getSoundField();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.BooleanField <em>Boolean Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boolean Field</em>'.
	 * @see org.imogene.model.core.BooleanField
	 * @generated
	 */
	EClass getBooleanField();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.EnumValue <em>Enum Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enum Value</em>'.
	 * @see org.imogene.model.core.EnumValue
	 * @generated
	 */
	EClass getEnumValue();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.EnumValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.imogene.model.core.EnumValue#getValue()
	 * @see #getEnumValue()
	 * @generated
	 */
	EAttribute getEnumValue_Value();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.EnumValue#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.imogene.model.core.EnumValue#getName()
	 * @see #getEnumValue()
	 * @generated
	 */
	EAttribute getEnumValue_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.imogene.model.core.EnumValue#getDescriptions <em>Descriptions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Descriptions</em>'.
	 * @see org.imogene.model.core.EnumValue#getDescriptions()
	 * @see #getEnumValue()
	 * @generated
	 */
	EReference getEnumValue_Descriptions();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.TextField <em>Text Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Text Field</em>'.
	 * @see org.imogene.model.core.TextField
	 * @generated
	 */
	EClass getTextField();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.TextField#isTranslatable <em>Translatable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Translatable</em>'.
	 * @see org.imogene.model.core.TextField#isTranslatable()
	 * @see #getTextField()
	 * @generated
	 */
	EAttribute getTextField_Translatable();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.TextAreaField <em>Text Area Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Text Area Field</em>'.
	 * @see org.imogene.model.core.TextAreaField
	 * @generated
	 */
	EClass getTextAreaField();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.FieldGroup <em>Field Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Field Group</em>'.
	 * @see org.imogene.model.core.FieldGroup
	 * @generated
	 */
	EClass getFieldGroup();

	/**
	 * Returns the meta object for the containment reference list '{@link org.imogene.model.core.FieldGroup#getDescriptions <em>Descriptions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Descriptions</em>'.
	 * @see org.imogene.model.core.FieldGroup#getDescriptions()
	 * @see #getFieldGroup()
	 * @generated
	 */
	EReference getFieldGroup_Descriptions();

	/**
	 * Returns the meta object for the containment reference list '{@link org.imogene.model.core.FieldGroup#getFields <em>Fields</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Fields</em>'.
	 * @see org.imogene.model.core.FieldGroup#getFields()
	 * @see #getFieldGroup()
	 * @generated
	 */
	EReference getFieldGroup_Fields();

	/**
	 * Returns the meta object for the container reference '{@link org.imogene.model.core.FieldGroup#getParentCard <em>Parent Card</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Parent Card</em>'.
	 * @see org.imogene.model.core.FieldGroup#getParentCard()
	 * @see #getFieldGroup()
	 * @generated
	 */
	EReference getFieldGroup_ParentCard();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.FieldGroup#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.imogene.model.core.FieldGroup#getName()
	 * @see #getFieldGroup()
	 * @generated
	 */
	EAttribute getFieldGroup_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.FieldGroup#getShortName <em>Short Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Short Name</em>'.
	 * @see org.imogene.model.core.FieldGroup#getShortName()
	 * @see #getFieldGroup()
	 * @generated
	 */
	EAttribute getFieldGroup_ShortName();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.FieldGroup#getIcon <em>Icon</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Icon</em>'.
	 * @see org.imogene.model.core.FieldGroup#getIcon()
	 * @see #getFieldGroup()
	 * @generated
	 */
	EAttribute getFieldGroup_Icon();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.Actor <em>Actor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Actor</em>'.
	 * @see org.imogene.model.core.Actor
	 * @generated
	 */
	EClass getActor();

	/**
	 * Returns the meta object for the containment reference list '{@link org.imogene.model.core.Actor#getNotificationInfos <em>Notification Infos</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Notification Infos</em>'.
	 * @see org.imogene.model.core.Actor#getNotificationInfos()
	 * @see #getActor()
	 * @generated
	 */
	EReference getActor_NotificationInfos();

	/**
	 * Returns the meta object for the reference list '{@link org.imogene.model.core.Actor#getAdminFields <em>Admin Fields</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Admin Fields</em>'.
	 * @see org.imogene.model.core.Actor#getAdminFields()
	 * @see #getActor()
	 * @generated
	 */
	EReference getActor_AdminFields();

	/**
	 * Returns the meta object for the containment reference list '{@link org.imogene.model.core.Actor#getFilters <em>Filters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Filters</em>'.
	 * @see org.imogene.model.core.Actor#getFilters()
	 * @see #getActor()
	 * @generated
	 */
	EReference getActor_Filters();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.NotificationInfo <em>Notification Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Notification Info</em>'.
	 * @see org.imogene.model.core.NotificationInfo
	 * @generated
	 */
	EClass getNotificationInfo();

	/**
	 * Returns the meta object for the reference '{@link org.imogene.model.core.NotificationInfo#getDataField <em>Data Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Data Field</em>'.
	 * @see org.imogene.model.core.NotificationInfo#getDataField()
	 * @see #getNotificationInfo()
	 * @generated
	 */
	EReference getNotificationInfo_DataField();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.NotificationInfo#getMethod <em>Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Method</em>'.
	 * @see org.imogene.model.core.NotificationInfo#getMethod()
	 * @see #getNotificationInfo()
	 * @generated
	 */
	EAttribute getNotificationInfo_Method();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.Language <em>Language</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Language</em>'.
	 * @see org.imogene.model.core.Language
	 * @generated
	 */
	EClass getLanguage();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.Language#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.imogene.model.core.Language#getName()
	 * @see #getLanguage()
	 * @generated
	 */
	EAttribute getLanguage_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.Language#getDateFormat <em>Date Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Date Format</em>'.
	 * @see org.imogene.model.core.Language#getDateFormat()
	 * @see #getLanguage()
	 * @generated
	 */
	EAttribute getLanguage_DateFormat();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.Language#getTimeFormat <em>Time Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time Format</em>'.
	 * @see org.imogene.model.core.Language#getTimeFormat()
	 * @see #getLanguage()
	 * @generated
	 */
	EAttribute getLanguage_TimeFormat();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.Language#getIntegerFormat <em>Integer Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Integer Format</em>'.
	 * @see org.imogene.model.core.Language#getIntegerFormat()
	 * @see #getLanguage()
	 * @generated
	 */
	EAttribute getLanguage_IntegerFormat();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.Language#getFloatFormat <em>Float Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Float Format</em>'.
	 * @see org.imogene.model.core.Language#getFloatFormat()
	 * @see #getLanguage()
	 * @generated
	 */
	EAttribute getLanguage_FloatFormat();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.Language#getIsoCode <em>Iso Code</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Iso Code</em>'.
	 * @see org.imogene.model.core.Language#getIsoCode()
	 * @see #getLanguage()
	 * @generated
	 */
	EAttribute getLanguage_IsoCode();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.FieldDependentVisibility <em>Field Dependent Visibility</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Field Dependent Visibility</em>'.
	 * @see org.imogene.model.core.FieldDependentVisibility
	 * @generated
	 */
	EClass getFieldDependentVisibility();

	/**
	 * Returns the meta object for the reference '{@link org.imogene.model.core.FieldDependentVisibility#getDependencyField <em>Dependency Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Dependency Field</em>'.
	 * @see org.imogene.model.core.FieldDependentVisibility#getDependencyField()
	 * @see #getFieldDependentVisibility()
	 * @generated
	 */
	EReference getFieldDependentVisibility_DependencyField();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.FieldDependentVisibility#getDependencyFieldValue <em>Dependency Field Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Dependency Field Value</em>'.
	 * @see org.imogene.model.core.FieldDependentVisibility#getDependencyFieldValue()
	 * @see #getFieldDependentVisibility()
	 * @generated
	 */
	EAttribute getFieldDependentVisibility_DependencyFieldValue();

	/**
	 * Returns the meta object for the container reference '{@link org.imogene.model.core.FieldDependentVisibility#getParentFieldEntity <em>Parent Field Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Parent Field Entity</em>'.
	 * @see org.imogene.model.core.FieldDependentVisibility#getParentFieldEntity()
	 * @see #getFieldDependentVisibility()
	 * @generated
	 */
	EReference getFieldDependentVisibility_ParentFieldEntity();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.ValidationRule <em>Validation Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Validation Rule</em>'.
	 * @see org.imogene.model.core.ValidationRule
	 * @generated
	 */
	EClass getValidationRule();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.ValidationRule#getValidationRegularExpression <em>Validation Regular Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Validation Regular Expression</em>'.
	 * @see org.imogene.model.core.ValidationRule#getValidationRegularExpression()
	 * @see #getValidationRule()
	 * @generated
	 */
	EAttribute getValidationRule_ValidationRegularExpression();

	/**
	 * Returns the meta object for the containment reference list '{@link org.imogene.model.core.ValidationRule#getDescriptions <em>Descriptions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Descriptions</em>'.
	 * @see org.imogene.model.core.ValidationRule#getDescriptions()
	 * @see #getValidationRule()
	 * @generated
	 */
	EReference getValidationRule_Descriptions();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.ActorFilter <em>Actor Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Actor Filter</em>'.
	 * @see org.imogene.model.core.ActorFilter
	 * @generated
	 */
	EClass getActorFilter();

	/**
	 * Returns the meta object for the reference '{@link org.imogene.model.core.ActorFilter#getEntityField <em>Entity Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Entity Field</em>'.
	 * @see org.imogene.model.core.ActorFilter#getEntityField()
	 * @see #getActorFilter()
	 * @generated
	 */
	EReference getActorFilter_EntityField();

	/**
	 * Returns the meta object for the reference '{@link org.imogene.model.core.ActorFilter#getActorField <em>Actor Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Actor Field</em>'.
	 * @see org.imogene.model.core.ActorFilter#getActorField()
	 * @see #getActorFilter()
	 * @generated
	 */
	EReference getActorFilter_ActorField();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.ActorFilter#isSufficient <em>Sufficient</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sufficient</em>'.
	 * @see org.imogene.model.core.ActorFilter#isSufficient()
	 * @see #getActorFilter()
	 * @generated
	 */
	EAttribute getActorFilter_Sufficient();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.FilterField <em>Filter Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Filter Field</em>'.
	 * @see org.imogene.model.core.FilterField
	 * @generated
	 */
	EClass getFilterField();

	/**
	 * Returns the meta object for the container reference '{@link org.imogene.model.core.FilterField#getParentActor <em>Parent Actor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Parent Actor</em>'.
	 * @see org.imogene.model.core.FilterField#getParentActor()
	 * @see #getFilterField()
	 * @generated
	 */
	EReference getFilterField_ParentActor();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.AddressField <em>Address Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Address Field</em>'.
	 * @see org.imogene.model.core.AddressField
	 * @generated
	 */
	EClass getAddressField();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.GeoField <em>Geo Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Geo Field</em>'.
	 * @see org.imogene.model.core.GeoField
	 * @generated
	 */
	EClass getGeoField();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.GeoField#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.imogene.model.core.GeoField#getType()
	 * @see #getGeoField()
	 * @generated
	 */
	EAttribute getGeoField_Type();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.BarcodeField <em>Barcode Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Barcode Field</em>'.
	 * @see org.imogene.model.core.BarcodeField
	 * @generated
	 */
	EClass getBarcodeField();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.Thema <em>Thema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Thema</em>'.
	 * @see org.imogene.model.core.Thema
	 * @generated
	 */
	EClass getThema();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.Thema#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.imogene.model.core.Thema#getName()
	 * @see #getThema()
	 * @generated
	 */
	EAttribute getThema_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.imogene.model.core.Thema#getDescriptions <em>Descriptions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Descriptions</em>'.
	 * @see org.imogene.model.core.Thema#getDescriptions()
	 * @see #getThema()
	 * @generated
	 */
	EReference getThema_Descriptions();

	/**
	 * Returns the meta object for the reference list '{@link org.imogene.model.core.Thema#getEntities <em>Entities</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Entities</em>'.
	 * @see org.imogene.model.core.Thema#getEntities()
	 * @see #getThema()
	 * @generated
	 */
	EReference getThema_Entities();

	/**
	 * Returns the meta object for class '{@link org.imogene.model.core.CardEntityUIFormat <em>Card Entity UI Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Card Entity UI Format</em>'.
	 * @see org.imogene.model.core.CardEntityUIFormat
	 * @generated
	 */
	EClass getCardEntityUIFormat();

	/**
	 * Returns the meta object for the reference '{@link org.imogene.model.core.CardEntityUIFormat#getEntity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Entity</em>'.
	 * @see org.imogene.model.core.CardEntityUIFormat#getEntity()
	 * @see #getCardEntityUIFormat()
	 * @generated
	 */
	EReference getCardEntityUIFormat_Entity();

	/**
	 * Returns the meta object for the attribute '{@link org.imogene.model.core.CardEntityUIFormat#isWithTabulations <em>With Tabulations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>With Tabulations</em>'.
	 * @see org.imogene.model.core.CardEntityUIFormat#isWithTabulations()
	 * @see #getCardEntityUIFormat()
	 * @generated
	 */
	EAttribute getCardEntityUIFormat_WithTabulations();

	/**
	 * Returns the meta object for enum '{@link org.imogene.model.core.PhoneType <em>Phone Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Phone Type</em>'.
	 * @see org.imogene.model.core.PhoneType
	 * @generated
	 */
	EEnum getPhoneType();

	/**
	 * Returns the meta object for enum '{@link org.imogene.model.core.RelationType <em>Relation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Relation Type</em>'.
	 * @see org.imogene.model.core.RelationType
	 * @generated
	 */
	EEnum getRelationType();

	/**
	 * Returns the meta object for enum '{@link org.imogene.model.core.NotificationMethod <em>Notification Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Notification Method</em>'.
	 * @see org.imogene.model.core.NotificationMethod
	 * @generated
	 */
	EEnum getNotificationMethod();

	/**
	 * Returns the meta object for enum '{@link org.imogene.model.core.GeoType <em>Geo Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Geo Type</em>'.
	 * @see org.imogene.model.core.GeoType
	 * @generated
	 */
	EEnum getGeoType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ImogeneFactory getImogeneFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.CardEntityImpl <em>Card Entity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.CardEntityImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getCardEntity()
		 * @generated
		 */
		EClass CARD_ENTITY = eINSTANCE.getCardEntity();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CARD_ENTITY__NAME = eINSTANCE.getCardEntity_Name();

		/**
		 * The meta object literal for the '<em><b>Descriptions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CARD_ENTITY__DESCRIPTIONS = eINSTANCE.getCardEntity_Descriptions();

		/**
		 * The meta object literal for the '<em><b>Main Fields</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CARD_ENTITY__MAIN_FIELDS = eINSTANCE.getCardEntity_MainFields();

		/**
		 * The meta object literal for the '<em><b>Column Fields</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CARD_ENTITY__COLUMN_FIELDS = eINSTANCE.getCardEntity_ColumnFields();

		/**
		 * The meta object literal for the '<em><b>Short Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CARD_ENTITY__SHORT_NAME = eINSTANCE.getCardEntity_ShortName();

		/**
		 * The meta object literal for the '<em><b>Icon</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CARD_ENTITY__ICON = eINSTANCE.getCardEntity_Icon();

		/**
		 * The meta object literal for the '<em><b>Top Level</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CARD_ENTITY__TOP_LEVEL = eINSTANCE.getCardEntity_TopLevel();

		/**
		 * The meta object literal for the '<em><b>Client Filter Fields</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CARD_ENTITY__CLIENT_FILTER_FIELDS = eINSTANCE.getCardEntity_ClientFilterFields();

		/**
		 * The meta object literal for the '<em><b>Groups</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CARD_ENTITY__GROUPS = eINSTANCE.getCardEntity_Groups();

		/**
		 * The meta object literal for the '<em><b>Sort Fields</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CARD_ENTITY__SORT_FIELDS = eINSTANCE.getCardEntity_SortFields();

		/**
		 * The meta object literal for the '<em><b>Actor Filter Fields</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CARD_ENTITY__ACTOR_FILTER_FIELDS = eINSTANCE.getCardEntity_ActorFilterFields();

		/**
		 * The meta object literal for the '<em><b>Secondary Fields</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CARD_ENTITY__SECONDARY_FIELDS = eINSTANCE.getCardEntity_SecondaryFields();

		/**
		 * The meta object literal for the '<em><b>Color</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CARD_ENTITY__COLOR = eINSTANCE.getCardEntity_Color();

		/**
		 * The meta object literal for the '<em><b>Client Period Filterable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CARD_ENTITY__CLIENT_PERIOD_FILTERABLE = eINSTANCE.getCardEntity_ClientPeriodFilterable();

		/**
		 * The meta object literal for the '<em><b>Georeferenced</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CARD_ENTITY__GEOREFERENCED = eINSTANCE.getCardEntity_Georeferenced();

		/**
		 * The meta object literal for the '<em><b>Rdf Subject</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CARD_ENTITY__RDF_SUBJECT = eINSTANCE.getCardEntity_RdfSubject();

		/**
		 * The meta object literal for the '<em><b>Rdf Predicate</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CARD_ENTITY__RDF_PREDICATE = eINSTANCE.getCardEntity_RdfPredicate();

		/**
		 * The meta object literal for the '<em><b>Nested Fields</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CARD_ENTITY__NESTED_FIELDS = eINSTANCE.getCardEntity_NestedFields();

		/**
		 * The meta object literal for the '<em><b>Has Dynamic Fields</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CARD_ENTITY__HAS_DYNAMIC_FIELDS = eINSTANCE.getCardEntity_HasDynamicFields();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.DescriptionImpl <em>Description</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.DescriptionImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getDescription()
		 * @generated
		 */
		EClass DESCRIPTION = eINSTANCE.getDescription();

		/**
		 * The meta object literal for the '<em><b>Display</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DESCRIPTION__DISPLAY = eINSTANCE.getDescription_Display();

		/**
		 * The meta object literal for the '<em><b>Help</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DESCRIPTION__HELP = eINSTANCE.getDescription_Help();

		/**
		 * The meta object literal for the '<em><b>Locale</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DESCRIPTION__LOCALE = eINSTANCE.getDescription_Locale();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.FieldEntityImpl <em>Field Entity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.FieldEntityImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getFieldEntity()
		 * @generated
		 */
		EClass FIELD_ENTITY = eINSTANCE.getFieldEntity();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FIELD_ENTITY__NAME = eINSTANCE.getFieldEntity_Name();

		/**
		 * The meta object literal for the '<em><b>Short Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FIELD_ENTITY__SHORT_NAME = eINSTANCE.getFieldEntity_ShortName();

		/**
		 * The meta object literal for the '<em><b>Descriptions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FIELD_ENTITY__DESCRIPTIONS = eINSTANCE.getFieldEntity_Descriptions();

		/**
		 * The meta object literal for the '<em><b>Default Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FIELD_ENTITY__DEFAULT_VALUE = eINSTANCE.getFieldEntity_DefaultValue();

		/**
		 * The meta object literal for the '<em><b>Required</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FIELD_ENTITY__REQUIRED = eINSTANCE.getFieldEntity_Required();

		/**
		 * The meta object literal for the '<em><b>Hidden</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FIELD_ENTITY__HIDDEN = eINSTANCE.getFieldEntity_Hidden();

		/**
		 * The meta object literal for the '<em><b>Read Only</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FIELD_ENTITY__READ_ONLY = eINSTANCE.getFieldEntity_ReadOnly();

		/**
		 * The meta object literal for the '<em><b>Parent Group</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FIELD_ENTITY__PARENT_GROUP = eINSTANCE.getFieldEntity_ParentGroup();

		/**
		 * The meta object literal for the '<em><b>Calculation Function Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FIELD_ENTITY__CALCULATION_FUNCTION_NAME = eINSTANCE.getFieldEntity_CalculationFunctionName();

		/**
		 * The meta object literal for the '<em><b>Field Dependent Visibility</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FIELD_ENTITY__FIELD_DEPENDENT_VISIBILITY = eINSTANCE.getFieldEntity_FieldDependentVisibility();

		/**
		 * The meta object literal for the '<em><b>Rdf Predicate</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FIELD_ENTITY__RDF_PREDICATE = eINSTANCE.getFieldEntity_RdfPredicate();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.NumericFieldImpl <em>Numeric Field</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.NumericFieldImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getNumericField()
		 * @generated
		 */
		EClass NUMERIC_FIELD = eINSTANCE.getNumericField();

		/**
		 * The meta object literal for the '<em><b>Min</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NUMERIC_FIELD__MIN = eINSTANCE.getNumericField_Min();

		/**
		 * The meta object literal for the '<em><b>Max</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NUMERIC_FIELD__MAX = eINSTANCE.getNumericField_Max();

		/**
		 * The meta object literal for the '<em><b>Unit</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NUMERIC_FIELD__UNIT = eINSTANCE.getNumericField_Unit();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.StringFieldImpl <em>String Field</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.StringFieldImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getStringField()
		 * @generated
		 */
		EClass STRING_FIELD = eINSTANCE.getStringField();

		/**
		 * The meta object literal for the '<em><b>Validation Rules</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRING_FIELD__VALIDATION_RULES = eINSTANCE.getStringField_ValidationRules();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.MediaFileFieldImpl <em>Media File Field</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.MediaFileFieldImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getMediaFileField()
		 * @generated
		 */
		EClass MEDIA_FILE_FIELD = eINSTANCE.getMediaFileField();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.RelationFieldEntityImpl <em>Relation Field Entity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.RelationFieldEntityImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getRelationFieldEntity()
		 * @generated
		 */
		EClass RELATION_FIELD_ENTITY = eINSTANCE.getRelationFieldEntity();

		/**
		 * The meta object literal for the '<em><b>Entity</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RELATION_FIELD_ENTITY__ENTITY = eINSTANCE.getRelationFieldEntity_Entity();

		/**
		 * The meta object literal for the '<em><b>Cardinality</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RELATION_FIELD_ENTITY__CARDINALITY = eINSTANCE.getRelationFieldEntity_Cardinality();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RELATION_FIELD_ENTITY__TYPE = eINSTANCE.getRelationFieldEntity_Type();

		/**
		 * The meta object literal for the '<em><b>Common Fields</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RELATION_FIELD_ENTITY__COMMON_FIELDS = eINSTANCE.getRelationFieldEntity_CommonFields();

		/**
		 * The meta object literal for the '<em><b>Opposite Relation Field</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RELATION_FIELD_ENTITY__OPPOSITE_RELATION_FIELD = eINSTANCE.getRelationFieldEntity_OppositeRelationField();

		/**
		 * The meta object literal for the '<em><b>Relation Hierarchical Filter</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RELATION_FIELD_ENTITY__RELATION_HIERARCHICAL_FILTER = eINSTANCE.getRelationFieldEntity_RelationHierarchicalFilter();

		/**
		 * The meta object literal for the '<em><b>Nested Form</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RELATION_FIELD_ENTITY__NESTED_FORM = eINSTANCE.getRelationFieldEntity_NestedForm();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.MainRelationFieldEntityImpl <em>Main Relation Field Entity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.MainRelationFieldEntityImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getMainRelationFieldEntity()
		 * @generated
		 */
		EClass MAIN_RELATION_FIELD_ENTITY = eINSTANCE.getMainRelationFieldEntity();

		/**
		 * The meta object literal for the '<em><b>Inverse Cardinality</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAIN_RELATION_FIELD_ENTITY__INVERSE_CARDINALITY = eINSTANCE.getMainRelationFieldEntity_InverseCardinality();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.ReverseRelationFieldEntityImpl <em>Reverse Relation Field Entity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.ReverseRelationFieldEntityImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getReverseRelationFieldEntity()
		 * @generated
		 */
		EClass REVERSE_RELATION_FIELD_ENTITY = eINSTANCE.getReverseRelationFieldEntity();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.IntegerFieldImpl <em>Integer Field</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.IntegerFieldImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getIntegerField()
		 * @generated
		 */
		EClass INTEGER_FIELD = eINSTANCE.getIntegerField();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.FloatFieldImpl <em>Float Field</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.FloatFieldImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getFloatField()
		 * @generated
		 */
		EClass FLOAT_FIELD = eINSTANCE.getFloatField();

		/**
		 * The meta object literal for the '<em><b>Decimal Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FLOAT_FIELD__DECIMAL_NUMBER = eINSTANCE.getFloatField_DecimalNumber();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.EnumFieldImpl <em>Enum Field</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.EnumFieldImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getEnumField()
		 * @generated
		 */
		EClass ENUM_FIELD = eINSTANCE.getEnumField();

		/**
		 * The meta object literal for the '<em><b>Enum Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENUM_FIELD__ENUM_VALUES = eINSTANCE.getEnumField_EnumValues();

		/**
		 * The meta object literal for the '<em><b>Multiple Selection</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENUM_FIELD__MULTIPLE_SELECTION = eINSTANCE.getEnumField_MultipleSelection();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.EmailFieldImpl <em>Email Field</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.EmailFieldImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getEmailField()
		 * @generated
		 */
		EClass EMAIL_FIELD = eINSTANCE.getEmailField();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.VideoFieldImpl <em>Video Field</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.VideoFieldImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getVideoField()
		 * @generated
		 */
		EClass VIDEO_FIELD = eINSTANCE.getVideoField();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.BinaryFieldImpl <em>Binary Field</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.BinaryFieldImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getBinaryField()
		 * @generated
		 */
		EClass BINARY_FIELD = eINSTANCE.getBinaryField();

		/**
		 * The meta object literal for the '<em><b>Category</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BINARY_FIELD__CATEGORY = eINSTANCE.getBinaryField_Category();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.DateFieldImpl <em>Date Field</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.DateFieldImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getDateField()
		 * @generated
		 */
		EClass DATE_FIELD = eINSTANCE.getDateField();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.DateTimeFieldImpl <em>Date Time Field</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.DateTimeFieldImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getDateTimeField()
		 * @generated
		 */
		EClass DATE_TIME_FIELD = eINSTANCE.getDateTimeField();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.TimeFieldImpl <em>Time Field</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.TimeFieldImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getTimeField()
		 * @generated
		 */
		EClass TIME_FIELD = eINSTANCE.getTimeField();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.ProjectImpl <em>Project</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.ProjectImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getProject()
		 * @generated
		 */
		EClass PROJECT = eINSTANCE.getProject();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT__NAME = eINSTANCE.getProject_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT__DESCRIPTION = eINSTANCE.getProject_Description();

		/**
		 * The meta object literal for the '<em><b>Entities</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT__ENTITIES = eINSTANCE.getProject_Entities();

		/**
		 * The meta object literal for the '<em><b>Entity UI Formats</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT__ENTITY_UI_FORMATS = eINSTANCE.getProject_EntityUIFormats();

		/**
		 * The meta object literal for the '<em><b>Themas</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT__THEMAS = eINSTANCE.getProject_Themas();

		/**
		 * The meta object literal for the '<em><b>Languages</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT__LANGUAGES = eINSTANCE.getProject_Languages();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.DatesFieldImpl <em>Dates Field</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.DatesFieldImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getDatesField()
		 * @generated
		 */
		EClass DATES_FIELD = eINSTANCE.getDatesField();

		/**
		 * The meta object literal for the '<em><b>Min</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATES_FIELD__MIN = eINSTANCE.getDatesField_Min();

		/**
		 * The meta object literal for the '<em><b>Max</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATES_FIELD__MAX = eINSTANCE.getDatesField_Max();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.PhoneFieldImpl <em>Phone Field</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.PhoneFieldImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getPhoneField()
		 * @generated
		 */
		EClass PHONE_FIELD = eINSTANCE.getPhoneField();

		/**
		 * The meta object literal for the '<em><b>Phone Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PHONE_FIELD__PHONE_TYPE = eINSTANCE.getPhoneField_PhoneType();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.PhotoFieldImpl <em>Photo Field</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.PhotoFieldImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getPhotoField()
		 * @generated
		 */
		EClass PHOTO_FIELD = eINSTANCE.getPhotoField();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.SoundFieldImpl <em>Sound Field</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.SoundFieldImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getSoundField()
		 * @generated
		 */
		EClass SOUND_FIELD = eINSTANCE.getSoundField();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.BooleanFieldImpl <em>Boolean Field</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.BooleanFieldImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getBooleanField()
		 * @generated
		 */
		EClass BOOLEAN_FIELD = eINSTANCE.getBooleanField();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.EnumValueImpl <em>Enum Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.EnumValueImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getEnumValue()
		 * @generated
		 */
		EClass ENUM_VALUE = eINSTANCE.getEnumValue();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENUM_VALUE__VALUE = eINSTANCE.getEnumValue_Value();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENUM_VALUE__NAME = eINSTANCE.getEnumValue_Name();

		/**
		 * The meta object literal for the '<em><b>Descriptions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENUM_VALUE__DESCRIPTIONS = eINSTANCE.getEnumValue_Descriptions();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.TextFieldImpl <em>Text Field</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.TextFieldImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getTextField()
		 * @generated
		 */
		EClass TEXT_FIELD = eINSTANCE.getTextField();

		/**
		 * The meta object literal for the '<em><b>Translatable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEXT_FIELD__TRANSLATABLE = eINSTANCE.getTextField_Translatable();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.TextAreaFieldImpl <em>Text Area Field</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.TextAreaFieldImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getTextAreaField()
		 * @generated
		 */
		EClass TEXT_AREA_FIELD = eINSTANCE.getTextAreaField();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.FieldGroupImpl <em>Field Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.FieldGroupImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getFieldGroup()
		 * @generated
		 */
		EClass FIELD_GROUP = eINSTANCE.getFieldGroup();

		/**
		 * The meta object literal for the '<em><b>Descriptions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FIELD_GROUP__DESCRIPTIONS = eINSTANCE.getFieldGroup_Descriptions();

		/**
		 * The meta object literal for the '<em><b>Fields</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FIELD_GROUP__FIELDS = eINSTANCE.getFieldGroup_Fields();

		/**
		 * The meta object literal for the '<em><b>Parent Card</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FIELD_GROUP__PARENT_CARD = eINSTANCE.getFieldGroup_ParentCard();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FIELD_GROUP__NAME = eINSTANCE.getFieldGroup_Name();

		/**
		 * The meta object literal for the '<em><b>Short Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FIELD_GROUP__SHORT_NAME = eINSTANCE.getFieldGroup_ShortName();

		/**
		 * The meta object literal for the '<em><b>Icon</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FIELD_GROUP__ICON = eINSTANCE.getFieldGroup_Icon();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.ActorImpl <em>Actor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.ActorImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getActor()
		 * @generated
		 */
		EClass ACTOR = eINSTANCE.getActor();

		/**
		 * The meta object literal for the '<em><b>Notification Infos</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTOR__NOTIFICATION_INFOS = eINSTANCE.getActor_NotificationInfos();

		/**
		 * The meta object literal for the '<em><b>Admin Fields</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTOR__ADMIN_FIELDS = eINSTANCE.getActor_AdminFields();

		/**
		 * The meta object literal for the '<em><b>Filters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTOR__FILTERS = eINSTANCE.getActor_Filters();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.NotificationInfoImpl <em>Notification Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.NotificationInfoImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getNotificationInfo()
		 * @generated
		 */
		EClass NOTIFICATION_INFO = eINSTANCE.getNotificationInfo();

		/**
		 * The meta object literal for the '<em><b>Data Field</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NOTIFICATION_INFO__DATA_FIELD = eINSTANCE.getNotificationInfo_DataField();

		/**
		 * The meta object literal for the '<em><b>Method</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NOTIFICATION_INFO__METHOD = eINSTANCE.getNotificationInfo_Method();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.LanguageImpl <em>Language</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.LanguageImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getLanguage()
		 * @generated
		 */
		EClass LANGUAGE = eINSTANCE.getLanguage();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LANGUAGE__NAME = eINSTANCE.getLanguage_Name();

		/**
		 * The meta object literal for the '<em><b>Date Format</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LANGUAGE__DATE_FORMAT = eINSTANCE.getLanguage_DateFormat();

		/**
		 * The meta object literal for the '<em><b>Time Format</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LANGUAGE__TIME_FORMAT = eINSTANCE.getLanguage_TimeFormat();

		/**
		 * The meta object literal for the '<em><b>Integer Format</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LANGUAGE__INTEGER_FORMAT = eINSTANCE.getLanguage_IntegerFormat();

		/**
		 * The meta object literal for the '<em><b>Float Format</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LANGUAGE__FLOAT_FORMAT = eINSTANCE.getLanguage_FloatFormat();

		/**
		 * The meta object literal for the '<em><b>Iso Code</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LANGUAGE__ISO_CODE = eINSTANCE.getLanguage_IsoCode();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.FieldDependentVisibilityImpl <em>Field Dependent Visibility</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.FieldDependentVisibilityImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getFieldDependentVisibility()
		 * @generated
		 */
		EClass FIELD_DEPENDENT_VISIBILITY = eINSTANCE.getFieldDependentVisibility();

		/**
		 * The meta object literal for the '<em><b>Dependency Field</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FIELD_DEPENDENT_VISIBILITY__DEPENDENCY_FIELD = eINSTANCE.getFieldDependentVisibility_DependencyField();

		/**
		 * The meta object literal for the '<em><b>Dependency Field Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FIELD_DEPENDENT_VISIBILITY__DEPENDENCY_FIELD_VALUE = eINSTANCE.getFieldDependentVisibility_DependencyFieldValue();

		/**
		 * The meta object literal for the '<em><b>Parent Field Entity</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FIELD_DEPENDENT_VISIBILITY__PARENT_FIELD_ENTITY = eINSTANCE.getFieldDependentVisibility_ParentFieldEntity();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.ValidationRuleImpl <em>Validation Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.ValidationRuleImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getValidationRule()
		 * @generated
		 */
		EClass VALIDATION_RULE = eINSTANCE.getValidationRule();

		/**
		 * The meta object literal for the '<em><b>Validation Regular Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALIDATION_RULE__VALIDATION_REGULAR_EXPRESSION = eINSTANCE.getValidationRule_ValidationRegularExpression();

		/**
		 * The meta object literal for the '<em><b>Descriptions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VALIDATION_RULE__DESCRIPTIONS = eINSTANCE.getValidationRule_Descriptions();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.ActorFilterImpl <em>Actor Filter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.ActorFilterImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getActorFilter()
		 * @generated
		 */
		EClass ACTOR_FILTER = eINSTANCE.getActorFilter();

		/**
		 * The meta object literal for the '<em><b>Entity Field</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTOR_FILTER__ENTITY_FIELD = eINSTANCE.getActorFilter_EntityField();

		/**
		 * The meta object literal for the '<em><b>Actor Field</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTOR_FILTER__ACTOR_FIELD = eINSTANCE.getActorFilter_ActorField();

		/**
		 * The meta object literal for the '<em><b>Sufficient</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTOR_FILTER__SUFFICIENT = eINSTANCE.getActorFilter_Sufficient();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.FilterFieldImpl <em>Filter Field</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.FilterFieldImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getFilterField()
		 * @generated
		 */
		EClass FILTER_FIELD = eINSTANCE.getFilterField();

		/**
		 * The meta object literal for the '<em><b>Parent Actor</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FILTER_FIELD__PARENT_ACTOR = eINSTANCE.getFilterField_ParentActor();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.AddressFieldImpl <em>Address Field</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.AddressFieldImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getAddressField()
		 * @generated
		 */
		EClass ADDRESS_FIELD = eINSTANCE.getAddressField();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.GeoFieldImpl <em>Geo Field</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.GeoFieldImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getGeoField()
		 * @generated
		 */
		EClass GEO_FIELD = eINSTANCE.getGeoField();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GEO_FIELD__TYPE = eINSTANCE.getGeoField_Type();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.BarcodeFieldImpl <em>Barcode Field</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.BarcodeFieldImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getBarcodeField()
		 * @generated
		 */
		EClass BARCODE_FIELD = eINSTANCE.getBarcodeField();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.ThemaImpl <em>Thema</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.ThemaImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getThema()
		 * @generated
		 */
		EClass THEMA = eINSTANCE.getThema();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute THEMA__NAME = eINSTANCE.getThema_Name();

		/**
		 * The meta object literal for the '<em><b>Descriptions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference THEMA__DESCRIPTIONS = eINSTANCE.getThema_Descriptions();

		/**
		 * The meta object literal for the '<em><b>Entities</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference THEMA__ENTITIES = eINSTANCE.getThema_Entities();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.impl.CardEntityUIFormatImpl <em>Card Entity UI Format</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.impl.CardEntityUIFormatImpl
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getCardEntityUIFormat()
		 * @generated
		 */
		EClass CARD_ENTITY_UI_FORMAT = eINSTANCE.getCardEntityUIFormat();

		/**
		 * The meta object literal for the '<em><b>Entity</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CARD_ENTITY_UI_FORMAT__ENTITY = eINSTANCE.getCardEntityUIFormat_Entity();

		/**
		 * The meta object literal for the '<em><b>With Tabulations</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CARD_ENTITY_UI_FORMAT__WITH_TABULATIONS = eINSTANCE.getCardEntityUIFormat_WithTabulations();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.PhoneType <em>Phone Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.PhoneType
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getPhoneType()
		 * @generated
		 */
		EEnum PHONE_TYPE = eINSTANCE.getPhoneType();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.RelationType <em>Relation Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.RelationType
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getRelationType()
		 * @generated
		 */
		EEnum RELATION_TYPE = eINSTANCE.getRelationType();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.NotificationMethod <em>Notification Method</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.NotificationMethod
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getNotificationMethod()
		 * @generated
		 */
		EEnum NOTIFICATION_METHOD = eINSTANCE.getNotificationMethod();

		/**
		 * The meta object literal for the '{@link org.imogene.model.core.GeoType <em>Geo Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imogene.model.core.GeoType
		 * @see org.imogene.model.core.impl.ImogenePackageImpl#getGeoType()
		 * @generated
		 */
		EEnum GEO_TYPE = eINSTANCE.getGeoType();

	}

} //ImogenePackage
