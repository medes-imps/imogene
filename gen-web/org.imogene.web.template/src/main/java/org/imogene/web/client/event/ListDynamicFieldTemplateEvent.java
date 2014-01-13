package org.imogene.web.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired in order to display the list of DynamicField_Template form entries
 * @author MEDES-IMPS
 */
public class ListDynamicFieldTemplateEvent
		extends
			GwtEvent<ListDynamicFieldTemplateEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	private String searchText = null;

	public interface Handler extends EventHandler {
		void listDynamicFieldTemplate();
		void listDynamicFieldTemplate(String searchText);
	}

	public ListDynamicFieldTemplateEvent() {
	}

	public ListDynamicFieldTemplateEvent(String searchText) {
		this.searchText = searchText;
	}

	@Override
	protected void dispatch(Handler handler) {
		if (searchText == null)
			handler.listDynamicFieldTemplate();
		else
			handler.listDynamicFieldTemplate(searchText);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}
}
