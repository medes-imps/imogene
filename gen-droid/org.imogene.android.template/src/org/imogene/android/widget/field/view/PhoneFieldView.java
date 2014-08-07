package org.imogene.android.widget.field.view;

import org.imogene.android.template.R;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

public class PhoneFieldView extends BaseFieldView<String> {

	public PhoneFieldView(Context context) {
		super(context, R.layout.imog__field_default);
		init();
	}

	public PhoneFieldView(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.imog__field_default);
		init();
	}

	private void init() {
		setOnClickListener(this);
		setIconId(android.R.drawable.sym_action_call);
	}

	@Override
	protected void dispatchClick(View v) {
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", getValue(), null));
		startActivity(intent);
	}

	@Override
	public boolean isEmpty() {
		return TextUtils.isEmpty(getValue());
	}

	@Override
	protected String getFieldDisplay() {
		return getValue();
	}

}
