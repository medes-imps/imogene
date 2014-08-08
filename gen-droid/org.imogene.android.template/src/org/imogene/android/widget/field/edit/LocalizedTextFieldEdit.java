package org.imogene.android.widget.field.edit;

import java.util.HashMap;
import java.util.Locale;

import org.imogene.android.common.entity.LocalizedText;
import org.imogene.android.template.R;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import fr.medes.android.util.Arrays;

public class LocalizedTextFieldEdit extends StringFieldEdit<LocalizedText> {

	private final String[] isoArray;
	private final String[] displayArray;

	private boolean mOtherLanguagesHidden = true;

	private final HashMap<String, EditText> mEditors;
	private final ViewGroup mEntries;

	public LocalizedTextFieldEdit(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.imog__field_edit_localized);

		isoArray = getResources().getStringArray(R.array.languages_iso);
		displayArray = getResources().getStringArray(R.array.languages_display);

		int pos = Arrays.find(isoArray, Locale.getDefault().getLanguage());
		if (pos > -1) {
			Arrays.replace(isoArray, 0, pos);
			Arrays.replace(displayArray, 0, pos);
		}

		mEditors = new HashMap<String, EditText>(isoArray.length);

		findViewById(R.id.imog__more_button).setOnClickListener(this);
		findViewById(R.id.imog__less_button).setOnClickListener(this);

		mEntries = (ViewGroup) findViewById(R.id.imog__localized_entries);

		boolean first = true;
		for (int i = 0; i < isoArray.length; i++) {
			if (first) {
				mEditors.put(isoArray[i], (EditText) findViewById(R.id.imog__localized));
				TextView language = (TextView) findViewById(R.id.imog__locale);
				language.setText(displayArray[i]);
				first = false;
			} else {
				ViewGroup entry = (ViewGroup) inflate(context, R.layout.imog__localized_text_editor, null);
				mEntries.addView(entry);
				mEditors.put(isoArray[i], (EditText) entry.findViewById(R.id.imog__localized));
				TextView language = (TextView) entry.findViewById(R.id.imog__locale);
				language.setText(displayArray[i]);
			}
		}

		setFocusable(false);
		updateOtherLanguagesVisibility();
	}

	@Override
	public boolean isEmpty() {
		LocalizedText value = getValue();
		return value != null ? value.isEmpty() : true;
	}

	@Override
	public boolean isValid() {
		LocalizedText value = getValue();
		if (value == null || value.isEmpty()) {
			return !isRequired();
		}
		final String[] regexs = getRegexs();
		if (regexs != null) {
			for (String regex : regexs) {
				if (!value.matches(regex)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	protected void updateView() {
		super.updateView();
		if (!getShouldUpdateValueView()) {
			return;
		}
		for (int i = 0; i < isoArray.length; i++) {
			MyTextWatcher watcher = new MyTextWatcher(isoArray[i]);

			EditText editText = mEditors.get(isoArray[i]);
			editText.setText(getValue() != null ? getValue().getValue(isoArray[i]) : null);
			editText.addTextChangedListener(watcher);
		}
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

	private void updateOtherLanguagesVisibility() {
		findViewById(R.id.imog__more_button).setVisibility(mOtherLanguagesHidden ? View.VISIBLE : View.GONE);
		findViewById(R.id.imog__less_button).setVisibility(mOtherLanguagesHidden ? View.GONE : View.VISIBLE);
		mEntries.setVisibility(mOtherLanguagesHidden ? View.GONE : View.VISIBLE);
	}

	@Override
	protected void dispatchClick(View v) {
		switch (v.getId()) {
		case R.id.imog__less_button:
		case R.id.imog__more_button:
			mOtherLanguagesHidden = !mOtherLanguagesHidden;
			updateOtherLanguagesVisibility();
		}
	}

	@Override
	public String getFieldDisplay() {
		return null;
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		final Parcelable superState = super.onSaveInstanceState();
		final SavedState myState = new SavedState(superState);
		myState.otherLanguagesHidden = mOtherLanguagesHidden;
		return myState;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		if (state == null || !state.getClass().equals(SavedState.class)) {
			// Didn't save state for us in onSaveInstanceState
			super.onRestoreInstanceState(state);
			return;
		}

		SavedState myState = (SavedState) state;
		super.onRestoreInstanceState(myState.getSuperState());
		mOtherLanguagesHidden = myState.otherLanguagesHidden;
		updateOtherLanguagesVisibility();
	}

	private static class SavedState extends BaseSavedState {

		private boolean otherLanguagesHidden;

		public SavedState(Parcel source) {
			super(source);
			otherLanguagesHidden = source.readInt() == 0;
		}

		public SavedState(Parcelable superState) {
			super(superState);
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			super.writeToParcel(dest, flags);
			dest.writeInt(otherLanguagesHidden ? 0 : 1);
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

	private class MyTextWatcher implements TextWatcher {

		private final String locale;

		public MyTextWatcher(String locale) {
			this.locale = locale;
		}

		@Override
		public void afterTextChanged(Editable s) {
			LocalizedText value = getValue();
			if (value == null) {
				value = LocalizedText.newInstance();
				setShouldUpdateValueView(false);
				setValueInternal(value, true);
				setShouldUpdateValueView(true);
			}
			value.setValue(locale, s.toString());
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
}
