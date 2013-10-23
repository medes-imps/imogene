package org.imogene.model.core.descriptions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;
import org.imogene.model.core.Project;

public class DescriptionViewer extends StructuredViewer {
	
	private ProjectWidget viewer;
	
	public DescriptionViewer(ProjectWidget viewer) {
		super();
		this.viewer = viewer;
		hookControl(viewer);
	}
	
	public void setEditingDomain(EditingDomain editingDomain) {
		viewer.setEditingDomain(editingDomain);
		setInput(editingDomain.getResourceSet().getResources().get(0).getContents().get(0));
	}
	
	@Override
	protected void inputChanged(Object input, Object oldInput) {
		if (input instanceof Project) {
			viewer.setProject((Project) input);
		}
	}

	@Override
	protected Widget doFindInputItem(Object element) {
		Object root = getRoot();
		if (root == null) {
			return null;
		}

		if (equals(root, element)) {
			return getControl();
		}
		return null;
	}

	@Override
	protected Widget doFindItem(Object element) {
		Object root = getRoot();
		if (root == null) {
			return null;
		}
		return internalFindItem(viewer, element);
	}
	
	private Widget internalFindItem(MyItem parent, Object element) {
		// compare with node
		Object data = parent.getEObject();
		if (data != null) {
			if (equals(data, element)) {
				return (Widget) parent;
			}
		}
		// recurse over children
		if (parent.hasChildren()) {
			List<MyItem> items = parent.getChildrenItem();
			for (MyItem item : items) {
				Widget o = internalFindItem(item, element);
				if (o != null) {
					return o;
				}
			}
		}
		return null;
	}

	@Override
	protected void doUpdateItem(Widget item, Object element, boolean fullMap) {
		if (item instanceof MyItem) {
			((MyItem) item).updateItem();
		}
	}

	@Override
	protected List getSelectionFromWidget() {
		return new ArrayList();
	}

	@Override
	protected void internalRefresh(Object element) {
		Widget item = findItem(element);
		if (item instanceof MyItem) {
			((MyItem) item).refreshItem();
		}
	}

	@Override
	public void reveal(Object element) {
	}
	
	@Override
	protected void setSelectionToWidget(List l, boolean reveal) {
	}

	@Override
	public Control getControl() {
		return viewer;
	}

}
