package org.imogene.admin.client.ui.workflow.panel;

import org.imogene.admin.client.AdminIconConstants;
import org.imogene.admin.client.event.list.ListNotificationEvent;
import org.imogene.admin.client.ui.workflow.NotificationEditorWorkflow;
import org.imogene.admin.shared.AdminRequestFactory;
import org.imogene.web.client.dynamicfields.ui.field.FormTypeUtil;
import org.imogene.web.client.event.GoHomeEvent;
import org.imogene.web.client.ui.panel.RelationPopupPanel;
import org.imogene.web.client.ui.panel.WrapperPanel;
import org.imogene.web.client.util.ImogBeanRenderer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * Panel that contains the Notification Workflow
 * @author Medes-IMPS
 */
public class NotificationFormPanel extends Composite {

	private static final Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, NotificationFormPanel> {
	}

	@UiField(provided = true)
	WrapperPanel wrapperPanel;
	@UiField(provided = true)
	NotificationEditorWorkflow editorWorkflow;

	/**
	 * Constructor
	 * @param requestFactory the application requestFactory
	 * @param entityId the id of the entity to be managed by the workflow. null if an entity is being created
	 * @param parent parent composite if the panel is contained in a RelationPopupPanel
	 * @param initField the field that initiated the display in a RelationPopupPanel
	 * @param returnToList true if after closing the wokflow, the application shall display the list of entities, false otherwise
	 */
	public NotificationFormPanel(AdminRequestFactory requestFactory, String entityId, RelationPopupPanel parent, String initField, Boolean returnToList, FormTypeUtil formTypeUtil,
			ImogBeanRenderer renderer) {

		wrapperPanel = new WrapperPanel();
		wrapperPanel.setWidth("90%");
		Label titleContainer = wrapperPanel.getTitleLabel();
		wrapperPanel.setIcon(AdminIconConstants.NOTIFICATION_ICON);

		if (entityId != null) {
			if (parent == null)
				editorWorkflow = new NotificationEditorWorkflow(requestFactory, entityId, titleContainer, formTypeUtil, renderer);
			else {
				editorWorkflow = new NotificationEditorWorkflow(requestFactory, entityId, titleContainer, parent, initField, formTypeUtil, renderer);
			}
		} else {
			if (parent == null) {
				editorWorkflow = new NotificationEditorWorkflow(requestFactory, titleContainer, formTypeUtil, renderer);
				if (returnToList != null && returnToList)
					editorWorkflow.setCloseEvent(new ListNotificationEvent());
				else
					editorWorkflow.setCloseEvent(new GoHomeEvent());
			} else {
				editorWorkflow = new NotificationEditorWorkflow(requestFactory, titleContainer, parent, initField, formTypeUtil, renderer);
			}
		}

		if (editorWorkflow.getEditButton() != null)
			wrapperPanel.addHeaderWidget(editorWorkflow.getEditButton());
		if (editorWorkflow.getCloseButton() != null)
			wrapperPanel.addHeaderWidget(editorWorkflow.getCloseButton());
		if (editorWorkflow.getSaveButton() != null)
			wrapperPanel.addHeaderWidget(editorWorkflow.getSaveButton());
		if (editorWorkflow.getCancelButton() != null)
			wrapperPanel.addHeaderWidget(editorWorkflow.getCancelButton());

		initWidget(uiBinder.createAndBindUi(this));
	}

	/**
	 * Constructor
	 * @param requestFactory the application requestFactory
	 * @param entityId the id of the entity to be managed by the workflow. null if an entity is being created
	 */
	public NotificationFormPanel(AdminRequestFactory requestFactory, String entityId, FormTypeUtil formTypeUtil, ImogBeanRenderer renderer) {
		this(requestFactory, entityId, null, null, null, formTypeUtil, renderer);
	}

	/**
	 * Constructor
	 * @param requestFactory the application requestFactory
	 * @param entityId the id of the entity to be managed by the workflow. null if an entity is being created
	 * @param returnToList true if after closing the wokflow, the application shall display the list of entities, false otherwise
	 */
	public NotificationFormPanel(AdminRequestFactory requestFactory, String entityId, Boolean returnToList, FormTypeUtil formTypeUtil, ImogBeanRenderer renderer) {
		this(requestFactory, entityId, null, null, returnToList, formTypeUtil, renderer);
	}

	public void setCloseEvent(GwtEvent<?> closeEvent) {
		editorWorkflow.setCloseEvent(closeEvent);
	}

}
