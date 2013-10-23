package org.imogene.lib.sync.handler;

import java.util.List;

import org.imogene.lib.common.entity.ImogActor;

public interface ImogActorHandler<T extends ImogActor> extends ImogBeanHandler<T> {
	
	public List<T> getUserFromLogin(String login);

}
