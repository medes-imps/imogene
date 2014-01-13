package org.imogene.web.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class SelectionChangedInTableEvent extends GwtEvent<SelectionChangedInTableEvent.Handler> {
	
	public static final Type<Handler> TYPE = new Type<Handler>();
	
	private final int selectedItems;

	/**
	 * Handles {@link CreateCultureRequestEvent}.
	 */
	public interface Handler extends EventHandler {
		void noticeSelectionChange(int selectedItems);
	}

	public SelectionChangedInTableEvent(int selectedItems) {
		this.selectedItems = selectedItems;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.noticeSelectionChange(selectedItems);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

}
