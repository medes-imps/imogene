package org.imogene.admin.client.ui.editor.nested;

import java.util.List;

import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.admin.shared.AdminRequestFactory;
import org.imogene.web.client.ui.field.ImogField;
import org.imogene.web.client.ui.field.group.FieldGroupPanel;
import org.imogene.web.client.util.ImogBeanRenderer;
import org.imogene.web.shared.proxy.EntityProfileProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Ignore;
import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.adapters.EditorSource;
import com.google.gwt.editor.client.adapters.ListEditor;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Editor that provides the UI components to manage the list of EntityProfileEditorNestedRow in the Profile editor
 * @author MEDES-IMPS
 */
public class ProfileEntityProfilesListEditor extends Composite implements IsEditor<ListEditor<EntityProfileProxy, EntityProfileEditorNestedRow>> {

	private static String CREATE = "createAll";
	private static String DELETE = "deleteAll";
	private static String DIRECTACCESS = "directAccessAll";
	private static String EXPORT = "exportAll";

	private static EditorUiBinder uiBinder = GWT.create(EditorUiBinder.class);

	interface EditorUiBinder extends UiBinder<Widget, ProfileEntityProfilesListEditor> {
	}

	protected final AdminRequestFactory requestFactory;
	private EntityProfileListEditorSource editorSource;
	private ListEditor<EntityProfileProxy, EntityProfileEditorNestedRow> editor;
	private ImogBeanRenderer renderer;

	@UiField(provided = true)
	@Ignore
	FieldGroupPanel listSection;
	@UiField(provided = true)
	VerticalPanel listContainer;

	/* header row (field names) */
	@UiField
	@Ignore
	Label entityLabel;
	@UiField
	@Ignore
	Label createLabel;
	@UiField
	@Ignore
	CheckBox createAllCheckBox;
	@UiField
	@Ignore
	Label deleteLabel;
	@UiField
	@Ignore
	CheckBox deleteAllCheckBox;
	@UiField
	@Ignore
	Label directAccessLabel;
	@UiField
	@Ignore
	CheckBox directAccessAllCheckBox;
	@UiField
	@Ignore
	Label exportLabel;
	@UiField
	@Ignore
	CheckBox exportAllCheckBox;

	public ProfileEntityProfilesListEditor(AdminRequestFactory factory, ImogBeanRenderer renderer) {

		this.requestFactory = factory;
		this.renderer = renderer;
		editorSource = new EntityProfileListEditorSource();
		editor = ListEditor.of(editorSource);

		listContainer = new VerticalPanel();

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

	@Override
	public ListEditor<EntityProfileProxy, EntityProfileEditorNestedRow> asEditor() {
		return editor;
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

	public void setEdited(boolean isEdited) {

		List<EntityProfileEditorNestedRow> editors = editor.getEditors();
		if (editors != null && editors.size() > 0) {
			for (EntityProfileEditorNestedRow subEditor : editors)
				subEditor.setEdited(isEdited);
		}
		
		createAllCheckBox.setVisible(isEdited);
		deleteAllCheckBox.setVisible(isEdited);
		directAccessAllCheckBox.setVisible(isEdited);
		exportAllCheckBox.setVisible(isEdited);	
	}

	public void computeVisibility(ImogField<?> source, boolean allValidation) {

		List<EntityProfileEditorNestedRow> editors = editor.getEditors();
		if (editors != null && editors.size() > 0) {
			for (EntityProfileEditorNestedRow subEditor : editors)
				subEditor.computeVisibility(source, allValidation);
		}
	}

	@UiHandler("createAllCheckBox")
	public void createAllClick(ValueChangeEvent<Boolean> e) {
		Boolean chkValue = e.getValue();
		if (chkValue != null)
			setBoxesValues(chkValue, CREATE);
	}

	@UiHandler("deleteAllCheckBox")
	public void deleteAllClick(ValueChangeEvent<Boolean> e) {
		Boolean chkValue = e.getValue();
		if (chkValue != null)
			setBoxesValues(chkValue, DELETE);
	}

	@UiHandler("directAccessAllCheckBox")
	public void directAccessAllClick(ValueChangeEvent<Boolean> e) {
		Boolean chkValue = e.getValue();
		if (chkValue != null)
			setBoxesValues(chkValue, DIRECTACCESS);
	}

	@UiHandler("exportAllCheckBox")
	public void exportAllClick(ValueChangeEvent<Boolean> e) {
		Boolean chkValue = e.getValue();
		if (chkValue != null)
			setBoxesValues(chkValue, EXPORT);
	}

	/**
	 * @param value
	 * @param type
	 */
	private void setBoxesValues(boolean value, String type) {

		List<EntityProfileEditorNestedRow> editors = editor.getEditors();
		if (editors != null && editors.size() > 0) {
			for (EntityProfileEditorNestedRow subEditor : editors) {
				if (type.equals(CREATE))
					subEditor.setCreateBoxValue(value);
				else if (type.equals(DELETE))
					subEditor.setDeleteBoxValue(value);
				else if (type.equals(DIRECTACCESS))
					subEditor.setDirectAccessBoxValue(value);
				else if (type.equals(EXPORT))
					subEditor.setExportBoxValue(value);
			}
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

			final EntityProfileEditorNestedRow subEditor = new EntityProfileEditorNestedRow(requestFactory, renderer);
			subEditor.setIndex(index);
			listContainer.insert(subEditor, index);
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
