package org.imogene.admin.client.ui.editor;

import java.util.ArrayList;
import java.util.List;

import org.imogene.admin.client.AdminRenderer;
import org.imogene.admin.client.dataprovider.EntityProfileDataProvider;
import org.imogene.admin.client.dataprovider.FieldGroupProfileDataProvider;
import org.imogene.admin.client.event.save.SaveEntityProfileEvent;
import org.imogene.admin.client.event.save.SaveFieldGroupProfileEvent;
import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.admin.client.ui.filter.EntityProfileFilterPanel;
import org.imogene.admin.client.ui.filter.FieldGroupProfileFilterPanel;
import org.imogene.admin.client.ui.workflow.panel.EntityProfileFormPanel;
import org.imogene.admin.client.ui.workflow.panel.FieldGroupProfileFormPanel;
import org.imogene.admin.shared.AdminRequestFactory;
import org.imogene.web.client.event.FieldValueChangeEvent;
import org.imogene.web.client.ui.field.ImogField;
import org.imogene.web.client.ui.field.ImogTextBox;
import org.imogene.web.client.ui.field.group.FieldGroupPanel;
import org.imogene.web.client.ui.field.relation.multi.ImogMultiRelationBox;
import org.imogene.web.client.ui.panel.RelationPopupPanel;
import org.imogene.web.client.util.ImogRoleUtil;
import org.imogene.web.shared.proxy.EntityProfileProxy;
import org.imogene.web.shared.proxy.FieldGroupProfileProxy;
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
 * Editor that provides the UI components that allow a ProfileProxy to be viewed and edited
 * 
 * @author MEDES-IMPS
 */
public class ProfileEditor extends Composite implements Editor<ProfileProxy>, HasEditorDelegate<ProfileProxy>,
		HasEditorErrors<ProfileProxy> {

	interface Binder extends UiBinder<Widget, ProfileEditor> {
	}

	private static final Binder BINDER = GWT.create(Binder.class);

	protected final AdminRequestFactory requestFactory;
	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();
	private EditorDelegate<ProfileProxy> delegate;

	private ProfileProxy editedValue; // Not used by the editor
	private boolean hideButtons = false;

	/* Description section widgets */
	@UiField
	@Ignore
	FieldGroupPanel descriptionSection;
	@UiField
	ImogTextBox name;
	@UiField(provided = true)
	ImogMultiRelationBox<EntityProfileProxy> entityProfiles;
	@UiField(provided = true)
	ImogMultiRelationBox<FieldGroupProfileProxy> fieldGroupProfiles;

	/**
	 * Constructor
	 * 
	 * @param factory the application request factory
	 * @param hideButtons true if the relation field buttons shall be hidden
	 */
	public ProfileEditor(AdminRequestFactory factory, boolean hideButtons) {

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
	public ProfileEditor(AdminRequestFactory factory) {
		this(factory, false);
	}

	/**
	 * Sets the properties of the fields
	 */
	private void properties() {

		/* Description section widgets */
		descriptionSection.setGroupTitle(AdminNLS.constants().profile_group_description());
		name.setLabel(AdminNLS.constants().profile_field_name());
		entityProfiles.setLabel(AdminNLS.constants().profile_field_entityProfiles());
		fieldGroupProfiles.setLabel(AdminNLS.constants().profile_field_fieldGroupProfiles());

	}

	/**
	 * Configures the widgets that manage relation fields
	 */
	private void setRelationFields() {

		/* field entityProfiles */
		EntityProfileDataProvider entityProfilesDataProvider;
		entityProfilesDataProvider = new EntityProfileDataProvider(requestFactory, "profile");
		if (hideButtons) // in popup, relation buttons hidden
			entityProfiles = new ImogMultiRelationBox<EntityProfileProxy>(entityProfilesDataProvider,
					AdminRenderer.get(), true);
		else {// in wrapper panel, relation buttons enabled
			if (ImogRoleUtil.isAdmin())
				entityProfiles = new ImogMultiRelationBox<EntityProfileProxy>(entityProfilesDataProvider,
						AdminRenderer.get(), null);
			else
				entityProfiles = new ImogMultiRelationBox<EntityProfileProxy>(false, entityProfilesDataProvider,
						AdminRenderer.get(), null);
		}
		entityProfiles.setPopUpTitle(AdminNLS.constants().entityProfile_select_title());
		entityProfiles.setFilterPanel(new EntityProfileFilterPanel());

		/* field fieldGroupProfiles */
		FieldGroupProfileDataProvider fieldGroupProfilesDataProvider;
		fieldGroupProfilesDataProvider = new FieldGroupProfileDataProvider(requestFactory, "profile");
		if (hideButtons) // in popup, relation buttons hidden
			fieldGroupProfiles = new ImogMultiRelationBox<FieldGroupProfileProxy>(fieldGroupProfilesDataProvider,
					AdminRenderer.get(), true);
		else {// in wrapper panel, relation buttons enabled
			if (ImogRoleUtil.isAdmin())
				fieldGroupProfiles = new ImogMultiRelationBox<FieldGroupProfileProxy>(fieldGroupProfilesDataProvider,
						AdminRenderer.get(), null);
			else
				fieldGroupProfiles = new ImogMultiRelationBox<FieldGroupProfileProxy>(false,
						fieldGroupProfilesDataProvider, AdminRenderer.get(), null);
		}
		fieldGroupProfiles.setPopUpTitle(AdminNLS.constants().fieldGroupProfile_select_title());
		fieldGroupProfiles.setFilterPanel(new FieldGroupProfileFilterPanel());

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
		name.setEdited(isEdited);
		entityProfiles.setEdited(isEdited);
		fieldGroupProfiles.setEdited(isEdited);

	}

	/**
	 * Configures the visibility of the fields in view mode depending on the user privileges
	 */
	private void setFieldReadAccess() {

		/* Description section widgets visibility */
		if (!ImogRoleUtil.isAdmin())
			descriptionSection.setVisible(false);

	}

	/**
	 * Configures the visibility of the fields in edit mode depending on the user privileges
	 */
	private void setFieldEditAccess() {

		/* Description section widgets visibility */
		if (!ImogRoleUtil.isAdmin())
			descriptionSection.setVisible(false);

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
	 * Configures the handlers of the widgets that manage relation fields
	 */
	private void setRelationHandlers() {

		/* 'Information' button for field EntityProfiles */
		entityProfiles.setViewClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				if (!entityProfiles.isEmpty() && entityProfiles.getSelectedEntities().size() > 0) {

					EntityProfileProxy value = entityProfiles.getSelectedEntities().get(0);
					RelationPopupPanel relationPopup = new RelationPopupPanel();
					EntityProfileFormPanel form = new EntityProfileFormPanel(requestFactory, value.getId(),
							relationPopup, "entityProfiles");
					relationPopup.addWidget(form);
					relationPopup.show();
				}
			}
		});

		/* 'Add' button for field EntityProfiles */
		entityProfiles.setAddClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				RelationPopupPanel relationPopup = new RelationPopupPanel();
				EntityProfileFormPanel form = new EntityProfileFormPanel(requestFactory, null, relationPopup,
						"entityProfiles");
				form.setProfile(editedValue, true);
				/* common fields */

				relationPopup.addWidget(form);
				relationPopup.show();
			}
		});

		/* SaveEvent handler when a EntityProfile is created or updated from the relation field EntityProfiles */
		registrations.add(requestFactory.getEventBus().addHandler(SaveEntityProfileEvent.TYPE,
				new SaveEntityProfileEvent.Handler() {
					@Override
					public void saveEntityProfile(EntityProfileProxy value) {
						if (!entityProfiles.isPresent(value))
							entityProfiles.addValue(value);
					}

					public void saveEntityProfile(EntityProfileProxy value, String initField) {
						if (initField.equals("entityProfiles")) {
							if (entityProfiles.isPresent(value))
								entityProfiles.replaceValue(value);
							else
								entityProfiles.addValue(value, true);
						}
					}
				}));

		/* 'Information' button for field FieldGroupProfiles */
		fieldGroupProfiles.setViewClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				if (!fieldGroupProfiles.isEmpty() && fieldGroupProfiles.getSelectedEntities().size() > 0) {

					FieldGroupProfileProxy value = fieldGroupProfiles.getSelectedEntities().get(0);
					RelationPopupPanel relationPopup = new RelationPopupPanel();
					FieldGroupProfileFormPanel form = new FieldGroupProfileFormPanel(requestFactory, value.getId(),
							relationPopup, "fieldGroupProfiles");
					relationPopup.addWidget(form);
					relationPopup.show();
				}
			}
		});

		/* 'Add' button for field FieldGroupProfiles */
		fieldGroupProfiles.setAddClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				RelationPopupPanel relationPopup = new RelationPopupPanel();
				FieldGroupProfileFormPanel form = new FieldGroupProfileFormPanel(requestFactory, null, relationPopup,
						"fieldGroupProfiles");
				form.setProfile(editedValue, true);
				/* common fields */

				relationPopup.addWidget(form);
				relationPopup.show();
			}
		});

		/* SaveEvent handler when a FieldGroupProfile is created or updated from the relation field FieldGroupProfiles */
		registrations.add(requestFactory.getEventBus().addHandler(SaveFieldGroupProfileEvent.TYPE,
				new SaveFieldGroupProfileEvent.Handler() {
					@Override
					public void saveFieldGroupProfile(FieldGroupProfileProxy value) {
						if (!fieldGroupProfiles.isPresent(value))
							fieldGroupProfiles.addValue(value);
					}

					public void saveFieldGroupProfile(FieldGroupProfileProxy value, String initField) {
						if (initField.equals("fieldGroupProfiles")) {
							if (fieldGroupProfiles.isPresent(value))
								fieldGroupProfiles.replaceValue(value);
							else
								fieldGroupProfiles.addValue(value, true);
						}
					}
				}));

	}

	/**
	 * Gets the ProfileProxy that is edited in the Workflow Not used by the editor Temporary storage used to transmit
	 * the proxy to related entities
	 * 
	 * @return
	 */
	public ProfileProxy getEditedValue() {
		return editedValue;
	}

	/**
	 * Sets the ProfileProxy that is edited in the Workflow Not used by the editor Temporary storage used to transmit
	 * the proxy to related entities
	 * 
	 * @param editedValue
	 */
	public void setEditedValue(ProfileProxy editedValue) {
		this.editedValue = editedValue;
	}

	/**
	 * Validate fields values
	 */
	public void validateFields() {

	}

	/**
	 */
	private void setAllLabelWith(String width) {

		/* Description field group */
		name.setLabelWidth(width);
		entityProfiles.setLabelWidth(width);
		fieldGroupProfiles.setLabelWidth(width);

	}

	/**
	 */
	private void setAllBoxWith(String width) {

		/* Description field group */
		name.setBoxWidth(width);
		entityProfiles.setBoxWidth(width);
		fieldGroupProfiles.setBoxWidth(width);

	}

	@Override
	public void setDelegate(EditorDelegate<ProfileProxy> delegate) {
		this.delegate = delegate;
	}

	@Override
	public void showErrors(List<EditorError> errors) {
		if (errors != null && errors.size() > 0) {

			for (EditorError error : errors) {
				Object userData = error.getUserData();
				if (userData != null && userData instanceof String) {
					String field = (String) userData;

				}
			}
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
