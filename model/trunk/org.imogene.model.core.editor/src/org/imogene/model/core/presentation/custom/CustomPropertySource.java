package org.imogene.model.core.presentation.custom;

import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.ui.provider.PropertySource;
import org.eclipse.ui.views.properties.IPropertyDescriptor;


/**
 * 
 * @author MEDES-IMPS
 *
 */
public class CustomPropertySource extends PropertySource {

	/**
	 * 
	 * @param object
	 * @param itemPropertySource
	 */
	public CustomPropertySource(Object object, IItemPropertySource itemPropertySource){
		super(object, itemPropertySource);
	}

	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.edit.ui.provider.PropertySource#createPropertyDescriptor(org.eclipse.emf.edit.provider.IItemPropertyDescriptor)
	 */
	@Override
	protected IPropertyDescriptor createPropertyDescriptor(
			IItemPropertyDescriptor itemPropertyDescriptor) {
		
		return new CustomPropertyDescriptor(object, itemPropertyDescriptor);
	}
	
	
	
}
