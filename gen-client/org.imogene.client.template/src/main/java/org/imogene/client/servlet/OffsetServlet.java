package org.imogene.client.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.imogene.client.Activator;
import org.imogene.client.ui.ISyncConstants;

public class OffsetServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2882029414414479473L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long offset = Activator.getDefault().getPreferenceStore().getLong(ISyncConstants.NTP_OFFSET);
		ServletOutputStream os = resp.getOutputStream();
		os.print(offset);
	}

}
