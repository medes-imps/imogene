package org.imogene.web.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired to request a back in the history
 * @author MEDES-IMPS
 */
public class HistoryBackEvent extends GwtEvent<HistoryBackEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	public interface Handler extends EventHandler {
		void onHistoryBackRequest();
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.onHistoryBackRequest();
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}
}
