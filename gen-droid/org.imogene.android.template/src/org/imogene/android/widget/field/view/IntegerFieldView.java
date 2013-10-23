package org.imogene.android.widget.field.view;

import org.imogene.android.util.field.FieldPattern;

import android.content.Context;
import android.util.AttributeSet;

public class IntegerFieldView extends NumberFieldView<Integer> {

	public IntegerFieldView(Context context) {
		super(context);
		setFormat("%1$d");
	}

	public IntegerFieldView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean matchesDependencyValue(String value) {
		final Integer i = getValue();
		return i != null ? FieldPattern.matchesInt(value, i.intValue()) : false;
	}

}
