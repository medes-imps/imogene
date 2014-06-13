package org.imogene.admin.client.ui.workflow;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.imogene.admin.client.AdminRenderer;
import org.imogene.admin.client.event.list.ListNotificationEvent;
import org.imogene.admin.client.event.save.SaveNotificationEvent;
import org.imogene.admin.client.event.view.ViewNotificationEvent;
import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.admin.client.ui.editor.NotificationEditor;
import org.imogene.admin.shared.AdminRequestFactory;
import org.imogene.admin.shared.proxy.NotificationProxy;
import org.imogene.admin.shared.request.NotificationRequest;
import org.imogene.web.client.dynamicfields.ui.field.FormTypeUtil;
import org.imogene.web.client.event.GoHomeEvent;
import org.imogene.web.client.ui.field.error.ImogConstraintViolation;
import org.imogene.web.client.ui.panel.RelationPopupPanel;
import org.imogene.web.client.ui.workflow.EditorWorkflowComposite;
import org.imogene.web.client.util.ImogBeanRenderer;
import org.imogene.web.client.util.ImogKeyGenerator;
import org.imogene.web.client.util.ProfileUtil;
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
 * Worflow that manages the life of a NotificationProxy in the UI
 * @author MEDES-IMPS
 */
public class NotificationEditorWorkflow extends EditorWorkflowComposite {

	interface Driver extends RequestFactoryEditorDriver<NotificationProxy, NotificationEditor> {
	}

	private AdminRequestFactory requestFactory;

	private NotificationRequest request;
	public NotificationProxy current;
	private Driver editorDriver;
	private NotificationEditor editor;
	private String initField;

	/**
	 * Workflow constructor for the creation of a Notification instance
	 * @param factory the application request factory
	 * @param titleContainer the Label that will display the workflow title
	 */
	public NotificationEditorWorkflow(AdminRequestFactory factory, Label titleContainer, FormTypeUtil formTypeUtil, ImogBeanRenderer renderer) {
		this(factory, titleContainer, null, null, formTypeUtil, renderer);
	}

	/**
	 * Workflow constructor for the creation of a Notification instance
	 * @param factory the application request factory
	 * @param titleContainer the Label that will display the workflow title
	 * @param parent the parent RelationPopupPanel when the workflow is opened from a relation field
	 * @param initField the name of the field that initiated the opening of the workflow when the workflow is opened from a relation field
	 */
	public NotificationEditorWorkflow(AdminRequestFactory factory, Label titleContainer, RelationPopupPanel parent, String initField, FormTypeUtil formTypeUtil, ImogBeanRenderer renderer) {

		super(factory.getEventBus(), titleContainer, parent);

		requestFactory = factory;
		if (parent != null) {
			editor = new NotificationEditor(factory, true, formTypeUtil, renderer);
			this.initField = initField;
		} else
			editor = new NotificationEditor(factory, formTypeUtil, renderer);

		isNew = true;
		setEditable(true);

		setTitle(AdminNLS.constants().notification_create_title());
		createDriver();
		createNewNotification();

		this.setContent(editor);
	}

	/**
	 * Workflow constructor for the visualization and edition of an existing Notification instance
	 * @param factory the application request factory
	 * @param entityId the id of the Notification instance to be visualized and edited
	 * @param titleContainer the Label that will display the workflow title
	 */
	public NotificationEditorWorkflow(AdminRequestFactory factory, String entityId, Label titleContainer, FormTypeUtil formTypeUtil, ImogBeanRenderer renderer) {
		this(factory, entityId, titleContainer, null, null, formTypeUtil, renderer);
	}

	/**
	 * Workflow constructor for the visualization and edition of an existing Notification instance
	 * @param factory the application request factory
	 * @param entityId the id of the Notification instance to be visualized and edited
	 * @param entityId the id of the Notification instance to be visualized and edited
	 * @param parent the parent RelationPopupPanel when the workflow is opened from a relation field
	 * @param initField the name of the field that initiated the opening of the workflow when the workflow is opened from a relation field
	 */
	public NotificationEditorWorkflow(AdminRequestFactory factory, String entityId, Label titleContainer, RelationPopupPanel parent, String initField, FormTypeUtil formTypeUtil,
			ImogBeanRenderer renderer) {

		super(factory.getEventBus(), titleContainer, parent);

		requestFactory = factory;
		if (parent != null) {
			editor = new NotificationEditor(factory, true, formTypeUtil, renderer);
			this.initField = initField;
		} else
			editor = new NotificationEditor(factory, formTypeUtil, renderer);

		if (!ProfileUtil.isAdmin())
			this.setModifiable(false);
		isNew = false;
		setEditable(false);

		createDriver();
		fetchNotification(entityId);

		this.setContent(editor);
	}

	/**
	 * Create a new instance of Notification
	 */
	private void createNewNotification() {

		request = requestFactory.notificationRequest();

		/* create a new intance of Notification */
		NotificationProxy newNotification = request.create(NotificationProxy.class);
		newNotification.setId(ImogKeyGenerator.generateKeyId("NOTIF"));

		/* push the instance to the editor */
		current = newNotification;
		editorDriver.edit(current, request);

		/* update field widgets in editor */
		editor.computeVisibility(null, true);
		editor.setEdited(true);
	}

	/**
	 * Get an existing instance of Notification
	 * @param entityId the id of the NotificationProxy to be fetched
	 */
	private void fetchNotification(String entityId) {

		NotificationRequest request = requestFactory.notificationRequest();

		/* get the Notification instance from database */
		Request<NotificationProxy> fetchRequest = request.findById(entityId);
		fetchRequest.with("actorRecipients");
		fetchRequest.with("roleRecipients");

		fetchRequest.to(new Receiver<NotificationProxy>() {
			@Override
			public void onSuccess(NotificationProxy entity) {
				viewNotification(entity);
			}
		}).fire();
	}

	/**
	 * Display the current instance of Notification in editor
	 * @param entity the NotificationProxy to be displayed
	 */
	private void viewNotification(NotificationProxy entity) {

		/* display instance information */
		setTitle(AdminNLS.constants().notification_name() + ": " + AdminRenderer.get().getDisplayValue(entity));
		setMetaData((ImogBeanProxy) entity);

		/* push the instance to the editor in view mode */
		request = requestFactory.notificationRequest();
		current = request.edit(entity);

		editorDriver.edit(current, request);
		editor.setEdited(false);

		/* update field widgets in editor */
		editor.computeVisibility(null, true);
	}

	/**
	 * Edit the current instance of Notification in editor
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
	 * Persist the current instance of Notification
	 */
	@Override
	protected void save() {

		editorDriver.flush();

		// Check for errors on the client side
		if (editorDriver.hasErrors()) {
			// Window.alert("Notification form not validated locally");
			return;
		}

		Request<Void> saveRequest = request.save(current, isNew);
		saveRequest.to(new Receiver<Void>() {
			@Override
			public void onSuccess(Void response) {
				requestFactory.getEventBus().fireEvent(new SaveNotificationEvent(current, initField));
				closeForm();
			}

			@Override
			public void onConstraintViolation(Set<ConstraintViolation<?>> errors) {
				// Window.alert("Notification form not validated on server");

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
				Window.alert("Error updating the Notification");
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
				requestFactory.getEventBus().fireEvent(new GoHomeEvent());
			else
				requestFactory.getEventBus().fireEvent(new ViewNotificationEvent(current.getId()));
		}

	}

	@Override
	protected void returnToList() {
		requestFactory.getEventBus().fireEvent(new ListNotificationEvent());
	}

}
