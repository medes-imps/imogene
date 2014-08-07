package org.imogene.android.widget.field.edit;

import org.imogene.android.template.R;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import fr.medes.android.util.field.FieldPattern;

public class BoundedIntegerFieldEdit extends BaseFieldEdit<Integer> implements DialogInterface.OnClickListener {

	private NumberPicker mNumberPicker;

	private int mMaxValue;
	private int mMinValue;
	private String mFormat;

	public BoundedIntegerFieldEdit(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.imog__field_default);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NumberField, 0, 0);
		setMinValue(a.getInt(R.styleable.NumberField_intMin, 0));
		setMaxValue(a.getInt(R.styleable.NumberField_intMax, 100));
		mFormat = a.getString(R.styleable.NumberField_format);
		a.recycle();
	}

	public void setMinValue(int min) {
		mMinValue = min;
	}

	public void setMaxValue(int max) {
		mMaxValue = max;
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);
		setOnClickListener(readOnly ? null : this);
		setOnLongClickListener(readOnly ? null : this);
	}

	@Override
	public String getFieldDisplay() {
		final Integer value = getValue();
		if (value == null) {
			return getEmptyText();
		} else {
			return String.format(mFormat, value);
		}
	}

	@Override
	public boolean matchesDependencyValue(String value) {
		final Integer i = getValue();
		return i != null ? FieldPattern.matchesInt(value, i.intValue()) : false;
	}

	@Override
	protected void onPrepareDialogBuilder(Builder builder) {
		View view = LayoutInflater.from(getContext()).inflate(R.layout.imog__field_edit_integer_bounded, null);

		mNumberPicker = (NumberPicker) view.findViewById(R.id.imog__numberPicker);
		mNumberPicker.setMinValue(mMinValue);
		mNumberPicker.setMaxValue(mMaxValue);
		if (getValue() != null) {
			mNumberPicker.setValue(getValue());
		} else {
			mNumberPicker.setValue((mMaxValue + mMinValue) / 2);
		}

		builder.setView(view);
		builder.setPositiveButton(android.R.string.ok, this);
		builder.setNeutralButton(android.R.string.cut, this);
		builder.setNegativeButton(android.R.string.cancel, null);
	}

	@Override
	public void dispatchClick(View v) {
		showDialog(null);
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
		case Dialog.BUTTON_POSITIVE:
			setValueInternal(mNumberPicker.getValue(), true);
			break;
		case Dialog.BUTTON_NEUTRAL:
			setValueInternal(null, true);
			break;
		}
	}

}
