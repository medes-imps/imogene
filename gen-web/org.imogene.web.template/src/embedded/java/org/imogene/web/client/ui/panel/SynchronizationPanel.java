package org.imogene.web.client.ui.panel;

import org.imogene.web.shared.SynchronizationRequestFactory;
import org.imogene.web.shared.proxy.SyncHistoryProxy;
import org.imogene.web.shared.request.SyncHistoryRequest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
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

	private final SynchronizationRequestFactory factory = GWT.create(SynchronizationRequestFactory.class);

	@UiField
	Image statusImage;

	@UiField
	Label statusLabel;

	@UiField
	PushButton resetButton;

	public SynchronizationPanel() {
		factory.initialize(new SimpleEventBus());
		initWidget(BINDER.createAndBindUi(this));

		resetButton.setText("Reset");
		resetButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (Window.confirm("Are you sure ?")) {
					SyncHistoryRequest request = factory.syncHistoryRequest();
					request.resetSyncHistory().fire(new Receiver<Void>() {
						public void onSuccess(Void response) {
							checkSyncHistory();
						}
					});
				}
			}
		});

		Timer timer = new Timer() {
			public void run() {
				checkSyncHistory();
			}
		};
		timer.scheduleRepeating(5000);
	}

	private void checkSyncHistory() {
		SyncHistoryRequest request = factory.syncHistoryRequest();
		request.loadLastSyncHistory().fire(new Receiver<SyncHistoryProxy>() {
			@Override
			public void onSuccess(SyncHistoryProxy response) {
				updateUI(response);
			}
		});
	}

	private void updateUI(SyncHistoryProxy history) {
		if (history != null) {
			statusLabel.setText(history.getTime().toGMTString());
		} else {
			statusLabel.setText("No synchronization");
		}
	}

}
