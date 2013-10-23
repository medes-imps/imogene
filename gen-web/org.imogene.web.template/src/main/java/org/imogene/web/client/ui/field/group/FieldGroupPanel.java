package org.imogene.web.client.ui.field.group;

import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class FieldGroupPanel  extends Composite implements HasWidgets{

	private static FieldGroupPanelUiBinder uiBinder = GWT
			.create(FieldGroupPanelUiBinder.class);

	interface FieldGroupPanelUiBinder extends UiBinder<Widget, FieldGroupPanel>{
	}

	@UiField
	DisclosurePanel disclosurePanel;
	@UiField
	Label groupTitle;
	@UiField
	VerticalPanel groupBody;
	
	public FieldGroupPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}	
	
	public FieldGroupPanel(String title) {
		initWidget(uiBinder.createAndBindUi(this));
		setGroupTitle(title);
	}
	
	public void setGroupTitle(String title) {
		groupTitle.setText(title);
	}
	
	@Override
	public void add(Widget w) {
		if(groupBody==null)			
			Window.alert("body is null");
		groupBody.add(w);
	}

	@Override
	public void clear() {
		groupBody.clear();
	}

	@Override
	public Iterator<Widget> iterator() {		
		return groupBody.iterator();
	}

	@Override
	public boolean remove(Widget w) {		
		return groupBody.remove(w);
	}
	
	/**
	 * 
	 * @param fontSize
	 */
	public void setLabelFontSize(String fontSize) {
		groupTitle.getElement().getStyle().setProperty("fontSize", fontSize);
	}
	
	/**
	 * 
	 */
	public void setDisclosurePanelWidth(String width) {
		disclosurePanel.getElement().getStyle().setProperty("width", width);
	}
	
	/**
	 * 
	 * @param margin
	 */
	public void setDisclosurePanelMarginRight(String margin) {		
		disclosurePanel.getElement().getStyle().setProperty("marginRight", margin);
	}
	
	/**
	 * Sets if the DisclosurePanel shall be opened of closed
	 * @param open true of the DisclosurePanel shall be opened, false otherwise
	 */
	public void setOpen(boolean open) {
		disclosurePanel.setOpen(open);
	}
}
