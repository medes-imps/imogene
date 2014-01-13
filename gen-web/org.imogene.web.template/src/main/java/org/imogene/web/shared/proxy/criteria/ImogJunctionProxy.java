package org.imogene.web.shared.proxy.criteria;

import java.util.List;

import org.imogene.lib.common.criteria.ImogJunction;

import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(value=ImogJunction.class)
 public interface ImogJunctionProxy extends ImogCriterionProxy {
	

	public List<ImogCriterionProxy> getCriterions();

	public void setCriterions(List<ImogCriterionProxy> criterions);
}
