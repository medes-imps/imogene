package org.imogene.admin.client.event.create;

import org.imogene.admin.client.event.list.ListCardEntityEvent;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired in order to display a SynchronizableEntity form in creation mode
 * 
 * @author MEDES-IMPS
 */
public class CreateCardEntityEvent extends GwtEvent<CreateCardEntityEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	private final GwtEvent<?> closeEvent;

	public interface Handler extends EventHandler {
		void createNewCardEntity(GwtEvent<?> closeEvent);
	}

	public CreateCardEntityEvent() {
		this(new ListCardEntityEvent());
	}

	public CreateCardEntityEvent(GwtEvent<?> closeEvent) {
		this.closeEvent = closeEvent;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.createNewCardEntity(closeEvent);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

}
