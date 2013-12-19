package org.imogene.web.client.dynamicfields.ui.field;

import java.util.ArrayList;
import java.util.List;

import org.imogene.web.client.dynamicfields.i18n.DynFieldsNLS;
import org.imogene.web.client.dynamicfields.ui.workflow.panel.DynamicFieldTemplateFormPanel;
import org.imogene.web.client.event.SaveDynamicFieldTemplateEvent;
import org.imogene.web.client.ui.field.group.FieldGroupPanel;
import org.imogene.web.client.ui.panel.RelationPopupPanel;
import org.imogene.web.client.util.ImogKeyGenerator;
import org.imogene.web.client.util.LocalSession;
import org.imogene.web.client.util.ProfileUtil;
import org.imogene.web.shared.ImogRequestFactory;
import org.imogene.web.shared.proxy.DynamicFieldInstanceProxy;
import org.imogene.web.shared.proxy.DynamicFieldTemplateProxy;
import org.imogene.web.shared.proxy.ImogActorProxy;
import org.imogene.web.shared.request.DynamicFieldsRequest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Ignore;
import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.adapters.EditorSource;
import com.google.gwt.editor.client.adapters.ListEditor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

/**
 * Editor that provides the UI components to manage the list of DynamicFieldEditorForList
 * 
 * @author MEDES-IMPS
 */
public class DynamicFieldBox extends Composite implements
		IsEditor<ListEditor<DynamicFieldInstanceProxy, DynamicFieldEditorForList>> {

	private static EditorUiBinder uiBinder = GWT.create(EditorUiBinder.class);

	interface EditorUiBinder extends UiBinder<Widget, DynamicFieldBox> {
	}

	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();

	protected final ImogRequestFactory requestFactory;
	private DynamicField_InstanceListEditorSource editorSource;
	private ListEditor<DynamicFieldInstanceProxy, DynamicFieldEditorForList> editor;
	private DynamicFieldsRequest context;

	private String formType;
	private boolean isNewFieldValue = false;
	private FormTypeUtil formTypeUtil;

	@UiField(provided = true)
	@Ignore
	FieldGroupPanel listSection;
	@UiField(provided = true)
	VerticalPanel listContainer;
	@Ignore
	@UiField(provided = true)
	PushButton createDFTemplateButton;

	/**
	 * Constructor
	 * 
	 * @param factory
	 * @param formType
	 */
	public DynamicFieldBox(ImogRequestFactory factory, String formType, FormTypeUtil formTypeUtil) {

		this.requestFactory = factory;
		this.formType = formType;
		this.formTypeUtil = formTypeUtil;
		editorSource = new DynamicField_InstanceListEditorSource();
		editor = ListEditor.of(editorSource);

		listSection = new FieldGroupPanel();
		listSection.setGroupTitle(DynFieldsNLS.constants().dynamicField_Template_name_plur());

		listContainer = new VerticalPanel();

		createDFTemplateButton = new PushButton(DynFieldsNLS.constants().dynamicfield_button_new());

		initWidget(uiBinder.createAndBindUi(this));
	}

	/**
	 * Remove the DynamicField_Instance at the specified index
	 * 
	 * @param index of the DynamicField_Instance
	 */
	private void remove(int index) {
		editor.getList().remove(index);
	}

	/**
	 * Get the DynamicField_Instance at the specified index
	 * 
	 * @param index of the DynamicField_Instance
	 * @return the DynamicField_Instance
	 */
	private DynamicFieldInstanceProxy getProxy(int index) {
		if (editor.getList() != null)
			return editor.getList().get(index);
		return null;
	}

	/**
	 * Get the index of a DynamicField_Instance
	 * 
	 * @param proxy the DynamicField_Instance whose index is looked for
	 * @return the index
	 */
	private Integer getIndex(DynamicFieldInstanceProxy proxy) {
		List<DynamicFieldInstanceProxy> list = editor.getList();
		if (editor.getList() != null) {
			return list.indexOf(proxy);
		}
		return null;
	}

	/**
	 * Order the Dynamic Field Values depending on the field position that has been defined in the Dynamic Field
	 * Template
	 */
	public void orderList() {

		List<DynamicFieldInstanceProxy> proxyList = editor.getList();
		if (proxyList != null) {
			for (DynamicFieldInstanceProxy proxy : proxyList) {

				DynamicFieldTemplateProxy template = proxy.getFieldTemplate();
				if (template != null && template.getFieldPosition() != null) {
					Integer position = template.getFieldPosition();

					List<DynamicFieldEditorForList> editors = editor.getEditors();
					DynamicFieldEditorForList subEditor = editors.get(getIndex(proxy));
					if (subEditor != null) {

						if (position <= proxyList.size())
							move(subEditor, position);
						else
							move(subEditor, proxyList.size());
					}
				}
			}
		}
	}

	/**
	 * Moves a DynamicFieldEditorForList to a given position
	 * 
	 * @param editor the DynamicFieldEditorForList to be moved
	 * @param index the position where the DynamicFieldEditorForList shall be moved
	 */
	public void move(DynamicFieldEditorForList editor, int index) {
		if (index > 0) {
			listContainer.insert(editor, index);
			updateIndex();
		}
	}

	/**
	 * 
	 */
	@Override
	public ListEditor<DynamicFieldInstanceProxy, DynamicFieldEditorForList> asEditor() {
		return editor;
	}

	/**
	 * Inserts a DynamicField_Instance in the list
	 * 
	 * @param value the DynamicField_Instance to be inserted in the list
	 */
	private void insert(DynamicFieldInstanceProxy value) {

		DynamicFieldTemplateProxy template = value.getFieldTemplate();

		if (template != null && template.getFieldPosition() != null) {

			// insert value at given position
			List<DynamicFieldInstanceProxy> proxyList = editor.getList();
			if (template.getFieldPosition() < 1)
				editor.getList().add(0, value);
			else if (template.getFieldPosition() <= proxyList.size())
				editor.getList().add(template.getFieldPosition() - 1, value);
			else
				editor.getList().add(value);
		} else
			// insert value at the end of the list
			editor.getList().add(value);

		updateIndex();
	}

	/**
	 * Adds a value to the list of values
	 * 
	 * @param value the DynamicField_Instance to be added to the list
	 */
	private void addValue(DynamicFieldInstanceProxy value) {
		if (value != null) {

			if (editor.getList() == null)
				editor.setValue(new ArrayList<DynamicFieldInstanceProxy>());
			isNewFieldValue = true;

			insert(value);

			List<DynamicFieldEditorForList> editors = editor.getEditors();
			editors.get(getIndex(value)).setEdited(true);
		}
	}

	/**
	 * Creates a new DynamicField_Instance instance for a given DynamicField_Template and adds it to the list of
	 * DynamicField_Instance
	 * 
	 * @param template the DynamicField_Template for which a new DynamicField_Instance has to be created
	 */
	private void add(DynamicFieldTemplateProxy template) {
		DynamicFieldInstanceProxy newDynamicField_Instance = context.create(DynamicFieldInstanceProxy.class);
		newDynamicField_Instance.setId(ImogKeyGenerator.generateKeyId("DFI"));
		newDynamicField_Instance.setVersion(0);
		newDynamicField_Instance.setFieldTemplate(template);

		// context.saveDynamicFieldValues(newDynamicField_Instance, true);
		addValue(newDynamicField_Instance);
	}

	/**
	 * Adds new DynamicField_Instance instances for a list of DynamicField_Template
	 * 
	 * @param response the list of DynamicField_Template for which DynamicField_Instance instances have to be created if
	 *        they are not already present
	 */
	public void addFieldTemplates(List<DynamicFieldTemplateProxy> response) {

		if (response != null) {
			for (DynamicFieldTemplateProxy template : response) {
				boolean isPresent = false;
				for (DynamicFieldInstanceProxy instance : editor.getList()) {
					if (instance.getFieldTemplate().getId().equals(template.getId()))
						isPresent = true;
				}
				if (!isPresent)
					add(template);
			}
		}
	}

	/**
	 * Updates the indexes of the editors in the editor list
	 */
	private void updateIndex() {
		int count = listContainer.getWidgetCount();
		for (int i = 0; i < count; i++) {
			DynamicFieldEditorForList subEditor = (DynamicFieldEditorForList) listContainer.getWidget(i);
			subEditor.setIndex(i);
		}
	}

	/**
	 * 
	 * @param ctx
	 */
	public void setRequestContext(DynamicFieldsRequest ctx) {
		this.context = ctx;
	}

	/**
	 * 
	 * @param isEdited
	 */
	public void setEdited(boolean isEdited) {

		List<DynamicFieldEditorForList> editors = editor.getEditors();
		if (editors != null && editors.size() > 0) {
			for (DynamicFieldEditorForList editor : editors)
				editor.setEdited(isEdited);
		} else {
			if (!isEdited)
				listSection.setVisible(false);
		}

		if (isEdited && !listSection.isVisible())
			listSection.setVisible(true);

		createDFTemplateButton.setVisible(isEdited);
	}

	/**
	 * Handler that manages the click event of the createDFTemplateButton button Opens a popup that enables to create a
	 * new DynamicField_Template
	 * 
	 * @param e
	 */
	@UiHandler("createDFTemplateButton")
	void onCreateDFTemplate(ClickEvent e) {
		RelationPopupPanel relationPopup = new RelationPopupPanel();
		DynamicFieldTemplateFormPanel form = new DynamicFieldTemplateFormPanel(requestFactory, null, relationPopup,
				"newDFTemplate_" + formType, false, formTypeUtil);
		form.setFormType(formType);
		relationPopup.addWidget(form);
		relationPopup.show();
	}

	/**
	 * Checks if a binary field is uploading a binary file
	 * 
	 * @param source
	 * @param allValidation
	 */
	public boolean isUploading() {

		List<DynamicFieldEditorForList> editors = editor.getEditors();
		if (editors != null && editors.size() > 0) {
			for (DynamicFieldEditorForList dynamicFieldInstanceEditor : editors)
				if (dynamicFieldInstanceEditor.isUploading())
					return true;
		}
		return false;
	}

	/**
	 * Configures the handlers of the widgets that manage relation fields
	 */
	private void setHandlers() {

		/* SaveEvent handler when a DynamicField_Template is created */
		registrations.add(requestFactory.getEventBus().addHandler(SaveDynamicFieldTemplateEvent.TYPE,
				new SaveDynamicFieldTemplateEvent.Handler() {
					@Override
					public void saveDynamicFieldTemplate(DynamicFieldTemplateProxy template) {
					}

					public void saveDynamicFieldTemplate(DynamicFieldTemplateProxy template, String initField) {
						if (initField.equals("newDFTemplate_" + formType))
							add(template);
						else {
							int i = 0;
							for (DynamicFieldInstanceProxy instance : editor.getList()) {
								if (instance.getFieldTemplate().getId().equals(initField)) {
									DynamicFieldEditorForList subEditor = editor.getEditors().get(i);
									subEditor.setLabel(template.getFieldName());
									return;
								}
								i = i + 1;
							}
						}
					}
				}));
	}

	/**
	 * Gets the command that enables to disable a DynamicField_Template
	 * 
	 * @param subEditor the editor whose DynamicField_Template has to be disabled
	 * @return
	 */
	private Command getDisableTemplateCommand(final DynamicFieldEditorForList subEditor,
			final DynamicFieldTemplateProxy template) {

		Command command = new Command() {
			public void execute() {

				if (Window.confirm(DynFieldsNLS.constants().dynamicfield_confirmation_delete())) {

					context.activateDynamicFieldTemplate(template, false).to(new Receiver<Void>() {
						@Override
						public void onSuccess(Void response) {
							Window.alert(DynFieldsNLS.constants().dynamicfield_delete_confirm());
						}

						@Override
						public void onFailure(ServerFailure error) {
							Window.alert("Error disabling the Dynamic Field Template");
							super.onFailure(error);
						}
					});
					// remove subeditor if an empty field
					if (subEditor.isNew()) {
						remove(subEditor.getIndex());
						updateIndex();
					}
				}
			}
		};
		return command;
	}

	/**
	 * Gets the command that enables to enable a DynamicField_Template
	 * 
	 * @param subEditor the editor whose DynamicField_Template has to be enabled
	 * @return
	 */
	private Command getEnableTemplateCommand(final DynamicFieldTemplateProxy template) {

		Command command = new Command() {
			public void execute() {

				if (Window.confirm(DynFieldsNLS.constants().dynamicfield_confirmation_enable())) {

					context.activateDynamicFieldTemplate(template, true).to(new Receiver<Void>() {
						@Override
						public void onSuccess(Void response) {
							Window.alert(DynFieldsNLS.constants().dynamicfield_enable_confirm());
						}

						@Override
						public void onFailure(ServerFailure error) {
							Window.alert("Error enabling the Dynamic Field Template");
							super.onFailure(error);
						}
					});
				}
			}
		};
		return command;
	}

	/**
	 * Gets the command that enables to update a DynamicField_Template
	 * 
	 * @param subEditor the editor whose DynamicField_Template has to be updated
	 * @return
	 */
	private Command getUpdateTemplateCommand(final DynamicFieldTemplateProxy template) {

		Command command = new Command() {
			public void execute() {
				RelationPopupPanel relationPopup = new RelationPopupPanel();
				DynamicFieldTemplateFormPanel form = new DynamicFieldTemplateFormPanel(requestFactory,
						template.getId(), relationPopup, template.getId(), false, formTypeUtil);
				relationPopup.addWidget(form);
				relationPopup.show();
			}
		};
		return command;
	}

	/**
	 * Sets the privileges and actions on Instances and Templates for the current user
	 * 
	 * @param subEditor the editor for whose DynamicField_Template commands apply
	 */
	private void setFieldPrivileges(final DynamicFieldEditorForList subEditor) {

		DynamicFieldInstanceProxy proxy = getProxy(subEditor.getIndex());
		if (proxy != null) {

			DynamicFieldTemplateProxy template = proxy.getFieldTemplate();
			if ((template != null && canManageTemplate(template.getTemplateCreator()))
					|| (template != null && template.getAllUsers() != null && template.getAllUsers())) {
				if (template.getIsActivated())
					subEditor.setDisableTemplateAction(getDisableTemplateCommand(subEditor, template));
				else
					subEditor.setEnableTemplateAction(getEnableTemplateCommand(template));
				subEditor.setUpdateTemplateAction(getUpdateTemplateCommand(template));
			} else
				subEditor.canManageField(false);
		}
	}

	/**
	 * Defines if and actor can manage (enable/disable/update) a DynamicField_Template
	 * 
	 * @param actor the actor that created the DynamicField_Template
	 * @return
	 */
	private boolean canManageTemplate(ImogActorProxy actor) {
		ImogActorProxy currentUser = LocalSession.get().getCurrentUser();
		if (ProfileUtil.isAdmin() || actor.getId().equals(currentUser.getId())
				|| ProfileUtil.hasProfile(currentUser, actor.getProfiles()))
			return true;
		return false;
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
		setHandlers();
		super.onLoad();
	}

	/**
	 * Provider for the editors of the List
	 * 
	 * @author MEDES-IMPS
	 */
	private class DynamicField_InstanceListEditorSource extends EditorSource<DynamicFieldEditorForList> {

		@Override
		public DynamicFieldEditorForList create(int index) {

			final DynamicFieldEditorForList subEditor = new DynamicFieldEditorForList(requestFactory, isNewFieldValue);
			if (isNewFieldValue)
				isNewFieldValue = false;
			subEditor.setIndex(index);
			listContainer.insert(subEditor, index);
			setFieldPrivileges(subEditor);
			return subEditor;
		}

		@Override
		public void dispose(DynamicFieldEditorForList subEditor) {
			subEditor.removeFromParent();
		}

		@Override
		public void setIndex(DynamicFieldEditorForList subEditor, int index) {
			listContainer.insert(subEditor, index);
		}
	}

}
