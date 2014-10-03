package org.imogene.web.client.ui.field;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.imogene.web.client.event.FieldValueChangeEvent;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.field.error.ImogErrorLabel;
import org.imogene.web.client.util.DateUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.EditorDelegate;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.HasEditorDelegate;
import com.google.gwt.editor.client.HasEditorErrors;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.web.bindery.event.shared.EventBus;

public class ImogDateBox extends Composite implements ImogField<Date>, LeafValueEditor<Date>, HasEditorErrors<Date>, HasEditorDelegate<Date> {

	private static final Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, ImogDateBox> {
	}

	private EditorDelegate<Date> delegate;

	/* status - behavior */
	private boolean edited = false;
	private boolean formatErrorIdentified = false;
	private boolean isValid = true;

	/* widgets */
	@UiField
	ImogErrorLabel errorLabel;
	@UiField
	@Ignore
	ImogFieldAbstract fieldBox;
	@UiField
	@Ignore
	DateBox dateBox;

	/**
	 * Constructor
	 */
	public ImogDateBox() {
		initWidget(uiBinder.createAndBindUi(this));
		dateBox.setFormat(new ImogDateFormat(DateUtil.getDateFormater()));
	}
	
	public ImogDateBox(DateTimeFormat dateFormater) {
		initWidget(uiBinder.createAndBindUi(this));
		dateBox.setFormat(new ImogDateFormat(dateFormater));
	}

	/**
	 * Constructor
	 * Can be used when the box is not used as an editor but just as a widget
	 * @param withExceptions true if the box shall throw exceptions when an unvalid date is entered
	 */
	public ImogDateBox(boolean withExceptions) {
		initWidget(uiBinder.createAndBindUi(this));
		if(withExceptions)
			dateBox.setFormat(new ImogDateFormatWithExceptions(DateUtil.getDateFormater()));
		else
			dateBox.setFormat(new ImogDateFormat(DateUtil.getDateFormater()));
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
	public void setDelegate(EditorDelegate<Date> delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public void setValue(Date value) {
		dateBox.setValue(value);
	}

	@Override
	public Date getValue() {
		return dateBox.getValue();
	}
	
	/**
	 * Used to get the value without throwing an exception nor an error message
	 * @return
	 */
	public Date getValueWithoutParseException() {
		formatErrorIdentified = true;
		return dateBox.getValue();
	}
	
	/**
	 * To be used with the constructor ImogDateBox(boolean withExceptions) with withExceptions=true
	 * Can be used when the box is not used as an editor but just as a widget
	 * @return
	 * @throws ParseException
	 */
	public Date getValueWithParseException() {
		formatErrorIdentified = true;
		return dateBox.getValue();
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
		return dateBox.getTextBox().getText();
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
			dateBox.addValueChangeHandler(new ValueChangeHandler<Date>() {			
				@Override
				public void onValueChange(ValueChangeEvent<Date> arg0) {
					eventBus.fireEvent(new FieldValueChangeEvent(ImogDateBox.this));
				}
			});		
		}
	}
	
	@Override
	public void showErrors(List<EditorError> errors) {		
		errorLabel.showErrors(errors);
		formatErrorIdentified = false;
	}	

	public Label getLabelWidget() {
		return fieldBox.getLabelBox();
	}

	public TextBox getTextBox() {
		return dateBox.getTextBox();
	}

	/**
	 *
	 */
	private class ImogDateFormat implements DateBox.Format {

		private final DateTimeFormat dateTimeFormat;

		public ImogDateFormat(DateTimeFormat dateTimeFormat) {
			this.dateTimeFormat = dateTimeFormat;
		}

		@Override
		public String format(DateBox dateBox, Date date) {
			if (date == null)
				return "";
			else
				return dateTimeFormat.format(date);
		}

		@SuppressWarnings("deprecation")
		@Override
		public Date parse(DateBox dateBox, String text, boolean reportError) {

			Date date = null;
			try {
				if (text.length() > 0){ 
					date = dateTimeFormat.parse(text);
				}
				isValid = true;
			} catch (IllegalArgumentException exception) {
				try {
					date = new Date(text);
					isValid = true;
				} catch (IllegalArgumentException e) {
					isValid = false;
					if (reportError) {
						if (!formatErrorIdentified) {
							delegate.recordError(BaseNLS.messages().error_format_date(BaseNLS.constants().format_date()), text, e);
							formatErrorIdentified = true;
						}
					}
					return null;
				}
			}
			return date;
		}

		@Override
		public void reset(DateBox dateBox, boolean abandon) {
		}
	}
	
	
	/**
	 *
	 */
	private class ImogDateFormatWithExceptions implements DateBox.Format {

		private final DateTimeFormat dateTimeFormat;

		public ImogDateFormatWithExceptions(DateTimeFormat dateTimeFormat) {
			this.dateTimeFormat = dateTimeFormat;
		}

		@Override
		public String format(DateBox dateBox, Date date) {
			if (date == null)
				return "";
			else
				return dateTimeFormat.format(date);
		}

		@Override
		public Date parse(DateBox dateBox, String text, boolean reportError) throws IllegalArgumentException{
			
			Date date = null;
				if (text!=null && text.length() > 0) {
					try {
						date = dateTimeFormat.parse(text);
					} catch (Exception e) {
						if (formatErrorIdentified) {
							formatErrorIdentified = false;
							throw new IllegalArgumentException(e);
						}
					}
				}
			return date;
		}

		@Override
		public void reset(DateBox dateBox, boolean abandon) {
		}
	}

}
