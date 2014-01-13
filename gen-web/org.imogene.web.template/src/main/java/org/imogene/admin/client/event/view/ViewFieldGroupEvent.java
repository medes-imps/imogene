package org.imogene.admin.client.event.view;

import org.imogene.admin.client.event.list.ListFieldGroupEvent;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired in order to display a FieldGroup form in view mode
 * 
 * @author MEDES-IMPS
 */
public class ViewFieldGroupEvent extends GwtEvent<ViewFieldGroupEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	public interface Handler extends EventHandler {
		void viewFieldGroup(String entityId, GwtEvent<?> closeEvent);
	}

	private final String entityId;

	private final GwtEvent<?> closeEvent;

	public ViewFieldGroupEvent(String entityId) {
		this(entityId, new ListFieldGroupEvent());
	}

	public ViewFieldGroupEvent(String entityId, GwtEvent<?> closeEvent) {
		this.entityId = entityId;
		this.closeEvent = closeEvent;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.viewFieldGroup(entityId, closeEvent);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}
}
