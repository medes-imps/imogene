/**
 * Medes-IMPS 2011
 *
 * $Id$
 */
package org.imogene.model.core.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.imogene.model.core.Actor;
import org.imogene.model.core.ActorFilter;
import org.imogene.model.core.AddressField;
import org.imogene.model.core.BarcodeField;
import org.imogene.model.core.BinaryField;
import org.imogene.model.core.BooleanField;
import org.imogene.model.core.CardEntity;
import org.imogene.model.core.CardEntityUIFormat;
import org.imogene.model.core.DateField;
import org.imogene.model.core.DateTimeField;
import org.imogene.model.core.DatesField;
import org.imogene.model.core.Description;
import org.imogene.model.core.EmailField;
import org.imogene.model.core.EnumField;
import org.imogene.model.core.EnumValue;
import org.imogene.model.core.FieldDependentVisibility;
import org.imogene.model.core.FieldEntity;
import org.imogene.model.core.FieldGroup;
import org.imogene.model.core.FilterField;
import org.imogene.model.core.FloatField;
import org.imogene.model.core.GeoField;
import org.imogene.model.core.GeoType;
import org.imogene.model.core.ImogeneFactory;
import org.imogene.model.core.ImogenePackage;
import org.imogene.model.core.IntegerField;
import org.imogene.model.core.Language;
import org.imogene.model.core.MainRelationFieldEntity;
import org.imogene.model.core.MediaFileField;
import org.imogene.model.core.NotificationInfo;
import org.imogene.model.core.NotificationMethod;
import org.imogene.model.core.NumericField;
import org.imogene.model.core.PhoneField;
import org.imogene.model.core.PhoneType;
import org.imogene.model.core.PhotoField;
import org.imogene.model.core.Project;
import org.imogene.model.core.RelationFieldEntity;
import org.imogene.model.core.RelationType;
import org.imogene.model.core.ReverseRelationFieldEntity;
import org.imogene.model.core.SoundField;
import org.imogene.model.core.StringField;
import org.imogene.model.core.TextAreaField;
import org.imogene.model.core.TextField;
import org.imogene.model.core.Thema;
import org.imogene.model.core.TimeField;
import org.imogene.model.core.ValidationRule;
import org.imogene.model.core.VideoField;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ImogenePackageImpl extends EPackageImpl implements ImogenePackage {
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
	private EClass cardEntityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass descriptionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fieldEntityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass numericFieldEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringFieldEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mediaFileFieldEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass relationFieldEntityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mainRelationFieldEntityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass reverseRelationFieldEntityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass integerFieldEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass floatFieldEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enumFieldEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass emailFieldEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass videoFieldEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass binaryFieldEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dateFieldEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dateTimeFieldEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass timeFieldEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass projectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass datesFieldEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass phoneFieldEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass photoFieldEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass soundFieldEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass booleanFieldEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enumValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass textFieldEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass textAreaFieldEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fieldGroupEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass actorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass notificationInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass languageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fieldDependentVisibilityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass validationRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass actorFilterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass filterFieldEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass addressFieldEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass geoFieldEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass barcodeFieldEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass themaEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cardEntityUIFormatEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum phoneTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum relationTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum notificationMethodEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum geoTypeEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.imogene.model.core.ImogenePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ImogenePackageImpl() {
		super(eNS_URI, ImogeneFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link ImogenePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ImogenePackage init() {
		if (isInited) return (ImogenePackage)EPackage.Registry.INSTANCE.getEPackage(ImogenePackage.eNS_URI);

		// Obtain or create and register package
		ImogenePackageImpl theImogenePackage = (ImogenePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ImogenePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ImogenePackageImpl());

		isInited = true;

		// Create package meta-data objects
		theImogenePackage.createPackageContents();

		// Initialize created meta-data
		theImogenePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theImogenePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ImogenePackage.eNS_URI, theImogenePackage);
		return theImogenePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCardEntity() {
		return cardEntityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCardEntity_Name() {
		return (EAttribute)cardEntityEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCardEntity_Descriptions() {
		return (EReference)cardEntityEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCardEntity_MainFields() {
		return (EReference)cardEntityEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCardEntity_ColumnFields() {
		return (EReference)cardEntityEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCardEntity_ShortName() {
		return (EAttribute)cardEntityEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCardEntity_Icon() {
		return (EAttribute)cardEntityEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCardEntity_TopLevel() {
		return (EAttribute)cardEntityEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCardEntity_ClientFilterFields() {
		return (EReference)cardEntityEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCardEntity_Groups() {
		return (EReference)cardEntityEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCardEntity_SortFields() {
		return (EReference)cardEntityEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCardEntity_ActorFilterFields() {
		return (EReference)cardEntityEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCardEntity_SecondaryFields() {
		return (EReference)cardEntityEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCardEntity_Color() {
		return (EAttribute)cardEntityEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCardEntity_ClientPeriodFilterable() {
		return (EAttribute)cardEntityEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCardEntity_Georeferenced() {
		return (EReference)cardEntityEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCardEntity_RdfSubject() {
		return (EReference)cardEntityEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCardEntity_RdfPredicate() {
		return (EAttribute)cardEntityEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCardEntity_NestedFields() {
		return (EReference)cardEntityEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCardEntity_HasDynamicFields() {
		return (EAttribute)cardEntityEClass.getEStructuralFeatures().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDescription() {
		return descriptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDescription_Display() {
		return (EAttribute)descriptionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDescription_Help() {
		return (EAttribute)descriptionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDescription_Locale() {
		return (EAttribute)descriptionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFieldEntity() {
		return fieldEntityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFieldEntity_Name() {
		return (EAttribute)fieldEntityEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFieldEntity_ShortName() {
		return (EAttribute)fieldEntityEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFieldEntity_Descriptions() {
		return (EReference)fieldEntityEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFieldEntity_DefaultValue() {
		return (EAttribute)fieldEntityEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFieldEntity_Required() {
		return (EAttribute)fieldEntityEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFieldEntity_Hidden() {
		return (EAttribute)fieldEntityEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFieldEntity_ReadOnly() {
		return (EAttribute)fieldEntityEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFieldEntity_ParentGroup() {
		return (EReference)fieldEntityEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFieldEntity_CalculationFunctionName() {
		return (EAttribute)fieldEntityEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFieldEntity_FieldDependentVisibility() {
		return (EReference)fieldEntityEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFieldEntity_RdfPredicate() {
		return (EAttribute)fieldEntityEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNumericField() {
		return numericFieldEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNumericField_Min() {
		return (EAttribute)numericFieldEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNumericField_Max() {
		return (EAttribute)numericFieldEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNumericField_Unit() {
		return (EAttribute)numericFieldEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStringField() {
		return stringFieldEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStringField_ValidationRules() {
		return (EReference)stringFieldEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMediaFileField() {
		return mediaFileFieldEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRelationFieldEntity() {
		return relationFieldEntityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRelationFieldEntity_Entity() {
		return (EReference)relationFieldEntityEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRelationFieldEntity_Cardinality() {
		return (EAttribute)relationFieldEntityEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRelationFieldEntity_Type() {
		return (EAttribute)relationFieldEntityEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRelationFieldEntity_CommonFields() {
		return (EReference)relationFieldEntityEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRelationFieldEntity_OppositeRelationField() {
		return (EReference)relationFieldEntityEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRelationFieldEntity_RelationHierarchicalFilter() {
		return (EReference)relationFieldEntityEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRelationFieldEntity_NestedForm() {
		return (EAttribute)relationFieldEntityEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMainRelationFieldEntity() {
		return mainRelationFieldEntityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMainRelationFieldEntity_InverseCardinality() {
		return (EAttribute)mainRelationFieldEntityEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReverseRelationFieldEntity() {
		return reverseRelationFieldEntityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIntegerField() {
		return integerFieldEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFloatField() {
		return floatFieldEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFloatField_DecimalNumber() {
		return (EAttribute)floatFieldEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEnumField() {
		return enumFieldEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEnumField_EnumValues() {
		return (EReference)enumFieldEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEnumField_MultipleSelection() {
		return (EAttribute)enumFieldEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEmailField() {
		return emailFieldEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVideoField() {
		return videoFieldEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBinaryField() {
		return binaryFieldEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBinaryField_Category() {
		return (EAttribute)binaryFieldEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDateField() {
		return dateFieldEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDateTimeField() {
		return dateTimeFieldEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTimeField() {
		return timeFieldEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProject() {
		return projectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProject_Name() {
		return (EAttribute)projectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProject_Description() {
		return (EReference)projectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProject_Entities() {
		return (EReference)projectEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProject_EntityUIFormats() {
		return (EReference)projectEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProject_Themas() {
		return (EReference)projectEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProject_Languages() {
		return (EReference)projectEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDatesField() {
		return datesFieldEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDatesField_Min() {
		return (EAttribute)datesFieldEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDatesField_Max() {
		return (EAttribute)datesFieldEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPhoneField() {
		return phoneFieldEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPhoneField_PhoneType() {
		return (EAttribute)phoneFieldEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPhotoField() {
		return photoFieldEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSoundField() {
		return soundFieldEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBooleanField() {
		return booleanFieldEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEnumValue() {
		return enumValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEnumValue_Value() {
		return (EAttribute)enumValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEnumValue_Name() {
		return (EAttribute)enumValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEnumValue_Descriptions() {
		return (EReference)enumValueEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTextField() {
		return textFieldEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTextField_Translatable() {
		return (EAttribute)textFieldEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTextAreaField() {
		return textAreaFieldEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFieldGroup() {
		return fieldGroupEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFieldGroup_Descriptions() {
		return (EReference)fieldGroupEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFieldGroup_Fields() {
		return (EReference)fieldGroupEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFieldGroup_ParentCard() {
		return (EReference)fieldGroupEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFieldGroup_Name() {
		return (EAttribute)fieldGroupEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFieldGroup_ShortName() {
		return (EAttribute)fieldGroupEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFieldGroup_Icon() {
		return (EAttribute)fieldGroupEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getActor() {
		return actorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActor_NotificationInfos() {
		return (EReference)actorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActor_AdminFields() {
		return (EReference)actorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActor_Filters() {
		return (EReference)actorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNotificationInfo() {
		return notificationInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNotificationInfo_DataField() {
		return (EReference)notificationInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNotificationInfo_Method() {
		return (EAttribute)notificationInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLanguage() {
		return languageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLanguage_Name() {
		return (EAttribute)languageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLanguage_DateFormat() {
		return (EAttribute)languageEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLanguage_TimeFormat() {
		return (EAttribute)languageEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLanguage_IntegerFormat() {
		return (EAttribute)languageEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLanguage_FloatFormat() {
		return (EAttribute)languageEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLanguage_IsoCode() {
		return (EAttribute)languageEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFieldDependentVisibility() {
		return fieldDependentVisibilityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFieldDependentVisibility_DependencyField() {
		return (EReference)fieldDependentVisibilityEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFieldDependentVisibility_DependencyFieldValue() {
		return (EAttribute)fieldDependentVisibilityEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFieldDependentVisibility_ParentFieldEntity() {
		return (EReference)fieldDependentVisibilityEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getValidationRule() {
		return validationRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getValidationRule_ValidationRegularExpression() {
		return (EAttribute)validationRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getValidationRule_Descriptions() {
		return (EReference)validationRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getActorFilter() {
		return actorFilterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActorFilter_EntityField() {
		return (EReference)actorFilterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActorFilter_ActorField() {
		return (EReference)actorFilterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getActorFilter_Sufficient() {
		return (EAttribute)actorFilterEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFilterField() {
		return filterFieldEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFilterField_ParentActor() {
		return (EReference)filterFieldEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAddressField() {
		return addressFieldEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGeoField() {
		return geoFieldEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGeoField_Type() {
		return (EAttribute)geoFieldEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBarcodeField() {
		return barcodeFieldEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getThema() {
		return themaEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getThema_Name() {
		return (EAttribute)themaEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getThema_Descriptions() {
		return (EReference)themaEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getThema_Entities() {
		return (EReference)themaEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCardEntityUIFormat() {
		return cardEntityUIFormatEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCardEntityUIFormat_Entity() {
		return (EReference)cardEntityUIFormatEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCardEntityUIFormat_WithTabulations() {
		return (EAttribute)cardEntityUIFormatEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getPhoneType() {
		return phoneTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getRelationType() {
		return relationTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getNotificationMethod() {
		return notificationMethodEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getGeoType() {
		return geoTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImogeneFactory getImogeneFactory() {
		return (ImogeneFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		projectEClass = createEClass(PROJECT);
		createEAttribute(projectEClass, PROJECT__NAME);
		createEReference(projectEClass, PROJECT__DESCRIPTION);
		createEReference(projectEClass, PROJECT__ENTITIES);
		createEReference(projectEClass, PROJECT__ENTITY_UI_FORMATS);
		createEReference(projectEClass, PROJECT__THEMAS);
		createEReference(projectEClass, PROJECT__LANGUAGES);

		cardEntityEClass = createEClass(CARD_ENTITY);
		createEAttribute(cardEntityEClass, CARD_ENTITY__NAME);
		createEAttribute(cardEntityEClass, CARD_ENTITY__SHORT_NAME);
		createEReference(cardEntityEClass, CARD_ENTITY__DESCRIPTIONS);
		createEReference(cardEntityEClass, CARD_ENTITY__COLUMN_FIELDS);
		createEReference(cardEntityEClass, CARD_ENTITY__MAIN_FIELDS);
		createEReference(cardEntityEClass, CARD_ENTITY__SECONDARY_FIELDS);
		createEAttribute(cardEntityEClass, CARD_ENTITY__ICON);
		createEAttribute(cardEntityEClass, CARD_ENTITY__TOP_LEVEL);
		createEReference(cardEntityEClass, CARD_ENTITY__CLIENT_FILTER_FIELDS);
		createEReference(cardEntityEClass, CARD_ENTITY__GROUPS);
		createEReference(cardEntityEClass, CARD_ENTITY__SORT_FIELDS);
		createEReference(cardEntityEClass, CARD_ENTITY__ACTOR_FILTER_FIELDS);
		createEAttribute(cardEntityEClass, CARD_ENTITY__COLOR);
		createEAttribute(cardEntityEClass, CARD_ENTITY__CLIENT_PERIOD_FILTERABLE);
		createEReference(cardEntityEClass, CARD_ENTITY__GEOREFERENCED);
		createEReference(cardEntityEClass, CARD_ENTITY__RDF_SUBJECT);
		createEAttribute(cardEntityEClass, CARD_ENTITY__RDF_PREDICATE);
		createEReference(cardEntityEClass, CARD_ENTITY__NESTED_FIELDS);
		createEAttribute(cardEntityEClass, CARD_ENTITY__HAS_DYNAMIC_FIELDS);

		fieldGroupEClass = createEClass(FIELD_GROUP);
		createEReference(fieldGroupEClass, FIELD_GROUP__DESCRIPTIONS);
		createEReference(fieldGroupEClass, FIELD_GROUP__FIELDS);
		createEReference(fieldGroupEClass, FIELD_GROUP__PARENT_CARD);
		createEAttribute(fieldGroupEClass, FIELD_GROUP__NAME);
		createEAttribute(fieldGroupEClass, FIELD_GROUP__SHORT_NAME);
		createEAttribute(fieldGroupEClass, FIELD_GROUP__ICON);

		fieldEntityEClass = createEClass(FIELD_ENTITY);
		createEAttribute(fieldEntityEClass, FIELD_ENTITY__NAME);
		createEAttribute(fieldEntityEClass, FIELD_ENTITY__SHORT_NAME);
		createEReference(fieldEntityEClass, FIELD_ENTITY__DESCRIPTIONS);
		createEAttribute(fieldEntityEClass, FIELD_ENTITY__DEFAULT_VALUE);
		createEAttribute(fieldEntityEClass, FIELD_ENTITY__REQUIRED);
		createEAttribute(fieldEntityEClass, FIELD_ENTITY__HIDDEN);
		createEAttribute(fieldEntityEClass, FIELD_ENTITY__READ_ONLY);
		createEReference(fieldEntityEClass, FIELD_ENTITY__PARENT_GROUP);
		createEAttribute(fieldEntityEClass, FIELD_ENTITY__CALCULATION_FUNCTION_NAME);
		createEReference(fieldEntityEClass, FIELD_ENTITY__FIELD_DEPENDENT_VISIBILITY);
		createEAttribute(fieldEntityEClass, FIELD_ENTITY__RDF_PREDICATE);

		booleanFieldEClass = createEClass(BOOLEAN_FIELD);

		numericFieldEClass = createEClass(NUMERIC_FIELD);
		createEAttribute(numericFieldEClass, NUMERIC_FIELD__MIN);
		createEAttribute(numericFieldEClass, NUMERIC_FIELD__MAX);
		createEAttribute(numericFieldEClass, NUMERIC_FIELD__UNIT);

		integerFieldEClass = createEClass(INTEGER_FIELD);

		floatFieldEClass = createEClass(FLOAT_FIELD);
		createEAttribute(floatFieldEClass, FLOAT_FIELD__DECIMAL_NUMBER);

		stringFieldEClass = createEClass(STRING_FIELD);
		createEReference(stringFieldEClass, STRING_FIELD__VALIDATION_RULES);

		validationRuleEClass = createEClass(VALIDATION_RULE);
		createEAttribute(validationRuleEClass, VALIDATION_RULE__VALIDATION_REGULAR_EXPRESSION);
		createEReference(validationRuleEClass, VALIDATION_RULE__DESCRIPTIONS);

		addressFieldEClass = createEClass(ADDRESS_FIELD);

		emailFieldEClass = createEClass(EMAIL_FIELD);

		geoFieldEClass = createEClass(GEO_FIELD);
		createEAttribute(geoFieldEClass, GEO_FIELD__TYPE);

		textFieldEClass = createEClass(TEXT_FIELD);
		createEAttribute(textFieldEClass, TEXT_FIELD__TRANSLATABLE);

		textAreaFieldEClass = createEClass(TEXT_AREA_FIELD);

		barcodeFieldEClass = createEClass(BARCODE_FIELD);

		phoneFieldEClass = createEClass(PHONE_FIELD);
		createEAttribute(phoneFieldEClass, PHONE_FIELD__PHONE_TYPE);

		mediaFileFieldEClass = createEClass(MEDIA_FILE_FIELD);

		relationFieldEntityEClass = createEClass(RELATION_FIELD_ENTITY);
		createEReference(relationFieldEntityEClass, RELATION_FIELD_ENTITY__ENTITY);
		createEAttribute(relationFieldEntityEClass, RELATION_FIELD_ENTITY__CARDINALITY);
		createEAttribute(relationFieldEntityEClass, RELATION_FIELD_ENTITY__TYPE);
		createEReference(relationFieldEntityEClass, RELATION_FIELD_ENTITY__COMMON_FIELDS);
		createEReference(relationFieldEntityEClass, RELATION_FIELD_ENTITY__OPPOSITE_RELATION_FIELD);
		createEReference(relationFieldEntityEClass, RELATION_FIELD_ENTITY__RELATION_HIERARCHICAL_FILTER);
		createEAttribute(relationFieldEntityEClass, RELATION_FIELD_ENTITY__NESTED_FORM);

		mainRelationFieldEntityEClass = createEClass(MAIN_RELATION_FIELD_ENTITY);
		createEAttribute(mainRelationFieldEntityEClass, MAIN_RELATION_FIELD_ENTITY__INVERSE_CARDINALITY);

		reverseRelationFieldEntityEClass = createEClass(REVERSE_RELATION_FIELD_ENTITY);

		enumFieldEClass = createEClass(ENUM_FIELD);
		createEReference(enumFieldEClass, ENUM_FIELD__ENUM_VALUES);
		createEAttribute(enumFieldEClass, ENUM_FIELD__MULTIPLE_SELECTION);

		enumValueEClass = createEClass(ENUM_VALUE);
		createEAttribute(enumValueEClass, ENUM_VALUE__VALUE);
		createEAttribute(enumValueEClass, ENUM_VALUE__NAME);
		createEReference(enumValueEClass, ENUM_VALUE__DESCRIPTIONS);

		binaryFieldEClass = createEClass(BINARY_FIELD);
		createEAttribute(binaryFieldEClass, BINARY_FIELD__CATEGORY);

		videoFieldEClass = createEClass(VIDEO_FIELD);

		photoFieldEClass = createEClass(PHOTO_FIELD);

		soundFieldEClass = createEClass(SOUND_FIELD);

		datesFieldEClass = createEClass(DATES_FIELD);
		createEAttribute(datesFieldEClass, DATES_FIELD__MIN);
		createEAttribute(datesFieldEClass, DATES_FIELD__MAX);

		dateFieldEClass = createEClass(DATE_FIELD);

		dateTimeFieldEClass = createEClass(DATE_TIME_FIELD);

		timeFieldEClass = createEClass(TIME_FIELD);

		actorEClass = createEClass(ACTOR);
		createEReference(actorEClass, ACTOR__NOTIFICATION_INFOS);
		createEReference(actorEClass, ACTOR__ADMIN_FIELDS);
		createEReference(actorEClass, ACTOR__FILTERS);

		filterFieldEClass = createEClass(FILTER_FIELD);
		createEReference(filterFieldEClass, FILTER_FIELD__PARENT_ACTOR);

		notificationInfoEClass = createEClass(NOTIFICATION_INFO);
		createEReference(notificationInfoEClass, NOTIFICATION_INFO__DATA_FIELD);
		createEAttribute(notificationInfoEClass, NOTIFICATION_INFO__METHOD);

		descriptionEClass = createEClass(DESCRIPTION);
		createEAttribute(descriptionEClass, DESCRIPTION__DISPLAY);
		createEAttribute(descriptionEClass, DESCRIPTION__HELP);
		createEAttribute(descriptionEClass, DESCRIPTION__LOCALE);

		languageEClass = createEClass(LANGUAGE);
		createEAttribute(languageEClass, LANGUAGE__NAME);
		createEAttribute(languageEClass, LANGUAGE__DATE_FORMAT);
		createEAttribute(languageEClass, LANGUAGE__TIME_FORMAT);
		createEAttribute(languageEClass, LANGUAGE__INTEGER_FORMAT);
		createEAttribute(languageEClass, LANGUAGE__FLOAT_FORMAT);
		createEAttribute(languageEClass, LANGUAGE__ISO_CODE);

		fieldDependentVisibilityEClass = createEClass(FIELD_DEPENDENT_VISIBILITY);
		createEReference(fieldDependentVisibilityEClass, FIELD_DEPENDENT_VISIBILITY__DEPENDENCY_FIELD);
		createEAttribute(fieldDependentVisibilityEClass, FIELD_DEPENDENT_VISIBILITY__DEPENDENCY_FIELD_VALUE);
		createEReference(fieldDependentVisibilityEClass, FIELD_DEPENDENT_VISIBILITY__PARENT_FIELD_ENTITY);

		actorFilterEClass = createEClass(ACTOR_FILTER);
		createEReference(actorFilterEClass, ACTOR_FILTER__ENTITY_FIELD);
		createEReference(actorFilterEClass, ACTOR_FILTER__ACTOR_FIELD);
		createEAttribute(actorFilterEClass, ACTOR_FILTER__SUFFICIENT);

		themaEClass = createEClass(THEMA);
		createEAttribute(themaEClass, THEMA__NAME);
		createEReference(themaEClass, THEMA__DESCRIPTIONS);
		createEReference(themaEClass, THEMA__ENTITIES);

		cardEntityUIFormatEClass = createEClass(CARD_ENTITY_UI_FORMAT);
		createEReference(cardEntityUIFormatEClass, CARD_ENTITY_UI_FORMAT__ENTITY);
		createEAttribute(cardEntityUIFormatEClass, CARD_ENTITY_UI_FORMAT__WITH_TABULATIONS);

		// Create enums
		geoTypeEEnum = createEEnum(GEO_TYPE);
		phoneTypeEEnum = createEEnum(PHONE_TYPE);
		relationTypeEEnum = createEEnum(RELATION_TYPE);
		notificationMethodEEnum = createEEnum(NOTIFICATION_METHOD);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		booleanFieldEClass.getESuperTypes().add(this.getFieldEntity());
		numericFieldEClass.getESuperTypes().add(this.getFieldEntity());
		integerFieldEClass.getESuperTypes().add(this.getNumericField());
		floatFieldEClass.getESuperTypes().add(this.getNumericField());
		stringFieldEClass.getESuperTypes().add(this.getFieldEntity());
		addressFieldEClass.getESuperTypes().add(this.getTextAreaField());
		emailFieldEClass.getESuperTypes().add(this.getTextField());
		geoFieldEClass.getESuperTypes().add(this.getStringField());
		textFieldEClass.getESuperTypes().add(this.getStringField());
		textAreaFieldEClass.getESuperTypes().add(this.getTextField());
		barcodeFieldEClass.getESuperTypes().add(this.getStringField());
		phoneFieldEClass.getESuperTypes().add(this.getTextField());
		mediaFileFieldEClass.getESuperTypes().add(this.getBinaryField());
		relationFieldEntityEClass.getESuperTypes().add(this.getFieldEntity());
		mainRelationFieldEntityEClass.getESuperTypes().add(this.getRelationFieldEntity());
		reverseRelationFieldEntityEClass.getESuperTypes().add(this.getRelationFieldEntity());
		enumFieldEClass.getESuperTypes().add(this.getFieldEntity());
		binaryFieldEClass.getESuperTypes().add(this.getFieldEntity());
		videoFieldEClass.getESuperTypes().add(this.getMediaFileField());
		photoFieldEClass.getESuperTypes().add(this.getMediaFileField());
		soundFieldEClass.getESuperTypes().add(this.getMediaFileField());
		datesFieldEClass.getESuperTypes().add(this.getFieldEntity());
		dateFieldEClass.getESuperTypes().add(this.getDatesField());
		dateTimeFieldEClass.getESuperTypes().add(this.getDatesField());
		timeFieldEClass.getESuperTypes().add(this.getDatesField());
		actorEClass.getESuperTypes().add(this.getCardEntity());
		filterFieldEClass.getESuperTypes().add(this.getRelationFieldEntity());

		// Initialize classes and features; add operations and parameters
		initEClass(projectEClass, Project.class, "Project", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProject_Name(), ecorePackage.getEString(), "name", "entities", 1, 1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProject_Description(), this.getDescription(), null, "description", null, 0, -1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProject_Entities(), this.getCardEntity(), null, "entities", null, 1, -1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProject_EntityUIFormats(), this.getCardEntityUIFormat(), null, "entityUIFormats", null, 0, -1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProject_Themas(), this.getThema(), null, "themas", null, 0, -1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProject_Languages(), this.getLanguage(), null, "languages", null, 1, -1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cardEntityEClass, CardEntity.class, "CardEntity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCardEntity_Name(), ecorePackage.getEString(), "name", null, 1, 1, CardEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCardEntity_ShortName(), ecorePackage.getEString(), "shortName", null, 1, 1, CardEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCardEntity_Descriptions(), this.getDescription(), null, "descriptions", null, 0, -1, CardEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCardEntity_ColumnFields(), this.getFieldEntity(), null, "columnFields", null, 1, -1, CardEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCardEntity_MainFields(), this.getFieldEntity(), null, "mainFields", null, 1, -1, CardEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCardEntity_SecondaryFields(), this.getFieldEntity(), null, "secondaryFields", null, 0, -1, CardEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCardEntity_Icon(), ecorePackage.getEString(), "icon", null, 0, 1, CardEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCardEntity_TopLevel(), ecorePackage.getEBoolean(), "topLevel", "true", 0, 1, CardEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCardEntity_ClientFilterFields(), this.getFieldEntity(), null, "clientFilterFields", null, 0, -1, CardEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCardEntity_Groups(), this.getFieldGroup(), this.getFieldGroup_ParentCard(), "groups", null, 1, -1, CardEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCardEntity_SortFields(), this.getFieldEntity(), null, "sortFields", null, 0, -1, CardEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCardEntity_ActorFilterFields(), this.getActorFilter(), null, "actorFilterFields", null, 0, -1, CardEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCardEntity_Color(), ecorePackage.getEString(), "color", "", 0, 1, CardEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCardEntity_ClientPeriodFilterable(), ecorePackage.getEBoolean(), "clientPeriodFilterable", "false", 0, 1, CardEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCardEntity_Georeferenced(), this.getGeoField(), null, "georeferenced", null, 0, 1, CardEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCardEntity_RdfSubject(), this.getRelationFieldEntity(), null, "rdfSubject", null, 0, 1, CardEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCardEntity_RdfPredicate(), ecorePackage.getEString(), "rdfPredicate", null, 0, 1, CardEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCardEntity_NestedFields(), this.getFieldEntity(), null, "nestedFields", null, 0, -1, CardEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCardEntity_HasDynamicFields(), ecorePackage.getEBoolean(), "hasDynamicFields", "false", 0, 1, CardEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(fieldGroupEClass, FieldGroup.class, "FieldGroup", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFieldGroup_Descriptions(), this.getDescription(), null, "descriptions", null, 0, -1, FieldGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFieldGroup_Fields(), this.getFieldEntity(), this.getFieldEntity_ParentGroup(), "fields", null, 1, -1, FieldGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFieldGroup_ParentCard(), this.getCardEntity(), this.getCardEntity_Groups(), "parentCard", null, 1, 1, FieldGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFieldGroup_Name(), ecorePackage.getEString(), "name", null, 1, 1, FieldGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFieldGroup_ShortName(), ecorePackage.getEString(), "shortName", null, 0, 1, FieldGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFieldGroup_Icon(), ecorePackage.getEString(), "icon", null, 0, 1, FieldGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(fieldGroupEClass, ecorePackage.getEBoolean(), "containsOnlyHiddenFields", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(fieldEntityEClass, FieldEntity.class, "FieldEntity", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFieldEntity_Name(), ecorePackage.getEString(), "name", null, 1, 1, FieldEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFieldEntity_ShortName(), ecorePackage.getEString(), "shortName", null, 1, 1, FieldEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFieldEntity_Descriptions(), this.getDescription(), null, "descriptions", null, 0, -1, FieldEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFieldEntity_DefaultValue(), ecorePackage.getEString(), "defaultValue", null, 0, 1, FieldEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFieldEntity_Required(), ecorePackage.getEBoolean(), "required", null, 1, 1, FieldEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFieldEntity_Hidden(), ecorePackage.getEBoolean(), "hidden", null, 0, 1, FieldEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFieldEntity_ReadOnly(), ecorePackage.getEBoolean(), "readOnly", null, 0, 1, FieldEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFieldEntity_ParentGroup(), this.getFieldGroup(), this.getFieldGroup_Fields(), "parentGroup", null, 0, 1, FieldEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFieldEntity_CalculationFunctionName(), ecorePackage.getEString(), "calculationFunctionName", null, 0, 1, FieldEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFieldEntity_FieldDependentVisibility(), this.getFieldDependentVisibility(), this.getFieldDependentVisibility_ParentFieldEntity(), "fieldDependentVisibility", null, 0, -1, FieldEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFieldEntity_RdfPredicate(), ecorePackage.getEString(), "rdfPredicate", null, 0, 1, FieldEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(fieldEntityEClass, null, "validate", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(booleanFieldEClass, BooleanField.class, "BooleanField", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(numericFieldEClass, NumericField.class, "NumericField", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNumericField_Min(), ecorePackage.getEString(), "min", "", 0, 1, NumericField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNumericField_Max(), ecorePackage.getEString(), "max", "", 0, 1, NumericField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNumericField_Unit(), ecorePackage.getEString(), "unit", null, 0, 1, NumericField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(integerFieldEClass, IntegerField.class, "IntegerField", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(floatFieldEClass, FloatField.class, "FloatField", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFloatField_DecimalNumber(), ecorePackage.getEInt(), "DecimalNumber", "-1", 0, 1, FloatField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stringFieldEClass, StringField.class, "StringField", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStringField_ValidationRules(), this.getValidationRule(), null, "validationRules", null, 0, -1, StringField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(validationRuleEClass, ValidationRule.class, "ValidationRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getValidationRule_ValidationRegularExpression(), ecorePackage.getEString(), "validationRegularExpression", null, 0, 1, ValidationRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getValidationRule_Descriptions(), this.getDescription(), null, "descriptions", null, 0, -1, ValidationRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(addressFieldEClass, AddressField.class, "AddressField", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(emailFieldEClass, EmailField.class, "EmailField", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(geoFieldEClass, GeoField.class, "GeoField", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGeoField_Type(), this.getGeoType(), "type", "", 1, 1, GeoField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(textFieldEClass, TextField.class, "TextField", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTextField_Translatable(), ecorePackage.getEBoolean(), "translatable", "false", 0, 1, TextField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(textAreaFieldEClass, TextAreaField.class, "TextAreaField", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(barcodeFieldEClass, BarcodeField.class, "BarcodeField", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(phoneFieldEClass, PhoneField.class, "PhoneField", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPhoneField_PhoneType(), this.getPhoneType(), "phoneType", "", 1, 1, PhoneField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mediaFileFieldEClass, MediaFileField.class, "MediaFileField", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(relationFieldEntityEClass, RelationFieldEntity.class, "RelationFieldEntity", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRelationFieldEntity_Entity(), this.getCardEntity(), null, "entity", null, 1, 1, RelationFieldEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRelationFieldEntity_Cardinality(), ecorePackage.getEInt(), "cardinality", "-1", 1, 1, RelationFieldEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRelationFieldEntity_Type(), this.getRelationType(), "type", null, 1, 1, RelationFieldEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRelationFieldEntity_CommonFields(), this.getRelationFieldEntity(), null, "commonFields", null, 0, -1, RelationFieldEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRelationFieldEntity_OppositeRelationField(), this.getRelationFieldEntity(), null, "oppositeRelationField", null, 0, 1, RelationFieldEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRelationFieldEntity_RelationHierarchicalFilter(), this.getRelationFieldEntity(), null, "relationHierarchicalFilter", null, 0, -1, RelationFieldEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRelationFieldEntity_NestedForm(), ecorePackage.getEBoolean(), "nestedForm", "false", 0, 1, RelationFieldEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mainRelationFieldEntityEClass, MainRelationFieldEntity.class, "MainRelationFieldEntity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMainRelationFieldEntity_InverseCardinality(), ecorePackage.getEInt(), "InverseCardinality", "-1", 0, 1, MainRelationFieldEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(reverseRelationFieldEntityEClass, ReverseRelationFieldEntity.class, "ReverseRelationFieldEntity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(enumFieldEClass, EnumField.class, "EnumField", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEnumField_EnumValues(), this.getEnumValue(), null, "enumValues", null, 2, -1, EnumField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEnumField_MultipleSelection(), ecorePackage.getEBoolean(), "multipleSelection", "false", 0, 1, EnumField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(enumValueEClass, EnumValue.class, "EnumValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEnumValue_Value(), ecorePackage.getEInt(), "value", null, 1, 1, EnumValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEnumValue_Name(), ecorePackage.getEString(), "name", "", 0, 1, EnumValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEnumValue_Descriptions(), this.getDescription(), null, "descriptions", null, 0, -1, EnumValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(binaryFieldEClass, BinaryField.class, "BinaryField", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBinaryField_Category(), ecorePackage.getEString(), "category", null, 1, 1, BinaryField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(videoFieldEClass, VideoField.class, "VideoField", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(photoFieldEClass, PhotoField.class, "PhotoField", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(soundFieldEClass, SoundField.class, "SoundField", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(datesFieldEClass, DatesField.class, "DatesField", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDatesField_Min(), ecorePackage.getEString(), "min", null, 0, 1, DatesField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDatesField_Max(), ecorePackage.getEString(), "max", null, 0, 1, DatesField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dateFieldEClass, DateField.class, "DateField", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(dateTimeFieldEClass, DateTimeField.class, "DateTimeField", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(timeFieldEClass, TimeField.class, "TimeField", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(actorEClass, Actor.class, "Actor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getActor_NotificationInfos(), this.getNotificationInfo(), null, "notificationInfos", null, 0, -1, Actor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getActor_AdminFields(), this.getFieldEntity(), null, "adminFields", null, 1, -1, Actor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getActor_Filters(), this.getFilterField(), this.getFilterField_ParentActor(), "filters", null, 0, -1, Actor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(filterFieldEClass, FilterField.class, "FilterField", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFilterField_ParentActor(), this.getActor(), this.getActor_Filters(), "parentActor", null, 0, 1, FilterField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(notificationInfoEClass, NotificationInfo.class, "NotificationInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNotificationInfo_DataField(), this.getFieldEntity(), null, "dataField", null, 1, 1, NotificationInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNotificationInfo_Method(), this.getNotificationMethod(), "method", null, 0, 1, NotificationInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(descriptionEClass, Description.class, "Description", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDescription_Display(), ecorePackage.getEString(), "display", null, 1, 1, Description.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDescription_Help(), ecorePackage.getEString(), "help", null, 0, 1, Description.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDescription_Locale(), ecorePackage.getEString(), "locale", null, 0, 1, Description.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(languageEClass, Language.class, "Language", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLanguage_Name(), ecorePackage.getEString(), "name", null, 1, 1, Language.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLanguage_DateFormat(), ecorePackage.getEString(), "dateFormat", "dd/MM/yyyy", 1, 1, Language.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLanguage_TimeFormat(), ecorePackage.getEString(), "timeFormat", "HH:mm", 1, 1, Language.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLanguage_IntegerFormat(), ecorePackage.getEString(), "integerFormat", "", 1, 1, Language.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLanguage_FloatFormat(), ecorePackage.getEString(), "floatFormat", null, 1, 1, Language.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLanguage_IsoCode(), ecorePackage.getEString(), "isoCode", null, 1, 1, Language.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(fieldDependentVisibilityEClass, FieldDependentVisibility.class, "FieldDependentVisibility", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFieldDependentVisibility_DependencyField(), this.getFieldEntity(), null, "dependencyField", null, 1, 1, FieldDependentVisibility.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFieldDependentVisibility_DependencyFieldValue(), ecorePackage.getEString(), "dependencyFieldValue", null, 1, 1, FieldDependentVisibility.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFieldDependentVisibility_ParentFieldEntity(), this.getFieldEntity(), this.getFieldEntity_FieldDependentVisibility(), "parentFieldEntity", null, 0, 1, FieldDependentVisibility.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(actorFilterEClass, ActorFilter.class, "ActorFilter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getActorFilter_EntityField(), this.getRelationFieldEntity(), null, "entityField", null, 1, 1, ActorFilter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getActorFilter_ActorField(), this.getFilterField(), null, "actorField", null, 1, 1, ActorFilter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getActorFilter_Sufficient(), ecorePackage.getEBoolean(), "sufficient", null, 0, 1, ActorFilter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(themaEClass, Thema.class, "Thema", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getThema_Name(), ecorePackage.getEString(), "name", null, 1, 1, Thema.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getThema_Descriptions(), this.getDescription(), null, "descriptions", null, 0, -1, Thema.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getThema_Entities(), this.getCardEntity(), null, "entities", null, 1, -1, Thema.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cardEntityUIFormatEClass, CardEntityUIFormat.class, "CardEntityUIFormat", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCardEntityUIFormat_Entity(), this.getCardEntity(), null, "entity", null, 0, 1, CardEntityUIFormat.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCardEntityUIFormat_WithTabulations(), ecorePackage.getEBoolean(), "withTabulations", null, 1, 1, CardEntityUIFormat.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(geoTypeEEnum, GeoType.class, "GeoType");
		addEEnumLiteral(geoTypeEEnum, GeoType.NOTHING);
		addEEnumLiteral(geoTypeEEnum, GeoType.GPS_VALUE);
		addEEnumLiteral(geoTypeEEnum, GeoType.NETWORK_TYPE);
		addEEnumLiteral(geoTypeEEnum, GeoType.MAP_TYPE);
		addEEnumLiteral(geoTypeEEnum, GeoType.BEST_TYPE);

		initEEnum(phoneTypeEEnum, PhoneType.class, "PhoneType");
		addEEnumLiteral(phoneTypeEEnum, PhoneType.PHONE_NUMBER_LITERAL);
		addEEnumLiteral(phoneTypeEEnum, PhoneType.FAX_NUMBER_LITERAL);
		addEEnumLiteral(phoneTypeEEnum, PhoneType.MOBILE_NUMBER_LITERAL);
		addEEnumLiteral(phoneTypeEEnum, PhoneType.FIXE_NUMBER_LITERAL);

		initEEnum(relationTypeEEnum, RelationType.class, "RelationType");
		addEEnumLiteral(relationTypeEEnum, RelationType.ASSOCIATION_LITERAL);
		addEEnumLiteral(relationTypeEEnum, RelationType.COMPOSITION_LITERAL);

		initEEnum(notificationMethodEEnum, NotificationMethod.class, "NotificationMethod");
		addEEnumLiteral(notificationMethodEEnum, NotificationMethod.SMS_METHOD_LITERAL);
		addEEnumLiteral(notificationMethodEEnum, NotificationMethod.MAIL_METHOD_LITERAL);
		addEEnumLiteral(notificationMethodEEnum, NotificationMethod.DEFAULT_METHOD_LITERAL);
		addEEnumLiteral(notificationMethodEEnum, NotificationMethod.VOCAL_METHOD_LITERAL);
		addEEnumLiteral(notificationMethodEEnum, NotificationMethod.WEB_SERVICE_METHOD_LITERAL);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";																		
		addAnnotation
		  (getNotificationInfo_DataField(), 
		   source, 
		   new String[] {
			 "name", "dataField",
			 "namespace", ""
		   });				
	}

} //ImogenePackageImpl
