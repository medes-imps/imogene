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

public class IntegerFieldEdit extends NumberFieldEdit<Integer> {
	
	public IntegerFieldEdit(Context context) {
		super(context, R.layout.ig_field_edit_numeric);
		setFocusable(false);
	}
	
	public IntegerFieldEdit(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.ig_field_edit_numeric);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NumberField, 0, 0);
		if (a.hasValue(R.styleable.NumberField_igIntMin)) {
			setMin(a.getInt(R.styleable.NumberField_igIntMin, 0));
		} else {
			setMin(null);
		}
		if (a.hasValue(R.styleable.NumberField_igIntMax)) {
			setMax(a.getInt(R.styleable.NumberField_igIntMax, 0));
		} else {
			setMax(null);
		}
		a.recycle();
		setFocusable(false);
	}
	
	@Override
	protected int getInputType() {
		return InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED;
	}
	
	@Override
	public boolean isValid() {
		final Integer value = getValue();
		if (value == null) {
			return !isRequired();
		}
		final Integer min = getMin();
		if (min != null && min > value) {
			return false;
		}
		final Integer max = getMax();
		if (max != null && max < value) {
			return false;
		}
		return true;
	}
	
	@Override
	public ErrorEntry getErrorEntry(int tag) {
		ErrorEntry entry = super.getErrorEntry(tag);
		final Integer min = getMin();
		if (min != null) {
			entry.addMessage(getResources().getString(R.string.ig_greater_than_decimal, min));
		}
		final Integer max = getMax();
		if (max != null) {
			entry.addMessage(getResources().getString(R.string.ig_lower_than_decimal, max));
		}
		return entry;
	}
	
	@Override
	public boolean matchesDependencyValue(String value) {	
		final Integer i = getValue();
		return i != null ? FieldPattern.matchesInt(value, i.intValue()) : false;
	}
	
	@Override
	public void afterTextChanged(Editable s) {
		disableUpdateDisplayOnChange();
		setValue(FormatHelper.toInteger(s.toString()));
		enableUpdateDisplayOnChange();
	}

}
