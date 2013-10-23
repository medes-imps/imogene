package org.imogene.web.client.event;


import org.imogene.web.client.ui.widget.SimpleMenuItem;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Begin editing a person.
 * 
 * TODO: Make this an Activity.
 */
public class SelectMenuItemEvent extends GwtEvent<SelectMenuItemEvent.Handler> {
	public static final Type<Handler> TYPE = new Type<Handler>();

	/**
	 * Handles {@link SelectMenuItemEvent}.
	 */
	public interface Handler extends EventHandler {
		void selectMenuItem(SimpleMenuItem menuItem);
	}

	private final SimpleMenuItem value;

	public SelectMenuItemEvent(SimpleMenuItem value) {
		this.value = value;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.selectMenuItem(value);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}
}
