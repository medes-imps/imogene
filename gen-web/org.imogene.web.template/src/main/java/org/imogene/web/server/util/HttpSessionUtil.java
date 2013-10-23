package org.imogene.web.server.util;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class HttpSessionUtil {
	
	/**
	 * Gets the current HttpSession
	 * @return the current HttpSession
	 */
	public static HttpSession getHttpSession() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);
		return session;
	}

}
