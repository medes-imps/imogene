package org.imogene.lib.sync.handler;

import org.imogene.lib.common.entity.ImogBean;


public interface DataHandlerManager {

	public ImogBeanHandler<? extends ImogBean> getHandler(Class<?> clazz);
	
	public ImogBeanHandler<? extends ImogBean> getHandler(String className);

}
