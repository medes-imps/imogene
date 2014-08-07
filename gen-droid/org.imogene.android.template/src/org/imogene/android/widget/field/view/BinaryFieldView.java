package org.imogene.android.widget.field.view;

import org.imogene.android.common.binary.Binary;
import org.imogene.android.template.R;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import fr.medes.android.util.content.ContentUrisUtils;

public class BinaryFieldView extends BaseFieldView<Binary> {

	public BinaryFieldView(Context context) {
		super(context, R.layout.imog__field_default);
		init();
	}

	public BinaryFieldView(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.imog__field_default);
		init();
	}

	private void init() {
		setOnClickListener(this);
	}

	@Override
	protected void dispatchClick(View v) {
		if (getValue() != null) {
			Uri uri = ContentUrisUtils.withAppendedId(Binary.Columns.CONTENT_URI, getValue().getId());
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);
		}
	}

	protected int getDisplayId() {
		return R.string.imog__bin_binary;
	}

	@Override
	public String getFieldDisplay() {
		if (getValue() != null) {
			return getResources().getString(getDisplayId());
		}
		return super.getFieldDisplay();
	}

}
