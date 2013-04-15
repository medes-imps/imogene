package org.imogene.android.widget.field.edit;

import java.util.Calendar;
import java.util.Date;

import org.imogene.android.app.DateTimePickerDialog;
import org.imogene.android.app.DateTimePickerDialog.OnDateTimeSetListener;
import org.imogene.android.template.R;
import org.imogene.android.util.FormatHelper;
import org.imogene.android.util.field.FieldPattern;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class DateTimeFieldEdit extends DatesFieldEdit implements OnDateTimeSetListener {

	public DateTimeFieldEdit(Context context) {
		super(context);
	}

	public DateTimeFieldEdit(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DatesFieldEdit, 0, 0);
		if (a.hasValue(R.styleable.DatesFieldEdit_igDateMin)) {
			String date = a.getString(R.styleable.DatesFieldEdit_igDateMin);
			setMin(FormatHelper.readDateTime(date));
		} else {
			setMin(null);
		}
		if (a.hasValue(R.styleable.DatesFieldEdit_igDateMax)) {
			String date = a.getString(R.styleable.DatesFieldEdit_igDateMax);
			setMax(FormatHelper.readDateTime(date));
		} else {
			setMax(null);
		}
		a.recycle();
	}

	@Override
	public String getFieldDisplay() {
		final Date time = getValue();
		if (time != null) {
			return FormatHelper.displayDateTime(time);
		} else {
			return getEmptyText();
		}
	}

	@Override
	public boolean matchesDependencyValue(String value) {
		final Date date = getValue();
		return date != null ? FieldPattern.matchesDateTime(value, date) : false;
	}

	@Override
	public Dialog createDialog() {
		final Calendar cal = Calendar.getInstance();
		final Date time = getValue();
		if (time != null) {
			cal.setTime(time);
		}
		return new DateTimePickerDialog(getContext(), this, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false);
	}

	@Override
	public void onDateTimeSet(DatePicker datePicker, TimePicker timePicker, int year, int monthOfYear, int dayOfMonth,
			int hourOfDay, int minute) {
		final Calendar cal = Calendar.getInstance();
		cal.set(year, monthOfYear, dayOfMonth, hourOfDay, minute);
		setValue(cal.getTime());
	}

}
