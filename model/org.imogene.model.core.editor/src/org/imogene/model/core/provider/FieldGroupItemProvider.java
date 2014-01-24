/**
 * Medes-IMPS 2011
 *
 * $Id$
 */
package org.imogene.model.core.provider;


import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EStructuralFeature;
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
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.imogene.model.core.FieldGroup;
import org.imogene.model.core.ImogeneFactory;
import org.imogene.model.core.ImogenePackage;
import org.imogene.model.core.editor.ImogeneModelEditPlugin;

/**
 * This is the item provider adapter for a {@link org.imogene.model.core.FieldGroup} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class FieldGroupItemProvider
	extends ItemProviderAdapter
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
	public FieldGroupItemProvider(AdapterFactory adapterFactory) {
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

			addNamePropertyDescriptor(object);
			addShortNamePropertyDescriptor(object);
			addIconPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_FieldGroup_name_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_FieldGroup_name_feature", "_UI_FieldGroup_type"),
				 ImogenePackage.Literals.FIELD_GROUP__NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Short Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addShortNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_FieldGroup_shortName_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_FieldGroup_shortName_feature", "_UI_FieldGroup_type"),
				 ImogenePackage.Literals.FIELD_GROUP__SHORT_NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Icon feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addIconPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_FieldGroup_icon_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_FieldGroup_icon_feature", "_UI_FieldGroup_type"),
				 ImogenePackage.Literals.FIELD_GROUP__ICON,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(ImogenePackage.Literals.FIELD_GROUP__DESCRIPTIONS);
			childrenFeatures.add(ImogenePackage.Literals.FIELD_GROUP__FIELDS);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns FieldGroup.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/custom16/FieldGroup.png"));
	}

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getText(Object object) {
        String label = ((FieldGroup) object).getName();   
        return label == null || label.length() == 0 ?
            getString("_UI_FieldGroup_type") :
            getString("_UI_FieldGroup_type") + " " + label;
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

		switch (notification.getFeatureID(FieldGroup.class)) {
			case ImogenePackage.FIELD_GROUP__NAME:
			case ImogenePackage.FIELD_GROUP__SHORT_NAME:
			case ImogenePackage.FIELD_GROUP__ICON:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case ImogenePackage.FIELD_GROUP__DESCRIPTIONS:
			case ImogenePackage.FIELD_GROUP__FIELDS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
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

		newChildDescriptors.add
			(createChildParameter
				(ImogenePackage.Literals.FIELD_GROUP__DESCRIPTIONS,
				 ImogeneFactory.eINSTANCE.createDescription()));

		newChildDescriptors.add
			(createChildParameter
				(ImogenePackage.Literals.FIELD_GROUP__FIELDS,
				 ImogeneFactory.eINSTANCE.createBooleanField()));

		newChildDescriptors.add
			(createChildParameter
				(ImogenePackage.Literals.FIELD_GROUP__FIELDS,
				 ImogeneFactory.eINSTANCE.createIntegerField()));

		newChildDescriptors.add
			(createChildParameter
				(ImogenePackage.Literals.FIELD_GROUP__FIELDS,
				 ImogeneFactory.eINSTANCE.createFloatField()));

		newChildDescriptors.add
			(createChildParameter
				(ImogenePackage.Literals.FIELD_GROUP__FIELDS,
				 ImogeneFactory.eINSTANCE.createTextField()));

		newChildDescriptors.add
			(createChildParameter
				(ImogenePackage.Literals.FIELD_GROUP__FIELDS,
				 ImogeneFactory.eINSTANCE.createTextAreaField()));

		newChildDescriptors.add
			(createChildParameter
				(ImogenePackage.Literals.FIELD_GROUP__FIELDS,
				 ImogeneFactory.eINSTANCE.createAddressField()));

		newChildDescriptors.add
			(createChildParameter
				(ImogenePackage.Literals.FIELD_GROUP__FIELDS,
				 ImogeneFactory.eINSTANCE.createEmailField()));

		newChildDescriptors.add
			(createChildParameter
				(ImogenePackage.Literals.FIELD_GROUP__FIELDS,
				 ImogeneFactory.eINSTANCE.createGeoField()));

		newChildDescriptors.add
			(createChildParameter
				(ImogenePackage.Literals.FIELD_GROUP__FIELDS,
				 ImogeneFactory.eINSTANCE.createBarcodeField()));

		newChildDescriptors.add
			(createChildParameter
				(ImogenePackage.Literals.FIELD_GROUP__FIELDS,
				 ImogeneFactory.eINSTANCE.createPhoneField()));

		newChildDescriptors.add
			(createChildParameter
				(ImogenePackage.Literals.FIELD_GROUP__FIELDS,
				 ImogeneFactory.eINSTANCE.createBinaryField()));

		newChildDescriptors.add
			(createChildParameter
				(ImogenePackage.Literals.FIELD_GROUP__FIELDS,
				 ImogeneFactory.eINSTANCE.createMainRelationFieldEntity()));

		newChildDescriptors.add
			(createChildParameter
				(ImogenePackage.Literals.FIELD_GROUP__FIELDS,
				 ImogeneFactory.eINSTANCE.createReverseRelationFieldEntity()));

		newChildDescriptors.add
			(createChildParameter
				(ImogenePackage.Literals.FIELD_GROUP__FIELDS,
				 ImogeneFactory.eINSTANCE.createEnumField()));

		newChildDescriptors.add
			(createChildParameter
				(ImogenePackage.Literals.FIELD_GROUP__FIELDS,
				 ImogeneFactory.eINSTANCE.createVideoField()));

		newChildDescriptors.add
			(createChildParameter
				(ImogenePackage.Literals.FIELD_GROUP__FIELDS,
				 ImogeneFactory.eINSTANCE.createPhotoField()));

		newChildDescriptors.add
			(createChildParameter
				(ImogenePackage.Literals.FIELD_GROUP__FIELDS,
				 ImogeneFactory.eINSTANCE.createSoundField()));

		newChildDescriptors.add
			(createChildParameter
				(ImogenePackage.Literals.FIELD_GROUP__FIELDS,
				 ImogeneFactory.eINSTANCE.createDateField()));

		newChildDescriptors.add
			(createChildParameter
				(ImogenePackage.Literals.FIELD_GROUP__FIELDS,
				 ImogeneFactory.eINSTANCE.createDateTimeField()));

		newChildDescriptors.add
			(createChildParameter
				(ImogenePackage.Literals.FIELD_GROUP__FIELDS,
				 ImogeneFactory.eINSTANCE.createTimeField()));

		newChildDescriptors.add
			(createChildParameter
				(ImogenePackage.Literals.FIELD_GROUP__FIELDS,
				 ImogeneFactory.eINSTANCE.createFilterField()));
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return ImogeneModelEditPlugin.INSTANCE;
	}

	@Override
	public Object getForeground(Object object) {		
		return URI.createURI("color://rgb/0/141/0");
	}

}
