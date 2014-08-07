package org.imogene.android.widget.field.view;

import java.util.Arrays;

import org.imogene.android.template.R;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import fr.medes.android.util.field.EnumHelper;

public class EnumMultipleFieldView extends DefaultEntityView<boolean[]> {

	private String[] mItems;
	private int[] mItemsValues;

	public EnumMultipleFieldView(Context context) {
		super(context);
	}

	public EnumMultipleFieldView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EnumField, 0, 0);
		mItems = getResources().getStringArray(a.getResourceId(R.styleable.EnumField_items, 0));
		mItemsValues = getResources().getIntArray(a.getResourceId(R.styleable.EnumField_itemsValues, 0));
		a.recycle();
	}

	public void setItems(String[] items) {
		mItems = items;
	}

	public void setItemsValues(int[] itemsValues) {
		mItemsValues = itemsValues;
	}

	@Override
	public boolean isEmpty() {
		final boolean[] value = getValue();
		return value == null || Arrays.equals(value, new boolean[value.length]);
	}

	@Override
	public String getFieldDisplay() {
		boolean[] value = getValue();
		if (value != null && !Arrays.equals(value, new boolean[value.length])) {
			return EnumHelper.displayEnumMulti(mItems, value);
		}
		return super.getFieldDisplay();
	}

	@Override
	protected void onPrepareDialogBuilder(Builder builder) {
		final String[] items = getFieldDisplay().split(" ; ");
		builder.setItems(items, null);
		builder.setPositiveButton(android.R.string.ok, null);
	}

	@Override
	public boolean matchesDependencyValue(String value) {
		final boolean[] array = getValue();
		if (array == null) {
			return false;
		}

		return EnumHelper.convert(mItemsValues, array).matches(value);
	}

}
