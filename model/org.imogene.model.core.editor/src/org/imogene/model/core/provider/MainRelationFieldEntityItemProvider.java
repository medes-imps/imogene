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
import org.imogene.model.core.MainRelationFieldEntity;
import org.imogene.model.core.RelationFieldEntity;

/**
 * This is the item provider adapter for a {@link org.imogene.model.core.MainRelationFieldEntity} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class MainRelationFieldEntityItemProvider
	extends RelationFieldEntityItemProvider
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
	public MainRelationFieldEntityItemProvider(AdapterFactory adapterFactory) {
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

			addInverseCardinalityPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Inverse Cardinality feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addInverseCardinalityPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_MainRelationFieldEntity_InverseCardinality_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_MainRelationFieldEntity_InverseCardinality_feature", "_UI_MainRelationFieldEntity_type"),
				 ImogenePackage.Literals.MAIN_RELATION_FIELD_ENTITY__INVERSE_CARDINALITY,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This returns MainRelationFieldEntity.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/MainRelationFieldEntity"));
	}

	   /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public String getText(Object object)
    {
        MainRelationFieldEntity rf = (MainRelationFieldEntity) object;
        String label = ((RelationFieldEntity) object).getName();
        String cardinality = (rf.getCardinality() == -1) ? " *" : " " + rf.getCardinality();
        String targetEntity = (rf.getEntity() == null) ? " NOT DEFINED" : " " + rf.getEntity().getName();
        RelationFieldEntity opposite = rf.getOppositeRelationField();
        int oppositeCardinality = (opposite == null) ? rf.getInverseCardinality() : opposite.getCardinality();
        String oppCard = (oppositeCardinality == -1) ? "*" : "" + oppositeCardinality;
        String oppositeName = (opposite == null) ? " (<- OPPOSITE NOT DEFINED)" : " " + "(<- " + opposite.getName() + ")";
        String result = null;
        if (label == null || label.length() == 0)
            result = getString("_UI_MainRelationFieldEntity_type");
        else
        {
            // on retourne :  Car.Drivers : Composition *,1 to Driver (<- parentCar)
            result = getParentName(rf) + "." + label + " (Main) : " + 
                     rf.getType().getLiteral() + 
                     cardinality + "," + oppCard + " to "
                        + targetEntity + oppositeName;
        }
        
        return result;
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

		switch (notification.getFeatureID(MainRelationFieldEntity.class)) {
			case ImogenePackage.MAIN_RELATION_FIELD_ENTITY__INVERSE_CARDINALITY:
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
