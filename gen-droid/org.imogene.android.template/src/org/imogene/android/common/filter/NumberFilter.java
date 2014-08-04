package org.imogene.android.common.filter;

import org.imogene.android.database.sqlite.ClientFilterCursor;

public abstract class NumberFilter<T extends Number> extends ClientFilter {

	public enum NumberOperator {
		UNDEF(OPERATOR_UNDEF, OPERATOR_UNDEF), EQUAL(INT_OPERATOR_EQUAL, FLOAT_OPERATOR_EQUAL), INFIMUM(
				INT_OPERATOR_INF, FLOAT_OPERATOR_INF), SUPREMUM(INT_OPERATOR_SUP, FLOAT_OPERATOR_SUP), BETWEEN(
				INT_OPERATOR_BETWEEN, FLOAT_OPERATOR_BETWEEN);

		private final String operatorInt;
		private final String operatorFloat;

		private NumberOperator(String opInt, String opFloat) {
			operatorInt = opInt;
			operatorFloat = opFloat;
		}

		public String operatorFloat() {
			return operatorFloat;
		}

		public String operatorInt() {
			return operatorInt;
		}

		public static NumberOperator fromOpFloat(String str) {
			for (NumberOperator o : values())
				if (o.operatorFloat().equals(str))
					return o;
			return UNDEF;
		}

		public static NumberOperator fromOpInt(String str) {
			for (NumberOperator o : values())
				if (o.operatorInt().equals(str))
					return o;
			return UNDEF;
		}
	}

	private NumberOperator mOperator;
	private T mEqual;
	private T mInfimum;
	private T mSupremum;

	protected NumberFilter() {
		super();
		init();
	}

	protected NumberFilter(ClientFilterCursor c) {
		super(c);
		init();
	}

	private void init() {
		mOperator = fromOperator(getOperator());
		String str = getFieldValue();
		if (str == null)
			return;
		switch (mOperator) {
		case BETWEEN:
			String[] values = str.split(";");
			if (values.length == 2) {
				mInfimum = toType(values[0]);
				mSupremum = toType(values[1]);
			}
			return;
		case EQUAL:
			mEqual = toType(str);
			return;
		case INFIMUM:
			mInfimum = toType(str);
			return;
		case SUPREMUM:
			mSupremum = toType(str);
			return;
		default:
			return;
		}
	}

	@Override
	protected void preCommit() {
		super.preCommit();
		switch (mOperator) {
		case BETWEEN:
			StringBuilder builder = new StringBuilder();
			builder.append(mInfimum != null ? mInfimum.toString() : null).append(';');
			builder.append(mSupremum != null ? mSupremum.toString() : null);

			setOperator(operator(mOperator));
			setFieldValue(builder.toString());
			return;
		case EQUAL:
			setOperator(operator(mOperator));
			setFieldValue(mEqual != null ? mEqual.toString() : null);
			return;
		case INFIMUM:
			setOperator(operator(mOperator));
			setFieldValue(mInfimum != null ? mInfimum.toString() : null);
			return;
		case SUPREMUM:
			setOperator(operator(mOperator));
			setFieldValue(mSupremum != null ? mSupremum.toString() : null);
			return;
		default:
			setOperator(operator(NumberOperator.UNDEF));
			setFieldValue(null);
			return;
		}
	}

	protected abstract NumberOperator fromOperator(String str);

	protected abstract String operator(NumberOperator operator);

	protected abstract T toType(String value);

	public NumberOperator getNumberOperator() {
		return mOperator;
	}

	public void setNumberOperator(NumberOperator op) {
		mOperator = op;
	}

	public T getEqual() {
		return mEqual;
	}

	public T getInfimum() {
		return mInfimum;
	}

	public T getSupremum() {
		return mSupremum;
	}

	public void setEqual(T equal) {
		mEqual = equal;
	}

	public void setInfimum(T from) {
		mInfimum = from;
	}

	public void setSupremum(T to) {
		mSupremum = to;
	}

}
