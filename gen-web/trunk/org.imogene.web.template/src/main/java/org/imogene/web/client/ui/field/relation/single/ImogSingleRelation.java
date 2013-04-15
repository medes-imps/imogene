package org.imogene.web.client.ui.field.relation.single;

import java.util.ArrayList;
import java.util.List;

import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.field.widget.ImogWidget;
import org.imogene.web.client.ui.table.ImogBeanDataProvider;
import org.imogene.web.client.util.ImogBeanRenderer;
import org.imogene.web.shared.proxy.ImogBeanProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ImogSingleRelation<T extends ImogBeanProxy> extends ImogWidget<T> {

	private static final Binder uiBinder = GWT.create(Binder.class);

	@SuppressWarnings("rawtypes")
	interface Binder extends UiBinder<Widget, ImogSingleRelation> {
	}
	
	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();

	/* status - behavior */
	private boolean edited = false;
	private boolean canCreateEntity = true;
	private boolean isLocked = false;
	private boolean hideButtons = false;
	private boolean hideOpenButton = false;
	private boolean withoutProvider = false;
	
	/* listbox selected value */
	private T value;
	private ImogBeanDataProvider<T> provider;
	private ImogBeanRenderer beanRenderer;

	/* widgets */
	@UiField
	TextBox textBox;
	@UiField
	Image openIcon;
	@UiField
	Image viewImage;
	@UiField
	Image addImage;
	@UiField
	Image clearImage;	
	
	
	public ImogSingleRelation(ImogBeanDataProvider<T> provider, ImogBeanRenderer beanRenderer, boolean hideButtons) {
		this(false, provider, beanRenderer);
		this.hideButtons = hideButtons;
	}

	public ImogSingleRelation(ImogBeanDataProvider<T> provider, ImogBeanRenderer beanRenderer) {
		this(true, provider, beanRenderer);
	}
	
	
	public ImogSingleRelation(boolean canCreateEntity, ImogBeanDataProvider<T> provider, ImogBeanRenderer beanRenderer) {
		initWidget(uiBinder.createAndBindUi(this));
		openIcon.setUrl(GWT.getModuleBaseURL()+ "images/ico_downward.png");
		viewImage.setTitle(BaseNLS.constants().button_view());
		viewImage.setUrl(GWT.getModuleBaseURL()+ "images/relation_view.png");
		addImage.setTitle(BaseNLS.constants().button_add());
		addImage.setUrl(GWT.getModuleBaseURL()+ "images/relation_add.png");
		clearImage.setTitle(BaseNLS.constants().button_remove());
		clearImage.setUrl(GWT.getModuleBaseURL()+ "images/relation_remove.png");
		this.canCreateEntity = canCreateEntity;
		this.provider = provider;
		this.beanRenderer = beanRenderer;
		if(provider==null)
			withoutProvider = true;
	}


	@Override
	public boolean isEdited() {
		return edited;
	}

	public void setLocked(boolean locked) {
		isLocked = locked;
	}
	
	private void showPopUpListBox() {
		
		ImogSingleRelationBoxPopUp<T> popupListBox = new ImogSingleRelationBoxPopUp<T>(this, provider, beanRenderer);
		popupListBox.popup.setPopupPosition(textBox.getAbsoluteLeft()-1, textBox.getAbsoluteTop() + textBox.getOffsetHeight());			
		popupListBox.show();
		
	}
	
	@UiHandler("textBox")
	void onClickTextBox(ClickEvent e) {
		if(edited && !withoutProvider && !isLocked && !hideOpenButton)
			showPopUpListBox();

	}
	
	@UiHandler("openIcon")
	void onOpenListBox(ClickEvent e) {
		if(edited)
			showPopUpListBox();
	}
	
	
	public void setViewClickHandler(ClickHandler handler){
		registrations.add(viewImage.addClickHandler(handler));
	}
	
	public void setAddClickHandler(ClickHandler handler){
		registrations.add(addImage.addClickHandler(handler));
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
	public void setEnabled(boolean enabled) {
				
		if(!enabled || isLocked){
			textBox.addStyleDependentName("disabled");
			edited = false;
		}
		else{
			textBox.removeStyleDependentName("disabled");
			edited = true;
		}
		setButtonVisibility();
	}
	
	/**
	 * 
	 */
	private void setButtonVisibility() {
		
		if(edited) {
			
			if(!isLocked) {
				
				if(!withoutProvider && !hideOpenButton)
					openIcon.setVisible(true);
				
				if(!canCreateEntity || hideButtons)
					addImage.setVisible(false);
				else
					addImage.setVisible(true);
				
				if(value!=null)
					clearImage.setVisible(true);
				else
					clearImage.setVisible(false);
			}
			else {
				openIcon.setVisible(false);
				clearImage.setVisible(false);
				addImage.setVisible(false);
			}
		}
		else {
			addImage.setVisible(false);
			openIcon.setVisible(false);
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

	/**
	 * Sets if the open button shall be hidden in edit mode
	 * default is false
	 * @param hideOpenButton
	 */
	public void setHideOpenButton(boolean hideOpenButton) {
		this.hideOpenButton = hideOpenButton;
	}

	@Override
	protected void onUnload() {
		for(HandlerRegistration r : registrations)
			r.removeHandler();
		registrations.clear();
		super.onUnload();
	}


	public void addValueChangeHandler(ValueChangeHandler<String> handler) {
		textBox.addValueChangeHandler(handler);
	}

}
