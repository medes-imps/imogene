package org.imogene.admin.client.event.list;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired in order to display the list of EntityProfile form entries
 * 
 * @author MEDES-IMPS
 */
public class ListEntityProfileEvent extends GwtEvent<ListEntityProfileEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	private String searchText = null;

	public interface Handler extends EventHandler {
		void listEntityProfile();

		void listEntityProfile(String searchText);
	}

	public ListEntityProfileEvent() {
	}

	public ListEntityProfileEvent(String searchText) {
		this.searchText = searchText;
	}

	@Override
	protected void dispatch(Handler handler) {
		if (searchText == null)
			handler.listEntityProfile();
		else
			handler.listEntityProfile(searchText);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}
}
