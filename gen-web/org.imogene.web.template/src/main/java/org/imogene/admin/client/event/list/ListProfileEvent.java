package org.imogene.admin.client.event.list;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired in order to display the list of Profile form entries
 * 
 * @author MEDES-IMPS
 */
public class ListProfileEvent extends GwtEvent<ListProfileEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	private String searchText = null;

	public interface Handler extends EventHandler {
		void listProfile();

		void listProfile(String searchText);
	}

	public ListProfileEvent() {
	}

	public ListProfileEvent(String searchText) {
		this.searchText = searchText;
	}

	@Override
	protected void dispatch(Handler handler) {
		if (searchText == null)
			handler.listProfile();
		else
			handler.listProfile(searchText);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}
}
