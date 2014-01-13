package org.imogene.model.core.descriptions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.imogene.model.core.CardEntity;
import org.imogene.model.core.FieldGroup;

public class CardEntityWidget extends CTabItem implements MyItem {
	
	private List<MyItem> children;
	private CardEntity cardEntity;
	private EditingDomain editingDomain;
	private ScrolledForm form;
	
	public CardEntityWidget(CTabFolder parent, int style) {
		super(parent, style);
	}
	
	public void setCardEntity(CardEntity cardEntity) {
		this.cardEntity = cardEntity;
		onCardEntityChanged();
	}
	
	public void setEditingDomain(EditingDomain editingDomain) {
		this.editingDomain = editingDomain;
	}
	
	protected void onCardEntityChanged() {
		if (form != null) {
			form.dispose();
		}
		if (children != null) {
			children.clear();
		}
		createTab();
		Notifier.getInstance().layout(cardEntity);
	}
	
	private void createTab() {
		FormToolkit toolkit = new FormToolkit(getDisplay());
		
		setText(cardEntity.getName() != null ? cardEntity.getName() : "");
		form = toolkit.createScrolledForm(getParent());
		form.getBody().setLayout(new GridLayout(2, true));
		
		Notifier.getInstance().addForm(cardEntity, form);
		
		for (FieldGroup group : cardEntity.getGroups()) {
			Section section = toolkit.createSection(form.getBody(), Section.TITLE_BAR | Section.EXPANDED);
			section.setText(group.getName() != null ? group.getName() : "");

			section.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_BEGINNING));
			
			FieldGroupWidget viewer = new FieldGroupWidget(section, SWT.NULL);
			toolkit.adapt(viewer);
			section.setClient(viewer);
			viewer.setEditingDomain(editingDomain);
			viewer.setFieldGroup(group);
			
			if (children == null) {
				children = new ArrayList<MyItem>();
			}
			children.add(viewer);
		}
		
		setControl(form);
	}
	
	@Override
	public void updateItem() {
		setText(cardEntity.getName() != null ? cardEntity.getName() : "");
	}
	
	@Override
	public void refreshItem() {
		onCardEntityChanged();
	}
	
	@Override
	public List<MyItem> getChildrenItem() {
		if (children != null) {
			return new ArrayList<MyItem>(children);
		}
		return null;
	}
	
	public EObject getEObject() {
		return cardEntity;
	};
	
	@Override
	public boolean hasChildren() {
		return children != null && children.size() > 0;
	}
	
}
