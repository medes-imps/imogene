package org.imogene.android.widget.field.edit;

import java.util.Calendar;
import java.util.Date;

import org.imogene.android.template.R;
import org.imogene.android.util.FormatHelper;
import org.imogene.android.util.field.FieldPattern;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.DatePicker;

public class DateFieldEdit extends DatesFieldEdit implements OnDateSetListener {

	public DateFieldEdit(Context context) {
		super(context);
	}

	public DateFieldEdit(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DatesFieldEdit, 0, 0);
		if (a.hasValue(R.styleable.DatesFieldEdit_igDateMin)) {
			String date = a.getString(R.styleable.DatesFieldEdit_igDateMin);
			setMin(FormatHelper.readDate(date));
		} else {
			setMin(null);
		}
		if (a.hasValue(R.styleable.DatesFieldEdit_igDateMax)) {
			String date = a.getString(R.styleable.DatesFieldEdit_igDateMax);
			setMax(FormatHelper.readDate(date));
		} else {
			setMax(null);
		}
		a.recycle();
	}

	@Override
	public String getFieldDisplay() {
		final Date time = getValue();
		if (time != null) {
			return FormatHelper.displayDate(time);
		} else {
			return getEmptyText();
		}
	}

	@Override
	public boolean matchesDependencyValue(String value) {
		final Date date = getValue();
		return date != null ? FieldPattern.matchesDate(value, date) : false;
	}

	@Override
	public Dialog createDialog() {
		final Calendar cal = Calendar.getInstance();
		final Date time = getValue();
		if (time != null) {
			cal.setTime(time);
		}
		return new DatePickerDialog(getContext(), this, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH));
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		final Calendar cal = Calendar.getInstance();
		cal.set(year, monthOfYear, dayOfMonth);
		setValue(cal.getTime());
	}

}
