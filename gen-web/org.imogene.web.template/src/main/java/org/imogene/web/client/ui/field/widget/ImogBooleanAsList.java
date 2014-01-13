package org.imogene.web.client.ui.field.widget;

import org.imogene.web.client.i18n.BaseNLS;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class ImogBooleanAsList extends ImogWidget<Boolean> {

	private static final Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, ImogBooleanAsList> {
	}

	/* status - behavior */
	private boolean edited = false;

	/* widgets */
	@UiField
	ListBox listBox;


	public ImogBooleanAsList() {
		initWidget(uiBinder.createAndBindUi(this));		
		listBox.addItem("", "");	
		listBox.addItem(BaseNLS.constants().boolean_true(), "true" );
		listBox.addItem(BaseNLS.constants().boolean_false(), "false");
		listBox.setSelectedIndex(0);
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
		String value = listBox.getValue(listBox.getSelectedIndex());
		if (value.equals("true"))
			return true;
		else if (value.equals("false"))
			return false;
		else
			return null;
	}

	/**
	 * 
	 * @param value
	 */
	public void setValue(Boolean value) {	
		if(value==null)
			listBox.setSelectedIndex(0);
		else if (value)
			listBox.setSelectedIndex(1);
		else
			listBox.setSelectedIndex(2);		
	}
	
	/**
	 * 
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		listBox.setEnabled(enabled);
		if(!enabled){
			listBox.addStyleDependentName("disabled");
		}else{
			listBox.removeStyleDependentName("disabled");
		}
		edited = enabled;
	}


}
