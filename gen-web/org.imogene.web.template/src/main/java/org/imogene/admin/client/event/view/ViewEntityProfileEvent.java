package org.imogene.admin.client.event.view;

import org.imogene.admin.client.event.list.ListEntityProfileEvent;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired in order to display a EntityProfile form in view mode
 * 
 * @author MEDES-IMPS
 */
public class ViewEntityProfileEvent extends GwtEvent<ViewEntityProfileEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	public interface Handler extends EventHandler {
		void viewEntityProfile(String entityId, GwtEvent<?> closeEvent);
	}

	private final String entityId;

	private final GwtEvent<?> closeEvent;

	public ViewEntityProfileEvent(String entityId) {
		this(entityId, new ListEntityProfileEvent());
	}

	public ViewEntityProfileEvent(String entityId, GwtEvent<?> closeEvent) {
		this.entityId = entityId;
		this.closeEvent = closeEvent;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.viewEntityProfile(entityId, closeEvent);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}
}
