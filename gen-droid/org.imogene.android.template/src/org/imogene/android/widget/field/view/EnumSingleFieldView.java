package org.imogene.android.widget.field.view;

import org.imogene.android.template.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import fr.medes.android.util.Arrays;
import fr.medes.android.util.field.EnumHelper;

public class EnumSingleFieldView extends DefaultEntityView<Integer> {

	private String[] mItems;
	private int[] mItemsValues;

	public EnumSingleFieldView(Context context) {
		super(context);
	}

	public EnumSingleFieldView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EnumField, 0, 0);
		mItems = getResources().getStringArray(a.getResourceId(R.styleable.EnumField_items, 0));
		mItemsValues = getResources().getIntArray(a.getResourceId(R.styleable.EnumField_itemsValues, 0));
		a.recycle();
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
	public String getFieldDisplay() {
		final Integer value = getValue();
		if (value != null) {
			final int intValue = value.intValue();
			if (intValue != -1) {
				return EnumHelper.displayEnumSingle(mItems, mItemsValues, intValue);
			}
		}
		return super.getFieldDisplay();
	}

	@Override
	public boolean matchesDependencyValue(String value) {
		final Integer i = getValue();
		if (i == null) {
			return false;
		}

		return i.toString().matches(value);
	}

}
