package org.imogene.web.server.servlet;

import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.imogene.web.server.service.BinaryUploadService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * This servlet enables to handle binary file upload for the Imogene platform.
 * 
 * @author Medes-IMPS
 */
public class BinaryUploadServlet extends UploadAction {

	/** serial version */
	private static final long serialVersionUID = 6429923373802231312L;

	private BinaryUploadService binaryUploadService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(config
				.getServletContext());
		binaryUploadService = (BinaryUploadService) wac.getBean("binaryUploadService");
		// videoConverter = (MediaConverter) wac.getBean("videoConverter");
	}

	/**
	 * Copy the uploaded file in the correct folder, Create the binary entity associated.
	 */
	@Override
	public String executeAction(HttpServletRequest request, List<FileItem> sessionFiles) throws UploadActionException {
		String entityId = binaryUploadService.executeAction(request, sessionFiles);
		removeSessionFileItems(request);
		return entityId;
	}

	/**
	 * Get the content of an uploaded file.
	 */
	@Override
	public void getUploadedFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		binaryUploadService.getUploadedFile(request, response);
	}

	/**
	 * Remove a file when the user sends a request
	 */
	@Override
	public void removeItem(HttpServletRequest request, FileItem item) throws UploadActionException {
		// getLocalFile(item.getName()).delete();
	}

}
