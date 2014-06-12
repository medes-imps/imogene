package org.imogene.admin.client.ui.editor;

import java.util.ArrayList;
import java.util.List;

import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.admin.client.ui.editor.nested.ProfileEntityProfilesListEditor;
import org.imogene.admin.client.ui.editor.nested.ProfileFieldGroupProfilesListEditor;
import org.imogene.admin.shared.AdminRequestFactory;
import org.imogene.lib.common.profile.Profile;
import org.imogene.web.client.event.FieldValueChangeEvent;
import org.imogene.web.client.ui.field.ImogField;
import org.imogene.web.client.ui.field.ImogTextBox;
import org.imogene.web.client.ui.field.group.FieldGroupPanel;
import org.imogene.web.client.util.ImogBeanRenderer;
import org.imogene.web.client.util.ProfileUtil;
import org.imogene.web.shared.proxy.ProfileProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorDelegate;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.HasEditorDelegate;
import com.google.gwt.editor.client.HasEditorErrors;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * Editor that provides the UI components that allow a ProfileProxy to be viewed and edited
 * @author MEDES-IMPS
 */
public class ProfileEditor extends Composite implements Editor<ProfileProxy>, HasEditorDelegate<ProfileProxy>, HasEditorErrors<ProfileProxy> {

	interface Binder extends UiBinder<Widget, ProfileEditor> {
	}

	private static final Binder BINDER = GWT.create(Binder.class);

	protected final AdminRequestFactory requestFactory;
	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();
	private EditorDelegate<ProfileProxy> delegate;

	private ProfileProxy editedValue; // Not used by the editor

	/* Description section widgets */
	@UiField
	@Ignore
	FieldGroupPanel descriptionSection;
	@UiField
	ImogTextBox name;
	@UiField(provided = true)
	ProfileEntityProfilesListEditor entityProfiles;
	@UiField(provided = true)
	ProfileFieldGroupProfilesListEditor fieldGroupProfiles;

	/**
	 * Constructor
	 * @param factory the application request factory
	 * @param hideButtons true if the relation field buttons shall be hidden
	 */
	public ProfileEditor(AdminRequestFactory factory, boolean hideButtons, ImogBeanRenderer renderer) {

		this.requestFactory = factory;

		setRelationFields(renderer);

		initWidget(BINDER.createAndBindUi(this));

		properties();
	}

	/**
	 * Constructor
	 * @param factory the application request factory
	 */
	public ProfileEditor(AdminRequestFactory factory, ImogBeanRenderer renderer) {
		this(factory, false, renderer);
	}

	/**
	 * Sets the properties of the fields
	 */
	private void properties() {

		/* Description section widgets */
		descriptionSection.setGroupTitle(AdminNLS.constants().profile_group_description());
		name.setLabel(AdminNLS.constants().profile_field_name());

	}

	/**
	 * Configures the widgets that manage relation fields
	 */
	private void setRelationFields(ImogBeanRenderer renderer) {

		/* field entityProfiles */
		entityProfiles = new ProfileEntityProfilesListEditor(requestFactory, renderer);

		/* field fieldGroupProfiles */
		fieldGroupProfiles = new ProfileFieldGroupProfilesListEditor(requestFactory, renderer);

	}

	/**
	 * Sets the edition mode
	 * @param isEdited true to enable the edition of the form
	 */
	public void setEdited(boolean isEdited) {

		if (isEdited)
			setFieldEditAccess();
		else
			setFieldReadAccess();

		/* Description section widgets */
		name.setEdited(isEdited);
		
		if(editedValue!=null && editedValue.getId()!=null && editedValue.getId().equals(Profile.ADMINISTRATOR)) {
			entityProfiles.setVisible(false);
			fieldGroupProfiles.setVisible(false);
		}
		else {
			entityProfiles.setEdited(isEdited);
			fieldGroupProfiles.setEdited(isEdited);			
		}
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

		registrations.add(requestFactory.getEventBus().addHandler(FieldValueChangeEvent.TYPE, new FieldValueChangeEvent.Handler() {
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
//		entityProfiles.computeVisibility(source, allValidation);
//		fieldGroupProfiles.computeVisibility(source, allValidation);

	}

	/**
	 * Configures the handlers of the widgets that manage relation fields
	 */
	private void setRelationHandlers() {

	}

	/**
	 * Gets the ProfileProxy that is edited in the Workflow Not used by the editor Temporary storage used to transmit the proxy to related entities
	 * @return
	 */
	public ProfileProxy getEditedValue() {
		return editedValue;
	}

	/**
	 * Sets the ProfileProxy that is edited in the Workflow Not used by the editor Temporary storage used to transmit the proxy to related entities
	 * @param editedValue
	 */
	public void setEditedValue(ProfileProxy editedValue) {
		this.editedValue = editedValue;
	}

	/**
	 * Validate fields values
	 */
	public void validateFields() {

		// entityProfiles nested form shall be validated
		entityProfiles.validateFields();
		// fieldGroupProfiles nested form shall be validated
		fieldGroupProfiles.validateFields();
	}

	@Override
	public void setDelegate(EditorDelegate<ProfileProxy> delegate) {
		this.delegate = delegate;
	}

	@Override
	public void showErrors(List<EditorError> errors) {
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
