package org.imogene.web.client.ui.field.binary.upload;

import gwtupload.client.HasProgress;
import gwtupload.client.IUploadStatus;
import gwtupload.client.IUploader;

import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author Medes-IMPS
 */
public class ImogUploadStatus implements IUploadStatus {

	/**
	 * A basic progress bar implementation used when the user doesn't provide
	 * anyone.
	 */
	public static class BasicProgressBar extends FlowPanel implements HasProgress {

		SimplePanel statusBar = new SimplePanel();
		Label statusMsg = new Label();

		public BasicProgressBar() {
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

	private IUploadStatus.Status status = Status.UNINITIALIZED;
	private Widget prg = null;
	private boolean hasCancelActions = false;

	private UploadStatusConstants i18nStrs = GWT.create(UploadStatusConstants.class);

	private Set<CancelBehavior> cancelCfg = DEFAULT_CANCEL_CFG;

	protected Panel panel = new VerticalPanel();

	protected Panel topPanel = new HorizontalPanel();

	protected Label fileNameLabel = new Label();

	protected Label statusLabel = new Label();

	protected Label cancelLabel = new Label(" ");

	/**
	 * Default Constructor
	 */
	public ImogUploadStatus() {
		topPanel.add(cancelLabel);
		topPanel.add(fileNameLabel);
		panel.add(topPanel);
		panel.add(statusLabel);
		fileNameLabel.setStyleName("filename");
		statusLabel.setStyleName("status");
		cancelLabel.setStyleName("cancel");
		cancelLabel.setVisible(true);
	}

	/**
	 * Override the default progress widget with a customizable one
	 * 
	 * @param progress
	 */
	protected void setProgressWidget(Widget progress) {
		if (prg != null)
			panel.remove(prg);

		prg = progress;
		panel.add(prg);
		prg.setVisible(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gwtupload.client.IUploadStatus#getWidget()
	 */
	public Widget getWidget() {
		return panel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gwtupload.client.IUploadStatus#setProgress(int, int)
	 */
	public void setProgress(long done, long total) {
		long percent = total > 0 ? done * 100 / total : 0;
		setPercent(percent);
		if (prg != null) {
			if (prg instanceof HasProgress)
				((HasProgress) prg).setProgress(done, total);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gwtupload.client.IUploadStatus#setVisible(boolean)
	 */
	public void setVisible(boolean b) {
		panel.setVisible(b);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gwtupload.client.IUploadStatus#setFileName(java.lang.String)
	 */
	public void setFileName(String name) {
		fileNameLabel.setText(name);
	}

	/**
	 * Set the percent of the upload process. Override this method to update
	 * your customized progress widget.
	 * 
	 * @param percent
	 */
	public void setPercent(long percent) {
		setStatus(status);
	}

	/**
	 * Thougth to be overridable by the user when extending this.
	 * 
	 * @param showProgress
	 * @param message
	 */
	protected void updateStatusPanel(boolean showProgress, String message) {
		if (showProgress && prg == null)
			setProgressWidget(new BasicProgressBar());

		if (prg != null)
			prg.setVisible(showProgress);

		fileNameLabel.setVisible(prg instanceof BasicProgressBar || !showProgress);
		statusLabel.setVisible(!showProgress);

		statusLabel.setText(message);
		cancelLabel.setVisible(hasCancelActions && !cancelCfg.contains(CancelBehavior.DISABLED));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gwtupload.client.IUploadStatus#setStatus(int)
	 */
	public void setStatus(Status stat) {
		status = stat;
		String statusName = status.toString().toLowerCase();
		statusLabel.removeStyleDependentName(statusName);
		statusLabel.addStyleDependentName(statusName);
		switch (stat) {
		case QUEUED:
			updateStatusPanel(false, i18nStrs.uploadStatusQueued());
			break;
		case SUBMITING:
			updateStatusPanel(false, i18nStrs.uploadStatusSubmitting());
			break;
		case INPROGRESS:
			updateStatusPanel(true, i18nStrs.uploadStatusInProgress());
			if (!cancelCfg.contains(CancelBehavior.STOP_CURRENT))
				cancelLabel.setVisible(false);
			break;
		case SUCCESS:
			updateStatusPanel(false, i18nStrs.uploadStatusSuccess());
			if (!cancelCfg.contains(CancelBehavior.REMOVE_REMOTE))
				cancelLabel.setVisible(false);
			break;
		case CANCELING:
			updateStatusPanel(false, i18nStrs.uploadStatusCanceling());
			break;
		case CANCELED:
			updateStatusPanel(false, i18nStrs.uploadStatusCanceled());
			if (cancelCfg.contains(CancelBehavior.REMOVE_CANCELLED_FROM_LIST))
				this.setVisible(false);
			break;
		case ERROR:
			updateStatusPanel(false, i18nStrs.uploadStatusError());
			break;
		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gwtupload.client.IUploadStatus#setError(java.lang.String)
	 */
	public void setError(String msg) {
		setStatus(Status.ERROR);
		Window.alert(msg.replaceAll("\\\\n", "\\n"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gwtupload.client.IUploadStatus#newInstance()
	 */
	public IUploadStatus newInstance() {
		IUploadStatus ret = new ImogUploadStatus();
		ret.setCancelConfiguration(cancelCfg);
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seegwtupload.client.IUploadStatus#addCancelHandler(gwtupload.client.
	 * UploadCancelHandler)
	 */
	public HandlerRegistration addCancelHandler(final UploadCancelHandler handler) {
		hasCancelActions = true;
		return cancelLabel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				handler.onCancel();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gwtupload.client.IUploadStatus#setCancelConfiguration(int)
	 */
	public void setCancelConfiguration(Set<CancelBehavior> config) {
		cancelCfg = config;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gwtupload.client.IUploadStatus#setI18Constants(gwtupload.client.IUploadStatus
	 * .UploadConstants)
	 */
	public void setI18Constants(UploadStatusConstants strs) {
		assert strs != null;
		i18nStrs = strs;
	}

	public Status getStatus() {
		return status;
	}

	@Override
	public void setStatusChangedHandler(UploadStatusChangedHandler handler) {
		// TODO Auto-generated method stub

	}

}
