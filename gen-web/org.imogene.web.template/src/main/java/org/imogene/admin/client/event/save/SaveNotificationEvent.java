package org.imogene.admin.client.event.save;

import org.imogene.admin.shared.proxy.NotificationProxy;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired after that a Notification form has been saved
 * @author MEDES-IMPS
 */
public class SaveNotificationEvent
		extends
			GwtEvent<SaveNotificationEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	public interface Handler extends EventHandler {
		void saveNotification(NotificationProxy value);
		void saveNotification(NotificationProxy value, String initField);
	}

	private final NotificationProxy value;
	/* the relation field that initiated the creation or update of the value */
	private final String initField;

	public SaveNotificationEvent(NotificationProxy value) {
		this(value, null);
	}

	public SaveNotificationEvent(NotificationProxy value, String initField) {
		this.value = value;
		this.initField = initField;
	}

	@Override
	protected void dispatch(Handler handler) {
		if (initField == null)
			handler.saveNotification(value);
		else
			handler.saveNotification(value, initField);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}
}
