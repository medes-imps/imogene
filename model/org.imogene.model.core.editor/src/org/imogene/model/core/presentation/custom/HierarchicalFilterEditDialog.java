package org.imogene.model.core.presentation.custom;

import java.net.URL;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.imogene.model.core.CardEntity;
import org.imogene.model.core.FieldEntity;
import org.imogene.model.core.FieldGroup;
import org.imogene.model.core.RelationFieldEntity;

public class HierarchicalFilterEditDialog extends Dialog implements SelectionListener {

	/* UI Table to display fields of the second entity */
	private Table firstListTable;
	
	/* UI Table to display fields of the second entity */
	private Table secondListTable;
	
	/* UI TableViewer to display fields of the second entity */
	private TableViewer secondListTableViewer;
	
	/* UI list to display relation hierarchical filters */
	private Label hierarchicalFilter;
	
	/* button to add an association */
	private Button addButton;

	/* button to remove an association */
	private Button removeButton;
	
	/* Relation fields of the first entity */
	private EList<RelationFieldEntity> firstEntityFields;
	
	/* Relation fields of the second entity */
	private EList<RelationFieldEntity> secondEntityFields;
	
	private final IItemLabelProvider mLabelProvider;
	
	private final RelationFieldEntity mRelation;
	
	private final EList<RelationFieldEntity> mResult;
	
	/**
	 * Create dialog to manage common fields associated with a relation.
	 * 
	 * @param shell
	 *            the parent shell
	 * @param relation
	 *            the handled relation
	 */
	public HierarchicalFilterEditDialog(Shell shell,
			RelationFieldEntity relation, IItemLabelProvider labelProvider) {
		super(shell);
		setShellStyle(SWT.RESIZE | SWT.MAX);
		firstEntityFields = getFilterChoices(relation);
		mResult = new BasicEList<RelationFieldEntity>();
		mRelation = relation;
		mLabelProvider = labelProvider;
		if(relation.getRelationHierarchicalFilter()!=null){
			mResult.addAll(relation.getRelationHierarchicalFilter());
		}
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		parent.getShell().setText("Hierarchical filter ...");
		parent.setSize(500, 550);

		Composite dialogArea = (Composite) super.createDialogArea(parent);
		dialogArea.setLayout(new GridLayout());
		
		Composite resultComposite = new Composite(dialogArea, SWT.NONE);
		resultComposite.setLayout(new GridLayout(2, false));
		resultComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		hierarchicalFilter = new Label(resultComposite, SWT.BORDER);
		hierarchicalFilter.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		removeButton = new Button(resultComposite, SWT.PUSH);
		removeButton.setText("Remove");
		removeButton.addSelectionListener(this);

		/* selector label composite */
		Composite selectorLabelComposite = new Composite(dialogArea, SWT.NONE);
		selectorLabelComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		selectorLabelComposite.setLayout(new GridLayout(2, false));
		Label firstEntityLabel = new Label(selectorLabelComposite, SWT.NONE);
		firstEntityLabel.setText("Relation filter");
		Label secondEntityLabel = new Label(selectorLabelComposite, SWT.NONE);
		secondEntityLabel.setText("Filtered column");

		/* selector composite */
		Composite selectorComposite = new Composite(dialogArea, SWT.NONE);
		selectorComposite.setLayout(new GridLayout(3, false));
		selectorComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

		firstListTable = new Table(selectorComposite, SWT.BORDER | SWT.SINGLE);
		firstListTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		firstListTable.addSelectionListener(this);
		TableViewer firstListTableViewer = new TableViewer(firstListTable);
		firstListTableViewer.setLabelProvider(new ChoiceLabelProvider(mLabelProvider));
		firstListTableViewer.setContentProvider(new ArrayContentProvider());
		firstListTableViewer.setInput(firstEntityFields);
		
		addButton = new Button(selectorComposite, SWT.PUSH);
		addButton.setText("Add");
		addButton.addSelectionListener(this);

		secondListTable = new Table(selectorComposite, SWT.BORDER | SWT.SINGLE);
		secondListTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		secondListTable.setSize(firstListTable.getSize());
		secondListTableViewer = new TableViewer(secondListTable);
		secondListTableViewer.setContentProvider(new ArrayContentProvider());
		secondListTableViewer.setLabelProvider(new ChoiceLabelProvider(mLabelProvider));

		if (mResult.size() == 2)
			firstListTable.select(firstEntityFields.indexOf(mResult.get(0)));
		else
			firstListTable.select(0);

		if (firstEntityFields.size() > 0)
			secondEntityFields = getFilteredColumnChoices(
				mRelation.getEntity(),
				firstEntityFields.get(firstListTable.getSelectionIndex()).getEntity());
		
		secondListTableViewer.setInput(secondEntityFields);

		if (mResult.size() == 2) {
			secondListTable.select(secondEntityFields.indexOf(mResult.get(1)));
		}

		refresh();

		return super.createDialogArea(parent);
	}
	
	private void refresh() {
		if (mResult != null && mResult.size() == 2) {
			RelationFieldEntity first = mResult.get(0);
			RelationFieldEntity second = mResult.get(1);
			StringBuilder builder = new StringBuilder()
			.append(first.getParentCard().getShortName()).append('.')
			.append(first.getName())
			.append(" (").append(first.getEntity().getName()).append(") ")
			.append("  <-->  ")
			.append(second.getParentCard().getShortName()).append('.')
			.append(second.getName())
			.append(" (").append(second.getEntity().getName()).append(")");
			hierarchicalFilter.setText(builder.toString());
			
			addButton.setEnabled(false);
			removeButton.setEnabled(true);
		} else {
			mResult.clear();
			hierarchicalFilter.setText("");
			addButton.setEnabled(true);
			removeButton.setEnabled(false);
		}
	}
	
	public EList<RelationFieldEntity> getResult() {
		return mResult;
	}
	
	@Override
	protected void cancelPressed() {
		mResult.clear();
		super.cancelPressed();
	}
	
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
	}

	public void widgetSelected(SelectionEvent e) {
		if (e.getSource().equals(firstListTable)) {
			RelationFieldEntity relation = firstEntityFields.get(firstListTable.getSelectionIndex());
			secondEntityFields = getFilteredColumnChoices(mRelation.getEntity(), relation.getEntity());
			secondListTableViewer.setInput(secondEntityFields);
		} else if (e.getSource().equals(addButton)) {
			int first = firstListTable.getSelectionIndex();
			int second = secondListTable.getSelectionIndex();
			if (first != -1 && second != -1) {
				mResult.clear();
				mResult.add(firstEntityFields.get(first));
				mResult.add(secondEntityFields.get(second));
				refresh();
			}
		} else if (e.getSource().equals(removeButton)) {
			mResult.clear();
			refresh();
		}
	}
	
	private EList<RelationFieldEntity> getFilterChoices(RelationFieldEntity relation) {
		EList<RelationFieldEntity> choices = new BasicEList<RelationFieldEntity>();
		for (FieldGroup group : relation.getParentCard().getGroups())
			for (FieldEntity field : group.getFields())
				if (field instanceof RelationFieldEntity)
					if (hasSingleRelation(relation.getEntity(), ((RelationFieldEntity) field).getEntity()))
						choices.add((RelationFieldEntity) field);
		return choices;
	}
	
	private EList<RelationFieldEntity> getFilteredColumnChoices(CardEntity entity, CardEntity filter) {
		EList<RelationFieldEntity> choices = new BasicEList<RelationFieldEntity>();
		for (FieldGroup group : entity.getGroups())
			for (FieldEntity field : group.getFields())
				if (field instanceof RelationFieldEntity) {
					RelationFieldEntity relation = (RelationFieldEntity) field;
					if (relation.getCardinality() == 1 && relation.getEntity() != null && relation.getEntity().equals(filter))
						choices.add(relation);
				}
		return choices;
	}
	
	private boolean hasSingleRelation(CardEntity entity, CardEntity relation) {
		for (FieldGroup group : entity.getGroups())
			for (FieldEntity field : group.getFields())
				if (field instanceof RelationFieldEntity)
					if (((RelationFieldEntity) field).getCardinality() == 1 && ((RelationFieldEntity) field).getEntity().equals(relation))
						return true;
		return false;
	}

	/**
	 * 
	 * @author MEDES-IMPS
	 *
	 */
	private class ChoiceLabelProvider extends LabelProvider {

		IItemLabelProvider delegate;
		
		public ChoiceLabelProvider(IItemLabelProvider delegate){
			super();
			this.delegate = delegate;
		}
		
		@Override
		public Image getImage(Object element) {
			URL url = (URL)delegate.getImage(element);
			ImageDescriptor imgDescriptor = ImageDescriptor.createFromURL(url);
			return imgDescriptor.createImage();
		}

		@Override
		public String getText(Object element) {
			
			String text = ((RelationFieldEntity)element).getParentCard().getShortName();
			text = text + "." + ((RelationFieldEntity)element).getName();
			text = text + " (" + (((RelationFieldEntity)element).getEntity().getName()) + ")";
			text = text + " (" + ((RelationFieldEntity)element).getCardinality() + ")";
			return text;
		}
		
	}
	
}
