package org.imogene.web.shared.request;

import org.imogene.web.shared.proxy.ImogBeanProxy;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;

public interface ImogEntityRequest extends RequestContext {
	
	Request<Void> save(ImogBeanProxy entity, boolean isNew);
	Request<Void> delete(ImogBeanProxy entity);

}
