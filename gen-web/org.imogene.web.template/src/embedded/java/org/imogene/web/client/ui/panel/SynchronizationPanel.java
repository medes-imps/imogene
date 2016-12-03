package org.imogene.web.client.ui.panel;

import java.util.Date;

import org.imogene.web.client.i18n.SyncConstants;
import org.imogene.web.client.ui.field.group.FieldGroupPanel;
import org.imogene.web.server.sync.SyncStatus;
import org.imogene.web.shared.SynchronizationRequestFactory;
import org.imogene.web.shared.proxy.SyncStatusProxy;
import org.imogene.web.shared.request.SyncStatusRequest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;

public class SynchronizationPanel extends Composite {

	interface Binder extends UiBinder<Widget, SynchronizationPanel> {
	}

	private static final Binder BINDER = GWT.create(Binder.class);
	private static final SyncConstants SYNC_CONSTANTS = GWT.create(SyncConstants.class);

	private final SynchronizationRequestFactory factory = GWT.create(SynchronizationRequestFactory.class);
	private final DateTimeFormat pattern = DateTimeFormat.getFormat(SYNC_CONSTANTS.sync_date_format());

	@UiField
	Label dateTitle;

	@UiField
	Label dateValue;

	@UiField
	Label statusTitle;

	@UiField
	Image statusValue;

	@UiField
	PushButton resetButton;

	@UiField
	FieldGroupPanel syncFieldGroup;

	public SynchronizationPanel() {
		factory.initialize(new SimpleEventBus());
		initWidget(BINDER.createAndBindUi(this));

		syncFieldGroup.setGroupTitle(SYNC_CONSTANTS.sync_title());

		dateTitle.setText(SYNC_CONSTANTS.sync_date_title());
		statusTitle.setText(SYNC_CONSTANTS.sync_status_title());

		resetButton.setText(SYNC_CONSTANTS.sync_reset());
		resetButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (Window.confirm(SYNC_CONSTANTS.sync_areyousure())) {
					SyncStatusRequest request = factory.syncHistoryRequest();
					request.resetSyncHistory().fire(new Receiver<Void>() {
						public void onSuccess(Void response) {
							checkSyncHistory();
						}
					});
				}
			}
		});

		checkSyncHistory();

		Timer timer = new Timer() {
			public void run() {
				checkSyncHistory();
			}
		};
		timer.scheduleRepeating(5000);
	}

	private void checkSyncHistory() {
		SyncStatusRequest request = factory.syncHistoryRequest();
		request.getSyncStatus().fire(new Receiver<SyncStatusProxy>() {
			@Override
			public void onSuccess(SyncStatusProxy response) {
				updateUI(response);
			}
		});
	}

	private void updateUI(SyncStatusProxy status) {
		Date lastSyncTime = status.getLastSyncTime();
		if (lastSyncTime == null) {
			dateValue.setText(SYNC_CONSTANTS.sync_date_empty());
		} else {
			dateValue.setText(pattern.format(lastSyncTime));
		}
		switch (status.getCurrentStatus()) {
		case SyncStatus.STATUS_OK:
			statusValue.setUrl(GWT.getModuleBaseURL() + "images/sync_ok.png");
			resetButton.setVisible(false);
			break;
		case SyncStatus.STATUS_ERROR:
			statusValue.setUrl(GWT.getModuleBaseURL() + "images/sync_error.png");
			resetButton.setVisible(true);
			break;
		case SyncStatus.STATUS_RUNNING:
			statusValue.setUrl(GWT.getModuleBaseURL() + "images/sync_running.png");
			resetButton.setVisible(false);
			break;
		case SyncStatus.STATUS_UNKNOWN:
			statusValue.setUrl(GWT.getModuleBaseURL() + "images/sync_unknown.png");
			resetButton.setVisible(true);
			break;
		}
	}

}
