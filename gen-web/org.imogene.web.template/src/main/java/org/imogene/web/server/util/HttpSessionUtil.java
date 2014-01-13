package org.imogene.web.server.util;

import javax.servlet.http.HttpSession;

import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.security.AccessPolicy;
import org.imogene.lib.common.security.AccessPolicyFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Http session tools to access attached session objects and perform session changes
 * 
 * @author MEDES-IMPS
 * 
 */
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

	/**
	 * Get the current http session identifier
	 * 
	 * @return the current http session identifier
	 */
	public static String getHttpSessionId() {
		return getHttpSession().getId();
	}

	/**
	 * Invalidate the current http session
	 */
	public static void invalidate() {
		getHttpSession().invalidate();
	}

	/**
	 * Get the current user attached to the current http session
	 * 
	 * @return the current user
	 */
	public static ImogActor getCurrentUser() {
		HttpSession session = HttpSessionUtil.getHttpSession();
		if (session != null) {
			return (ImogActor) session.getAttribute(SESSION_USER);
		}
		return null;
	}

	/**
	 * Attach the given user to the current http session
	 * 
	 * @param actor the current user
	 */
	public static void setCurrentUser(ImogActor actor) {
		HttpSession session = getHttpSession();
		if (session != null) {
			session.setAttribute(SESSION_USER, actor);
		}
	}

	/**
	 * Attach the given access policy to the current http session
	 * 
	 * @param policy the access policy
	 */
	public static void setAccessPolicy(AccessPolicy policy) {
		HttpSession session = getHttpSession();
		if (session != null) {
			session.setAttribute(SESSION_SECURITY_POLICY, policy);
		}
	}

	/**
	 * Attach the current access policy given a factory and a user
	 * 
	 * @param factory The access policy factory
	 * @param actor The actor to create the access policy with
	 */
	public static void setAccessPolicy(AccessPolicyFactory factory, ImogActor actor) {
		setAccessPolicy(factory.create(actor));
	}

	/**
	 * Get the access policy attached to the current http session
	 * 
	 * @return the current access policy
	 */
	public static AccessPolicy getAccessPolicy() {
		HttpSession session = getHttpSession();
		if (session != null) {
			return (AccessPolicy) session.getAttribute(SESSION_SECURITY_POLICY);
		}
		return null;
	}

}
