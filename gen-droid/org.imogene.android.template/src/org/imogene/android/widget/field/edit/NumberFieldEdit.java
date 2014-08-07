package org.imogene.android.widget.field.edit;

import org.imogene.android.template.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public abstract class NumberFieldEdit<T extends Number> extends BaseFieldEdit<T> implements TextWatcher {

	private T mMin;
	private T mMax;

	public NumberFieldEdit(Context context, int layoutId) {
		super(context, layoutId);
	}

	public NumberFieldEdit(Context context, AttributeSet attrs, int layoutId) {
		super(context, attrs, layoutId);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NumberField, 0, 0);
		setUnit(a.getResourceId(R.styleable.NumberField_unit, -1));
		a.recycle();
	}

	public void setMin(T min) {
		mMin = min;
	}

	public T getMin() {
		return mMin;
	}

	public void setMax(T max) {
		mMax = max;
	}

	public T getMax() {
		return mMax;
	}

	@Override
	public void setTitle(int titleId) {
		super.setTitle(titleId);
		getValueView().setHint(titleId);
	}

	@Override
	public void setTitle(CharSequence title) {
		super.setTitle(title);
		getValueView().setHint(title);
	}

	public void setUnit(int unitId) {
		final TextView unitView = (TextView) findViewById(R.id.imog__unit);
		if (unitView != null) {
			if (unitId > 0) {
				unitView.setText(unitId);
			}
			unitView.setVisibility(unitId > 0 ? View.VISIBLE : View.GONE);
		}
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);
		final TextView v = getValueView();
		if (readOnly) {
			v.removeTextChangedListener(this);
			v.setEnabled(false);
			v.setInputType(InputType.TYPE_NULL);
		} else {
			v.addTextChangedListener(this);
			v.setEnabled(true);
			v.setInputType(getInputType());
		}
	}

	protected abstract int getInputType();

	@Override
	public String getFieldDisplay() {
		final T value = getValue();
		return value != null ? value.toString() : null;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// Don't care
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// Don't care
	}

}
