package org.imogene.admin.client.event.list;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired in order to display the list of FieldGroupProfile form entries
 * 
 * @author MEDES-IMPS
 */
public class ListFieldGroupProfileEvent extends GwtEvent<ListFieldGroupProfileEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	private String searchText = null;

	public interface Handler extends EventHandler {
		void listFieldGroupProfile();

		void listFieldGroupProfile(String searchText);
	}

	public ListFieldGroupProfileEvent() {
	}

	public ListFieldGroupProfileEvent(String searchText) {
		this.searchText = searchText;
	}

	@Override
	protected void dispatch(Handler handler) {
		if (searchText == null)
			handler.listFieldGroupProfile();
		else
			handler.listFieldGroupProfile(searchText);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}
}
