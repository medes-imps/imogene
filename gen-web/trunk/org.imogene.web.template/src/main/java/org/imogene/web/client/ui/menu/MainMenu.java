package org.imogene.web.client.ui.menu;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MainMenu extends Composite {

	private static MainMenuUiBinder uiBinder = GWT.create(MainMenuUiBinder.class);

	interface MainMenuUiBinder extends UiBinder<Widget, MainMenu> {
	}

	@UiField VerticalPanel container;
	
	private List<MenuTema> temas = new ArrayList<MenuTema>();

	public MainMenu() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	
	public void addItem(MenuTema tema){
		container.add(tema);
		temas.add(tema);
	}
	
	/**
	 * Adds a line separator
	 */
	public void addSeparator(){
		Label separator = new Label();
		separator.setStyleName("imogene-MenuItemSeparator");
		container.add(separator);
		container.setCellHorizontalAlignment(separator, VerticalPanel.ALIGN_LEFT);
	}
	
	/**
	 * Unselect all item of this menu.
	 */
	public void unselectAll(){
		for(MenuTema tema : temas){
			tema.unselectAll();
		}
	}
	
	

}
