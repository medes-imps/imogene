package org.imogene.web.server.util.impl;

import org.imogene.lib.common.constants.CommonConstants;
import org.imogene.web.server.util.SystemUtil;

public class SystemUtilImpl implements SystemUtil {

	@Override
	public long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}

	@Override
	public String getTerminal() {
		return CommonConstants.IS_WEB;
	}

}
