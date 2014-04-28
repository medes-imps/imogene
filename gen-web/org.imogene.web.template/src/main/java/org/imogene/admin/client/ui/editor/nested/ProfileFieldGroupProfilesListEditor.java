package org.imogene.admin.client.ui.editor.nested;

import java.util.ArrayList;
import java.util.List;

import org.imogene.admin.client.i18n.AdminNLS;
import org.imogene.admin.shared.AdminRequestFactory;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.field.ImogField;
import org.imogene.web.client.ui.field.group.FieldGroupPanel;
import org.imogene.web.client.util.ImogKeyGenerator;
import org.imogene.web.shared.proxy.FieldGroupProfileProxy;
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
 * Editor that provides the UI components to manage the list of FieldGroupProfileEditorNestedRow in the Profile editor
 * 
 * @author MEDES-IMPS
 */
public class ProfileFieldGroupProfilesListEditor extends Composite implements
		IsEditor<ListEditor<FieldGroupProfileProxy, FieldGroupProfileEditorNestedRow>> {

	private static EditorUiBinder uiBinder = GWT.create(EditorUiBinder.class);

	interface EditorUiBinder extends UiBinder<Widget, ProfileFieldGroupProfilesListEditor> {
	}

	protected final AdminRequestFactory requestFactory;
	private FieldGroupProfileListEditorSource editorSource;
	private ListEditor<FieldGroupProfileProxy, FieldGroupProfileEditorNestedRow> editor;
	// private ListEditor<FieldGroupProfileProxy, FieldGroupProfileEditorNestedForm> editor;
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
	Label cardEntityLabel;
	@UiField
	@Ignore
	Label fieldGroupLabel;
	@UiField
	@Ignore
	Label readLabel;
	@UiField
	@Ignore
	Label writeLabel;
	@UiField
	@Ignore
	Label exportLabel;

	public ProfileFieldGroupProfilesListEditor(AdminRequestFactory factory) {

		this.requestFactory = factory;
		editorSource = new FieldGroupProfileListEditorSource();
		editor = ListEditor.of(editorSource);

		listContainer = new VerticalPanel();
		addItem = new Image(GWT.getModuleBaseURL() + "images/relation_add.png");
		addItem.setTitle(BaseNLS.constants().button_add());

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

	/**
	 * Remove the FieldGroupProfile at the specified index
	 * 
	 * @param index of the FieldGroupProfile
	 */
	private void remove(int index) {
		editor.getList().remove(index);
	}

	/**
	 * Get the FieldGroupProfile at the specified index
	 * 
	 * @param index of the FieldGroupProfile
	 */
	private FieldGroupProfileProxy getProxy(int index) {
		return editor.getList().get(index);
	}

	@Override
	public ListEditor<FieldGroupProfileProxy, FieldGroupProfileEditorNestedRow> asEditor() {
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
		FieldGroupProfileProxy newFieldGroupProfile = request.create(FieldGroupProfileProxy.class);
		newFieldGroupProfile.setId(ImogKeyGenerator.generateKeyId("PRO_GRP"));
		newFieldGroupProfile.setVersion(0);
		// request.saveFieldGroupProfiles(newFieldGroupProfile, true);

		addValue(newFieldGroupProfile, true);
	}

	/**
	 * Adds a list of values to the editor list
	 */
	private void addValue(FieldGroupProfileProxy value, boolean isNew) {
		if (value != null) {
			if (editor.getList() == null)
				editor.setValue(new ArrayList<FieldGroupProfileProxy>());
			editor.getList().add(value);
			updateIndex();

			// update subeditor
			List<FieldGroupProfileEditorNestedRow> editors = editor.getEditors();
			FieldGroupProfileEditorNestedRow subEditor = editors.get(editors.size() - 1);
			subEditor.setNewProxy(isNew);
			subEditor.computeVisibility(null, true);
			subEditor.setEdited(true);
		}
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

	public void setRequestContextForListEditors(ImogEntityRequest ctx) {
		this.request = ctx;
	}

	public void setEdited(boolean isEdited) {

		List<FieldGroupProfileEditorNestedRow> editors = editor.getEditors();
		if (editors != null && editors.size() > 0) {
			for (FieldGroupProfileEditorNestedRow subEditor : editors)
				subEditor.setEdited(isEdited);
		}
		addItem.setVisible(isEdited);
	}

	public void computeVisibility(ImogField<?> source, boolean allValidation) {

		List<FieldGroupProfileEditorNestedRow> editors = editor.getEditors();
		if (editors != null && editors.size() > 0) {
			for (FieldGroupProfileEditorNestedRow subEditor : editors)
				subEditor.computeVisibility(source, allValidation);
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

			final FieldGroupProfileEditorNestedRow subEditor = new FieldGroupProfileEditorNestedRow(requestFactory);
			subEditor.setIndex(index);
			listContainer.insert(subEditor, index);

			subEditor.setDeleteClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (Window.confirm(BaseNLS.constants().confirmation_delete())) {
						FieldGroupProfileProxy proxy = getProxy(subEditor.getIndex());
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
		public void dispose(FieldGroupProfileEditorNestedRow subEditor) {
			subEditor.removeFromParent();
		}

		@Override
		public void setIndex(FieldGroupProfileEditorNestedRow subEditor, int index) {
			listContainer.insert(subEditor, index);
		}
	}
}
