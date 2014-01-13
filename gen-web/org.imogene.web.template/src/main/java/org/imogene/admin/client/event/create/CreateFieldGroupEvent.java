package org.imogene.admin.client.event.create;

import org.imogene.admin.client.event.list.ListFieldGroupEvent;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired in order to display a FieldGroup form in creation mode
 * 
 * @author MEDES-IMPS
 */
public class CreateFieldGroupEvent extends GwtEvent<CreateFieldGroupEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	private final GwtEvent<?> closeEvent;

	public interface Handler extends EventHandler {
		void createNewFieldGroup(GwtEvent<?> closeEvent);
	}

	public CreateFieldGroupEvent() {
		this(new ListFieldGroupEvent());
	}

	public CreateFieldGroupEvent(GwtEvent<?> closeEvent) {
		this.closeEvent = closeEvent;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.createNewFieldGroup(closeEvent);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

}
