package org.imogene.web.server.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.imogene.encryption.EncryptionManager;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.role.ImogRole;
import org.imogene.web.server.handler.GenericHandler;
import org.imogene.web.server.identity.IdentityConstants;
import org.imogene.web.server.util.FormUtil;
import org.imogene.web.server.util.ServerConstants;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet that enables to encrypt an identity file for download.
 * @author Medes-IMPS 
 */
public class EncryptionServlet extends HttpServlet {

	private static final long serialVersionUID = 5893277007596990281L;

	private final static String PARAM_TYPE = "type";
	private final static String PARAM_ID = "id";

	/* dao part */
	private GenericHandler genericHandler;

	/* encryptionManager for encrypting id file */
	private EncryptionManager encryptionManager;
	
	private FormUtil formUtil;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		genericHandler = (GenericHandler) wac.getBean("genericHandler");
		encryptionManager = (EncryptionManager) wac.getBean("encryptionManager");
		formUtil = (FormUtil) wac.getBean("formUtil");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getSession().getAttribute(ServerConstants.SESSION_USER) != null) {
			String actorShortName = req.getParameter(PARAM_TYPE);
			String actorId = req.getParameter(PARAM_ID);
			Class<?> actorClass = getActorClass(actorShortName);
			if (actorClass != null) {
				ImogActor actor = (ImogActor) genericHandler.find(actorClass, actorId);
				if (actor != null) {
					StringBuffer imogId = new StringBuffer();
					imogId.append(IdentityConstants.KEY_TYPE + "=").append(actorClass.getName());
					imogId.append("\n");
					imogId.append(IdentityConstants.KEY_ID + "=").append(actor.getId());
					imogId.append("\n");
					imogId.append(IdentityConstants.KEY_LOGIN + "=").append(actor.getLogin());
					imogId.append("\n");
					imogId.append(IdentityConstants.KEY_PASSWORD + "=").append(actor.getPassword());
					imogId.append("\n");
					imogId.append(IdentityConstants.KEY_ROLES + "=");
					if (actor.getRoles() != null && actor.getRoles().size() > 0) {
						StringBuffer roles = new StringBuffer();
						for (ImogRole role : actor.getRoles()) {
							roles.append(role.getId());
							roles.append(IdentityConstants.ROLE_SEPARATOR);
						}
						imogId.append(roles.toString());
					}
					imogId.append("\n");
					imogId.append("\n");
					imogId.append("/*------- end of the id file -------*/");
					resp.setHeader("Content-Disposition", "attachment; filename=\"" + actor.getId() + ".imogid" + "\"");
					resp.setContentType("application/octet-stream");
					OutputStream out = encryptionManager.getEncryptedOutputStream(resp.getOutputStream());

					out.write(imogId.toString().getBytes());
				}
			} else {
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			}

		} else {
			resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
	}

	/**
	 * Get the actor class from the its short name
	 * @param shortName the actor short name
	 * @return the actor class or null if it doesn't exist
	 */
	private Class<?> getActorClass(String shortName) {
		try {
			return Class.forName(formUtil.getActorClassName(shortName));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
