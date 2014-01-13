package org.imogene.web.client.event;

import org.imogene.web.client.ui.field.ImogField;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class FieldValueChangeEvent extends GwtEvent<FieldValueChangeEvent.Handler> {
	
	public static final Type<Handler> TYPE = new Type<Handler>();
	
	private final ImogField<?> field;


	public interface Handler extends EventHandler {
		void onValueChange(ImogField<?> field);
	}

	public FieldValueChangeEvent(ImogField<?> field) {
		this.field = field;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.onValueChange(field);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

}
