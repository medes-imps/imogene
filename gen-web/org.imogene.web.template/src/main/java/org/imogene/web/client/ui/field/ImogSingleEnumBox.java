package org.imogene.web.client.ui.field;

import java.util.List;

import org.imogene.web.client.event.FieldValueChangeEvent;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.field.error.ImogErrorLabel;

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
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Field Editor for Enumeration fields with single selection
 * @author MEDES-IMPS
 */
public class ImogSingleEnumBox extends Composite implements ImogField<String>, LeafValueEditor<String>, HasEditorErrors<String> {

	private static final Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, ImogSingleEnumBox> {
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
	ListBox listBox;

	@UiField
	@Ignore
	TextBox textBox;

	public ImogSingleEnumBox() {
		initWidget(uiBinder.createAndBindUi(this));

		listBox.addItem("", BaseNLS.constants().enumeration_unknown());
		listBox.setSelectedIndex(0);
		textBox.setVisible(false);
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

	public String getValue() {
		String result = listBox.getValue(listBox.getSelectedIndex());
		if (result.equals(""))
			return null;
		else
			return result;
	}

	public void setValue(String value) {
		listBox.setSelectedIndex(0);
		for (int i = 0; i < listBox.getItemCount(); i++) {
			if (listBox.getValue(i).equals(value)) {
				listBox.setSelectedIndex(i);
				return;
			}
		}
	}

	public void setEdited(boolean enabled) {
		listBox.setEnabled(enabled);
		// if(!enabled){
		// listBox.addStyleDependentName("disabled");
		// }else{
		// listBox.removeStyleDependentName("disabled");
		// }
		if (!enabled) {
			listBox.addStyleDependentName("disabled");
			String itemText = listBox.getItemText(listBox.getSelectedIndex());
			listBox.setVisible(false);
			textBox.setText(itemText);
			textBox.setVisible(true);
		} else {
			listBox.removeStyleDependentName("disabled");
			textBox.setVisible(false);
			listBox.setVisible(true);
		}

		edited = enabled;

	}

	/**
	 * Removes all the items from the enumeration field
	 */
	public void removeItems() {
		listBox.clear();
	}

	/**
	 * Add an item to the enumeration field.
	 * @param label the item label
	 * @param value the item value.
	 */
	public void addItem(String value, String label) {
		listBox.addItem(label, value);
	}

	/**
	 * Sets the widget's width
	 */
	public void setBoxWidth(int width) {
		listBox.getElement().getStyle().setProperty("width", width + "px");
		textBox.getElement().getStyle().setProperty("width", width + "px");
	}

	/**
	 * Defines that the field shall notify value changes
	 * @param eventBusthe event bus that will be used to fire the value change events
	 */
	public void notifyChanges(final EventBus eventBus) {
		if (eventBus != null) {
			listBox.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					eventBus.fireEvent(new FieldValueChangeEvent(
							ImogSingleEnumBox.this));
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

	public void setLabelWidth(String width) {
		fieldBox.setLabelWidth(width);
	}
}
