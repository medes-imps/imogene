package org.imogene.web.client.ui.field;

import java.text.ParseException;
import java.util.List;

import org.imogene.web.client.event.FieldValueChangeEvent;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.field.error.ImogErrorLabel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.EditorDelegate;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.HasEditorDelegate;
import com.google.gwt.editor.client.HasEditorErrors;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Field Editor for Email fields 
 * @author MEDES-IMPS
 */
public class ImogEmailBox extends Composite implements ImogField<String>,
		LeafValueEditor<String>, HasEditorErrors<String>,
		HasEditorDelegate<String> {

	private static final Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, ImogEmailBox> {
	}

	public static final String EMAIL_REGEX = "[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]";

	/* status - behavior */
	private boolean edited = false;
	private boolean isValid = true;
	private boolean clickable = true;
	private EditorDelegate<String> delegate;

	/* widgets */
	@UiField
	@Ignore
	ImogFieldAbstract fieldBox;
	@UiField
	ImogErrorLabel errorLabel;
	@UiField
	@Ignore
	TextBox textBox;
	@UiField
	@Ignore
	Image email;

	public ImogEmailBox() {
		initWidget(uiBinder.createAndBindUi(this));
		email.setUrl(GWT.getModuleBaseURL() + "/images/email.png");
	}

	public void setEdited(boolean enabled) {
		textBox.setEnabled(enabled);
		if (!enabled) {
			textBox.addStyleDependentName("disabled");
			email.addStyleDependentName("clickable");
		} else {
			textBox.removeStyleDependentName("disabled");
			email.removeStyleDependentName("clickable");
		}
		edited = enabled;
		clickable = !enabled;
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
	public void showErrors(List<EditorError> errors) {
		errorLabel.showErrors(errors);
	}

	@UiHandler("email")
	void handleClick(ClickEvent e) {
		if (clickable && textBox.getText().length() > 0)
			Window.open("mailto:" + textBox.getText(), "email", "");
	}

	@Override
	public void setValue(String value) {
		textBox.setValue(value);	
	}

	@Override
	public String getValue() {

		String result = textBox.getValue();
		isValid = true;
		if (!result.isEmpty() && !result.matches(EMAIL_REGEX)) {
			isValid = false;
			delegate.recordError(BaseNLS.messages().error_format_email(), result, null);
		}

		if (result.isEmpty())
			return null;
		else
			return result;
	}
	
	/**
	 * Used to get the value without throwing an error message
	 * @return
	 * @throws ParseException
	 */
	public String getValueWithoutError() {

		String result = textBox.getValue();
		isValid = true;
		if (!result.isEmpty() && !result.matches(EMAIL_REGEX)) {
			isValid = false;
		}

		if (result.isEmpty())
			return null;
		else
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
	 * Defines that the field shall notify value changes
	 * @param eventBus the event bus that will be used to fire the value change events
	 */
	public void notifyChanges(final EventBus eventBus) {
		if(eventBus!=null) {
			textBox.addValueChangeHandler(new ValueChangeHandler<String>() {			
				@Override
				public void onValueChange(ValueChangeEvent<String> arg0) {
					eventBus.fireEvent(new FieldValueChangeEvent(ImogEmailBox.this));
				}
			});		
		}
	}

	@Override
	public void setDelegate(EditorDelegate<String> delegate) {
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

}
