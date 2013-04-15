package org.imogene.web.client.ui.panel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author MEDES-IMPS
 */
public class RelationPopupPanel {
	
	interface Binder extends UiBinder<PopupPanel, RelationPopupPanel> {
		Binder BINDER = GWT.create(Binder.class);
	}
	
	@UiField(provided = true)
	DialogBox popup;
	@UiField
	VerticalPanel mainPanel;


	public RelationPopupPanel() {
		popup = new DialogBox(false, true);
		popup.setHTML("<img src='" + GWT.getModuleBaseURL() + "images/drag_top_right.png' style='position:relative;top:-8px;right:-8px;'>");
		popup.setGlassEnabled(true);
		Binder.BINDER.createAndBindUi(this);
	}
	
	public void show(int left, int top) {
		popup.setPopupPosition(left, top);
		popup.show();
	}
	
	public void show() {
		popup.center();
		popup.show();
	}
	
	public void hide() {
		popup.hide();
	}
	
	public void addWidget(Widget w) {
		mainPanel.add(w);
		mainPanel.setCellHorizontalAlignment(w, HasHorizontalAlignment.ALIGN_CENTER);
		mainPanel.setCellVerticalAlignment(w, HasVerticalAlignment.ALIGN_MIDDLE);
	}
	
	public int getAbsoluteLeft() {
		return mainPanel.getAbsoluteLeft();
	}
	
	public int getAbsoluteTop() {
		return mainPanel.getAbsoluteTop();
	}
	
	public int getOffsetWidth() {
		return mainPanel.getOffsetWidth();
	}
	
	public int getOffsetHeight() {
		return mainPanel.getOffsetHeight();
	}
	
}
