package org.imogene.model.core.descriptions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;
import org.imogene.model.core.CardEntity;
import org.imogene.model.core.Project;

public class ProjectWidget extends CTabFolder implements MyItem {
	
	private List<MyItem> children;
	private Project project;
	private EditingDomain editingDomain;

	public ProjectWidget(Composite parent, int style) {
		super(parent, style);
		GridData tabData = new GridData(GridData.FILL_BOTH | GridData.GRAB_VERTICAL);
		tabData.horizontalSpan = 2;
		setLayoutData(tabData);
	}
	
	public void setEditingDomain(EditingDomain editingDomain) {
		this.editingDomain = editingDomain;
	}
	
	public void setProject(Project project) {
		this.project = project;
		onProjectChanged();
	}
	
	protected void onProjectChanged() {
		if (children != null) {
			for (MyItem item : children) {
				if (item instanceof Widget) {
					((Widget) item).dispose();
				}
			}
			children.clear();
		}
		createTabs();
	}
	
	private void createTabs() {
		Notifier.getInstance().clear();
		for (CardEntity entity : project.getEntities()) {
			CardEntityWidget viewer = new CardEntityWidget(this, SWT.FLAT);
			viewer.setEditingDomain(editingDomain);
			viewer.setCardEntity(entity);
			
			if (children == null) {
				children = new ArrayList<MyItem>();
			}
			children.add(viewer);
		}
	}
	
	@Override
	public void refreshItem() {
		onProjectChanged();
	}
	
	@Override
	public void updateItem() {
		
	}
	
	@Override
	public List<MyItem> getChildrenItem() {
		if (children != null) {
			return new ArrayList<MyItem>(children);
		}
		return null;
	}
	
	public EObject getEObject() {
		return project;
	};
	
	@Override
	public boolean hasChildren() {
		return children != null && children.size() > 0;
	}
	
}
