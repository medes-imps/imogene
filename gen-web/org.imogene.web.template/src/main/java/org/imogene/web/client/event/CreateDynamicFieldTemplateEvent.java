package org.imogene.web.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired in order to display a DynamicField_Template form in creation mode
 * @author MEDES-IMPS
 */
public class CreateDynamicFieldTemplateEvent
		extends
			GwtEvent<CreateDynamicFieldTemplateEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	private final GwtEvent<?> closeEvent;

	public interface Handler extends EventHandler {
		void createNewDynamicFieldTemplate(GwtEvent<?> closeEvent);
	}
	
	public CreateDynamicFieldTemplateEvent() {
		this(new ListDynamicFieldTemplateEvent());
	}

	public CreateDynamicFieldTemplateEvent(GwtEvent<?> closeEvent) {
		this.closeEvent = closeEvent;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.createNewDynamicFieldTemplate(closeEvent);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

}
