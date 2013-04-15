package org.imogene.web.client.ui.field;

import java.util.List;

import com.google.gwt.editor.client.EditorError;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.EventBus;

public interface ImogField<E> extends IsWidget{
	
	public void setLabel(String label);
	
	public void setValue(E value);
	
	public E getValue();
	
	public void setEdited(boolean editable);
	
	public boolean isEdited();
	
	public void notifyChanges(EventBus eventBus);
	
	public void setLabelWidth(String width);
	
	public void showErrors(List<EditorError> errors);
	
	
}
