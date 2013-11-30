package org.imogene.admin.client.event.save;

import org.imogene.web.shared.proxy.CardEntityProxy;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired after that a CardEntity form has been saved
 * 
 * @author MEDES-IMPS
 */
public class SaveCardEntityEvent extends GwtEvent<SaveCardEntityEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	public interface Handler extends EventHandler {
		void saveCardEntity(CardEntityProxy value);

		void saveCardEntity(CardEntityProxy value, String initField);
	}

	private final CardEntityProxy value;
	/* the relation field that initiated the creation or update of the value */
	private final String initField;

	public SaveCardEntityEvent(CardEntityProxy value) {
		this(value, null);
	}

	public SaveCardEntityEvent(CardEntityProxy value, String initField) {
		this.value = value;
		this.initField = initField;
	}

	@Override
	protected void dispatch(Handler handler) {
		if (initField == null)
			handler.saveCardEntity(value);
		else
			handler.saveCardEntity(value, initField);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}
}
