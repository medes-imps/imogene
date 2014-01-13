package org.imogene.lib.common.security;

import java.util.List;

import org.imogene.lib.common.entity.ImogBean;

public interface ImogBeanFilter {

	/**
	 * 
	 * @param bean The bean to secure
	 * @param actor The actor of this session
	 * @return a secured bean
	 */
	public <T extends ImogBean> T toSecure(T bean);

	/**
	 * 
	 * @param list
	 * @param actor
	 * @return
	 */
	public <T extends ImogBean> List<T> toSecure(List<T> list);

	/**
	 * 
	 * @param bean The secured bean
	 * @param actor The session actor
	 * @return The global 'unsecured' bean
	 */
	public <T extends ImogBean> T toHibernate(T bean);

	/**
	 * 
	 * @param list
	 * @param actor
	 * @return
	 */
	public <T extends ImogBean> List<T> toHibernate(List<T> list);
}
