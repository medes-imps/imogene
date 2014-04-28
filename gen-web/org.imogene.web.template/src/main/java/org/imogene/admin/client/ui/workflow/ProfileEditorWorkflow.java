package org.imogene.admin.client.ui.workflow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.imogene.admin.client.AdminRenderer;
import org.imogene.admin.client.event.list.ListProfileEvent;
import org.imogene.admin.client.event.save.SaveProfileEvent;
import org.imogene.admin.client.event.view.ViewProfileEvent;
import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.admin.client.ui.editor.ProfileEditor;
import org.imogene.admin.shared.AdminRequestFactory;
import org.imogene.admin.shared.request.ProfileRequest;
import org.imogene.web.client.ui.field.error.ImogConstraintViolation;
import org.imogene.web.client.ui.panel.RelationPopupPanel;
import org.imogene.web.client.ui.workflow.EditorWorkflowComposite;
import org.imogene.web.client.util.ImogKeyGenerator;
import org.imogene.web.client.util.ProfileUtil;
import org.imogene.web.shared.proxy.EntityProfileProxy;
import org.imogene.web.shared.proxy.FieldGroupProfileProxy;
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
 * Worflow that manages the life of a ProfileProxy in the UI
 * 
 * @author MEDES-IMPS
 */
public class ProfileEditorWorkflow extends EditorWorkflowComposite {

	interface Driver extends RequestFactoryEditorDriver<ProfileProxy, ProfileEditor> {
	}

	private AdminRequestFactory requestFactory;

	private ProfileRequest request;
	public ProfileProxy current;
	private Driver editorDriver;
	private ProfileEditor editor;
	private String initField;

	/**
	 * Workflow constructor for the creation of a Profile instance
	 * 
	 * @param factory the application request factory
	 * @param titleContainer the Label that will display the workflow title
	 */
	public ProfileEditorWorkflow(AdminRequestFactory factory, Label titleContainer) {
		this(factory, titleContainer, null, null);
	}

	/**
	 * Workflow constructor for the creation of a Profile instance
	 * 
	 * @param factory the application request factory
	 * @param titleContainer the Label that will display the workflow title
	 * @param parent the parent RelationPopupPanel when the workflow is opened from a relation field
	 * @param initField the name of the field that initiated the opening of the workflow when the workflow is opened
	 *            from a relation field
	 */
	public ProfileEditorWorkflow(AdminRequestFactory factory, Label titleContainer, RelationPopupPanel parent,
			String initField) {

		super(factory.getEventBus(), titleContainer, parent);

		requestFactory = factory;
		if (parent != null) {
			editor = new ProfileEditor(factory, true);
			this.initField = initField;
		} else
			editor = new ProfileEditor(factory);

		isNew = true;
		setEditable(true);

		setTitle(AdminNLS.constants().profile_create_title());
		createDriver();
		createNewProfile();

		this.setContent(editor);
	}

	/**
	 * Workflow constructor for the visualization and edition of an existing Profile instance
	 * 
	 * @param factory the application request factory
	 * @param entityId the id of the Profile instance to be visualized and edited
	 * @param titleContainer the Label that will display the workflow title
	 */
	public ProfileEditorWorkflow(AdminRequestFactory factory, String entityId, Label titleContainer) {
		this(factory, entityId, titleContainer, null, null);
	}

	/**
	 * Workflow constructor for the visualization and edition of an existing Profile instance
	 * 
	 * @param factory the application request factory
	 * @param entityId the id of the Profile instance to be visualized and edited
	 * @param titleContainer the label
	 * @param parent the parent RelationPopupPanel when the workflow is opened from a relation field
	 * @param initField the name of the field that initiated the opening of the workflow when the workflow is opened
	 *            from a relation field
	 */
	public ProfileEditorWorkflow(AdminRequestFactory factory, String entityId, Label titleContainer,
			RelationPopupPanel parent, String initField) {

		super(factory.getEventBus(), titleContainer, parent);

		requestFactory = factory;
		if (parent != null) {
			editor = new ProfileEditor(factory, true);
			this.initField = initField;
		} else
			editor = new ProfileEditor(factory);

		setModifiable(false);
		isNew = false;
		setEditable(false);

		createDriver();
		fetchProfile(entityId);

		this.setContent(editor);
	}

	/**
	 * Create a new instance of Profile
	 */
	private void createNewProfile() {

		request = requestFactory.profileRequest();

		/* create a new intance of Profile */
		ProfileProxy newProfile = request.create(ProfileProxy.class);
		newProfile.setId(ImogKeyGenerator.generateKeyId("PRO"));
		// create list of EntityProfile in editor
		newProfile.setEntityProfiles(new ArrayList<EntityProfileProxy>());
		// create list of FieldGroupProfile in editor
		newProfile.setFieldGroupProfiles(new ArrayList<FieldGroupProfileProxy>());

		/* push the instance to the editor */
		current = newProfile;
		editorDriver.edit(current, request);

		/* set request context for list editor operations */
		editor.setRequestContextForListEditors(request);

		/* update field widgets in editor */
		editor.computeVisibility(null, true);
		editor.setEdited(true);
	}

	/**
	 * Get an existing instance of Profile
	 * 
	 * @param entityId the id of the ProfileProxy to be fetched
	 */
	private void fetchProfile(String entityId) {

		ProfileRequest request = requestFactory.profileRequest();

		/* get the Profile instance from database */
		Request<ProfileProxy> fetchRequest = request.findById(entityId);

		fetchRequest.with("entityProfiles");

		fetchRequest.with("entityProfiles.entity");

		fetchRequest.with("fieldGroupProfiles");

		fetchRequest.with("fieldGroupProfiles.cardEntity");

		fetchRequest.with("fieldGroupProfiles.fieldGroup");

		fetchRequest.with("fieldGroupProfiles.fieldGroup.entity");

		fetchRequest.to(new Receiver<ProfileProxy>() {
			@Override
			public void onSuccess(ProfileProxy entity) {
				viewProfile(entity);
			}
		}).fire();
	}

	/**
	 * Display the current instance of Profile in editor
	 * 
	 * @param entity the ProfileProxy to be displayed
	 */
	private void viewProfile(ProfileProxy entity) {

		/* display instance information */
		setTitle(AdminNLS.constants().profile_name() + ": " + AdminRenderer.get().getDisplayValue(entity));
		setMetaData((ImogBeanProxy) entity);

		/* push the instance to the editor in view mode */
		request = requestFactory.profileRequest();
		current = request.edit(entity);
		List<EntityProfileProxy> entityProfiles = current.getEntityProfiles();
		if (entityProfiles != null && entityProfiles.size() > 0) {
			for (EntityProfileProxy item : entityProfiles) {
			}
		}
		List<FieldGroupProfileProxy> fieldGroupProfiles = current.getFieldGroupProfiles();
		if (fieldGroupProfiles != null && fieldGroupProfiles.size() > 0) {
			for (FieldGroupProfileProxy item : fieldGroupProfiles) {
			}
		}

		editor.setEditedValue(current);

		/* set request context for list editor operations */
		editor.setRequestContextForListEditors(request);

		editorDriver.edit(current, request);
		editor.setEdited(false);

		/* update field widgets in editor */
		editor.computeVisibility(null, true);

		/* display edit button */
		if (ProfileUtil.isAdmin())
			setModifiable(true);
	}

	/**
	 * Edit the current instance of Profile in editor
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
	 * Persist the current instance of Profile
	 */
	@Override
	protected void save() {

		editor.validateFields();

		editorDriver.flush();

		// Check for errors on the client side
		if (editorDriver.hasErrors()) {
			// Window.alert("Profile form not validated locally");
			return;
		}

		Request<Void> saveRequest = request.save(current, isNew);
		saveRequest.to(new Receiver<Void>() {
			@Override
			public void onSuccess(Void response) {
				requestFactory.getEventBus().fireEvent(new SaveProfileEvent(current, initField));
				closeForm();
			}

			@Override
			public void onConstraintViolation(Set<ConstraintViolation<?>> errors) {
				// Window.alert("Profile form not validated on server");

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
				Window.alert("Error updating the Profile");
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
				requestFactory.getEventBus().fireEvent(new ViewProfileEvent(current.getId(), closeEvent));
		}

	}

	@Override
	protected void returnToList() {
		requestFactory.getEventBus().fireEvent(new ListProfileEvent());
	}

}
