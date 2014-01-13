package org.imogene.admin.client.event.create;

import org.imogene.admin.client.event.list.ListEntityProfileEvent;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired in order to display a EntityProfile form in creation mode
 * 
 * @author MEDES-IMPS
 */
public class CreateEntityProfileEvent extends GwtEvent<CreateEntityProfileEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	private final GwtEvent<?> closeEvent;

	public interface Handler extends EventHandler {
		void createNewEntityProfile(GwtEvent<?> closeEvent);
	}

	public CreateEntityProfileEvent() {
		this(new ListEntityProfileEvent());
	}

	public CreateEntityProfileEvent(GwtEvent<?> closeEvent) {
		this.closeEvent = closeEvent;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.createNewEntityProfile(closeEvent);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

}
