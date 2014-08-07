package org.imogene.android.widget.field.edit;

import org.imogene.android.template.R;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;

public class SoundFieldEdit extends BinaryFieldEdit {

	public SoundFieldEdit(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected int displayId() {
		return R.string.imog__bin_audio;
	}

	@Override
	protected void onCreateIntent(Intent acquire) {
		acquire.setType("audio/*");
	}

}
