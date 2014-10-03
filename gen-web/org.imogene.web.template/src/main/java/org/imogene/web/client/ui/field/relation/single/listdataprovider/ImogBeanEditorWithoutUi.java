package org.imogene.web.client.ui.field.relation.single.listdataprovider;

import org.imogene.web.shared.proxy.ImogBeanProxy;

import com.google.gwt.editor.client.LeafValueEditor;

public class ImogBeanEditorWithoutUi<T extends ImogBeanProxy>  implements LeafValueEditor<T>{


	private T value;


	public ImogBeanEditorWithoutUi() {
	}
		

	/**
	 * Get the value
	 * @return the value
	 */
	public T getValue(){		
		return value;
	}
	
	
	/**
	 * Sets the value 
	 * @param value the value 
	 */
	public void setValue(T value){
		this.value = value;
	}
	

}
