package org.imogene.android.domain.filters;

import org.imogene.android.database.sqlite.ClientFilterCursor;
import org.imogene.android.util.FormatHelper;

public class FloatFilter extends NumberFilter<Float> {
	
	public static final Creator<FloatFilter> FILTER_CREATOR = new DefaultCreator<FloatFilter>() {
		@Override
		protected FloatFilter newFilter() {
			return new FloatFilter();
		}
		
		@Override
		protected FloatFilter newFilter(ClientFilterCursor c) {
			return new FloatFilter(c);
		}
	};
	
	private FloatFilter() {
		super();
	}

	private FloatFilter(ClientFilterCursor c) {
		super(c);
	}
	
	@Override
	protected Float toType(String value) {
		return FormatHelper.toFloat(value);
	}
	
	@Override
	protected NumberOperator fromOperator(String str) {
		return NumberOperator.fromOpFloat(str);
	}
	
	@Override
	protected String operator(NumberOperator operator) {
		return operator.operatorFloat();
	}

}
