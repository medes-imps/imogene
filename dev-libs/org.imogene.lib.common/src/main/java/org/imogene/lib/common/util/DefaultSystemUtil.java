package org.imogene.lib.common.util;

import org.imogene.lib.common.constants.CommonConstants;

public class DefaultSystemUtil implements SystemUtil {

	@Override
	public long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}

	@Override
	public String getTerminal() {
		return CommonConstants.IS_WEB;
	}

}
