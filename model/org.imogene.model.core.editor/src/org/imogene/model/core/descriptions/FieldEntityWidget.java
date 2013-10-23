package org.imogene.model.core.descriptions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.imogene.model.core.Description;
import org.imogene.model.core.FieldEntity;

public class FieldEntityWidget extends Composite implements MyItem {
	
	private FieldEntity fieldEntity;
	private Group descriptions;
	private EditingDomain editingDomain;
	private List<MyItem> children;

	public FieldEntityWidget(Composite parent, int style) {
		super(parent, style);
	}
	
	public void setFieldEntity(FieldEntity fieldEntity) {
		this.fieldEntity = fieldEntity;
		onFieldEntityChanged();
	}
	
	public void setEditingDomain(EditingDomain editingDomain) {
		this.editingDomain = editingDomain;
	}
	
	protected void onFieldEntityChanged() {
		if (descriptions != null) {
			descriptions.dispose();
		}
		if (children != null) {
			children.clear();
		}
 		createComposite();
 		Notifier.getInstance().layout(fieldEntity.getParentCard());
	}
	
	private void createComposite() {
		setLayout(new GridLayout());
		
		descriptions = new Group(this, SWT.NONE);
		descriptions.setText(fieldEntity.getName() != null ? fieldEntity.getName() : "");
		descriptions.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		descriptions.setLayout(new GridLayout(2, false));
		
		for (Description description : fieldEntity.getDescriptions()) {
			LabelWidget label = new LabelWidget(descriptions, SWT.NONE);
			label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
			label.setDescription(description);
			
			DescriptionWidget text = new DescriptionWidget(descriptions, SWT.NONE);
			text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			text.setEditingDomain(editingDomain);
			text.setLabel(label);
			text.setDescription(description);

			if (children == null) {
				children = new ArrayList<MyItem>();
			}
			children.add(text);
		}
		
	}
	
	@Override
	public void updateItem() {
		descriptions.setText(fieldEntity.getName() != null ? fieldEntity.getName() : "");
		descriptions.layout(true, true);
		Notifier.getInstance().layout(fieldEntity.getParentCard());
	}
	
	@Override
	public void refreshItem() {
		onFieldEntityChanged();
	}
	
	@Override
	public List<MyItem> getChildrenItem() {
		if (children != null) {
			return new ArrayList<MyItem>(children);
		}
		return null;
	}
	
	public EObject getEObject() {
		return fieldEntity;
	};
	
	@Override
	public boolean hasChildren() {
		return children != null && children.size() > 0;
	}
	
}
