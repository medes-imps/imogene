package org.imogene.admin.client.event.view;

import org.imogene.admin.client.event.list.ListNotificationEvent;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired in order to display a Notification form in view mode
 * @author MEDES-IMPS
 */
public class ViewNotificationEvent
		extends
			GwtEvent<ViewNotificationEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	public interface Handler extends EventHandler {
		void viewNotification(String entityId, GwtEvent<?> closeEvent);
	}

	private final String entityId;
	
	private final GwtEvent<?> closeEvent;

	public ViewNotificationEvent(String entityId) {
		this(entityId, new ListNotificationEvent());
	}
	
	public ViewNotificationEvent(String entityId, GwtEvent<?> closeEvent) {
		this.entityId = entityId;
		this.closeEvent = closeEvent;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.viewNotification(entityId, closeEvent);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}
}
