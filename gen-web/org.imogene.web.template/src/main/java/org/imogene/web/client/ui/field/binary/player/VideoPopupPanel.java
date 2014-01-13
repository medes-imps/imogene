package org.imogene.web.client.ui.field.binary.player;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;

public class VideoPopupPanel extends PopupPanel implements ClickHandler {

	
	private VideoPlayer videoPlayer;	
	private HorizontalPanel main;
	private Image closeImage;
	
	
	public VideoPopupPanel(VideoPlayer player){
		super();
		if(player==null){
			throw new RuntimeException("The video player can't be 'null'");
		}
		videoPlayer = player;
		layout();
	}
	
	private void layout(){
		main = new HorizontalPanel();
		closeImage = new Image(GWT.getModuleBaseURL()+"images/close_video_32.png");
		main.add(videoPlayer);
		main.add(closeImage);
		properties();
		setWidget(main);		
	}
	
	private void properties(){		
		setStylePrimaryName("imogene-VideoPopupPanel");
		setPopupPosition(Window.getClientWidth()/2-(videoPlayer.getWidth()/2), Window.getClientHeight()/2-(videoPlayer.getHeight()/2));
		main.setCellWidth(closeImage, "32px");
		main.setCellWidth(videoPlayer, videoPlayer.getWidth()+"px");
		main.setCellHeight(videoPlayer, videoPlayer.getHeight()+"px");
		main.setCellVerticalAlignment(closeImage, HorizontalPanel.ALIGN_TOP);
		closeImage.setStylePrimaryName("imogene-ImageLink");		
		closeImage.addClickHandler(this);		
	}

	@Override
	public void onClick(ClickEvent event) {
		if(event.getSource().equals(closeImage)){
			hide();
		}
	}			
	
}
