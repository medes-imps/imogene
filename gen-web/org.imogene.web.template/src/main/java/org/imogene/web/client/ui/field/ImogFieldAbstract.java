package org.imogene.web.client.ui.field;

import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * Abstract class that provides common field components
 * @author Medes-IMPS 
 */
public class ImogFieldAbstract extends Composite implements HasWidgets {	
	
	
	private static final Binder uiBinder = GWT.create(Binder.class);
	interface Binder extends UiBinder<Widget, ImogFieldAbstract> {	}

	@UiField HorizontalPanel layout;
	@UiField Label fieldLabel;
	@UiField HorizontalPanel fieldWidget;
	
	/**
	 * Field common parts
	 */
	public ImogFieldAbstract(){
		super.initWidget(uiBinder.createAndBindUi(this));
	}

	/**
	 * Sets the field label text
	 * @param label the text to be added a the field label
	 */
	public void setLabel(String label) {
		fieldLabel.setText(label);
	}
	
	/**
	 * Sets the field label text
	 * @param label the text to be added a the field label
	 * @param alignment the alignment of the label in comparison of
	 * the field value
	 */
	public void setLabel(String label, HorizontalAlignmentConstant alignment) {
		layout.setCellHorizontalAlignment(fieldLabel, alignment);
		fieldLabel.setText(label);
	}

	/**
	 * Gets the field label text
	 * @return the field label text
	 */
	public String getLabel() {
		return fieldLabel.getText();
	}
	
	public Label getLabelBox() {
		return fieldLabel;
	}
	
	/**
	 * Defines the width of cell that contains the label
	 * @param width width of cell that contains the label
	 */
	public void setLabelWidth(String width) {
		fieldLabel.setWidth(width);
	}

	@Override
	public void add(Widget w) {
		fieldWidget.add(w);
	}

	@Override
	public void clear() {
		fieldWidget.clear();
	}

	@Override
	public Iterator<Widget> iterator() {
		return fieldWidget.iterator();
	}

	@Override
	public boolean remove(Widget w) {
		return fieldWidget.remove(w);
	}
	


		
}
