package org.imogene.web.client.ui.field.widget;

import java.util.HashSet;
import java.util.Set;

import org.imogene.web.client.i18n.BaseNLS;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;

public class ImogBooleanAsRadio extends ImogWidget<Boolean> {

	private static final Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, ImogBooleanAsRadio> {
	}
	
	protected Set<ValueChangeHandler<Boolean>> valueChangeHandlers = new HashSet<ValueChangeHandler<Boolean>>();

	/* status - behavior */
	private Boolean thisValue = null;
	private boolean edited = false;
	private boolean isStrict = false;

	/* widgets */
	@UiField
	RadioButton yes;
	@UiField
	RadioButton no;
	@UiField
	RadioButton unknown;

	public ImogBooleanAsRadio() {
		initWidget(uiBinder.createAndBindUi(this));

		String groupName = String.valueOf(this.hashCode());
		yes.setName(groupName);
		no.setName(groupName);
		unknown.setName(groupName);

		yes.setText(BaseNLS.constants().boolean_true());
		no.setText(BaseNLS.constants().boolean_false());
		unknown.setText(BaseNLS.constants().boolean_unknown());
	}


	public boolean isEdited() {
		return edited;
	}

	public Boolean getValue() {
		if (unknown.getValue())
			return null;
		if (yes.getValue())
			return true;
		if (no.getValue())
			return false;
		return thisValue;
	}

	public void setValue(Boolean value) {
		if (value == null) {
			unknown.setValue(true);
		} else if (value) {
			yes.setValue(true);
		} else if (!value) {
			no.setValue(true);
		}
		thisValue = value;
		setVisible(thisValue);
	}
	
	
	public void setEnabled(boolean enabled) {
		edited = enabled;
		if (enabled) {
			yes.setVisible(true);
			no.setVisible(true);
			if(!isStrict)
				unknown.setVisible(true);
			else
				unknown.setVisible(false);
		}
	}

	private void setVisible(Boolean value) {
		if(!edited) {
			if (value == null) {
				yes.setVisible(false);
				no.setVisible(false);
				if(!isStrict)
					unknown.setVisible(true);
			} else if (value) {
				yes.setVisible(true);
				no.setVisible(false);
				unknown.setVisible(false);
			} else if (!value) {
				yes.setVisible(false);
				no.setVisible(true);
				unknown.setVisible(false);
			}
		}
	}


	public void addValueChangeHandler(ValueChangeHandler<Boolean> valueChangeHandler) {
		unknown.addValueChangeHandler(valueChangeHandler);
		yes.addValueChangeHandler(valueChangeHandler);
		no.addValueChangeHandler(valueChangeHandler);
	}
	
	/**
	 * Sets if the Boolean shall be strict 
	 * (displays only 'yes' and 'no' options, hides the 'unknown' option)
	 * @param isStrict true to set the Boolean Box as strict
	 */
	public void isStrict(boolean isStrict) {
		this.isStrict = isStrict;
	}


}
