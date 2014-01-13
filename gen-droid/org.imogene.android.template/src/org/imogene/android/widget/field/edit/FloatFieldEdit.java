package org.imogene.android.widget.field.edit;

import org.imogene.android.template.R;
import org.imogene.android.util.FormatHelper;
import org.imogene.android.util.field.FieldPattern;
import org.imogene.android.widget.ErrorAdapter.ErrorEntry;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.util.AttributeSet;

public class FloatFieldEdit extends NumberFieldEdit<Float> {
	
	public FloatFieldEdit(Context context) {
		super(context, R.layout.ig_field_edit_numeric);
		setFocusable(false);
	}

	public FloatFieldEdit(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.ig_field_edit_numeric);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NumberField, 0, 0);
		if (a.hasValue(R.styleable.NumberField_igFloatMin)) {
			setMin(a.getFloat(R.styleable.NumberField_igFloatMin, 0));
		} else {
			setMin(null);
		}
		if (a.hasValue(R.styleable.NumberField_igFloatMax)) {
			setMax(a.getFloat(R.styleable.NumberField_igFloatMax, 0));
		} else {
			setMax(null);
		}
		a.recycle();
		setFocusable(false);		
	}
	
	@Override
	protected int getInputType() {
		return InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL
				| InputType.TYPE_NUMBER_FLAG_SIGNED;
	}
	
	@Override
	public boolean isValid() {
		final Float value = getValue();
		if (value == null) {
			return !isRequired();
		}
		final Float min = getMin();
		if (min != null && min > value) {
			return false;
		}
		final Float max = getMax();
		if (max != null && max < value) {
			return false;
		}
		return true;
	}
	
	@Override
	public ErrorEntry getErrorEntry(int tag) {
		ErrorEntry entry = super.getErrorEntry(tag);
		final Float min = getMin();
		if (min != null) {
			entry.addMessage(getResources().getString(R.string.ig_greater_than_float, min));
		}
		final Float max = getMax();
		if (max != null) {
			entry.addMessage(getResources().getString(R.string.ig_lower_than_float, max));
		}
		return entry;
	}
	
	@Override
	public boolean matchesDependencyValue(String value) {
		final Float f = getValue();
		return f != null ? FieldPattern.matchesFloat(value, f.floatValue()) : false;
	}

	@Override
	public void afterTextChanged(Editable s) {
		disableUpdateDisplayOnChange();
		setValue(FormatHelper.toFloat(s.toString()));
		enableUpdateDisplayOnChange();
	}

}
