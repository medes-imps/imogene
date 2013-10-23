package org.imogene.web.client.ui.table.filter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ImogFilterBox extends Composite {

	private static final Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, ImogFilterBox> {
	}

	/* widgets */
	@UiField
	VerticalPanel filterPanel;	
	@UiField
	Label filterLabel;
	@UiField (provided=true)
	Widget filterWidget;	


	public ImogFilterBox(Widget widget) {
		filterWidget = widget;
		initWidget(uiBinder.createAndBindUi(this));		
	}

	public void setFilterLabel(String label) {
		filterLabel.setText(label);
	}

}
