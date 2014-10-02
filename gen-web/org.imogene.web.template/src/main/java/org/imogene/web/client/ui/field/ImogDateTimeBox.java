package org.imogene.web.client.ui.field;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.imogene.web.client.ui.field.error.ImogErrorLabel;
import org.imogene.web.client.util.DateUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.HasEditorErrors;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

public class ImogDateTimeBox extends Composite implements ImogField<Date>,
		LeafValueEditor<Date>, HasEditorErrors<Date> {

	private static final Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, ImogDateTimeBox> {
	}


	/* status - behavior */
	private boolean edited = false;
	private boolean isValid = true;

	/* widgets */
	@UiField
	ImogErrorLabel errorLabel;
	@UiField
	@Ignore
	ImogFieldAbstract fieldBox;
	@UiField
	@Ignore
	TextBox dateBox;

	/**
	 * Constructor
	 */
	public ImogDateTimeBox() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	/**
	 * Constructor
	 * Can be used when the box is not used as an editor but just as a widget
	 * @param withExceptions true if the box shall throw exceptions when an unvalid date is entered
	 */
	public ImogDateTimeBox(boolean withExceptions) {
		initWidget(uiBinder.createAndBindUi(this));
	}


	public void setEdited(boolean enabled) {
		dateBox.setEnabled(enabled);
		if (!enabled) {
			dateBox.addStyleDependentName("disabled");
		} else {
			dateBox.removeStyleDependentName("disabled");
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
	public void setValue(Date value) {
		if(value!=null)
			dateBox.setValue(DateUtil.getDateTime(value));
	}

	@Override
	public Date getValue() {
		
		String text = dateBox.getValue();
		if(text!=null && !text.isEmpty())
			return DateUtil.parseDateTime(text);
		else
			return null;
	}
	
	/**
	 * Used to get the value without throwing an exception nor an error message
	 * @return
	 */
	public Date getValueWithoutParseException() {
		String text = dateBox.getValue();
		if(text!=null && !text.isEmpty())
			return DateUtil.parseDateTime(text);
		else
			return null;
	}
	
	/**
	 * To be used with the constructor ImogDateBox(boolean withExceptions) with withExceptions=true
	 * Can be used when the box is not used as an editor but just as a widget
	 * @return
	 * @throws ParseException
	 */
	public Date getValueWithParseException() {
		String text = dateBox.getValue();
		if(text!=null && !text.isEmpty())
			return DateUtil.parseDateTime(text);
		else
			return null;
	}
	
	/**
	 * Tells if the entered value is a valid Integer
	 * @return
	 */
	public boolean isValid() {
		return isValid;
	}
	
	/**
	 * Gets the text of the box
	 * @return
	 */
	public String getText() {
		return dateBox.getText();
	}

	public void setLabelWidth(String width) {
		fieldBox.setLabelWidth(width);
	}
	
	/**
	 * Sets the widget's width
	 */
	public void setBoxWidth(int width) {
		dateBox.getElement().getStyle().setProperty("width", width + "px");
	}
	
	/**
	 * Defines that the field shall notify value changes
	 * @param eventBus the event bus that will be used to fire the value change events
	 */
	public void notifyChanges(final EventBus eventBus) {
		if(eventBus!=null) {	
		}
	}
	
	@Override
	public void showErrors(List<EditorError> errors) {		
		errorLabel.showErrors(errors);
	}	

}
