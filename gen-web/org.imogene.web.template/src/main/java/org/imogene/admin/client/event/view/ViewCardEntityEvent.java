package org.imogene.admin.client.event.view;

import org.imogene.admin.client.event.list.ListCardEntityEvent;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired in order to display a SynchronizableEntity form in view mode
 * 
 * @author MEDES-IMPS
 */
public class ViewCardEntityEvent extends GwtEvent<ViewCardEntityEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	public interface Handler extends EventHandler {
		void viewCardEntity(String entityId, GwtEvent<?> closeEvent);
	}

	private final String entityId;

	private final GwtEvent<?> closeEvent;

	public ViewCardEntityEvent(String entityId) {
		this(entityId, new ListCardEntityEvent());
	}

	public ViewCardEntityEvent(String entityId, GwtEvent<?> closeEvent) {
		this.entityId = entityId;
		this.closeEvent = closeEvent;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.viewCardEntity(entityId, closeEvent);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}
}
