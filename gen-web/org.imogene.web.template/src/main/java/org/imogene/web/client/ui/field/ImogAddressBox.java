package org.imogene.web.client.ui.field;

import java.util.List;

import org.imogene.web.client.event.FieldValueChangeEvent;
import org.imogene.web.client.ui.field.error.ImogErrorLabel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.EditorError;
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
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;


/**
 * Field Editor for Address fields 
 * @author MEDES-IMPS
 */
public class ImogAddressBox extends Composite implements ImogField<String>,
		LeafValueEditor<String>, HasEditorErrors<String> {

	private static final Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, ImogAddressBox> {
	}

	/* status - behavior */
	private boolean edited = false;
	private boolean clickable = true;
	private AddressFieldBehavior thisBehavior;

	/* widgets */
	@UiField
	@Ignore
	ImogFieldAbstract fieldBox;
	@UiField
	ImogErrorLabel errorLabel;
	@UiField
	@Ignore
	TextArea textBox;
	@UiField
	@Ignore
	Image viewOnMap;

	public ImogAddressBox() {
		this(null);
	}

	public ImogAddressBox(AddressFieldBehavior behavior) {

		initWidget(uiBinder.createAndBindUi(this));

		if (behavior == null) {
			thisBehavior = new DefaultAddressBehavior();
		} else {
			thisBehavior = behavior;
		}
		viewOnMap.setUrl(GWT.getModuleBaseURL() + "/images/view_on_map-24.png");
	}

	public void setEdited(boolean enabled) {
		textBox.setEnabled(enabled);
		if (!enabled) {
			textBox.addStyleDependentName("disabled");
			viewOnMap.addStyleDependentName("clickable");
		} else {
			textBox.removeStyleDependentName("disabled");
			viewOnMap.removeStyleDependentName("clickable");
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

	@UiHandler("viewOnMap")
	void handleClick(ClickEvent e) {
		if (clickable && textBox.getText().length() > 0)
			thisBehavior.addressAction(textBox.getText());
	}

	/**
	 */
	@Override
	public void setValue(String value) {
		textBox.setValue(value);	
	}

	/**
	 */
	@Override
	public String getValue() {
		if (textBox.getValue().isEmpty())
			return null;
		else
			return textBox.getValue();
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
					eventBus.fireEvent(new FieldValueChangeEvent(ImogAddressBox.this));
				}
			});		
		}
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
		textBox.getElement().getStyle().setProperty("width", width + "px");
	}

	/* INTERNAL CLASSES/INTERFACES */
	/**
	 * Interface which describes the behavior of the widget when the user
	 * chooses to open this address.
	 */
	public interface AddressFieldBehavior {
		public void addressAction(String address);
	}

	/**
	 * Behavior that enables to display the address location on the Google maps
	 * web site.
	 * @author Medes-IMPS
	 */

	public static class DefaultAddressBehavior implements AddressFieldBehavior {

		private final static String GMAPS_TMPL = "http://maps.google.com?q=%ADDRESS%";

		@Override
		public void addressAction(String address) {
			String encodedAddress = address.replace("\n", " ");
			Window.open(GMAPS_TMPL.replace("%ADDRESS%", encodedAddress),
					"Google maps", "");
		}
	}

}
