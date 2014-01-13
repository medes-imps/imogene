/**
 * Medes-IMPS 2011
 *
 * $Id$
 */
package org.imogene.model.core.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemColorProvider;
import org.eclipse.emf.edit.provider.IItemFontProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.imogene.model.core.ImogenePackage;
import org.imogene.model.core.RelationFieldEntity;
import org.imogene.model.core.RelationType;

/**
 * This is the item provider adapter for a {@link org.imogene.model.core.RelationFieldEntity} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class RelationFieldEntityItemProvider
	extends FieldEntityItemProvider
	implements
		IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource, IItemColorProvider, IItemFontProvider {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Medes-IMPS 2011";

	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RelationFieldEntityItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addEntityPropertyDescriptor(object);
			addCardinalityPropertyDescriptor(object);
			addTypePropertyDescriptor(object);
			addCommonFieldsPropertyDescriptor(object);
			addOppositeRelationFieldPropertyDescriptor(object);
			addRelationHierarchicalFilterPropertyDescriptor(object);
			addNestedFormPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Entity feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addEntityPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_RelationFieldEntity_entity_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_RelationFieldEntity_entity_feature", "_UI_RelationFieldEntity_type"),
				 ImogenePackage.Literals.RELATION_FIELD_ENTITY__ENTITY,
				 true,
				 false,
				 false,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Cardinality feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addCardinalityPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_RelationFieldEntity_cardinality_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_RelationFieldEntity_cardinality_feature", "_UI_RelationFieldEntity_type"),
				 ImogenePackage.Literals.RELATION_FIELD_ENTITY__CARDINALITY,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Type feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_RelationFieldEntity_type_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_RelationFieldEntity_type_feature", "_UI_RelationFieldEntity_type"),
				 ImogenePackage.Literals.RELATION_FIELD_ENTITY__TYPE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Common Fields feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addCommonFieldsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_RelationFieldEntity_commonFields_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_RelationFieldEntity_commonFields_feature", "_UI_RelationFieldEntity_type"),
				 ImogenePackage.Literals.RELATION_FIELD_ENTITY__COMMON_FIELDS,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Opposite Relation Field feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addOppositeRelationFieldPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_RelationFieldEntity_oppositeRelationField_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_RelationFieldEntity_oppositeRelationField_feature", "_UI_RelationFieldEntity_type"),
				 ImogenePackage.Literals.RELATION_FIELD_ENTITY__OPPOSITE_RELATION_FIELD,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Relation Hierarchical Filter feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addRelationHierarchicalFilterPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_RelationFieldEntity_relationHierarchicalFilter_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_RelationFieldEntity_relationHierarchicalFilter_feature", "_UI_RelationFieldEntity_type"),
				 ImogenePackage.Literals.RELATION_FIELD_ENTITY__RELATION_HIERARCHICAL_FILTER,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}
	
	/**
	 * This adds a property descriptor for the Nested Form feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNestedFormPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_RelationFieldEntity_nestedForm_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_RelationFieldEntity_nestedForm_feature", "_UI_RelationFieldEntity_type"),
				 ImogenePackage.Literals.RELATION_FIELD_ENTITY__NESTED_FORM,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
     * overrided to make the DefaultValue property not settable anymore
     * @generated NOT
     */
    @Override
    protected void addDefaultValuePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
		(createItemPropertyDescriptor
			(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
			 getResourceLocator(),
			 getString("_UI_FieldEntity_defaultValue_feature"),
			 getString("_UI_PropertyDescriptor_description", "_UI_FieldEntity_defaultValue_feature", "_UI_FieldEntity_type"),
			 ImogenePackage.Literals.FIELD_ENTITY__DEFAULT_VALUE,
			 false,
			 false,
			 false,
			 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
			 null,
			 null));
    }

    /**
     * This returns RelationFieldEntity.gif. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated NOT
     */
    public Object getImage(Object object)
    {
        RelationFieldEntity rf = (RelationFieldEntity) object;
        String imagePath = "full/obj16/RelationFieldEntity";

        if (rf.getType().getValue() == RelationType.ASSOCIATION)
        {
            imagePath = (rf.getCardinality() == 1) ? "full/obj16/assoType" : "full/obj16/assonType";
        }
        else
        {
            // This is a composition. Must check if reverse field is set.
            if (rf.getOppositeRelationField() == null)
            {
                imagePath = (rf.getCardinality() == 1) ? "full/obj16/compoType-red" : "full/obj16/componType-red";
            }
            else
            {
                imagePath = (rf.getCardinality() == 1) ? "full/obj16/compoType" : "full/obj16/componType";
            }
        }
        return overlayImage(object, getResourceLocator().getImage(imagePath));
    }

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((RelationFieldEntity)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_RelationFieldEntity_type") :
			getString("_UI_RelationFieldEntity_type") + " " + label;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(RelationFieldEntity.class)) {
			case ImogenePackage.RELATION_FIELD_ENTITY__CARDINALITY:
			case ImogenePackage.RELATION_FIELD_ENTITY__TYPE:
			case ImogenePackage.RELATION_FIELD_ENTITY__NESTED_FORM:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

}
