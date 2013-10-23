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

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.allen_sauer.gwt.voices.client.handler.PlaybackCompleteEvent;
import com.allen_sauer.gwt.voices.client.handler.SoundHandler;
import com.allen_sauer.gwt.voices.client.handler.SoundLoadStateChangeEvent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.HasEditorErrors;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
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
 * Widget that enables to display and edit an audio file. This widget proposes
 * to download the audio file or to play it in an embedded flash player
 * @author Medes-IMPS
 */
public class ImogAudioBox extends Composite implements ImogUploader,
		ImogField<BinaryProxy>, LeafValueEditor<BinaryProxy>, HasEditorErrors<BinaryProxy>, SoundHandler {

	private static final Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, ImogAudioBox> {
	}

	private static String PLAYER_PLAY = GWT.getModuleBaseURL()
			+ "images/sound_play_32.png";
	private static String PLAYER_STOP = GWT.getModuleBaseURL()
			+ "images/sound_stop_32.png";
	private static String PLAYER_DISABLED = GWT.getModuleBaseURL()
			+ "images/sound_disable_32.png";

	ImogRequestFactory requestFactory;

	/* status */
	private BinaryProxy thisValue;
	private boolean edited = false;

	private boolean playing = false;
	private SoundController sndController;
	private Sound sound;

	/* widget */
	@UiField
	ImogErrorLabel errorLabel;
	@UiField
	@Ignore
	ImogFieldAbstract fieldBox;
	@UiField
	HorizontalPanel main;
	@UiField(provided = true)
	Image playerStatus;

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
	@UiField(provided = true)
	ImogBinaryUploader uploader;

	public ImogAudioBox(ImogRequestFactory requestFactory) {
		this.requestFactory = requestFactory;
		playerStatus = new Image(PLAYER_DISABLED);
		uploader = new ImogBinaryUploader(requestFactory);
		initWidget(uiBinder.createAndBindUi(this));

		// thumbnail.setUrl(GWT.getModuleBaseURL() + "images/no_photo.png");
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
			/* sound controller */
			playerStatus.setUrl(PLAYER_PLAY);
			playerStatus.setStylePrimaryName("imogene-ImageLink");
			sndController = new SoundController();
			sound = sndController.createSound(Sound.MIME_TYPE_AUDIO_MPEG,
		        GWT.getHostPageBaseURL()+"getbinary?mp3Id="+value.getId());			
			sound.addEventHandler(this);
			/* download controller */
			downloadLink.setHTML(BinaryTools.DOWNLOAD_TMPL.replace("%PARAM_ID%", value.getId()));
			if(thisValue!=value)
				setBinaryMetadata(value.getId());
		}	
		thisValue = value;
	}

	@Override
	public void setEdited(boolean editable) {
		if (editable) {
			infoPanel.setVisible(false);
			editPanel.setVisible(true);
			// main.remove(infoPanel);
			// main.add(editPanel);
		} else {
			infoPanel.setVisible(true);
			editPanel.setVisible(false);
			// main.remove(editPanel);
			// main.add(infoPanel);
		}
		edited = editable;
		setPlayerStatus();	
	}

	@UiHandler("playerStatus")
	void handleClick(ClickEvent e) {
		if (sound != null && !playing) {
			sound.play();
			playing = true;
			playerStatus.setUrl(PLAYER_STOP);
			GWT.log("Start playing the sound", null);
			return;
		}
		if (sound != null && playing) {
			sound.stop();
			playing = false;
			playerStatus.setUrl(PLAYER_PLAY);
			GWT.log("Stop playing the sound", null);
			return;
		}
	}
	
	@Override
	public void onPlaybackComplete(PlaybackCompleteEvent event) {		
		playing = false;
		playerStatus.setUrl(PLAYER_PLAY);
	}

	@Override
	public void onSoundLoadStateChange(SoundLoadStateChangeEvent event) {		
		
	}

	@Override
	public boolean isEdited() {
		return edited;
	}

	/**
	 * Set the player status (icon and behavior)
	 */
	private void setPlayerStatus(){
		if(edited){
			playerStatus.setUrl(PLAYER_DISABLED);
		}else{
			if(thisValue!=null)
				playerStatus.setUrl(PLAYER_PLAY);
			else
				playerStatus.setUrl(PLAYER_DISABLED);
		}
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
				sizeLabel.setText(BinaryTools.getSizeAsString(response
						.getSize()));
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

}
