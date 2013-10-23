package org.imogene.web.client.ui.panel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * Panel that wraps Form and Table contents
 * @author Medes-IMPS
 */
public class WrapperPanelForTable extends Composite implements HasWidgets {

	private static final Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, WrapperPanelForTable> {
	}
	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();

	@UiField
	VerticalPanel layout;
	@UiField
	HorizontalPanel titlePanel;
	@UiField
	Label titleLabel;
	@UiField
	Label messageLabel;
	@UiField
	HorizontalPanel headerWidgetPanel;
//	@UiField
//	ScrollPanel scrollPanel;
	@UiField
	VerticalPanel panelContent;

	private Widget child;

	
	/**
	 * Constructor
	 */
	public WrapperPanelForTable() {
		this(null, null);
	}	
	

	/**
	 * Constructor
	 * @param pTitle
	 * @param pChild
	 */
	public WrapperPanelForTable(String pTitle, Widget pChild) {		
		initWidget(uiBinder.createAndBindUi(this));
		setTitle(pTitle);
		setChild(pChild);		
//		resize();		
	}



	/**
	 * 
	 * @param pChild
	 */
	public void setChild(Widget pChild) {
		if(pChild!=null) {
			child = pChild;
			child.setHeight("100%");
			panelContent.add(child);
			panelContent.setCellHeight(child, "100%");
			panelContent.setCellWidth(child, "100%");
			panelContent.setCellHorizontalAlignment(child, HasHorizontalAlignment.ALIGN_CENTER);
//			scrollPanel.ensureVisible(panelContent);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public Widget getChild() {
		if(panelContent.getWidgetCount()==2)
			return panelContent.getWidget(1);
		else
			return null;
	}

	/**
	 * 
	 * @param buttons
	 */
	public void addButtons(List<PushButton> buttons) {
		if (buttons != null && buttons.size() > 0) {
			for (PushButton entry : buttons) {
				addHeaderWidget(entry);
			}
		}
	}

	/**
	 * 
	 * @param widget
	 */
	public void addHeaderWidget(Widget widget) {
		addHeaderWidget(widget, -1);
	}
	
	/**
	 * 
	 * @param widget
	 * @param position
	 */
	public void addHeaderWidget(Widget widget, int position) {
		widget.getElement().getStyle().setProperty("marginLeft", "10px");		
		if(position>=0)
			headerWidgetPanel.insert(widget, position);
		else
			headerWidgetPanel.add(widget);
		headerWidgetPanel.setCellVerticalAlignment(widget,
				VerticalPanel.ALIGN_MIDDLE);
	}

	/**
	 * 
	 */
	public void clearHeaderWidget() {
		headerWidgetPanel.clear();
	}

	/**
	 * 
	 * @param message
	 */
	public void setMessageLabel(String message) {
		messageLabel.setText(message);
	}

	/**
	 * 
	 */
	public void clearMessageLabel() {
		messageLabel.setText("");
	}
	
	/**
	 * 
	 */
	public void setTitle(String title) {
		titleLabel.setText(title);
	}
	
	/**
	 * 
	 * @param url
	 */
	public void setIcon(String url) {
		Image titleIcon = new Image(url);
		titleIcon.setHeight("32px");
		titleIcon.setWidth("32px");
		titleIcon.setStyleName("iconMagin");
		titleIcon.getElement().getStyle().setProperty("marginRight", "10px");
		titlePanel.insert(titleIcon, 0);
		titlePanel.setCellHeight(titleIcon, "32px");
		titlePanel.setCellWidth(titleIcon, "32px");
		titlePanel.setCellVerticalAlignment(titleIcon, HasVerticalAlignment.ALIGN_MIDDLE);
		
	}
	
	/**
	 * 
	 * @param width
	 */
	public void setPanelWidth(String width) {
		layout.getElement().getStyle().setProperty("width", width);
	}	

	/**
	 * 
	 * @return
	 */
	public Label getTitleLabel() {
		return titleLabel;
	}
	
	/**
	 * 
	 * @param height
	 */
//	public void setPanelContentHeight(String height) {
//		scrollPanel.getElement().getStyle().clearHeight();
//		scrollPanel.getElement().getStyle().setProperty("height", height);
//	}	
//	
//	private void setPanelContentHeight(int height) {
//		scrollPanel.getElement().getStyle().clearHeight();
//		scrollPanel.getElement().getStyle().setProperty("height", height + "px");
//	}
//	
//	public void setPanelContentWidth(String width) {
//		scrollPanel.getElement().getStyle().clearWidth();
//		scrollPanel.getElement().getStyle().setProperty("width", width);
//	}
//	
//	private void setPanelContentWidth(int  width) {
//		scrollPanel.getElement().getStyle().clearWidth();
//		scrollPanel.getElement().getStyle().setProperty("width", width + "px" );
//	}

	@Override
	public void add(Widget w) {
		setChild(w);

	}

	@Override
	public void clear() {
		panelContent.clear();
		child = null;
	}

	@Override
	public Iterator<Widget> iterator() {
		return panelContent.iterator();
	}

	@Override
	public boolean remove(Widget w) {
		child = null;
		return panelContent.remove(w);
	}
	
	/**
	 * 
	 */
//	private void setWindowHandler() {
//		registrations.add(Window.addResizeHandler(new ResizeHandler() {
//			@Override
//			public void onResize(ResizeEvent arg0) {
//				resize();
//			}			
//		}));
//	}
	
	/**
	 * 
	 */
//	private void resize() {
//		
//		int height = Window.getClientHeight();
//		Double panelHeight = height - (height / 3.3);
//		setPanelContentHeight(panelHeight.intValue());
//		
//		int width = Window.getClientWidth();
//		setPanelContentWidth(width - width / 10);
//	}
	
	@Override
	protected void onUnload() {
		for (HandlerRegistration r : registrations)
			r.removeHandler();
		registrations.clear();
		super.onUnload();
	}

	@Override
	protected void onLoad() {
//		setWindowHandler();
		super.onLoad();
	}

}
