package org.imogene.web.client.ui.menu;

import java.util.ArrayList;
import java.util.List;

import org.imogene.web.client.i18n.BaseNLS;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Ignore;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PopupMenu {
	
	interface Binder extends UiBinder<PopupPanel, PopupMenu> {
		Binder BINDER = GWT.create(Binder.class);
	}
	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();
	
	@UiField(provided = true)
	@Ignore 
	PopupPanel popup;
	@UiField(provided = true)
	@Ignore 
	VerticalPanel mainPanel;
	@UiField(provided = true)
	@Ignore 
	Label title;
	@UiField(provided=true)
	PushButton create;
	@UiField(provided=true)
	PushButton list;
	@UiField(provided = true)
	@Ignore 
	Label searchLabel;

	public PopupMenu(String title, boolean autohide) {
		
		popup = new PopupPanel(autohide);
		create = new PushButton(BaseNLS.constants().button_create());
		list = new PushButton(BaseNLS.constants().button_list());
		
		
		this.title = new Label(title);
		searchLabel = new Label(BaseNLS.constants().button_search());
		
		mainPanel = new VerticalPanel();
		MouseOutHandler mouseOut = new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent arg0) {
				hide();
			}
		};	
		registrations.add(mainPanel.addDomHandler(mouseOut, MouseOutEvent.getType()));
		
		Binder.BINDER.createAndBindUi(this);
	}
	
	public PopupMenu(String title) {
		this(title, true);
	}
	
	
	public void show(Widget parentWidget) {
		popup.setPopupPosition(parentWidget.getAbsoluteLeft()+parentWidget.getOffsetWidth()*2/3, parentWidget.getAbsoluteTop() + (parentWidget.getOffsetHeight()*2/3));
		popup.show();	
	}
	
	public void show(int left, int top) {
		popup.setPopupPosition(left, top);
		popup.show();
	}
	
	public void hide() {
		popup.hide();
	}
	
	public void setCreateClickHandler(ClickHandler handler){
		registrations.add(create.addClickHandler(handler));
	}
	
	
	public void setListClickHandler(ClickHandler handler){
		registrations.add(list.addClickHandler(handler));
	}
	
	public void setCloseHandler(CloseHandler<PopupPanel> handler){
		registrations.add(popup.addCloseHandler(handler));
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
