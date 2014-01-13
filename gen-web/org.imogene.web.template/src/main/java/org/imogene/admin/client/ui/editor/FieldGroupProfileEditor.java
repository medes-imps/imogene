package org.imogene.admin.client.ui.editor;

import java.util.ArrayList;
import java.util.List;

import org.imogene.admin.client.AdminRenderer;
import org.imogene.admin.client.dataprovider.FieldGroupDataProvider;
import org.imogene.admin.client.dataprovider.ProfileDataProvider;
import org.imogene.admin.client.event.save.SaveFieldGroupEvent;
import org.imogene.admin.client.event.save.SaveProfileEvent;
import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.admin.client.ui.workflow.panel.FieldGroupFormPanel;
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
import org.imogene.web.shared.proxy.FieldGroupProfileProxy;
import org.imogene.web.shared.proxy.FieldGroupProxy;
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
 * Editor that provides the UI components that allow a FieldGroupProfileProxy to be viewed and edited
 * 
 * @author MEDES-IMPS
 */
public class FieldGroupProfileEditor extends Composite implements Editor<FieldGroupProfileProxy>,
		HasEditorDelegate<FieldGroupProfileProxy>, HasEditorErrors<FieldGroupProfileProxy> {

	interface Binder extends UiBinder<Widget, FieldGroupProfileEditor> {
	}

	private static final Binder BINDER = GWT.create(Binder.class);

	protected final AdminRequestFactory requestFactory;
	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();
	private EditorDelegate<FieldGroupProfileProxy> delegate;

	private FieldGroupProfileProxy editedValue; // Not used by the editor
	private boolean hideButtons = false;

	/* Description section widgets */
	@UiField
	@Ignore
	FieldGroupPanel descriptionSection;
	@UiField(provided = true)
	ImogSingleRelationBox<ProfileProxy> profile;
	@UiField(provided = true)
	ImogSingleRelationBox<FieldGroupProxy> fieldGroup;
	@UiField
	ImogBooleanBox read;
	@UiField
	ImogBooleanBox write;
	@UiField
	ImogBooleanBox export;

	/**
	 * Constructor
	 * 
	 * @param factory the application request factory
	 * @param hideButtons true if the relation field buttons shall be hidden
	 */
	public FieldGroupProfileEditor(AdminRequestFactory factory, boolean hideButtons) {

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
	public FieldGroupProfileEditor(AdminRequestFactory factory) {
		this(factory, false);
	}

	/**
	 * Sets the properties of the fields
	 */
	private void properties() {

		/* Description section widgets */
		descriptionSection.setGroupTitle(AdminNLS.constants().fieldGroupProfile_group_description());
		profile.setLabel(AdminNLS.constants().fieldGroupProfile_field_profile());
		fieldGroup.setLabel(AdminNLS.constants().fieldGroupProfile_field_fieldGroup());
		read.setLabel(AdminNLS.constants().fieldGroupProfile_field_read());
		write.setLabel(AdminNLS.constants().fieldGroupProfile_field_write());
		export.setLabel(AdminNLS.constants().fieldGroupProfile_field_export());

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
			if (ProfileUtil.isAdmin())
				profile = new ImogSingleRelationBox<ProfileProxy>(profileDataProvider, AdminRenderer.get());
			else
				profile = new ImogSingleRelationBox<ProfileProxy>(false, profileDataProvider, AdminRenderer.get());
		}

		/* field fieldGroup */
		FieldGroupDataProvider fieldGroupDataProvider;
		fieldGroupDataProvider = new FieldGroupDataProvider(requestFactory);
		if (hideButtons) // in popup, relation buttons hidden
			fieldGroup = new ImogSingleRelationBox<FieldGroupProxy>(fieldGroupDataProvider, AdminRenderer.get(), true);
		else {// in wrapper panel, relation buttons enabled
			if (ProfileUtil.isAdmin())
				fieldGroup = new ImogSingleRelationBox<FieldGroupProxy>(fieldGroupDataProvider, AdminRenderer.get());
			else
				fieldGroup = new ImogSingleRelationBox<FieldGroupProxy>(false, fieldGroupDataProvider,
						AdminRenderer.get());
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
		fieldGroup.setEdited(isEdited);
		read.setEdited(isEdited);
		write.setEdited(isEdited);
		export.setEdited(isEdited);

	}

	/**
	 * Configures the visibility of the fields in view mode depending on the user privileges
	 */
	private void setFieldReadAccess() {

		/* Description section widgets visibility */
		if (!ProfileUtil.isAdmin())
			descriptionSection.setVisible(false);

	}

	/**
	 * Configures the visibility of the fields in edit mode depending on the user privileges
	 */
	private void setFieldEditAccess() {

		/* Description section widgets visibility */
		if (!ProfileUtil.isAdmin())
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
	 * Setter to inject a FieldGroup value
	 * 
	 * @param value the value to be injected into the editor
	 * @param isLocked true if relation field shall be locked (non editable)
	 */
	public void setFieldGroup(FieldGroupProxy value, boolean isLocked) {
		fieldGroup.setLocked(isLocked);
		fieldGroup.setValue(value);

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

		/* 'Information' button for field FieldGroup */
		fieldGroup.setViewClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (fieldGroup.getValue() != null) {
					RelationPopupPanel relationPopup = new RelationPopupPanel();
					FieldGroupFormPanel form = new FieldGroupFormPanel(requestFactory, fieldGroup.getValue().getId(),
							relationPopup, "fieldGroup");
					relationPopup.addWidget(form);
					relationPopup.show();
				}
			}
		});

		/* 'Add' button for field FieldGroup */
		fieldGroup.setAddClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				RelationPopupPanel relationPopup = new RelationPopupPanel();
				FieldGroupFormPanel form = new FieldGroupFormPanel(requestFactory, null, relationPopup, "fieldGroup");
				/* common fields */

				relationPopup.addWidget(form);
				relationPopup.show();
			}
		});

		/* SaveEvent handler when a FieldGroup is created or updated from the relation field FieldGroup */
		registrations.add(requestFactory.getEventBus().addHandler(SaveFieldGroupEvent.TYPE,
				new SaveFieldGroupEvent.Handler() {
					@Override
					public void saveFieldGroup(FieldGroupProxy value) {
						fieldGroup.setValue(value);
					}

					@Override
					public void saveFieldGroup(FieldGroupProxy value, String initField) {
						if (initField.equals("fieldGroup"))
							fieldGroup.setValue(value, true);
					}
				}));

	}

	/**
	 * Gets the FieldGroupProfileProxy that is edited in the Workflow Not used by the editor Temporary storage used to
	 * transmit the proxy to related entities
	 * 
	 * @return
	 */
	public FieldGroupProfileProxy getEditedValue() {
		return editedValue;
	}

	/**
	 * Sets the FieldGroupProfileProxy that is edited in the Workflow Not used by the editor Temporary storage used to
	 * transmit the proxy to related entities
	 * 
	 * @param editedValue
	 */
	public void setEditedValue(FieldGroupProfileProxy editedValue) {
		this.editedValue = editedValue;
	}

	/**
	 * Validate fields values
	 */
	public void validateFields() {

		// profile is a required field
		if (profile.getValue() == null)
			delegate.recordError(BaseNLS.messages().error_required(), null, "profile");
		// fieldGroup is a required field
		if (fieldGroup.getValue() == null)
			delegate.recordError(BaseNLS.messages().error_required(), null, "fieldGroup");
	}

	/**
	 */
	private void setAllLabelWith(String width) {

		/* Description field group */
		profile.setLabelWidth(width);
		fieldGroup.setLabelWidth(width);
		read.setLabelWidth(width);
		write.setLabelWidth(width);
		export.setLabelWidth(width);

	}

	/**
	 */
	private void setAllBoxWith(String width) {

		/* Description field group */
		profile.setBoxWidth(width);
		fieldGroup.setBoxWidth(width);

	}

	@Override
	public void setDelegate(EditorDelegate<FieldGroupProfileProxy> delegate) {
		this.delegate = delegate;
	}

	@Override
	public void showErrors(List<EditorError> errors) {
		if (errors != null && errors.size() > 0) {

			List<EditorError> profileFieldErrors = new ArrayList<EditorError>();
			List<EditorError> fieldGroupFieldErrors = new ArrayList<EditorError>();

			for (EditorError error : errors) {
				Object userData = error.getUserData();
				if (userData != null && userData instanceof String) {
					String field = (String) userData;

					if (field.equals("profile"))
						profileFieldErrors.add(error);
					if (field.equals("fieldGroup"))
						fieldGroupFieldErrors.add(error);

				}
			}
			if (profileFieldErrors.size() > 0)
				profile.showErrors(profileFieldErrors);
			if (fieldGroupFieldErrors.size() > 0)
				fieldGroup.showErrors(fieldGroupFieldErrors);
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
