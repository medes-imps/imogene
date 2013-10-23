package org.imogene.web.client.ui.menu;


import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class MenuItem extends Composite {

	private static MenuItemUiBinder uiBinder = GWT.create(MenuItemUiBinder.class);

	interface MenuItemUiBinder extends UiBinder<Widget, MenuItem> {
	}

	interface MenuItemStyle extends CssResource {
		String imogeneMenuItemOver();
		// String disabled();
	}
	
	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();

	@UiField MenuItemStyle style;
	@UiField SimplePanel layout;
	@UiField Label link;
	
	private final EventBus eventBus;

	private String label;
	private Command command;
	private String color;
	private boolean selected = false;

	public MenuItem(EventBus eventBus, String pLabel, String styleColor, Command pCommand) {
		this.eventBus = eventBus;
		label = pLabel;
		command = pCommand;
		color = styleColor;
		initWidget(uiBinder.createAndBindUi(this));
		link.setText(label);
		addMouseHandlers();
	}

	@UiHandler("link")
	public void onClick(ClickEvent event) {
		eventBus.fireEvent(new SelectMenuItemEvent(this));
		command.execute();
	}

	/**
	 * 
	 * @param selected
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
		if (selected) {
			layout.removeStyleName(style.imogeneMenuItemOver());
			layout.addStyleDependentName(color + "selected");
		}
		else
			layout.removeStyleDependentName(color + "selected");
	}

	private void addMouseHandlers() {

		MouseOverHandler mouseOver = new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent arg0) {
				if (!selected)
					layout.addStyleName(style.imogeneMenuItemOver());
			}
		};
		MouseOutHandler mouseOut = new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent arg0) {
				layout.removeStyleName(style.imogeneMenuItemOver());
			}
		};
		registrations.add(layout.addDomHandler(mouseOver, MouseOverEvent.getType()));
		registrations.add(layout.addDomHandler(mouseOut, MouseOutEvent.getType()));
	}
	
	@Override
	protected void onUnload() {
		for(HandlerRegistration r : registrations)
			r.removeHandler();
		registrations.clear();
		super.onUnload();
	}

}
