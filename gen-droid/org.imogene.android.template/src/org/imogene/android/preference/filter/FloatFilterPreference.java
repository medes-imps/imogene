package org.imogene.android.preference.filter;

import org.imogene.android.common.filter.FloatFilter;
import org.imogene.android.template.R;

import android.content.Context;
import android.util.AttributeSet;
import fr.medes.android.util.FormatHelper;

public class FloatFilterPreference extends NumberFilterPreference<Float, FloatFilter> {

	public FloatFilterPreference(Context context, AttributeSet attrs) {
		super(context, attrs, FloatFilter.FILTER_CREATOR);
		setDialogLayoutResource(R.layout.imog__dialog_float_filter);
	}

	@Override
	protected Float parseNumber(String str) {
		return FormatHelper.toFloat(str);
	}

}
