package org.imogene.web.shared;

import org.imogene.web.shared.proxy.CardEntityProxy;
import org.imogene.web.shared.proxy.EntityProfileProxy;
import org.imogene.web.shared.proxy.FieldGroupProfileProxy;
import org.imogene.web.shared.proxy.FieldGroupProxy;
import org.imogene.web.shared.proxy.GeoFieldProxy;
import org.imogene.web.shared.proxy.ImogActorProxy;
import org.imogene.web.shared.proxy.ImogBeanProxy;
import org.imogene.web.shared.proxy.ProfileProxy;
import org.imogene.web.shared.proxy.criteria.BasicCriteriaProxy;
import org.imogene.web.shared.proxy.criteria.ImogConjunctionProxy;
import org.imogene.web.shared.proxy.criteria.ImogDisjunctionProxy;
import org.imogene.web.shared.request.BinaryRequest;
import org.imogene.web.shared.request.DynamicFieldInstanceRequest;
import org.imogene.web.shared.request.DynamicFieldTemplateRequest;
import org.imogene.web.shared.request.SessionRequest;

import com.google.web.bindery.requestfactory.shared.ExtraTypes;
import com.google.web.bindery.requestfactory.shared.RequestFactory;


@ExtraTypes({   ImogConjunctionProxy.class, 
				ImogDisjunctionProxy.class, 
				BasicCriteriaProxy.class, 
				ProfileProxy.class,
				CardEntityProxy.class,
				EntityProfileProxy.class,
				FieldGroupProxy.class,
				FieldGroupProfileProxy.class,
				ImogActorProxy.class, 
				GeoFieldProxy.class,
				ImogBeanProxy.class})
public interface ImogRequestFactory extends RequestFactory {

	BinaryRequest binaryRequest();

	SessionRequest sessionInfoRequest();
	
	DynamicFieldTemplateRequest dynamicFieldTemplateRequest();
	DynamicFieldInstanceRequest dynamicFieldInstanceRequest();
}
