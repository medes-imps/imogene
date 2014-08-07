package org.imogene.android.widget.field.view;

import org.imogene.android.template.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

public class NumberFieldView<T extends Number> extends DefaultEntityView<T> {

	private String format;

	public NumberFieldView(Context context) {
		super(context);
	}

	public NumberFieldView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NumberField, 0, 0);
		format = a.getString(R.styleable.NumberField_format);
		a.recycle();
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@Override
	public String getFieldDisplay() {
		final T number = getValue();
		if (number != null) {
			return String.format(format, number);
		}
		return null;
	}

}
