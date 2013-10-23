package org.imogene.web.client.event;

import org.imogene.web.shared.proxy.ImogBeanProxy;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired after that a ImogBean form has been saved
 * @author MEDES-IMPS
 */
public class SaveImogBeanEvent extends GwtEvent<SaveImogBeanEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	public interface Handler extends EventHandler {
		void saveImogBean(ImogBeanProxy value);
		void saveImogBean(ImogBeanProxy value, String initField);
	}

	private final ImogBeanProxy value;
	/* the relation field that initiated the creation or update of the value */
	private final String initField;

	public SaveImogBeanEvent(ImogBeanProxy value) {
		this(value, null);
	}

	public SaveImogBeanEvent(ImogBeanProxy value, String initField) {
		this.value = value;
		this.initField = initField;
	}

	@Override
	protected void dispatch(Handler handler) {
		if (initField == null)
			handler.saveImogBean(value);
		else
			handler.saveImogBean(value, initField);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}
}
