package org.imogene.web.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired in order to display a DynamicField_Template form in view mode
 * @author MEDES-IMPS
 */
public class ViewDynamicFieldTemplateEvent
		extends
			GwtEvent<ViewDynamicFieldTemplateEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	public interface Handler extends EventHandler {
		void viewDynamicFieldTemplate(String entityId, GwtEvent<?> closeEvent);
	}

	private final String entityId;
	
	private final GwtEvent<?> closeEvent;

	public ViewDynamicFieldTemplateEvent(String entityId) {
		this(entityId, new ListDynamicFieldTemplateEvent());
	}
	
	public ViewDynamicFieldTemplateEvent(String entityId, GwtEvent<?> closeEvent) {
		this.entityId = entityId;
		this.closeEvent = closeEvent;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.viewDynamicFieldTemplate(entityId, closeEvent);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}
}
