package org.imogene.admin.client.event.create;

import org.imogene.admin.client.event.list.ListNotificationEvent;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired in order to display a Notification form in creation mode
 * @author MEDES-IMPS
 */
public class CreateNotificationEvent
		extends
			GwtEvent<CreateNotificationEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	private final GwtEvent<?> closeEvent;

	public interface Handler extends EventHandler {
		void createNewNotification(GwtEvent<?> closeEvent);
	}
	
	public CreateNotificationEvent() {
		this(new ListNotificationEvent());
	}

	public CreateNotificationEvent(GwtEvent<?> closeEvent) {
		this.closeEvent = closeEvent;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.createNewNotification(closeEvent);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

}
