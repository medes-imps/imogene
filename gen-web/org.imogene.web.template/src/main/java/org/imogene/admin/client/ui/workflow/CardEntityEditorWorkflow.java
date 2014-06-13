package org.imogene.admin.client.ui.workflow;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.imogene.admin.client.AdminRenderer;
import org.imogene.admin.client.event.list.ListCardEntityEvent;
import org.imogene.admin.client.event.save.SaveCardEntityEvent;
import org.imogene.admin.client.event.view.ViewCardEntityEvent;
import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.admin.client.ui.editor.CardEntityEditor;
import org.imogene.admin.shared.AdminRequestFactory;
import org.imogene.admin.shared.request.CardEntityRequest;
import org.imogene.web.client.ui.field.error.ImogConstraintViolation;
import org.imogene.web.client.ui.panel.RelationPopupPanel;
import org.imogene.web.client.ui.workflow.EditorWorkflowComposite;
import org.imogene.web.client.util.ImogBeanRenderer;
import org.imogene.web.client.util.ImogKeyGenerator;
import org.imogene.web.shared.proxy.CardEntityProxy;
import org.imogene.web.shared.proxy.ImogBeanProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.BaseProxy;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

/**
 * Worflow that manages the life of a CardEntityProxy in the UI
 * @author MEDES-IMPS
 */
public class CardEntityEditorWorkflow extends EditorWorkflowComposite {

	interface Driver extends RequestFactoryEditorDriver<CardEntityProxy, CardEntityEditor> {
	}

	private AdminRequestFactory requestFactory;
	private ImogBeanRenderer renderer;

	private CardEntityRequest request;
	public CardEntityProxy current;
	private Driver editorDriver;
	private CardEntityEditor editor;
	private String initField;

	/**
	 * Workflow constructor for the creation of a CardEntity instance
	 * @param factory the application request factory
	 * @param titleContainer the Label that will display the workflow title
	 */
	public CardEntityEditorWorkflow(AdminRequestFactory factory, Label titleContainer) {
		this(factory, titleContainer, null, null);
	}

	/**
	 * Workflow constructor for the creation of a CardEntity instance
	 * @param factory the application request factory
	 * @param titleContainer the Label that will display the workflow title
	 * @param parent the parent RelationPopupPanel when the workflow is opened from a relation field
	 * @param initField the name of the field that initiated the opening of the workflow when the workflow is opened from a relation field
	 */
	public CardEntityEditorWorkflow(AdminRequestFactory factory, Label titleContainer, RelationPopupPanel parent, String initField) {

		super(factory.getEventBus(), titleContainer, parent);

		requestFactory = factory;
		if (parent != null) {
			editor = new CardEntityEditor(factory, true);
			this.initField = initField;
		} else
			editor = new CardEntityEditor(factory);

		isNew = true;
		setEditable(true);

		setTitle(AdminNLS.constants().cardEntity_create_title());
		createDriver();
		createNewCardEntity();

		this.setContent(editor);
	}

	/**
	 * Workflow constructor for the visualization and edition of an existing CardEntity instance
	 * @param factory the application request factory
	 * @param entityId the id of the CardEntity instance to be visualized and edited
	 * @param titleContainer the Label that will display the workflow title
	 */
	public CardEntityEditorWorkflow(AdminRequestFactory factory, String entityId, Label titleContainer, ImogBeanRenderer renderer) {
		this(factory, entityId, titleContainer, null, null, renderer);
	}

	/**
	 * Workflow constructor for the visualization and edition of an existing CardEntity instance
	 * @param factory the application request factory
	 * @param entityId the id of the CardEntity instance to be visualized and edited
	 * @param titleContainer the label
	 * @param parent the parent RelationPopupPanel when the workflow is opened from a relation field
	 * @param initField the name of the field that initiated the opening of the workflow when the workflow is opened from a relation field
	 */
	public CardEntityEditorWorkflow(AdminRequestFactory factory, String entityId, Label titleContainer, RelationPopupPanel parent, String initField, ImogBeanRenderer renderer) {

		super(factory.getEventBus(), titleContainer, parent);

		requestFactory = factory;
		this.renderer = renderer;
		if (parent != null) {
			editor = new CardEntityEditor(factory, true);
			this.initField = initField;
		} else
			editor = new CardEntityEditor(factory);

		setModifiable(false);
		isNew = false;
		setEditable(false);

		createDriver();
		fetchCardEntity(entityId);

		this.setContent(editor);
	}

	@Override
	protected void onAttach() {
		super.onAttach();
	}

	/**
	 * Create a new instance of CardEntity
	 */
	private void createNewCardEntity() {

		request = requestFactory.cardEntityRequest();

		/* create a new intance of CardEntity */
		CardEntityProxy newCardEntity = request.create(CardEntityProxy.class);
		newCardEntity.setId(ImogKeyGenerator.generateKeyId("SYNC_ENT"));

		/* push the instance to the editor */
		current = newCardEntity;
		editorDriver.edit(current, request);

		/* set request context for list editor operations */
		editor.setRequestContextForListEditors(request);

		/* update field widgets in editor */
		editor.computeVisibility(null, true);
		editor.setEdited(true);
	}

	/**
	 * Get an existing instance of CardEntity
	 * @param entityId the id of the CardEntityProxy to be fetched
	 */
	private void fetchCardEntity(String entityId) {

		CardEntityRequest request = requestFactory.cardEntityRequest();

		/* get the CardEntity instance from database */
		Request<CardEntityProxy> fetchRequest = request.findById(entityId);

		fetchRequest.to(new Receiver<CardEntityProxy>() {
			@Override
			public void onSuccess(CardEntityProxy entity) {
				viewCardEntity(entity);
			}
		}).fire();
	}

	/**
	 * Display the current instance of CardEntity in editor
	 * @param entity the CardEntityProxy to be displayed
	 */
	private void viewCardEntity(CardEntityProxy entity) {

		/* display instance information */
		setTitle(AdminNLS.constants().cardEntity_name() + ": " + renderer.getDisplayValue(entity));
		setMetaData((ImogBeanProxy) entity);

		/* push the instance to the editor in view mode */
		request = requestFactory.cardEntityRequest();
		current = request.edit(entity);

		editor.setEditedValue(current);

		/* set request context for list editor operations */
		editor.setRequestContextForListEditors(request);

		editorDriver.edit(current, request);
		editor.setEdited(false);

		/* update field widgets in editor */
		editor.computeVisibility(null, true);
	}

	/**
	 * Edit the current instance of CardEntity in editor
	 */
	@Override
	protected void edit() {

		/* set the instance in edit mode in the editor */
		editor.setEdited(true);

		/* update field widgets in editor */
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
	 * Persist the current instance of CardEntity
	 */
	@Override
	protected void save() {

		editor.validateFields();

		editorDriver.flush();

		// Check for errors on the client side
		if (editorDriver.hasErrors()) {
			// Window.alert("CardEntity form not validated locally");
			return;
		}

		Request<Void> saveRequest = request.save(current, isNew);
		saveRequest.to(new Receiver<Void>() {
			@Override
			public void onSuccess(Void response) {
				requestFactory.getEventBus().fireEvent(new SaveCardEntityEvent(current, initField));
				closeForm();
			}

			@Override
			public void onConstraintViolation(Set<ConstraintViolation<?>> errors) {
				// Window.alert("CardEntity form not validated on server");

				// TODO manage errors on client side when made available by GWT
				if (errors != null && errors.size() > 0) {
					// convert ConstraintViolation to get localized messages
					AdminRenderer renderer = AdminRenderer.get();
					Set<ConstraintViolation<?>> imogErrors = new HashSet<ConstraintViolation<?>>();
					for (ConstraintViolation<?> error : errors) {
						ImogConstraintViolation violation = new ImogConstraintViolation();
						violation.setLeafBean((BaseProxy) error.getLeafBean());
						violation.setPropertyPath(error.getPropertyPath());
						violation.setRootBean((BaseProxy) error.getRootBean());
						violation.setMessage(renderer.getI18nErrorMessage(error.getMessage()));
						imogErrors.add(violation);
					}
					editorDriver.setConstraintViolations(imogErrors);
				}
			}

			@Override
			public void onFailure(ServerFailure error) {
				Window.alert("Error updating the CardEntity");
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
				requestFactory.getEventBus().fireEvent(new ViewCardEntityEvent(current.getId(), closeEvent));
		}

	}

	@Override
	protected void returnToList() {
		requestFactory.getEventBus().fireEvent(new ListCardEntityEvent());
	}

}
