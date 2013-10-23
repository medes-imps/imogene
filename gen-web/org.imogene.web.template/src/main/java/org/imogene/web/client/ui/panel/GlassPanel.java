package org.imogene.web.client.ui.panel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class GlassPanel extends Composite implements ResizeHandler {

	private static GlassPanelUiBinder uiBinder = GWT
			.create(GlassPanelUiBinder.class);
	
	private static EmptyGlassPanelUiBinder emptyUiBinder = GWT
		.create(EmptyGlassPanelUiBinder.class);
	
	@UiTemplate("GlassPanel.ui.xml")
	interface GlassPanelUiBinder extends UiBinder<Widget, GlassPanel> {
	}
	
	@UiTemplate("EmptyGlassPanel.ui.xml")
	interface EmptyGlassPanelUiBinder extends UiBinder<Widget, GlassPanel>{		
	}
	
	public GlassPanel() {
		this(false);
		
	}
		
	public GlassPanel(boolean empty){
		if(empty){
			initWidget(emptyUiBinder.createAndBindUi(this));
			//message.setText("");
		}else{
			initWidget(uiBinder.createAndBindUi(this));
			//message.setText("loading");
		}
		Window.addResizeHandler(this);
	}
	
	
	@UiField
	VerticalPanel simplePanel;	
	
//	@UiField
//	Label message;
	
	private Widget receptor;
	
	public void show(Widget w){
		show(w.getAbsoluteLeft(), w.getAbsoluteTop(), w.getOffsetWidth(), w.getOffsetHeight());
	}
	
	public void showAndAdapt(Widget w){
		receptor=w;
		show(w.getAbsoluteLeft(), w.getAbsoluteTop(), w.getOffsetWidth(), w.getOffsetHeight());
	}
	
	public void show(int left, int top, int width, int height){				
		Element elem = simplePanel.getElement();
		DOM.setStyleAttribute(elem, "left", String.valueOf(left)+"px"); //$NON-NLS-1$ //$NON-NLS-2$
		DOM.setStyleAttribute(elem, "top", String.valueOf(top)+"px"); //$NON-NLS-1$ //$NON-NLS-2$
		DOM.setStyleAttribute(elem, "width", String.valueOf(width)+"px"); //$NON-NLS-1$ //$NON-NLS-2$
		DOM.setStyleAttribute(elem, "height", String.valueOf(height)+"px"); //$NON-NLS-1$ //$NON-NLS-2$
		DOM.setStyleAttribute(elem, "position", "absolute"); //$NON-NLS-1$ //$NON-NLS-2$
		DOM.setStyleAttribute(elem, "zIndex", "1005"); //$NON-NLS-1$ //$NON-NLS-2$
		RootPanel.get().add(this);
	}
	
	public void hide(){
		RootPanel.get().remove(this);
		receptor=null;
	}

	@Override
	public void onResize(ResizeEvent event) {		
		if(receptor!=null){
			Element elem = simplePanel.getElement();
			DOM.setStyleAttribute(elem, "left", receptor.getAbsoluteLeft()+"px"); //$NON-NLS-1$ //$NON-NLS-2$
			DOM.setStyleAttribute(elem, "top", receptor.getAbsoluteTop()+"px"); //$NON-NLS-1$ //$NON-NLS-2$
			DOM.setStyleAttribute(elem, "width", receptor.getOffsetWidth()+"px"); //$NON-NLS-1$ //$NON-NLS-2$
			DOM.setStyleAttribute(elem, "height", receptor.getOffsetHeight()+"px"); //$NON-NLS-1$ //$NON-NLS-2$
			DOM.setStyleAttribute(elem, "position", "absolute");			 //$NON-NLS-1$ //$NON-NLS-2$
			DOM.setStyleAttribute(elem, "zIndex", "1005"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

}
