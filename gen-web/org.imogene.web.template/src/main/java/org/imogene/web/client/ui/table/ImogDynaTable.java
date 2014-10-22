package org.imogene.web.client.ui.table;

import java.util.ArrayList;
import java.util.List;

import org.imogene.lib.common.constants.CriteriaConstants;
import org.imogene.web.client.css.ImogResources;
import org.imogene.web.client.event.IsTableFilteredEvent;
import org.imogene.web.client.event.SelectionChangedInTableEvent;
import org.imogene.web.client.ui.table.filter.ImogFilterPanel;
import org.imogene.web.client.ui.table.pager.ImogSimplePager;
import org.imogene.web.client.util.FilterCriteria;
import org.imogene.web.client.util.LocalSession;
import org.imogene.web.client.util.ProfileUtil;
import org.imogene.web.shared.ImogRequestFactory;
import org.imogene.web.shared.proxy.ImogBeanProxy;
import org.imogene.web.shared.proxy.criteria.BasicCriteriaProxy;
import org.imogene.web.shared.proxy.criteria.ImogConjunctionProxy;
import org.imogene.web.shared.proxy.criteria.ImogCriterionProxy;
import org.imogene.web.shared.proxy.criteria.ImogJunctionProxy;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.AsyncHandler;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.RowStyles;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

/**
 * A composite that enables to display the entries of a given entity in a paginated table
 * @param <T> the type of entity for which the table applies to
 * @author MEDES-IMPS
 */
public abstract class ImogDynaTable<T extends ImogBeanProxy> extends Composite {

	@SuppressWarnings("rawtypes")
	interface Binder extends UiBinder<Widget, ImogDynaTable> {
	}

	private static Binder imogDynaTableUiBinder = GWT.create(Binder.class);

	public interface TableResources extends DataGrid.Resources {
		@Override
		@Source(value = { ImogDataGridStyle.IMOG_CSS })
		ImogDataGridStyle dataGridStyle();
	}

	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();

	@UiField
	protected ImogResources imogResources;

	private ImogSimplePager pager;

	@UiField(provided = true)
	protected ImogDataGrid<T> table;
	private Timer refreshTimer = null;

	private int lastFetch;
	protected int itemByPage = 30;
	private String sortProperty = "";
	private boolean newSort = true;
	private boolean ascSort = true;
	boolean checkBoxesVisible = false;
	private int lastScrollPos = 0;

	protected final ImogRequestFactory requestFactory;
	protected ImogBeanDataProvider<T> beanDataProvider;
	protected MultiSelectionModel<T> selectionModel;

	public ImogDynaTable(final ImogRequestFactory requestFactory, ImogBeanDataProvider<T> provider, boolean checkBoxesVisible, int itemByPage, DataGrid.Resources resource) {

		this.requestFactory = requestFactory;
		this.beanDataProvider = provider;
		this.checkBoxesVisible = checkBoxesVisible;
		this.itemByPage = itemByPage;

		table = new ImogDataGrid<T>(itemByPage, resource);

		if (ProfileUtil.isAdmin()) {
			RowStyles<T> deletedEntityStyle = new RowStyles<T>() {
				@Override
				public String getStyleNames(T row, int rowIndex) {
					if (row != null && row.getDeleted() != null)
						return "dataGridDeletedRow";
					return null;
				}
			};
			table.setRowStyles(deletedEntityStyle);
		}

		initWidget(imogDynaTableUiBinder.createAndBindUi(this));

		imogResources.imogStyle().ensureInjected();

		pager = new ImogSimplePager();
		pager.setDisplay(table);
		pager.setRangeLimited(false);
		// addMouseWheelHandler();

		if (checkBoxesVisible) {

			Column<T, Boolean> checkColumn = new CheckColumn();
			CheckHeader checkHeader = new CheckHeader();
			checkHeader.setUpdater(new ValueUpdater<Boolean>() {
				@Override
				public void update(Boolean value) {
					for (T item : table.getVisibleItems())
						selectionModel.setSelected(item, value);
				}
			});

			table.addColumn(checkColumn, checkHeader);
			table.addStyleName("with-checkboxes");

			selectionModel = new MultiSelectionModel<T>();
			table.setSelectionModel(selectionModel, DefaultSelectionEventManager.<T> createCheckboxManager(0));

			selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
				@Override
				public void onSelectionChange(SelectionChangeEvent event) {
					requestFactory.getEventBus().fireEvent(new SelectionChangedInTableEvent(selectionModel.getSelectedSet().size()));
				}
			});
		}

		setColumns();

		ImogAsyncDataProvider dataProvider = new ImogAsyncDataProvider();
		dataProvider.addDataDisplay(table);
	}

	public ImogDynaTable(final ImogRequestFactory requestFactory, ImogBeanDataProvider<T> provider, boolean checkBoxesVisible) {
		this(requestFactory, provider, checkBoxesVisible, 30, GWT.<TableResources> create(TableResources.class));
	}

	protected void viewSelection(T value) {
		if (value == null) {
			return;
		}
		if (getViewEvent(value) != null)
			requestFactory.getEventBus().fireEvent(getViewEvent(value));
	}

	/**
	 * Adds a Mouse Wheel Handler to browse the table rows
	 * @return
	 */
	private MouseWheelHandler addMouseWheelHandler() {

		MouseWheelHandler mouseWheel = new MouseWheelHandler() {
			@Override
			public void onMouseWheel(MouseWheelEvent e) {
				if (e.isNorth()) {
					if (pager.hasPreviousPage())
						pager.previousPage();
				} else {
					if (pager.hasNextPage())
						pager.nextPage();
				}
			}
		};
		return mouseWheel;
	}

	public ImogSimplePager getTablePager() {
		return pager;
	}

	/**
	 * @param filterPanel
	 * @return
	 */
	protected ImogFilterPanel configureFilterPanel(final ImogFilterPanel filterPanel) {

		filterPanel.setFilterButtonClickHandler((new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				List<FilterCriteria> criteria = filterPanel.getFilterCriteria();
				String message = beanDataProvider.filter(criteria);
				table.setVisibleRangeAndClearData(new Range(0, itemByPage), true);
				if (message != null) {
					LocalSession.get().setSearchCriterions(beanDataProvider.getSearchCriterions(), message);
					requestFactory.getEventBus().fireEvent(new IsTableFilteredEvent(true, message));
				} else
					requestFactory.getEventBus().fireEvent(new IsTableFilteredEvent(false, null));
			}
		}));
		filterPanel.setCancelFilterButtonClickOHandler((new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				filterPanel.resetFilterWidgets();
				beanDataProvider.setSearchCriterions(null);
				LocalSession.get().setSearchCriterions(null, null);
				table.setVisibleRangeAndClearData(new Range(0, itemByPage), true);
				requestFactory.getEventBus().fireEvent(new IsTableFilteredEvent(false, null));
			}
		}));

		return filterPanel;
	}

	/**
	 * Converts the search criteria in URL query parameters for the CSV export
	 * @return
	 */
	protected String getDataProviderCriteria() {
		StringBuffer result = new StringBuffer();
		ImogJunctionProxy criterions = beanDataProvider.getSearchCriterions();
		if (criterions instanceof ImogConjunctionProxy) {

			ImogConjunctionProxy conj = (ImogConjunctionProxy) criterions;
			List<ImogCriterionProxy> secondLevelCrits = conj.getCriterions();

			if (secondLevelCrits != null) {
				for (ImogCriterionProxy secondLevelCrit : secondLevelCrits) {
					if (secondLevelCrit instanceof BasicCriteriaProxy) {
						BasicCriteriaProxy crit = (BasicCriteriaProxy) secondLevelCrit;
						if (crit.getOperation().equals(CriteriaConstants.DATE_OPERATOR_BEFORE))
							result.append("&" + crit.getField().replace(".", "_") + "Before=" + crit.getValue());
						else if (crit.getOperation().equals(CriteriaConstants.DATE_OPERATOR_AFTER))
							result.append("&" + crit.getField().replace(".", "_") + "After=" + crit.getValue());
						else
							result.append("&" + crit.getField().replace(".", "_") + "=" + crit.getValue());
					}
				}
			}
		}
		return result.toString();
	}

	/**
	 * Sets the handlers for the DynaTable
	 */
	private void setHandlers() {

		// column sort handler
		AsyncHandler columnSortHandler = new AsyncHandler(table);
		registrations.add(table.addColumnSortHandler(columnSortHandler));

		// row view handler
		registrations.add(table.addCellPreviewHandler(new CellPreviewEvent.Handler<T>() {
			@Override
			public void onCellPreview(CellPreviewEvent<T> event) {

				event.setCanceled(true);
				String type = event.getNativeEvent().getType();
				if (type.equals("click")) {
					T value = event.getValue();
					if (checkBoxesVisible) {
						if (event.getColumn() > 0)
							viewSelection(value);
					} else {
						viewSelection(value);
					}
				}
			}
		}));

		// mouse wheel handler
		// registrations.add(table.addDomHandler(addMouseWheelHandler(), MouseWheelEvent.getType()));

		final ScrollPanel scrollable = table.getScrollPanel();
		registrations.add(scrollable.addScrollHandler(new ScrollHandler() {
			@Override
			public void onScroll(ScrollEvent event) {

				int oldScrollPos = lastScrollPos;
				lastScrollPos = scrollable.getVerticalScrollPosition();

				if (oldScrollPos > lastScrollPos) {
					// Scrolling up
					if (lastScrollPos > 0 && lastScrollPos <= 20) {
						// We are near the start, so previous page.
						if (pager.hasPreviousPage())
							pager.previousPage();
					}
				} else {
					// // Scrolling down
					// int maxScrollBottom = scrollable.getWidget().getOffsetHeight() - scrollable.getOffsetHeight();
					// if (lastScrollPos >= maxScrollBottom) {
					// // We are near the end, so next page.
					// if (pager.hasNextPage())
					// pager.nextPage();
					// }
				}
			}
		}));

	}

	public void setTableWidth(String width) {
		table.getElement().getStyle().setProperty("minWidth", width);
	}

	public void setTableHeight(String height) {
		table.getElement().getStyle().setProperty("minHeight", height);
	}

	/**
	 * Sets if the table shall refresh itself automatically
	 * @param autorefresh true if the table shall refresh itself automatically
	 * @param millisecons the refresh period in milliseconds
	 */
	public void setAutoRefresh(boolean autorefresh, int millisecons) {

		if (autorefresh) {
			closeTimer();
			refreshTimer = new Timer() {
				public void run() {
					table.setVisibleRangeAndClearData(new Range(0, itemByPage), true);
				}
			};
			refreshTimer.scheduleRepeating(millisecons);
		} else {
			closeTimer();
		}
	}

	/**
	 * Sets if the table shall refresh itself automatically
	 * @param autorefresh true if the table shall refresh itself automatically
	 */
	public void setAutoRefresh(boolean autorefresh) {
		setAutoRefresh(autorefresh, 30000);
	}

	/**
	 * Closes the timer if it has been set
	 */
	private void closeTimer() {
		if (refreshTimer != null) {
			refreshTimer.cancel();
			refreshTimer = null;
		}
	}

	@Override
	protected void onUnload() {
		for (HandlerRegistration r : registrations)
			r.removeHandler();
		registrations.clear();

		closeTimer();

		super.onUnload();
	}

	@Override
	protected void onLoad() {
		setHandlers();
		super.onLoad();
	}

	/**
	 * Set Table Columns
	 */
	protected abstract void setColumns();

	protected abstract GwtEvent<?> getViewEvent(T value);

	protected abstract String getDefaultSortProperty();

	protected abstract boolean getDefaultSortPropertyOrder();

	/**
	 * @author MEDES-IMPS
	 */
	public class CheckHeader extends Header<Boolean> {

		public CheckHeader() {
			super(new CheckboxCell(true, false));
			this.setHeaderStyleNames("dynatable-select-checkbox");
		}

		@Override
		public Boolean getValue() {
			for (T item : table.getVisibleItems()) {
				if (!selectionModel.isSelected(item))
					return false;
			}
			return table.getVisibleItems().size() > 0;
		}

	}

	/**
	 * @author MEDES-IMPS
	 */
	private class CheckColumn extends Column<T, Boolean> {
		public CheckColumn() {
			super(new CheckboxCell(true, false));
			this.setCellStyleNames("dynatable-select-checkbox");
		}

		@Override
		public Boolean getValue(T object) {
			return selectionModel.isSelected(object);
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
		 * @param display
		 */
		private void updateTable(final HasData<T> display) {

			final Range range = display.getVisibleRange();
			final int start = range.getStart();

			setSortConfiguration(start);

			// define request
			lastFetch = start;
			Request<List<T>> request;

			if (sortProperty.isEmpty())
				request = beanDataProvider.getList(getDefaultSortProperty(), start, itemByPage, getDefaultSortPropertyOrder());
			else {
				if (newSort)
					request = beanDataProvider.getList(sortProperty, 0, itemByPage, ascSort);
				else
					request = beanDataProvider.getList(sortProperty, start, itemByPage, ascSort);
			}

			request.fire(new Receiver<List<T>>() {
				@Override
				public void onSuccess(List<T> response) {
					updateRowData(start, response);
				}

				@Override
				public void onFailure(ServerFailure error) {
					Window.alert("Error retrieving the list items");
					super.onFailure(error);
				}

			});
		}

		/**
		 * @param start
		 */
		private void setSortConfiguration(int start) {

			// sorting configuration
			ColumnSortList sortList = table.getColumnSortList();
			if (sortList != null && sortList.size() > 0) {
				@SuppressWarnings("unchecked")
				ImogColumn<T, String> sortColumn = (ImogColumn<T, String>) sortList.get(0).getColumn();
				String newSortProperty = sortColumn.getPropertyName();
				if (newSortProperty.equals(sortProperty)) {
					newSort = false;
					if (lastFetch == start)
						// not pagination, changing order of sorting
						ascSort = !ascSort;
				} else {
					newSort = true;
					sortProperty = newSortProperty;
					ascSort = true;
				}
			}
		}
	}

}
