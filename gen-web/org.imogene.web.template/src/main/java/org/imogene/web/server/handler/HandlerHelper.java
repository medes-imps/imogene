package org.imogene.web.server.handler;

import java.util.Date;

import org.imogene.lib.common.criteria.BasicCriteria;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.entity.ImogBean;

public interface HandlerHelper {

	public void delete(ImogBean bean);

	public void save(ImogBean bean, boolean isNew);

	public void prepare(ImogBean bean);

	public void detach(ImogActor actor);

	public void prepareForDelete(ImogBean bean);

	public Date getCurrentTimeMillis();

	public BasicCriteria getNotDeletedFilterCriteria();

}
