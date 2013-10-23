package org.imogene.web.client.event;

import org.imogene.web.shared.proxy.DynamicFieldTemplateProxy;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired after that a DynamicField_Template form has been saved
 * @author MEDES-IMPS
 */
public class SaveDynamicFieldTemplateEvent
		extends
			GwtEvent<SaveDynamicFieldTemplateEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	public interface Handler extends EventHandler {
		void saveDynamicFieldTemplate(DynamicFieldTemplateProxy value);
		void saveDynamicFieldTemplate(DynamicFieldTemplateProxy value,
				String initField);
	}

	private final DynamicFieldTemplateProxy value;
	/* the relation field that initiated the creation or update of the value */
	private final String initField;

	public SaveDynamicFieldTemplateEvent(DynamicFieldTemplateProxy value) {
		this(value, null);
	}

	public SaveDynamicFieldTemplateEvent(DynamicFieldTemplateProxy value,
			String initField) {
		this.value = value;
		this.initField = initField;
	}

	@Override
	protected void dispatch(Handler handler) {
		if (initField == null)
			handler.saveDynamicFieldTemplate(value);
		else
			handler.saveDynamicFieldTemplate(value, initField);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}
}
