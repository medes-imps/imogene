package org.imogene.admin.client.event.view;

import org.imogene.admin.client.event.list.ListFieldGroupProfileEvent;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired in order to display a FieldGroupProfile form in view mode
 * 
 * @author MEDES-IMPS
 */
public class ViewFieldGroupProfileEvent extends GwtEvent<ViewFieldGroupProfileEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	public interface Handler extends EventHandler {
		void viewFieldGroupProfile(String entityId, GwtEvent<?> closeEvent);
	}

	private final String entityId;

	private final GwtEvent<?> closeEvent;

	public ViewFieldGroupProfileEvent(String entityId) {
		this(entityId, new ListFieldGroupProfileEvent());
	}

	public ViewFieldGroupProfileEvent(String entityId, GwtEvent<?> closeEvent) {
		this.entityId = entityId;
		this.closeEvent = closeEvent;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.viewFieldGroupProfile(entityId, closeEvent);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}
}
