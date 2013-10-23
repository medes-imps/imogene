package org.imogene.model.core.presentation.custom;

import java.net.URL;
import java.util.Iterator;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
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
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.imogene.model.core.CardEntity;
import org.imogene.model.core.FieldEntity;
import org.imogene.model.core.FieldGroup;
import org.imogene.model.core.RelationFieldEntity;


/**
 * 
 * @author MEDES-IMPS
 *
 */
public class CommonFieldsEditDialog extends Dialog implements SelectionListener {
	
	private static String MESSAGE_WARNING = new String("The field of entity 1 and the one of entity 2 which are common must be of the same type with the same cardinality.");
	
	private CardEntity firstEntity;
	
	private CardEntity secondEntity;	
	
	/* UI list to display fields of the first entity */
	//private List firstEntityFieldsList;
	
	/* UI Table to display fields of the second entity */
	private Table firstListTable;
	
	/* UI list to display fields of the second entity */
	//private List secondEntityFieldsList;
	
	/* UI Table to display fields of the second entity */
	private Table secondListTable; 
		
	/* UI list to display common fields association*/
	private List commonFields;
	
	/* button to add an association */
	private Button addButton;

	/* button to remove an association */
	private Button removeButton;
	
	/* Relation fields of the first entity */
	private EList<RelationFieldEntity> firstEntityFields;
	
	/* Relation fields of the second entity */
	private EList<RelationFieldEntity> secondEntityFields;
	
	/* list of commons fields 
	 * WARNING : n is associated with n+1
	 */
	private EList<RelationFieldEntity> result;
	
	private IItemLabelProvider labelProvider;
	
	
	/**
	 * Create dialog to manage common fields 
	 * associated with a relation.
	 * 
	 * @param shell the parent shell
	 * @param relation the handled relation
	 */
	public CommonFieldsEditDialog(Shell shell, RelationFieldEntity relation, IItemLabelProvider labelProvider){			
		super(shell);				
		this.setShellStyle(SWT.RESIZE | SWT.MAX);
		setEntityName(relation);
		firstEntityFields = getEntityFields(relation.getParentCard());
		secondEntityFields = getEntityFields(relation.getEntity());					
		result = new BasicEList<RelationFieldEntity>();
		if(relation.getCommonFields()!=null){
			result.addAll(relation.getCommonFields());
		}
		this.labelProvider = labelProvider;		
	}
	



	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		
		parent.getShell().setText("Common fields ...");	
		parent.setSize(500, 550);
		
        Composite dialogArea = (Composite) super.createDialogArea(parent);
		dialogArea.setLayout(new GridLayout());				
		
		/* common fields list */		
		Label commonFieldsListLabel = new Label(dialogArea, SWT.NONE);
		commonFieldsListLabel.setText("List of common fields");
		
		Composite resultComposite = new Composite(dialogArea, SWT.NONE);
		resultComposite.setLayout(new GridLayout(2, false));
		resultComposite.setLayoutData(new GridData(GridData.FILL_BOTH));		
		commonFields = new List(resultComposite, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
		setLayoutData(commonFields);		
		removeButton = new Button(resultComposite, SWT.PUSH);
		removeButton.setText("Remove");
		removeButton.addSelectionListener(this);
		
		/* selector label composite */
		Composite selectorLabelComposite = new Composite(dialogArea, SWT.NONE);
		selectorLabelComposite.setLayoutData(new GridData(
				GridData.FILL_HORIZONTAL));
		selectorLabelComposite.setLayout(new GridLayout(2, false));
		Label firstEntityLabel = new Label(selectorLabelComposite, SWT.NONE);
		firstEntityLabel.setText("Entity name, fields");
		Label secondEntityLabel = new Label(selectorLabelComposite, SWT.NONE);
		secondEntityLabel.setText("Second entity name, fields");

		/* selector composite */
		Composite selectorComposite = new Composite(dialogArea, SWT.NONE);
		selectorComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		selectorComposite.setLayout(new GridLayout(3, false));			
		
		firstListTable = new Table(selectorComposite, SWT.BORDER | SWT.SINGLE);
		firstListTable.setLayoutData(new GridData(GridData.FILL_BOTH));
		TableViewer firstListTableViewer = new TableViewer(firstListTable);		
		firstListTableViewer.setLabelProvider(new ChoiceLabelProvider(labelProvider));
		firstListTableViewer.setContentProvider(new ArrayContentProvider());
		firstListTableViewer.setInput(firstEntityFields);		
		
		Composite buttonComposite = new Composite(selectorComposite, SWT.NONE);
		buttonComposite.setLayout(new GridLayout());
		addButton = new Button(buttonComposite, SWT.PUSH);
		addButton.setText("Add");
		addButton.addSelectionListener(this);
		
		secondListTable = new Table(selectorComposite, SWT.BORDER | SWT.SINGLE);
		secondListTable.setLayoutData(new GridData(GridData.FILL_BOTH));
		secondListTable.setSize(firstListTable.getSize());
		TableViewer secondListTableViewer = new TableViewer(secondListTable);
		secondListTableViewer.setContentProvider(new ArrayContentProvider());		
		secondListTableViewer.setLabelProvider(new ChoiceLabelProvider(labelProvider));
		secondListTableViewer.setInput(secondEntityFields);
					
		addToList(result);					
		return dialogArea;
	}
	
	/**
	 * Set layout data for List widgets
	 * 
	 * @param list The List widget
	 */
	private void setLayoutData(List list){
		GridData data = new GridData();
		data.grabExcessVerticalSpace = true;
		data.grabExcessHorizontalSpace = true;
		data.verticalAlignment = SWT.FILL;
		data.horizontalAlignment = SWT.FILL;	
		list.setLayoutData(data);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#cancelPressed()
	 */
	@Override
	protected void cancelPressed() {
		result.clear();
		super.cancelPressed();		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {		
		super.okPressed();		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected(SelectionEvent e) {				
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {		
		if(e.getSource().equals(addButton)){				
			RelationFieldEntity first = firstEntityFields.get(firstListTable.getSelectionIndex());			
			RelationFieldEntity second = secondEntityFields.get(secondListTable.getSelectionIndex());			
			if(first.getEntity().getName().equals(second.getEntity().getName()) && first.getCardinality() == second.getCardinality()){				
					result.add(first);
					result.add(second);
					addToList(first, second);				
			}else{
				MessageDialog.openWarning(getShell(), "Warning", MESSAGE_WARNING);
				
			}
		}
		if(e.getSource().equals(removeButton)){
			int index = commonFields.getSelectionIndex();
			result.remove(index+1);
			result.remove(index);
			commonFields.remove(index);
		}
		
	}	
	
	
	/**
	 * Get all fields contained in this entity
	 * 
	 * @param entity The container entity
	 * @return list of all fields contained by this entity
	 */
	private EList<RelationFieldEntity> getEntityFields(CardEntity entity){
		EList<FieldGroup> groups = entity.getGroups();
		EList<RelationFieldEntity> fields = new BasicEList<RelationFieldEntity>();
		for(Iterator<FieldGroup> it = groups.iterator();it.hasNext();){
			for(Iterator<FieldEntity> it2 = it.next().getFields().iterator();it2.hasNext();){
				FieldEntity current = it2.next();
				if (current instanceof RelationFieldEntity)
					fields.add((RelationFieldEntity)current);
			}
			
		}
		return fields;
	}
	
	
	/**
	 * Set the variables with the name of the entities
	 * 
	 * @param relation The parent relation field.
	 */
	private void setEntityName(RelationFieldEntity relation){
		firstEntity = relation.getParentCard();
		secondEntity = relation.getEntity();
	}
	
	/**
	 * Get the result of the selection
	 * 
	 * @return CommonFields selected.
	 */
	public EList<RelationFieldEntity> getResult(){	
		
		return result;
	}
	
	/**
	 * Populate the CommonFields list.
	 * 
	 * @param common The CommonFields to add to the list
	 */
	private void addToList(RelationFieldEntity first, RelationFieldEntity second){
		String label = first.getParentCard().getShortName() + "." + first.getName();
		label = label + " (" + first.getEntity().getName() + ")";
		label = label + "  <-->  ";
		label = label + second.getParentCard().getShortName() + "." + second.getName();
		label = label + " (" + second.getEntity().getName() + ")";
		commonFields.add(label);
	}
	
	/**
	 * Add a list of CommonFields to the UI list
	 * 
	 * @param list CommonFields to add
	 */
	private void addToList(EList<RelationFieldEntity> list){
		for(Iterator<RelationFieldEntity> it = list.iterator();it.hasNext();){			
			addToList(it.next(), it.next());
		}
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
