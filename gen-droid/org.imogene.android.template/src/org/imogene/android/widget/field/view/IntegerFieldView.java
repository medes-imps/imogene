package org.imogene.android.widget.field.view;

import android.content.Context;
import android.util.AttributeSet;
import fr.medes.android.util.field.FieldPattern;

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
