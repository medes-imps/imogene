package org.imogene.web.server.servlet;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.imogene.encryption.EncryptionManager;
import org.imogene.lib.sync.client.Synchronizer;
import org.imogene.lib.sync.client.params.SyncParams;
import org.imogene.web.server.handler.GenericHandler;
import org.imogene.web.server.scheduling.SynchronizationScheduler;
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
		GenericHandler handler = (GenericHandler) context.getBean("genericHandler");
		Synchronizer synchronizer = (Synchronizer) context.getBean("synchronizer");
		EncryptionManager encryptionManager = (EncryptionManager) context.getBean("encryptionManager");
		SynchronizationScheduler scheduler = (SynchronizationScheduler) context.getBean("synchronizationScheduler");

		String url = req.getParameter("j_url");
		String login = req.getParameter("j_username");
		String password = req.getParameter("j_password");

		try {
			// Try to authenticate
			if (synchronizer.authenticate(url, login, password) != Synchronizer.AUTH_SUCCESS) {
				return;
			}
			// Run a synchronization just after
			String terminal = UUID.randomUUID().toString();
			if (synchronizer.synchronize(url, login, password, terminal, null) != Synchronizer.SYNC_SUCCESS) {
				return;
			}
			// If succeed register sync parameters
			SyncParams params = handler.find(SyncParams.class, SyncParams.ID);
			if (params == null) {
				params = new SyncParams();
			}
			params.setLogin(login);
			params.setUrl(url);
			params.setPassword(new String(Base64.encodeBase64(encryptionManager.encrypt(password.getBytes()))));
			params.setTerminal(terminal);
			handler.save(params);
			scheduler.schedule();
		} finally {
			// Redirect to login page
			resp.sendRedirect(getServletContext().getContextPath());
		}
	}
}
