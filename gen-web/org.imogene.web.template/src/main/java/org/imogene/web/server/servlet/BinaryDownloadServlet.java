package org.imogene.web.server.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.imogene.web.server.service.BinaryDownloadService;
import org.imogene.web.server.util.ExportedPropertiesHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet that enables to access a binary file for download.
 * @author Medes-IMPS
 */
public class BinaryDownloadServlet extends HttpServlet {

	/**
	 */
	private static final long serialVersionUID = 3593916311143354739L;

	private static final String PATH_PARAM = "binaryPath";

	private BinaryDownloadService binaryDownloadService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		binaryDownloadService = (BinaryDownloadService) wac.getBean("binaryDownloadService");
		initBinaryPath(wac);
	}

	/**
	 * Set the binary path used by this application
	 */
	private void initBinaryPath(WebApplicationContext wac) {
		ExportedPropertiesHolder eph = (ExportedPropertiesHolder) wac.getBean("exportedProperties");
		binaryDownloadService.setBinaryPath(eph.getProperties().get(PATH_PARAM));
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		binaryDownloadService.doGet(req, resp);
	}
	
}