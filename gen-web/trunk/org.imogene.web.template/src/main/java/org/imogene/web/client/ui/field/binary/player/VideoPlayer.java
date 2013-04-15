package org.imogene.web.client.ui.field.binary.player;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;



public class VideoPlayer extends Composite {

	private HTML content;
	private int thisHeight;
	private int thisWidth;
	

	public VideoPlayer(String title, int height, int width, String videoUrl) {
		thisHeight = height;
		thisWidth = width;
		content = new HTML(createHtmlEmbedded(title, height, width, videoUrl));
		initWidget(content);
		properties();
	}
	

	private void properties() {
		content.setSize(thisWidth + "px", thisHeight + "px");
	}

	private String createHtmlEmbedded(String title, int height, int width,
			String videoUrl) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<object type=\"application/x-shockwave-flash\" ");
		buffer.append("data=\"" + GWT.getModuleBaseURL()
				+ "flash/player_flv.swf\" height=\"" + height + "\" width=\""
				+ width + "\">");
		buffer.append("\n<param name=\"movie\" value=\""
				+ GWT.getModuleBaseURL() + "flash/player_flv.swf\">");
		buffer.append("<param name=\"wmode\" value=\"transparent\">");
		buffer.append("<param name=\"allowFullScreen\" value=\"true\">");
		buffer.append("<param name=\"FlashVars\" value=\"title="
				+ title
				+ "&amp;"
				+ "margin=1&amp;showvolume=1&amp;autoplay=1&amp;showtime=1&amp;showfullscreen=1&amp;"
				+ "buttonovercolor=ff9900&amp;slidercolor1=cccccc&amp;slidercolor2=999999&amp;"
				+ "sliderovercolor=0066cc&amp;flv=" + videoUrl
				+ "&amp;thisWidth=" + width + "&amp;thisHeight=" + height
				+ "\">");
		buffer.append("</object>");
		return buffer.toString();
	}

	/**
	 * @return the video player thisHeight
	 */
	public int getHeight() {
		return thisHeight;
	}

	/**
	 * @return the video player thisWidth
	 */
	public int getWidth() {
		return thisWidth;
	}

}
