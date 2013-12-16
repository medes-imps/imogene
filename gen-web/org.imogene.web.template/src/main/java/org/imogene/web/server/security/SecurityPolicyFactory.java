package org.imogene.web.server.security;

import org.imogene.lib.common.entity.ImogActor;

public interface SecurityPolicyFactory {

	public SecurityPolicy createSecurityPolicy(ImogActor actor);

}
