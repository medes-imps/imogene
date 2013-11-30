package org.imogene.admin.client.event.list;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired in order to display the list of FieldGroup form entries
 * 
 * @author MEDES-IMPS
 */
public class ListFieldGroupEvent extends GwtEvent<ListFieldGroupEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	private String searchText = null;

	public interface Handler extends EventHandler {
		void listFieldGroup();

		void listFieldGroup(String searchText);
	}

	public ListFieldGroupEvent() {
	}

	public ListFieldGroupEvent(String searchText) {
		this.searchText = searchText;
	}

	@Override
	protected void dispatch(Handler handler) {
		if (searchText == null)
			handler.listFieldGroup();
		else
			handler.listFieldGroup(searchText);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}
}
