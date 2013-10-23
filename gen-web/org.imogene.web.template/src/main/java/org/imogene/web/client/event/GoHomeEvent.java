package org.imogene.web.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class GoHomeEvent extends GwtEvent<GoHomeEvent.Handler> {
	
	public static final Type<Handler> TYPE = new Type<Handler>();

	/**
	 * Handles {@link CreateCultureRequestEvent}.
	 */
	public interface Handler extends EventHandler {
		void goHome();
	}

	public GoHomeEvent() {
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.goHome();
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

}
