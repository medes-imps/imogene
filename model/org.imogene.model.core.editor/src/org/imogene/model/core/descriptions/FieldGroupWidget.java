package org.imogene.model.core.descriptions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.widgets.Section;
import org.imogene.model.core.FieldEntity;
import org.imogene.model.core.FieldGroup;

public class FieldGroupWidget extends Composite implements MyItem {
	
	private List<MyItem> children;
	private FieldGroup fieldGroup;
	private EditingDomain editingDomain;

	public FieldGroupWidget(Composite parent, int style) {
		super(parent, style);
	}
	
	public void setFieldGroup(FieldGroup fieldGroup) {
		this.fieldGroup = fieldGroup;
		onFieldGroupChanged();
	}
	
	public void setEditingDomain(EditingDomain editingDomain) {
		this.editingDomain = editingDomain;
	}
	
	protected void onFieldGroupChanged() {
		if (children != null) {
			for (MyItem item : children) {
				if (item instanceof Widget) {
					((Widget) item).dispose();
				}
			}
			children.clear();
		}
		createSection();
		Notifier.getInstance().layout(fieldGroup.getParentCard());
	}
	
	private void createSection() {
		setLayout(new GridLayout());
		
		FieldGroupDescriptionWidget v = new FieldGroupDescriptionWidget(this, SWT.NULL);
		v.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.FILL_HORIZONTAL));
		v.setEditingDomain(editingDomain);
		v.setFieldGroup(fieldGroup);
		
		if (children == null) {
			children = new ArrayList<MyItem>();
		}
		children.add(v);
		
		for (FieldEntity entity : fieldGroup.getFields()) {
			FieldEntityWidget viewer = new FieldEntityWidget(this, SWT.NULL);
			viewer.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.FILL_HORIZONTAL));
			viewer.setEditingDomain(editingDomain);
			viewer.setFieldEntity(entity);
			
			if (children == null) {
				children = new ArrayList<MyItem>();
			}
			children.add(viewer);
		}
	}
	
	@Override
	public void refreshItem() {
		onFieldGroupChanged();
	}

	@Override
	public void updateItem() {
		Composite parent = getParent();
		if (parent instanceof Section) {
			((Section) parent).setText(fieldGroup.getName() != null ? fieldGroup.getName() : "");
			parent.layout(true, true);
		}
	}
	
	@Override
	public List<MyItem> getChildrenItem() {
		if (children != null) {
			return new ArrayList<MyItem>(children);
		}
		return null;
	}
	
	public EObject getEObject() {
		return fieldGroup;
	};
	
	@Override
	public boolean hasChildren() {
		return children != null && children.size() > 0;
	}
	
}
