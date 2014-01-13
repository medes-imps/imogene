package org.imogene.web.client.ui.menu;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author Medes-IMPS 
 */
public class MenuTema extends Composite {
	
	
	private static final MenuTemaUiBinder uiBinder = GWT.create(MenuTemaUiBinder.class);
	interface MenuTemaUiBinder extends UiBinder<Widget, MenuTema> {	}
	
	@UiField DisclosurePanel container;
	@UiField VerticalPanel layout;
	@UiField Label titleLabel;
	
	private final EventBus eventBus;
	private List<MenuItem> items = new ArrayList<MenuItem>();
	
	
	/**
	 * 
	 * @param eventBus
	 */
	public MenuTema(EventBus eventBus){	
				
		initWidget(uiBinder.createAndBindUi(this));
		
		this.eventBus = eventBus;
		
		this.eventBus.addHandler(SelectMenuItemEvent.TYPE,
				new SelectMenuItemEvent.Handler() {
					@Override
					public void selectMenuItem(MenuItem item) {
						selectionEvent(item);
					}
				});
	}

	public void setOpen(boolean open) {
		container.setOpen(open);
	}
	
	public void setHeader (String title) {
		titleLabel.setText(title);
	}	


	private void selectionEvent(MenuItem item) {		
		for(MenuItem current: items){
			if(current.equals(item))
				current.setSelected(true);
			else
				current.setSelected(false);
		}
	}
	
	/**
	 * Adds an item to the menu
	 * @param label the item label
	 * @param token the link token
	 * @param color the associated color
	 */
	public void addItem(String label, Command command, String color){
		MenuItem item = new MenuItem(eventBus, label, color, command);
		items.add(item);
		layout.add(item);
		layout.setCellWidth(item, "100%");		
	}
	
	public void addItem(MenuItem item){
		items.add(item);
		layout.add(item);
		layout.setCellWidth(item, "100%");	
	}
	
	/**
	 * Counts the number of items added to the tema
	 * @return the number of items added to the tema
	 */
	public int getItemCount() {
		return items.size();
	}
	
	/**
	 * Adds a line separator
	 */
	public void addSeparator(){
		Label separator = new Label();
		separator.setStyleName("imogene-MenuItemSeparator");
		layout.add(separator);
		layout.setCellHorizontalAlignment(separator, VerticalPanel.ALIGN_LEFT);
	}
	
	public void unselectAll(){
		for(MenuItem item : items){
			item.setSelected(false);
		}
	}
	
}
