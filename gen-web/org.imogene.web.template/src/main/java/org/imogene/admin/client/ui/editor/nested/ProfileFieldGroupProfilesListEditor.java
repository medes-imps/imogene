package org.imogene.admin.client.ui.editor.nested;

import java.util.List;

import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.admin.shared.AdminRequestFactory;
import org.imogene.web.client.ui.field.ImogField;
import org.imogene.web.client.ui.field.group.FieldGroupPanel;
import org.imogene.web.client.util.ImogBeanRenderer;
import org.imogene.web.shared.proxy.FieldGroupProfileProxy;

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
 * Editor that provides the UI components to manage the list of FieldGroupProfileEditorNestedRow in the Profile editor
 * @author MEDES-IMPS
 */
public class ProfileFieldGroupProfilesListEditor extends Composite implements IsEditor<ListEditor<FieldGroupProfileProxy, FieldGroupProfileEditorNestedRow>> {

	private static String READ = "readAll";
	private static String WRITE = "writeAll";
	private static String EXPORT = "exportAll";

	private static EditorUiBinder uiBinder = GWT.create(EditorUiBinder.class);

	interface EditorUiBinder extends UiBinder<Widget, ProfileFieldGroupProfilesListEditor> {
	}

	protected final AdminRequestFactory requestFactory;
	private FieldGroupProfileListEditorSource editorSource;
	private ListEditor<FieldGroupProfileProxy, FieldGroupProfileEditorNestedRow> editor;
	private ImogBeanRenderer renderer;

	@UiField(provided = true)
	@Ignore
	FieldGroupPanel listSection;
	@UiField(provided = true)
	VerticalPanel listContainer;

	/* header row (field names) */
	@UiField
	@Ignore
	Label cardEntityLabel;
	@UiField
	@Ignore
	Label fieldGroupLabel;
	@UiField
	@Ignore
	Label readLabel;
	@UiField
	@Ignore
	CheckBox readAllCheckBox;
	@UiField
	@Ignore
	Label writeLabel;
	@UiField
	@Ignore
	CheckBox writeAllCheckBox;
	@UiField
	@Ignore
	Label exportLabel;
	@UiField
	@Ignore
	CheckBox exportAllCheckBox;

	public ProfileFieldGroupProfilesListEditor(AdminRequestFactory factory, ImogBeanRenderer renderer) {

		this.requestFactory = factory;
		this.renderer = renderer;
		editorSource = new FieldGroupProfileListEditorSource();
		editor = ListEditor.of(editorSource);

		listContainer = new VerticalPanel();

		listSection = new FieldGroupPanel();
		listSection.setGroupTitle(AdminNLS.constants().profile_field_fieldGroupProfiles());
		listSection.setLabelFontSize("12px");
		listSection.addStyleName("fieldGroup-ListEditor");

		initWidget(uiBinder.createAndBindUi(this));

		cardEntityLabel.setText(AdminNLS.constants().fieldGroupProfile_field_cardEntity());
		fieldGroupLabel.setText(AdminNLS.constants().fieldGroupProfile_field_fieldGroup());
		readLabel.setText(AdminNLS.constants().fieldGroupProfile_field_read());
		writeLabel.setText(AdminNLS.constants().fieldGroupProfile_field_write());
		exportLabel.setText(AdminNLS.constants().fieldGroupProfile_field_export());
	}

	@Override
	public ListEditor<FieldGroupProfileProxy, FieldGroupProfileEditorNestedRow> asEditor() {
		return editor;
	}

	public void up(FieldGroupProfileEditorNestedRow editor) {
		int currentIndex = listContainer.getWidgetIndex(editor);
		if (currentIndex > 0) {
			listContainer.insert(editor, currentIndex - 1);
			updateIndex();
		}
	}

	public void down(FieldGroupProfileEditorNestedRow editor) {
		int currentIndex = listContainer.getWidgetIndex(editor);
		if (currentIndex < listContainer.getWidgetCount() + 1) {
			listContainer.insert(editor, currentIndex + 2);
			updateIndex();
		}
	}

	private void updateIndex() {
		int count = listContainer.getWidgetCount();
		for (int i = 0; i < count; i++) {
			FieldGroupProfileEditorNestedRow subEditor = (FieldGroupProfileEditorNestedRow) listContainer.getWidget(i);
			subEditor.setIndex(i);
		}
	}

	public void setEdited(boolean isEdited) {

		List<FieldGroupProfileEditorNestedRow> editors = editor.getEditors();
		if (editors != null && editors.size() > 0) {
			for (FieldGroupProfileEditorNestedRow subEditor : editors)
				subEditor.setEdited(isEdited);
		}
		readAllCheckBox.setVisible(isEdited);
		writeAllCheckBox.setVisible(isEdited);
		exportAllCheckBox.setVisible(isEdited);
	}

	public void computeVisibility(ImogField<?> source, boolean allValidation) {

		List<FieldGroupProfileEditorNestedRow> editors = editor.getEditors();
		if (editors != null && editors.size() > 0) {
			for (FieldGroupProfileEditorNestedRow subEditor : editors)
				subEditor.computeVisibility(source, allValidation);
		}
	}

	@UiHandler("readAllCheckBox")
	public void readAllClick(ValueChangeEvent<Boolean> e) {
		Boolean chkValue = e.getValue();
		if (chkValue != null)
			setBoxesValues(chkValue, READ);
	}

	@UiHandler("writeAllCheckBox")
	public void writeAllClick(ValueChangeEvent<Boolean> e) {
		Boolean chkValue = e.getValue();
		if (chkValue != null)
			setBoxesValues(chkValue, WRITE);
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

		List<FieldGroupProfileEditorNestedRow> editors = editor.getEditors();
		if (editors != null && editors.size() > 0) {
			for (FieldGroupProfileEditorNestedRow subEditor : editors) {
				if (type.equals(READ))
					subEditor.setReadBoxValue(value);
				else if (type.equals(WRITE))
					subEditor.setWriteBoxValue(value);
				else if (type.equals(EXPORT))
					subEditor.setExportBoxValue(value);
			}
		}
	}

	/**
	 * Validate nested forms fields values
	 */
	public void validateFields() {

		List<FieldGroupProfileEditorNestedRow> editors = editor.getEditors();
		if (editors != null && editors.size() > 0) {
			for (FieldGroupProfileEditorNestedRow subEditor : editors)
				subEditor.validateFields();
		}
	}

	/**
	 * Internal class
	 */
	private class FieldGroupProfileListEditorSource extends EditorSource<FieldGroupProfileEditorNestedRow> {

		@Override
		public FieldGroupProfileEditorNestedRow create(int index) {

			final FieldGroupProfileEditorNestedRow subEditor = new FieldGroupProfileEditorNestedRow(requestFactory, renderer);
			subEditor.setIndex(index);
			listContainer.insert(subEditor, index);
			return subEditor;
		}

		@Override
		public void dispose(FieldGroupProfileEditorNestedRow subEditor) {
			subEditor.removeFromParent();
		}

		@Override
		public void setIndex(FieldGroupProfileEditorNestedRow subEditor, int index) {
			listContainer.insert(subEditor, index);
		}
	}
}
