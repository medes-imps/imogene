package org.imogene.web.server;

import com.google.web.bindery.requestfactory.server.DefaultExceptionHandler;
import com.google.web.bindery.requestfactory.server.ExceptionHandler;
import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.google.web.bindery.requestfactory.server.ServiceLayerDecorator;

/**
 * Custom RequestFactory Servlet to take into account the Access Service Decorator
 * 
 * @author Medes-IMPS
 */
public class ImogRequestFactoryServlet extends RequestFactoryServlet {

	private static final long serialVersionUID = -9054084147452091285L;

	public ImogRequestFactoryServlet() {
		this(new DefaultExceptionHandler(), new AccessServiceDecorator());
	}

	public ImogRequestFactoryServlet(ExceptionHandler exceptionHandler, ServiceLayerDecorator... serviceDecorators) {
		super(exceptionHandler, serviceDecorators);
	}

}