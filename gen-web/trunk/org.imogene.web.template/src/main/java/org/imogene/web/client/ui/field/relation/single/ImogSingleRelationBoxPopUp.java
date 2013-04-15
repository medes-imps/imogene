package org.imogene.web.client.ui.field.relation.single;

import java.util.ArrayList;
import java.util.List;

import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.table.ImogBeanDataProvider;
import org.imogene.web.client.ui.table.pager.ImogSimplePager;
import org.imogene.web.client.util.ImogBeanRenderer;
import org.imogene.web.shared.proxy.ImogBeanProxy;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Ignore;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;


/**
 * Popup panel that contains the list of entries that can be selected
 * in a Relation Field with cardinality = 1
 * @author MEDES-IMPS
 */
public class ImogSingleRelationBoxPopUp<T extends ImogBeanProxy> {
	
	
	@SuppressWarnings("rawtypes")
	interface Binder extends UiBinder<PopupPanel, ImogSingleRelationBoxPopUp> {
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
	private final SingleSelectionModel<T> selectionModel = new SingleSelectionModel<T>();
	private ImogSingleRelation<T> parentBox;
	private ImogBeanDataProvider<T> beanDataProvider;
	private ImogBeanRenderer beanRenderer;


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
	@UiField
	@Ignore
	Label searchLabel;
	@UiField
	@Ignore
	TextBox valueFilter;
	@UiField
	@Ignore
	PushButton filterButton;
	

	public ImogSingleRelationBoxPopUp(ImogSingleRelation<T> parentBoxValue, ImogBeanDataProvider<T> provider, ImogBeanRenderer beanRenderer) {
		
		this.parentBox = parentBoxValue;
		this.beanDataProvider = provider;
		this.beanRenderer = beanRenderer;
		
		if(beanDataProvider!=null && beanDataProvider.getSearchCriterions()!=null)
			beanDataProvider.setSearchCriterions(null);
		
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

		addMouseWheelHandler();
		
		searchLabel.setText(BaseNLS.constants().button_search());
		
		ImogAsyncDataProvider dataProvider = new ImogAsyncDataProvider();
		dataProvider.addDataDisplay(table);

	}
	
	
	@UiHandler("filterButton")
	void onFilter(ClickEvent e) {
		beanDataProvider.fullTextSearch(valueFilter.getText());
		//pager.firstPage();
		table.setVisibleRangeAndClearData(new Range(0,itemByPage), true);
	}	
	
	public void show() {
		popup.show();	
	}
	
	public void hide() {
		popup.hide();
	}
	
	
	private void addMouseWheelHandler() {

		MouseWheelHandler mouseWheel = new MouseWheelHandler() {
			@Override
			public void onMouseWheel(MouseWheelEvent e) {
				if(e.isNorth()) {	
					if(pager.hasPreviousPage())
						pager.previousPage();
				}
				else {
					if(pager.hasNextPage())
						pager.nextPage();	
				}
			}
		};
		registrations.add(table.addDomHandler(mouseWheel, MouseWheelEvent.getType()));
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
	
	
	
	/**
	 * @author MEDES-IMPS
	 */
	private class ImogAsyncDataProvider extends AsyncDataProvider<T> {

		@Override
		protected void onRangeChanged(final HasData<T> display) {

			/** count total nb of rows and display the table **/
			Request<Long> countRowsRequest = beanDataProvider.getTotalRowCount();
			countRowsRequest.fire(new Receiver<Long>() {
				@Override
				public void onSuccess(Long count) {
					if (count != null) {

						updateRowCount(count.intValue(), true);
						pager.setTotalNbOfRows(count.intValue());

						updateTable(display);
					}
				}
			});
		}
		
		/**
		 * 
		 * @param display
		 */
		private void updateTable(final HasData<T> display) {
			
			final Range range = display.getVisibleRange();
			final int start = range.getStart();

			Request<List<T>> request = beanDataProvider.getList(start, itemByPage);
			request.fire(new Receiver<List<T>>() {
				@Override
				public void onSuccess(List<T> response) {
					updateRowData(start, response);
				}
			});
		}

	}
	
}
