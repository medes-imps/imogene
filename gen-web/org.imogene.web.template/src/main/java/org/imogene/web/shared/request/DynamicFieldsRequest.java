package org.imogene.web.shared.request;

import org.imogene.web.shared.proxy.DynamicFieldInstanceProxy;
import org.imogene.web.shared.proxy.DynamicFieldTemplateProxy;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;

public interface DynamicFieldsRequest extends RequestContext {
	
	Request<Void> saveDynamicFieldValues(DynamicFieldInstanceProxy entity,	boolean isNew);
	Request<Void> deleteDynamicFieldValues(DynamicFieldInstanceProxy entity);
		
	Request<Void> activateDynamicFieldTemplate(DynamicFieldTemplateProxy entity, boolean activate);
}
