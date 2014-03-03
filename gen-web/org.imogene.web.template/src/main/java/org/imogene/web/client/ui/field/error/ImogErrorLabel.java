package org.imogene.web.client.ui.field.error;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ImogErrorLabel extends Composite {

	private static final Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, ImogErrorLabel> {
	}
	
	private boolean errorDisplayed = false;

	@UiField
	DivElement errorLabel;

	public ImogErrorLabel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void showErrors(List<EditorError> errors) {

		StringBuilder sb = new StringBuilder();
		for (EditorError error : errors) {
			sb.append("\n").append(error.getMessage());
		}

		if (sb.length() == 0) {
			errorLabel.setInnerText("");
			errorLabel.getStyle().setDisplay(Display.NONE);
			return;
		}

		errorLabel.setInnerText(sb.substring(1));
		errorLabel.getStyle().setDisplay(Display.INLINE_BLOCK);
		errorDisplayed = true;		
	}
	
	public void showError(String error) {

		StringBuilder sb = new StringBuilder();
		sb.append("\n").append(error);

		if (sb.length() == 0) {
			errorLabel.setInnerText("");
			errorLabel.getStyle().setDisplay(Display.NONE);
			return;
		}

		errorLabel.setInnerText(sb.substring(1));
		errorLabel.getStyle().setDisplay(Display.INLINE_BLOCK);
		errorDisplayed = true;		
	}
	
	public void hideErrors() {
		if(errorDisplayed) {
			errorLabel.setInnerText(null);
			errorDisplayed = false;
		}
	}

}
