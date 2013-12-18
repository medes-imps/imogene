package org.imogene.web.server.security;

import org.imogene.lib.common.entity.ImogActor;

public interface AccessPolicyFactory {

	public AccessPolicy create(ImogActor actor);

}
