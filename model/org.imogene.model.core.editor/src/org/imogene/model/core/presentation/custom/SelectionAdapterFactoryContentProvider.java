package org.imogene.model.core.presentation.custom;

import java.util.List;
import java.util.Vector;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.imogene.model.core.ActorFilter;

public class SelectionAdapterFactoryContentProvider extends AdapterFactoryContentProvider {

	public SelectionAdapterFactoryContentProvider(AdapterFactory adapterFactory){
		super(adapterFactory);
	}

	@Override
	public Object[] getChildren(Object object) {
				
			List<Object> result = new Vector<Object>();
			Object[] superResult = super.getChildren(object);
			for(Object obj:superResult){
				if(!(obj instanceof ActorFilter))
					result.add(obj);
			}
			return result.toArray();		
	}

	@Override
	public Object[] getElements(Object object) {		
		
			List<Object> result = new Vector<Object>();
			Object[] superResult = super.getElements(object);
			for(Object obj:superResult){
				if(!(obj instanceof ActorFilter)){
					result.add(obj);
				}
			}
			return result.toArray();		
	}
	
	
}
