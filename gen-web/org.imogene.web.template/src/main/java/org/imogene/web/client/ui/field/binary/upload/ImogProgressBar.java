package org.imogene.web.client.ui.field.binary.upload;

import gwtupload.client.HasProgress;
import gwtupload.client.IUploader;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Progress bar used in the Upload widget
 * 
 * @author Medes-IMPS
 */
public class ImogProgressBar extends FlowPanel implements HasProgress {

	SimplePanel statusBar = new SimplePanel();
	Label statusMsg = new Label();

	public ImogProgressBar() {
		this.setWidth("100px");
		this.setStyleName("prgbar-back");
		this.add(statusBar);
		this.add(statusMsg);
		statusBar.setStyleName("prgbar-done");
		statusBar.setWidth("0px");
		statusMsg.setStyleName("prgbar-msg");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gwtupload.client.HasProgress#setProgress(int, int)
	 */
	public void setProgress(long done, long total) {
		if (statusBar == null)
			return;
		int percent = IUploader.Utils.getPercent(done, total);
		statusBar.setWidth(percent + "px");
		statusMsg.setText(percent + "%");
	}

}
