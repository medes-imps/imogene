package org.imogene.web.server.security;

import java.util.Date;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.apache.tomcat.util.codec.binary.Base64;
import org.imogene.admin.server.security.SecurityUtils;
import org.imogene.lib.common.constants.NotificationConstants;
import org.imogene.lib.common.dao.GenericDao;
import org.imogene.lib.common.dao.GenericDaoImpl;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.entity.ImogActorImpl;
import org.imogene.lib.common.user.DefaultUser;
import org.imogene.web.server.util.OgnlUtil;
import org.imogene.web.server.util.SMTPSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PasswordRecoveryController {

	private Logger logger = LoggerFactory.getLogger(PasswordRecoveryController.class);

	public static final long TIMEOUT = 60 * 60 * 1000;

	public static final String RECOVERY_TOKEN = "t";
	
	public static final String LOCALE_PARAM = "locale";

	public static final String PASSWORD_CONSTRAINT_DEFAULT = "^\\w{8,}$";

	private String frontendUrl;

	private String applicationName;

	private SMTPSender smtpSender;

	private GenericDao genericDao;

	private String constraintRegex = PASSWORD_CONSTRAINT_DEFAULT;

	/**
	 * @param model
	 * @return
	 */
	@Transactional
	@RequestMapping(value = "requestpwd", method = RequestMethod.GET)
	public String requestPasswordForm(Model model) {
		model.addAttribute("user", new RequestUser());
		return "request_password";
	}

	/**
	 * @param user
	 * @param req
	 * @param resp
	 * @param model
	 * @return
	 */
	@Transactional
	@RequestMapping(value = "requestpwd", method = RequestMethod.POST)
	public String requestPassword(@ModelAttribute("request") RequestUser user, HttpServletRequest req, HttpServletResponse resp, Model model) {

		if (user.getUsername() != null) {
			String username = user.getUsername();
			String locale = user.getLocale();
			PasswordRecovery pr = new PasswordRecovery();
			String token = UUID.randomUUID().toString() + "-" + username;
			pr.setToken(Base64.encodeBase64String(token.getBytes()));
			pr.setUserId(username);
			pr.setRequested(new Date());
			genericDao.saveOrUpdate(pr);

			// notify the user.
			sendEmailWithToken(pr.getToken(), username, locale);
			user.sent = true;
			model.addAttribute("user", user);
			return "request_password";
		} else {
			resp.setStatus(HttpStatus.SC_BAD_GATEWAY);
			return "";
		}
	}

	/**
	 * @param token
	 * @param login
	 */
	private void sendEmailWithToken(String token, String login, String locale) {
		ImogActor actor = getActorFromLogin(login);
		String email = null;
		if(actor instanceof DefaultUser)
			email = ((DefaultUser)actor).getEmail();
		else
			email = actor.getNotificationData(NotificationConstants.MailMethod);
		
		if(email!=null && !email.isEmpty()) {
			try {
				smtpSender.sendMessage("[" + applicationName + "] - " + getSmsTitle(locale), getSmsContent(token, locale), new String[] { email });
			} catch (Exception e) {
				logger.error("Error sending email for password recovery.", e);
			}
		}
	}

	/**
	 * @param token
	 * @param locale
	 * @return
	 */
	private String getSmsContent(String token, String locale) {
		MessageVariable mv = new MessageVariable();
		mv.setUrl(getUrl(token, locale));
		mv.setApplicationName(applicationName);

		if (locale != null && locale.startsWith("fr"))
			return OgnlUtil.readTemplate("/recovery_password_mail_fr.tpl", mv);
		else
			return OgnlUtil.readTemplate("/recovery_password_mail_en.tpl", mv);
	}

	/**
	 * @param locale
	 * @return
	 */
	private String getSmsTitle(String locale) {
		String propertyFile = "org.imogene.web.client.i18n.ImogConstants";
		ResourceBundle rb = null;
		if (locale != null) {
			if (locale.length() == 2) {
				rb = ResourceBundle.getBundle(propertyFile + "_" + locale);
			} else if (locale.length() == 5)
				rb = ResourceBundle.getBundle(propertyFile + "_" + locale.substring(0, 2));
			else
				rb = ResourceBundle.getBundle(propertyFile);
		} else
			rb = ResourceBundle.getBundle(propertyFile);

		return rb.getString("pass_recovery_sms_title");
	}

	/**
	 * @param token
	 * @return
	 */
	private String getUrl(String token, String locale) {
		return frontendUrl.endsWith("/") ? frontendUrl + "resetpwd?t=" + token : frontendUrl + "/resetpwd?t=" + token + "&" + LOCALE_PARAM + "=" + locale;
	}

	/**
	 * @param req
	 * @param resp
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "resetpwd", method = RequestMethod.GET)
	public String resetPassword(HttpServletRequest req, HttpServletResponse resp, Model model) {
		String token = req.getParameter(RECOVERY_TOKEN);
		if (token != null) {
			PasswordRecovery pwr = loadRecoveryRequest(token);
			if (isValid(pwr)) {
				PasswordRequest pr = new PasswordRequest();
				pr.setToken(token);
				model.addAttribute("request", pr);
				return "reset_password";
			} else {
				return "timeout";
			}
		}
		resp.setStatus(404);
		return null;
	}

	/**
	 * @param pr
	 * @param model
	 * @param resp
	 * @return
	 */
	@Transactional
	@RequestMapping(value = "resetpwd", method = RequestMethod.POST)
	public String newPassword(@ModelAttribute("request") PasswordRequest pr, Model model, HttpServletResponse resp) {

		PasswordRecovery recovery = genericDao.load(PasswordRecovery.class, pr.getToken());
		if (recovery != null) {

			if (pr.getPassword() != null && pr.getPassword().matches(constraintRegex)) {
				// update the user password
				ImogActor actor = getActorFromLogin(recovery.getUserId());
				String hash = SecurityUtils.passwordHashAsBase64(pr.getPassword(), recovery.getUserId());
				actor.setPassword(hash);
				genericDao.saveOrUpdate(actor);
				String locale = pr.getLocale();

				// delete the request
				recovery.setModified(new Date());
				genericDao.saveOrUpdate(recovery);

				return "redirect:/jsp/Login.jsp" + "?" + LOCALE_PARAM + "=" + locale;
			}
			resp.setStatus(HttpStatus.SC_BAD_GATEWAY);
		}
		return null;
	}

	/**
	 * @param login
	 * @return
	 */
	public ImogActor getActorFromLogin(String login) {
		ImogActorImpl actor = (ImogActorImpl) genericDao.loadFromLogin(login);
		return actor;
	}

	/**
	 * @param token
	 * @return
	 */
	private PasswordRecovery loadRecoveryRequest(String token) {
		return genericDao.load(PasswordRecovery.class, token);
	}

	/**
	 * @param pr
	 * @return
	 */
	private boolean isValid(PasswordRecovery pr) {
		Date now = new Date();
		long delay = now.getTime() - pr.getRequested().getTime();
		boolean isValid = delay < TIMEOUT;
		return isValid && pr.getModified() == null;
	}

	public void setGenericDao(GenericDaoImpl genericDao) {
		this.genericDao = genericDao;
	}

	public void setSmtpSender(SMTPSender sender) {
		this.smtpSender = sender;
	}

	public void setFrontendUrl(String url) {
		this.frontendUrl = url;
	}

	public void setConstraintRegex(String regex) {
		constraintRegex = regex;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	/* INTERNAL CLASSES */

	private static class MessageVariable {

		private String url;

		private String applicationName;

		public void setUrl(String url) {
			this.url = url;
		}

		public String getUrl() {
			return url;
		}

		public String getApplicationName() {
			return applicationName;
		}

		public void setApplicationName(String applicationName) {
			this.applicationName = applicationName;
		}
	}

	/**
	 * Request a new password
	 */
	public static class RequestUser {

		private String username;

		private String locale;

		private boolean sent = false;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public boolean isSent() {
			return sent;
		}

		public void setSent(boolean sent) {
			this.sent = sent;
		}

		public String getLocale() {
			return locale;
		}

		public void setLocale(String locale) {
			this.locale = locale;
		}
	}

	/**
	 * Recovery request information
	 */
	public static class PasswordRequest {

		private String token;

		private String password;

		private String confirm;
		
		private String locale;

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getConfirm() {
			return confirm;
		}

		public void setConfirm(String confirm) {
			this.confirm = confirm;
		}
		
		public String getLocale() {
			return locale;
		}

		public void setLocale(String locale) {
			this.locale = locale;
		}

	}

}
