package org.imogene.admin.client.event.save;

import org.imogene.web.shared.proxy.FieldGroupProfileProxy;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired after that a FieldGroupProfile form has been saved
 * 
 * @author MEDES-IMPS
 */
public class SaveFieldGroupProfileEvent extends GwtEvent<SaveFieldGroupProfileEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	public interface Handler extends EventHandler {
		void saveFieldGroupProfile(FieldGroupProfileProxy value);

		void saveFieldGroupProfile(FieldGroupProfileProxy value, String initField);
	}

	private final FieldGroupProfileProxy value;
	/* the relation field that initiated the creation or update of the value */
	private final String initField;

	public SaveFieldGroupProfileEvent(FieldGroupProfileProxy value) {
		this(value, null);
	}

	public SaveFieldGroupProfileEvent(FieldGroupProfileProxy value, String initField) {
		this.value = value;
		this.initField = initField;
	}

	@Override
	protected void dispatch(Handler handler) {
		if (initField == null)
			handler.saveFieldGroupProfile(value);
		else
			handler.saveFieldGroupProfile(value, initField);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}
}
