package org.imogene.android.text;

import android.text.Editable;
import android.text.TextWatcher;

public class TextWatcherHelper implements TextWatcher {

	public interface OnTextChangeListener {
		public void onChange(int id, String newText);
	}
	
	private final int mId;
	private final OnTextChangeListener mOnTextChangedListener;
	
	public TextWatcherHelper(final int id, final OnTextChangeListener onTextChangedListener) {
		mId = id;
		mOnTextChangedListener = onTextChangedListener;
	}
	
	@Override
	public void afterTextChanged(Editable s) {
		mOnTextChangedListener.onChange(mId, s.toString());
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
