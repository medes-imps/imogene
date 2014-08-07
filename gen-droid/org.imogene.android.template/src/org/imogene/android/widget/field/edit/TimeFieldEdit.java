package org.imogene.android.widget.field.edit;

import java.util.Calendar;
import java.util.Date;

import org.imogene.android.template.R;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TimePicker;
import fr.medes.android.util.FormatHelper;
import fr.medes.android.util.field.FieldPattern;

public class TimeFieldEdit extends DatesFieldEdit implements OnTimeSetListener {

	public TimeFieldEdit(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DatesFieldEdit, 0, 0);
		if (a.hasValue(R.styleable.DatesFieldEdit_dateMin)) {
			String date = a.getString(R.styleable.DatesFieldEdit_dateMin);
			setMin(FormatHelper.readTime(date));
		} else {
			setMin(null);
		}
		if (a.hasValue(R.styleable.DatesFieldEdit_dateMax)) {
			String date = a.getString(R.styleable.DatesFieldEdit_dateMax);
			setMax(FormatHelper.readTime(date));
		} else {
			setMax(null);
		}
		a.recycle();
	}

	@Override
	public String getFieldDisplay() {
		final Date time = getValue();
		if (time != null) {
			return FormatHelper.displayTime(time);
		} else {
			return getEmptyText();
		}
	}

	@Override
	public boolean matchesDependencyValue(String value) {
		final Date date = getValue();
		return date != null ? FieldPattern.matchesTime(value, date) : false;
	}

	@Override
	public Dialog createDialog() {
		final Calendar cal = Calendar.getInstance();
		final Date time = getValue();
		if (time != null) {
			cal.setTime(time);
		}
		return new TimePickerDialog(getContext(), this, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false);
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		cal.set(Calendar.MINUTE, minute);
		setValueInternal(cal.getTime(), true);
	}

}
