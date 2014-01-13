package org.imogene.web.client.dynamicfields.ui.workflow;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.imogene.web.client.dynamicfields.i18n.DynFieldsNLS;
import org.imogene.web.client.dynamicfields.ui.editor.DynamicFieldTemplateEditor;
import org.imogene.web.client.dynamicfields.ui.field.FormTypeUtil;
import org.imogene.web.client.dynamicfields.ui.renderer.DynFieldsRenderer;
import org.imogene.web.client.event.ListDynamicFieldTemplateEvent;
import org.imogene.web.client.event.SaveDynamicFieldTemplateEvent;
import org.imogene.web.client.event.ViewDynamicFieldTemplateEvent;
import org.imogene.web.client.ui.field.error.ImogConstraintViolation;
import org.imogene.web.client.ui.panel.RelationPopupPanel;
import org.imogene.web.client.ui.workflow.EditorWorkflowComposite;
import org.imogene.web.client.util.ImogKeyGenerator;
import org.imogene.web.client.util.LocalSession;
import org.imogene.web.shared.ImogRequestFactory;
import org.imogene.web.shared.proxy.DynamicFieldTemplateProxy;
import org.imogene.web.shared.proxy.ImogActorProxy;
import org.imogene.web.shared.proxy.ImogBeanProxy;
import org.imogene.web.shared.request.DynamicFieldTemplateRequest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.BaseProxy;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

/**
 * Worflow that manages the life of a DynamicField_TemplateProxy in the UI
 * @author MEDES-IMPS
 */
public class DynamicFieldTemplateEditorWorkflow
		extends
			EditorWorkflowComposite {

	interface Driver
			extends
				RequestFactoryEditorDriver<DynamicFieldTemplateProxy, DynamicFieldTemplateEditor> {
	}

	private ImogRequestFactory requestFactory;

	private DynamicFieldTemplateRequest request;
	public DynamicFieldTemplateProxy current;
	private Driver editorDriver;
	private DynamicFieldTemplateEditor editor;
	private String initField;
	

	/**
	 * Workflow constructor for the creation of a DynamicField_Template instance
	 * @param factory the application request factory
	 * @param titleContainer the Label that will display the workflow title
	 */
	public DynamicFieldTemplateEditorWorkflow(ImogRequestFactory factory,
			Label titleContainer, FormTypeUtil formTypeUtil) {
		this(factory, titleContainer, null, null, formTypeUtil);
	}	

	/**
	 * Workflow constructor for the creation of a DynamicField_Template instance
	 * @param factory the application request factory
	 * @param titleContainer the Label that will display the workflow title
	 * @param parent the parent RelationPopupPanel when the workflow is opened from a relation field
	 * @param initField  the name of the field that initiated the opening of the workflow when the workflow is opened from a relation field
	 */
	public DynamicFieldTemplateEditorWorkflow(ImogRequestFactory factory,
			Label titleContainer, RelationPopupPanel parent, String initField,
			FormTypeUtil formTypeUtil) {

		super(factory.getEventBus(), titleContainer, parent);

		requestFactory = factory;
		if (parent != null) {
			editor = new DynamicFieldTemplateEditor(factory, true, true, formTypeUtil);
			this.initField = initField;
		} else
			editor = new DynamicFieldTemplateEditor(factory, false, true, formTypeUtil);

		isNew = true;
		setEditable(true);

		setTitle(DynFieldsNLS.constants().dynamicField_Template_create_title());
		createDriver();
		createNewDynamicField_Template();

		this.setContent(editor);
	}

	/**
	 * Workflow constructor for the visualization and edition of an existing DynamicField_Template instance
	 * @param factory the application request factory
	 * @param entityId the id of the DynamicField_Template instance to be visualized and edited	 
	 * @param titleContainer the Label that will display the workflow title	 
	 */
	public DynamicFieldTemplateEditorWorkflow(ImogRequestFactory factory,
			String entityId, Label titleContainer, FormTypeUtil formTypeUtil) {
		this(factory, entityId, titleContainer, null, null, formTypeUtil);
	}

	/**
	 * Workflow constructor for the visualization and edition of an existing DynamicField_Template instance
	 * @param factory the application request factory
	 * @param entityId the id of the DynamicField_Template instance to be visualized and edited	
	 * @param entityId the id of the DynamicField_Template instance to be visualized and edited	 	 
	 * @param parent the parent RelationPopupPanel when the workflow is opened from a relation field
	 * @param initField  the name of the field that initiated the opening of the workflow when the workflow is opened from a relation field
	 */
	public DynamicFieldTemplateEditorWorkflow(ImogRequestFactory factory,
			String entityId, Label titleContainer, RelationPopupPanel parent,
			String initField, FormTypeUtil formTypeUtil) {

		super(factory.getEventBus(), titleContainer, parent);

		requestFactory = factory;
		if (parent != null) {
			editor = new DynamicFieldTemplateEditor(factory, true, false, formTypeUtil);
			this.initField = initField;
		} else
			editor = new DynamicFieldTemplateEditor(factory, false, false, formTypeUtil);

		setModifiable(false);
		isNew = false;
		setEditable(false);

		createDriver();
		fetchDynamicField_Template(entityId);

		this.setContent(editor);
	}

	/**
	 * Create a new instance of DynamicField_Template
	 */
	private void createNewDynamicField_Template() {

		request = requestFactory.dynamicFieldTemplateRequest();

		/* create a new intance of DynamicField_Template */
		DynamicFieldTemplateProxy newDynamicField_Template = request
				.create(DynamicFieldTemplateProxy.class);
		newDynamicField_Template.setId(ImogKeyGenerator.generateKeyId("DFT"));
		newDynamicField_Template.setIsDefault(true);
		newDynamicField_Template.setRequiredValue(false);
		newDynamicField_Template.setAllUsers(false);
		newDynamicField_Template.setIsActivated(true);
		
		ImogActorProxy currentUser = LocalSession.get().getCurrentUser();
		newDynamicField_Template.setTemplateCreator(currentUser);

		/* push the instance to the editor */
		current = newDynamicField_Template;
		editorDriver.edit(current, request);

		/* update field widgets in editor */
		editor.computeVisibility(null, true);
		editor.setEdited(true);
	}

	/**
	 * Get an existing instance of DynamicField_Template
	 * @param entityId the id of the DynamicField_TemplateProxy to be fetched
	 */
	private void fetchDynamicField_Template(String entityId) {

		DynamicFieldTemplateRequest request = requestFactory
				.dynamicFieldTemplateRequest();

		/* get the DynamicField_Template instance from database */
		Request<DynamicFieldTemplateProxy> fetchRequest = request
				.findById(entityId);
		fetchRequest.with("templateCreator");

		fetchRequest.to(new Receiver<DynamicFieldTemplateProxy>() {
			@Override
			public void onSuccess(DynamicFieldTemplateProxy entity) {
				viewDynamicField_Template(entity);
			}
		}).fire();
	}

	/**
	 * Display the current instance of DynamicField_Template in editor
	 * @param entity the DynamicField_TemplateProxy to be displayed
	 */
	private void viewDynamicField_Template(DynamicFieldTemplateProxy entity) {

		/* display instance information */
		setTitle(DynFieldsNLS.constants().dynamicField_Template_name() + ": "
				+ DynFieldsRenderer.get().getDisplayValue(entity));
		setMetaData((ImogBeanProxy) entity);

		/* push the instance to the editor in view mode */
		request = requestFactory.dynamicFieldTemplateRequest();
		current = request.edit(entity);

		editorDriver.edit(current, request);
		editor.setEdited(false);

		/* update field widgets in editor */
		editor.computeVisibility(null, true);

		/* display edit button */
		setModifiable(true);
	}

	/**
	 * Edit the current instance of DynamicField_Template in editor
	 */
	@Override
	protected void edit() {

		/* set the instance in edit mode in the editor */
		editor.setEdited(true);
	}

	/**
	 * Initialize the editor driver
	 */
	private void createDriver() {
		if (editorDriver == null) {
			editorDriver = GWT.create(Driver.class);
			editorDriver.initialize(requestFactory, editor);
		}
	}

	/**
	 * Persist the current instance of DynamicField_Template
	 */
	@Override
	protected void save() {
		
		editor.validateFields();

		editorDriver.flush();

		// Check for errors on the client side
		if (editorDriver.hasErrors()) {
			//Window.alert("DynamicField_Template form not validated locally");
			return;
		}

		Request<Void> saveRequest = request.save(current, isNew);
		saveRequest.to(new Receiver<Void>() {
			@Override
			public void onSuccess(Void response) {
				requestFactory.getEventBus().fireEvent(
						new SaveDynamicFieldTemplateEvent(current, initField));
				closeForm();
			}

			@Override
			public void onConstraintViolation(Set<ConstraintViolation<?>> errors) {
				//Window.alert("DynamicField_Template form not validated on server");

				//TODO manage errors on client side when made available by GWT				
				if (errors != null && errors.size() > 0) {
					// convert ConstraintViolation to get localized messages
					DynFieldsRenderer renderer = DynFieldsRenderer.get();
					Set<ConstraintViolation<?>> imogErrors = new HashSet<ConstraintViolation<?>>();
					for (ConstraintViolation<?> error : errors) {
						ImogConstraintViolation violation = new ImogConstraintViolation();
						violation.setLeafBean((BaseProxy) error.getLeafBean());
						violation.setPropertyPath(error.getPropertyPath());
						violation.setRootBean((BaseProxy) error.getRootBean());
						violation.setMessage(renderer.getI18nErrorMessage(error
								.getMessage()));
						imogErrors.add(violation);
					}
					editorDriver.setConstraintViolations(imogErrors);
				}
			}

			@Override
			public void onFailure(ServerFailure error) {
				Window.alert("Error updating the DynamicField_Template");
				super.onFailure(error);
			}
		});

		request.fire();
	}

	@Override
	protected void cancel() {
		if (parent != null)
			parent.hide();
		else {
			if (isNew)
				requestFactory.getEventBus().fireEvent(closeEvent);
			else
				requestFactory.getEventBus().fireEvent(
						new ViewDynamicFieldTemplateEvent(current.getId(),
								closeEvent));
		}

	}

	@Override
	protected void returnToList() {
		requestFactory.getEventBus().fireEvent(
				new ListDynamicFieldTemplateEvent());
	}

	/**
	 * Setter to inject a Doctor value
	 * @param value the value to be injected
	 * @param isLocked true if relation field shall be locked (non editable)
	 */
	public void setTemplateCreator(ImogActorProxy value, boolean isLocked) {
		editor.setTemplateCreator(value, isLocked);
	}

	public void setFormType(String value) {
		editor.setFormType(value);
		
	}

}
