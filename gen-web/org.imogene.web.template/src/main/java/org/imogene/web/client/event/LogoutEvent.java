package org.imogene.web.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class LogoutEvent extends GwtEvent<LogoutEvent.Handler> {
	
	public static final Type<Handler> TYPE = new Type<Handler>();

	/**
	 * Handles {@link CreateCultureRequestEvent}.
	 */
	public interface Handler extends EventHandler {
		void onLogout();
	}

	public LogoutEvent() {
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.onLogout();
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

}
