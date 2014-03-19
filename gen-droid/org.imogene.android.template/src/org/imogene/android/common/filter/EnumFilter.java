package org.imogene.android.common.filter;

import org.imogene.android.database.sqlite.ClientFilterCursor;

public class EnumFilter extends ClientFilter {

	private boolean multi = false;

	public static final Creator<EnumFilter> FILTER_CREATOR = new DefaultCreator<EnumFilter>() {
		@Override
		protected EnumFilter newFilter() {
			return new EnumFilter();
		}

		@Override
		protected EnumFilter newFilter(ClientFilterCursor c) {
			return new EnumFilter(c);
		}
	};

	private EnumFilter(ClientFilterCursor c) {
		super(c);
	}

	private EnumFilter() {
		super();
	}

	public void setMultipleSelectable(boolean isMultipleSelection) {
		multi = isMultipleSelection;
	}

	@Override
	protected void preCommit() {
		super.preCommit();
		String value = getFieldValue();
		if (value != null && !"-1".equals(value))
			setOperator(multi ? MULTIENUM_OPERATOR_EQUAL : STRING_OPERATOR_EQUAL);
		else
			setOperator(OPERATOR_UNDEF);
	}
}
