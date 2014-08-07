package org.imogene.android.widget.field.view;

import android.content.Context;
import android.util.AttributeSet;
import fr.medes.android.util.field.FieldPattern;

public class FloatFieldView extends NumberFieldView<Float> {

	public FloatFieldView(Context context) {
		super(context);
		setFormat("%1$.5f");
	}

	public FloatFieldView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean matchesDependencyValue(String value) {
		final Float f = getValue();
		return f != null ? FieldPattern.matchesFloat(value, f.floatValue()) : false;
	}

}
