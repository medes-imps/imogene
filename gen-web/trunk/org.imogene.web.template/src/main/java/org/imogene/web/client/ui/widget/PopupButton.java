package org.imogene.web.client.ui.widget;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class PopupButton extends Composite {

	private static Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, PopupButton> {
	}

	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();
	
	private PopupButtonPopup popupPanel;

	@UiField
	HorizontalPanel popupButton;
	@UiField(provided = true)
	Label buttonLabel;
	@UiField(provided = true)
	Image buttonArrow;



	public PopupButton(String title) {
		buttonLabel = new Label(title);
		buttonArrow = new Image(GWT.getModuleBaseURL() + "/images/arrow_down.png");
		initWidget(uiBinder.createAndBindUi(this));		
		popupPanel = new PopupButtonPopup();
	}
	
	public PopupButton() {
		this("");
	}
	
	public void setButtonTitle(String title) {
		buttonLabel.setText(title);
	}

	/**
	 * Adds the Button handlers
	 */
	private void addHandlers() {

		ClickHandler clickHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				popupPanel.show(popupButton.getAbsoluteLeft(), popupButton.getAbsoluteTop() + popupButton.getOffsetHeight());
			}
		};
		registrations.add(popupButton.addDomHandler(clickHandler, ClickEvent.getType()));
		
		MouseOverHandler mouseOver = new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent arg0) {			
				popupButton.removeStyleName("popupButtonStyle");
				popupButton.addStyleName("popupButtonStyle-hover");
				buttonArrow.removeStyleName("popupButtonArrow");
				buttonArrow.addStyleName("popupButtonArrow-hover");
			}
		};	
		registrations.add(popupButton.addDomHandler(mouseOver, MouseOverEvent.getType()));
		
		MouseOutHandler mouseOut = new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent arg0) {			
				popupButton.addStyleName("popupButtonStyle");
				popupButton.removeStyleName("popupButtonStyle-hover");
				buttonArrow.addStyleName("popupButtonArrow");
				buttonArrow.removeStyleName("popupButtonArrow-hover");
			}
		};	
		registrations.add(popupButton.addDomHandler(mouseOut, MouseOutEvent.getType()));

	}
	
	public void addPopupPanelContent(Widget w) {
		popupPanel.addWidget(w);
	}
	
	public boolean isEmpty() {
		return popupPanel.isEmpty();
	}
	
	public void setButtonActivatedStyle() {
		buttonLabel.addStyleName("popupButtonStyle-activated");
	}
	
	public void removeButtonActivatedStyle() {
		buttonLabel.removeStyleName("popupButtonStyle-activated");
	}
	
	public void hidePopup() {
		popupPanel.hide();
	}
	
	public void setPopupWidth(String width) {
		popupPanel.setPopupWidth(width);
	}
	
	public void setButtonWidth(String width) {
		popupButton.getElement().getStyle().clearWidth();
		popupButton.getElement().getStyle().setProperty("width", width);
	}
	
	public void setButtonHeight(String height) {
		popupButton.getElement().getStyle().clearHeight();
		popupButton.getElement().getStyle().setProperty("height", height);
	}
	
	public void setButtonPadding(String padding) {
		popupButton.getElement().getStyle().clearPadding();
		popupButton.getElement().getStyle().setProperty("padding", padding);
	}

	@Override
	protected void onUnload() {
		for (HandlerRegistration r : registrations)
			r.removeHandler();
		registrations.clear();
		super.onUnload();
	}
	
	@Override
	protected void onLoad() {
		addHandlers();
		super.onLoad();
	}

}
