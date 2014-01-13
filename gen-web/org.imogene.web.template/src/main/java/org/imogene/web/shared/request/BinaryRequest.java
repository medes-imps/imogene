package org.imogene.web.shared.request;

import org.imogene.web.server.handler.BinaryHandler;
import org.imogene.web.server.locator.SpringServiceLocator;
import org.imogene.web.shared.proxy.BinaryDescProxy;
import org.imogene.web.shared.proxy.BinaryProxy;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;



@Service(value= BinaryHandler.class, locator = SpringServiceLocator.class)
public interface BinaryRequest extends RequestContext {
	
	
	Request<BinaryDescProxy> getBinaryDesc(String binaryId);
	
	Request<BinaryProxy> getBinary(String id);

}