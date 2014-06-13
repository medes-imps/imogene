package org.imogene.web.client.ui.field.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;

public class ImogBooleanAsCheckBox extends ImogWidget<Boolean> {

	private static final Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, ImogBooleanAsCheckBox> {
	}

	/* status - behavior */
	private boolean edited = false;

	/* widgets */
	@UiField
	CheckBox checkBox;

	public ImogBooleanAsCheckBox() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	/**
	 * 
	 * @return
	 */
	public boolean isEdited() {
		return edited;
	}

	/**
	 * 
	 * @return
	 */
	public Boolean getValue() {
		return checkBox.getValue();
	}

	/**
	 * 
	 * @param value
	 */
	public void setValue(Boolean value) {
		checkBox.setValue(value);
	}

	/**
	 * 
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		checkBox.setEnabled(enabled);
		edited = enabled;
	}
	
	public void addValueChangeHandler(ValueChangeHandler<Boolean> valueChangeHandler) {
		checkBox.addValueChangeHandler(valueChangeHandler);
	}

}
