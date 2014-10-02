package org.imogene.web.client.ui.field.relation.single.listdataprovider;

import java.util.ArrayList;
import java.util.List;

import org.imogene.web.client.ui.table.pager.ImogSimplePager;
import org.imogene.web.client.util.ImogBeanRenderer;
import org.imogene.web.shared.proxy.ImogBeanProxy;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Ignore;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;


/**
 * Popup panel that contains the list of entries that can be selected
 * in a Relation Field with cardinality = 1
 * @author MEDES-IMPS
 */
public class ImogSingleRelationBoxPopUpForList<T extends ImogBeanProxy> {
	
	
	@SuppressWarnings("rawtypes")
	interface Binder extends UiBinder<PopupPanel, ImogSingleRelationBoxPopUpForList> {
		Binder BINDER = GWT.create(Binder.class);
	}
	
	interface ImogSingleSelectionBoxGridStyle extends DataGrid.Style {	
	    String IMOG_CSS = "ImogSingleSelectionBoxGrid.css";
	}
	
	interface TableResources extends DataGrid.Resources {
		@Override
		@Source(value = {ImogSingleSelectionBoxGridStyle.IMOG_CSS })
		ImogSingleSelectionBoxGridStyle dataGridStyle();
	}
	
	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();
	
	private static final int itemByPage=10;
	private ImogSingleRelationForList<T> parentBox;
	private ImogBeanRenderer beanRenderer;
	private ListDataProvider<T> dataProvider = new ListDataProvider<T>();


	/* widgets */
	@UiField(provided = true)
	@Ignore 
	PopupPanel popup;
	@UiField(provided = true)
	@Ignore
	DataGrid<T> table;
	@UiField (provided = true)
	@Ignore
	ImogSimplePager pager;

	

	public ImogSingleRelationBoxPopUpForList(ImogSingleRelationForList<T> parentBoxValue, ImogBeanRenderer beanRenderer) {
		
		this.parentBox = parentBoxValue;
		this.beanRenderer = beanRenderer;
		
		popup = new PopupPanel(true);
		table = new DataGrid<T>(itemByPage, GWT.<TableResources> create(TableResources.class)) {
			@Override
			protected void onUnload() {
				for(HandlerRegistration r : registrations)
					r.removeHandler();
				registrations.clear();
				super.onUnload();
			}
		};
		
		pager = new ImogSimplePager();			
		Binder.BINDER.createAndBindUi(this);		
	
		Column<T, String> listColumn = new ListColumn();
		table.addColumn(listColumn);
		
		final SingleSelectionModel<T> selectionModel = new SingleSelectionModel<T>();
		table.setSelectionModel(selectionModel);
		registrations.add(selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				T value = selectionModel.getSelectedObject();
				if (value == null) {
					return;
				}
				parentBox.setValue(value, true);
				hide();
				selectionModel.setSelected(value, false);
			}
		}));
		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);		
		dataProvider.addDataDisplay(table);
	}	
	
	public void show() {
		popup.show();	
	}
	
	public void hide() {
		popup.hide();
	}
	
	/**
	 * 
	 * @param values
	 */
	public void addValuesToList(List<T> values) {	
		if(values!=null)
			dataProvider.getList().addAll(values);
		else
			dataProvider.getList().clear();
	}
	
	
	
	/**
	 * 
	 * @author MEDES-IMPS
	 *
	 */
	private class ListColumn extends Column<T, String> {
		
		public ListColumn() {
			super(new TextCell());
		}

		@Override
		public String getValue(T object) {
			return beanRenderer.getDisplayValue(object);
		}
	}	
	
}
