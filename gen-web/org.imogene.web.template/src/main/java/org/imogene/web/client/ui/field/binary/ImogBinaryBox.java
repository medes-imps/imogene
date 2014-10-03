package org.imogene.web.client.ui.field.binary;

import java.util.List;

import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.field.ImogField;
import org.imogene.web.client.ui.field.ImogFieldAbstract;
import org.imogene.web.client.ui.field.binary.upload.ImogBinaryUploader;
import org.imogene.web.client.ui.field.error.ImogErrorLabel;
import org.imogene.web.client.util.BinaryTools;
import org.imogene.web.shared.ImogRequestFactory;
import org.imogene.web.shared.proxy.BinaryDescProxy;
import org.imogene.web.shared.proxy.BinaryProxy;
import org.imogene.web.shared.request.BinaryRequest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.HasEditorErrors;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

/**
 * Composite to manage the uploading and downloading of Binary fields
 * @author Medes-IMPS
 */
public class ImogBinaryBox extends Composite implements ImogUploader,
		ImogField<BinaryProxy>, LeafValueEditor<BinaryProxy>, HasEditorErrors<BinaryProxy> {

	private static final Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, ImogBinaryBox> {
	}

	ImogRequestFactory requestFactory;

	/* status */
	private BinaryProxy thisValue;
	private boolean edited = false;

	/* widget */
	@UiField
	ImogErrorLabel errorLabel;
	@UiField
	@Ignore
	ImogFieldAbstract fieldBox;
	@UiField
	HorizontalPanel main;
	@UiField(provided=true)
	Image fileIcon;

	/* consultation widgets */
	@UiField
	VerticalPanel infoPanel;
	@UiField
	Label nameLabel;
	@UiField
	Label sizeLabel;
	@UiField
	HTML downloadLink;

	/* edition widgets */
	@UiField
	VerticalPanel editPanel;
	@UiField(provided=true)
	ImogBinaryUploader uploader;

	public ImogBinaryBox(ImogRequestFactory requestFactory) {
		this.requestFactory = requestFactory;
		fileIcon = new Image(GWT.getModuleBaseURL()+ "images/file_icon_32.png");
		uploader = new ImogBinaryUploader(requestFactory);
		initWidget(uiBinder.createAndBindUi(this));

		nameLabel.setText(BaseNLS.constants().binary_nofile());
	}


	@Override
	public BinaryProxy getValue() {
		if (uploader.getEntity() != null) {
			thisValue = uploader.getEntity();
		}
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
	public void setValue(BinaryProxy value) {
		
		if(value!=null && value.getId()!=null){
			downloadLink.setHTML(BinaryTools.DOWNLOAD_TMPL.replace("%PARAM_ID%",value.getId()));
			if(value!=thisValue)
				setBinaryMetadata(value.getId());
		}	
		thisValue = value;	
	}

	@Override
	public void setEdited(boolean editable) {
		if (editable) {
			infoPanel.setVisible(false);
			editPanel.setVisible(true);
//			main.remove(infoPanel);
//			main.add(editPanel);
		}
		else {
			infoPanel.setVisible(true);
			editPanel.setVisible(false);
//			main.remove(editPanel);
//			main.add(infoPanel);
		}
		edited = editable;
	}

	@Override
	public boolean isEdited() {
		return edited;
	}

	@Override
	public boolean isUploading() {
		return uploader.isUploading();
	}

	/**
	 * Display the binary meta-data
	 */
	public void setBinaryMetadata(String value) {

		BinaryRequest binaryRequest = requestFactory.binaryRequest();
		Request<BinaryDescProxy> binaryDescRequest = binaryRequest.getBinaryDesc(value);
		
		binaryDescRequest.fire(new Receiver<BinaryDescProxy>() {
			@Override
			public void onSuccess(BinaryDescProxy response) {
				nameLabel.setText(response.getName());
				sizeLabel.setText(BinaryTools.getSizeAsString(response.getSize()));
			}

			@Override
			public void onFailure(ServerFailure error) {
				Window.alert("Error retrieving the Binary Description");
				super.onFailure(error);
			}
		});
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
	
	public void setBoxWidth(int width) {
	}
}
