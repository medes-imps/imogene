package org.imogene.web.client.ui.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PopupButtonPopup {
	
	interface Binder extends UiBinder<PopupPanel, PopupButtonPopup> {
		Binder BINDER = GWT.create(Binder.class);
	}
	
	@UiField(provided = true)
	PopupPanel popup;
	@UiField
	VerticalPanel mainPanel;


	public PopupButtonPopup() {
		popup = new PopupPanel(true);
		Binder.BINDER.createAndBindUi(this);
	}
	
	public void show(int left, int top) {
		popup.setPopupPosition(left, top);
		popup.show();
	}
	
	public void hide() {
		popup.hide();
	}
	
	public void addWidget(Widget w) {
		mainPanel.add(w);
	}
	
	public boolean isEmpty() {		
		if(mainPanel.getWidgetCount()>0)
			return false;
		else
			return true;	
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
	
	public void setPopupWidth(String width) {
		popup.getElement().getStyle().clearWidth();
		popup.getElement().getStyle().setProperty("Width", width);
	}
}
