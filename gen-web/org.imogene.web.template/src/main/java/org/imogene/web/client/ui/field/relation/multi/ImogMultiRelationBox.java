package org.imogene.web.client.ui.field.relation.multi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.imogene.web.client.event.FieldValueChangeEvent;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.field.ImogField;
import org.imogene.web.client.ui.field.ImogFieldAbstract;
import org.imogene.web.client.ui.field.error.ImogErrorLabel;
import org.imogene.web.client.ui.table.ImogBeanDataProvider;
import org.imogene.web.client.ui.table.filter.ImogFilterPanel;
import org.imogene.web.client.util.ImogBeanRenderer;
import org.imogene.web.shared.proxy.ImogBeanProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.HasEditorErrors;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;


/**
 * Field Editor for Relation fields with cardinality = n
 * @author MEDES-IMPS
 */
public class ImogMultiRelationBox<T extends ImogBeanProxy> extends Composite implements ImogField<List<T>>,
		LeafValueEditor<List<T>>, HasEditorErrors<List<T>> {

	private static final Binder uiBinder = GWT.create(Binder.class);

	@SuppressWarnings("rawtypes")
	interface Binder extends UiBinder<Widget, ImogMultiRelationBox> {
	}
	
	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();

	/* status - behavior */
	private String color;
	private boolean edited = false;
	private boolean canCreateEntity = true;
	private boolean isEmpty = true;
	private boolean isLocked = false;
	private String popupTitle = "";
	private boolean hideButtons = false;
	private boolean hideAffectButton = false;
	private boolean hideClearButton = false;
	private boolean hideCreateButton = false;
	private boolean notifyChanges = false;
	private int itemsPerPageInPopup = 15;
	
	/* listbox selected values */
	private Map<String,T> values = new HashMap<String, T>();
	//private ImogMultiRelationBoxPopUpAsPanel<T> popupListBox;
	private ImogBeanDataProvider<T> provider;
	private ImogBeanRenderer beanRenderer;
	private ImogFilterPanel filterPanel;

	/* widgets */
	@UiField
	ImogErrorLabel errorLabel;
	@UiField
	@com.google.gwt.editor.client.Editor.Ignore
	ImogFieldAbstract fieldBox;
	@UiField
	@com.google.gwt.editor.client.Editor.Ignore
	ListBox list;
	@UiField
	@com.google.gwt.editor.client.Editor.Ignore
	Image affectImage;
	@UiField
	@com.google.gwt.editor.client.Editor.Ignore
	Image viewImage;
	@UiField
	@com.google.gwt.editor.client.Editor.Ignore
	Image addImage;
	@UiField
	@com.google.gwt.editor.client.Editor.Ignore
	Image clearImage;

	
	
	
	public ImogMultiRelationBox(ImogBeanDataProvider<T> provider, ImogBeanRenderer beanRenderer, boolean hideButtons) {
		this(false, provider, beanRenderer, null);
		this.hideButtons = hideButtons;
	}
	
	public ImogMultiRelationBox(ImogBeanDataProvider<T> provider, ImogBeanRenderer beanRenderer, boolean hideButtons, int itemsPerPage) {
		this(false, provider, beanRenderer, null);
		this.hideButtons = hideButtons;
		this.itemsPerPageInPopup = itemsPerPage;
	}

	public ImogMultiRelationBox(ImogBeanDataProvider<T> provider, ImogBeanRenderer beanRenderer, String color) {
		this(true, provider, beanRenderer, color);
	}
	
	
	public ImogMultiRelationBox(boolean canCreateEntity, ImogBeanDataProvider<T> provider, ImogBeanRenderer beanRenderer, String color) {
		
		initWidget(uiBinder.createAndBindUi(this));
		
		affectImage.setUrl(GWT.getModuleBaseURL()+ "images/relation_affect.png");
		affectImage.setTitle(BaseNLS.constants().button_assign());
		
		viewImage.setTitle(BaseNLS.constants().button_view());
		viewImage.setUrl(GWT.getModuleBaseURL()+ "images/relation_view.png");
		
		addImage.setTitle(BaseNLS.constants().button_add());
		addImage.setUrl(GWT.getModuleBaseURL()+ "images/relation_add.png");
		
		clearImage.setTitle(BaseNLS.constants().button_remove());
		
		clearImage.setUrl(GWT.getModuleBaseURL()+ "images/relation_remove.png");
		this.canCreateEntity = canCreateEntity;
		this.provider = provider;
		this.color = color;
		this.beanRenderer = beanRenderer;
	}

	@Override
	public void setLabel(String label) {
		fieldBox.setLabel(label);
	}
	
	public void setLabel(String label, HorizontalAlignmentConstant alignment) {
		fieldBox.setLabel(label, alignment);
	}

	@Override
	public boolean isEdited() {
		return edited;
	}

	public void setLocked(boolean locked) {
		isLocked = locked;
	}
	
	private void showPopUpListBox() {
		ImogMultiRelationBoxPopUpAsPanel<T> popupListBox = new ImogMultiRelationBoxPopUpAsPanel<T>(this, provider, beanRenderer, color, itemsPerPageInPopup);
		popupListBox.setTitle(popupTitle);
		if(filterPanel!=null)
			popupListBox.setFilterPanel(filterPanel);
		//popupListBox.popup.setPopupPosition(Window.getClientWidth()/5 , 50);			
		popupListBox.show();
	}
	
	@UiHandler("affectImage")
	void onAffectEntities(ClickEvent e) {
		if(edited)
			showPopUpListBox();
	}
	
	public void setViewClickHandler(ClickHandler handler){
		registrations.add(viewImage.addClickHandler(handler));
	}
	
	public void setFilterPanel(final ImogFilterPanel filterPanel) {
		this.filterPanel = filterPanel;
	}
	
	public void setAddClickHandler(ClickHandler handler){
		registrations.add(addImage.addClickHandler(handler));
	}
	
	@UiHandler("clearImage")
	void onRemoveEntity(ClickEvent e) {
		for(int i=0; i<list.getItemCount(); i++){
			if(list.isItemSelected(i)){
				String toRemove = list.getValue(i);
				values.remove(toRemove);
				list.removeItem(i);
			}
		}
		if(values.size()==0)
			isEmpty = true;	
		setButtonVisibility();
		if(notifyChanges)
			notifyListChange();	
	}	
	
	/**
	 * Removes a value from the box
	 * @param entity the value to be removed
	 */
	public void removeValue(T entity) {
		String id = entity.getId();

		for(int i=0; i<list.getItemCount(); i++){
			String key = list.getValue(i);
			if (key.equals(id))
				list.removeItem(i);
		}
		values.remove(id);
		if(values.size()==0)
			isEmpty = true;	
		setButtonVisibility();
	}	
	

	/**
	 * Get the selected values 
	 * @return the selected values 
	 */
	public List<T> getValue() {	
		List<T> result = new ArrayList<T>();
		for(T entity : values.values()){
			result.add(entity);
		}
		if(result.size()>0)
			return result;
		else
			return null;
	}
	
	/**
	 * Sets the box values 
	 * @param value the values 
	 * @param notifyChange if true notify changes
	 */
	public void setValue(List<T> pValue) {
		values = new HashMap<String, T>();
		list.clear();
		if(pValue!=null) {
			for(T entity : pValue){
				values.put(entity.getId(), entity);				
				list.addItem(beanRenderer.getDisplayValue(entity), entity.getId());
				if (isEmpty)
					isEmpty = false;
			}
		}
		setButtonVisibility();

	}
	
	/**
	 * Sets the box values 
	 * @param value the values 
	 */
	public void setValue(List<T> pValue, boolean notifyChange) {
		setValue(pValue);
		this.notifyChanges = notifyChange;
		if(notifyChange)
			notifyListChange();	
	}
	
	/**
	 * Adds a value to the box
	 * @param value the value 
	 */
	public void addValue(T value){
		list.addItem(beanRenderer.getDisplayValue(value), value.getId());
		values.put(value.getId(), value);
		if (isEmpty)
			isEmpty = false;
		setButtonVisibility();
	}
	
	/**
	 * Adds a value to the box
	 * @param value the value 
	 * @param notifyChange if true notify changes
	 */
	public void addValue(T value, boolean notifyChange){
		addValue(value);
		this.notifyChanges = notifyChange;
		if(notifyChange)
			notifyListChange();	
	}
	
	/**
	 * 
	 */
	private void notifyListChange() {
		NativeEvent event = Document.get().createChangeEvent();
		DomEvent.fireNativeEvent(event, this) ;
	}
	
	/**
	 * Replaces a value in the list
	 * @param entity the item to 
	 */
	public void replaceValue(T entity) {
		for (int i = 0; i < list.getItemCount(); i++) {
			if (list.getValue(i).equals(entity.getId())) {
				removeValue(entity);
				addValue(entity);
			}
		}
	}
	
	/**
	 * Returns true if the specified entity is attached to this field
	 * @param toTest the entity to test
	 * @return true is the entity is already present.
	 */
	public boolean isPresent(T toTest){
		for (int i = 0; i < list.getItemCount(); i++) {
			if (list.getValue(i).equals(toTest.getId()))
				return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param title
	 */
	public void setPopUpTitle(String title) {
		popupTitle = title;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<T> getSelectedEntities() {
		
		List<T> result = new ArrayList<T>();
		
		for(int i=0; i<list.getItemCount(); i++){
			if(list.isItemSelected(i)){
				T selected = values.get(list.getValue(i));
				result.add(selected);
			}
		}
		return result;
	}


	public void setEdited(boolean enabled) {
				
		if(!enabled || isLocked){
			list.addStyleDependentName("disabled");
			edited = false;
		}
		else{
			list.removeStyleDependentName("disabled");
			edited = true;
		}
		setButtonVisibility();
	}
	
	private void setButtonVisibility() {
			
		if(edited) {
			if(!isLocked) {
				
				if(!hideAffectButton)
					affectImage.setVisible(true);
				
				if(!canCreateEntity || hideButtons || hideCreateButton)
					addImage.setVisible(false);
				else
					addImage.setVisible(true);
				
				if(!isEmpty && !hideClearButton)
					clearImage.setVisible(true);
				else
					clearImage.setVisible(false);
			}
			else {
				affectImage.setVisible(false);
				clearImage.setVisible(false);
				addImage.setVisible(false);
			}
		}
		else {
			addImage.setVisible(false);
			affectImage.setVisible(false);
			clearImage.setVisible(false);
		}
		
		if(isEmpty || hideButtons)
			viewImage.setVisible(false);
		else
			viewImage.setVisible(true);		
	}
	
	public void hideButtons(boolean hideButtons) {
		this.hideButtons = hideButtons;
		setButtonVisibility();
	}
	
	public void setHideAffectButton(boolean hideAffectButton) {
		this.hideAffectButton = hideAffectButton;
	}
	
	public void setHideClearButton(boolean hideClearButton) {
		this.hideClearButton = hideClearButton;
	}
	
	public void setHideCreateButton(boolean hideCreateButton) {
		this.hideCreateButton = hideCreateButton;
	}

	
	/**
	 * Removes all elements from the widget
	 */
	public void emptyList() {
		values.clear();
		list.clear();
		isEmpty = true;
		setButtonVisibility();
	}
	
	public boolean isEmpty() {
		return isEmpty;
	}

	/**
	 * Defines that the field shall notify value changes
	 * @param eventBus the event bus that will be used to fire the value change events
	 */
	public void notifyChanges(final EventBus eventBus) {
		if(eventBus!=null) {
			notifyChanges = true;
			this.addHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					eventBus.fireEvent(new FieldValueChangeEvent(ImogMultiRelationBox.this));
				}			
			}, ChangeEvent.getType());	
		}
	}

	@Override
	public void showErrors(List<EditorError> errors) {
		errorLabel.showErrors(errors);
	}
	
	public void setLabelWidth(String width) {
		fieldBox.setLabelWidth(width);
	}
	
	public void setBoxWidth(int width) {
	}
	
	@Override
	protected void onUnload() {
		for(HandlerRegistration r : registrations)
			r.removeHandler();
		registrations.clear();
		super.onUnload();
	}



}
