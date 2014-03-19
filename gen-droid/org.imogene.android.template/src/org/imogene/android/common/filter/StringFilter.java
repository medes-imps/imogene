package org.imogene.android.common.filter;

import org.imogene.android.database.sqlite.ClientFilterCursor;

public class StringFilter extends ClientFilter {

	public static final Creator<StringFilter> FILTER_CREATOR = new DefaultCreator<StringFilter>() {
		@Override
		protected StringFilter newFilter() {
			return new StringFilter();
		}

		@Override
		protected StringFilter newFilter(ClientFilterCursor c) {
			return new StringFilter(c);
		}
	};

	public enum StringOperator {
		UNDEF(OPERATOR_UNDEF), CONTAINS(STRING_OPERATOR_CONTAINS), EQUAL(STRING_OPERATOR_EQUAL), STARTWITH(
				STRING_OPERATOR_STARTWITH);

		private final String operator;

		private StringOperator(String op) {
			operator = op;
		}

		public String operator() {
			return operator;
		}

		public static StringOperator fromOp(String str) {
			for (StringOperator o : values())
				if (o.operator().equals(str))
					return o;
			return UNDEF;
		}
	}

	private StringOperator mOperator;

	private StringFilter() {
		super();
		init();
	}

	private StringFilter(ClientFilterCursor c) {
		super(c);
		init();
	}

	private void init() {
		mOperator = StringOperator.fromOp(getOperator());
	}

	@Override
	protected void preCommit() {
		super.preCommit();
		setOperator(mOperator != null ? mOperator.operator() : StringOperator.UNDEF.operator());
	}

	public StringOperator getStringOperator() {
		return mOperator;
	}

	public void setStringOperator(StringOperator op) {
		mOperator = op;
	}

}
