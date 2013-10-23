package org.imogene.lib.sync.uao.security;

import java.util.List;

import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.entity.ImogBean;

public interface ImogSecurityPolicy {

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

	/**
	 * 
	 * @param bean The secured bean
	 * @param actor The session actor
	 * @return The global 'unsecured' bean
	 */
	public <T extends ImogBean> T toHibernate(T bean, ImogActor actor);

	/**
	 * 
	 * @param list
	 * @param actor
	 * @return
	 */
	public <T extends ImogBean> List<T> toHibernate(List<T> list, ImogActor actor);
}
