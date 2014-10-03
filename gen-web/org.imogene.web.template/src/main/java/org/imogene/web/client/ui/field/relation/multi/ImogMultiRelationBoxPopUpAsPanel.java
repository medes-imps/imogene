package org.imogene.web.client.ui.field.relation.multi;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.imogene.web.client.css.ImogResources;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.panel.WrapperPanel;
import org.imogene.web.client.ui.table.ImogBeanDataProvider;
import org.imogene.web.client.ui.table.ImogDynaTable.CheckHeader;
import org.imogene.web.client.ui.table.filter.ImogFilterPanel;
import org.imogene.web.client.ui.table.pager.ImogSimplePager;
import org.imogene.web.client.ui.widget.ImageButton;
import org.imogene.web.client.ui.widget.PopupButton;
import org.imogene.web.client.util.FilterCriteria;
import org.imogene.web.client.util.ImogBeanRenderer;
import org.imogene.web.shared.proxy.ImogBeanProxy;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Ignore;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;


/**
 * Popup that displays the list of items that can be selected in a ImogMultiRelationBox
 * @author MEDES-IMPS
 */
public class ImogMultiRelationBoxPopUpAsPanel<T extends ImogBeanProxy> {

	@SuppressWarnings("rawtypes")
	interface Binder extends
			UiBinder<PopupPanel, ImogMultiRelationBoxPopUpAsPanel> {
		Binder BINDER = GWT.create(Binder.class);
	}

	/* A ClientBundle that provides images for this widget */
	static interface Resources extends ClientBundle {
		@ImageOptions(flipRtl = true)
		ImageResource nextPageBottom();

		@ImageOptions(flipRtl = true)
		ImageResource nextPageBottomDisabled();

		@ImageOptions(flipRtl = true)
		ImageResource previousPageTop();

		@ImageOptions(flipRtl = true)
		ImageResource previousPageTopDisabled();

		@Source("ImogMultiSelectionBoxButton.css")
		Style scrollButtonStyle();
	}

	/* A CssResource that provides styles for this widget's buttons */
	static interface Style extends CssResource {		
		String abledButton();
		String disabledButton();
	}

	/* A CssResource that provides styles for this widget */
	interface ImogMultiSelectionBoxGridStyle extends DataGrid.Style {
		String IMOG_CSS = "ImogMultiSelectionBoxGridInPanel.css";
	}

	/* A CssResource that provides styles for this widget's entry list */
	interface TableResources extends DataGrid.Resources {
		@Override
		@Source(value = { ImogMultiSelectionBoxGridStyle.IMOG_CSS })
		ImogMultiSelectionBoxGridStyle dataGridStyle();
	}

	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();

	private int itemByPage = 15;
	private final MultiSelectionModel<T> selectionModel;
	private ImogMultiRelationBox<T> parentBox;
	private ImogBeanDataProvider<T> beanDataProvider;
	private ImogBeanRenderer beanRenderer;

	@UiField
	protected ImogResources imogResources;

	/* widgets */
	@UiField(provided = true)
	@Ignore
	DialogBox popup;
	@UiField(provided = true)
	@Ignore
	WrapperPanel wrapperPanel;
	@UiField(provided = true)
	@Ignore
	DataGrid<T> table;
	@UiField(provided = true)
	@Ignore
	ImageButton scrollTopImage;
	@UiField(provided = true)
	@Ignore
	ImageButton scrollBottomImage;

	private ImogSimplePager pager;
	private PushButton okButton;
	private PushButton cancelButton;

	
	/**
	 * Constructor
	 * @param parentBoxValue the parent of the popup
	 * @param provider data provider to get the popup list items
	 * @param beanRenderer renderer to get the display value of the popup list items
	 * @param color
	 */
	public ImogMultiRelationBoxPopUpAsPanel(
			ImogMultiRelationBox<T> parentBoxValue,
			ImogBeanDataProvider<T> provider, ImogBeanRenderer beanRenderer,
			String color, int _itemsPerPage) {

		this.parentBox = parentBoxValue;
		this.beanDataProvider = provider;
		this.beanRenderer = beanRenderer;
		if(_itemsPerPage!=0)
			itemByPage = _itemsPerPage;

		if (beanDataProvider != null
				&& beanDataProvider.getSearchCriterions() != null)
			beanDataProvider.setSearchCriterions(null);

		popup = new DialogBox(false, true);
		popup.setHTML("<img src='" + GWT.getModuleBaseURL() + "images/drag_top_right.png' style='text-align: right; height=16px; width=16px'>");
		popup.setGlassEnabled(true);
		wrapperPanel = new WrapperPanel();
		wrapperPanel.setPanelContentHeight("470px");

		/* table configuration */
		table = new DataGrid<T>(itemByPage,
				GWT.<TableResources> create(TableResources.class)) {
			@Override
			protected void onUnload() {
				removeHandlers();
				super.onUnload();
			}
		};

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
		table.addStyleName("imogene-MultiRelation-with-checkboxes");

		selectionModel = new MultiSelectionModel<T>();
		table.setSelectionModel(selectionModel,
				DefaultSelectionEventManager.<T> createCheckboxManager());

		registrations.add(table
				.addCellPreviewHandler(new CellPreviewEvent.Handler<T>() {
					@Override
					public void onCellPreview(CellPreviewEvent<T> event) {
						event.setCanceled(true);
						String type = event.getNativeEvent().getType();
						if (type.equals("click")) {
							if (event.getColumn() > 0) {
								T value = event.getValue();
								if (selectionModel.isSelected(value))
									selectionModel.setSelected(value, false);
								else
									selectionModel.setSelected(value, true);
							}
						}
					}
				}));

		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		Column<T, String> listColumn = new ListColumn();
		table.addColumn(listColumn);

		table.setSelectionModel(selectionModel);

		ImogAsyncDataProvider dataProvider = new ImogAsyncDataProvider();
		dataProvider.addDataDisplay(table);

		/* scroll buttons */
		Resources buttonRessources = GWT.create(Resources.class);
		Style buttonStyle = buttonRessources.scrollButtonStyle();
		buttonStyle.ensureInjected();
		scrollTopImage = new ImageButton(buttonRessources.previousPageTop(),
				buttonRessources.previousPageTopDisabled(), buttonStyle.abledButton(),  buttonStyle.disabledButton());
		scrollBottomImage = new ImageButton(buttonRessources.nextPageBottom(),
				buttonRessources.nextPageBottomDisabled(), buttonStyle.abledButton(), buttonStyle.disabledButton());

		/* header configuration */

		pager = new ImogSimplePager();
		pager.setDisplay(table);
		wrapperPanel.addHeaderWidget(pager);
		
		cancelButton = new PushButton(BaseNLS.constants().button_cancel());
		cancelButton.setWidth("45px");

		okButton = new PushButton(BaseNLS.constants().button_ok());
		okButton.setWidth("45px");

		wrapperPanel.addHeaderWidget(cancelButton);
		wrapperPanel.addHeaderWidget(okButton);	

		Binder.BINDER.createAndBindUi(this);

		imogResources.imogStyle().ensureInjected();
		okButton.setStylePrimaryName(imogResources.imogStyle().imogButton());
		okButton.addStyleName(imogResources.imogStyle().imogButton2());
		okButton.setVisible(false);
		registrations.add(selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				if(selectionModel.getSelectedSet().size()>0)
					okButton.setVisible(true);
				else
					okButton.setVisible(false);
			}
		}));
		cancelButton.setStylePrimaryName(imogResources.imogStyle().imogButton());

		setButtonHandlers();
		//addMouseWheelHandler();
	}
	

	/**
	 * Creates the filter panel that enables to filter the entry list 
	 * @param filterPanel
	 */
	public void setFilterPanel(final ImogFilterPanel filterPanel) {

		if (filterPanel != null) {

			// add filter button
			final PopupButton filterButton = new PopupButton(BaseNLS.constants().button_filter());
			filterButton.addPopupPanelContent(filterPanel);
			wrapperPanel.addHeaderWidget(filterButton, 1);

			filterPanel.setFilterButtonClickHandler((new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					List<FilterCriteria> criteria = filterPanel
							.getFilterCriteria();
					beanDataProvider.filter(criteria);
					table.setVisibleRangeAndClearData(new Range(0, itemByPage),
							true);
				}
			}));

			filterPanel.setCancelFilterButtonClickOHandler((new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					filterPanel.resetFilterWidgets();
					beanDataProvider.setSearchCriterions(null);
					table.setVisibleRangeAndClearData(new Range(0, itemByPage),
							true);
				}
			}));
		}
	}

	/**
	 * 
	 */
	private void setButtonHandlers() {

		registrations.add(okButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Set<T> values = selectionModel.getSelectedSet();
				if (values != null) {
					for (T value : values) {
						if (!parentBox.isPresent(value))
							parentBox.addValue(value, true);
					}
				}
				hide();
			}
		}));

		registrations.add(cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		}));
	}

	/**
	 * 
	 */
	public void show() {
		//popup.setPopupPosition(Window.getClientWidth()/5 , 50);
		popup.center();
		popup.show();
	}

	/**
	 * 
	 */
	public void hide() {
		popup.hide();
		// removeHandlers();
	}

	/**
	 * 
	 * @param text
	 */
	public void setTitle(String text) {
		wrapperPanel.setTitle(text);
	}
	
	/**
	 * 
	 */
	private void setScrollButtonsVisibility() {
		
		if (pager.hasPreviousPage())
			scrollTopImage.setDisabled(false);
		else
			scrollTopImage.setDisabled(true);
		
		if (pager.hasNextPage())
			scrollBottomImage.setDisabled(false);
		else
			scrollBottomImage.setDisabled(true);
		
	}

	/**
	 * 
	 */
	private void addMouseWheelHandler() {

		MouseWheelHandler mouseWheel = new MouseWheelHandler() {
			@Override
			public void onMouseWheel(MouseWheelEvent e) {
				if (e.isNorth()) {
					if (pager.hasPreviousPage()){
						pager.previousPage();
						setScrollButtonsVisibility();
					}
					
				} else {
					if (pager.hasNextPage()) {
						pager.nextPage();
						setScrollButtonsVisibility();
					}
				}
			}
		};
		registrations.add(table.addDomHandler(mouseWheel,
				MouseWheelEvent.getType()));
	}

	/**
	 * Checks if a value is present in the box
	 * @param toTest the value to be tested
	 * @return
	 */
	protected boolean isPresent(T toTest) {
		return parentBox.isPresent(toTest);
	}
	
	@UiHandler("scrollTopImage")
	void scrollPrevious(ClickEvent e) {
		if (pager.hasPreviousPage()){
			pager.previousPage();
			setScrollButtonsVisibility();
		}
	}
	
	
	@UiHandler("scrollBottomImage")
	void scrollNext(ClickEvent e) {
		if (pager.hasNextPage()) {
			pager.nextPage();
			setScrollButtonsVisibility();
		}
	}
	
	/**
	 * 
	 */
	protected void removeHandlers() {
		for (HandlerRegistration r : registrations)
			r.removeHandler();
		registrations.clear();
	}

	/*
	 * Internal classes
	 */
	
	/**
	 * @author MEDES-IMPS
	 */
	public class CheckHeader extends Header<Boolean> {

		public CheckHeader() {
			super(new CheckboxCell(true, false));
			this.setHeaderStyleNames("multiRelation-selectAll-checkbox");
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
			this.setCellStyleNames("imogene-MultiRelation-select-checkbox");
		}

		@Override
		public Boolean getValue(T object) {
			return selectionModel.isSelected(object);
		}
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
			Request<Long> countRowsRequest = beanDataProvider
					.getTotalRowCount();
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

			Request<List<T>> request = beanDataProvider.getList(start,
					itemByPage);
			request.fire(new Receiver<List<T>>() {
				@Override
				public void onSuccess(List<T> response) {
					updateRowData(start, response);
					setScrollButtonsVisibility();
				}
			});
		}

	}

}
