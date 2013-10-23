package org.imogene.lib.sync.server.custom;

import java.io.File;

import javax.servlet.ServletContext;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class OptimizedMultiPartResolver extends CommonsMultipartResolver{
	
	
	@Override
	public void setServletContext(ServletContext servletContext) {		
		getFileItemFactory().setRepository(getSyncWorkDirectory(servletContext));        
	}

	@Override
	protected DiskFileItemFactory newFileItemFactory() {		
		return new OptimizedFileItemFactory();
	}
	
	/**
	 * Create the sync work directory
	 * @param req the request.
	 * @return the sync work directory
	 */
	private File getSyncWorkDirectory(ServletContext context){		
		String rootPath = context.getRealPath("WEB-INF");
		File syncWork = new File(rootPath+"/syncWork");
		syncWork.mkdir();
		return syncWork;
	}
}
