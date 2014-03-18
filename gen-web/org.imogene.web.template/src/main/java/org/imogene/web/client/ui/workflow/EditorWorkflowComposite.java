package org.imogene.web.client.ui.workflow;

import java.util.ArrayList;
import java.util.List;

import org.imogene.web.client.css.ImogResources;
import org.imogene.web.client.event.GoHomeEvent;
import org.imogene.web.client.event.HistoryBackEvent;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.panel.RelationPopupPanel;
import org.imogene.web.client.util.DateUtil;
import org.imogene.web.shared.proxy.ImogBeanProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;


/**
 * Abstract class for Entity Workflows
 * @author Medes-IMPS
 */
public abstract class EditorWorkflowComposite extends Composite {

	private static final Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, EditorWorkflowComposite> {
	}

	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();

	/* status */
	protected boolean isNew;
	protected boolean isModifiable = true;
	protected boolean isEdited = false;
	protected GwtEvent<?> closeEvent = new GoHomeEvent();

	protected EventBus eventBus;
	protected RelationPopupPanel parent = null;

	private Label titleContainer;
	protected PushButton saveButton;
	protected PushButton editButton;
	protected PushButton cancelButton;
	protected PushButton closeButton;
	// protected PushButton printButton;
	
	@UiField
	protected ImogResources imogResources;

	@UiField
	VerticalPanel layout;

	/* form metadata */
	@UiField
	VerticalPanel metaInfoPanel;
	@UiField
	HTML idLabel;
	@UiField
	HTML creationLabel;
	@UiField
	HTML modificationLabel;

	@UiField
	VerticalPanel formContent;
	private Widget content;
		
	/**
	 * Constructor
	 * @param eventBus the application event bus
	 * @param titleContainer the Label that will display the workflow title
	 * @param parent the parent RelationPopupPanel when the workflow is opened from a relation field
	 * @param returnToList true if the application shall display the Entity list after closing the workflow
	 */
	public EditorWorkflowComposite(EventBus eventBus, Label titleContainer, RelationPopupPanel parent) {

		this.eventBus = eventBus;
		this.parent = parent;
		this.titleContainer = titleContainer;

		initWidget(uiBinder.createAndBindUi(this));
		
		imogResources.imogStyle().ensureInjected();

		saveButton = new PushButton(BaseNLS.constants().button_save());
		editButton = new PushButton(BaseNLS.constants().button_edit());
		cancelButton = new PushButton(BaseNLS.constants().button_cancel());
		closeButton = new PushButton(BaseNLS.constants().button_close());
		// printButton = new PushButton(BaseNLS.constants().button_print());
		
		properties();
	}
	
	/**
	 * Sets the main content of the wokflow (the form editor)
	 * @param pContent content of the workflow
	 */
	protected void setContent(Widget pContent) {
		if (content != null)
			formContent.remove(content);
		content = pContent;
		formContent.add(content);
		formContent.setCellWidth(content, "100%");
		formContent.setCellHeight(content, "100%");
	}
	
	public void setCloseEvent(GwtEvent<?> closeEvent){
		this.closeEvent = closeEvent;		
	}

	/**
	 * Sets the workflow widget properties
	 */
	private void properties() {

		// layout.setCellHeight(metaInfoPanel, "22px");
		metaInfoPanel.setSpacing(0);
		
		saveButton.setStyleName(imogResources.imogStyle().imogButton());
		saveButton.addStyleName(imogResources.imogStyle().imogButton2());
		saveButton.addStyleName("imogene-Form-button");
		editButton.setStyleName(imogResources.imogStyle().imogButton());
		editButton.addStyleName(imogResources.imogStyle().imogButton1());
		editButton.addStyleName("imogene-Form-button");
		cancelButton.setStyleName(imogResources.imogStyle().imogButton());
		cancelButton.addStyleName("imogene-Form-button");
		closeButton.setStyleName(imogResources.imogStyle().imogButton());
		closeButton.addStyleName("imogene-Form-button");
		// printButton = new PushButton(imogResources.imogStyle().imogButton2());
	}

	/**
	 * Sets the workflow button handlers
	 */
	private void setButtonHandlers() {

		registrations.add(saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (Window.confirm(BaseNLS.constants().confirmation_save()))
					save();
			}
		}));

		registrations.add(cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// LocalSession.get().removeFromEdited(hashCode());
				if (isNew) {
					closeForm();
				} else {
					setEditable(false);
					cancel();
				}
			}
		}));

		registrations.add(editButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setEditable(true);
				edit();
			}
		}));

		registrations.add(closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				closeForm();
			}
		}));

		// registrations.add(printButton.addClickHandler(new ClickHandler() {
		// @Override
		// public void onClick(ClickEvent event) {
		// }}));
	}

	/**
	 * Gets the workflow title
	 */
	public String getTitle() {
		if (titleContainer != null)
			return titleContainer.getText();
		else
			return "";
	}

	/**
	 * Sets the workflow title
	 */
	public void setTitle(String title) {
		if (titleContainer != null)
			titleContainer.setText(title);
	}

	/**
	 * Sets the status of the form : editable or not
	 * @param editable true if the form is editable
	 */
	public void setEditable(boolean editable) {
		editButton.setVisible(!editable && isModifiable);
		// printButton.setVisible(!editable);
		saveButton.setVisible(editable);
		cancelButton.setVisible(editable);
		closeButton.setVisible(!editable);
	}

	/**
	 * Sets if this form is modifiable.
	 * @param pModifiable true if the form is modifiable
	 */
	public void setModifiable(boolean pModifiable) {
		isModifiable = pModifiable;
		editButton.setVisible(isModifiable);
	}

	/**
	 * Displays metadata about the instance that is managed by the workflow
	 * @param bean
	 */
	public void setMetaData(ImogBeanProxy bean) {
		String createUpdate = "";
		String modifUpdate = "";
		String idUpdate = "";
		if (bean.getCreated() != null && bean.getCreatedBy() != null) {
			String created = DateUtil.getDate(bean.getCreated());
			String creator = bean.getCreatedBy();
			createUpdate = BaseNLS.messages().form_metadata_creation(created, creator);
		}
		if (bean.getModified() != null && bean.getModifiedBy() != null) {
			String modified = DateUtil.getDate(bean.getModified());
			String modifier = bean.getModifiedBy();
			modifUpdate = BaseNLS.messages().form_metadata_modification(modified, modifier);
		}
		if (bean.getId() != null) {
			idUpdate = BaseNLS.messages().form_metadata_id(bean.getId());
		}
		idLabel.setHTML(idUpdate);
		modificationLabel.setHTML(modifUpdate);
		creationLabel.setHTML(createUpdate);
	}
	

	/**
	 * Closes the form
	 */
	protected void closeForm() {
		
		if(parent!=null)
			parent.hide();
		else {
			eventBus.fireEvent(new HistoryBackEvent());
		}
	}
	
	public PushButton getSaveButton() {
		return saveButton;
	}

	public PushButton getEditButton() {
		return editButton;
	}

	public PushButton getCancelButton() {
		return cancelButton;
	}

	public PushButton getCloseButton() {
		return closeButton;
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

	/**
	 * Action when the save button is clicked
	 */
	protected abstract void save();

	/**
	 * Action when the cancel button is clicked
	 */
	protected abstract void cancel();

	/**
	 * Action when the edit button is clicked
	 */
	protected abstract void edit();

	/**
	 * Action when the intance list shall be displayed
	 * after that the workflow is closed
	 */
	protected abstract void returnToList();

}
