package org.imogene.admin.client.event.save;

import org.imogene.web.shared.proxy.FieldGroupProxy;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired after that a FieldGroup form has been saved
 * 
 * @author MEDES-IMPS
 */
public class SaveFieldGroupEvent extends GwtEvent<SaveFieldGroupEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	public interface Handler extends EventHandler {
		void saveFieldGroup(FieldGroupProxy value);

		void saveFieldGroup(FieldGroupProxy value, String initField);
	}

	private final FieldGroupProxy value;
	/* the relation field that initiated the creation or update of the value */
	private final String initField;

	public SaveFieldGroupEvent(FieldGroupProxy value) {
		this(value, null);
	}

	public SaveFieldGroupEvent(FieldGroupProxy value, String initField) {
		this.value = value;
		this.initField = initField;
	}

	@Override
	protected void dispatch(Handler handler) {
		if (initField == null)
			handler.saveFieldGroup(value);
		else
			handler.saveFieldGroup(value, initField);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}
}
