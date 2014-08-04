package org.imogene.android.preference.filter;

import org.imogene.android.common.filter.ClientFilter.Creator;
import org.imogene.android.common.filter.NumberFilter;
import org.imogene.android.common.filter.NumberFilter.NumberOperator;
import org.imogene.android.template.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;

public abstract class NumberFilterPreference<U extends Number, T extends NumberFilter<U>> extends FilterPreference<T>
		implements OnItemSelectedListener {

	public NumberFilterPreference(Context context, AttributeSet attrs, Creator<T> creator) {
		super(context, attrs, creator);
	}

	private Spinner spinner;
	private TextView equals;
	private TextView infimum;
	private TextView supremum;
	private View interval;

	protected abstract U parseNumber(String str);

	@Override
	public CharSequence getSummary() {
		NumberFilter<U> filter = getFilter();
		NumberOperator o = filter.getNumberOperator();
		Number e = filter.getEqual();
		Number i = filter.getInfimum();
		Number s = filter.getSupremum();
		switch (o) {
		case BETWEEN:
			if (i != null && s != null)
				return getContext().getString(R.string.imog__filter_number_between, i.toString(), s.toString());
			break;
		case EQUAL:
			if (e != null)
				return getContext().getString(R.string.imog__filter_number_equal, e.toString());
			break;
		case INFIMUM:
			if (i != null)
				return getContext().getString(R.string.imog__filter_number_gt, i.toString());
			break;
		case SUPREMUM:
			if (s != null)
				return getContext().getString(R.string.imog__filter_number_lt, s.toString());
			break;
		default:
			break;
		}
		return getContext().getString(android.R.string.unknownName);
	}

	@Override
	protected void onDialogClosed(boolean positiveResult) {
		super.onDialogClosed(positiveResult);
		if (positiveResult) {
			T filter = getFilter();
			filter.setNumberOperator(NumberOperator.UNDEF);
			filter.setEqual(null);
			filter.setInfimum(null);
			filter.setSupremum(null);
			switch (spinner.getSelectedItemPosition()) {
			case 1:
				U eq = parseNumber(equals.getText().toString());
				if (eq != null) {
					filter.setNumberOperator(NumberOperator.EQUAL);
					filter.setEqual(eq);
				}
				break;
			case 2:
				U inf = parseNumber(infimum.getText().toString());
				U sup = parseNumber(supremum.getText().toString());
				if (inf != null && sup != null) {
					filter.setNumberOperator(NumberOperator.BETWEEN);
					filter.setInfimum(inf);
					filter.setSupremum(sup);
				} else if (inf != null) {
					filter.setNumberOperator(NumberOperator.INFIMUM);
					filter.setInfimum(inf);
				} else if (sup != null) {
					filter.setNumberOperator(NumberOperator.SUPREMUM);
					filter.setSupremum(sup);
				}
				break;
			}
			persistFilter();
		}
	}

	@Override
	protected void onBindDialogView(View view) {
		super.onBindDialogView(view);
		spinner = ((Spinner) view.findViewById(R.id.imog__spinner));

		equals = (TextView) view.findViewById(R.id.imog__equals);
		infimum = (TextView) view.findViewById(R.id.imog__infimum);
		supremum = (TextView) view.findViewById(R.id.imog__supremum);
		interval = view.findViewById(R.id.imog__interval);

		spinner.setOnItemSelectedListener(this);

		NumberFilter<U> filter = getFilter();
		U eq = filter.getEqual();
		U inf = filter.getInfimum();
		U sup = filter.getSupremum();
		switch (filter.getNumberOperator()) {
		case BETWEEN:
		case INFIMUM:
		case SUPREMUM:
			spinner.setSelection(2);
			break;
		case EQUAL:
			spinner.setSelection(1);
			break;
		case UNDEF:
			spinner.setSelection(0);
			break;
		}
		equals.setText(eq != null ? eq.toString() : null);
		infimum.setText(inf != null ? inf.toString() : null);
		supremum.setText(sup != null ? sup.toString() : null);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int pos, long row) {
		equals.setVisibility(pos == 1 ? View.VISIBLE : View.GONE);
		interval.setVisibility(pos == 2 ? View.VISIBLE : View.GONE);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// Nothing to do
	}
}
