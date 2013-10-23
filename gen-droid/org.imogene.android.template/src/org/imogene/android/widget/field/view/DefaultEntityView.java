package org.imogene.android.widget.field.view;

import org.imogene.android.template.R;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public abstract class DefaultEntityView<T> extends BaseFieldView<T> {

	public DefaultEntityView(Context context) {
		super(context, R.layout.ig_field_default);
		init();
	}
	
	public DefaultEntityView(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.ig_field_default);
		init();
	}
	
	private void init() {
		setOnClickListener(this);
	}
	
	@Override
	protected void dispatchClick(View v) {
		showDialog(null);
	}
	
	@Override
	protected void onPrepareDialogBuilder(Builder builder) {
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setMessage(getFieldDisplay());
		builder.setPositiveButton(android.R.string.ok, null);
	}

}
