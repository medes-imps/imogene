package org.imogene.android.widget.field.view;

import org.imogene.android.template.R;

import android.content.Context;
import android.util.AttributeSet;

public class PhotoFieldView extends BinaryFieldView {

	public PhotoFieldView(Context context) {
		super(context);
	}

	public PhotoFieldView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public int getDisplayId() {
		return R.string.imog__bin_photo;
	}

}
