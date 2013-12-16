package org.imogene.admin.client.ui.workflow;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.imogene.admin.client.AdminRenderer;
import org.imogene.admin.client.event.list.ListEntityProfileEvent;
import org.imogene.admin.client.event.save.SaveEntityProfileEvent;
import org.imogene.admin.client.event.view.ViewEntityProfileEvent;
import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.admin.client.ui.editor.EntityProfileEditor;
import org.imogene.admin.shared.AdminRequestFactory;
import org.imogene.admin.shared.request.EntityProfileRequest;
import org.imogene.web.client.ui.field.error.ImogConstraintViolation;
import org.imogene.web.client.ui.panel.RelationPopupPanel;
import org.imogene.web.client.ui.workflow.EditorWorkflowComposite;
import org.imogene.web.client.util.ImogKeyGenerator;
import org.imogene.web.client.util.ProfileUtil;
import org.imogene.web.shared.proxy.CardEntityProxy;
import org.imogene.web.shared.proxy.EntityProfileProxy;
import org.imogene.web.shared.proxy.ImogBeanProxy;
import org.imogene.web.shared.proxy.ProfileProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.BaseProxy;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

/**
 * Worflow that manages the life of a EntityProfileProxy in the UI
 * 
 * @author MEDES-IMPS
 */
public class EntityProfileEditorWorkflow extends EditorWorkflowComposite {

	interface Driver extends RequestFactoryEditorDriver<EntityProfileProxy, EntityProfileEditor> {
	}

	private AdminRequestFactory requestFactory;

	private EntityProfileRequest request;
	public EntityProfileProxy current;
	private Driver editorDriver;
	private EntityProfileEditor editor;
	private String initField;

	/**
	 * Workflow constructor for the creation of a EntityProfile instance
	 * 
	 * @param factory the application request factory
	 * @param titleContainer the Label that will display the workflow title
	 */
	public EntityProfileEditorWorkflow(AdminRequestFactory factory, Label titleContainer) {
		this(factory, titleContainer, null, null);
	}

	/**
	 * Workflow constructor for the creation of a EntityProfile instance
	 * 
	 * @param factory the application request factory
	 * @param titleContainer the Label that will display the workflow title
	 * @param parent the parent RelationPopupPanel when the workflow is opened from a relation field
	 * @param initField the name of the field that initiated the opening of the workflow when the workflow is opened
	 *            from a relation field
	 */
	public EntityProfileEditorWorkflow(AdminRequestFactory factory, Label titleContainer, RelationPopupPanel parent,
			String initField) {

		super(factory.getEventBus(), titleContainer, parent);

		requestFactory = factory;
		if (parent != null) {
			editor = new EntityProfileEditor(factory, true);
			this.initField = initField;
		} else
			editor = new EntityProfileEditor(factory);

		isNew = true;
		setEditable(true);

		setTitle(AdminNLS.constants().entityProfile_create_title());
		createDriver();
		createNewEntityProfile();

		this.setContent(editor);
	}

	/**
	 * Workflow constructor for the visualization and edition of an existing EntityProfile instance
	 * 
	 * @param factory the application request factory
	 * @param entityId the id of the EntityProfile instance to be visualized and edited
	 * @param titleContainer the Label that will display the workflow title
	 */
	public EntityProfileEditorWorkflow(AdminRequestFactory factory, String entityId, Label titleContainer) {
		this(factory, entityId, titleContainer, null, null);
	}

	/**
	 * Workflow constructor for the visualization and edition of an existing EntityProfile instance
	 * 
	 * @param factory the application request factory
	 * @param entityId the id of the EntityProfile instance to be visualized and edited
	 * @param titleContainer the label
	 * @param parent the parent RelationPopupPanel when the workflow is opened from a relation field
	 * @param initField the name of the field that initiated the opening of the workflow when the workflow is opened
	 *            from a relation field
	 */
	public EntityProfileEditorWorkflow(AdminRequestFactory factory, String entityId, Label titleContainer,
			RelationPopupPanel parent, String initField) {

		super(factory.getEventBus(), titleContainer, parent);

		requestFactory = factory;
		if (parent != null) {
			editor = new EntityProfileEditor(factory, true);
			this.initField = initField;
		} else
			editor = new EntityProfileEditor(factory);

		setModifiable(false);
		isNew = false;
		setEditable(false);

		createDriver();
		fetchEntityProfile(entityId);

		this.setContent(editor);
	}

	/**
	 * Create a new instance of EntityProfile
	 */
	private void createNewEntityProfile() {

		request = requestFactory.entityProfileRequest();

		/* create a new intance of EntityProfile */
		EntityProfileProxy newEntityProfile = request.create(EntityProfileProxy.class);
		newEntityProfile.setId(ImogKeyGenerator.generateKeyId("PRO_ENT"));

		/* push the instance to the editor */
		current = newEntityProfile;
		editorDriver.edit(current, request);

		/* update field widgets in editor */
		editor.computeVisibility(null, true);
		editor.setEdited(true);
	}

	/**
	 * Get an existing instance of EntityProfile
	 * 
	 * @param entityId the id of the EntityProfileProxy to be fetched
	 */
	private void fetchEntityProfile(String entityId) {

		EntityProfileRequest request = requestFactory.entityProfileRequest();

		/* get the EntityProfile instance from database */
		Request<EntityProfileProxy> fetchRequest = request.findById(entityId);
		fetchRequest.with("profile");
		fetchRequest.with("entity");

		fetchRequest.to(new Receiver<EntityProfileProxy>() {
			@Override
			public void onSuccess(EntityProfileProxy entity) {
				viewEntityProfile(entity);
			}
		}).fire();
	}

	/**
	 * Display the current instance of EntityProfile in editor
	 * 
	 * @param entity the EntityProfileProxy to be displayed
	 */
	private void viewEntityProfile(EntityProfileProxy entity) {

		/* display instance information */
		setTitle(AdminNLS.constants().entityProfile_name() + ": " + AdminRenderer.get().getDisplayValue(entity));
		setMetaData((ImogBeanProxy) entity);

		/* push the instance to the editor in view mode */
		request = requestFactory.entityProfileRequest();
		current = request.edit(entity);

		editor.setEditedValue(current);

		editorDriver.edit(current, request);
		editor.setEdited(false);

		/* update field widgets in editor */
		editor.computeVisibility(null, true);

		/* display edit button */
		if (ProfileUtil.isAdmin())
			setModifiable(true);
	}

	/**
	 * Edit the current instance of EntityProfile in editor
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
	 * Persist the current instance of EntityProfile
	 */
	@Override
	protected void save() {

		editor.validateFields();

		editorDriver.flush();

		// Check for errors on the client side
		if (editorDriver.hasErrors()) {
			// Window.alert("EntityProfile form not validated locally");
			return;
		}

		Request<Void> saveRequest = request.save(current, isNew);
		saveRequest.to(new Receiver<Void>() {
			@Override
			public void onSuccess(Void response) {
				requestFactory.getEventBus().fireEvent(new SaveEntityProfileEvent(current, initField));
				closeForm();
			}

			@Override
			public void onConstraintViolation(Set<ConstraintViolation<?>> errors) {
				// Window.alert("EntityProfile form not validated on server");

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
				Window.alert("Error updating the EntityProfile");
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
				requestFactory.getEventBus().fireEvent(new ViewEntityProfileEvent(current.getId(), closeEvent));
		}

	}

	@Override
	protected void returnToList() {
		requestFactory.getEventBus().fireEvent(new ListEntityProfileEvent());
	}

	/**
	 * Setter to inject a Profile value
	 * 
	 * @param value the value to be injected
	 * @param isLocked true if relation field shall be locked (non editable)
	 */
	public void setProfile(ProfileProxy value, boolean isLocked) {
		editor.setProfile(value, isLocked);
	}

	/**
	 * Setter to inject a SynchronizableEntity value
	 * 
	 * @param value the value to be injected
	 * @param isLocked true if relation field shall be locked (non editable)
	 */
	public void setEntity(CardEntityProxy value, boolean isLocked) {
		editor.setEntity(value, isLocked);
	}

}
