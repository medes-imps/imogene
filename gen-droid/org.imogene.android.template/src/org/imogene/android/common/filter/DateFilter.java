package org.imogene.android.common.filter;

import java.util.Date;

import org.imogene.android.database.sqlite.ClientFilterCursor;

import fr.medes.android.util.FormatHelper;

public class DateFilter extends ClientFilter {

	public static final Creator<DateFilter> FILTER_CREATOR = new DefaultCreator<DateFilter>() {
		@Override
		protected DateFilter newFilter() {
			return new DateFilter();
		}

		@Override
		protected DateFilter newFilter(ClientFilterCursor c) {
			return new DateFilter(c);
		}
	};

	public enum DateOperator {
		UNDEF(OPERATOR_UNDEF), EQUAL(DATE_OPERATOR_EQUAL), AFTER(DATE_OPERATOR_AFTER), BEFORE(DATE_OPERATOR_BEFORE), BETWEEN(
				DATE_OPERATOR_BETWEEN);

		private final String operator;

		private DateOperator(String op) {
			operator = op;
		}

		public String operator() {
			return operator;
		}

		public static DateOperator fromOp(String str) {
			for (DateOperator o : values())
				if (o.operator().equals(str))
					return o;
			return UNDEF;
		}
	}

	private DateOperator mOperator;
	private Date mEqual;
	private Date mBefore;
	private Date mAfter;

	private DateFilter(ClientFilterCursor c) {
		super(c);
		init();
	}

	private DateFilter() {
		super();
		init();
	}

	private void init() {
		mOperator = DateOperator.fromOp(getOperator());
		String str = getFieldValue();
		if (str == null)
			return;
		switch (mOperator) {
		case BETWEEN:
			String[] values = str.split(";");
			if (values.length == 2) {
				mAfter = FormatHelper.toDate(values[0]);
				mBefore = FormatHelper.toDate(values[1]);
			}
			return;
		case BEFORE:
			mBefore = FormatHelper.toDate(str);
			return;
		case AFTER:
			mAfter = FormatHelper.toDate(str);
			return;
		case EQUAL:
			mEqual = FormatHelper.toDate(str);
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
			builder.append(mAfter != null ? mAfter.toString() : null).append(';');
			builder.append(mBefore != null ? mBefore.toString() : null);
			setOperator(mOperator.operator());
			setFieldValue(builder.toString());
			return;
		case BEFORE:
			setOperator(mOperator.operator());
			setFieldValue(mBefore != null ? mBefore.toString() : null);
			return;
		case AFTER:
			setOperator(mOperator.operator());
			setFieldValue(mAfter != null ? mAfter.toString() : null);
			return;
		case EQUAL:
			setOperator(mOperator.operator());
			setFieldValue(mEqual != null ? mEqual.toString() : null);
			return;
		default:
			setOperator(DateOperator.UNDEF.operator());
			setFieldValue(null);
			return;
		}
	}

	public DateOperator getDateOperator() {
		return mOperator;
	}

	public Date getEqual() {
		return mEqual;
	}

	public Date getBefore() {
		return mBefore;
	}

	public Date getAfter() {
		return mAfter;
	}

	public void setDateOperator(DateOperator op) {
		mOperator = op;
	}

	public void setEqual(Date equal) {
		mEqual = equal;
	}

	public void setBefore(Date before) {
		mBefore = before;
	}

	public void setAfter(Date after) {
		mAfter = after;
	}
}
