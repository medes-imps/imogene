package org.imogene.web.client.ui.field.widget;

import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.Composite;

public abstract class ImogWidget<T> extends Composite implements TakesValue<T> {

	public abstract void setValue(T value);

	public abstract T getValue();
	
	public abstract void setEnabled(boolean enabled);
	
	public abstract boolean isEdited();

}
