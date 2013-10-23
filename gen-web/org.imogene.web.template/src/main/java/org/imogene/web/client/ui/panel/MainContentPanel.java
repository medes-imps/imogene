package org.imogene.web.client.ui.panel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * This panel is the main content panel,
 * which contains all panels displayed after
 * a successful login.
 * 
 * This panel displays a list menu and different 
 * information panels on the left side, and a main 
 * content panel on the right side.
 * @author Medes-IMPS
 */
public class MainContentPanel extends Composite {	
	
	private static final Binder uiBinder = GWT.create(Binder.class);
	interface Binder extends UiBinder<Widget, MainContentPanel> {	}
	
	@UiField HorizontalPanel layout;	
	
	private Widget content;
	
	
			
	public MainContentPanel(){
		initWidget(uiBinder.createAndBindUi(this));
	}

	
	public void setContent(Widget pContent){
		if(content != null)
			layout.remove(content);
		content = pContent;
		layout.add(content);
		layout.setCellWidth(content, "100%");
		layout.setCellHorizontalAlignment(content, HasHorizontalAlignment.ALIGN_CENTER);
		
	}
	
}
