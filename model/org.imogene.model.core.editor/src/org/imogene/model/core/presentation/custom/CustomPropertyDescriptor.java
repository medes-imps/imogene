package org.imogene.model.core.presentation.custom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.emf.common.ui.celleditor.ExtendedComboBoxCellEditor;
import org.eclipse.emf.common.ui.celleditor.ExtendedDialogCellEditor;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.ui.celleditor.FeatureEditorDialog;
import org.eclipse.emf.edit.ui.provider.PropertyDescriptor;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.imogene.model.core.CardEntity;
import org.imogene.model.core.EmailField;
import org.imogene.model.core.FieldDependentVisibility;
import org.imogene.model.core.FieldEntity;
import org.imogene.model.core.ImogenePackage;
import org.imogene.model.core.NotificationInfo;
import org.imogene.model.core.PhoneField;
import org.imogene.model.core.RelationFieldEntity;
import org.imogene.model.core.impl.CardEntityImpl;

/**
 * 
 * @author MEDES-IMPS
 * 
 */
public class CustomPropertyDescriptor extends PropertyDescriptor {

	/**
	 * 
	 * @param object
	 * @param itemPropertyDescriptor
	 */
	public CustomPropertyDescriptor(Object object,
			IItemPropertyDescriptor itemPropertyDescriptor) {
		super(object, itemPropertyDescriptor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.edit.ui.provider.PropertyDescriptor#createPropertyEditor
	 * (org.eclipse.swt.widgets.Composite)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public CellEditor createPropertyEditor(Composite composite) {

		CellEditor result = super.createPropertyEditor(composite);
		@SuppressWarnings("unused")
		EClassifier eType = ((EStructuralFeature) itemPropertyDescriptor
				.getFeature(object)).getEType();
		final EStructuralFeature feature = (EStructuralFeature) itemPropertyDescriptor
				.getFeature(object);

		if(feature.getName().equals("rdfSubject") && object instanceof CardEntityImpl){
			result = cardEntityRdfSubject(composite);
		}
		
		/*
		 * Case of opposite relation field in a RelationFieldEntity
		 */
		if (feature.getName().equals("oppositeRelationField")
				&& object instanceof RelationFieldEntity) {

			final RelationFieldEntity relationEntity = (RelationFieldEntity) object;
			final CardEntity relationCard = relationEntity.getEntity();
			final CardEntity parentCard = relationEntity.getParentCard();

			/* filter choices of values */
			Collection oppositeChoices = itemPropertyDescriptor
					.getChoiceOfValues(object);
			Collection noGoodOpposite = new Vector();
			for (Iterator it = oppositeChoices.iterator(); it.hasNext();) {
				RelationFieldEntity opposite = (RelationFieldEntity) it.next();

				if (opposite != null) {
					if (opposite.getParentCard() == null
							|| !(opposite.getParentCard().equals(relationCard))) {
						noGoodOpposite.add(opposite);
					}
					if (opposite.getEntity() != null
							&& !opposite.getEntity().equals(parentCard)) {
						noGoodOpposite.add(opposite);
					}
				}
			}
			oppositeChoices.removeAll(noGoodOpposite);

			result = new ExtendedComboBoxCellEditor(composite,
					new ArrayList<Object>(oppositeChoices),
					getEditLabelProvider(), itemPropertyDescriptor
							.isSortChoices(object));
		}

		/*
		 * Case parent hierarchical filter
		 */
		if (ImogenePackage.RELATION_FIELD_ENTITY__RELATION_HIERARCHICAL_FILTER == feature
				.getFeatureID()
				&& object instanceof RelationFieldEntity) {
			final RelationFieldEntity relation = (RelationFieldEntity) object;
			if (relation.getEntity() == null)
				result = null;
			else
				result = new ExtendedDialogCellEditor(composite, getEditLabelProvider()) {
					@Override
					protected Object openDialogBox(Control cellEditorWindow) {

						HierarchicalFilterEditDialog dialog = new HierarchicalFilterEditDialog(cellEditorWindow.getShell(), relation, itemPropertyDescriptor.getLabelProvider(object));
						dialog.open();
						if (dialog.getReturnCode() == Dialog.OK) {
							return dialog.getResult();
						}
						return null;
					}
				};
		}

		/*
		 * case of FieldEntity fields for an CardEntity that are part of this
		 * card
		 */
		if ((feature.getName().equals("mainFields")
				|| feature.getName().equals("columnFields")
				|| feature.getName().equals("nestedFields")
				|| feature.getName().equals("filterFields")
				|| feature.getName().equals("sortFields")
				|| feature.getName().equals("adminFields")
				|| feature.getName().equals("secondaryFields")
				|| feature.getName().equals("clientFilterFields"))
				&& object instanceof CardEntity) {

			final CardEntity card = (CardEntity) object;

			/* customize choices of values */
			final Collection choices = itemPropertyDescriptor
					.getChoiceOfValues(object);
			Collection noGood = new Vector();
			for (Iterator it = choices.iterator(); it.hasNext();) {
				FieldEntity current = (FieldEntity) it.next();
				if (current.getParentCard() == null
						|| !current.getParentCard().equals(card)) {
					noGood.add(current);
				} else if (current instanceof RelationFieldEntity) {
					RelationFieldEntity relation = (RelationFieldEntity) current;
					if (relation.getCardinality() != 1
							&& !feature.getName().equals("columnFields"))
						noGood.add(current);
				}
			}
			choices.removeAll(noGood);

			result = new ExtendedDialogCellEditor(composite,
					getEditLabelProvider()) {
				@Override
				protected Object openDialogBox(Control cellEditorWindow) {
					FeatureEditorDialog dialog = new FeatureEditorDialog(
							cellEditorWindow.getShell(),
							getEditLabelProvider(), object, feature.getEType(),
							(List<?>) doGetValue(), getDisplayName(),
							new ArrayList<Object>(choices), false,
							itemPropertyDescriptor.isSortChoices(object));
					dialog.open();
					return dialog.getResult();
				}
			};
		}
		
		/*
		 * case of GeoField fields for a CardEntity that are part of this
		 * card
		 */
		if (feature.getName().equals("georeferenced") && object instanceof CardEntity) {
			final CardEntity card = (CardEntity) object;

			/* customize choices of values */
			final Collection choices = itemPropertyDescriptor.getChoiceOfValues(object);
			Collection noGood = new Vector();
			for (Iterator it = choices.iterator(); it.hasNext();) {
				FieldEntity current = (FieldEntity) it.next();
				if (current != null &&
						(current.getParentCard() == null	|| !current.getParentCard().equals(card))) {
					noGood.add(current);
				}
			}
			choices.removeAll(noGood);

			result = new ExtendedComboBoxCellEditor(
					composite,
					new ArrayList<Object>(choices),
					getEditLabelProvider(),
					itemPropertyDescriptor.isSortChoices(object));
		}
		
		/*
		 * case of notification info data field
		 */
		if (feature.getName().equals("dataField") && object instanceof NotificationInfo) {
			final NotificationInfo info = (NotificationInfo) object;

			/* customize choices of values */
			final Collection choices = itemPropertyDescriptor
					.getChoiceOfValues(object);
			Collection noGood = new Vector();
			for (Iterator it = choices.iterator(); it.hasNext();) {
				FieldEntity current = (FieldEntity) it.next();
				if (current != null && (!(current instanceof EmailField || current instanceof PhoneField) || !info.eContainer().equals(current.getParentCard()))) {
					noGood.add(current);
				}
			}
			choices.removeAll(noGood);

			result = new ExtendedComboBoxCellEditor(
					composite,
					new ArrayList<Object>(choices),
					getEditLabelProvider(),
					itemPropertyDescriptor.isSortChoices(object));
		}

		/*
		 * case of Field Dependent Visibility
		 */
		if ((feature.getName().equals("dependencyField"))
				&& object instanceof FieldDependentVisibility) {

			final FieldDependentVisibility fieldDependentVisibility = (FieldDependentVisibility) object;
			final FieldEntity parentFieldEntity = fieldDependentVisibility
					.getParentFieldEntity();
			final CardEntity parentCard = parentFieldEntity.getParentCard();

			/* customize choices of values */
			final Collection dependencyFieldChoices = itemPropertyDescriptor
					.getChoiceOfValues(object);
			Collection noGoodDependencyFields = new Vector();

			for (Iterator it = dependencyFieldChoices.iterator(); it.hasNext();) {
				FieldEntity current = (FieldEntity) it.next();
				if (current != null) {
					if (current.getParentCard() == null
							|| !current.getParentCard().equals(parentCard)
							|| current.equals(parentFieldEntity)) {
						noGoodDependencyFields.add(current);
					} else if (current instanceof RelationFieldEntity) {
						noGoodDependencyFields.add(current);
					}/*
					 * else if(current instanceof EnumField &&
					 * ((EnumField)current).isMultipleSelection() ){
					 * noGoodDependencyFields.add(current); }
					 */
				}
			}
			dependencyFieldChoices.removeAll(noGoodDependencyFields);

			result = new ExtendedComboBoxCellEditor(composite,
					new ArrayList<Object>(dependencyFieldChoices),
					getEditLabelProvider(), itemPropertyDescriptor
							.isSortChoices(object));
		}

		/*
		 * Case of the actor filter fields
		 */
		if (((EStructuralFeature) itemPropertyDescriptor.getFeature(object))
				.getName().equals("actorFilterFields")) {
			result = new ExtendedDialogCellEditor(composite,
					getEditLabelProvider()) {
				@Override
				protected Object openDialogBox(Control cellEditorWindow) {

					ActorFilterEditDialog dialog = new ActorFilterEditDialog(
							cellEditorWindow.getShell(), (CardEntity) object,
							itemPropertyDescriptor.getLabelProvider(object));
					dialog.open();
					if (dialog.getReturnCode() == Dialog.OK) {
						this.fireEditorValueChanged(true, true);
						return dialog.getResult();
					}
					return null;
				}

			};
		}

		/*
		 * case commonField properties
		 */
		if (((EStructuralFeature) itemPropertyDescriptor.getFeature(object))
				.getName().equals("commonFields")) {
			final RelationFieldEntity parentField = (RelationFieldEntity) object;

			/* It is mandatory that the Entity property have been filled */
			if (parentField.getEntity() == null) {
				return null;
			}

			result = new ExtendedDialogCellEditor(composite,
					getEditLabelProvider()) {

				@Override
				protected Object openDialogBox(Control cellEditorWindow) {

					String MESSAGE_WARNING = "CommonFields have been reset du to previous relation deletion\n"
							+ "Please fix it again.";

					/* if common fields are ??? remove all of it */
					if (fixErrorOnList()) {
						MessageDialog.openWarning(cellEditorWindow.getShell(),
								"Warning", MESSAGE_WARNING);
						return new BasicEList<RelationFieldEntity>();
					}

					CommonFieldsEditDialog dialog = new CommonFieldsEditDialog(
							cellEditorWindow.getShell(), parentField,
							itemPropertyDescriptor.getLabelProvider(object));
					dialog.open();
					if (dialog.getReturnCode() == Dialog.OK) {
						this.fireApplyEditorValue();
						return dialog.getResult();
					}
					return null;
				}

				private boolean fixErrorOnList() {
					if (parentField.getCommonFields() != null
							&& parentField.getCommonFields().size() % 2 == 1)
						return true;
					return false;
				}

			};
		}
		return result;
	}
	
	private CellEditor cardEntityRdfSubject(Composite composite){
		
		final CardEntity parentCard = (CardEntityImpl) object;
		
		/* filter choices of values */
		Collection<?> defaultChoices = itemPropertyDescriptor
				.getChoiceOfValues(object);
		
		Collection<RelationFieldEntity> choices = new Vector<RelationFieldEntity>();
		for (Iterator<?> it = defaultChoices.iterator(); it.hasNext();) {
			
			RelationFieldEntity choice = (RelationFieldEntity) it.next();
			if (choice != null && choice.getCardinality()==1 && choice.getParentCard().equals(parentCard)) {
				choices.add(choice);
			}
		}		

		return new ExtendedComboBoxCellEditor(composite,
				new ArrayList<Object>(choices),
				getEditLabelProvider(), itemPropertyDescriptor
						.isSortChoices(object));
		
		
	}

}
