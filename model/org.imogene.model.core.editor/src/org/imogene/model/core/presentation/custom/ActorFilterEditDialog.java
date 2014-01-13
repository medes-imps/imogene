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
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.imogene.model.core.Actor;
import org.imogene.model.core.ActorFilter;
import org.imogene.model.core.CardEntity;
import org.imogene.model.core.FieldEntity;
import org.imogene.model.core.FieldGroup;
import org.imogene.model.core.FilterField;
import org.imogene.model.core.ImogeneFactory;
import org.imogene.model.core.Project;
import org.imogene.model.core.RelationFieldEntity;
import org.imogene.model.core.impl.ImogeneFactoryImpl;


/**
 * 
 * @author MEDES-IMPS
 *
 */
public class ActorFilterEditDialog extends Dialog implements SelectionListener, MouseListener {
	
	private static String WARNING_SAME_ENTITY = new String("The field of entity 1 and the one of entity 2 which are common must be of the same type with the same cardinality.");
	
	private static String WARNING_EXISTS = new String("This association is already defined.");	
	
	
	/* UI Table to display fields of the card entity */
	private Table entityFieldsTable;	
	
	/* UI Table to display filter fields of the actors */
	private Table actorFiltersTable; 
		
	/* UI list to display common fields association*/
	private List actorFilters;
	
	/* button to add an association */
	private Button addButton;

	/* button to remove an association */
	private Button removeButton;
	
	/* Relation fields of the first entity */
	private EList<RelationFieldEntity> entityFields;
	
	/* Relation fields of the second entity */
	private EList<FilterField> actorFilterFields;
	
	/* list of the ActorFilter associations  */
	private EList<ActorFilter> result = new BasicEList<ActorFilter>();
	
	/* */
	private IItemLabelProvider labelProvider;
	
	/* */
	private MenuItem item;
	
	
	/**
	 * Create dialog to manage actor filter fields
	 * associated with a card entity.
	 * 
	 * @param shell the parent shell
	 * @param relation the handled relation
	 */
	public ActorFilterEditDialog(Shell shell, CardEntity entity, IItemLabelProvider labelProvider){	
		
		super(shell);				
		this.setShellStyle(SWT.RESIZE | SWT.MAX);		
		try{
		entityFields = getEntityFields(entity);
		actorFilterFields = getAllFilterFields(entity);					
		result = new BasicEList<ActorFilter>();
		if(entity.getActorFilterFields()!=null){
			result.addAll(entity.getActorFilterFields());
		}
		this.labelProvider = labelProvider;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	



	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		
		parent.getShell().setText("Actor filter fields ...");	
		parent.setSize(500, 550);
		
        Composite dialogArea = (Composite) super.createDialogArea(parent);
		dialogArea.setLayout(new GridLayout());				
		
		/* common fields list */		
		Label commonFieldsListLabel = new Label(dialogArea, SWT.NONE);
		commonFieldsListLabel.setText("List of common fields");
		
		Composite resultComposite = new Composite(dialogArea, SWT.NONE);
		resultComposite.setLayout(new GridLayout(2, false));
		resultComposite.setLayoutData(new GridData(GridData.FILL_BOTH));		
		actorFilters = new List(resultComposite, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
		setLayoutData(actorFilters);
		actorFilters.addMouseListener(this);
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
		
		entityFieldsTable = new Table(selectorComposite, SWT.BORDER | SWT.SINGLE);
		entityFieldsTable.setLayoutData(new GridData(GridData.FILL_BOTH));
		TableViewer firstListTableViewer = new TableViewer(entityFieldsTable);		
		firstListTableViewer.setLabelProvider(new RelationFieldLabelProvider(labelProvider));
		firstListTableViewer.setContentProvider(new ArrayContentProvider());
		firstListTableViewer.setInput(entityFields);		
		
		Composite buttonComposite = new Composite(selectorComposite, SWT.NONE);
		buttonComposite.setLayout(new GridLayout());
		addButton = new Button(buttonComposite, SWT.PUSH);
		addButton.setText("Add");
		addButton.addSelectionListener(this);
		
		actorFiltersTable = new Table(selectorComposite, SWT.BORDER | SWT.SINGLE);
		actorFiltersTable.setLayoutData(new GridData(GridData.FILL_BOTH));
		actorFiltersTable.setSize(entityFieldsTable.getSize());
		TableViewer secondListTableViewer = new TableViewer(actorFiltersTable);
		secondListTableViewer.setContentProvider(new ArrayContentProvider());		
		secondListTableViewer.setLabelProvider(new FilterFieldLabelProvider(labelProvider));
		secondListTableViewer.setInput(actorFilterFields);
					
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
		
		/* add button */
		if(e.getSource().equals(addButton)){				
			RelationFieldEntity entityField = entityFields.get(entityFieldsTable.getSelectionIndex());			
			FilterField actorField = actorFilterFields.get(actorFiltersTable.getSelectionIndex());	
			if(alreadyExists(entityField,actorField)){
				MessageDialog.openWarning(getShell(), "Warning", WARNING_EXISTS);
			}else{
			if(entityField.getEntity().getName().equals(actorField.getEntity().getName())){
					ImogeneFactory factory = ImogeneFactoryImpl.init();
					ActorFilter actorFilter = factory.createActorFilter();
					actorFilter.setEntityField(entityField);
					actorFilter.setActorField(actorField);
					entityField.eResource().getContents().add(actorFilter);
					addToList(entityField, actorField);
					result.add(actorFilter);
			}else{
				MessageDialog.openWarning(getShell(), "Warning", WARNING_SAME_ENTITY);
				
			}
			}
		}
		
		/* remove button */
		if(e.getSource().equals(removeButton)){
			int index = actorFilters.getSelectionIndex();
			ActorFilter toRemove = result.get(index);
			toRemove.eResource().getContents().remove(toRemove);
			result.remove(index);
			actorFilters.remove(index);
			
		}
		
	}	
	
	private boolean alreadyExists(RelationFieldEntity entityField, FilterField actorField){
		
		for(ActorFilter actorFilter:result){
			if(actorFilter.getActorField().equals(actorField)&&actorFilter.getEntityField().equals(entityField))
				return true;
		}
		return false;
	}
	
	
	/**
	 * Get all relation fields contained in this entity
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
	 * Get all filter fields contained in this entity
	 * 
	 * @param entity The container entity
	 * @return list of all filter fields contained by the project
	 */
	private EList<FilterField> getAllFilterFields(CardEntity entity){
		Project project = (Project)entity.eContainer();
		EList<FilterField> filterFields = new BasicEList<FilterField>();
		for(CardEntity cEntity:project.getEntities()){
			if(cEntity instanceof Actor){
				filterFields.addAll(((Actor)cEntity).getFilters());
			}
		}
		return filterFields;		
	}
	
	/**
	 * Get the result of the selection
	 * 
	 * @return CommonFields selected.
	 */
	public EList<ActorFilter> getResult(){	
		return result;
	}
		
	
	/**
	 * Populate the CommonFields list.
	 * 
	 * @param common The CommonFields to add to the list
	 */
	private void addToList(RelationFieldEntity first, FilterField second){
		String label = first.getParentCard().getShortName() + "." + first.getName();
		label = label + " (" + first.getEntity().getName() + ")";
		label = label + "  <-->  ";
		label = label + second.getParentActor().getShortName() + "." + second.getName();
		label = label + " (" + second.getEntity().getName() + ")";
		actorFilters.add(label);
	}
	
	/**
	 * Add a list of CommonFields to the UI list
	 * 
	 * @param list CommonFields to add
	 */
	private void addToList(EList<ActorFilter> list){
		for(ActorFilter actorFilter:list){
			addToList(actorFilter.getEntityField(), actorFilter.getActorField());
		}	
	}
	
	/**
	 * 
	 * @author MEDES-IMPS
	 *
	 */
	private class RelationFieldLabelProvider extends LabelProvider {

		IItemLabelProvider delegate;
		
		public RelationFieldLabelProvider(IItemLabelProvider delegate){
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
			//System.out.println(text);
			text = text + " (" + (((RelationFieldEntity)element).getEntity().getName()) + ")";
			text = text + " (" + ((RelationFieldEntity)element).getCardinality() + ")";
			return text;
		}
		
	}
	
	/**
	 * 
	 * @author MEDES-IMPS
	 *
	 */
	private class FilterFieldLabelProvider extends LabelProvider {

		IItemLabelProvider delegate;
		
		public FilterFieldLabelProvider(IItemLabelProvider delegate){
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
			String text = ((FilterField)element).getParentActor().getShortName();
			text = text + "." + ((RelationFieldEntity)element).getName();
			text = text + " (" + (((RelationFieldEntity)element).getEntity().getName()) + ")";
			text = text + " (" + ((RelationFieldEntity)element).getCardinality() + ")";			
			return text;
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseListener#mouseDoubleClick(org.eclipse.swt.events.MouseEvent)
	 */
	public void mouseDoubleClick(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseListener#mouseDown(org.eclipse.swt.events.MouseEvent)
	 */
	public void mouseDown(MouseEvent e) {
		if(e.getSource().equals(actorFilters)){
			Menu menu = new Menu(Display.getCurrent().getActiveShell());
			item = new MenuItem(menu, SWT.CHECK);
			item.setText("Is sufficient");
			item.setSelection(result.get(actorFilters.getSelectionIndex()).isSufficient());
			item.addSelectionListener(new SelectionAdapter(){

				@Override
				public void widgetSelected(SelectionEvent e) {				
					super.widgetSelected(e);					
					ActorFilter filter = result.get(actorFilters.getSelectionIndex());
					filter.setSufficient(item.getSelection());														
				}
				
			});
			
			menu.setLocation(Display.getCurrent().getCursorLocation());
			menu.setVisible(true);				
		}		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseListener#mouseUp(org.eclipse.swt.events.MouseEvent)
	 */
	public void mouseUp(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	

}
