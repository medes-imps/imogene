package org.imogene.android.widget.field.view;

import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import fr.medes.android.util.FormatHelper;
import fr.medes.android.util.field.FieldPattern;

public class DateFieldView extends DefaultEntityView<Date> {

	public DateFieldView(Context context) {
		super(context);
	}

	public DateFieldView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public String getFieldDisplay() {
		final Date time = getValue();
		if (time != null) {
			return FormatHelper.displayDate(time);
		}
		return super.getFieldDisplay();
	}

	@Override
	public boolean matchesDependencyValue(String value) {
		final Date date = getValue();
		return date != null ? FieldPattern.matchesDate(value, date) : false;
	}

}
