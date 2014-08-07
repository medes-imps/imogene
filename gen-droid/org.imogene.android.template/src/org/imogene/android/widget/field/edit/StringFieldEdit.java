package org.imogene.android.widget.field.edit;

import org.imogene.android.template.R;
import org.imogene.android.widget.ErrorAdapter.ErrorEntry;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.util.AttributeSet;

public abstract class StringFieldEdit<T> extends BaseFieldEdit<T> {

	private String[] mRegexs;
	private int[] mRegexDisplayIds;

	private int mStringType;

	public StringFieldEdit(Context context, int layoutId) {
		super(context, layoutId);
	}

	public StringFieldEdit(Context context, AttributeSet attrs, int layoutId) {
		super(context, attrs, layoutId);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StringFieldEdit, 0, 0);
		setStringType(a.getInt(R.styleable.StringFieldEdit_stringType, InputType.TYPE_NULL));
		a.recycle();
	}

	@Override
	public ErrorEntry getErrorEntry(int tag) {
		ErrorEntry entry = super.getErrorEntry(tag);
		if (mRegexDisplayIds != null) {
			for (int i = 0; i < mRegexDisplayIds.length; i++) {
				entry.addMessage(getResources().getString(mRegexDisplayIds[i]));
			}
		}
		return entry;
	}

	public void setStringType(int stringType) {
		mStringType = stringType;
	}

	public int getStringType() {
		return mStringType;
	}

	public void setRegexs(String[] regexs) {
		mRegexs = regexs;
	}

	public String[] getRegexs() {
		return mRegexs;
	}

	public void setRegexDisplayIds(int[] regexDisplayIds) {
		mRegexDisplayIds = regexDisplayIds;
	}

}
