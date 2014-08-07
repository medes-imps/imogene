package org.imogene.android.widget.field.view;

import org.imogene.android.template.R;

import android.content.Context;
import android.util.AttributeSet;

public class BooleanFieldView extends DefaultEntityView<Boolean> {

	public BooleanFieldView(Context context) {
		super(context);
	}

	public BooleanFieldView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public String getFieldDisplay() {
		final Boolean bool = getValue();
		if (bool != null) {
			String[] array = getResources().getStringArray(R.array.imog__select_yes_no);
			return bool.booleanValue() ? array[0] : array[1];
		}
		return super.getFieldDisplay();
	}

	@Override
	public boolean matchesDependencyValue(String value) {
		final Boolean b = getValue();
		return b != null ? b.booleanValue() == Boolean.parseBoolean(value) : false;
	}
}
