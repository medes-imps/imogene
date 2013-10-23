package org.imogene.sync;

import org.imogene.lib.common.constants.CriteriaConstants;
import org.imogene.lib.common.criteria.BasicCriteria;
import org.imogene.lib.common.criteria.ImogJunction;

public class FilterFieldsHelper {
	
	public static final String EMPTY_FILTER = "undefined-filter";

	public static void addEmptyFilter(ImogJunction junction) {
		BasicCriteria criteria = new BasicCriteria();
		criteria.setOperation(CriteriaConstants.STRING_OPERATOR_EQUAL);
		criteria.setField("id");
		criteria.setValue(EMPTY_FILTER);
		junction.add(criteria);
	}
}
