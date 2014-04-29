package org.imogene.admin.client.ui.editor.nested;

import java.util.ArrayList;
import java.util.List;

import org.imogene.admin.client.AdminRenderer;
import org.imogene.admin.client.dataprovider.CardEntityDataProvider;
import org.imogene.admin.client.dataprovider.FieldGroupDataProvider;
import org.imogene.admin.client.event.save.SaveCardEntityEvent;
import org.imogene.admin.client.event.save.SaveFieldGroupEvent;
import org.imogene.admin.client.ui.workflow.panel.CardEntityFormPanel;
import org.imogene.admin.client.ui.workflow.panel.FieldGroupFormPanel;
import org.imogene.admin.shared.AdminRequestFactory;
import org.imogene.web.client.event.FieldValueChangeEvent;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.field.ImogCheckBox;
import org.imogene.web.client.ui.field.ImogField;
import org.imogene.web.client.ui.field.relation.single.ImogSingleRelationBox;
import org.imogene.web.client.ui.panel.RelationPopupPanel;
import org.imogene.web.client.util.ProfileUtil;
import org.imogene.web.shared.proxy.CardEntityProxy;
import org.imogene.web.shared.proxy.FieldGroupProfileProxy;
import org.imogene.web.shared.proxy.FieldGroupProxy;

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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * Editor that provides the UI components that allow a FieldGroupProfileProxy to be viewed and edited in an editor list
 * 
 * @author MEDES-IMPS
 */
public class FieldGroupProfileEditorNestedRow extends Composite implements Editor<FieldGroupProfileProxy>,
		HasEditorDelegate<FieldGroupProfileProxy>, HasEditorErrors<FieldGroupProfileProxy> {

	interface Binder extends UiBinder<Widget, FieldGroupProfileEditorNestedRow> {
	}

	private static final Binder BINDER = GWT.create(Binder.class);

	protected final AdminRequestFactory requestFactory;
	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();
	private EditorDelegate<FieldGroupProfileProxy> delegate;

	private boolean hideButtons = false;
	private int index = 0;
	private boolean isNewProxy = false;

	@UiField
	Image clearImage;

	@UiField(provided = true)
	ImogSingleRelationBox<CardEntityProxy> cardEntity;
	@UiField(provided = true)
	ImogSingleRelationBox<FieldGroupProxy> fieldGroup;
	private FieldGroupDataProvider fieldGroupDataProvider;
	@UiField
	ImogCheckBox read;
	@UiField
	ImogCheckBox write;
	@UiField
	ImogCheckBox export;

	/**
	 * Constructor
	 * 
	 * @param factory the application request factory
	 * @param hideButtons true if the relation field buttons shall be hidden
	 */
	public FieldGroupProfileEditorNestedRow(AdminRequestFactory factory, boolean hideButtons) {

		this.requestFactory = factory;
		this.hideButtons = hideButtons;

		setRelationFields();

		initWidget(BINDER.createAndBindUi(this));

		clearImage.setTitle(BaseNLS.constants().button_remove());
		clearImage.setUrl(GWT.getModuleBaseURL() + "images/relation_remove.png");

		properties();
	}

	/**
	 * Constructor
	 * 
	 * @param factory the application request factory
	 */
	public FieldGroupProfileEditorNestedRow(AdminRequestFactory factory) {
		this(factory, false);
	}

	/**
	 * Sets the properties of the fields
	 */
	public void properties() {

		// cardEntity.setLabel(NLS.constants().fieldGroupProfile_field_cardEntity(),
		// HasHorizontalAlignment.ALIGN_RIGHT);
		cardEntity.setLabelWidth("0px");
		// the value of cardEntity affects the value of other fields
		cardEntity.notifyChanges(requestFactory.getEventBus());
		// fieldGroup.setLabel(NLS.constants().fieldGroupProfile_field_fieldGroup(),
		// HasHorizontalAlignment.ALIGN_RIGHT);
		fieldGroup.setLabelWidth("0px");
		// read.setLabel(NLS.constants().fieldGroupProfile_field_read(), HasHorizontalAlignment.ALIGN_RIGHT);
		read.setLabelWidth("0px");
		// write.setLabel(NLS.constants().fieldGroupProfile_field_write(), HasHorizontalAlignment.ALIGN_RIGHT);
		write.setLabelWidth("0px");
		// export.setLabel(NLS.constants().fieldGroupProfile_field_export(), HasHorizontalAlignment.ALIGN_RIGHT);
		export.setLabelWidth("0px");
	}

	/**
	 * Configures the widgets that manage relation fields
	 */
	public void setRelationFields() {

		/* field cardEntity */
		CardEntityDataProvider cardEntityDataProvider;
		cardEntityDataProvider = new CardEntityDataProvider(requestFactory);
		if (hideButtons) // in popup, relation buttons hidden
			cardEntity = new ImogSingleRelationBox<CardEntityProxy>(cardEntityDataProvider, AdminRenderer.get(), true);
		else {// in wrapper panel, relation buttons enabled
			if (ProfileUtil.isAdmin())
				cardEntity = new ImogSingleRelationBox<CardEntityProxy>(cardEntityDataProvider, AdminRenderer.get());
			else
				cardEntity = new ImogSingleRelationBox<CardEntityProxy>(false, cardEntityDataProvider,
						AdminRenderer.get());
		}

		/* field fieldGroup */
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

		clearImage.setVisible(isEdited);

		if (isEdited)
			setFieldEditAccess();
		else
			setFieldReadAccess();

		cardEntity.setEdited(isEdited);
		fieldGroup.setEdited(isEdited);
		read.setEdited(isEdited);
		write.setEdited(isEdited);
		export.setEdited(isEdited);
	}

	/**
	 * Configures the visibility of the fields in view mode depending on the user privileges
	 */
	public void setFieldReadAccess() {
		if (!ProfileUtil.isAdmin()) {
			cardEntity.setVisible(false);
			fieldGroup.setVisible(false);
			read.setVisible(false);
			write.setVisible(false);
			export.setVisible(false);
		}
	}

	/**
	 * Configures the visibility of the fields in edit mode depending on the user privileges
	 */
	public void setFieldEditAccess() {
		if (!ProfileUtil.isAdmin()) {
			cardEntity.setVisible(false);
			fieldGroup.setVisible(false);
			read.setVisible(false);
			write.setVisible(false);
			export.setVisible(false);
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

						/* FieldGroup list content depends on the value of field CardEntity */
						if (field.equals(cardEntity)) {
							clearFieldGroupWidget();
							getFieldGroupFilteredByCardEntity(cardEntity.getValue());
						}
					}
				}));
	}

	/**
	 * Computes the field visibility
	 */
	public void computeVisibility(ImogField<?> source, boolean allValidation) {

	}

	/**
	 * Filters the content of the RelationField FieldGroup by the value of the RelationField CardEntity
	 * 
	 * @param cardEntity the value of the RelationField CardEntity
	 */
	public void getFieldGroupFilteredByCardEntity(CardEntityProxy pCardEntity) {

		if (pCardEntity != null) {
			if (!hideButtons)
				fieldGroup.hideButtons(false);
			fieldGroupDataProvider.setFilterCriteria(pCardEntity.getId(), "entity.id");
		} else {
			fieldGroup.hideButtons(true);
			fieldGroupDataProvider.setFilterCriteria(null);
		}
	}

	public void setDeleteClickHandler(ClickHandler handler) {
		// registrations.add(clearImage.addClickHandler(handler));
		clearImage.addClickHandler(handler);
	}

	/**
	 * Setter to inject a CardEntity value
	 * 
	 * @param value the value to be injected into the editor
	 * @param isLocked true if relation field shall be locked (non editable)
	 */
	public void setCardEntity(CardEntityProxy value, boolean isLocked) {
		cardEntity.setLocked(isLocked);
		cardEntity.setValue(value);

		// Field FieldGroup depends on the value of field cardEntity
		getFieldGroupFilteredByCardEntity(value);
	}

	/** Widget update for field cardEntity */
	private void clearCardEntityWidget() {
		cardEntity.clear();
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

	/** Widget update for field fieldGroup */
	private void clearFieldGroupWidget() {
		fieldGroup.clear();
	}

	/**
	 * Configures the handlers of the widgets that manage relation fields
	 */
	private void setRelationHandlers() {

		/* 'Information' button for field CardEntity */
		cardEntity.setViewClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (cardEntity.getValue() != null) {
					RelationPopupPanel relationPopup = new RelationPopupPanel();
					CardEntityFormPanel form = new CardEntityFormPanel(requestFactory, cardEntity.getValue().getId(),
							relationPopup, "cardEntity");
					relationPopup.addWidget(form);
					relationPopup.show();
				}
			}
		});

		/* 'Add' button for field CardEntity */
		cardEntity.setAddClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				RelationPopupPanel relationPopup = new RelationPopupPanel();
				CardEntityFormPanel form = new CardEntityFormPanel(requestFactory, null, relationPopup, "cardEntity");
				/* common fields */

				relationPopup.addWidget(form);
				relationPopup.show();
			}
		});

		/* SaveEvent handler when a CardEntity is created or updated from the relation field CardEntity */
		registrations.add(requestFactory.getEventBus().addHandler(SaveCardEntityEvent.TYPE,
				new SaveCardEntityEvent.Handler() {
					@Override
					public void saveCardEntity(CardEntityProxy value) {
						cardEntity.setValue(value);
					}

					@Override
					public void saveCardEntity(CardEntityProxy value, String initField) {
						if (initField.equals("cardEntity"))
							cardEntity.setValue(value, true);
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

		// cardEntity is a required field
		if (cardEntity.getValue() == null)
			delegate.recordError(BaseNLS.messages().error_required(), null, "cardEntity");
		// fieldGroup is a required field
		if (fieldGroup.getValue() == null)
			delegate.recordError(BaseNLS.messages().error_required(), null, "fieldGroup");
	}

	@Override
	public void setDelegate(EditorDelegate<FieldGroupProfileProxy> delegate) {
		this.delegate = delegate;
	}

	@Override
	public void showErrors(List<EditorError> errors) {
		if (errors != null && errors.size() > 0) {

			List<EditorError> cardEntityFieldErrors = new ArrayList<EditorError>();
			List<EditorError> fieldGroupFieldErrors = new ArrayList<EditorError>();

			for (EditorError error : errors) {
				Object userData = error.getUserData();
				if (userData != null && userData instanceof String) {
					String field = (String) userData;

					if (field.equals("cardEntity"))
						cardEntityFieldErrors.add(error);
					if (field.equals("fieldGroup"))
						fieldGroupFieldErrors.add(error);
				}
			}
			if (cardEntityFieldErrors.size() > 0)
				cardEntity.showErrors(cardEntityFieldErrors);
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
