package org.imogene.android.widget.field.edit;

import org.imogene.android.template.R;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextFieldEdit extends StringFieldEdit<String> implements TextWatcher {

	public static final int TYPE_TEXT = 0;
	public static final int TYPE_ADDRESS = 1;
	public static final int TYPE_PHONE = 2;
	public static final int TYPE_TEXT_LONG = 3;
	public static final int TYPE_EMAIL = 4;

	public TextFieldEdit(Context context) {
		super(context, R.layout.imog__field_edit_text);
		setFocusable(false);
	}

	public TextFieldEdit(Context context, int layout) {
		super(context, layout);
		setFocusable(false);
	}

	public TextFieldEdit(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.imog__field_edit_text);
		setFocusable(false);
	}

	@Override
	public boolean isEmpty() {
		return TextUtils.isEmpty(getValue());
	}

	@Override
	public boolean isValid() {
		String value = getValue();
		if (TextUtils.isEmpty(value)) {
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
	public void setTitle(int titleId) {
		super.setTitle(titleId);
		getValueView().setHint(titleId);
	}

	@Override
	public void setTitle(CharSequence title) {
		super.setTitle(title);
		getValueView().setHint(title);
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);
		setStringType(getStringType());
		final TextView v = getValueView();
		if (readOnly) {
			v.removeTextChangedListener(this);
		} else {
			v.addTextChangedListener(this);
		}
	}

	@Override
	public void setStringType(int stringType) {
		super.setStringType(stringType);
		final TextView v = getValueView();
		if (isReadOnly()) {
			v.setEnabled(false);
			v.setInputType(InputType.TYPE_NULL);
		} else {
			v.setEnabled(true);
			switch (stringType) {
			case TYPE_TEXT:
				v.setMaxLines(1);
				v.setInputType(InputType.TYPE_CLASS_TEXT);
				break;
			case TYPE_ADDRESS:
				v.setMinLines(3);
				v.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS
						| InputType.TYPE_TEXT_FLAG_MULTI_LINE);
				break;
			case TYPE_PHONE:
				v.setMaxLines(1);
				v.setInputType(InputType.TYPE_CLASS_PHONE);
				break;
			case TYPE_TEXT_LONG:
				v.setMinLines(3);
				v.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
				break;
			case TYPE_EMAIL:
				v.setMaxLines(1);
				v.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
				break;
			}
		}
	}

	@Override
	public boolean matchesDependencyValue(String value) {
		final String str = getValue();
		return str != null ? str.matches(value) : false;
	}

	@Override
	public String getFieldDisplay() {
		return getValue();
	}

	@Override
	public void afterTextChanged(Editable s) {
		setShouldUpdateValueView(false);
		setValueInternal(s.toString(), true);
		setShouldUpdateValueView(true);
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
