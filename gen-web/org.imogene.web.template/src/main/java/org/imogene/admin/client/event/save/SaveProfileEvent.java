package org.imogene.admin.client.event.save;

import org.imogene.web.shared.proxy.ProfileProxy;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired after that a Profile form has been saved
 * 
 * @author MEDES-IMPS
 */
public class SaveProfileEvent extends GwtEvent<SaveProfileEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	public interface Handler extends EventHandler {
		void saveProfile(ProfileProxy value);

		void saveProfile(ProfileProxy value, String initField);
	}

	private final ProfileProxy value;
	/* the relation field that initiated the creation or update of the value */
	private final String initField;

	public SaveProfileEvent(ProfileProxy value) {
		this(value, null);
	}

	public SaveProfileEvent(ProfileProxy value, String initField) {
		this.value = value;
		this.initField = initField;
	}

	@Override
	protected void dispatch(Handler handler) {
		if (initField == null)
			handler.saveProfile(value);
		else
			handler.saveProfile(value, initField);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}
}
