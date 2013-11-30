package org.imogene.admin.client.event.create;

import org.imogene.admin.client.event.list.ListFieldGroupProfileEvent;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired in order to display a FieldGroupProfile form in creation mode
 * 
 * @author MEDES-IMPS
 */
public class CreateFieldGroupProfileEvent extends GwtEvent<CreateFieldGroupProfileEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	private final GwtEvent<?> closeEvent;

	public interface Handler extends EventHandler {
		void createNewFieldGroupProfile(GwtEvent<?> closeEvent);
	}

	public CreateFieldGroupProfileEvent() {
		this(new ListFieldGroupProfileEvent());
	}

	public CreateFieldGroupProfileEvent(GwtEvent<?> closeEvent) {
		this.closeEvent = closeEvent;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.createNewFieldGroupProfile(closeEvent);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

}
