package org.imogene.admin.client.event.view;

import org.imogene.admin.client.event.list.ListProfileEvent;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired in order to display a Profile form in view mode
 * 
 * @author MEDES-IMPS
 */
public class ViewProfileEvent extends GwtEvent<ViewProfileEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	public interface Handler extends EventHandler {
		void viewProfile(String entityId, GwtEvent<?> closeEvent);
	}

	private final String entityId;

	private final GwtEvent<?> closeEvent;

	public ViewProfileEvent(String entityId) {
		this(entityId, new ListProfileEvent());
	}

	public ViewProfileEvent(String entityId, GwtEvent<?> closeEvent) {
		this.entityId = entityId;
		this.closeEvent = closeEvent;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.viewProfile(entityId, closeEvent);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}
}
