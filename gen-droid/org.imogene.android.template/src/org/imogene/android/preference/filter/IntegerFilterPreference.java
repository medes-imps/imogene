package org.imogene.android.preference.filter;

import org.imogene.android.common.filter.IntegerFilter;
import org.imogene.android.template.R;

import android.content.Context;
import android.util.AttributeSet;
import fr.medes.android.util.FormatHelper;

public class IntegerFilterPreference extends NumberFilterPreference<Integer, IntegerFilter> {

	public IntegerFilterPreference(Context context, AttributeSet attrs) {
		super(context, attrs, IntegerFilter.FILTER_CREATOR);
		setDialogLayoutResource(R.layout.imog__dialog_integer_filter);
	}

	@Override
	protected Integer parseNumber(String str) {
		return FormatHelper.toInteger(str);
	}
}
