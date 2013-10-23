package org.imogene.web.server.security;

import java.util.List;

import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.entity.ImogBean;


/**
 * To secure the beans by filtering the field values
 * depending on the user roles
 * Used for the report generation
 * @author MEDES-IMPS
 */
public interface ImogBeanFilter {

	/**
	 * 
	 * @param bean The bean to secure
	 * @param actor The actor of this session
	 * @return a secured bean
	 */
	public <T extends ImogBean> T toSecure(T bean, ImogActor actor);

	/**
	 * 
	 * @param list
	 * @param actor
	 * @return
	 */
	public <T extends ImogBean> List<T> toSecure(List<T> list, ImogActor actor);
}
