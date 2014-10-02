package org.imogene.web.client.ui.field;

import java.util.Date;
import java.util.List;

import org.imogene.web.client.event.FieldValueChangeEvent;
import org.imogene.web.client.ui.field.error.ImogErrorLabel;
import org.imogene.web.client.ui.field.widget.ImogTimePicker;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.HasEditorErrors;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Field Editor for Time fields 
 * @author MEDES-IMPS
 */
public class ImogTimeBox extends Composite implements ImogField<Date>,
		LeafValueEditor<Date>, HasEditorErrors<Date> {

	private static final Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, ImogTimeBox> {
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
	ImogTimePicker timeBox;

	public ImogTimeBox() {
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

	public Date getValue() {
		return timeBox.getValue();
	}

	public void setValue(Date value) {
		timeBox.setValue(value);
	}
	
	public void setEdited(boolean enabled) {
		timeBox.setEnabled(enabled);
		edited = enabled;
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
		timeBox.getElement().getStyle().setProperty("width", width + "px");
	}

	/**
	 * Defines that the field shall notify value changes
	 * @param eventBus the event bus that will be used to fire the value change events
	 */
	public void notifyChanges(final EventBus eventBus) {
		if(eventBus!=null) {
			timeBox.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					eventBus.fireEvent(new FieldValueChangeEvent(ImogTimeBox.this));
				}			
			});		
		}
	}
	
	/**
	 * Enables to define the different minutes that will be available in the box
	 * @param minuteStep the step between 2 minute values (default 1)
	 */
	public void setMinuteStep(int minuteStep) {
		timeBox.setMinuteStep(minuteStep);
	}

}
