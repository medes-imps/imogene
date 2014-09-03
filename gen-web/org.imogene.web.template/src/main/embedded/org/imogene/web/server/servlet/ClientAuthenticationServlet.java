package org.imogene.web.server.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.imogene.web.server.sync.SynchronizationHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ClientAuthenticationServlet extends HttpServlet {

	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = 7673472386655120484L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		SynchronizationHelper helper = (SynchronizationHelper) context.getBean("synchronizationScheduler");

		String url = req.getParameter("j_url");
		String login = req.getParameter("j_username");
		String password = req.getParameter("j_password");

		try {
			helper.init(url, login, password);
		} finally {
			// Redirect to login page
			resp.sendRedirect(getServletContext().getContextPath());
		}
	}
}
