package org.imogene.android.common.filter;

import org.imogene.android.database.sqlite.ClientFilterCursor;

import fr.medes.android.util.FormatHelper;

public class BooleanFilter extends ClientFilter {

	public static final Creator<BooleanFilter> FILTER_CREATOR = new DefaultCreator<BooleanFilter>() {
		@Override
		protected BooleanFilter newFilter() {
			return new BooleanFilter();
		}

		@Override
		protected BooleanFilter newFilter(ClientFilterCursor c) {
			return new BooleanFilter(c);
		}
	};

	private Boolean mValue;

	private BooleanFilter(ClientFilterCursor c) {
		super(c);
		init();
	}

	private BooleanFilter() {
		super();
		init();
	}

	private void init() {
		if (BOOLEAN_OPERATOR_EQUAL.equals(getOperator()))
			mValue = FormatHelper.toBoolean(getFieldValue());
		else
			mValue = null;
	}

	@Override
	protected void preCommit() {
		super.preCommit();
		if (mValue != null) {
			setOperator(BOOLEAN_OPERATOR_EQUAL);
			setFieldValue(mValue.toString());
		} else {
			setOperator(OPERATOR_UNDEF);
			setFieldValue(null);
		}
	}

	public Boolean getValue() {
		return mValue;
	}

	public void setValue(Boolean value) {
		mValue = value;
	}

}
