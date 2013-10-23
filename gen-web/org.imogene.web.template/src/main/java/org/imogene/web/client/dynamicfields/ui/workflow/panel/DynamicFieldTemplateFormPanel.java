package org.imogene.web.client.dynamicfields.ui.workflow.panel;

import org.imogene.web.client.dynamicfields.ui.DynFieldsIconConstants;
import org.imogene.web.client.dynamicfields.ui.field.FormTypeUtil;
import org.imogene.web.client.dynamicfields.ui.workflow.DynamicFieldTemplateEditorWorkflow;
import org.imogene.web.client.event.GoHomeEvent;
import org.imogene.web.client.event.ListDynamicFieldTemplateEvent;
import org.imogene.web.client.ui.panel.RelationPopupPanel;
import org.imogene.web.client.ui.panel.WrapperPanel;
import org.imogene.web.shared.ImogRequestFactory;
import org.imogene.web.shared.proxy.ImogActorProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * Panel that contains the DynamicField_Template Workflow
 * @author Medes-IMPS
 */
public class DynamicFieldTemplateFormPanel extends Composite {

	private static final Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, DynamicFieldTemplateFormPanel> {
	}

	@UiField(provided = true)
	WrapperPanel wrapperPanel;
	@UiField(provided = true)
	DynamicFieldTemplateEditorWorkflow editorWorkflow;

	/**
	 * Constructor
	 * @param requestFactory the application requestFactory
	 * @param entityId the id of the entity to be managed by the workflow. null if an entity is being created
	 * @param parent parent composite if the panel is contained in a RelationPopupPanel
	 * @param initField the field that initiated the display in a RelationPopupPanel
	 * @param returnToList true if after closing the wokflow, the application shall display the list of entities, false otherwise
	 */
	public DynamicFieldTemplateFormPanel(
			ImogRequestFactory requestFactory, String entityId,
			RelationPopupPanel parent, String initField, Boolean returnToList, FormTypeUtil formTypeUtil) {

		wrapperPanel = new WrapperPanel();
		wrapperPanel.setWidth("90%");
		Label titleContainer = wrapperPanel.getTitleLabel();
		wrapperPanel.setIcon(DynFieldsIconConstants.DYNAMICFIELD_TEMPLATE_ICON);

		if (entityId != null) {
			if (parent == null)
				editorWorkflow = new DynamicFieldTemplateEditorWorkflow(
						requestFactory, entityId, titleContainer, formTypeUtil);
			else {
				editorWorkflow = new DynamicFieldTemplateEditorWorkflow(
						requestFactory, entityId, titleContainer, parent,
						initField, formTypeUtil);
				wrapperPanel.setPanelContentHeight("650px");
				wrapperPanel.setPanelContentWidth("600px");
			}
		} else {
			if (parent == null) {
				editorWorkflow = new DynamicFieldTemplateEditorWorkflow(
						requestFactory, titleContainer, formTypeUtil);
				if (returnToList != null && returnToList)
					editorWorkflow.setCloseEvent(new ListDynamicFieldTemplateEvent());
				else
					editorWorkflow.setCloseEvent(new GoHomeEvent());
			} else {
				editorWorkflow = new DynamicFieldTemplateEditorWorkflow(
						requestFactory, titleContainer, parent, initField, formTypeUtil);
				wrapperPanel.setPanelContentHeight("650px");
				wrapperPanel.setPanelContentWidth("600px");
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
	public DynamicFieldTemplateFormPanel(
			ImogRequestFactory requestFactory, String entityId, FormTypeUtil formTypeUtil) {
		this(requestFactory, entityId, null, null, null, formTypeUtil);
	}

	/**
	 * Constructor
	 * @param requestFactory the application requestFactory
	 * @param entityId the id of the entity to be managed by the workflow. null if an entity is being created
	 * @param returnToList true if after closing the wokflow, the application shall display the list of entities, false otherwise
	 */
	public DynamicFieldTemplateFormPanel(
			ImogRequestFactory requestFactory, String entityId,
			Boolean returnToList, FormTypeUtil formTypeUtil) {
		this(requestFactory, entityId, null, null, returnToList, formTypeUtil);
	}

	/**
	 * Setter to inject a Doctor value into the workflow and the editor
	 * @param value the value to be injected
	 * @param isLocked true if relation field shall be locked (non editable)
	 */
	public void setTemplateCreator(ImogActorProxy value, boolean isLocked) {
		editorWorkflow.setTemplateCreator(value, isLocked);
	}

	/**
	 * Setter to inject a Doctor value into the workflow and the editor
	 * @param value the value to be injected
	 */
	public void setTemplateCreator(ImogActorProxy value) {
		setTemplateCreator(value, false);
	}
	
	public void setFormType(String value) {
		editorWorkflow.setFormType(value);
	}
	
	public void setCloseEvent(GwtEvent<?> closeEvent) {
		editorWorkflow.setCloseEvent(closeEvent);
	}

}
