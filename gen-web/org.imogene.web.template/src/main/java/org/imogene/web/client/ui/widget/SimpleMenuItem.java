package org.imogene.web.client.ui.widget;


import java.util.ArrayList;
import java.util.List;

import org.imogene.web.client.event.SelectMenuItemEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

public class SimpleMenuItem extends Composite {

	private static MenuItemUiBinder uiBinder = GWT.create(MenuItemUiBinder.class);

	interface MenuItemUiBinder extends UiBinder<Widget, SimpleMenuItem> {
	}

	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();

	@UiField SimplePanel layout;
	@UiField Label link;
	
	private final EventBus eventBus;

	private String label;
	private Command command;
	private boolean selected = false;

	public SimpleMenuItem(EventBus eventBus, String pLabel, Command pCommand) {
		this.eventBus = eventBus;
		label = pLabel;
		command = pCommand;
		initWidget(uiBinder.createAndBindUi(this));
		link.setText(label);
	}

	@UiHandler("link")
	public void onClick(ClickEvent event) {
		eventBus.fireEvent(new SelectMenuItemEvent(this));
		command.execute();
	}

	private void addMouseHandlers() {

		MouseOverHandler mouseOver = new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent arg0) {
				if (!selected)
					layout.addStyleName("imogeneSimpleMenuItemOver");
			}
		};
		MouseOutHandler mouseOut = new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent arg0) {
				layout.removeStyleName("imogeneSimpleMenuItemOver");
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

	@Override
	protected void onLoad() {
		addMouseHandlers();
		super.onLoad();
	}
}
