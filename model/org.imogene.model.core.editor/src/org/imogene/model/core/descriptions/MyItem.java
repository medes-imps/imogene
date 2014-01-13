package org.imogene.model.core.descriptions;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

public interface MyItem {
	
	EObject getEObject();

	boolean hasChildren();
	
	List<MyItem> getChildrenItem();
	
	void updateItem();
	
	void refreshItem();
	
}
