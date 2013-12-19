package org.imogene.lib.sync.server.util;

import javax.servlet.http.HttpSession;

import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.security.AccessPolicy;
import org.imogene.lib.common.security.AccessPolicyFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class HttpSessionUtil {

	private static String SESSION_USER = "session_user";
	private static String SESSION_SECURITY_POLICY = "session_security_policy";

	/**
	 * Gets the current HttpSession
	 * 
	 * @return the current HttpSession
	 */
	private static HttpSession getHttpSession() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);
		return session;
	}

	public static String getHttpSessionId() {
		return getHttpSession().getId();
	}

	public static void invalidate() {
		getHttpSession().invalidate();
	}

	public static ImogActor getCurrentUser() {
		HttpSession session = HttpSessionUtil.getHttpSession();
		if (session != null) {
			return (ImogActor) session.getAttribute(SESSION_USER);
		}
		return null;
	}

	public static void setCurrentUser(ImogActor actor) {
		HttpSession session = getHttpSession();
		if (session != null) {
			session.setAttribute(SESSION_USER, actor);
		}
	}

	public static void setAccessPolicy(AccessPolicy policy) {
		HttpSession session = getHttpSession();
		if (session != null) {
			session.setAttribute(SESSION_SECURITY_POLICY, policy);
		}
	}

	public static void setAccessPolicy(AccessPolicyFactory factory, ImogActor actor) {
		setAccessPolicy(factory.create(actor));
	}

	public static AccessPolicy getAccessPolicy() {
		HttpSession session = getHttpSession();
		if (session != null) {
			return (AccessPolicy) session.getAttribute(SESSION_SECURITY_POLICY);
		}
		return null;
	}

}
