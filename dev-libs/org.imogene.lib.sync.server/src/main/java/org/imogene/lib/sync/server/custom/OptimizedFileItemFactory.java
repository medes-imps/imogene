package org.imogene.lib.sync.server.custom;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;


public class OptimizedFileItemFactory extends DiskFileItemFactory {

	@Override
	public FileItem createItem(String fieldName, String contentType,
			boolean isFormField, String fileName) {		
		if("data".equals(fieldName))
			return new OptimizedFileItem(fieldName, contentType, isFormField, fileName,-1, getRepository());
		return super.createItem(fieldName, contentType, isFormField, fileName);		
	}
	
}
