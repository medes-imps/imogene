package org.imogene.web.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class IsTableFilteredEvent extends GwtEvent<IsTableFilteredEvent.Handler> {
	
	public static final Type<Handler> TYPE = new Type<Handler>();
	
	private final Boolean isFiltered;
	private final String filterMessage;

	/**
	 * Handles {@link CreateCultureRequestEvent}.
	 */
	public interface Handler extends EventHandler {
		void noticeFilteringChange(Boolean isFiltered, String filterMessage);
	}

	public IsTableFilteredEvent(boolean isFiltered, String filterMessage) {
		this.isFiltered = isFiltered;
		this.filterMessage = filterMessage;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.noticeFilteringChange(isFiltered, filterMessage);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

}
