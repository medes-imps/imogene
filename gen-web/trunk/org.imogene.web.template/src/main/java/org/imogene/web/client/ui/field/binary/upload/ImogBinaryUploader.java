package org.imogene.web.client.ui.field.binary.upload;

import gwtupload.client.BaseUploadStatus;
import gwtupload.client.IUploadStatus;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploadStatus.UploadCancelHandler;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.IUploader.OnStartUploaderHandler;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.PreloadedImage;
import gwtupload.client.PreloadedImage.OnLoadPreloadedImageHandler;
import gwtupload.client.Uploader;

import org.imogene.web.shared.ImogRequestFactory;
import org.imogene.web.shared.proxy.BinaryProxy;
import org.imogene.web.shared.request.BinaryRequest;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

/**
 * Imogene binary uploader widget. this class uses the GWTupload project.
 * @author Medes-IMPS
 */

public class ImogBinaryUploader extends Composite implements
		UploadCancelHandler, OnFinishUploaderHandler, OnStartUploaderHandler {
	
	private ImogRequestFactory requestFactory;

	private VerticalPanel main;
	private Uploader embedded;
	private IUploadStatus statusBar;
	private BinaryProxy value = null;
	private boolean uploading = false;
	private Image thumbnail = null;

	
	public ImogBinaryUploader(Image thumbnail, ImogRequestFactory requestFactory) {
		this.thumbnail = thumbnail;
		this.requestFactory = requestFactory;
		main = new VerticalPanel();
		createInstance();
		main.add(embedded);
		initWidget(main);
		properties();
	}
	
	public ImogBinaryUploader(ImogRequestFactory requestFactory) {
		this.requestFactory = requestFactory;
		main = new VerticalPanel();
		createInstance();
		main.add(embedded);
		initWidget(main);
		properties();
	}

	private void properties() {
		embedded.setFileInputSize(20);
		embedded.setAutoSubmit(true);
	}

	/**
	 * Get the id of the binary entity associated with the last binary file.
	 * @return the entity id
	 */
	public BinaryProxy getEntity() {
		return value;
	}

	@Override
	public void onCancel() {
		value = null;
		uploading = false;
		if (embedded.getStatus().equals(Status.SUCCESS)) {
			setNew();
		}
	}

	@Override
	public void onStart(IUploader uploader) {
		uploading = true;
	}

	@Override
	public void onFinish(IUploader uploader) {

		if (uploader.getStatus().equals(Status.SUCCESS)) {
			
			UploadedInfo info = uploader.getServerInfo();
			if (info != null && info.message != null && !info.message.equals("noid")) {
				String entityId = info.message;
				
				BinaryRequest binaryRequest = requestFactory.binaryRequest();
				Request<BinaryProxy> binaryFileRequest = binaryRequest.getBinary(entityId);
				
				binaryFileRequest.fire(new Receiver<BinaryProxy>() {
					@Override
					public void onSuccess(BinaryProxy response) {
						value = response;
						uploading = false;
					}

					@Override
					public void onFailure(ServerFailure error) {
						Window.alert("Error retrieving the Binary Description");
						super.onFailure(error);
					}
				});
				
				
				
				if(thumbnail!=null && info.ctype.contains("image")) {
					// Attach thumbnail
					OnLoadPreloadedImageHandler showImage = new OnLoadPreloadedImageHandler() {
						public void onLoad(PreloadedImage image) {
							thumbnail.setUrl(image.getUrl());
						}
					};
					new PreloadedImage("servlet.gupld?show=" + entityId, showImage);
				}
			}
		}
		if (uploader.getStatus().equals(Status.CANCELED)) {
			setNew();
		}
	}

	/**
	 * reset the uploader
	 */
	public void setNew() {
		main.remove(embedded);
		createInstance();
		main.add(embedded);
		properties();
	}

	/**
	 * @return true if the uploader is uploading, false otherwise.
	 */
	public boolean isUploading() {
		return uploading;
	}

	/**
	 * Create new instance of this uploader
	 */
	private void createInstance() {
		embedded = null;
		statusBar = null;
		embedded = new Uploader();
		embedded.addOnFinishUploadHandler(this);
		embedded.addOnStartUploadHandler(this);
		statusBar = new BaseUploadStatus();
		statusBar.addCancelHandler(this);
		embedded.setStatusWidget(statusBar);
	}
}
