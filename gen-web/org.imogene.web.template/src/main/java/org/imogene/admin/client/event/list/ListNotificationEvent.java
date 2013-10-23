package org.imogene.admin.client.event.list;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired in order to display the list of Notification form entries
 * @author MEDES-IMPS
 */
public class ListNotificationEvent
		extends
			GwtEvent<ListNotificationEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	private String searchText = null;

	public interface Handler extends EventHandler {
		void listNotification();
		void listNotification(String searchText);
	}

	public ListNotificationEvent() {
	}

	public ListNotificationEvent(String searchText) {
		this.searchText = searchText;
	}

	@Override
	protected void dispatch(Handler handler) {
		if (searchText == null)
			handler.listNotification();
		else
			handler.listNotification(searchText);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}
}
