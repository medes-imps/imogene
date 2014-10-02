package org.imogene.web.client.ui.field;

import java.text.ParseException;
import java.util.List;

import org.imogene.web.client.event.FieldValueChangeEvent;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.field.error.ImogErrorLabel;
import org.imogene.web.client.ui.field.widget.ImogIntBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.EditorDelegate;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.HasEditorDelegate;
import com.google.gwt.editor.client.HasEditorErrors;
import com.google.gwt.editor.client.LeafValueEditor;
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
 * Field Editor for Integer fields 
 * @author MEDES-IMPS
 */
public class ImogIntegerBox extends Composite implements ImogField<Integer>,
		LeafValueEditor<Integer>, HasEditorErrors<Integer>, HasEditorDelegate<Integer> {

	private static final Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, ImogIntegerBox> {
	}

	/* status - behavior */
	private boolean edited = false;
	private boolean isValid = true;
	private EditorDelegate<Integer> delegate;

	/* widgets */
	@UiField
	@Ignore
	ImogFieldAbstract fieldBox;
	@UiField
	ImogErrorLabel errorLabel;
	@UiField
	@Ignore
	ImogIntBox textBox;
	@UiField
	@Ignore
	Label unitBox;

	public ImogIntegerBox() {
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


	@Override
	public void setValue(Integer value) {
		textBox.setValue(value);
	}


	@Override
	public Integer getValue() {
		
		Integer result = null;
		try {
			result = textBox.getValueOrThrow();
			isValid = true;
		} catch (ParseException e) {
			isValid = false;
			if(delegate!=null)
				delegate.recordError(BaseNLS.messages().error_format_int(), textBox.getText(), null);
		}	
		return result;
	}

	/**
	 * Used to get the value without throwing an exception nor an error message
	 * @return
	 */
	public Integer getValueWithoutParseException() {
		
		Integer result = null;
		try {
			result = textBox.getValueOrThrow();
			isValid = true;
		} catch (ParseException e) {
			isValid = false;
		}	
		return result;
	}
	
	/**
	 * Tells if the entered value is a valid Integer
	 * @return
	 */
	public boolean isValid() {
		return isValid;
	}
	
	/**
	 * Can be used when the box is not used as an editor but just as a widget
	 * Throws an exception when an invalid integer is entered
	 * @return
	 * @throws ParseException
	 */
	public Integer getValueWithParseException() throws ParseException {
		return textBox.getValueOrThrow();	
	}
	
	/**
	 * Gets the text of the box
	 * @return
	 */
	public String getText() {
		return textBox.getText();
	}
	
	
	/**
	 * Defines that the field shall notify value changes
	 * @param eventBus the event bus that will be used to fire the value change events
	 */
	public void notifyChanges(final EventBus eventBus) {
		if(eventBus!=null) {
			textBox.addValueChangeHandler(new ValueChangeHandler<Integer>() {			
				@Override
				public void onValueChange(ValueChangeEvent<Integer> arg0) {
					eventBus.fireEvent(new FieldValueChangeEvent(ImogIntegerBox.this));
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
	
	@Override
	public void setDelegate(EditorDelegate<Integer> delegate) {
		this.delegate = delegate;
		
	}
	
	public void setLabelWidth(String width) {
		fieldBox.setLabelWidth(width);
	}
	
	/**
	 * Sets the widget's width
	 */
	public void setBoxWidth(int width) {
		textBox.getElement().getStyle().setProperty("width", width + "px");
	}
	
	public void setUnit(String unit) {
		unitBox.setVisible(true);
		unitBox.setText(unit);
	}

}
