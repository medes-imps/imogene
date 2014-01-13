package org.imogene.android.widget.field.edit;

import org.imogene.android.template.R;
import org.imogene.android.util.Arrays;
import org.imogene.android.util.field.EnumHelper;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

public class EnumSingleFieldEdit extends BaseFieldEdit<Integer> implements DialogInterface.OnClickListener {
	
	private String[] mItems;
	private int[] mItemsValues;
	
	public EnumSingleFieldEdit(Context context) {
		super(context, R.layout.ig_field_default);
		setValue(-1);
	}
	
	public EnumSingleFieldEdit(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.ig_field_default);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EnumField, 0, 0);
		mItems = getResources().getStringArray(a.getResourceId(R.styleable.EnumField_igItems, 0));
		mItemsValues = getResources().getIntArray(a.getResourceId(R.styleable.EnumField_igItemsValues, 0));
		a.recycle();
		setValue(-1);
	}
	
	public void setItems(String[] items) {
		mItems = items;
		mItemsValues = Arrays.defaultIntArray(items.length);
	}
	
	public void setItemsValues(int[] itemsValues) {
		mItemsValues = itemsValues;
	}
	
	@Override
	public boolean isEmpty() {
		final Integer i = getValue();
		return i == null || i == -1;
	}
	
	@Override
	public boolean isValid() {
		Integer value = getValue();
		if (isRequired()) {
			return value != null && value.intValue() != -1;
		}
		return true;
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
			final int intValue = value.intValue();
			if (intValue == -1) {
				return getEmptyText();
			} else {
				return EnumHelper.displayEnumSingle(mItems, mItemsValues, intValue);
			}
		}
	}
	
	@Override
	public Integer getValue() {
		final Integer superValue = super.getValue();
		if (superValue == null) {
			return -1;
		}
		return superValue;
	}
	
	@Override
	public boolean matchesDependencyValue(String value) {
		final Integer i = getValue();
		if (i == null)
			return false;
		
		return i.toString().matches(value);
	}
	
	@Override
	protected void onPrepareDialogBuilder(Builder builder) {
		int checkedItem = -1;
		Integer value = getValue();
		if (value != null) {
			checkedItem = Arrays.find(mItemsValues, value.intValue());
		}
		builder.setSingleChoiceItems(mItems, checkedItem, this);
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
		case Dialog.BUTTON_NEUTRAL:
			setValue(-1);
			break;
		default:
			if (which > -1) {
				setValue(mItemsValues[which]);
				dialog.dismiss();
			}
			break;
		}
	}
	
}
