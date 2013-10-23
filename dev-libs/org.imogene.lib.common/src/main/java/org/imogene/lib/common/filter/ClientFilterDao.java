package org.imogene.lib.common.filter;

import java.util.List;

import org.imogene.lib.common.dao.ImogBeanDao;

/**
 * Implements a Hibernate DAO for the ClientFilter
 * @author MEDES-IMPS
 */
public interface ClientFilterDao extends ImogBeanDao<ClientFilter> {

	public List<ClientFilter> loadFilters(String userId, String terminalId, String classname);

	public List<ClientFilter> loadFilters(String userId, String terminalId);

}
