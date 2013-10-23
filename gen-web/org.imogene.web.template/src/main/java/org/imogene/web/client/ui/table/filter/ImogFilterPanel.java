package org.imogene.web.client.ui.table.filter;

import java.util.ArrayList;
import java.util.List;

import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.util.FilterCriteria;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class ImogFilterPanel extends Composite {

	private static final Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, ImogFilterPanel> {
	}
	
	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();

	/* widgets */
	@UiField
	protected VerticalPanel filters;
	@UiField
	protected PushButton filterButton;
	@UiField
	protected PushButton cancelFilterButton;


	public ImogFilterPanel() {
		initWidget(uiBinder.createAndBindUi(this));		
		filterButton.setText(BaseNLS.constants().button_search());
		cancelFilterButton.setText(BaseNLS.constants().button_cancel());
		createFilterWidgets();
	}
	
	public void setFilterButtonClickHandler(ClickHandler handler){
		registrations.add(filterButton.addClickHandler(handler));
	}
	
	public void setCancelFilterButtonClickOHandler(ClickHandler handler){
		registrations.add(cancelFilterButton.addClickHandler(handler));
	}
	
	/**
	 * 
	 * @param filterBox
	 */
	public void addTableFilter(ImogFilterBox filterBox) {
		filters.add(filterBox);
	}
	
	/**
	 * 
	 */
	public abstract List<FilterCriteria> getFilterCriteria();

	/**
	 * 
	 */
	protected abstract void createFilterWidgets();

	/**
	 * 
	 */
	public abstract void resetFilterWidgets();
	

//	@Override
//	protected void onUnload() {
//		for (HandlerRegistration r : registrations)
//			r.removeHandler();
//		registrations.clear();
//		super.onUnload();
//	}
	
	

}
