package org.imogene.android.widget.field.edit;

import java.util.Arrays;

import org.imogene.android.template.R;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import fr.medes.android.util.field.EnumHelper;

public class EnumMultipleFieldEdit extends BaseFieldEdit<boolean[]> implements OnMultiChoiceClickListener,
		android.content.DialogInterface.OnClickListener {

	private String[] mItems;
	private int[] mItemsValues;
	private int mSize;

	private boolean[] mCheckedItems;

	public EnumMultipleFieldEdit(Context context) {
		super(context, R.layout.imog__field_default);
	}

	public EnumMultipleFieldEdit(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.imog__field_default);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EnumField, 0, 0);
		mItems = getResources().getStringArray(a.getResourceId(R.styleable.EnumField_items, 0));
		mItemsValues = getResources().getIntArray(a.getResourceId(R.styleable.EnumField_itemsValues, 0));
		a.recycle();
		mSize = mItems.length;
		setValue(new boolean[mSize]);
	}

	public void setItems(String[] items) {
		mItems = items;
		mSize = items.length;
		mItemsValues = new int[mSize];
		setValue(new boolean[mSize]);
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
	public boolean isValid() {
		final boolean[] value = getValue();
		if (isRequired()) {
			return value != null && !Arrays.equals(value, new boolean[value.length]);
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
	public void setValueInternal(boolean[] value, boolean notifyChange) {
		super.setValueInternal(value, notifyChange);
		mCheckedItems = value != null ? value.clone() : new boolean[mSize];
	}

	@Override
	public String getFieldDisplay() {
		boolean[] value = getValue();
		if (value == null || Arrays.equals(value, new boolean[mSize])) {
			return getEmptyText();
		} else {
			return EnumHelper.displayEnumMulti(mItems, value);
		}
	}

	@Override
	public boolean matchesDependencyValue(String value) {
		final boolean[] array = getValue();
		if (array == null)
			return false;

		return EnumHelper.convert(mItemsValues, array).matches(value);
	}

	@Override
	protected void onPrepareDialogBuilder(Builder builder) {
		builder.setMultiChoiceItems(mItems, getValue(), this);
		builder.setPositiveButton(android.R.string.ok, this);
		builder.setNeutralButton(android.R.string.cut, this);
		builder.setNegativeButton(android.R.string.cancel, this);
	}

	@Override
	public void dispatchClick(View v) {
		showDialog(null);
	}

	@Override
	public void onClick(DialogInterface dialog, int which, boolean isChecked) {
		mCheckedItems[which] = isChecked;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
		case Dialog.BUTTON_POSITIVE:
			setValue(mCheckedItems.clone());
			break;
		case Dialog.BUTTON_NEUTRAL:
			setValue(new boolean[mSize]);
			break;
		}
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		final Parcelable superState = super.onSaveInstanceState();
		final SavedState myState = new SavedState(superState);
		myState.checkedItems = mCheckedItems;
		return myState;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		if (state == null || !state.getClass().equals(SavedState.class)) {
			// Didn't save state for us in onSaveInstanceState
			super.onRestoreInstanceState(state);
			return;
		}

		final SavedState myState = (SavedState) state;
		super.onRestoreInstanceState(myState.getSuperState());
		mCheckedItems = myState.checkedItems;
	}

	private static class SavedState extends BaseSavedState {

		private boolean[] checkedItems;

		public SavedState(Parcel source) {
			super(source);
			source.readBooleanArray(checkedItems);
		}

		public SavedState(Parcelable superState) {
			super(superState);
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			super.writeToParcel(dest, flags);
			dest.writeBooleanArray(checkedItems);
		}

		public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {

			@Override
			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}

			@Override
			public SavedState createFromParcel(Parcel source) {
				return new SavedState(source);
			}
		};

	}

}
