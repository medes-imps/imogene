package org.imogene.admin.client.event.list;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired in order to display the list of SynchronizableEntity form entries
 * 
 * @author MEDES-IMPS
 */
public class ListCardEntityEvent extends GwtEvent<ListCardEntityEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	private String searchText = null;

	public interface Handler extends EventHandler {
		void listCardEntity();

		void listCardEntity(String searchText);
	}

	public ListCardEntityEvent() {
	}

	public ListCardEntityEvent(String searchText) {
		this.searchText = searchText;
	}

	@Override
	protected void dispatch(Handler handler) {
		if (searchText == null)
			handler.listCardEntity();
		else
			handler.listCardEntity(searchText);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}
}
