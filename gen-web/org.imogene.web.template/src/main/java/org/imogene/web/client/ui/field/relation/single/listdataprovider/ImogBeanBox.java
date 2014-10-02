package org.imogene.web.client.ui.field.relation.single.listdataprovider;

import java.util.ArrayList;
import java.util.List;

import org.imogene.web.client.event.FieldValueChangeEvent;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.field.ImogField;
import org.imogene.web.client.ui.field.ImogFieldAbstract;
import org.imogene.web.client.ui.field.error.ImogErrorLabel;
import org.imogene.web.client.util.ImogBeanRenderer;
import org.imogene.web.shared.proxy.ImogBeanProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.HasEditorErrors;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

public class ImogBeanBox<T extends ImogBeanProxy> extends Composite implements ImogField<T>, LeafValueEditor<T>, HasEditorErrors<T>{

	private static final Binder uiBinder = GWT.create(Binder.class);

	@SuppressWarnings("rawtypes")
	interface Binder extends UiBinder<Widget, ImogBeanBox> {
	}
	
	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();

	/* status - behavior */
	private boolean edited = false;
	private boolean isLocked = false;
	private boolean hideButtons = false;
	
	/* listbox selected value */
	private T value;
	private ImogBeanRenderer beanRenderer;

	/* widgets */
	@UiField
	ImogErrorLabel errorLabel;
	@UiField
	@Ignore
	ImogFieldAbstract fieldBox;
	@UiField
	TextBox textBox;
	@UiField
	Image viewImage;
	@UiField
	Image clearImage;	
	
	
	public ImogBeanBox(ImogBeanRenderer beanRenderer, boolean hideButtons) {
		this(beanRenderer);
		this.hideButtons = hideButtons;
	}

	public ImogBeanBox(ImogBeanRenderer beanRenderer) {
		initWidget(uiBinder.createAndBindUi(this));
		viewImage.setTitle(BaseNLS.constants().button_view());
		viewImage.setUrl(GWT.getModuleBaseURL()+ "images/relation_view.png");
		clearImage.setTitle(BaseNLS.constants().button_remove());
		clearImage.setUrl(GWT.getModuleBaseURL()+ "images/relation_remove.png");
		this.beanRenderer = beanRenderer;
	}
	
	@Override
	public boolean isEdited() {
		return edited;
	}

	public void setLocked(boolean locked) {
		isLocked = locked;
	}
	
	
	public void setViewClickHandler(ClickHandler handler){
		registrations.add(viewImage.addClickHandler(handler));
	}
	
	@UiHandler("clearImage")
	void onRemoveEntity(ClickEvent e) {
		clear();
	}	
	

	/**
	 * Get the value selected
	 * @return the value selected
	 */
	public T getValue(){		
		return value;
	}
	
	/**
	 * Get the display text of the selected value
	 * @return the display text of the selected value
	 */
	public String getText(){		
		return textBox.getText();
	}
	
	/**
	 * Sets the listbox selected value 
	 * @param value the selected value 
	 */
	public void setValue(T value){
		this.value = value;
		if(value!=null)
			textBox.setValue(beanRenderer.getDisplayValue(value));
		else
			textBox.setValue("");
		setButtonVisibility();
	}
	
	/**
	 * Sets the listbox selected value 
	 * @param value the selected value 
	 * @param notifyChange if true notify changes
	 */
	public void setValue(T value, boolean notifyChange){
		setValue(value);
		if(notifyChange)
			ValueChangeEvent.fire(textBox, textBox.getValue());
	}
	
	/**
	 * 
	 */
	public void setEdited(boolean enabled) {		
		this.edited = enabled;
		setButtonVisibility();
	}
	
	/**
	 * 
	 */
	private void setButtonVisibility() {
		
		if(edited) {
			
			if(!isLocked) {
				
				if(value!=null)
					clearImage.setVisible(true);
				else
					clearImage.setVisible(false);
			}
			else {
				clearImage.setVisible(false);
			}
		}
		else {
			clearImage.setVisible(false);
		}
		
		if(value==null || hideButtons)
			viewImage.setVisible(false);
		else
			viewImage.setVisible(true);
		
	}
	
	/**
	 * CLear the content of the MedanyListBox TextBox
	 */
	public void clear() {
		setValue(null, true);
	}
	
	public void hideButtons(boolean hideButtons) {
		this.hideButtons = hideButtons;
		setButtonVisibility();
	}
	
	/**
	 * Sets the widget's width
	 */
	public void setBoxWidth(String width) {
		textBox.getElement().getStyle().setProperty("width", width);
	}



	@Override
	protected void onUnload() {
		for(HandlerRegistration r : registrations)
			r.removeHandler();
		registrations.clear();
		super.onUnload();
	}

	@Override
	public void setLabel(String label) {
		fieldBox.setLabel(label);
	}
	
	public void setLabel(String label, HorizontalAlignmentConstant alignment) {
		fieldBox.setLabel(label, alignment);
	}

	/**
	 * Defines that the field shall notify value changes
	 * @param eventBus the event bus that will be used to fire the value change events
	 */
	public void notifyChanges(final EventBus eventBus) {
		if(eventBus!=null) {
			textBox.addValueChangeHandler(new ValueChangeHandler<String>() {			
				@Override
				public void onValueChange(ValueChangeEvent<String> arg0) {
					eventBus.fireEvent(new FieldValueChangeEvent(ImogBeanBox.this));
				}
			});		
		}
	}

	@Override
	public void showErrors(List<EditorError> errors) {
		errorLabel.showErrors(errors);
	}
	
	public void setLabelWidth(String width) {
		fieldBox.setLabelWidth(width);
	}

}
