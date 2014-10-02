package org.imogene.web.client.ui.field;

import java.util.List;

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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Field Editor for Text fields 
 * @author MEDES-IMPS
 */
public class ImogTextBox extends Composite implements ImogField<String>,
		LeafValueEditor<String>, HasEditorErrors<String> {

	private static final Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, ImogTextBox> {
	}

	/* status - behavior */
	private boolean edited = false;

	/* widgets */
	@UiField
	ImogErrorLabel errorLabel;
	@UiField
	@Ignore
	ImogFieldAbstract fieldBox;
	@UiField
	@Ignore
	TextBox textBox;

	public ImogTextBox() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setEdited(boolean enabled) {
		textBox.setEnabled(enabled);
		if (!enabled) {
			textBox.addStyleDependentName("disabled");
		} else {
			textBox.removeStyleDependentName("disabled");
		}
		edited = enabled;
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

	/**
	 */
	@Override
	public void setValue(String value) {
		textBox.setValue(value);	
	}

	/**
	 */
	@Override
	public String getValue() {
		if (textBox.getValue().isEmpty())
			return null;
		else
			return textBox.getValue();
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
					eventBus.fireEvent(new FieldValueChangeEvent(ImogTextBox.this));
				}
			});		
		}
	}

	/**
	 * Defines the width of cell that contains the label
	 * @param width width of cell that contains the label
	 */
	public void setLabelWidth(String width) {
		fieldBox.setLabelWidth(width);
	}
	
	/**
	 * Sets the widget's width
	 */
	public void setBoxWidth(int width) {
		textBox.getElement().getStyle().setProperty("width", width + "px");
	}
	
	@Override
	public void showErrors(List<EditorError> errors) {
		errorLabel.showErrors(errors);
	}

}
