package org.imogene.android.widget.field.view;

import java.util.Date;

import org.imogene.android.util.FormatHelper;
import org.imogene.android.util.field.FieldPattern;

import android.content.Context;
import android.util.AttributeSet;

public class DateTimeFieldView extends DefaultEntityView<Date> {

	public DateTimeFieldView(Context context) {
		super(context);
	}

	public DateTimeFieldView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public String getFieldDisplay() {
		final Date time = getValue();
		if (time != null) {
			return FormatHelper.displayDateTime(time);
		}
		return super.getFieldDisplay();
	}

	@Override
	public boolean matchesDependencyValue(String value) {
		final Date date = getValue();
		return date != null ? FieldPattern.matchesDateTime(value, date) : false;
	}

}
