package org.imogene.android.preference.filter;

import org.imogene.android.domain.filters.FloatFilter;
import org.imogene.android.template.R;
import org.imogene.android.util.FormatHelper;

import android.content.Context;
import android.util.AttributeSet;

public class FloatFilterPreference extends NumberFilterPreference<Float, FloatFilter> {

	public FloatFilterPreference(Context context, AttributeSet attrs) {
		super(context, attrs, FloatFilter.FILTER_CREATOR);
		setDialogLayoutResource(R.layout.ig_dialog_float_filter);
	}

	@Override
	protected Float parseNumber(String str) {
		return FormatHelper.toFloat(str);
	}

}
