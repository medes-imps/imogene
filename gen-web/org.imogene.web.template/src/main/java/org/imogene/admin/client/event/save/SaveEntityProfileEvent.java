package org.imogene.admin.client.event.save;

import org.imogene.web.shared.proxy.EntityProfileProxy;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired after that a EntityProfile form has been saved
 * 
 * @author MEDES-IMPS
 */
public class SaveEntityProfileEvent extends GwtEvent<SaveEntityProfileEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	public interface Handler extends EventHandler {
		void saveEntityProfile(EntityProfileProxy value);

		void saveEntityProfile(EntityProfileProxy value, String initField);
	}

	private final EntityProfileProxy value;
	/* the relation field that initiated the creation or update of the value */
	private final String initField;

	public SaveEntityProfileEvent(EntityProfileProxy value) {
		this(value, null);
	}

	public SaveEntityProfileEvent(EntityProfileProxy value, String initField) {
		this.value = value;
		this.initField = initField;
	}

	@Override
	protected void dispatch(Handler handler) {
		if (initField == null)
			handler.saveEntityProfile(value);
		else
			handler.saveEntityProfile(value, initField);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}
}
