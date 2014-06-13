package org.imogene.admin.client.ui.editor.nested;

import java.util.ArrayList;
import java.util.List;

import org.imogene.admin.client.dataprovider.CardEntityDataProvider;
import org.imogene.admin.shared.AdminRequestFactory;
import org.imogene.web.client.event.FieldValueChangeEvent;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.field.ImogCheckBox;
import org.imogene.web.client.ui.field.ImogField;
import org.imogene.web.client.ui.field.relation.single.ImogSingleRelationBox;
import org.imogene.web.client.util.ImogBeanRenderer;
import org.imogene.web.client.util.ProfileUtil;
import org.imogene.web.shared.proxy.CardEntityProxy;
import org.imogene.web.shared.proxy.EntityProfileProxy;

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
 * Editor that provides the UI components that allow a EntityProfileProxy to be viewed and edited in an editor list
 * @author MEDES-IMPS
 */
public class EntityProfileEditorNestedRow extends Composite implements Editor<EntityProfileProxy>, HasEditorDelegate<EntityProfileProxy>, HasEditorErrors<EntityProfileProxy> {

	interface Binder extends UiBinder<Widget, EntityProfileEditorNestedRow> {
	}

	private static final Binder BINDER = GWT.create(Binder.class);

	protected final AdminRequestFactory requestFactory;
	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();
	private EditorDelegate<EntityProfileProxy> delegate;

	private boolean hideButtons = false;
	private int index = 0;
	private boolean isNewProxy = false;

	@UiField(provided = true)
	ImogSingleRelationBox<CardEntityProxy> entity;
	@UiField
	ImogCheckBox create;
	@UiField
	ImogCheckBox delete;
	@UiField
	ImogCheckBox directAccess;
	@UiField
	ImogCheckBox export;

	/**
	 * Constructor
	 * @param factory the application request factory
	 * @param hideButtons true if the relation field buttons shall be hidden
	 */
	public EntityProfileEditorNestedRow(AdminRequestFactory factory, boolean hideButtons, ImogBeanRenderer renderer) {

		this.requestFactory = factory;
		this.hideButtons = hideButtons;

		setRelationFields(renderer);

		initWidget(BINDER.createAndBindUi(this));

		properties();
	}

	/**
	 * Constructor
	 * @param factory the application request factory
	 */
	public EntityProfileEditorNestedRow(AdminRequestFactory factory, ImogBeanRenderer renderer) {
		this(factory, false, renderer);
	}

	/**
	 * Sets the properties of the fields
	 */
	public void properties() {

		entity.setLabelWidth("0px");
		create.setLabelWidth("0px");
		delete.setLabelWidth("0px");
		directAccess.setLabelWidth("0px");
		export.setLabelWidth("0px");
	}

	/**
	 * Configures the widgets that manage relation fields
	 */
	private void setRelationFields(ImogBeanRenderer renderer) {

		/* field entity */
		CardEntityDataProvider entityDataProvider;
		entityDataProvider = new CardEntityDataProvider(requestFactory);
		if (hideButtons) // in popup, relation buttons hidden
			entity = new ImogSingleRelationBox<CardEntityProxy>(entityDataProvider, renderer, true);
		else {// in wrapper panel, relation buttons enabled
			if (ProfileUtil.isAdmin())
				entity = new ImogSingleRelationBox<CardEntityProxy>(entityDataProvider, renderer);
			else
				entity = new ImogSingleRelationBox<CardEntityProxy>(false, entityDataProvider, renderer);
		}
		entity.hideButtonPanel(true);

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

		entity.setEdited(false);
		create.setEdited(isEdited);
		delete.setEdited(isEdited);
		directAccess.setEdited(isEdited);
		export.setEdited(isEdited);
	}

	/**
	 * Configures the visibility of the fields in view mode depending on the user privileges
	 */
	public void setFieldReadAccess() {
		if (!ProfileUtil.isAdmin()) {
			entity.setVisible(false);
			create.setVisible(false);
			delete.setVisible(false);
			directAccess.setVisible(false);
			export.setVisible(false);
		}
	}

	/**
	 * Configures the visibility of the fields in edit mode depending on the user privileges
	 */
	public void setFieldEditAccess() {
		if (!ProfileUtil.isAdmin()) {
			entity.setVisible(false);
			create.setVisible(false);
			delete.setVisible(false);
			directAccess.setVisible(false);
			export.setVisible(false);
		}
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

	}

	/**
	 * Setter to inject a CardEntity value
	 * @param value the value to be injected into the editor
	 * @param isLocked true if relation field shall be locked (non editable)
	 */
	public void setEntity(CardEntityProxy value, boolean isLocked) {
		entity.setLocked(isLocked);
		entity.setValue(value);

	}

	public void setIndex(int newIndex) {
		this.index = newIndex;
	}

	public int getIndex() {
		return index;
	}

	public boolean isNewProxy() {
		return isNewProxy;
	}

	public void setNewProxy(boolean isNewProxy) {
		this.isNewProxy = isNewProxy;
	}

	/**
	 * Validate fields values
	 */
	public void validateFields() {

		// entity is a required field
		if (entity.getValue() == null)
			delegate.recordError(BaseNLS.messages().error_required(), null, "entity");
	}

	@Override
	public void setDelegate(EditorDelegate<EntityProfileProxy> delegate) {
		this.delegate = delegate;
	}

	@Override
	public void showErrors(List<EditorError> errors) {
		if (errors != null && errors.size() > 0) {

			List<EditorError> entityFieldErrors = new ArrayList<EditorError>();

			for (EditorError error : errors) {
				Object userData = error.getUserData();
				if (userData != null && userData instanceof String) {
					String field = (String) userData;

					if (field.equals("entity"))
						entityFieldErrors.add(error);
				}
			}
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
		setFieldValueChangeHandler();
		super.onLoad();
	}

	public void setCreateBoxValue(boolean value) {
		create.setValue(value);
	}

	public void setDeleteBoxValue(boolean value) {
		delete.setValue(value);
	}

	public void setDirectAccessBoxValue(boolean value) {
		directAccess.setValue(value);
	}

	public void setExportBoxValue(boolean value) {
		export.setValue(value);
	}
}
