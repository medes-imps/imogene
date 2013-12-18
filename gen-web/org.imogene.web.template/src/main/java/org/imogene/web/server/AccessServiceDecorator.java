package org.imogene.web.server;

import javax.servlet.http.HttpSession;

import org.imogene.web.server.security.AccessPolicy;
import org.imogene.web.server.util.HttpSessionUtil;
import org.imogene.web.server.util.ServerConstants;

import com.google.web.bindery.requestfactory.server.ServiceLayerDecorator;

/**
 * Service Decorator to control the access to the domain objects
 * 
 * @author Medes-IMPS
 */
public class AccessServiceDecorator extends ServiceLayerDecorator {

	@Override
	public Object getProperty(Object bean, String property) {
		AccessPolicy policy = getPolicy();
		if (policy != null && policy.canAccessProperty(bean, property)) {
			return super.getProperty(bean, property);
		}
		return null;
	}

	@Override
	public void setProperty(Object bean, String property, Class<?> expectedType, Object value) {
		AccessPolicy policy = getPolicy();
		if (policy != null && policy.canUpdateProperty(bean, property)) {
			super.setProperty(bean, property, expectedType, value);
		}
	}

	private AccessPolicy getPolicy() {
		HttpSession session = HttpSessionUtil.getHttpSession();
		if (session != null) {
			AccessPolicy policy = (AccessPolicy) session.getAttribute(ServerConstants.SESSION_SECURITY_POLICY);
			return policy;
		}
		return null;
	}

}
