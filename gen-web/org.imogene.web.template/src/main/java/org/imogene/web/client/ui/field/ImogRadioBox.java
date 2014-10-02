package org.imogene.web.client.ui.field;

import java.util.List;
import java.util.Vector;

import org.imogene.web.client.event.FieldValueChangeEvent;
import org.imogene.web.client.ui.field.error.ImogErrorLabel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.HasEditorErrors;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Field Editor for Enumeration fields with multiple selection
 * @author MEDES-IMPS
 */
public class ImogRadioBox extends Composite implements ImogField<String>,
		LeafValueEditor<String>, HasEditorErrors<String>, ValueChangeHandler<Boolean>{

	private static final Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, ImogRadioBox> {
	}

	/* status - behavior */
	protected boolean notifyChange = false;
	protected EventBus eventBus = null;
	private boolean edited = false;
	private List<RadioButton> choices = new Vector<RadioButton>();

	/* widgets */
	@UiField
	ImogErrorLabel errorLabel;
	@UiField
	@Ignore
	ImogFieldAbstract fieldBox;
	@UiField
	@Ignore
	VerticalPanel layout;

	public ImogRadioBox() {
		initWidget(uiBinder.createAndBindUi(this));
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

	public String getValue() {
//		StringBuffer result = new StringBuffer();
		String result = null;
		for(RadioButton choice : choices){
			if(choice.getValue()){
				result = choice.getFormValue();
			}
		}
		return result;
	}

	public void setValue(String value) {
		if (value == null) {
			for (RadioButton choice : choices)
				choice.setValue(false);
		} else {

				for (RadioButton choice : choices) {
					if (value.equals(choice.getFormValue())) {
						choice.setValue(true);
						choice.setVisible(true);
					} //else {
						//if (!edited && !choice.getValue())
						//	choice.setVisible(false);
					//}

				}
		}
	}

	public void setEdited(boolean enabled) {
		
		for(RadioButton choice: choices){
			choice.setEnabled(enabled);
//			if(enabled)
//				choice.setVisible(true);
		}		
		edited = enabled;
	}

	public void clear(){
		layout.clear();
	}
	
	/**
	 * Add a choice item widget
	 * @param choice the widget to add
	 */
	public void addItem(RadioButton choice){
		choice.setStyleName("imogene-Radio");
		choices.add(choice);
		choice.addValueChangeHandler(this);
		layout.add(choice);
	}
	
	/**
	 * Defines that the field shall notify value changes
	 * @param eventBus the event bus that will be used to fire the value change events
	 */
	public void notifyChanges(final EventBus eventBus) {	
		if(eventBus!=null) {
			this.eventBus = eventBus;
			notifyChange = true;
		}
	}

	@Override
	public void showErrors(List<EditorError> errors) {
		errorLabel.showErrors(errors);
	}
	
	public void setLabelWidth(String width) {
		fieldBox.setLabelWidth(width);
	}
	
	/**
	 * Sets the widget's width
	 */
	public void setBoxWidth(int width) {
		layout.getElement().getStyle().setProperty("width", width + "px");
	}


	@Override
	public void onValueChange(ValueChangeEvent<Boolean> event) {
		if(notifyChange) {
			eventBus.fireEvent(new FieldValueChangeEvent(ImogRadioBox.this));
		}
	}

}
