package org.imogene.admin.client.ui.editor.nested;

import java.util.ArrayList;
import java.util.List;

import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.admin.shared.AdminRequestFactory;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.field.ImogField;
import org.imogene.web.client.ui.field.group.FieldGroupPanel;
import org.imogene.web.client.util.ImogKeyGenerator;
import org.imogene.web.shared.proxy.EntityProfileProxy;
import org.imogene.web.shared.request.ImogEntityRequest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Ignore;
import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.adapters.EditorSource;
import com.google.gwt.editor.client.adapters.ListEditor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Editor that provides the UI components to manage the list of EntityProfileEditorNestedRow in the Profile editor
 * 
 * @author MEDES-IMPS
 */
public class ProfileEntityProfilesListEditor extends Composite implements
		IsEditor<ListEditor<EntityProfileProxy, EntityProfileEditorNestedRow>> {

	private static EditorUiBinder uiBinder = GWT.create(EditorUiBinder.class);

	interface EditorUiBinder extends UiBinder<Widget, ProfileEntityProfilesListEditor> {
	}

	protected final AdminRequestFactory requestFactory;
	private EntityProfileListEditorSource editorSource;
	private ListEditor<EntityProfileProxy, EntityProfileEditorNestedRow> editor;
	// private ListEditor<EntityProfileProxy, EntityProfileEditorNestedForm> editor;
	private ImogEntityRequest request;

	@UiField(provided = true)
	@Ignore
	FieldGroupPanel listSection;
	@UiField(provided = true)
	VerticalPanel listContainer;
	@UiField(provided = true)
	@com.google.gwt.editor.client.Editor.Ignore
	Image addItem;

	/* header row (field names) */
	@UiField
	@Ignore
	Label entityLabel;
	@UiField
	@Ignore
	Label createLabel;
	@UiField
	@Ignore
	Label deleteLabel;
	@UiField
	@Ignore
	Label directAccessLabel;
	@UiField
	@Ignore
	Label exportLabel;

	public ProfileEntityProfilesListEditor(AdminRequestFactory factory) {

		this.requestFactory = factory;
		editorSource = new EntityProfileListEditorSource();
		editor = ListEditor.of(editorSource);

		listContainer = new VerticalPanel();
		addItem = new Image(GWT.getModuleBaseURL() + "images/relation_add.png");
		addItem.setTitle(BaseNLS.constants().button_add());

		listSection = new FieldGroupPanel();
		listSection.setGroupTitle(AdminNLS.constants().profile_field_entityProfiles());
		listSection.setLabelFontSize("12px");
		listSection.addStyleName("fieldGroup-ListEditor");

		initWidget(uiBinder.createAndBindUi(this));

		entityLabel.setText(AdminNLS.constants().entityProfile_field_entity());
		createLabel.setText(AdminNLS.constants().entityProfile_field_create());
		deleteLabel.setText(AdminNLS.constants().entityProfile_field_delete());
		directAccessLabel.setText(AdminNLS.constants().entityProfile_field_directAccess());
		exportLabel.setText(AdminNLS.constants().entityProfile_field_export());
	}

	/**
	 * Remove the EntityProfile at the specified index
	 * 
	 * @param index of the EntityProfile
	 */
	private void remove(int index) {
		editor.getList().remove(index);
	}

	/**
	 * Get the EntityProfile at the specified index
	 * 
	 * @param index of the EntityProfile
	 */
	private EntityProfileProxy getProxy(int index) {
		return editor.getList().get(index);
	}

	@Override
	public ListEditor<EntityProfileProxy, EntityProfileEditorNestedRow> asEditor() {
		return editor;
	}

	@UiHandler("addItem")
	void onAddClick(ClickEvent event) {
		add();
	}

	/**
	 * Adds a new value to the editor list Prerequisite: Context must have been set through the SetRequestContext method
	 */
	private void add() {
		EntityProfileProxy newEntityProfile = request.create(EntityProfileProxy.class);
		newEntityProfile.setId(ImogKeyGenerator.generateKeyId("PRO_ENT"));
		newEntityProfile.setVersion(0);
		// request.saveEntityProfiles(newEntityProfile, true);

		addValue(newEntityProfile, true);
	}

	/**
	 * Adds a list of values to the editor list
	 */
	private void addValue(EntityProfileProxy value, boolean isNew) {
		if (value != null) {
			if (editor.getList() == null)
				editor.setValue(new ArrayList<EntityProfileProxy>());
			editor.getList().add(value);
			updateIndex();

			// update subeditor
			List<EntityProfileEditorNestedRow> editors = editor.getEditors();
			EntityProfileEditorNestedRow subEditor = editors.get(editors.size() - 1);
			subEditor.setNewProxy(isNew);
			subEditor.computeVisibility(null, true);
			subEditor.setEdited(true);
		}
	}

	public void up(EntityProfileEditorNestedRow editor) {
		int currentIndex = listContainer.getWidgetIndex(editor);
		if (currentIndex > 0) {
			listContainer.insert(editor, currentIndex - 1);
			updateIndex();
		}
	}

	public void down(EntityProfileEditorNestedRow editor) {
		int currentIndex = listContainer.getWidgetIndex(editor);
		if (currentIndex < listContainer.getWidgetCount() + 1) {
			listContainer.insert(editor, currentIndex + 2);
			updateIndex();
		}
	}

	private void updateIndex() {
		int count = listContainer.getWidgetCount();
		for (int i = 0; i < count; i++) {
			EntityProfileEditorNestedRow subEditor = (EntityProfileEditorNestedRow) listContainer.getWidget(i);
			subEditor.setIndex(i);
		}
	}

	public void setRequestContextForListEditors(ImogEntityRequest ctx) {
		this.request = ctx;
	}

	public void setEdited(boolean isEdited) {

		List<EntityProfileEditorNestedRow> editors = editor.getEditors();
		if (editors != null && editors.size() > 0) {
			for (EntityProfileEditorNestedRow subEditor : editors)
				subEditor.setEdited(isEdited);
		}
		addItem.setVisible(isEdited);
	}

	public void computeVisibility(ImogField<?> source, boolean allValidation) {

		List<EntityProfileEditorNestedRow> editors = editor.getEditors();
		if (editors != null && editors.size() > 0) {
			for (EntityProfileEditorNestedRow subEditor : editors)
				subEditor.computeVisibility(source, allValidation);
		}
	}

	/**
	 * Validate nested forms fields values
	 */
	public void validateFields() {

		List<EntityProfileEditorNestedRow> editors = editor.getEditors();
		if (editors != null && editors.size() > 0) {
			for (EntityProfileEditorNestedRow subEditor : editors)
				subEditor.validateFields();
		}
	}

	/**
	 * Internal class
	 */
	private class EntityProfileListEditorSource extends EditorSource<EntityProfileEditorNestedRow> {

		@Override
		public EntityProfileEditorNestedRow create(int index) {

			final EntityProfileEditorNestedRow subEditor = new EntityProfileEditorNestedRow(requestFactory);
			subEditor.setIndex(index);
			listContainer.insert(subEditor, index);

			subEditor.setDeleteClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (Window.confirm(BaseNLS.constants().confirmation_delete())) {
						EntityProfileProxy proxy = getProxy(subEditor.getIndex());
						if (!subEditor.isNewProxy())
							request.delete(proxy);
						remove(subEditor.getIndex());
						updateIndex();
					}
				}
			});
			return subEditor;
		}

		@Override
		public void dispose(EntityProfileEditorNestedRow subEditor) {
			subEditor.removeFromParent();
		}

		@Override
		public void setIndex(EntityProfileEditorNestedRow subEditor, int index) {
			listContainer.insert(subEditor, index);
		}
	}
}
