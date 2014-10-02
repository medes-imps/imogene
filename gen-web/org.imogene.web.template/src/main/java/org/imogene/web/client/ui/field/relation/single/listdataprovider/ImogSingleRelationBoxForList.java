package org.imogene.web.client.ui.field.relation.single.listdataprovider;

import java.util.List;

import org.imogene.web.client.event.FieldValueChangeEvent;
import org.imogene.web.client.ui.field.ImogField;
import org.imogene.web.client.ui.field.ImogFieldAbstract;
import org.imogene.web.client.ui.field.error.ImogErrorLabel;
import org.imogene.web.client.util.ImogBeanRenderer;
import org.imogene.web.shared.proxy.ImogBeanProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.HasEditorErrors;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Field Editor for Relation fields with cardinality = 1
 * @author MEDES-IMPS
 */
public class ImogSingleRelationBoxForList<T extends ImogBeanProxy> extends Composite implements ImogField<T>,
		LeafValueEditor<T>, HasEditorErrors<T> {

	private static final Binder uiBinder = GWT.create(Binder.class);

	@SuppressWarnings("rawtypes")
	interface Binder extends UiBinder<Widget, ImogSingleRelationBoxForList> {
	}

	/* widgets */
	@UiField
	ImogErrorLabel errorLabel;
	@UiField
	@Ignore
	ImogFieldAbstract fieldBox;
	@UiField (provided=true)
	@Ignore
	ImogSingleRelationForList<T> singleRelationBox;	
	
	

	public ImogSingleRelationBoxForList(ImogBeanRenderer beanRenderer, boolean hideButtons) {
		singleRelationBox = new ImogSingleRelationForList<T>(beanRenderer, hideButtons);
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public ImogSingleRelationBoxForList(ImogBeanRenderer beanRenderer) {
		this(true, beanRenderer);
	}
	
	
	public ImogSingleRelationBoxForList(boolean canCreateEntity, ImogBeanRenderer beanRenderer) {
		singleRelationBox = new ImogSingleRelationForList<T>(canCreateEntity, beanRenderer);
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void addValuesToList(List<T> values) {		
		singleRelationBox.addValuesToList(values);
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
		return singleRelationBox.isEdited();
	}

	public void setLocked(boolean locked) {
		singleRelationBox.setLocked(locked);
	}
	
	public void setViewClickHandler(ClickHandler handler){
		singleRelationBox.setViewClickHandler(handler);
	}

	/**
	 * Get the value selected
	 * @return the value selected
	 */
	public T getValue(){		
		return singleRelationBox.getValue();
	}
	
	/**
	 * Get the display text of the selected value
	 * @return the display text of the selected value
	 */
	public String getText(){		
		return singleRelationBox.getText();
	}
	
	/**
	 * Sets the listbox selected value 
	 * @param value the selected value 
	 */
	public void setValue(T value){
		singleRelationBox.setValue(value);
	}
	
	public void setValue(T value, boolean notifyChange){
		singleRelationBox.setValue(value, notifyChange);
	}

	public void setEdited(boolean enabled) {				
		singleRelationBox.setEnabled(enabled);
	}
	
	/**
	 * Clear the content of the MedanyListBox TextBox
	 */
	public void clear() {
		singleRelationBox.clear();	
	}
	
	public void hideButtons(boolean hideButtons) {
		singleRelationBox.hideButtons(hideButtons);
	}

	/**
	 * Sets if the open button shall be hidden in edit mode
	 * default is false
	 * @param hideOpenButton
	 */
	public void setHideOpenButton(boolean hideOpenButton) {
		singleRelationBox.setHideOpenButton(hideOpenButton);
	}
	
	public void setHideClearButton(boolean hideClearButton) {
		singleRelationBox.setHideClearButton(hideClearButton);
	}
	
	public void hideButtonPanel(boolean hide) {
		singleRelationBox.hideButtonPanel(hide);
	}
	
	/**
	 * Defines that the field shall notify value changes
	 * @param eventBus the event bus that will be used to fire the value change events
	 */
	public void notifyChanges(final EventBus eventBus) {
		if(eventBus!=null) {
			singleRelationBox.addValueChangeHandler(new ValueChangeHandler<String>() {			
				@Override
				public void onValueChange(ValueChangeEvent<String> arg0) {
					eventBus.fireEvent(new FieldValueChangeEvent(ImogSingleRelationBoxForList.this));
				}
			});		
		}
	}

	@Override
	public void showErrors(List<EditorError> errors) {
		errorLabel.showErrors(errors);
	}
	
	public void hideErrors() {
		errorLabel.hideErrors();
	}
	
	public void setLabelWidth(String width) {
		fieldBox.setLabelWidth(width);
	}
	
	public Label getLabelBox() {
		return fieldBox.getLabelBox();
	}
	
	/**
	 * Sets the widget's width
	 */
	public void setBoxWidth(int width) {
		singleRelationBox.setBoxWidth(width);
	}
	
	public void setButtonPanelWidth(String width) {
		singleRelationBox.setButtonPanelWidth(width);
	}

}
