package org.imogene.sync.client.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.imogene.sync.client.SyncActivator;
import org.imogene.sync.client.ui.ISyncConstants;

public class TerminalServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1233790010726922272L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String terminal = SyncActivator.getDefault().getPreferenceStore().getString(ISyncConstants.SYNC_TERMINAL);
		ServletOutputStream os = resp.getOutputStream();
		os.print(terminal);
	}

}
