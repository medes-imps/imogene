package org.imogene.web.server.util;

import org.imogene.lib.common.constants.CriteriaConstants;
import org.imogene.lib.common.criteria.BasicCriteria;
import org.imogene.lib.common.criteria.ImogJunction;

public class FilterFieldsHelper {
	
public static final String EMPTY_FILTER = "undefined-filter";
	
	public static void addEmptyFilter(ImogJunction junction){
		BasicCriteria criteria = new BasicCriteria();
		criteria.setOperation(CriteriaConstants.OPERATOR_ISNULL);
		criteria.setField("id");
		criteria.setValue(EMPTY_FILTER);
		junction.add(criteria);
	}
}
