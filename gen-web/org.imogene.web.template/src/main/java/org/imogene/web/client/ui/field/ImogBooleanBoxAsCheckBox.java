package org.imogene.web.client.ui.field;

import java.util.List;

import org.imogene.web.client.event.FieldValueChangeEvent;
import org.imogene.web.client.ui.field.error.ImogErrorLabel;
import org.imogene.web.client.ui.field.widget.ImogBooleanAsCheckBox;

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
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;


/**
 * Field Editor for Boolean fields 
 * @author MEDES-IMPS
 */
public class ImogBooleanBoxAsCheckBox extends Composite implements ImogField<Boolean>,
		LeafValueEditor<Boolean>, HasEditorErrors<Boolean> {

	private static final Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, ImogBooleanBoxAsCheckBox> {
	}

	/* widgets */
	@UiField
	ImogErrorLabel errorLabel;
	@UiField
	@Ignore
	ImogFieldAbstract fieldBox;
	@UiField
	@Ignore
	ImogBooleanAsCheckBox booleanBox;

	public ImogBooleanBoxAsCheckBox() {
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
		return booleanBox.isEdited();
	}


	public Boolean getValue() {
		return booleanBox.getValue();
	}

	public void setValue(Boolean value) {
		booleanBox.setValue(value);
	}
	
	public void setEdited(boolean enabled) {
		booleanBox.setEnabled(enabled);
	}
	
	/**
	 * Defines that the field shall notify value changes
	 * @param eventBus the event bus that will be used to fire the value change events
	 */
	public void notifyChanges(final EventBus eventBus) {
		if(eventBus!=null) {
			booleanBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {			
				@Override
				public void onValueChange(ValueChangeEvent<Boolean> arg0) {
					eventBus.fireEvent(new FieldValueChangeEvent(ImogBooleanBoxAsCheckBox.this));
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
	
	/**
	 * Sets the widget's width
	 */
	public void setBoxWidth(int width) {
		booleanBox.getElement().getStyle().setProperty("width", width + "px");
	}

	/**
	 * Sets if the Boolean shall be strict 
	 * (displays only 'yes' and 'no' options, hides the 'unknown' option)
	 * @param isStrict true to set the Boolean Box as strict
	 */
	public void isStrict(boolean isStrict) {
//		booleanBox.isStrict(isStrict);
	}
	

}
