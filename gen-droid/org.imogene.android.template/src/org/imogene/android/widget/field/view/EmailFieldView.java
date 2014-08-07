package org.imogene.android.widget.field.view;

import org.imogene.android.template.R;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

public class EmailFieldView extends BaseFieldView<String> {

	public EmailFieldView(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.imog__field_default);
		setOnClickListener(this);
		setIconId(android.R.drawable.sym_action_email);
	}

	@Override
	protected void dispatchClick(View v) {
		Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", getValue(), null));
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
