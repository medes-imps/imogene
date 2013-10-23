package org.imogene.web.client.ui.panel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class MessagePanel extends Composite {

	private static MessagePanelUiBinder uiBinder = GWT.create(MessagePanelUiBinder.class);

	private Map<String, String> messages = new HashMap<String, String>();

	private String currentMessageKey;

	private int currentIndex = 0;

	private Timer timer;

	interface MessagePanelUiBinder extends UiBinder<Widget, MessagePanel> {
	}

	public MessagePanel() {
		initWidget(uiBinder.createAndBindUi(this));
		initPoperties();
	}

	private void initPoperties() {
		deleteMessage.setUrl(GWT.getModuleBaseURL() + "images/message_delete.png");
		messagesPanel.setVisible(false);
	}

	@UiField
	HorizontalPanel messagesPanel;

	@UiField
	Image deleteMessage;

	@UiField
	Label displayedMessage;

	@UiField
	Label messageCount;

	@UiHandler("deleteMessage")
	void onClick(ClickEvent e) {
		removeMessage(currentMessageKey);
	}

	public void addMessage(String key, String message) {
		synchronized (messages) {
			messages.put(key, message);
		}
		if (messages.size() > 0)
			messagesPanel.setVisible(true);
	}

	public void removeMessage(String id) {
		synchronized (messages) {
			messages.remove(id);
		}
		if (currentMessageKey != null && currentMessageKey.equals(id))
			displayMessage("");
		if (messages.size() < 1)
			messagesPanel.setVisible(false);
	}

	@Override
	protected void onAttach() {
		super.onAttach();
		timer = new MessageDisplayer();
		timer.scheduleRepeating(5000);
		exportNativeMethod();
		// testNativeMethod();
	}

	@Override
	protected void onDetach() {
		super.onDetach();
		if (timer != null)
			timer.cancel();
	}

	private void displayMessage(String text) {
		boolean empty = "".equals(text);
		if (!empty)
			messageCount.setText("(" + (currentIndex + 1) + "/" + messages.size() + ")");
		else
			messageCount.setText("");
		displayedMessage.setText(text);
		deleteMessage.setVisible(!empty);
	}

	/**
	 * 
	 * @author yann
	 * 
	 */
	private class MessageDisplayer extends Timer {

		@Override
		public void run() {
			synchronized (messages) {
				List<String> keys = new ArrayList<String>(messages.keySet());

				if (currentIndex >= keys.size() && keys.size() > 0)
					currentIndex = currentIndex - 1;

				if (currentIndex < keys.size()) {
					currentMessageKey = keys.get(currentIndex);
					displayMessage(messages.get(currentMessageKey));
					currentIndex = (currentIndex + 1) % messages.size();
				}
			}
		}
	}

	public native void exportNativeMethod() /*-{  
											$messagePanelInstance = this 		
											$wnd.imogAddMessage = function(id, message) {       
											$messagePanelInstance.@org.imogene.web.client.ui.panel.MessagePanel::addMessage(Ljava/lang/String;Ljava/lang/String;)(id, message);        	
											}
											
											$wnd.imogRemoveMessage = function(id) {       
											$messagePanelInstance.@org.imogene.web.client.ui.panel.MessagePanel::removeMessage(Ljava/lang/String;)(id);
											}
											}-*/;

	public native void testNativeMethod() /*-{
											$wnd.imogAddMessage("test1", "test un");
											$wnd.imogAddMessage("test2", "test deux");
											}-*/;

}
