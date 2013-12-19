package org.imogene.lib.common.security;

import org.imogene.lib.common.entity.ImogActor;

public interface AccessPolicyFactory {

	public AccessPolicy create(ImogActor actor);

}
