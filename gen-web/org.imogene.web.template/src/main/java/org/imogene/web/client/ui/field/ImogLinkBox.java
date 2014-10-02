package org.imogene.web.client.ui.field;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Ignore;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Field Editor for Text fields 
 * @author MEDES-IMPS
 */
public class ImogLinkBox extends Composite implements ImogField<String> {

	private static final Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, ImogLinkBox> {
	}

	/* widgets */
	@UiField
	@Ignore
	ImogFieldAbstract fieldBox;
	@UiField
	@Ignore
	HTML idLink;

	public ImogLinkBox() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setEdited(boolean enabled) {
	}

	@Override
	public void setLabel(String label) {
		fieldBox.setLabel(label);
	}
	
	public void setLabel(String label, HorizontalAlignmentConstant alignment) {
		fieldBox.setLabel(label, alignment);
	}

	@Override
	public boolean isEdited() {
		return false;
	}

	/**
	 */
	@Override
	public void setValue(String value) {
		idLink.setHTML(value);	
	}

	/**
	 */
	@Override
	public String getValue() {
		if (idLink.getHTML().isEmpty())
			return null;
		else
			return idLink.getHTML();
	}
	
	/**
	 * Defines that the field shall notify value changes
	 * @param eventBus the event bus that will be used to fire the value change events
	 */
	public void notifyChanges(final EventBus eventBus) {
	}

	/**
	 * Defines the width of cell that contains the label
	 * @param width width of cell that contains the label
	 */
	public void setLabelWidth(String width) {
		fieldBox.setLabelWidth(width);
	}
	
	/**
	 * Sets the widget's width
	 */
	public void setBoxWidth(int width) {
		idLink.getElement().getStyle().setProperty("width", width + "px");
	}

	@Override
	public void showErrors(List<EditorError> errors) {
	}

}
