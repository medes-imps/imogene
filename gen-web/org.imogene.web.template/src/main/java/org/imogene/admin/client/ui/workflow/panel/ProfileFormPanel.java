package org.imogene.admin.client.ui.workflow.panel;

import org.imogene.admin.client.ui.workflow.ProfileEditorWorkflow;
import org.imogene.admin.shared.AdminRequestFactory;
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
 * Panel that contains the Profile Workflow
 * @author Medes-IMPS
 */
public class ProfileFormPanel extends Composite {

	private static final Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, ProfileFormPanel> {
	}

	@UiField(provided = true)
	WrapperPanel wrapperPanel;
	@UiField(provided = true)
	ProfileEditorWorkflow editorWorkflow;

	/**
	 * Constructor
	 * @param requestFactory the application requestFactory
	 * @param entityId the id of the entity to be managed by the workflow. null if an entity is being created
	 * @param parent parent composite if the panel is contained in a RelationPopupPanel
	 * @param initField the field that initiated the display in a RelationPopupPanel
	 * @param returnToList true if after closing the wokflow, the application shall display the list of entities, false otherwise
	 */
	public ProfileFormPanel(AdminRequestFactory requestFactory, String entityId, RelationPopupPanel parent, String initField, ImogBeanRenderer renderer) {

		wrapperPanel = new WrapperPanel();
		wrapperPanel.setWidth("90%");
		Label titleContainer = wrapperPanel.getTitleLabel();

		if (entityId != null) {
			if (parent == null)
				editorWorkflow = new ProfileEditorWorkflow(requestFactory, entityId, titleContainer, renderer);
			else {
				editorWorkflow = new ProfileEditorWorkflow(requestFactory, entityId, titleContainer, parent, initField, renderer);
			}
		} else {
			if (parent == null) {
				editorWorkflow = new ProfileEditorWorkflow(requestFactory, titleContainer, renderer);
			} else {
				editorWorkflow = new ProfileEditorWorkflow(requestFactory, titleContainer, parent, initField, renderer);
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
	public ProfileFormPanel(AdminRequestFactory requestFactory, String entityId, ImogBeanRenderer renderer) {
		this(requestFactory, entityId, null, null, renderer);
	}

	public void setCloseEvent(GwtEvent<?> closeEvent) {
		editorWorkflow.setCloseEvent(closeEvent);
	}

}
