package org.imogene.android.widget.field.view;

import org.imogene.android.template.R;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;

public class BinaryFieldView extends BaseFieldView<Uri> {
	
	public BinaryFieldView(Context context) {
		super(context, R.layout.ig_field_default);
		init();
	}

	public BinaryFieldView(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.ig_field_default);
		init();
	}
	
	private void init() {
		setOnClickListener(this);
	}

	@Override
	protected void dispatchClick(View v) {
		if (getValue() != null) {
			Intent intent = new Intent(Intent.ACTION_VIEW, getValue());
			getContext().startActivity(intent);
		}
	}
	
	protected int getDisplayId() {
		return R.string.ig_bin_binary;
	}
	
	@Override
	public String getFieldDisplay() {
		if (getValue() != null) {
			return getResources().getString(getDisplayId());			
		}
		return super.getFieldDisplay();
	}
	

}
