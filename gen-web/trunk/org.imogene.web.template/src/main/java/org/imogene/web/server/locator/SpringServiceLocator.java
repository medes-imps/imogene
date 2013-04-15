package org.imogene.web.server.locator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.google.web.bindery.requestfactory.shared.ServiceLocator;



/**
 */
public class SpringServiceLocator implements ServiceLocator {
	
	@Override
	public Object getInstance(Class<?> clazz) {
        HttpServletRequest request = RequestFactoryServlet.getThreadLocalRequest();
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
        return context.getBean(clazz);
	}
}