package org.imogene.admin.client.ui.editor;

import java.util.ArrayList;
import java.util.List;

import org.imogene.admin.client.AdminRenderer;
import org.imogene.admin.client.dataprovider.CardEntityDataProvider;
import org.imogene.admin.client.dataprovider.ProfileDataProvider;
import org.imogene.admin.client.event.save.SaveCardEntityEvent;
import org.imogene.admin.client.event.save.SaveProfileEvent;
import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.admin.client.ui.workflow.panel.CardEntityFormPanel;
import org.imogene.admin.client.ui.workflow.panel.ProfileFormPanel;
import org.imogene.admin.shared.AdminRequestFactory;
import org.imogene.web.client.event.FieldValueChangeEvent;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.field.ImogBooleanBox;
import org.imogene.web.client.ui.field.ImogField;
import org.imogene.web.client.ui.field.group.FieldGroupPanel;
import org.imogene.web.client.ui.field.relation.single.ImogSingleRelationBox;
import org.imogene.web.client.ui.panel.RelationPopupPanel;
import org.imogene.web.client.util.ProfileUtil;
import org.imogene.web.shared.proxy.CardEntityProxy;
import org.imogene.web.shared.proxy.EntityProfileProxy;
import org.imogene.web.shared.proxy.ProfileProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorDelegate;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.HasEditorDelegate;
import com.google.gwt.editor.client.HasEditorErrors;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * Editor that provides the UI components that allow a EntityProfileProxy to be viewed and edited
 * 
 * @author MEDES-IMPS
 */
public class EntityProfileEditor extends Composite implements Editor<EntityProfileProxy>,
		HasEditorDelegate<EntityProfileProxy>, HasEditorErrors<EntityProfileProxy> {

	interface Binder extends UiBinder<Widget, EntityProfileEditor> {
	}

	private static final Binder BINDER = GWT.create(Binder.class);

	protected final AdminRequestFactory requestFactory;
	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();
	private EditorDelegate<EntityProfileProxy> delegate;

	private EntityProfileProxy editedValue; // Not used by the editor
	private boolean hideButtons = false;

	/* Description section widgets */
	@UiField
	@Ignore
	FieldGroupPanel descriptionSection;
	@UiField(provided = true)
	ImogSingleRelationBox<ProfileProxy> profile;
	@UiField(provided = true)
	ImogSingleRelationBox<CardEntityProxy> entity;
	@UiField
	ImogBooleanBox create;
	@UiField
	ImogBooleanBox directAccess;
	@UiField
	ImogBooleanBox delete;
	@UiField
	ImogBooleanBox export;

	/**
	 * Constructor
	 * 
	 * @param factory the application request factory
	 * @param hideButtons true if the relation field buttons shall be hidden
	 */
	public EntityProfileEditor(AdminRequestFactory factory, boolean hideButtons) {

		this.requestFactory = factory;
		this.hideButtons = hideButtons;

		setRelationFields();

		initWidget(BINDER.createAndBindUi(this));

		properties();
	}

	/**
	 * Constructor
	 * 
	 * @param factory the application request factory
	 */
	public EntityProfileEditor(AdminRequestFactory factory) {
		this(factory, false);
	}

	/**
	 * Sets the properties of the fields
	 */
	private void properties() {

		/* Description section widgets */
		descriptionSection.setGroupTitle(AdminNLS.constants().entityProfile_group_description());
		profile.setLabel(AdminNLS.constants().entityProfile_field_profile());
		entity.setLabel(AdminNLS.constants().entityProfile_field_entity());
		create.setLabel(AdminNLS.constants().entityProfile_field_create());
		directAccess.setLabel(AdminNLS.constants().entityProfile_field_directAccess());
		delete.setLabel(AdminNLS.constants().entityProfile_field_delete());
		export.setLabel(AdminNLS.constants().entityProfile_field_export());

	}

	/**
	 * Configures the widgets that manage relation fields
	 */
	private void setRelationFields() {
		/* field profile */
		ProfileDataProvider profileDataProvider;
		profileDataProvider = new ProfileDataProvider(requestFactory);
		if (hideButtons) // in popup, relation buttons hidden
			profile = new ImogSingleRelationBox<ProfileProxy>(profileDataProvider, AdminRenderer.get(), true);
		else {// in wrapper panel, relation buttons enabled
			if (ProfileUtil.isAdmin()) {
				profile = new ImogSingleRelationBox<ProfileProxy>(profileDataProvider, AdminRenderer.get());
			} else {
				profile = new ImogSingleRelationBox<ProfileProxy>(false, profileDataProvider, AdminRenderer.get());
			}
		}

		/* field entity */
		CardEntityDataProvider entityDataProvider;
		entityDataProvider = new CardEntityDataProvider(requestFactory);
		if (hideButtons) // in popup, relation buttons hidden
			entity = new ImogSingleRelationBox<CardEntityProxy>(entityDataProvider, AdminRenderer.get(), true);
		else {// in wrapper panel, relation buttons enabled
			if (ProfileUtil.isAdmin()) {
				entity = new ImogSingleRelationBox<CardEntityProxy>(entityDataProvider, AdminRenderer.get());
			} else {
				entity = new ImogSingleRelationBox<CardEntityProxy>(false, entityDataProvider, AdminRenderer.get());
			}
		}

	}

	/**
	 * Sets the edition mode
	 * 
	 * @param isEdited true to enable the edition of the form
	 */
	public void setEdited(boolean isEdited) {

		if (isEdited)
			setFieldEditAccess();
		else
			setFieldReadAccess();

		/* Description section widgets */
		profile.setEdited(isEdited);
		entity.setEdited(isEdited);
		create.setEdited(isEdited);
		directAccess.setEdited(isEdited);
		delete.setEdited(isEdited);
		export.setEdited(isEdited);

	}

	private void setFieldReadAccess() {
		if (!ProfileUtil.isAdmin()) {
			descriptionSection.setVisible(false);
		}
	}

	/**
	 * Configures the visibility of the fields in edit mode depending on the user privileges
	 */
	private void setFieldEditAccess() {
		if (!ProfileUtil.isAdmin()) {
			descriptionSection.setVisible(false);
		}
	}

	/**
	 * Manages editor updates when a field value changes
	 */
	private void setFieldValueChangeHandler() {

		registrations.add(requestFactory.getEventBus().addHandler(FieldValueChangeEvent.TYPE,
				new FieldValueChangeEvent.Handler() {
					@Override
					public void onValueChange(ImogField<?> field) {

						// field dependent visibility management
						computeVisibility(field, false);

					}
				}));
	}

	/**
	 * Computes the field visibility
	 */
	public void computeVisibility(ImogField<?> source, boolean allValidation) {

	}

	/**
	 * Setter to inject a Profile value
	 * 
	 * @param value the value to be injected into the editor
	 * @param isLocked true if relation field shall be locked (non editable)
	 */
	public void setProfile(ProfileProxy value, boolean isLocked) {
		profile.setLocked(isLocked);
		profile.setValue(value);

	}

	/**
	 * Setter to inject a SynchronizableEntity value
	 * 
	 * @param value the value to be injected into the editor
	 * @param isLocked true if relation field shall be locked (non editable)
	 */
	public void setEntity(CardEntityProxy value, boolean isLocked) {
		entity.setLocked(isLocked);
		entity.setValue(value);

	}

	/**
	 * Configures the handlers of the widgets that manage relation fields
	 */
	private void setRelationHandlers() {

		/* 'Information' button for field Profile */
		profile.setViewClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (profile.getValue() != null) {
					RelationPopupPanel relationPopup = new RelationPopupPanel();
					ProfileFormPanel form = new ProfileFormPanel(requestFactory, profile.getValue().getId(),
							relationPopup, "profile");
					relationPopup.addWidget(form);
					relationPopup.show();
				}
			}
		});

		/* 'Add' button for field Profile */
		profile.setAddClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				RelationPopupPanel relationPopup = new RelationPopupPanel();
				ProfileFormPanel form = new ProfileFormPanel(requestFactory, null, relationPopup, "profile");
				/* common fields */

				relationPopup.addWidget(form);
				relationPopup.show();
			}
		});

		/* SaveEvent handler when a Profile is created or updated from the relation field Profile */
		registrations.add(requestFactory.getEventBus().addHandler(SaveProfileEvent.TYPE,
				new SaveProfileEvent.Handler() {
					@Override
					public void saveProfile(ProfileProxy value) {
						profile.setValue(value);
					}

					@Override
					public void saveProfile(ProfileProxy value, String initField) {
						if (initField.equals("profile"))
							profile.setValue(value, true);
					}
				}));

		/* 'Information' button for field Entity */
		entity.setViewClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (entity.getValue() != null) {
					RelationPopupPanel relationPopup = new RelationPopupPanel();
					CardEntityFormPanel form = new CardEntityFormPanel(requestFactory, entity.getValue()
							.getId(), relationPopup, "entity");
					relationPopup.addWidget(form);
					relationPopup.show();
				}
			}
		});

		/* 'Add' button for field Entity */
		entity.setAddClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				RelationPopupPanel relationPopup = new RelationPopupPanel();
				CardEntityFormPanel form = new CardEntityFormPanel(requestFactory, null, relationPopup,
						"entity");
				/* common fields */

				relationPopup.addWidget(form);
				relationPopup.show();
			}
		});

		/* SaveEvent handler when a SynchronizableEntity is created or updated from the relation field Entity */
		registrations.add(requestFactory.getEventBus().addHandler(SaveCardEntityEvent.TYPE,
				new SaveCardEntityEvent.Handler() {
					@Override
					public void saveCardEntity(CardEntityProxy value) {
						entity.setValue(value);
					}

					@Override
					public void saveCardEntity(CardEntityProxy value, String initField) {
						if (initField.equals("entity"))
							entity.setValue(value, true);
					}
				}));

	}

	/**
	 * Gets the EntityProfileProxy that is edited in the Workflow Not used by the editor Temporary storage used to
	 * transmit the proxy to related entities
	 * 
	 * @return
	 */
	public EntityProfileProxy getEditedValue() {
		return editedValue;
	}

	/**
	 * Sets the EntityProfileProxy that is edited in the Workflow Not used by the editor Temporary storage used to
	 * transmit the proxy to related entities
	 * 
	 * @param editedValue
	 */
	public void setEditedValue(EntityProfileProxy editedValue) {
		this.editedValue = editedValue;
	}

	/**
	 * Validate fields values
	 */
	public void validateFields() {

		// profile is a required field
		if (profile.getValue() == null)
			delegate.recordError(BaseNLS.messages().error_required(), null, "profile");
		// entity is a required field
		if (entity.getValue() == null)
			delegate.recordError(BaseNLS.messages().error_required(), null, "entity");
	}

	/**
	 */
	private void setAllLabelWith(String width) {

		/* Description field group */
		profile.setLabelWidth(width);
		entity.setLabelWidth(width);
		create.setLabelWidth(width);
		directAccess.setLabelWidth(width);
		delete.setLabelWidth(width);
		export.setLabelWidth(width);

	}

	/**
	 */
	private void setAllBoxWith(String width) {

		/* Description field group */
		profile.setBoxWidth(width);
		entity.setBoxWidth(width);

	}

	@Override
	public void setDelegate(EditorDelegate<EntityProfileProxy> delegate) {
		this.delegate = delegate;
	}

	@Override
	public void showErrors(List<EditorError> errors) {
		if (errors != null && errors.size() > 0) {

			List<EditorError> profileFieldErrors = new ArrayList<EditorError>();
			List<EditorError> entityFieldErrors = new ArrayList<EditorError>();

			for (EditorError error : errors) {
				Object userData = error.getUserData();
				if (userData != null && userData instanceof String) {
					String field = (String) userData;

					if (field.equals("profile"))
						profileFieldErrors.add(error);
					if (field.equals("entity"))
						entityFieldErrors.add(error);

				}
			}
			if (profileFieldErrors.size() > 0)
				profile.showErrors(profileFieldErrors);
			if (entityFieldErrors.size() > 0)
				entity.showErrors(entityFieldErrors);
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
		setRelationHandlers();
		setFieldValueChangeHandler();
		super.onLoad();
	}
}
