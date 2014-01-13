package org.imogene.web.client.ui.field.binary;

import java.util.List;

import org.imogene.web.client.ui.field.ImogField;
import org.imogene.web.client.ui.field.ImogFieldAbstract;
import org.imogene.web.client.ui.field.error.ImogErrorLabel;
import org.imogene.web.shared.ImogRequestFactory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.HasEditorErrors;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Composite to manage the display of BarCode fields
 * @author Medes-IMPS
 */
public class ImogQRCodeBox extends Composite implements ImogField<String>, LeafValueEditor<String>, HasEditorErrors<String> {

	private static final Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, ImogQRCodeBox> {
	}

	ImogRequestFactory requestFactory;

	/* status */
	private String thisValue;
	private boolean edited = false;

	/* widget */
	@UiField
	ImogErrorLabel errorLabel;
	@UiField
	@Ignore
	ImogFieldAbstract fieldBox;
	@UiField
	HorizontalPanel main;
	@UiField
	Image barcode;



	public ImogQRCodeBox(ImogRequestFactory requestFactory) {
		this.requestFactory = requestFactory;
		initWidget(uiBinder.createAndBindUi(this));
	}


	@Override
	public String getValue() {
		return thisValue;
	}

	@Override
	public void setLabel(String label) {
		fieldBox.setLabel(label);
	}
	
	public void setLabel(String label, HorizontalAlignmentConstant alignment) {
		fieldBox.setLabel(label, alignment);
	}

	@Override
	public void setValue(String value) {
		if (value != null && !"".equals(value)) {
			barcode.setUrl(GWT.getHostPageBaseURL()+"getbinary?barcodeId="+value);
		}
		thisValue = value;
	}

	@Override
	public void setEdited(boolean editable) {
	}

	@Override
	public boolean isEdited() {
		return edited;
	}
	
	/**
	 * Defines that the field shall notify value changes
	 * @param eventBus the event bus that will be used to fire the value change events
	 */
	public void notifyChanges(EventBus eventBus) {
	}

	@Override
	public void showErrors(List<EditorError> errors) {
		errorLabel.showErrors(errors);
	}
	
	public void setLabelWidth(String width) {
		fieldBox.setLabelWidth(width);
	}

}
