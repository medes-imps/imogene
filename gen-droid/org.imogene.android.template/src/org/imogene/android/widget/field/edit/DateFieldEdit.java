package org.imogene.android.widget.field.edit;

import java.util.Calendar;
import java.util.Date;

import org.imogene.android.template.R;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.DatePicker;
import fr.medes.android.util.FormatHelper;
import fr.medes.android.util.field.FieldPattern;

public class DateFieldEdit extends DatesFieldEdit implements OnDateSetListener {

	public DateFieldEdit(Context context) {
		super(context);
	}

	public DateFieldEdit(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DatesFieldEdit, 0, 0);
		if (a.hasValue(R.styleable.DatesFieldEdit_dateMin)) {
			String date = a.getString(R.styleable.DatesFieldEdit_dateMin);
			setMin(FormatHelper.readDate(date));
		} else {
			setMin(null);
		}
		if (a.hasValue(R.styleable.DatesFieldEdit_dateMax)) {
			String date = a.getString(R.styleable.DatesFieldEdit_dateMax);
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
		setValueInternal(cal.getTime(), true);
	}

}
