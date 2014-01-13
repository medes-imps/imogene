package org.imogene.admin.client.event.create;

import org.imogene.admin.client.event.list.ListProfileEvent;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired in order to display a Profile form in creation mode
 * 
 * @author MEDES-IMPS
 */
public class CreateProfileEvent extends GwtEvent<CreateProfileEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	private final GwtEvent<?> closeEvent;

	public interface Handler extends EventHandler {
		void createNewProfile(GwtEvent<?> closeEvent);
	}

	public CreateProfileEvent() {
		this(new ListProfileEvent());
	}

	public CreateProfileEvent(GwtEvent<?> closeEvent) {
		this.closeEvent = closeEvent;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.createNewProfile(closeEvent);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

}
