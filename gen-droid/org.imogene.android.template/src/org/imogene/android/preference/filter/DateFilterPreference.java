package org.imogene.android.preference.filter;

import java.util.Calendar;
import java.util.Date;

import org.imogene.android.common.filter.DateFilter;
import org.imogene.android.common.filter.DateFilter.DateOperator;
import org.imogene.android.template.R;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import fr.medes.android.util.FormatHelper;

public class DateFilterPreference extends FilterPreference<DateFilter> implements OnClickListener, OnLongClickListener,
		OnItemSelectedListener, OnDateSetListener {

	private int mPosition = 0;
	private Date mEquals;
	private Date mAfter;
	private Date mBefore;

	private Spinner spinner;
	private TextView equals;
	private TextView after;
	private TextView before;
	private View period;

	Dialog equalsPicker;
	Dialog afterPicker;
	Dialog beforePicker;

	public DateFilterPreference(Context context, AttributeSet attrs) {
		super(context, attrs, DateFilter.FILTER_CREATOR);
		setDialogLayoutResource(R.layout.imog__dialog_date_filter);
	}

	@Override
	public CharSequence getSummary() {
		DateFilter filter = getFilter();
		DateOperator o = filter.getDateOperator();
		Date e = filter.getEqual();
		Date a = filter.getAfter();
		Date b = filter.getBefore();
		switch (o) {
		case AFTER:
			if (a != null)
				return getContext().getString(R.string.imog__filter_date_after, FormatHelper.displayDate(a));
			break;
		case BEFORE:
			if (b != null)
				return getContext().getString(R.string.imog__filter_date_before, FormatHelper.displayDate(b));
			break;
		case BETWEEN:
			if (a != null && b != null)
				return getContext().getString(R.string.imog__filter_date_between, FormatHelper.displayDate(a),
						FormatHelper.displayDate(b));
			break;
		case EQUAL:
			if (e != null)
				return getContext().getString(R.string.imog__filter_date_equal, FormatHelper.displayDate(e));
			break;
		default:
			break;
		}
		return getContext().getString(android.R.string.unknownName);
	}

	@Override
	protected void onClick() {
		DateFilter filter = getFilter();
		switch (filter.getDateOperator()) {
		case AFTER:
		case BEFORE:
		case BETWEEN:
			mPosition = 1;
			break;
		case EQUAL:
			mPosition = 2;
			break;
		case UNDEF:
			mPosition = 0;
			break;
		}
		mEquals = filter.getEqual();
		mAfter = filter.getAfter();
		mBefore = filter.getBefore();
		super.onClick();
	}

	@Override
	protected void onDialogClosed(boolean positiveResult) {
		super.onDialogClosed(positiveResult);
		if (positiveResult) {
			DateFilter filter = getFilter();
			filter.setDateOperator(DateOperator.UNDEF);
			filter.setAfter(null);
			filter.setBefore(null);
			filter.setEqual(null);
			switch (spinner.getSelectedItemPosition()) {
			case 1:
				if (mAfter != null && mBefore != null) {
					filter.setDateOperator(DateOperator.BETWEEN);
					filter.setAfter(mAfter);
					filter.setBefore(mBefore);
				} else if (mAfter != null) {
					filter.setDateOperator(DateOperator.AFTER);
					filter.setAfter(mAfter);
				} else if (mBefore != null) {
					filter.setDateOperator(DateOperator.BEFORE);
					filter.setBefore(mBefore);
				}
				break;
			case 2:
				if (mEquals != null) {
					filter.setDateOperator(DateOperator.EQUAL);
					filter.setEqual(mEquals);
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
		final Calendar cal = Calendar.getInstance();
		final int year = cal.get(Calendar.YEAR);
		final int month = cal.get(Calendar.MONTH);
		final int day = cal.get(Calendar.DAY_OF_MONTH);
		equalsPicker = new DatePickerDialog(getContext(), this, year, month, day);
		afterPicker = new DatePickerDialog(getContext(), this, year, month, day);
		beforePicker = new DatePickerDialog(getContext(), this, year, month, day);

		equals = (TextView) view.findViewById(R.id.imog__equals);
		after = (TextView) view.findViewById(R.id.imog__after);
		before = (TextView) view.findViewById(R.id.imog__before);
		period = view.findViewById(R.id.imog__period);

		updateView(equals, mEquals);
		updateView(after, mAfter);
		updateView(before, mBefore);

		spinner.setOnItemSelectedListener(this);
		spinner.setSelection(mPosition);
		equals.setOnClickListener(this);
		equals.setOnLongClickListener(this);
		after.setOnClickListener(this);
		after.setOnLongClickListener(this);
		before.setOnClickListener(this);
		before.setOnLongClickListener(this);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int pos, long row) {
		mPosition = pos;
		period.setVisibility(pos == 1 ? View.VISIBLE : View.GONE);
		equals.setVisibility(pos == 2 ? View.VISIBLE : View.GONE);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// Nothing to do
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imog__equals:
			updateDatePickerDialog(equalsPicker, mEquals);
			equalsPicker.show();
			break;
		case R.id.imog__after:
			updateDatePickerDialog(afterPicker, mAfter);
			afterPicker.show();
			break;
		case R.id.imog__before:
			updateDatePickerDialog(beforePicker, mBefore);
			beforePicker.show();
			break;
		}
	}

	@Override
	public boolean onLongClick(View v) {
		switch (v.getId()) {
		case R.id.imog__equals:
			mEquals = null;
			updateView(equals, null);
			return true;
		case R.id.imog__after:
			mAfter = null;
			updateView(after, null);
			return true;
		case R.id.imog__before:
			mBefore = null;
			updateView(before, null);
			return true;
		}
		return false;
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		final Calendar cal = Calendar.getInstance();
		cal.set(year, monthOfYear, dayOfMonth);
		if (equalsPicker.isShowing()) {
			mEquals = cal.getTime();
			updateView(equals, mEquals);
		} else if (afterPicker.isShowing()) {
			mAfter = cal.getTime();
			updateView(after, mAfter);
		} else if (beforePicker.isShowing()) {
			mBefore = cal.getTime();
			updateView(before, mBefore);
		}
	}

	private static void updateView(TextView v, Date time) {
		if (time != null)
			v.setText(FormatHelper.displayDate(time));
		else
			v.setText(R.string.imog__select);
	}

	private static void updateDatePickerDialog(Dialog dialog, Date time) {
		Calendar cal = Calendar.getInstance();
		if (time != null)
			cal.setTime(time);
		((DatePickerDialog) dialog).updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH));
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		final Parcelable superState = super.onSaveInstanceState();
		/*
		 * if (isPersistent()) { // No need to save instance state since it's persistent return superState; }
		 */
		final SavedState myState = new SavedState(superState);
		myState.position = mPosition;
		myState.equals = mEquals;
		myState.after = mAfter;
		myState.before = mBefore;
		if (equalsPicker != null && equalsPicker.isShowing()) {
			myState.savedDialogState = equalsPicker.onSaveInstanceState();
			myState.savedDialogIndex = 0;
		} else if (afterPicker != null && afterPicker.isShowing()) {
			myState.savedDialogState = afterPicker.onSaveInstanceState();
			myState.savedDialogIndex = 1;
		} else if (beforePicker != null && beforePicker.isShowing()) {
			myState.savedDialogState = beforePicker.onSaveInstanceState();
			myState.savedDialogIndex = 2;
		} else {
			myState.savedDialogState = null;
			myState.savedDialogIndex = -1;
		}
		return myState;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		if (state == null || !state.getClass().equals(SavedState.class)) {
			// Didn't save state for us in onSaveInstanceState
			super.onRestoreInstanceState(state);
			return;
		}

		SavedState myState = (SavedState) state;
		mPosition = myState.position;
		mEquals = myState.equals;
		mAfter = myState.after;
		mBefore = myState.before;
		super.onRestoreInstanceState(myState.getSuperState());
		int savedDialogIndex = myState.savedDialogIndex;
		switch (savedDialogIndex) {
		case 0:
			equalsPicker.show();
			equalsPicker.onRestoreInstanceState(myState.savedDialogState);
			break;
		case 1:
			afterPicker.show();
			afterPicker.onRestoreInstanceState(myState.savedDialogState);
			break;
		case 2:
			beforePicker.show();
			beforePicker.onRestoreInstanceState(myState.savedDialogState);
			break;
		}
	}

	private static class SavedState extends BaseSavedState {
		int position;
		Date equals;
		Date after;
		Date before;

		int savedDialogIndex;
		Bundle savedDialogState;

		public SavedState(Parcel source) {
			super(source);
			position = source.readInt();
			equals = (Date) source.readSerializable();
			after = (Date) source.readSerializable();
			before = (Date) source.readSerializable();

			savedDialogIndex = source.readInt();
			savedDialogState = source.readBundle();
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			super.writeToParcel(dest, flags);
			dest.writeInt(position);
			dest.writeSerializable(equals);
			dest.writeSerializable(after);
			dest.writeSerializable(before);

			dest.writeInt(savedDialogIndex);
			dest.writeBundle(savedDialogState);
		}

		public SavedState(Parcelable superState) {
			super(superState);
		}

		@SuppressWarnings("unused")
		public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
			@Override
			public SavedState createFromParcel(Parcel in) {
				return new SavedState(in);
			}

			@Override
			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}
		};
	}
}
