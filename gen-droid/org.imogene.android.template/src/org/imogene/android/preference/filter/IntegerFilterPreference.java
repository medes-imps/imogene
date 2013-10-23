package org.imogene.android.preference.filter;

import org.imogene.android.domain.filters.IntegerFilter;
import org.imogene.android.template.R;
import org.imogene.android.util.FormatHelper;

import android.content.Context;
import android.util.AttributeSet;

public class IntegerFilterPreference extends NumberFilterPreference<Integer, IntegerFilter> {
	
	public IntegerFilterPreference(Context context, AttributeSet attrs) {
		super(context, attrs, IntegerFilter.FILTER_CREATOR);
		setDialogLayoutResource(R.layout.ig_dialog_integer_filter);
	}

	@Override
	protected Integer parseNumber(String str) {
		return FormatHelper.toInteger(str);
	}
}
