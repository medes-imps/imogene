package org.imogene.android.widget.field.view;

import org.imogene.android.template.R;

import android.content.Context;
import android.util.AttributeSet;

public class VideoFieldView extends BinaryFieldView {

	public VideoFieldView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public int getDisplayId() {
		return R.string.imog__bin_video;
	}

}
