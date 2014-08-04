package org.imogene.android.preference.filter;

import org.imogene.android.common.filter.StringFilter;
import org.imogene.android.common.filter.StringFilter.StringOperator;
import org.imogene.android.template.R;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;

public class StringFilterPreference extends FilterPreference<StringFilter> implements OnItemSelectedListener {

	public StringFilterPreference(Context context, AttributeSet attrs) {
		super(context, attrs, StringFilter.FILTER_CREATOR);
		setDialogLayoutResource(R.layout.imog__dialog_string_filter);
	}

	private Spinner spinner;
	private TextView value;

	@Override
	public CharSequence getSummary() {
		StringFilter filter = getFilter();
		StringOperator operator = filter.getStringOperator();
		switch (operator) {
		case CONTAINS:
			return getContext().getString(R.string.imog__filter_string_contains, filter.getFieldValue());
		case EQUAL:
			return getContext().getString(R.string.imog__filter_string_equal, filter.getFieldValue());
		case STARTWITH:
			return getContext().getString(R.string.imog__filter_string_startwith, filter.getFieldValue());
		default:
			break;
		}
		return getContext().getString(android.R.string.unknownName);
	}

	@Override
	protected void onDialogClosed(boolean positiveResult) {
		super.onDialogClosed(positiveResult);
		if (positiveResult) {
			StringFilter filter = getFilter();
			filter.setStringOperator(StringOperator.UNDEF);
			filter.setFieldValue(null);
			CharSequence cs = value.getText();
			if (!TextUtils.isEmpty(cs)) {
				switch (spinner.getSelectedItemPosition()) {
				case 1:
					filter.setStringOperator(StringOperator.CONTAINS);
					filter.setFieldValue(cs.toString());
					break;
				case 2:
					filter.setStringOperator(StringOperator.EQUAL);
					filter.setFieldValue(cs.toString());
					break;
				case 3:
					filter.setStringOperator(StringOperator.STARTWITH);
					filter.setFieldValue(cs.toString());
					break;
				}
			}
			persistFilter();
		}
	}

	@Override
	protected void onBindDialogView(View view) {
		super.onBindDialogView(view);
		spinner = ((Spinner) view.findViewById(R.id.imog__spinner));
		value = (TextView) view.findViewById(R.id.imog__value);
		spinner.setOnItemSelectedListener(this);
		StringFilter filter = getFilter();
		switch (filter.getStringOperator()) {
		case CONTAINS:
			spinner.setSelection(1);
			break;
		case EQUAL:
			spinner.setSelection(2);
			break;
		case STARTWITH:
			spinner.setSelection(3);
			break;
		case UNDEF:
			spinner.setSelection(0);
		}
		value.setText(filter.getFieldValue());
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int pos, long row) {
		value.setVisibility(pos != 0 ? View.VISIBLE : View.GONE);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// Nothing to do
	}

}
