package org.imogene.web.server.handler;

import org.imogene.lib.common.entity.ImogBean;

public interface HandlerHelper {

	public void delete(ImogBean bean);

	public void save(ImogBean bean, boolean isNew);
	
	public void prepare(ImogBean bean);

}
