package org.imogene.admin.client.ui.table.panel;

import java.util.ArrayList;
import java.util.List;

import org.imogene.admin.client.AdminIconConstants;
import org.imogene.admin.client.dataprovider.DynamicFieldTemplateDataProvider;
import org.imogene.admin.client.ui.table.DynamicFieldTemplateDynaTable;
import org.imogene.admin.shared.AdminRequestFactory;
import org.imogene.web.client.css.ImogResources;
import org.imogene.web.client.dynamicfields.i18n.DynFieldsNLS;
import org.imogene.web.client.dynamicfields.ui.field.FormTypeUtil;
import org.imogene.web.client.event.IsTableFilteredEvent;
import org.imogene.web.client.event.SelectMenuItemEvent;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.panel.WrapperPanel;
import org.imogene.web.client.ui.table.filter.ImogFilterPanel;
import org.imogene.web.client.ui.widget.PopupButton;
import org.imogene.web.client.ui.widget.SimpleMenuItem;
import org.imogene.web.client.util.LocalSession;
import org.imogene.web.client.util.ProfileUtil;
import org.imogene.web.client.util.TokenHelper;
import org.imogene.web.shared.proxy.criteria.ImogJunctionProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * Panel that contains the DynamicField_Template Dynatable
 * 
 * @author Medes-IMPS
 */
public class DynamicFieldTemplateListPanel extends Composite {

	private static final Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, DynamicFieldTemplateListPanel> {
	}

	private static List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();
	private final AdminRequestFactory requestFactory;
	private PopupButton filterButton;
	private PopupButton plusButton;
	private PushButton goHomeButton;

	@UiField(provided = true)
	ImogResources imogResources;

	@UiField(provided = true)
	WrapperPanel wrapperPanel;
	@UiField(provided = true)
	DynamicFieldTemplateDynaTable listComposite;

	/**
	 * Constructor
	 * 
	 * @param requestFactory
	 * @param searchText text that will be used to filter the table entries
	 */
	public DynamicFieldTemplateListPanel(AdminRequestFactory requestFactory, String searchText,
			FormTypeUtil formTypeUtil) {

		this.requestFactory = requestFactory;
		imogResources = GWT.create(ImogResources.class);
		imogResources.imogStyle().ensureInjected();

		DynamicFieldTemplateDataProvider provider = new DynamicFieldTemplateDataProvider(requestFactory, formTypeUtil);
		String filteringMessage = configureDataProvider(provider, searchText);

		wrapperPanel = new WrapperPanel();

		if (ProfileUtil.isAdmin())
			listComposite = new DynamicFieldTemplateDynaTable(requestFactory, provider, false);

		wrapperPanel.setTitle(DynFieldsNLS.constants().dynamicField_Template_table_text());
		wrapperPanel.setIcon(AdminIconConstants.DYNAMICFIELD_TEMPLATE_ICON);

		configureWrapperPanelForTable(formTypeUtil);

		if (filteringMessage != null)
			wrapperPanel.setMessageLabel(filteringMessage);

		initWidget(uiBinder.createAndBindUi(this));
	}

	/**
	 * Constructor
	 * 
	 * @param requestFactory
	 */
	public DynamicFieldTemplateListPanel(AdminRequestFactory requestFactory, FormTypeUtil formTypeUtil) {
		this(requestFactory, null, formTypeUtil);
	}

	/**
	 * Configures the Data provider that enables to get the data to feed the table that lists DynamicField_Template
	 * entries
	 * 
	 * @param provider
	 * @param searchText
	 * @return
	 */
	private String configureDataProvider(DynamicFieldTemplateDataProvider provider, String searchText) {

		String filteringMessage = null;
		if (searchText != null) {
			filteringMessage = provider.fullTextSearch(searchText);
			LocalSession.get().setSearchCriterions(provider.getSearchCriterions(), filteringMessage);
		} else {
			ImogJunctionProxy searchCriterions = LocalSession.get().getSearchCriterions();
			if (searchCriterions != null) {
				provider.setSearchCriterions(searchCriterions);
				filteringMessage = LocalSession.get().getFilteringMessage();
			}
		}
		return filteringMessage;
	}

	/**
	 * Configures the Wrapper panel to manage the actions that are related to the table that lists DynamicField_Template
	 * entries
	 * 
	 * @param eventBus
	 * @param listComposite
	 */
	private void configureWrapperPanelForTable(FormTypeUtil formTypeUtil) {

		ImogFilterPanel filterPanel = listComposite.getFilterPanel(formTypeUtil);
		Command createCommand = listComposite.getCreateCommand();
		PushButton deleteButton = listComposite.getDeleteButton();

		// add pager
		wrapperPanel.addHeaderWidget(listComposite.getTablePager());

		// add filter panel
		setFilterButton(filterPanel);

		// add create and export buttons
		setOtherActions(createCommand);

		// add delete button
		if (deleteButton != null)
			wrapperPanel.addHeaderWidget(deleteButton);

		// add goHome button
		wrapperPanel.addHeaderWidget(goHomeButton());
	}

	/**
	 * Adds a Filter button that enables to filter the table entries to the wrapper panel
	 * 
	 * @param eventBus
	 */
	private void setFilterButton(ImogFilterPanel filterPanel) {

		if (filterPanel != null) {
			filterButton = new PopupButton(BaseNLS.constants().button_filter());
			filterButton.addPopupPanelContent(filterPanel);
			wrapperPanel.addHeaderWidget(filterButton);
		}
	}

	/**
	 * Adds create and export buttons to the wrapper panel
	 * 
	 * @param createCommand
	 * @param exportButton
	 * @param eventBus
	 */
	private void setOtherActions(Command createCommand) {

		if (createCommand != null) {

			plusButton = new PopupButton(BaseNLS.constants().button_plus());

			if (createCommand != null) {
				SimpleMenuItem item = new SimpleMenuItem(requestFactory.getEventBus(), BaseNLS.constants()
						.button_create(), createCommand);
				plusButton.addPopupPanelContent(item);
			}

			wrapperPanel.addHeaderWidget(plusButton);
		}
	}

	/**
	 * Adds a button that enables to go the the Home panel
	 * 
	 * @param eventBus
	 * @return
	 */
	private PushButton goHomeButton() {

		goHomeButton = new PushButton(BaseNLS.constants().button_home());
		goHomeButton.setStyleName(imogResources.imogStyle().imogButton());
		goHomeButton.addStyleName(imogResources.imogStyle().imogButton2());
		goHomeButton.addStyleName("Dynatable-Button");
		return goHomeButton;
	}

	/**
	 * 
	 */
	private void setButtonHandlers() {

		final EventBus eventBus = requestFactory.getEventBus();

		// Handler for filterButton
		if (filterButton != null) {
			registrations.add(eventBus.addHandler(IsTableFilteredEvent.TYPE, new IsTableFilteredEvent.Handler() {
				@Override
				public void noticeFilteringChange(Boolean isFiltered, String message) {
					if (isFiltered) {
						filterButton.setButtonActivatedStyle();
						wrapperPanel.setMessageLabel(message);
					} else {
						filterButton.removeButtonActivatedStyle();
						wrapperPanel.clearMessageLabel();
					}
				}
			}));
		}

		// Handler for plusButton
		if (plusButton != null) {
			registrations.add(eventBus.addHandler(SelectMenuItemEvent.TYPE, new SelectMenuItemEvent.Handler() {
				@Override
				public void selectMenuItem(SimpleMenuItem menuItem) {
					plusButton.hidePopup();
				}
			}));
		}

		// Handler for goHomeButton
		if (goHomeButton != null) {
			registrations.add(goHomeButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					History.newItem(TokenHelper.TK_CLASSIC, true);
				}
			}));
		}

	}

	@Override
	protected void onUnload() {
		for (HandlerRegistration r : registrations)
			r.removeHandler();
		registrations.clear();
		super.onUnload();
	}

	@Override
	protected void onLoad() {
		setButtonHandlers();
		super.onLoad();
	}

}
