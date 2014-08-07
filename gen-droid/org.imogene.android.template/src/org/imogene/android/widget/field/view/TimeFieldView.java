package org.imogene.android.widget.field.view;

import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import fr.medes.android.util.FormatHelper;
import fr.medes.android.util.field.FieldPattern;

public class TimeFieldView extends DefaultEntityView<Date> {

	public TimeFieldView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public String getFieldDisplay() {
		final Date time = getValue();
		if (time != null) {
			return FormatHelper.displayTime(time);
		}
		return super.getFieldDisplay();
	}

	@Override
	public boolean matchesDependencyValue(String value) {
		final Date date = getValue();
		return date != null ? FieldPattern.matchesTime(value, date) : false;
	}

}
